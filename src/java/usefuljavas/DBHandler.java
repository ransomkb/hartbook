// Altered 2006-06-4

// figure out why the search does not return a full set

// Class to allow other classes to select, insert and update tables in a database.

package usefuljavas;

import usefulbeans.TableRow;
import usefulbeans.WordBean;
import java.sql.*;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;


public class DBHandler
{
    
	public static void closeSSRS(SessionState ss)
    {
    	try {ss.rs.close();}
		catch(SQLException ex) {ss.dbgApp("SQLException: " + ex.getMessage());}
    }
	
	// for DisplayLists
	public static String checkRowType(int x, SessionState ss)
	{
		try
		{
			String sql = "SELECT type FROM "+ss.table+" WHERE ID = "+x;
			setQuery(sql, ss);
			
			ss.rs.beforeFirst();
			
			if(ss.rs.next())
			{
				String type = ss.rs.getString("type");
				return type;
			}
			else
				return "Invalid";
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.checkRowType ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.checkRowType SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.checkRowType Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return "Invalid";
	}
	
	
	// for GetSessionState.java
	
	
	// Handles the user's choice from the Log-In form. It sets the Appropriate Response.
	public static boolean handleLogIn(String userid, String password, SessionState ss)
	{
		try
		{
			if(!ss.loggedIn)
			{
                ss.rs = null;

				ss.cleanSlate();
					//ss.dbgApp("got here handleLogIn 1: ss.cleanSlate done");
				
				String sql = "SELECT userID, password, role, teacherID, contact FROM userlist WHERE userID = '"+userid+
					"' AND password = '"+password+"'";
					//ss.dbgApp("got here handleLogIn 2: sql = "+sql);
				
				ss.rs = SqlUtil.sqlQuery(sql);
				
				ss.rs.beforeFirst();
				
				if(!ss.rs.next()) 
				{
                    ss.logmes = Messages.getMess55(ss);
					return false;
				}
				else
				{
					ss.loggedIn = true;
						//ss.dbgApp("got here handleLogIn 3: there was a result set");
					
					ss.userID = ss.rs.getString("userID");
					ss.psswd = ss.rs.getString("password");
					ss.teacher = ss.rs.getString("teacherID");
					ss.role = ss.rs.getString("role");
					ss.contact = ss.rs.getString("contact");
					ss.registered = ss.registerUserId(ss.userID);
					
					if (!ss.registered)
					{
						// This userid is in use by another session which we have 
						// no control over.  They must use or close the other session,
						//  or wait for it to time-out.
						//ss.logmes = Messages.getMess52(ss);
                        ss.loggedIn = false;
                        ss.logmes = Messages.getMess52(ss);
						return false;  // caller should check for false and invalidate hSession.
					}
						//ss.dbgApp("got here handleLogIn 4: ss.userID = "+ss.userID+", ss.psswd = "+ss.psswd+", ss.teacher = "+ss.teacher+", ss.role = "+ss.role+", ss.contact = "+ss.contact+", ss.registered = "+ss.registered);					
					
					return true;
				}
			}

            ss.logmes = Messages.getMess52(ss);
			return false;
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.handleLogIn ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.handleLogIn SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.handleLogIn Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		return false; 
	}
			
	// Handles the user's choice from the Log-In form. It sets the Appropriate Response.
	public static boolean handleRegistration
		(
			HttpServletResponse res,
			String familyName, 
			String firstName, 
			String email, 
			String userid, 
			String password1, 
			SessionState ss
		)
	{
		try
		{
			String sql = "SELECT userID FROM userlist WHERE userID = '"+userid+"'";
			
			ss.rs = SqlUtil.sqlQuery(sql);
						
			ss.rs.beforeFirst();
				//ss.dbgApp("got here handleReg 1: sql = "+sql);
			
			if(ss.rs.next()) 
			{
				ss.logmes = Messages.getMess50(ss);
				return false;
			}
			else
			{
				sql = "INSERT INTO userlist SET userID = '"+userid+"', password = '"+password1+
					"', familyName = '"+familyName+"', firstName = '"+firstName+"', contact = '"+email+
					"', instID = 'Individual', teacherID = '"+userid+"', role = 'teacher', accessCode = 'new'";
					
				SqlUtil.sqlExec(sql);
					//ss.dbgApp("got here handleReg 2: sql = "+sql);
				
				sql = "SELECT userID, password, teacherID, role, contact FROM userlist WHERE userID = '"+userid+
					"' AND password = '"+password1+"'";
					
				ss.rs = SqlUtil.sqlQuery(sql);
					//ss.dbgApp("got here handleReg 3: sql = "+sql);
							
				ss.rs.beforeFirst();
				
				if(!ss.rs.next()) 
				{
                    ss.logmes = Messages.getMess55(ss);
					return false;
				}
				else
				{
					ss.loggedIn = true;
						//ss.dbgApp("got here handleLogIn 3: there was a result set");
					
					ss.userID = ss.rs.getString("userID");
					ss.psswd = ss.rs.getString("password");
					ss.teacher = ss.rs.getString("teacherID");
					ss.role = ss.rs.getString("role");
					ss.contact = ss.rs.getString("contact");
					ss.registered = ss.registerUserId(ss.userID);
						//ss.dbgApp("got here handleReg 4: ss.userID = "+ss.userID+", ss.psswd = "+ss.psswd+", ss.teacher = "+ss.teacher+", ss.role = "+ss.role+", ss.contact = "+ss.contact+", ss.registered = "+ss.registered);					
					
					return true;
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.handleRegistration ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.handleRegistration SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.handleRegistration Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		return false; 
	}
	
	
	
	// for various programs
	
	
	public static void addToList(SessionState ss)
	{
		try
		{
			ss.wordList = new ArrayList<WordBean>();
			
			ss.rs.beforeFirst();
			
			while(ss.rs.next())
			{
				int x = 1;
				
				WordBean wBean = new WordBean();
				
				wBean.id = ss.rs.getInt("ID");
				wBean.typ = ss.rs.getString("type");
				wBean.eng = ss.rs.getString("english");
				wBean.kan = ss.rs.getString("meaning");
				
				if(ss.threeCols)
					wBean.yom = ss.rs.getString("reading");
				
				wBean.rowID = String.valueOf(wBean.id);
				
				ss.wordList.add(wBean);
					//ss.dbgApp("got here addToList "+x); //debug
				x++;
			}
		}
		catch(SQLException ex) { ss.dbgApp("dbHand.addToList SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.addToList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void fetchSearch(SessionState ss)
	{
		try
		{
			String sql = "";
			
			ss.wordList = new ArrayList<WordBean>();
				//ss.dbgApp("got here fetchSearch 1"); //debug
			
			// all search sql statements used to include the three columns: eiken, jtest, business
			if(ss.threeCols)
			{
				sql = "SELECT ID, edit, type, english, meaning, reading FROM "+ss.table+" WHERE english = '"+ss.searchVar+"' ";
			}
			else 
			{
				sql = "SELECT ID, edit, type, english, meaning FROM "+ss.table+" WHERE english = '"+ss.searchVar+"' ";
			}
			
			ss.rs = SqlUtil.sqlQuery(sql);
			
			if(!ss.rs.next())
			{
					//ss.dbgApp("got here fetchSearch 2. no eng"); //debug
				if(ss.threeCols)
				{
					sql = "SELECT ID, edit, type, english, meaning, reading FROM "+ss.table+
					" WHERE meaning = '"+ss.searchVar+"' ";
				}
				else 
				{
					sql = "SELECT ID, edit, type, english, meaning FROM "+ss.table+
					" WHERE meaning = '"+ss.searchVar+"' ";
				}
				
				ss.rs = SqlUtil.sqlQuery(sql);	
				
				if(ss.threeCols)
				{
					if(!ss.rs.next())
					{
						//ss.dbgApp("got here fetchSearch 3, no kan"); //debug
						sql = "SELECT ID, edit, type, english, meaning, reading FROM "+ss.table+" WHERE reading = '"+ss.searchVar+"' ";
						
						ss.rs = SqlUtil.sqlQuery(sql);	
						
						if(!ss.rs.next()) { ss.message = Messages.getMess27(ss); return; }
					}
				}
				else { ss.message = Messages.getMess27(ss); return; }
			}
			else 
			{
				//ss.dbgApp("got here fetchSearch 4, got eng"); //debug
			}
			
			ss.message = Messages.getMess26(ss);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchSearch ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchSearch SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchSearch Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// used to be fetchWordList but changed when another method with same name was moved here from bkMeth
	// check to see that all methods are using the correct one
	public static void fetchListRows(SessionState ss)
	{
		try
		{
			String sql = "";
			
			ss.wordList = new ArrayList<WordBean>();
					//ss.dbgApp("got here rsBean.fetchListRows 1"+ss.list); //debug
			
			if(ss.list.isEmpty())
			{
					//ss.dbgApp("got here rsBean.fetchListRows (list is empty)"+ss.list); //debug
				WordBean wBean = new WordBean();
				
				wBean.typ = "Empty";
				wBean.eng = "Empty";
				wBean.kan = "Empty";
				wBean.yom = "Empty";
			
				ss.wordList.add(wBean);
			}
			else
			{
				for(int x : ss.list)
				{
					//ss.dbgApp("got here rsBean.fetchListRows 2: "+x); //debug
					if(ss.threeCols)
					{
						sql = "SELECT ID, edit, type, english, meaning, reading FROM "+ss.table+" WHERE ID = "+x;
					}
					else
					{
						sql = "SELECT ID, edit, type, english, meaning FROM "+ss.table+" WHERE ID = "+x;
					}
					
					ss.rs = SqlUtil.sqlQuery(sql);
					
					ss.rs.beforeFirst();
					
					if(ss.rs.next())
					{
						WordBean wBean = new WordBean();
						
						wBean.id = ss.rs.getInt("ID");
						wBean.edit = ss.rs.getInt("edit");
						wBean.typ = ss.rs.getString("type");
						wBean.eng = ss.rs.getString("english");
						wBean.kan = ss.rs.getString("meaning");
						
						if(ss.threeCols)
							wBean.yom = ss.rs.getString("reading");
						
						wBean.rowID = String.valueOf(wBean.id);
						
						ss.wordList.add(wBean);
					}
				}
					//ss.dbgApp("got here rsBean.fetchListRows 3"); //debug
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchListRows ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchListRows SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchListRows Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	} // fetches all of the rows in ss.table that are in ss.list, assigns the values to a WordBean, which is added to ss.wordList
	
	
	// probably won't be using this because misunderstood how to use AND
	public static void fetchWholeList(SessionState ss)
	{
		try
		{
			String sql = "";
			
			ss.whereCol = "ID";
			
			ss.wordList = new ArrayList<WordBean>();
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			list = ss.list;
			
			if(ss.threeCols)
			{
				sql = "SELECT ID, edit, type, english, meaning, reading FROM " + ss.table + " WHERE " +
					getWholeList(list, ss)+ "ORDER BY ID DESC";
			}
			else 
			{
				sql = "SELECT ID, edit, type, english, meaning FROM " + ss.table + " WHERE " +
					getWholeList(list, ss)+ "ORDER BY ID DESC";
			}
				//ss.dbgApp("got to setQuery of fetchWholeList, sql: "+sql); 
			
			setQuery(sql, ss);
			
			ss.rs.beforeFirst();
				//ss.dbgApp("got to rs of fetchWholeList"); 
			
			while(ss.rs.next())
			{
				WordBean wBean = new WordBean();
				
				wBean.id = ss.rs.getInt("ID");
				wBean.edit = ss.rs.getInt("edit");
				wBean.typ = ss.rs.getString("type");
				wBean.eng = ss.rs.getString("english");
				wBean.kan = ss.rs.getString("meaning");
				
				if(ss.threeCols)
					wBean.yom = ss.rs.getString("reading");
				
				wBean.rowID = String.valueOf(wBean.id);
				
				ss.wordList.add(wBean);
					//ss.dbgApp("got to ss.wordList.add(wBean): "+wBean.getID()+" of fetchWholeList");
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchWholeList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchWholeList SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchWholeList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// probably won't be using this because misunderstood how to use AND
	private static String getWholeList(ArrayList<Integer> list, SessionState ss)
	{
		String wholeList = "";
		
		for(int i = 0; i < 6; i++)
		{
			 String idNum = String.valueOf(list.get(i));
			
			wholeList = wholeList + ss.whereCol + " = "+list.get(i)+" ";
			
			if(i != (5))
			{
				wholeList = wholeList + "AND ";
			}
		}
		
		return wholeList;
	}
	
	
	// SETS MYLIST - IS IT NEEDED? yes, ss.tempList is needed, but not myList
	public static void fetchTemp(String word, SessionState ss)
	{
		try
		{
			String sql = "";
			
			ss.tempList = new ArrayList<WordBean>();
			
			ss.setLists();
					//ss.dbgApp("got here fetchTemp 1");
			
			if(ss.threeCols)
			{
				sql = "SELECT ID, type, english, meaning, reading, engexamp, langexamp FROM "+ss.table+" WHERE "+ss.tempCol+" = '"+word+"'";
			}
			else 
			{
				sql = "SELECT ID, type, english, meaning, engexamp, langexamp FROM "+ss.table+" WHERE "+ss.tempCol+" = '"+word+"'";
			}
			
			ss.rs = SqlUtil.sqlQuery(sql);
					//ss.dbgApp("got here fetchTemp 2 sent sql ["+sql+"]");
					
			ss.rs.beforeFirst();
					//ss.dbgApp("got here fetchTemp 3 placed beforeFirst"); 
			
			while(ss.rs.next())
			{
					//ss.dbgApp("got here fetchTemp 4 while RS.next"); 
				WordBean wb = new WordBean();
				
				wb.id = ss.rs.getInt("ID");
				wb.typ = ss.rs.getString("type");
				wb.eng = ss.rs.getString("english");
				wb.kan = ss.rs.getString("meaning");
				
				wb.rowID = String.valueOf(wb.id);
				
				if(ss.threeCols)
					wb.yom = ss.rs.getString("reading");
                
                Clob engEx = ss.rs.getClob("engexamp");
                Clob langEx = ss.rs.getClob("langexamp");

                wb.setEngEx(clobString(engEx, ss));
                wb.setLangEx(clobString(langEx, ss));


				ss.tempList.add(wb);
					//ss.dbgApp("got here fetchTemp 5 wb added to tempList"); 
				
				if(ss.myList.contains(wb.rowID)) continue;
				if(ss.knowList.contains(wb.rowID)) continue;
				if(ss.maybeList.contains(wb.rowID)) continue;
				
				ss.type = wb.typ;
				ss.setLists(); // IMPORTANT: this may not be necessary now.
					//ss.dbgApp("got here fetchTemp 6 wb added to myList"); 
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchTemp ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchTemp SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchTemp Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	public static void fetchHistoryLists(SessionState ss)
	{
		try
		{
			//ss.dbgApp("got here fetchHistoryLists try"); debug
			String sql = "SELECT scores, history FROM userlist WHERE userID = '"+ss.userID+"' ";
			ss.rs = SqlUtil.sqlQuery(sql);
			
			while(ss.rs.next())
			{
				//ss.dbgApp("got here fetchHistoryLists rs"); debug
				//ss.dbgApp("got here fetchHistoryLists tString"); debug
				
				Clob sc = ss.rs.getClob("scores");
				Clob hs = ss.rs.getClob("history");
					//ss.dbgApp("got here fetchHistoryLists clobs"); debug
				
				if (sc.length() == 0 || sc == null)	
				{
					//ss.dbgApp("sc clob is empty"); debug
					ss.stringScores();
				}
				else ss.scores = clobString(sc, ss);
				
				if (hs.length() == 0 || hs == null)	
				{
						//ss.dbgApp("hs clob is empty"); debug
					ss.stringHistory();
				}
				else ss.history = clobString(hs, ss);
					//ss.dbgApp("hs clob is "+ss.history); debug
				
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchHistoryLists ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchHistoryLists SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchHistoryLists Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void fetchUserLists(SessionState ss)
	{
		try
		{
			String sql = "";
			//ss.dbgApp("got here fetchLists try"); //debug
			
			sql = "SELECT teacherID, maybe, know FROM userlist WHERE userID = '"+ss.userID+"' ";
			ss.rs = SqlUtil.sqlQuery(sql);
				//ss.dbgApp("sql: "+sql); //debug
			
			ss.rs.beforeFirst();
					
			while(ss.rs.next())
			{
				//ss.dbgApp("got here fetchLists rs"); //debug
				ss.myteacher = ss.rs.getString("teacherID");
				//ss.dbgApp("got here fetchLists tString"); //debug
				
				Clob mb = ss.rs.getClob("maybe");
				Clob kn = ss.rs.getClob("know");
				//ss.dbgApp("got here fetchLists clobs"); //debug
				
				if (mb.length() == 0 || mb == null)	
					ss.dbgApp("mb clob is empty"); //debug
				else 
					ss.maybeClobString = clobString(mb, ss);
				
				if (kn.length() == 0 || kn == null)	
					ss.dbgApp("kn clob is empty"); //debug
				else 
					ss.knowClobString = clobString(kn, ss);
				//ss.dbgApp("got here fetchLists clobs not empty"); //debug
			}
			
			if(!ss.role.equals("user"))
			{
				sql = "SELECT typelist, chaplist FROM teachers WHERE teacherID = '"+ss.myteacher+"' ";
				
				ss.rs = SqlUtil.sqlQuery(sql);
				//ss.dbgApp("sql: "+sql);
				
				ss.rs.beforeFirst();
						
				while(ss.rs.next())
				{
					//ss.dbgApp("got here fetchLists fetchTString"); //debug
					Clob tch = ss.rs.getClob("typelist");
					Clob chap = ss.rs.getClob("chaplist");
					
					if (tch.length() == 0 || tch == null)	
						ss.dbgApp("tl clob is empty"); //debug
					else 
						ss.tchClobString = clobString(tch, ss);
					
					if (chap.length() == 0 || chap == null)	
						ss.dbgApp("t2 clob is empty"); //debug
					else 
						ss.chapClobString = clobString(chap, ss);
				}
			}
			
			BookMethods.setAllLists(ss);
			
			sql = "SELECT chaplist FROM teachers WHERE teacherID = 'ransom' ";
			
			ss.rs = SqlUtil.sqlQuery(sql);
				//ss.dbgApp("sql: "+sql);
				
			ss.rs.beforeFirst();
					
			while(ss.rs.next())
			{
				//ss.dbgApp("got here fetchLists fetchTString"); //debug
				Clob ad = ss.rs.getClob("chaplist");
				
				if (ad.length() == 0 || ad == null)	
					ss.dbgApp("ad clob is empty"); //debug
				else 
					ss.adminClobString = clobString(ad, ss);
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchUserLists ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchUserLists SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchUserLists Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static String clobString(Clob clob, SessionState ss) throws SQLException
	{
		long x = 1;
		long len = clob.length();
		
		String clobString = clob.getSubString(x, (int)len);
		
		return clobString;
	}
	
	
	public static void selectList(SessionState ss)
	{
		try
		{
			ss.wordList = new ArrayList<WordBean>();
					//ss.dbgApp("got here selectList 1"+ss.list); //debug
			
			ArrayList<Integer> randList = new ArrayList<Integer>();
			ArrayList<String> doNotUseList = new ArrayList<String>();

            if(!ss.wholeList)
            {
                if(ss.learn.equals("study")) {doNotUseList = ss.tempStudyList2;}
                else if(ss.learn.equals("review")){doNotUseList = ss.tempHideList;}
                else if(ss.learn.equals("test")) {doNotUseList = ss.tempTestList2;}
            }
            else
            {
                doNotUseList.add("empty");
            }

			randList = randomizeList(randList, ss);
					//ss.dbgApp("got here selectList 2: randomized"); //debug
					
			for(int x : randList)
			{
                if(checkLists(x, ss)) continue;
				
				if(!doNotUseList.contains(String.valueOf(x)))
				{
					String sql = "";
					
					if(ss.teachChapList)
					{ 
						if(ss.threeCols)
						{
							sql = "SELECT ID, type, english, meaning, reading FROM "+ss.table+" WHERE ID = "+x; 
						}
						else 
						{
							sql = "SELECT ID, type, english, meaning FROM "+ss.table+" WHERE ID = "+x; 
						}
					}
					else 
					{ 
						if(ss.threeCols)
						{
							sql = "SELECT ID, type, english, meaning, reading FROM "+ss.table+" WHERE ID = "+x+" AND type = '"+ss.type+"' "; 
						}
						else 
						{
							sql = "SELECT ID, type, english, meaning FROM "+ss.table+" WHERE ID = "+x+" AND type = '"+ss.type+"' "; 
						}
					}
					
					ss.rs = SqlUtil.sqlQuery(sql);
						//ss.dbgApp("got here selectList 2A: sql = "+sql); //debug
						
					ss.rs.beforeFirst();
					
					while(ss.rs.next())
					{
						WordBean wBean = new WordBean();
						
						wBean.id = ss.rs.getInt("ID");
						wBean.typ = ss.rs.getString("type");
						wBean.eng = ss.rs.getString("english");
						wBean.kan = ss.rs.getString("meaning");
						
						if(ss.threeCols)
							wBean.yom = ss.rs.getString("reading");
						
						wBean.rowID = String.valueOf(wBean.id);
						
						ss.wordList.add(wBean);
							//ss.dbgApp("got here selectList 3 "+wBean.rowID+" "+wBean.eng); //debug						
					}
				}
			}
				//ss.dbgApp("got here selectList 4"); //debug
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.selectList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.selectList SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.selectList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// used to determine if the integer from the list is in one of the user's know or maybe lists
	private static boolean checkLists(int x, SessionState ss)
	{
		if(ss.listGroup.equals("knowList") || ss.listGroup.equals("maybeList")) // IMPORTANT: check if this is the best place to put this
			return false;
		else
		{
			for(Types type : Types.values())
			{
				for(Lists list : EnumSet.range(Lists.KNOWLIST, Lists.MAYBELIST))
				{
					String name = list.getListName(type.getName());
					
					if(ss.mapKeyCheckSubKey(name, String.valueOf(x), ss)) {return true;} 
				}
			}
		}
		
		return false;
	}
	
	
	private static ArrayList<Integer> randomizeList(ArrayList<Integer> randList, SessionState ss)
	{
		Random randomInt = new Random();
		
		if(ss.list.size() > 60)
		{
			while(randList.size() <= 50)
			{
				int id = randomInt.nextInt(ss.list.size());
				
				if(randList.contains(ss.list.get(id))) continue;
				randList.add(ss.list.get(id));
			}
		}
		else
		{
			while(randList.size() < (ss.list.size()*80/100))
			{
				int id = randomInt.nextInt(ss.list.size());
				
				if(randList.contains(ss.list.get(id))) continue;
				else randList.add(ss.list.get(id));
			}
			
			for(int x : ss.list)
			{
				if(randList.contains(x)) continue;
				else if(randList.size() <= 50) { randList.add(x); }
				else break;
			}
		}
		
		return randList;
	}
	
	
	// Selects the eiken list, then chooses 50 of those IDs that are not already in map's my, know or maybe
	public static void addWordSets(SessionState ss)
	{
		try
		{
			int i = 0;
			String csv = "", my = Lists.MYLIST.getListName(ss.type);
			
			if(ss.role.equals("sampler"))
				ss.checkMap(ss);
				
			ss.listName = getAnEikenList(ss);
			
			if(ss.listName.equals("Invalid")) return;
				//ss.dbgApp("got here addWordSets 1: ss.type = "+ss.type); //debug
				
			ss.setLists();
			
			selectEikenList(ss.listName, ss);  // selects an eiken list from table lists
			
			ss.rs.beforeFirst();
			
			while(ss.rs.next())
			{
				Clob c = ss.rs.getClob("csvlist");
				
				if(c == null) break;
				
				csv = clobString(c, ss);
				
				if(csv == null) break;
				else if(csv.length() == 0) break;
				
				String[] str = StringHandler.splitSecondString(csv);
				
				for(String rowID : str)
				{
					if(i >= 50) break;
					
					if(ss.mapKeyCheckAll(rowID, ss)) continue;
					
					ss.addToMapVal(my, rowID, ss);
					
					i++;
				}
			}
				//ss.dbgApp("got here addWordSets 3: rs number = "+i); //debug
			
			ss.setTypes();
			ss.setLists();
			
			if(i == 0) {ss.message = Messages.getMess18(ss);}
			else {ss.message = Messages.getMess19(i, ss);}
		}
		catch(SQLException ex) { ss.dbgApp("dbHand.addWordSets SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.addWordSets Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// Returns an ArrayList of WordBeans from the result set.
	// List may need to be synchronized for the future.
	public static void setWordList(SessionState ss)
	{
		try
		{
			ss.setLists();
			
			ss.wordList = new ArrayList<WordBean>();
			
			ss.rs.beforeFirst();
			
			while(ss.rs.next())
			{
				WordBean wBean = new WordBean();
				
				wBean.id = ss.rs.getInt("ID");
				
				wBean.rowID = String.valueOf(wBean.id);
				
				if(!ss.wholeList)
				{
					if(ss.knowList != null && ss.knowList.size() != 0)
						{if(ss.knowList.contains(wBean.rowID)) continue;}
					else if(ss.tempHideList != null && ss.tempHideList.size() != 0)
						{if(ss.tempHideList.contains(wBean.rowID)) continue;}
					else if(ss.maybeList != null && ss.maybeList.size() != 0)
						{if(ss.maybeList.contains(wBean.rowID)) continue;}
				}
				
				wBean.eng = ss.rs.getString("english");
				wBean.kan = ss.rs.getString("meaning");
				
				if(ss.threeCols)
					wBean.yom = ss.rs.getString("reading");
				
				ss.wordList.add(wBean);
			}
		}
		catch(SQLException ex) { ss.dbgApp("dbHand.setWordList SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.setWordList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	// May want to set this to return void. See if anything takes the wordlist
	
	// Returns an ArrayList of WordBeans from the result set.
	// List may need to be synchronized for the future.
	public static void getWordList(SessionState ss)
	{
		try
		{
			ss.wordList = new ArrayList<WordBean>();
			
			ss.rs.beforeFirst();
				//ss.dbgApp("got to rsBean.getWordList (beforeFirst)"); 
				
			while(ss.rs.next())
			{
				WordBean wBean = new WordBean();
				
				wBean.id = ss.rs.getInt("ID");
				wBean.edit = ss.rs.getInt("edit");
				wBean.typ = ss.rs.getString("type");
				wBean.eng = ss.rs.getString("english");
				wBean.kan = ss.rs.getString("meaning");
				
				if(ss.threeCols)
				{
					wBean.yom = ss.rs.getString("reading");
				}
				
				wBean.rowID = String.valueOf(wBean.id);
				
				ss.wordList.add(wBean);
			}
				//ss.dbgApp("got to rsBean.getWordList (wBean(s) added to ss.wordList)"); 
		}
		catch(SQLException ex) { ss.dbgApp("dbHand.getWordList SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.getWordList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// for saveState stuff
	
	
	// 
	public static void saveState(SessionState ss) // throws SQLException, ServletException
	{
		if(ss.role.equals("sampler")) return;
		
		ss.setLists();
		ss.stringScores();
		
		saveMap(ss); // updates the lists table with all the csvLists that are created by using ss.mapValueToCSV()
			// while iterating through Types and Lists
		
		updateScores(ss);
	}
	
	
	private static void saveMap(SessionState ss)
	{
		for(Types type : Types.values())
		{
			for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
			{
				String listName = list.getListName(type.getName());
				String csvList = ss.mapValueToCSV(listName, ss);
				
				if(csvList.length() == 0) continue;
				
				updateDBList(ss.userID, listName, csvList, ss);
			}
		}
	} // updates the lists table with all the csvLists that are created by using ss.mapValueToCSV()
		// while iterating through Types and Lists
	


    // IMPORTANT: see if used or needed
	private static String[][] getIntArray(ArrayList<String> verbs, ArrayList<String> nouns, ArrayList<String> adjs, ArrayList<String> misc)
	{
		String lists[][];
		lists = new String[4][];
		lists[0] = verbs.toArray(new String[verbs.size()]);
		lists[1] = nouns.toArray(new String[nouns.size()]);
		lists[2] = adjs.toArray(new String[adjs.size()]);
		lists[3] = misc.toArray(new String[misc.size()]);
		
		return lists;
	}
	
	
	private static String stringList(String integersList[][], SessionState ss)
	{
		String intString = "";
		String numString = "";
		String typString = "";
		String finalString = "";
		
		for(int i = 0; i < integersList.length; i++)
		{
			for(int y = 0; y < integersList[i].length; y++)
			{
				intString = integersList[i][y];
				numString = numString + intString + ",";
			}
			
			typString = Selections.t[i] + "," + numString;
			finalString = finalString + typString + ";";
			numString = "";
			typString = "";
		}
		return finalString;
	}
	
	
	// probably unnecessary
	private static String addLangStrings(ArrayList<String> langList, String temp, SessionState ss)
	{
		String allLang = "";
		
		for(String clobString : langList)
		{
			if((clobString.length() == 0) || (clobString == null)) {  } // do nothing; used to be - typList = new ArrayList<String>();
			else
			{
				String[] tList = clobString.split(";"); //{"123;456;444;22", "12;13;14;55;66"};
				
				if(ss.table.equals(tList[0]))
				{
					clobString = ss.table+";"+temp;
				}
			}
			
			allLang = clobString+":"+allLang;
		}
		
		return allLang;
	}
	
	
	// for linking all the strings in ss.everyList into one semicolon separated list
	private static String everyLangString(SessionState ss)
	{
		String o = ss.TimeStarted.getTime().toString();
		
		for(String s : ss.everyList)
		{
			s = ss.language+","+s;
			o = o+";"+s;
		}
		
		return o;
	}
	
	
	// Was updateClob
	private static void updateScores(SessionState ss)
	{	
		try
		{
			String sql = "UPDATE userlist SET scores = '"+ss.scores+"' WHERE userID = '"+ss.userID+"'";
			
			SqlUtil.sqlExec(sql);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateChapClob ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateChapClob Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void updateHistoryClob(SessionState ss)
	{	
		try
		{
			ss.stringHistory();
			
			String sql = "UPDATE userlist SET history = '"+ss.history+"' WHERE userID = '"+ss.userID+"'";
			
			SqlUtil.sqlExec(sql);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateHistoryClob ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateHistoryClob Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	
	// methods for creating and filling out the lists table
	
	
	public static void createListsTable(SessionState ss)
	{
		try
		{
			String sql = "CREATE TABLE lists (listID INT(11) NULL AUTO_INCREMENT PRIMARY KEY, userID VARCHAR(30), language VARCHAR(30), name VARCHAR(30), kind VARCHAR(10), csvlist LONGTEXT)";
			
				SqlUtil.sqlExec(sql);
						//ss.dbgApp("got to dbHand.createListsTable 1: sql = "+sql); 
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.createListsTable ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.createListsTable Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	} // creates the table called lists in Hartbook DB
	
	// selects a list from table lists
	public static void selectAList(String name, String kind, SessionState ss)
	{	
		try
		{
			String sql = "SELECT csvlist FROM lists WHERE userID = '"+ss.userID+"' AND language = '"+ss.language+"' AND name = '"+name+"' AND kind = '"+kind+"' ";
			
			setQuery(sql, ss);
				//ss.dbgApp("got to dbHand.selectAList: sql = "+sql); 
			
			return;
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.selectAList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.selectAList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	// selects an eiken list from table lists
	public static void selectEikenList(String name, SessionState ss)
	{	
		try
		{
			String sql = "SELECT csvlist FROM lists WHERE userID = 'admin' AND language = 'japanese' AND name = '"+name+"' AND kind = 'hart' ";
			
			setQuery(sql, ss);
				//ss.dbgApp("got to dbHand.selectEikenList: sql = "+sql); 
			
			return;
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.selectEikenList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.selectEikenList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void updateDBList(String userID, String name, String csvList, SessionState ss)
	{	
		try
		{
			String sql = "UPDATE lists SET csvlist='"+csvList+"' WHERE userID='"+userID+"' AND name='"+name+"' AND language='"+ss.language+"'";
			
			SqlUtil.sqlExec(sql);
				//ss.dbgApp("got to dbHand.updateDBList: sql = "+sql); 
			
			return;
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateDBList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateDBList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void updateDBListName(String userID, String name, String listID, SessionState ss)
	{	
		int id = Integer.parseInt(listID);
		
		try
		{
			String sql = "UPDATE lists SET name='"+name+"' WHERE userID='"+userID+"' AND listID='"+id+"' AND language='"+ss.language+"'";
			
			SqlUtil.sqlExec(sql);
				//ss.dbgApp("got to dbHand.updateDBListName: sql = "+sql); 
			
			return;
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateDBListName ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateDBListName Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// creates new rows by inserting the given parameters into the lists table
	public static void insertList(String userID, String name, String kind, String csvList, SessionState ss)
	{	
		try
		{
			String sql = "INSERT INTO lists SET userID='"+userID+"', language='"+ss.language+"', name='"+name+"', kind = '"+kind+"', csvlist='"+csvList+"'";
			
			SqlUtil.sqlExec(sql);
				//ss.dbgApp("got to dbHand.insertList: sql = "+sql); 
			
			return;
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.insertList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.insertList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static String fetchAnEikenList(SessionState ss)
	{
		try
		{
			String csv = "";
			
			ss.listName = getAnEikenList(ss);
			
			String sql = "SELECT csvlist FROM lists WHERE userID = 'admin' AND language = 'japanese' AND name = '"+ss.listName+"' AND kind = 'hart' ";
			
			setQuery(sql, ss);
				//ss.dbgApp("got to dbHand.fetchAnEikenList 1: sql = "+sql); 
			
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchAnEikenList 2:(beforeFirst)"); 
				
			while(ss.rs.next())
			{
				Clob c = ss.rs.getClob("csvlist");
				
				if(c == null) return "";
				
				csv = clobString(c, ss);
				
				if(csv == null) 
				{
					//ss.dbgApp("got to dbHand.fetchAnEikenList 3: csv = null "+csv);
					return "";
				}
				else if(csv.length() == 0) 
				{
					//ss.dbgApp("got to dbHand.fetchAnEikenList 3: csv = 0 "+csv);
					return "";
				}
				else 
				{
					//ss.dbgApp("got to dbHand.fetchAnEikenList 3: csv = "+csv);
					return csv;
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchAnEikenList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchAnEikenList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return "";
	}
	
	
	private static String getAnEikenList(SessionState ss)
	{
		for(Lists list : EnumSet.range(Lists.EIKEN1, Lists.EIKEN4))
		{
			String name = list.getName();
			
			if(name.equals(ss.listGroup))
			{
				return list.getListName(ss.type);
			}
		}
		
		return "Invalid";
	}
	
	// now sets ss.map by creating HashMaps of < String, Integer > of csv, then setting them in ss.map under their names
	// it used to return ArrayList<String> ownList
	public static void fetchAllOwnLists(SessionState ss)
	{
		try
		{
			allOwnSQL(ss); // fetches all the user's own lists (knowlist, maybelist) from table lists
			
			if(ss.numOfRows == 0)
			{
				// Inserts all the user's own lists (knowlist, maybelist) into table lists 
				// (called if they don't exist already)
				insertAllOwnLists(ss);
				allOwnSQL(ss);
			}
			
			while(ss.rs.next())
			{
				String csv = "";
				
				HashMap< String, Integer > own = new HashMap< String, Integer >();
				
				String name = ss.rs.getString("name");
				Clob c = ss.rs.getClob("csvlist");
				
				if(c == null) 
				{
					//ss.dbgApp("got to dbHand.fetchAllOwnLists 3a: c = null "+csv); 
					continue;
				}
				
				csv = clobString(c, ss);
				
				if(csv == null)
				{
					//ss.dbgApp("got to dbHand.fetchAllOwnLists 3b: csv = null "+csv); 
				}
				else if(csv.length() == 0)
				{
					//ss.dbgApp("got to dbHand.fetchAllOwnLists 3c: csv length = 0 "+csv); 
					 //continue;
				}
				else 
				{
					String[] str = StringHandler.splitSecondString(csv);
					for(String s : str)
					{
						own.put(s, Integer.valueOf(s));
					}
					//ss.dbgApp("got to dbHand.fetchAllOwnLists 3d: csv = "+csv); 
					//ss.dbgApp("got to dbHand.fetchAllOwnLists 3e: ss.ownLists = "+ownList); 
				}
				
				ss.map.put(name, own);
			}
			//ss.dbgApp("got to dbHand.fetchAllOwnLists 4: ss.ownLists = "+ownList); 
			return;
		}
		catch(NumberFormatException ex) { ss.dbgApp("dbHand.fetchAllOwnLists NumberFormatException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchAllOwnLists Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	} // gets all of the user'S own lists (mylist, etc.) at a time for the chosen language.
	
	
	// fetches all the user's own lists (knowlist, maybelist) from table lists
	private static void allOwnSQL(SessionState ss)
	{
		try
		{
			String sql = "SELECT name, csvlist FROM lists WHERE userID = '"+ss.userID+"' AND language = '"+ss.language+"' AND kind = 'own' ";
			
			setQuery(sql, ss);
				//ss.dbgApp("got to dbHand.fetchAllOwnLists 1: sql = "+sql); 
				//ss.dbgApp("got to dbHand.fetchAllOwnLists 1A: ss.numOfRows = "+ss.numOfRows); 
			
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchAllOwnLists 2:(beforeFirst)"); 
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.allOwnSQL ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.allOwnSQL Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// Inserts all the user's own lists (knowlist, maybelist) into table lists (called if they don't exist already)
	private static void insertAllOwnLists(SessionState ss)
	{
		for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
		{
			for(Types type : Types.values())
			{
				ss.listName = list.getListName(type.getName());
				insertList(ss.userID, ss.listName, "own", "", ss);
			}
		}
	}
	
	
	public static String fetchOwnList(SessionState ss)
	{
		try
		{
			String csv = "";
			
			ss.listName = getOwnList(ss);
			
			selectAList(ss.listName, "own", ss); // selects a list from table lists
			
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchOwnList 2:(beforeFirst)"); 
				
			while(ss.rs.next())
			{
				Clob c = ss.rs.getClob("csvlist");
				
				if(c == null) return "";
				
				csv = clobString(c, ss);
				
				if(csv == null) return "";
				else if(csv.length() == 0) return "";
				else return csv;
			}
		}
		catch(Exception ex) { ss.dbgApp("dbHand.fetchOwnList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return "";
	} // gets one of the user'S own lists (mylist, etc.) at a time for the chosen language.
	
	
	private static String getOwnList(SessionState ss)
	{
		for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
		{
			String name = list.getName();
			
			if(name.equals(ss.listGroup))
			{
				return list.getListName(ss.type);
			}
		}
		
		return "Invalid";
	}
	
	
	public static String fetchOwnChapList(SessionState ss)
	{
		try
		{
			String csv = "";
			selectAList(ss.listName, "chap", ss); // selects a list from table lists
			
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchOwnChapList 2:(beforeFirst)"); 
				
			while(ss.rs.next())
			{
				Clob c = ss.rs.getClob("csvlist");
				
				if(c == null) return "";
				
				csv = clobString(c, ss);
				
				if(csv == null) return "";
				else if(csv.length() == 0) return "";
				else return csv;
			}
		}
		catch(Exception ex) { ss.dbgApp("dbHand.fetchOwnChapList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return "";
	} // used to get one Chapter list
	
	
	public static void fetchEiken(SessionState ss)
	{	
		try
		{
			String name = "", kind = "hart";
			String[] types = {"verb", "noun", "adjective", "misc"};	
			
			for(Lists list : EnumSet.range(Lists.EIKEN1, Lists.EIKEN4))
			{
				for(String type : types)
				{
					String csvList = "";
					
					String sql = "SELECT ID FROM japanese WHERE eiken = '"+list.getName()+"' AND type = '"+type+"' ";
					
					ss.rs = SqlUtil.sqlQuery(sql);
						//ss.dbgApp("got to dbHand.fetchEiken 1: sql = "+sql); 
						
					ss.rs.beforeFirst();
						//ss.dbgApp("got to dbHand.fetchEiken 2:(beforeFirst)"); 
						
					while(ss.rs.next())
					{
						csvList = csvList+String.valueOf(ss.rs.getInt("ID"))+",";
					}
					
					name = list.getListName(type);
					
					if(name.length() != 0)
					{
						insertList(ss.userID, name, kind, csvList, ss);
							//ss.dbgApp("got to dbHand.fetchEiken 3: csvList = "+csvList); 
					}
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchEiken ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchEiken Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	} // fetches all the eiken rows from japanese, then puts them all together in a csv that is inserted into lists under the names
	
	
	// fetches the userIDs from userlist, then creates a knowlist and maybelist for each one
	public static void fetchUserIDs(SessionState ss)
	{	
		try
		{
			String kind = "own", csvList = "";
			
			String[] types = {"verb", "noun", "adjective", "misc"};	
			
			String sql = "SELECT userID FROM userlist";
			
			ss.rs = SqlUtil.sqlQuery(sql);
				//ss.dbgApp("got to dbHand.fetchUserIDs 1: sql = "+sql); 
				
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchUserIDs 2:(beforeFirst)"); 
				
			while(ss.rs.next())
			{
				String user = ss.rs.getString("userID");
				
				for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
				{
					for(String type : types)
					{
						String name = list.getListName(type);
						
						insertList(user, name, kind, csvList, ss);
							//ss.dbgApp("got to dbHand.fetchUserIDs 3: csvList = "+name); 
					}
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchUserIDs ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchUserIDs Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// fetches the csv from knowlist and maybelist from userlist for each userID
	// then sends it to getCSVString to update the appropriate rows
	public static void fetchUserCSVLists(SessionState ss)
	{	
		try
		{
			String csvList = "";
			String[] cols = {"my", "maybe", "know"};
			String[] names = {"myList", "maybeList", "knowList"};
			
			for(int i = 0; i < cols.length; i++)
			{
				int x = 0; 
				
				String sql = "SELECT userID, "+cols[i]+" FROM userlist";
				
				setQuery(sql, ss);
					//ss.dbgApp("got to dbHand.fetchUserCSVLists 1: sql = "+sql); 
				
				String rsArray[][];
				rsArray = new String[ss.numOfRows][2];
				
				ss.rs.beforeFirst();
					//ss.dbgApp("got to dbHand.fetchUserCSVLists 2:(beforeFirst)"); 
					
				while(ss.rs.next())
				{
					String user = ss.rs.getString("userID");
					Clob c = ss.rs.getClob(cols[i]);
						//ss.dbgApp("got to dbHand.fetchUserCSVLists 3: user = "+user); 
					
					if(c == null) continue;
					
					String csv = clobString(c, ss);
					
					if(csv == null) continue;
					else if(csv.length() == 0) continue;
					
					rsArray[x][0] = user;
					rsArray[x][1] = csv;
					
					x++;
				}
					
				for(String[] rs : rsArray)
				{
					getCSVString(rs[0], names[i], rs[1], ss);
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchUserCSVLists ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchUserCSVLists Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// PROBABLY DON'T NEED AS WON'T USE TEACHLISTS
	// fetches teachers typelist from teachers and for each teacherID,
	// then uses getCSVString to update the appropriate rows in lists
	public static void fetchTeach(SessionState ss)
	{
		try
		{
			int x = 0; 
				
			String name = "teachList", csv = "";
			
			String sql = "SELECT teacherID, typelist FROM teachers";
			
			setQuery(sql, ss);
				//ss.dbgApp("got to dbHand.fetchTeach 1: sql = "+sql); 
			
			String rsArray[][];
			rsArray = new String[ss.numOfRows][2];
			
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchTeach 2:(beforeFirst)"); 
				
			while(ss.rs.next())
			{
				String teacher = ss.rs.getString("teacherID");
				Clob c = ss.rs.getClob("typelist");
				
				if(c == null) continue;
				
				csv = clobString(c, ss);
				
				if(csv == null) continue;
				else if(csv.length() == 0) continue;
				
				rsArray[x][0] = teacher;
				rsArray[x][1] = csv;
				
				x++;
			}
			
			for(String[] rs : rsArray)
			{
				getCSVString(rs[0], name, rs[1], ss);
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchTeach ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchTeach Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// fetches teachers chaplist from teachers and for each teacherID,
	// then uses insertList to create the appropriate rows in lists
	public static void fetchChap(SessionState ss)
	{
		try
		{
			String csv = "", name = "", kind = "chap", csvList = "";
			
			String sql = "SELECT teacherID, chaplist FROM teachers";
			
			ss.rs = SqlUtil.sqlQuery(sql);
				//ss.dbgApp("got to dbHand.fetchChap 1: sql = "+sql); 
				
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchChap 2:(beforeFirst)"); 
				
			while(ss.rs.next())
			{
				String teacher = ss.rs.getString("teacherID");
				Clob c = ss.rs.getClob("chaplist");
				
				if(c == null) continue;
				
				csv = clobString(c, ss);
				
				setCSVAList(csv, ss);
				
				Set< String > mapKeys = ss.chapNameList.keySet();
				
				for(String s : mapKeys)
				{
					name = s; 
					csvList = getChapCSVString(ss.chapterList.get((Integer)ss.chapNameList.get(s)), ss);
					
					insertList(teacher, name, kind, csvList, ss);
						//ss.dbgApp("got to dbHand.fetchChap 3: csvList = "+csvList);
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchChap ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchChap Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// assigns all the user's chapter names from lists table to hashmap ss.chapterNameList
	public static void fetchChapNames(SessionState ss)
	{
		try
		{
			String name = "";  // csv = "", , csvList = ""
			ss.chapNameList = new HashMap();
			
			String sql = "SELECT listID, name FROM lists WHERE userID = '"+ss.userID+"' AND language = '"+ss.language+"' AND kind = 'chap' ";
			
			ss.rs = SqlUtil.sqlQuery(sql);
				//ss.dbgApp("got to dbHand.fetchChapNames 1: sql = "+sql); 
				
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchChapNames 2:(beforeFirst)"); 
				
			while(ss.rs.next())
			{
				Integer listID = ss.rs.getInt("listID");
				name = ss.rs.getString("name");
				
				ss.chapNameList.put(name, listID); 
			}
				//ss.dbgApp("got to dbHand.fetchChapNames 3:ss.chapNameList = "+ss.chapNameList); 
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchChapNames ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchChapNames Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// assigns all the user's names from lists table to arraylist ss.everyList
	public static void fetchEveryList(SessionState ss)
	{
		try
		{
			String csv = "", name = ""; 
			
			ss.everyList = new ArrayList<String>();
			
			String sql = "SELECT name, csvlist FROM lists WHERE userID = '"+ss.userID+"' AND language = '"+ss.language+"' ";
			
			ss.rs = SqlUtil.sqlQuery(sql);
				//ss.dbgApp("got to dbHand.fetchEveryList 1: sql = "+sql); 
				
			ss.rs.beforeFirst();
				//ss.dbgApp("got to dbHand.fetchEveryList 2:(beforeFirst)"); 
				
			while(ss.rs.next())
			{
				name = ss.rs.getString("name");
				Clob c = ss.rs.getClob("csvlist");
				
				if(c == null) continue;
				
				csv = clobString(c, ss);
				
				csv = name+","+csv;
				
				ss.everyList.add(csv); 
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchEveryList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchEveryList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// makes sure the list names are not the standard set 
	// Probably won't use
	private static boolean checkChap(String name, SessionState ss)
	{
		for(Types type : Types.values())
		{
			for(Lists list : Lists.values())
			{
				if(name.equals(list.getListName(type.getName())))
					return false;
				else
					continue;
			}
		}
		
		return true;
	}
	
	
	private static void setCSVAList(String csv, SessionState ss)
	{
		ss.chapterList = new ArrayList<String>();
		ss.chapNameList = new HashMap();
			//ss.dbgApp("got to setCSVAList 1: csv = "+csv); //debug
		
		if(csv == null) return;
		else
			ss.chapterList = StringHandler.splitFirstString(csv);
		
		for(int i = 0; i < ss.chapterList.size(); i++)
		{
			String csvType = ss.chapterList.get(i);
			String[] split = StringHandler.splitSecondString(csvType);
			
			ss.chapNameList.put(split[0], i);
		}
	}
	
	
	private static String getChapCSVString(String csv, SessionState ss)
	{
		String allCSV = "";
		
		String[] split = StringHandler.splitSecondString(csv);
		
		if(split.length > 2)
		{
			for(int i = 1; i < split.length; i++)
			{
				allCSV = allCSV+split[i]+",";
			}
		}
		
		return allCSV;
	}
	
	
	// creates a csv from japanese based on userIDs userlist for knowlist and maybelist,
	// then updates the appropriate column and row for lists table
	private static void getCSVString(String user, String name, String csv, SessionState ss) throws SQLException, ServletException
	{
		String typName = "", listName = "";
		
		ArrayList<String> csvAList = new ArrayList<String>();
			//ss.dbgApp("got to getCSVString 1: csv = "+csv); //debug
		
		for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
		{
			listName = list.getName();
			if(name.equals(listName))
			{
				if(csv == null) 
					return;
				else
					csvAList = StringHandler.splitFirstString(csv);
				
				for(String csvType : csvAList)
				{
					String allCSV = "";
					
					String[] split = StringHandler.splitSecondString(csvType);
					
					if(split.length > 2)
					{
						typName = list.getListName(split[0]);
							//ss.dbgApp("got to dbHand.getCSVString 2: typName = "+typName); 
						
						for(int i = 1; i < split.length; i++)
						{
							
							String sql = "SELECT type FROM japanese WHERE ID = "+Integer.parseInt(split[i]);
							
							ss.rs = SqlUtil.sqlQuery(sql);
								//ss.dbgApp("got to dbHand.getCSVString 1: sql = "+sql); 
								
							ss.rs.beforeFirst();
								//ss.dbgApp("got to dbHand.getCSVString 2:(beforeFirst)"); 
								
							while(ss.rs.next())
							{
								String typ = ss.rs.getString("type");
								
								if(typ.equals(split[0]))
									allCSV = allCSV+split[i]+",";
							}
						}
						
						updateDBList(user, typName, allCSV, ss);
							//ss.dbgApp("got to dbHand.getCSVString 3: csvList = "+allCSV);
					}
				}
			}
		}
		return;
	}
	
	
	// for EditRows.java
	
	// FINISH
   	public static void handleInsert(SessionState ss)
	{
		try
		{
			String sql = "";
			
			if(ss.threeCols)
			{
				if((ss.eng.length() == 0) && (ss.kan.length() == 0) && (ss.yom.length() == 0))
				{
					ss.message = Messages.getMess5(ss);
					return;
				}
			}
			else
			{
				if((ss.eng.length() == 0) && (ss.kan.length() == 0))
				{
					ss.message = Messages.getMess5(ss);
					return;
				}
			}
			
			ss.search = false;
			
			if(ss.role.equals("teacher"))
			{
				ss.whereCol = "jtest";
				ss.whereVal = "teacher";
			}
			else if(ss.role.equals("admin"))
			{
				if(ss.prevSelect.equals("business1"))
				{
					ss.whereCol = "business";
					ss.whereVal = ss.prevSelect;
				}
				else if(ss.prevSelect.equals("jTest1"))
				{
					ss.whereCol = "jtest";
					ss.whereVal = ss.prevSelect;
				}
				else
				{
					ss.whereCol = "eiken";
					ss.whereVal = ss.prevSelect;
				}
			}
			
			
			// all sql statements used to include the three columns: eiken, jtest, business
			
			// checks to see if words already in ss.table
			if(ss.threeCols)
			{
				sql = "SELECT ID, edit, type, english, meaning, reading FROM "+ss.table+
					" WHERE english = '"+ss.eng+"' AND meaning = '"+ss.kan+"' AND reading = '"+ss.yom+"'";
			}
			else 
			{
				sql = "SELECT ID, edit, type, english, meaning FROM "+ss.table+
					" WHERE english = '"+ss.eng+"' AND meaning = '"+ss.kan+"'";
			}
				//ss.dbgApp("got to handleInsert 1, ss.edit: "+ss.edit+", sql: "+sql); //debug
			
			setQuery(sql, ss);
				
			ss.rs.beforeFirst();
			
			if(ss.rs.next())
			{
				int y = ss.rs.getInt("ID");
				String x = String.valueOf(y);
					//ss.dbgApp("got to handleInsert 4, ss.type: "+ss.type+", teachList: "+ss.chapterList); //debug
				
				if(ss.role.equals("admin"))
					ss.message = Messages.getMess4(ss);
				else if(ss.role.equals("teacher"))
					ss.message = Messages.getMess28(ss);
					
				
				ss.selection = y;
				ss.search = true;
			}
			else
			{
				if(ss.role.equals("admin"))
				{
					if(ss.table.equals("japanese"))
					{
						if(ss.threeCols)
						{
							sql = "INSERT INTO "+ss.table+" (edit, type, english, meaning, reading, eiken, engexamp, langexamp) VALUES (0, '"+
								ss.typ+"', '"+ss.eng+"', '"+ss.kan+"', '"+ss.yom+"', '"+ss.listGroup+"', '"+ss.engExOrig+"', '"+ss.langExOrig+"')";
						}
						else 
						{
							sql = "INSERT INTO "+ss.table+" (edit, type, english, meaning, eiken, engexamp, langexamp) VALUES (0, '"+
								ss.typ+"', '"+ss.eng+"', '"+ss.kan+"', '"+ss.listGroup+"', '"+ss.engExOrig+"', '"+ss.langExOrig+"')";
						}
					}
					else
					{
						if(ss.threeCols)
						{
							sql = "INSERT INTO "+ss.table+" (edit, type, english, meaning, reading, engexamp, langexamp) VALUES (0, '"+
								ss.typ+"', '"+ss.eng+"', '"+ss.kan+"', '"+ss.yom+"', '"+ss.engExOrig+"', '"+ss.langExOrig+"')";
						}
						else 
						{
							sql = "INSERT INTO "+ss.table+" (edit, type, english, meaning, engexamp, langexamp) VALUES (0, '"+
								ss.typ+"', '"+ss.eng+"', '"+ss.kan+"', '"+ss.engExOrig+"', '"+ss.langExOrig+"')";
						}
					}
						
					SqlUtil.sqlExec(sql);
					
			// all sql statements used to include the three columns: eiken, jtest, business
					if(ss.threeCols)
					{
						sql = "SELECT ID, type, english, meaning, reading FROM "+ss.table+
							" WHERE english = '"+ss.eng+"' AND meaning = '"+ss.kan+"' AND reading = '"+ss.yom+"'";
					}
					else 
					{
						sql = "SELECT ID, type, english, meaning FROM "+ss.table+
							" WHERE english = '"+ss.eng+"' AND meaning = '"+ss.kan+"'";
					}
					
					setQuery(sql, ss);
					//ss.dbgApp("got to handleInsert 6, sql: "+sql); //debug
					
					ss.rs.beforeFirst();
					
				// added to chapter lists in EditRows, but need to set ss.selection, and resetting type is probably safer
					if(ss.rs.next())
					{
						ss.selection = ss.rs.getInt("ID");
						ss.type = ss.rs.getString("type");
						
						ss.setLists();
						// ADD TO SS.MAINLIST done in EditRows
						
						ss.setTypes();
							//ss.dbgApp("got to handleInsert 8, new chapterList: "+ss.chapterList); //debug
					}
				}
				else if(ss.role.equals("teacher"))
				{
					if(ss.threeCols)
					{
						sql = "INSERT INTO "+ss.table+" (edit, type, english, meaning, reading, "+ss.whereCol+
							", engexamp, langexamp) VALUES (1, '"+ss.typ+"', '"+ss.eng+"', '"+ss.kan+"', '"+ss.yom+"', '"+ss.whereVal+"', '"+ss.engExOrig+"', '"+ss.langExOrig+"')";
					}
					else 
					{
						sql = "INSERT INTO "+ss.table+" (edit, type, english, meaning, engexamp, langexamp) VALUES (1, '"+ss.typ+"', '"+ss.eng+"', '"+ss.kan+"', '"+ss.engExOrig+"', '"+ss.langExOrig+"')";
					}
					
					SqlUtil.sqlExec(sql);
					//ss.dbgApp("got to handleInsert 5, sql: "+sql); //debug
					
			// all sql statements used to include the three columns: eiken, jtest, business
					if(ss.threeCols)
					{
						sql = "SELECT ID, type, english, meaning, reading FROM "+ss.table+
							" WHERE english = '"+ss.eng+"' AND meaning = '"+ss.kan+"' AND reading = '"+ss.yom+"'";
					}
					else 
					{
						sql = "SELECT ID, type, english, meaning FROM "+ss.table+
							" WHERE english = '"+ss.eng+"' AND meaning = '"+ss.kan+"'";
					}
					
					setQuery(sql, ss);
					//ss.dbgApp("got to handleInsert 6, sql: "+sql); //debug
					
					ss.rs.beforeFirst();
					
				// added to chapter lists in EditRows, but need to set ss.selection, and resetting type is probably safer
					if(ss.rs.next())
					{
						ss.selection = ss.rs.getInt("ID");
						ss.type = ss.rs.getString("type");
						
						ss.setLists();
						
						// ADD TO SS.MAINLIST done in EditRows
						ss.setTypes();
							//ss.dbgApp("got to handleInsert 8, new chapterList: "+ss.chapterList); //debug
					}
				}
				
				ss.message = Messages.getMess3(ss);
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.handleInsert ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.handleInsert SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.handleInsert Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void handleUpdate(SessionState ss) throws ServletException
	{
		String sql = "";
		
		if((ss.selection == 0)) return;
			
		if(ss.role.equals("admin"))
		{
			if(ss.table.equals("japanese"))
			{
				if(ss.threeCols)
				{
					sql = "UPDATE "+ss.table+" SET edit = 0, type = '"+ss.typ+"', english = '"+ss.eng+"', meaning = '"+ss.kan+
						"', reading = '"+ss.yom+"', "+ss.whereCol+" = '"+ss.whereVal+"' WHERE ID = "+ss.selection;
				}
				else 
				{
					sql = "UPDATE "+ss.table+" SET edit = 0, type = '"+ss.typ+"', english = '"+ss.eng+"', meaning = '"+ss.kan+
						"', "+ss.whereCol+" = '"+ss.whereVal+"' WHERE ID = "+ss.selection;
				}
			}
			else
			{
				if(ss.threeCols)
				{
					sql = "UPDATE "+ss.table+" SET edit = 0, type = '"+ss.typ+"', english = '"+ss.eng+"', meaning = '"+ss.kan+
						"', reading = '"+ss.yom+"' WHERE ID = "+ss.selection;
				}
				else 
				{
					sql = "UPDATE "+ss.table+" SET edit = 0, type = '"+ss.typ+"', english = '"+ss.eng+"', meaning = '"+ss.kan+
						"' WHERE ID = "+ss.selection;
				}
			}
			
			SqlUtil.sqlExec(sql);
				
			ss.message = Messages.getMess10(ss);
			
			ss.setLists(); // MAY BE UNNECESSARY
		}
		if(ss.role.equals("teacher"))
		{
			String x = String.valueOf(ss.selection);
				
			ss.type = ss.typ;
				//ss.dbgApp("got to handleUpdate 1, ss.type: "+ss.type); //debug
			
			if(ss.edit == 1)
			{
				if(ss.threeCols)
				{
					sql = "UPDATE "+ss.table+" SET type = '"+ss.typ+"', english = '"+ss.eng+"', meaning = '"+ss.kan+
						"', reading = '"+ss.yom+"' WHERE ID = "+ss.selection;
				}
				else 
				{
					sql = "UPDATE "+ss.table+" SET type = '"+ss.typ+"', english = '"+ss.eng+"', meaning = '"+ss.kan+
						"' WHERE ID = "+ss.selection;
				}
				
				SqlUtil.sqlExec(sql);
					//ss.dbgApp("got to handleUpdate 2, ss.edit: "+ss.edit+", sql: "+sql); //debug
				
				ss.message = Messages.getMess10(ss);
			}
			else { ss.message = Messages.getMess30(ss); }
			
			// ADD TO SS.MAINLIST AND SAVE
			ss.setLists();
				//ss.dbgApp("got to handleUpdate 3, ss.teachList: "+ss.teachList); //debug
				
		}
	}
	
	
	public static void checkSelect(SessionState ss)
	{
		try
		{
			String sql = "";
			
			// all sql statements used to include the three columns: eiken, jtest, business
			if(ss.selected)
			{
				if(ss.threeCols)
				{
					sql = "SELECT ID, edit, type, english, meaning, reading FROM "+ss.table+" WHERE ID = "+ss.selection;
				}
				else 
				{
					sql = "SELECT ID, edit, type, english, meaning FROM "+ss.table+" WHERE ID = "+ss.selection;
				}
				
				setQuery(sql, ss);
				
				getWordList(ss);
				//ss.dbgApp("got to checkSelect 1: ss.eng = "+ss.eng+" ss.kan = "+ss.kan+" ss.yom = "+ss.yom); 
				
				for(WordBean wBean : ss.wordList)
				{
					ss.typ = wBean.getTyp();
					ss.eng = wBean.getEng();
					ss.kan = wBean.getKan();
					ss.yom = wBean.getYom();
					ss.edit = wBean.edit;
					ss.prevType = ss.typ;
				}
				//ss.dbgApp("got to checkSelect 2: ss.eng = "+ss.eng+" ss.kan = "+ss.kan+" ss.yom = "+ss.yom); 
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.checkSelect ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.checkSelect SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.checkSelect Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void fetchWordList(SessionState ss)
	{
		try
		{
			String sql = "";
			
			// all sql statements used to include the three columns: eiken, jtest, business
			if(ss.role.equals("admin"))
			{
				if(ss.search)
				{
					getWordList(ss);
						//ss.dbgApp("got to getWordList (got wordList)"); 
				}
				else if(ss.whereVal.equals(ss.table)) 
				{
					if(ss.threeCols)
					{
						sql = "SELECT ID, edit, type, english, meaning, reading FROM "+ss.table+
							" WHERE type = '"+ss.typ+"' ORDER BY ID DESC";
					}
					else 
					{
						sql = "SELECT ID, edit, type, english, meaning FROM "+ss.table+" WHERE type = '"+ss.typ+"' ORDER BY ID DESC";
					}
					
					setQuery(sql, ss);
						//ss.dbgApp("got to fetchWordList (Vocab 1)"); 
						
					getWordList(ss);
						//ss.dbgApp("got to getWordList (got wordList)"); 
				}
				else 
				{
					BookMethods.setMainList(ss); // sets ss.mainList by finding the right list in the DB or ss.map
					
					ss.list = ss.mainList;
					
					fetchListRows(ss); // fetches all of the rows in ss.table that are in ss.list, assigns the values to a WordBean, which is added to ss.wordList
				}
			}
			else if(ss.role.equals("teacher"))
			{
				if(ss.search)
				{
					getWordList(ss);
						//ss.dbgApp("got to fetchWordList (got wordList)"); 
				}
				else { fetchListRows(ss); }
			}
			else
			{
				ss.message = Messages.getMess29(ss);
				return;
			}
				// ss.dbgApp("got to fetchWordList 1: ss.chapNameList = "+ss.chapNameList+", ss.chapterList = "+ss.chapterList); //debug
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchWordList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchWordList SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchWordList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	// probably not usingthis 
	public static void updateHartList(SessionState ss)
	{
		try
		{
			String sql = "UPDATE hartlists SET "+ss.typ+" = '"+ss.hList+"' WHERE name = '"+ss.prevSelect+"' ";
				//ss.dbgApp("got to "+sql);
			SqlUtil.sqlExec(sql);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateHartList ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateHartList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// FOR examp_sent_popup.jsp
	
	// used to update example sentences columns
	public static void updateExampSent(SessionState ss)
	{	
		try
		{
			ss.dbgApp("got here updateExampSent try"); //debug
			String sql = "UPDATE "+ss.table+" SET engexamp='"+ss.engEx+"', langexamp='"+ss.langEx+"'  WHERE ID='"+ss.rowID+"'";
			
			SqlUtil.sqlExec(sql);
				ss.dbgApp("sql: "+sql);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateChapClob ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateChapClob Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// used to update example sentences columns
	public static void fetchExampSent(SessionState ss)
	{
		try
		{
			String sql = "";
			ss.dbgApp("got here fetchExampSent try"); //debug
			
			sql = "SELECT engexamp, langexamp FROM "+ss.table+" WHERE ID='"+ss.rowID+"'";
			
			ss.rs = SqlUtil.sqlQuery(sql);
				ss.dbgApp("sql: "+sql);
				
			ss.rs.beforeFirst();
					
			while(ss.rs.next())
			{
				ss.dbgApp("got here fetchExampSent"); //debug
				Clob eSent = ss.rs.getClob("engexamp");
				Clob lSent = ss.rs.getClob("langexamp");
				
				if (eSent.length() == 0 || eSent == null)	
					ss.dbgApp("eSent clob is empty"); //debug
				else 
					ss.engEx = clobString(eSent, ss);
				
				if (lSent.length() == 0 || lSent == null)	
					ss.dbgApp("lSent clob is empty"); //debug
				else 
					ss.langEx = clobString(lSent, ss);
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchExampSent ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchExampSent SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchExampSent Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// for EditTables.java
	
	
	public static void handleTable(SessionState ss) // throws ServletException, SQLException
	{
        try
        {
            String sql = "SELECT * FROM " + ss.dbTable;

            setQuery(sql, ss);
                //ss.dbgApp("got to handleTable");

            getTableRows(ss);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchExampSent ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchExampSent SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchExampSent Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void handleRowSelection(SessionState ss) // throws ServletException, SQLException
	{
        try
        {
            String sql = "SELECT * FROM " + ss.dbTable + " WHERE " + getColumnName(0, ss) + " = '" + ss.radioID + "' ";

            setQuery(sql, ss);

            ss.rs.beforeFirst();
                //ss.dbgApp("got to handleRowSelection");

            while(ss.rs.next())
            {
                for( int i = 0; i < ss.numOfCols; i++ )
                {
                    String c = getColumnTypeName(i, ss);

                    if(c.equalsIgnoreCase("varchar"))
                        ss.cols[i] = ss.rs.getString(i + 1);
                }
            }

            handleTable(ss);
        }
		catch(SQLException ex) {ss.dbgApp("SQLException: " + ex.getMessage());}
		catch(ServletException ex) {ss.dbgApp("ServletException: " + ex.getMessage());}
		catch(Exception ex) {ss.dbgApp("Exception: " + ex.getMessage());}
	}
	
	
	public static void handleUpdateTable(SessionState ss) // throws ServletException, SQLException
	{
        try
        {
            String sql = "UPDATE "+ss.dbTable+" SET " + setSQL(ss) + " WHERE " + getColumnName(0, ss) + " = '" + ss.radioID + "' ";
                //ss.dbgApp("got to "+sql);

            SqlUtil.sqlExec(sql);
                //ss.dbgApp("got to handleUpdateTable");

            handleTable(ss);
        }
		catch(SQLException ex) {ss.dbgApp("SQLException: " + ex.getMessage());}
		catch(ServletException ex) {ss.dbgApp("ServletException: " + ex.getMessage());}
		catch(Exception ex) {ss.dbgApp("Exception: " + ex.getMessage());}
	}
	
	
	public static void handleAddRow(SessionState ss) // throws ServletException, SQLException
	{
        try
        {
            String sql = "INSERT INTO "+ss.dbTable+" SET " + setSQL(ss);
                //ss.dbgApp("got to "+sql);

            SqlUtil.sqlExec(sql);
                //ss.dbgApp("got to handleAddRow");

            handleTable(ss);
        }
		catch(SQLException ex) {ss.dbgApp("SQLException: " + ex.getMessage());}
		catch(ServletException ex) {ss.dbgApp("ServletException: " + ex.getMessage());}
		catch(Exception ex) {ss.dbgApp("Exception: " + ex.getMessage());}
	}
	
	
	public static String getHeads(SessionState ss)
	{
		String o = "";
		//ss.dbgApp("number of columns = "+ss.numOfCols); 
		
		for( int i = 0; i < ss.numOfCols; i++ )
		{
			String c = getColumnTypeName(i, ss);
			//ss.dbgApp("col type "+i+"="+c); 
			
			if(c.equalsIgnoreCase("int") || c.equalsIgnoreCase("varchar")) 
			{
				if(i == 5) { o = o + "</tr><tr>"; }
			
				o = o + "<th>"+getColumnName(i, ss)+"</th>";
			}
		}
		
		return o;
	}
	
	
	public static String getInputs(SessionState ss)
	{
        String o = "";
            //ss.dbgApp("got inputs");

        for( int i = 0; i < ss.numOfCols; i++ )
        {
            String c = getColumnTypeName(i, ss);

            if(c.equalsIgnoreCase("varchar"))
            {
                if(i == 5) { o = o + "</tr><tr>"; }

                o = o + "<td class='center'><input type = 'text' style='width:100px' name='"+ss.cols[i]+"' value='"+ss.cols[i]+"' /></td>";
            }
            //ss.dbgApp("got inputs: value of name = "+ss.cols[i]);
        }

        return o;
	}
	
	
	public static String getDispCols(TableRow tr, SessionState ss)
	{
		String o = "";
		
		for( int i = 0; i < ss.numOfCols; i++ )
		{
			String c = getColumnTypeName(i, ss);
			
			if(c.equalsIgnoreCase("int") || c.equalsIgnoreCase("varchar")) 
			{
				if(i == 5) { o = o + "</tr><tr>"; }
			
				o = o + "<td style='font-size:12pt;color:darkblue'>"+tr.cols[i]+"</td>";
			}
		}
		
		return o;
	}
	
	
	public static void getTables(SessionState ss) throws ServletException, SQLException
	{
		ss.tables = new ArrayList<String>();
		
		String sql = "SHOW TABLES";
		ResultSet rs = SqlUtil.sqlQuery(sql);
		
		while(rs.next()) { ss.tables.add(rs.getString(1)); }
	}
	
	
	private static void getTableRows(SessionState ss) throws SQLException
	{
		ss.tableRow = new ArrayList<TableRow>();
			//ss.dbgApp("got to getTableRows 1"); 
			
		ss.rs.beforeFirst();
		
		while(ss.rs.next())
		{
			TableRow tr = new TableRow();
			
			for( int i = 0; i < ss.numOfCols; i++ )
			{
				String c = getColumnTypeName(i, ss);
				
				if(c.equalsIgnoreCase("int")) 
				{
					tr.cols[i] = String.valueOf(ss.rs.getInt(i + 1));
				}
				else if(c.equalsIgnoreCase("varchar")) 
				{ 
					tr.cols[i] = ss.rs.getString(i + 1); 
				}
			}
			
			ss.tableRow.add(tr);
		}
	}
	
	
	private static void getTableRowsWB(SessionState ss) throws SQLException
	{
		ss.tableRow = new ArrayList<TableRow>();
			//ss.dbgApp("got to getTableRows 1"); 
		
		ss.rs.beforeFirst();
		
		while(ss.rs.next())
		{
			TableRow tr = new TableRow();
			
			for( int i = 0; i < ss.numOfCols; i++ )
			{
				String c = getColumnTypeName(i, ss);
				
				if(c.equalsIgnoreCase("int")) 
				{
					tr.cols[i] = String.valueOf(ss.rs.getInt(i + 1));
				}
				else if(c.equalsIgnoreCase("varchar")) 
				{ 
					tr.cols[i] = ss.rs.getString(i + 1); 
				}
			}
			
			ss.tableRow.add(tr);
		}
	}
	
	
	private static String setSQL(SessionState ss) throws SQLException
	{
		String values = "";
			//ss.dbgApp("got to setSQL"); 
			
		for( int i = 0; i < ss.numOfCols; i++ )
		{
			String c = getColumnTypeName(i, ss);
			
			if(c.equalsIgnoreCase("int")) 
			{
				if(i == 0) { values = getColumnName(i, ss) + " = " + ss.cols[i]; }
				else { values = values + ", " + getColumnName(i, ss) + " = " + ss.cols[i]; }
			}
			if(c.equalsIgnoreCase("varchar")) 
			{
				if(i == 0) { values = getColumnName(i, ss) + " = '" + ss.cols[i] + "'"; }
				else { values = values + ", " + getColumnName(i, ss) + " = '" + ss.cols[i] + "'"; }
			}
		}
		
		return values;
	}
	
	
	// for notes.jsp
	
	
	public static void saveNote(String note, SessionState ss) throws ServletException
	{
		String sql = "";
		
		sql = "INSERT notepad SET noteText = '"+note+"'";
				
		SqlUtil.sqlExec(sql);
				
		ss.message = Messages.getMess41(ss);
	}
	
	
	public static void fetchMisplaced(ArrayList<String> splitList, SessionState ss)
	{
		try
		{
			ss.wordList = new ArrayList<WordBean>();
			
			String sql = "";
			
			if(ss.threeCols)
			{
				sql = "SELECT type, english, meaning, reading FROM "+ss.table+" WHERE ID > "+Integer.parseInt(splitList.get(1));
			}
			else 
			{
				sql = "SELECT type, english, meaning FROM "+ss.table+" WHERE ID > "+Integer.parseInt(splitList.get(1));
			}
			
			ss.rs = SqlUtil.sqlQuery(sql);
			
			ss.rs.beforeFirst();
			
			while(ss.rs.next())
			{
				WordBean wBean = new WordBean();
				
				wBean.typ = ss.rs.getString("type");
				wBean.eng = ss.rs.getString("english");
				wBean.kan = ss.rs.getString("meaning");
				
				if(ss.threeCols)
					wBean.yom = ss.rs.getString("reading");
				
				ss.wordList.add(wBean);
			}
				//ss.dbgApp("got to dbHand.getWordList (wBean(s) added to ss.wordList)"); 
				
			BookMethods.createCSV(ss);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchMisplaced ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchMisplaced SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchMisplaced Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	// FINISH
	public static void insertCSV(SessionState ss)
	{
		try
		{
			String sql = "";
			
			for(WordBean wb : ss.wordList)
			{
				if(ss.threeCols)
				{
					sql = "SELECT ID, english, meaning, reading FROM "+ss.table+
						" WHERE english = '"+wb.eng+"' AND meaning = '"+wb.kan+"' AND reading = '"+wb.yom+"' ";
				}
				else 
				{
					sql = "SELECT ID, english, meaning FROM "+ss.table+
						" WHERE english = '"+wb.eng+"' AND meaning = '"+wb.kan+"' ";
				}
				
				ss.rs = SqlUtil.sqlQuery(sql);
					//ss.dbgApp("got here insertCSV 1: sql = "+sql); //debug
					
				if(ss.rs.next())
				{
						//ss.dbgApp("got here insertCSV 2 (got RS): ID = "+ss.rs.getInt("ID")+"', english = "+ss.rs.getString("english")+"', meaning = "+ss.rs.getString("meaning")+"', reading = "+ss.rs.getString("reading")); //debug
					ss.message = "Those meanings are already entered";
				}
				else
				{
					if(ss.threeCols)
					{
						sql = "INSERT "+ss.table+" SET edit = 0, type = '"+wb.typ+"', english = '"+wb.eng+"', meaning = '"+
							wb.kan+"', reading = '"+wb.yom+"', eiken = 'eikenPre2', engexamp = '"+ss.engExOrig+"', langexamp = '"+ss.langExOrig+"'";
					}
					else 
					{
						sql = "INSERT "+ss.table+" SET edit = 0, type = '"+wb.typ+"', english = '"+wb.eng+"', meaning = '"+
							wb.kan+"', engexamp = '"+ss.engExOrig+"', langexamp = '"+ss.langExOrig+"'";
					}
					
					SqlUtil.sqlExec(sql);
						//ss.dbgApp("got here insertCSV 3: sql = "+sql); //debug
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.insertCSV ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.insertCSV SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.insertCSV Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void updateNote(ArrayList<String> splitList, SessionState ss)
	{
		try
		{
			String sql = "";
			
			sql = "UPDATE notepad SET noteText='"+splitList.get(2)+"' WHERE noteID="+Integer.parseInt(splitList.get(1));
			
			SqlUtil.sqlExec(sql);
				//ss.dbgApp("got here updateNote 1: sql = "+sql); //debug
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateNote ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateNote Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	
	// for allHistories.jsp
	
	// sets elements of ArrayList ss.students with Selected userIDs from table userlist; used in allHistories;
	public static void fetchAllIDs(SessionState ss)
	{
		try
		{
			ss.students = new ArrayList<String>();
			
			String sql = "SELECT userID FROM userlist";
			ss.rs = SqlUtil.sqlQuery(sql);
			
			while(ss.rs.next())
			{
				String studID = ss.rs.getString("userID");
				ss.students.add(studID);
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchAllIDs ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchAllIDs SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchAllIDs Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	// sets elements of ArrayList ss.students with Selected userIDs from table userlist for the user's students;
    // used in allStudHistories;
	public static void fetchStudID(SessionState ss)
	{
		try
		{
			ss.students = new ArrayList<String>();
			
			String sql = "SELECT userID FROM userlist WHERE teacherID = '"+ss.userID+"' ";
			ss.rs = SqlUtil.sqlQuery(sql);
			
			while(ss.rs.next())
			{
				String studID = ss.rs.getString("userID");
				ss.students.add(studID);
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchStudID ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchStudID SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchStudID Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	
	// for EditTLists.java
	
	
	public static void fetchTeachEdits(SessionState ss)
	{
		try
		{
			String sql = "";
			
			ss.wordList = new ArrayList<WordBean>();
			
			if(ss.threeCols)
			{
				sql = "SELECT ID, type, english, meaning, reading FROM "+ss.table+" WHERE edit = 1";
			}
			else 
			{
				sql = "SELECT ID, type, english, meaning FROM "+ss.table+" WHERE edit = 1";
			}
			
			ss.rs = SqlUtil.sqlQuery(sql);
			
			ss.rs.beforeFirst();
			
			while(ss.rs.next())
			{
				WordBean wBean = new WordBean();
				
				wBean.id = ss.rs.getInt("ID");
				wBean.typ = ss.rs.getString("type");
				wBean.eng = ss.rs.getString("english");
				wBean.kan = ss.rs.getString("meaning");
				
				if(ss.threeCols)
					wBean.yom = ss.rs.getString("reading");
				
				wBean.rowID = String.valueOf(wBean.id);
				
				ss.wordList.add(wBean);
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchTeachEdits ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchTeachEdits SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchTeachEdits Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void updateTLists(WordBean wb, SessionState ss)
	{
		try
		{
			String sql = "";
			
			if(ss.threeCols)
			{
				sql = "UPDATE "+ss.table+" SET type='"+wb.typ+"', english='"+wb.eng+"', meaning='"+wb.kan+"', reading='"+wb.yom+
					"' WHERE ID="+wb.id;
			}
			else 
			{
				sql = "UPDATE "+ss.table+" SET type='"+wb.typ+"', english='"+wb.eng+"', meaning='"+wb.kan+"' WHERE ID="+wb.id;
			}
			
			SqlUtil.sqlExec(sql);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateTLists ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateTLists Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static void updateApproved(int y, SessionState ss)
	{
		try
		{
			WordBean wb = new WordBean();
			wb = ss.wordList.get(y);
			
			String sql = "UPDATE "+ss.table+" SET edit=0 WHERE ID="+wb.id;
				
			SqlUtil.sqlExec(sql);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.updateApproved ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.updateApproved Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// for userinfo.jsp
	
	
	public static void fetchUserInfo(SessionState ss) throws ServletException, SQLException
	{
		String sql = "SELECT familyName, firstName, contact FROM userlist WHERE userID = '"+ss.userID+"' ";
		
		ss.rs = SqlUtil.sqlQuery(sql);
		
		ss.rs.beforeFirst();
		
		while(ss.rs.next())
		{
			ss.familyName = ss.rs.getString("familyName");
			ss.firstName = ss.rs.getString("firstName");
			ss.contact = ss.rs.getString("contact");
		}
	}
	
	
	// for FindSame.java
	
	
	public static void findSame(SessionState ss)
	{
		try
		{
			ss.wordList = new ArrayList<WordBean>();
			ArrayList<WordBean> wbList = new ArrayList<WordBean>();
			ArrayList<Integer> sameRange = new ArrayList<Integer>();
				
			
			String sql = "SELECT english, meaning FROM "+ss.table+"";
			
			ss.rs = SqlUtil.sqlQuery(sql);
			
			ss.rs.beforeFirst();
				//ss.dbgApp("findSame 1 : got Result Set with "+sql); //debug
				
			while(ss.rs.next())
			{
				WordBean wBean = new WordBean();
				
				wBean.eng = ss.rs.getString("english");
				wBean.kan = ss.rs.getString("meaning");
				
				wbList.add(wBean);
			}
				//ss.dbgApp("findSame 1A : added Eng and Kan to "+wbList.size()+" WordBeans"); //debug
				
			for(int x = 0; x < wbList.size(); x++)
			{
				WordBean wb = new WordBean();
				
				wb = wbList.get(x);
				
				if(ss.threeCols)
				{
					sql = "SELECT ID, type, english, meaning, reading FROM "+ss.table+
						" WHERE english = '"+wb.eng+"' AND meaning = '"+wb.kan+"' ";
				}
				else 
				{
					sql = "SELECT ID, type, english, meaning FROM "+ss.table+
						" WHERE english = '"+wb.eng+"' AND meaning = '"+wb.kan+"' ";
				}
					//ss.dbgApp("findSame 2 : before Result Set with "+sql); //debug
				
				setQuery(sql, ss);
				
				if(ss.numOfRows > 1)
				{
					ss.rs.beforeFirst();
						//ss.dbgApp("findSame 3 : got Result Set with "+sql); //debug
					
					while(ss.rs.next())
					{
						WordBean wBean = new WordBean();
						
						wBean.id = ss.rs.getInt("ID");
						wBean.typ = ss.rs.getString("type");
						wBean.eng = ss.rs.getString("english");
						wBean.kan = ss.rs.getString("meaning");
						
						if(ss.threeCols)
							wBean.yom = ss.rs.getString("reading");
						
						wBean.rowID = String.valueOf(wBean.id);
						
						if(!sameRange.contains(wBean.id)) 
						{
							sameRange.add(wBean.id);
							ss.wordList.add(wBean);
						}
					}
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.findSame ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.findSame SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.findSame Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	
	// for switch_lang.jsp
	public static void setThreeCols(SessionState ss)
	{
		try
		{
			ss.threeCols = false;
			
			String sql = "SELECT * FROM "+ss.table+" WHERE ID = 1";
			
			setQuery(sql, ss);
			
			for(int i = 0; i < getColumnCount(ss); i++)
			{
				if(getColumnName(i, ss).equals("reading"))
					ss.threeCols = true;
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.setThreeCols ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.setThreeCols SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.setThreeCols Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// for CreateLangTable.jsp
	
	
	public static void handleCLT(SessionState ss)
	{
		try
		{
			String sql = "";
			
			ss.tempTable = StringHandler.adjustString(ss.tempTable);
			
			getTables(ss); // adds SHOW TABLES ResultSet to ss.tables
			
				//ss.dbgApp("got to handleCLT 1: ss.tables = "+ss.tables); 
			if(ss.tables.contains(ss.tempTable)) 
			{
					//ss.dbgApp("got to handleCLT 2: ss.tableb("+ss.tables+") is in ss.tables = "+ss.tables); 
				ss.message = "Sorry, but that table already exists";
				return;
			}
			else
			{
				ss.setTable(ss.tempTable);
				// maybe we want the ID to be set to Null so will begin at 1
				if(ss.threeCols)
				{
					sql = "CREATE TABLE "+ss.table+" (ID int(11) NULL AUTO_INCREMENT PRIMARY KEY, edit int(4), type varchar(20), english varchar(50), "+
						"meaning varchar(50), reading varchar(50), engexamp longtext, langexamp longtext) ENGINE=InnoDB DEFAULT CHARSET=utf8";
				}
				else
				{
					sql = "CREATE TABLE "+ss.table+" (ID int(11) NULL AUTO_INCREMENT PRIMARY KEY, edit int(4), type varchar(20), english varchar(50), "+
						"meaning varchar(50), engexamp longtext, langexamp longtext) ENGINE=InnoDB DEFAULT CHARSET=utf8";
				}
					//ss.dbgApp("got to handleCLT 3: sql = "+sql); 
					
				SqlUtil.sqlExec(sql);
				
				getTables(ss); // adds SHOW TABLES ResultSet to ss.tables; this time makes new tables visible
				
				return;
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.handleCLT ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.handleCLT ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	// not using this as only admin will create tables
	private static void addToCreatorsTable(SessionState ss)
	{
		try
		{
			String sql = "INSERT INTO creators SET tableName = '"+ss.table+"', creatorID = '"+ss.userID+"', date = '"+ss.TimeStarted.getTime().toString()+"'";
				//ss.dbgApp("got to addToCreatorsTable 1: sql = "+sql); 
				
			SqlUtil.sqlExec(sql);
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.addToCreatorsTable ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	
	// for dbtable.jsp
	
	
	public static String[] getColNames(SessionState ss) throws ServletException, SQLException
	{
		String[] cols;
		
		String sql = "SELECT * FROM "+ss.dbTable+" WHERE ID = 1"; 
		
		setQuery(sql, ss);
		
		cols = new String[ss.numOfCols];
		
		for( int i = 0; i < cols.length; i++ )
		{
			cols[i] = getColumnName(i, ss);
		}
		
		return cols;
	}
	
	
	public static void changeTableName(String oldTName, String newTName, SessionState ss) throws ServletException
	{
		String sql = "RENAME TABLE "+oldTName+" TO "+newTName;
		
		SqlUtil.sqlExec(sql);
	}
	
	
	public static void changeColumnName(String oldCName, String newCName, SessionState ss) throws ServletException
	{
		String sql = "ALTER TABLE "+oldCName+" TO "+newCName;
		
		SqlUtil.sqlExec(sql);
	}
	
	
	
	// for updating adminList with japanese table column details
	public static void fetchID(SessionState ss)
	{
		try
		{
			String sql = "";
			
			for(String s : Selections.v)
			{
				ss.prevSelect = s;
				
				if(s.equals("eiken4"))
				{
					sql = "SELECT ID FROM japanese WHERE eiken = '"+s+"' "; //AND ID < 1000
					setQuery(sql, ss);
						//ss.dbgApp("got to fetchID sql: "+sql); 
				}
				else if(s.equals("business1"))
				{
					continue;
				}
				else
				{
					sql = "SELECT ID FROM japanese WHERE eiken = '"+s+"'";
					setQuery(sql, ss);
						//ss.dbgApp("got to fetchID sql: "+sql); 
				}
				
				ss.rs.beforeFirst();
				
				while(ss.rs.next())
				{
					int x = ss.rs.getInt("ID");
					
					ss.adminList = StringHandler.addToALString(x, ss.prevSelect, ss.adminList, ss);
						//ss.dbgApp("got to fetchID: x = "+x);
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("dbHand.fetchID ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.fetchID SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.fetchID Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	
	// ResultSet and MetaData stuff
	
	
	// handles ss.rs and ss.metaData related details
	public static void setQuery(String query, SessionState ss) throws ServletException, SQLException, IllegalStateException
	{
		// if( !connectedToDatabase ) throw new IllegalStateException( "Not Connected to Database" );
		
		ss.rs = SqlUtil.sqlQuery(query);
		
		ss.metaData = ss.rs.getMetaData();
		ss.numOfCols = ss.metaData.getColumnCount();
		
		ss.rs.last();
		ss.numOfRows = ss.rs.getRow();
		
		// fireTableStructureChanged();
	}
	
	
	public static int getColumnCount(SessionState ss) throws IllegalStateException
	{
		// if( !connectedToDatabase ) throw new IllegalStateException( "Not Connected to Database" );
		
		try { return ss.metaData.getColumnCount(); }
		catch(SQLException ex) { ss.dbgApp("dbHand.getColumnCount SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.getColumnCount Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return 0;
	}
	
	
	public static String getColumnName(int column, SessionState ss) throws IllegalStateException
	{
		// if( !connectedToDatabase ) throw new IllegalStateException( "Not Connected to Database" );
		
		try { return ss.metaData.getColumnName(column + 1); }
		catch(SQLException ex) { ss.dbgApp("dbHand.getColumnName SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.getColumnName Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return "";
	}
	
	
	public static String getColumnTypeName(int column, SessionState ss) throws IllegalStateException
	{
		// if( !connectedToDatabase ) throw new IllegalStateException( "Not Connected to Database" );
		
		try
		{
			String typeName = ss.metaData.getColumnTypeName( column + 1 );
			
			return typeName;
		}
		catch(SQLException ex) { ss.dbgApp("dbHand.getColumnTypeName SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.getColumnTypeName Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return "";
	}
	
	
	public static Class getColumnClass(int column, SessionState ss) throws IllegalStateException
	{
		// if( !connectedToDatabase ) throw new IllegalStateException( "Not Connected to Database" );
		
		try
		{
			String className = ss.metaData.getColumnClassName( column + 1 );
			
			return Class.forName( className );
		}
		catch(SQLException ex) { ss.dbgApp("dbHand.getColumnClass SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.getColumnClass Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return Object.class;
	}
	
	
	public static int getRowCount(SessionState ss) throws IllegalStateException
	{
		// if( !connectedToDatabase ) throw new IllegalStateException( "Not Connected to Database" );
		
		return ss.numOfRows;
	}
	
	
	public static Object getValueAt(int row, int column, SessionState ss) throws IllegalStateException
	{
		// if( !connectedToDatabase ) throw new IllegalStateException( "Not Connected to Database" );
		
		try 
		{ 
			ss.rs.absolute(row + 1);
			return ss.rs.getObject(column + 1); 
		}
		catch(SQLException ex) { ss.dbgApp("dbHand.getValueAt SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand.getValueAt Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		return "";
	}
	
	
	
	/*public static void tryCatchTemplate(String x, SessionState ss)
	{
		try
		{
		}
		catch(ServletException ex) { ss.dbgApp("dbHand. ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(SQLException ex) { ss.dbgApp("dbHand. SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("dbHand. Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}*/
	
	
	/*public static void fetchTemplate(SessionState ss) throws ServletException, SQLException
	{
		String sql = "SELECT * FROM " + ss.dbTable + " WHERE " + getColumnName(0, ss) + " = '" + ss.radioID + "' ";
		
		setQuery(sql, ss);
		
		ss.rs.beforeFirst();
		
			//ss.dbgApp("got to handleRowSelection"); 
		while(ss.rs.next())
		{
		}
	}*/
	
}
