// Altered on 2006-06-18
// A Class that provides sets and gets for just
// the id, typ, english, kanji and reading variables.

package usefuljavas;

import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;

	
public class CalculateHistory
{
	// in SessionState
	private int studMinutes = 0, perRev = 0, totRev = 0, perTest = 0, totTest = 0;
	private int studMinPrev = 0, perRevPrev = 0, totRevPrev = 0, perTestPrev = 0, totTestPrev = 0, countRev = 0, countTest = 0;
	
	private String first = "", last = "", totals = "";
	private String[] totArray = new String[4];
	
	private ResultSet rs = null;
	
	private ArrayList<String> dates = new ArrayList<String>();
	private ArrayList<String> historyList = new ArrayList<String>();
	
	
	// sets many of the above int variables to 0; used in getHistoryList();
	private void clearHistory()
	{
		studMinPrev = 0;
		perRevPrev = 0;
		totRevPrev = 0;
		perTestPrev = 0;
		totTestPrev = 0;
		countRev = 0;
		countTest = 0;
	}
	
	// returns String[] totArray, which is filled with html elements for a table of the user's history;
    // used in allHistories.jsp
	public String[] getHistoryList(String userID, SessionState ss)
	{
		String list = "", totList = "";
		dates = new ArrayList<String>();
		
		clearHistory(); // sets many of the above int variables to 0;
		fetchHistoryList(userID, ss); // sets ArrayList historyList with selected history elements from table userlist for a given userID;
		
		for( int i = 2; i < historyList.size(); i++ )
		{
			String s = historyList.get(i).toString();
			String[] histArray = StringHandler.splitSecondString(s); // returns array secSplit after splitting the passed string around commas;
			
			sumUpHistory(histArray, ss); // sets many of the top int variables with values in String[] histArray,
                // then adds them to the previous values with the given dates;

            // generates html for display in a table
			list = "<tr>";
			
			for(String h : histArray)
			{
				list = list + "<td class='p2'>"+h+"</td>";
			}
			
			list = list + "</tr>";
			
			totList = totList + list;
		}
		
		calculateHistory(ss); // sets first and last vars with values from ArrayList dates;
            //sets perRev and perTest to calculated percentages;
		
		totArray[0] = totList; // a String filled with table rows full of historylist elements
		totArray[1] = getTableHTML(userID, ss); // returns String html full of html rows filled with most of the above main variables, as well as userID;
		totArray[2] = getTotalHTML(ss); // returns a String filled with most of the total and details vars
		totArray[3] = getHeadHTML(ss); // returns a String filled with html for creating table headers;
		
		return totArray;
	}
	
	// sets ArrayList historyList with selected history elements from table userlist for a given userID; used in getHistoryList();
	private void fetchHistoryList(String userID, SessionState ss)
	{
		try
		{
			String history = "";
			
			//ss.dbgApp("got here fetchHistoryLists try"); //debug
			
			String sql = "SELECT history FROM userlist WHERE userID = '"+userID+"' ";
			rs = SqlUtil.sqlQuery(sql);
			
			// rs.beforeFirst();
			rs.next();
			
			//ss.dbgApp("got here fetchHistoryLists RS SQL: "+sql); //debug
			
			Clob hs = rs.getClob("history");
			
			//ss.dbgApp("got here fetchHistoryLists clobs"); //debug
			
			if (hs.length() == 0 || hs == null)	
			{
				//ss.dbgApp("hs clob is empty"); //debug
			}
			else history = clobString(hs, ss); // returns String clobString created from the passed clob
				//ss.dbgApp("hs clob is "+history); //debug
				
			historyList = StringHandler.splitFirstString(history); // returns ArrayList<String> splitList which is filled with elements from history split around semi-colons;
			
			rs.close();
		}
		catch(SQLException ex) {ss.dbgApp("SQLException: " + ex.getMessage());}
		catch(ServletException e) {ss.dbgApp("ServletException: " + e.getMessage());}
		catch(Exception e) {ss.dbgApp("Exception: " + e.getMessage());}
	}
	
	// returns String clobString created from the passed clob
	private String clobString(Clob clob, SessionState ss) throws SQLException
	{
		long x = 1;
		long len = clob.length();
		
		String clobString = clob.getSubString(x, (int)len);
		
		return clobString;
	}
	
	
	// in BookMethods

