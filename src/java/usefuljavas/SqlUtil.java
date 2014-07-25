package usefuljavas;

import java.sql.*;

import javax.servlet.ServletException;

/**
 * SQL Utilities to handle connection pool, query, and update
 *  to SQL Database.
 *
 *  project: Web eigo
 *
 * @author Ransom Barber 2005/05/23
 */

public final class SqlUtil
{

 // these connect-info settings are over-ridden by initialization params in the web.xml

	private static int    connPoolSize = 10;
	
	private static String driver = "com.mysql.jdbc.Driver";
	private static String    url = "jdbc:mysql://localhost/hartbook?autoReconnect=true&?useUnicode=true&characterEncoding=utf8";
	private static String   user = "root";
	private static String    pwd = "yumi@tako";
	private static String dbname = "hartbook";
	
	private static Connection[] connPool = new Connection[connPoolSize];
	private static Statement[]  statPool = new  Statement[connPoolSize];
	private static boolean[]   connInUse = new    boolean[connPoolSize];
	
	
	
	public SqlUtil() throws ServletException 
	{
		System.out.println("dbg: got to SqlUtil()  constructor!");     
		
		int i;
		for (i = 0; i < connPoolSize; i++)
		{
			connPool[i] = getConn();
			connInUse[i] = false;
			
			if (connPool[i] == null) {System.out.println("SqlUtil.init(): conn from getConn() returned null");}
			else 
			{
				try 
				{
					statPool[i] = connPool[i].createStatement();
					statPool[i].executeUpdate("use " + dbname);
				} 
				catch(SQLException ex) {log("SQLException: " + ex.getMessage());}
			}
		}
	}
	
	
	public SqlUtil(
			String xdriver,
			String    xurl,
			String   xuser,
			String    xpwd,
			String xdbname,
			String xconnPoolSize
			) throws ServletException 
		
	{
		System.out.println("dbg: got to SqlUtil()  constructor!");     
		
		driver = xdriver; 
		url = xurl; 
		user = xuser; 
		pwd = xpwd; 
		dbname = xdbname; 
		
		try { connPoolSize = Integer.parseInt(xconnPoolSize);  } 
		catch(NumberFormatException ignored) { }  // null or non-integer value
		
		int i;
		for (i = 0; i < connPoolSize; i++)
		{
			connPool[i] = getConn();
			connInUse[i] = false;
			
			if (connPool[i] == null) {System.out.println("SqlUtil.init(): conn from getConn() returned null");}
			else 
			{
				try 
				{
					statPool[i] = connPool[i].createStatement();
					statPool[i].executeUpdate("use " + dbname);
				} 
				catch(SQLException ex) {log("SQLException: " + ex.getMessage());}
			}
		}
	}
	
	
	private static Connection getConn() throws ServletException 
	{
	// todo: make connection pool initialized with pool size and 
		Connection conn = null;
		
		try   { Class.forName(driver); } 
		catch ( java.lang.ClassNotFoundException e ) { System.out.println("Class.forName() failed.<br>"); return null;}
		
		try 
		{
			conn = DriverManager.getConnection(url, user, pwd);
			if (conn == null) {log("SqlUtil.getConn(): conn from DriverManager.getConnection() returned null but not error");}
			return conn;
		} 
		catch(SQLException ex) 
		{
			log("SqlUtil.getConn(): conn from DriverManager.getConnection() raised exception SQLException: " + ex.getMessage() );
			return null;
		}
	}



	public static ResultSet execWithRetry(String sql, boolean hasResult) throws ServletException
	{
		ResultSet rs = null;
		int retries = 0;
		int c = -1;
		c = getConnFromPool();
		if (c == -1) {log("free conn pool index was c = " + String.valueOf(c));}
		if (statPool[c] == null) {log("conn/stat was null, free conn pool index was c = " + String.valueOf(c));}
		
		// deal with automatic retry crap for mysql server connection timeout wait_timeout
		boolean success = false;
		do
		{
			try
			{
				if (hasResult)
					rs = statPool[c].executeQuery(sql);
				else
					statPool[c].executeUpdate(sql);
				success = true;
			}
			catch(SQLException ex)
			{
				System.out.println("SQLException message:" + ex.getMessage());
				System.out.println("SQLException getErrorCode: " + ex.getErrorCode());
				System.out.println("SQLException getSQLState:" + ex.getSQLState());
				// assume this is due to mysql wait_timeout
				if (retries++ > 2) 
				{
					returnConnToPool(c);
					return null;
				}
				try {Thread.sleep(200);} // 200 milliseconds sleep.
				catch(InterruptedException ignored){}
				
				connPool[c] = getConn();
				if (connPool[c] == null) 
				{
					System.out.println("SqlUtil.init(): conn from getConn() returned null");
					returnConnToPool(c);
					return null;
				}
				else 
				{
					try 
					{
						statPool[c] = connPool[c].createStatement();
						statPool[c].executeUpdate("use " + dbname);
					} 
					catch(SQLException e) {log("SQLException: " + e.getMessage());}
				}

			}
		} while (!success);

		returnConnToPool(c);
		
		return rs;
	
	}
	
	public static ResultSet sqlQuery(String sql) throws ServletException 
	{
		return execWithRetry(sql, true);
	}
	
	
	public static void sqlExec(String sql) throws ServletException 
	{
		execWithRetry(sql, false);
	}
	
	
	private static int getConnFromPool() 
	{
		int found = -1;
		int retries = 0;
		
		while (found == -1) 
		{
			found = getFreeConn();
			
			if (found == -1) {if (retries++ > 100) {return -1;}
				try {Thread.sleep(200);} // 200 milliseconds sleep.
			catch(InterruptedException ignored)  { }}
		}
		
		return found;
	}
	
	
	private static synchronized int getFreeConn() 
	{
		int found = -1;
		
		for (int i = 0; i < connPoolSize; i++) {if ((!connInUse[i]) && (connPool[i] != null)) {found = i;}}
		if (found >= 0) {connInUse[found] = true;}
		return found;
	}
	
	
	private static void returnConnToPool(int i) {connInUse[i] = false;}
	
	@Override
	public void finalize() throws ServletException 
	{
		System.out.println("dbg: got to SqlUtil() destructor, closing connections and statements in pool!"); 
		
		for (int i = 0; i < connPoolSize; i++) {releaseConn(i);}
	}
	
	
	private static void releaseConn(int i) throws ServletException 
	{
		try 
		{
			statPool[i].close();
			connPool[i].close();
		} 
		catch(SQLException ex) {log("SQLException: " + ex.getMessage() + "<br>");}
	}
	
	
	private static void log(String msg) throws ServletException {System.out.println(msg); throw new ServletException(msg);}
	// hack: we use ServletException here just because the main java servlet stuff already declares it thrown
	//  which means we don't have to add yet another entry to the throws clause of the method callers.
	// THIS causes everything that calls log() to have to declare that it throws this too
	//  which then passes the buck to the routines that call these methods.
	
}