    // sets many of the top int variables with values in String[] histArray, then adds them to the previous values; used in getHistoryList();
	private void sumUpHistory(String[] histArray, SessionState ss)
	{
		studMinutes = Integer.parseInt(histArray[1]);
		perRev = Integer.parseInt(histArray[2]);
		totRev = Integer.parseInt(histArray[3]);
		perTest = Integer.parseInt(histArray[4]);
		totTest = Integer.parseInt(histArray[5]);
		
		if(studMinutes != 0) {studMinPrev = studMinPrev + studMinutes;}
		if(perRev != 0) {perRevPrev = perRevPrev + perRev;}
		if(totRev != 0) {totRevPrev = totRevPrev + totRev;}
		if(perTest != 0) {perTestPrev = perTestPrev + perTest;}
		if(totTest != 0) {totTestPrev = totTestPrev + totTest;}
		
		dates.add(histArray[0]);
		
		if(totRev != 0) {countRev++;}
		if(totTest != 0) {countTest++;}
	}
	
	
	// in BookMethods
    // sets first and last vars with values from ArrayList dates; sets perRev and perTest to calculated percentages;
    // used in getHistoryList
	private void calculateHistory(SessionState ss)
	{
		if(countRev == 0) {countRev = 1;}
		if(countTest == 0) {countTest = 1;}
		
		if(dates.size() > 1) 
		{ 
			first = dates.get(0).toString();
			last = dates.get(dates.size() - 1).toString(); 
		}
		else if(dates.size() == 1) 
		{ 
			first = dates.get(0).toString();
			last = "Empty"; 
		}
		else
		{ 
			first = "Empty"; 
			last = "Empty"; 
		}
		
		perRev = perRevPrev / countRev;
		perTest = perTestPrev / countTest;
	}
	
	// returns a String filled with html for creating table headers;
    // used in getHistoryList()
	private String getHeadHTML(SessionState ss)
	{
		String[] html = {"<tr><th>Date Started</th><th>Minutes</th><th>Correct<br />Review (%)</th><th>Rev Total</th>"+
			"<th>Correct<br />Test (%)</th><th>Test Total</th></tr>",
			"<tr><th>開始日時</th><th>時間（分）</th><th>復習の正解(%)</th><th>復習の合計</th>"+
			"<th>試験の正解(%)</th><th>試験の合計</th></tr>"};
		
		return html[ss.lang];
	}
	
	// returns a String filled with most of the total and details vars
    // used in getHistoryList()
	private String getTotalHTML(SessionState ss)
	{
		String[] html = {"<tr><td colspan='6'><p class='study_word'>( Between "+first+" and "+last+" )</p>"+
			"<p class='study_word'>Minutes studied: "+studMinPrev+" ("+studMinPrev/60+"+ Hours)</p>"+
			"<p class='study_word'>Percent Correct (Review): "+perRev+"  %</p>"+
			"<p class='study_word'>Words reviewed: "+totRevPrev+"</p>"+
			"<p class='study_word'>Percent Correct (Test): "+perTest+"  %</p>"+
			"<p class='study_word'>Words tested: "+totTestPrev+" </p></td></tr>",
			"<tr><td colspan='6'><p class='study_word'>( "+first+" と "+last+"　の間 )</p>"+
			"<p class='study_word'>勉強時間: "+studMinPrev+"分 ("+studMinPrev/60+"+ 時間)</p>"+
			"<p class='study_word'>正解率 (復習): "+perRev+"  %</p>"+
			"<p class='study_word'>復習した単語: "+totRevPrev+"</p>"+
			"<p class='study_word'>正解率 (試験): "+perTest+"  %</p>"+
			"<p class='study_word'>試験した単語: "+totTestPrev+" </p></td></tr>"};
			
		
		return html[ss.lang];
	}
	
	// returns String html full of html rows filled with most of the above variables;
    // used in getHistoryList()
	public String getTableHTML(String userID, SessionState ss)
	{
		String html = "<tr><td>"+userID+"</td>"+
			"<td class='p2'>"+first+"</td>"+
			"<td class='p2'>"+studMinPrev+"</td>"+
			"<td class='p2'>"+perRev+"</td>"+
			"<td class='p2'>"+totRevPrev+"</td>"+
			"<td class='p2'>"+perTest+"</td>"+
			"<td class='p2'>"+totTestPrev+"</td>"+
			"<td><input type = radio  name = 'radio' value = '"+userID+"'></td></tr>";
			
		return html;
	}
	
	// for notes.jsp
	
	
	public String[][] getNotes(SessionState ss)
	{
		try
		{
			int i = 0;
			String notes = "";
			
			String sql = "SELECT * FROM notepad ORDER BY noteID DESC";
			rs = SqlUtil.sqlQuery(sql);
			
				ss.dbgApp("got here notes 1: sql = "+sql); //debug
				
			rs.last();
			String[][] nn = new String[rs.getRow()][2];
		
			rs.beforeFirst();
			while(rs.next())
			{ 	
				int x = rs.getInt("noteID");
				Clob n = rs.getClob("noteText");
				
				if (n.length() == 0 || n == null)	
				{
					ss.dbgApp("n clob is empty"); //debug
				}
				else notes = clobString(n, ss);
					//ss.dbgApp("n clob is "+notes); //debug
					
				nn[i][0] = Integer.toString(x);
				nn[i][1] = notes;
					
				i++;
			}
			
			rs.close();
			
			return nn;
			//historyList = ss.bkMeth.splitFirstString(notes);
		}
		catch(SQLException ex) {ss.dbgApp("SQLException: " + ex.getMessage());}
		catch(ServletException e) {ss.dbgApp("ServletException: " + e.getMessage());}
		catch(Exception e) {ss.dbgApp("Exception: " + e.getMessage());}
		
		String[][] nn;
		nn = new String[1][2];
		
		nn[0][0] = "No";
		nn[0][1] = "Notes";
		
		return nn;
	}
	
	
}
