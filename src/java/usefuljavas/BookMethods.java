// Altered on 2009-06-23

// Double-check handleTest as it may be doing redundant or stupid things.

// A Class that provides variables, sets and gets for Sessions, SQL connections and Book classes.

package usefuljavas;

import usefulbeans.*;
import java.util.*;

// import java.sql.SQLException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;


public final class BookMethods
{
	
	
	// for putting listStrings into ArrayLists
	
	
	public static void setMainList(SessionState ss)
	{
		if(ss.listGroup.equals(ss.table) && ss.role.equals("admin"))
			return;
		else if(checkEiken(ss))
			ss.mainString = DBHandler.fetchAnEikenList(ss); // should only be used by add_popup.jsp and EditRows, and now by Learning
		else if(checkOwn(ss))
			ss.mainString = ss.mapValueToCSV(ss.listName, ss); // used to be ss.dbHand.fetchOwnList(ss);
		else
			ss.mainString = DBHandler.fetchOwnChapList(ss); // should get all the user's own lists where language = ss.language
		
		if(ss.mainString == null || ss.mainString.length() == 0)
		{
			ss.mainList = new ArrayList<Integer>();
		}
		else
		{
			String[] spList = StringHandler.splitSecondString(ss.mainString);
			
			ss.mainList = new ArrayList<Integer>();
			
			for(String s : spList)
			{
				ss.mainList.add(Integer.parseInt(s));
			}
				//ss.dbgApp("got here setMainList: ss.mainList.size() = "+ss.mainList.size()); //debug
		}
	} // sets ss.mainList by finding the right list in the DB or ss.map
	
	
	private static boolean checkEiken(SessionState ss)
	{
		if(ss.language.equals("japanese"))
		{
			for(Lists list : EnumSet.range(Lists.EIKEN1, Lists.EIKEN4))
			{
				String name = list.getName();
				
				if(name.equals(ss.listGroup))
					return true;
			}
		}
		
		return false;
	}
	
	
	private static boolean checkOwn(SessionState ss)
	{
		for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
		{
			String name = list.getName();
			
			if(name.equals(ss.listGroup))
			{
				ss.listName = list.getListName(ss.type);
				return true;
			}
		}
		
		return false;
	} // returns true if ss.listGroup equals a name in Lists.KNOWLIST, Lists.KNOWLIST or Lists.MAYBELIST, 
		// and then sets ss.listName from ss.type
	
	
	public static ArrayList<String> setLangLists(String clobString, SessionState ss)
	{
		ArrayList<String> langList  = new ArrayList<String>();
		
		String[] tList = clobString.split(":"); //{"123;456;444;22", "12;13;14;55;66"};
		
		for(int i = 0; i < tList.length; i++)
		{
			String list = tList[i];
			langList.add(list);
		}
		
		return langList;
	}
	
	
	private static String[] getCS(ArrayList<String> langList, SessionState ss)
	{
		ArrayList<String> typList = new ArrayList<String>();
			
		for(String clobString : langList)
		{
			if((clobString.length() == 0) || (clobString == null)) {  } // do nothing; used to be - typList = new ArrayList<String>();
			else
			{
				String[] tList = clobString.split(";"); //{"123;456;444;22", "12;13;14;55;66"};
				
				if(ss.table.equals(tList[0]) && tList.length > 2)
				{
					for(int i = 1; i < tList.length; i++)
					{
						String list = tList[i];
						typList.add(list);
					}
				}
			}
		}
		
		return typList.toArray(new String[typList.size()]);
	}
	
	
	public static void setOwnLists(SessionState ss)
	{
		int x = 0;
		
		for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
		{
			int y = 0;
			
			for(Types type : Types.values())
			{
				ss.listName = list.getListName(type.getName());
				ss.lists[x][y] = ss.listName+","+DBHandler.fetchOwnList(ss);
				
				y++;
			}
			
			x++;
		}
		
	}
	
	// CHECK
	// is this being used at all?
	public static void setAllLists(SessionState ss) //throws SQLException, ServletException
	{
		ss.maybeListVerbs = StringHandler.setIntList(ss.lists[1][0]);
		ss.maybeListNouns = StringHandler.setIntList(ss.lists[1][1]);
		ss.maybeListAdjs = StringHandler.setIntList(ss.lists[1][2]);
		ss.maybeListMisc = StringHandler.setIntList(ss.lists[1][3]);
		
					//ss.dbgApp("got here setAllLists maybe"); //debug
					
		ss.knowListVerbs = StringHandler.setIntList(ss.lists[2][0]);
		ss.knowListNouns = StringHandler.setIntList(ss.lists[2][1]);
		ss.knowListAdjs = StringHandler.setIntList(ss.lists[2][2]);
		ss.knowListMisc = StringHandler.setIntList(ss.lists[2][3]);
		
					//ss.dbgApp("got here setAllLists teach"); //debug
	}
	
	
	
	public static boolean adjustReq(HttpServletRequest req, String[] reqVars, SessionState ss) // HttpServletResponse res, 
		throws ServletException, IOException
	{
		ss.rVars = new ArrayList<String>();
		
		for(String r : reqVars)
		{
			r = req.getParameter(r);
			
			if(r == null || r.length() == 0) 
			{
				ss.message = Messages.getMess9(ss);
				return false;
			}
			else 
			{ 
				r = StringHandler.adjustString(r); 
				
				if(r.length() == 0 || r == null || r.equals("NG")) // may need to change this NG; why is it here?
				{
					ss.message = Messages.getMess49(ss);
					return false;
				}
				
				ss.rVars.add(r);
			}
		}
		
		return true;
	}
	
	
	public static String getPopUpScript()
	{
		String s = "function popup(mylink, windowname, w, h){if (! window.focus)return true;var href;"+
			"if (typeof(mylink) == 'string')href=mylink; else href=mylink.href;"+
			"window.open(href, windowname, 'width='+w+',height='+h+',scrollbars=yes'); return false;} ";
		
		return s;
	}
	
	
	public static String getPopUpScriptForm()
	{
		String s = "function popupform(myform, windowname){if (! window.focus)return true;"+
			"window.open('', windowname, 'menubar=yes,scrollbars=yes,resizable=yes'); myform.target=windowname; return true;}";
		
		return s;
	}
	
	
	public static void handleDropList(String dropList, SessionState ss)
	{
		Selections.setSSList(dropList, ss); // takes the useList, determines if it is in Lists or is a Chapter,
			// and sets ss.listGroup, ss.list, ss.mainList and ss.prevList
	} // sets ss.listGroup, ss.list, ss.mainList and ss.prevList

    
    
    // for FindSame


    public static String prntFS(SessionState ss)
    {
        String s = "";

        s = "<tr><td colspan='4'>&nbsp;</td>"+Buttons.roundButton(Buttons.getBut32(ss), ss)+"<td>&nbsp;</td></tr>"+
                "<tr><th>ID</th><th>Type</th><th>English</th><th>Kanji</th><th>Yomi</th>"+
                Buttons.roundButton(Buttons.getBut5(ss), ss)+"<td><p>"+ss.check+"</p></td></tr>";

        for(int y = 0; y < ss.wordList.size(); y++)
        {
            WordBean wb = new WordBean();

            wb = ss.wordList.get(y);

            String x = String.valueOf(y);
            ss.typSel = x;
            setPrevType(wb.typ, ss);

            s = s+"<tr><td><p align='center'>"+wb.rowID+"</p><input type='hidden' name='"+x+"ID' value='"+wb.rowID+
                "'/><td class='center'>"+Selections.getSelect19(ss)+
                "</td><td><input type='text' style='width:140px' name='"+x+"eng' value=\""+wb.eng+"\"/>"+
                "</td><td><input type='text' style='width:140px' name='"+x+"kan' value=\""+wb.kan+"\"/>"+
                "</td><td><input type='text' style='width:140px' name='"+x+"yom' value=\""+wb.yom+"\"/></td>"+
                "<td align='center'><input type='checkbox' name='"+x+"check'></td></tr>";
        }

        return s;
    }



    // for all searches

    public static String prntSearch(SessionState ss)
    {
        String sch = "<form action='/"+ss.folder+"/Search' method='post' name='search' id='search' accept-charset='UTF-8' >"+
			"<td class='center'><input type='text' style='width:150px' maxlength='30' name='searchvar' value=\""+
				ss.searchVar+"\" /></td>"+Buttons.roundButton(Buttons.getBut2(ss), ss)+"</form>";

        return sch;
    }




	public static void handleSearch(SessionState ss)
	{
		try
		{
			if(ss.searchVar == null || ss.searchVar.length() == 0)
			{
				ss.message = Messages.getMess25(ss);
				return;
			}
			else { DBHandler.fetchSearch(ss); }
		}
		catch(Exception ex) { ss.dbgApp("bkMeth.handleSearch Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}


	
	// for notes.jsp
	
	
	// used to make a csv string from wordbean elements
	public static void createCSV(SessionState ss)
	{
		String str = "", totStr = "";
		
		for(WordBean wb : ss.wordList)
		{
			str = wb.typ+","+wb.eng+","+wb.kan+","+wb.yom;
			
			totStr = totStr+str+";";
		}
		
		ss.strCSV = totStr;
	}
	
	
	// used to make ArrayList<WordBean> ss.wordList by splitting a csv string
	private static void getCSV(ArrayList<String> splitList, SessionState ss)
	{
			String[] s = new String[4];
			
			ss.wordList = new ArrayList<WordBean>();
			
			for(int x = 1; x < splitList.size(); x++)
			{
				WordBean wb = new WordBean();
				
				s = StringHandler.splitSecondString(splitList.get(x));
			
				wb.typ = s[0];
				wb.eng = s[1];
				wb.kan = s[2];
				wb.yom = s[3];
				
				ss.wordList.add(wb);
			}
			
			DBHandler.insertCSV(ss);
	}
	
	
	public static void handleNotes(String note, SessionState ss)
	{
		if(note != null)
		{
			if(note.contains(";"))
			{
				ArrayList<String> splitList = new ArrayList<String>();
				
				splitList = StringHandler.splitFirstString(note);
				
				if(splitList.size() >= 2)
				{
					if(splitList.get(0).equals("fetchMisplaced")) { DBHandler.fetchMisplaced(splitList, ss); }
					if(splitList.get(0).equals("update")) { DBHandler.updateNote(splitList, ss); }
					if(splitList.get(0).equals("insert")) { getCSV(splitList, ss); }
				}
			}
		}
	}
	
	
	// for CreateQuiz.java and print_quiz.jsp


    public static String prntQzzr(SessionState ss)
    {
        String s = "";

        if(ss.quizList)
        {
            s = "<form method = 'post' action='/"+ss.folder+"/print_quiz.jsp' onSubmit='return popupform(this,\"printquiz\")'"+
                " name='printquizzer' id='printquizzer' accept-charset='UTF-8' >"+
                "<tr><td colspan='3'><p class='study_word'>Choose the column you would like the students to see: </p></td></tr>"+
                "<tr><td colspan='4' align='right'>"+ss.html.begTable()+"<tr><td align='right'>"+Selections.getSelect18(ss)+"</td>"+
                Buttons.roundButton(Buttons.getBut26(ss), ss)+"</tr>"+ss.html.endTable()+"</td></tr></form>";
        }

        return s;
    }


    public static String prntQOpts(SessionState ss)
    {
        int i = 1;
        String s = "";

        if(ss.threeCols)
        {
            s = "<tr><th>Number</th><th>English</th><th>Kanji</th><th>Yomi</th></tr>";

            for(WordBean wBean : ss.printList)
            {
                s = s+"<tr style='padding: 0.1cm 0.1cm'><td class='id'>"+i+
                    "</td><td class='eng'>"+wBean.getEng()+
                    "</td><td class='kan'>"+wBean.getKan()+
                    "</td><td class='yom'>"+wBean.getYom()+"</td></tr>";

                i++;
            }
        }
        else
        {
            s = "<tr><th>Number</th><th>English</th><th>Language</th></tr>";

            for(WordBean wBean : ss.printList)
            {
                s = s+"<tr style='padding: 0.1cm 0.1cm'><td class='id'>"+i+
                    "</td><td class='eng'>"+wBean.getEng()+
                    "</td><td class='kan'>"+wBean.getKan()+
                    "</td></tr>";

                i++;
            }
        }

        return s;
    }


    public static String prntQAns(SessionState ss)
    {
        String s = "";

        if(ss.quizList)
        {
            s = "<form method = 'post' action='/"+ss.folder+"/print_answers.jsp'  onSubmit='return popupform(this,\"printquiz\")'"+
                " name='printquizzer' id='printquizzer' ><tr><td colspan='3'><p class='study_word'>Print the Answers for the Quiz<br />(there may be more than one correct answer):</p></td></tr>"+
                "<tr><td colspan='4' align='right'>"+ss.html.begTable()+"<tr>"+Buttons.roundButton(Buttons.getBut27(ss), ss)+"</tr>"+ss.html.endTable()+"</td></tr></form>";
        }
        
        return s;
    }

	
	public static void getQuizList(SessionState ss)
	{
		int x = 0;
		
		ArrayList<String> tempList = new ArrayList<String>();
		
		if(ss.quizNum < ss.list.size())
		{
			x = ss.quizNum;
				
			for(int i = 0; i < x; i++)
			{
				int id = 0;
				
				do 
				{
					Random randomInt = new Random();
					id = randomInt.nextInt(x);
				}
				while(tempList.contains(String.valueOf(ss.list.get(id))));
				
				tempList.add(String.valueOf(ss.list.get(id)));
			}
			
			ss.list = new ArrayList<Integer>();
			ss.list = StringHandler.makeList(tempList, ss);
		}
		else
			{ ss.message = Messages.getMess36(ss); }
		
		try
		{
			DBHandler.fetchListRows(ss); // fetches all of the rows in ss.table that are in ss.list, assigns the values to a WordBean, which is added to ss.wordList
			ss.printList = ss.wordList;
			
			ss.message = Messages.getMess37(ss);
			ss.quizList = true;
		}
		catch(Exception ex) { ss.dbgApp("bkMeth.getQuizList Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
	}
	
	
	public static String getPrintQz(SessionState ss)
	{
		int x = 1;
		String qPrint = "";
		
		for(WordBean list : ss.printList)
		{
			if(ss.printChoice.equals("English") || ss.printChoice.equals("英語"))
			{
				qPrint = qPrint + "<tr><td>"+x+". </td><td align='left'>"+list.eng+"</td><td> _______________________________________</td></tr>"+
					"<tr><td></td><td></td><td></td></tr>";
			}
			else if(ss.printChoice.equals("Kanji") || ss.printChoice.equals("漢字"))
			{
				qPrint = qPrint + "<tr><td>"+x+". </td><td align='left'>"+list.kan+"</td><td> _______________________________________</td></tr>"+
					"<tr><td></td><td></td><td></td></tr>";
			}
			else if(ss.printChoice.equals("Yomi") || ss.printChoice.equals("読み"))
			{
				qPrint = qPrint + "<tr><td>"+x+". </td><td align='left'>"+list.yom+"</td><td> _______________________________________</td></tr>"+
					"<tr><td></td><td></td><td></td></tr>";
			}
			
			x++;
		}
		
		return qPrint;
	}
	
	
	
	public static String getPrintAns(SessionState ss)
	{
		int x = 1;
		String qPrint = "";
		
		if(ss.threeCols)
		{
			for(WordBean list : ss.printList)
			{
				qPrint = qPrint + "<tr><td>"+x+". </td><td align='left'>"+
					list.eng+"</td><td align='left'>"+list.kan+"</td><td align='left'>"+list.yom+"</td></tr>"+
					"<tr><td></td><td></td><td></td></tr>";
					
				x++;
			}
		}
		else
		{
			for(WordBean list : ss.printList)
			{
				qPrint = qPrint + "<tr><td>"+x+". </td><td align='left'>"+
					list.eng+"</td><td align='left'>"+list.kan+"</td><td align='left'></td></tr>"+
					"<tr><td></td><td></td><td></td></tr>";
					
				x++;
			}
		}
			
		return qPrint;
	}
	
	
	
	// for allHistories.jsp
	
	
	public static void sumUpHistory(String[] histArray, SessionState ss)
	{
		ss.studMinutes = Integer.parseInt(histArray[1]);
		ss.perRev = Integer.parseInt(histArray[2]);
		ss.totRev = Integer.parseInt(histArray[3]);
		ss.perTest = Integer.parseInt(histArray[4]);
		ss.totTest = Integer.parseInt(histArray[5]);
		
		if(ss.studMinutes != 0) {ss.studMinPrev = ss.studMinPrev + ss.studMinutes;}
		if(ss.perRev != 0) {ss.perRevPrev = ss.perRevPrev + ss.perRev;}
		if(ss.totRev != 0) {ss.totRevPrev = ss.totRevPrev + ss.totRev;}
		if(ss.perTest != 0) {ss.perTestPrev = ss.perTestPrev + ss.perTest;}
		if(ss.totTest != 0) {ss.totTestPrev = ss.totTestPrev + ss.totTest;}
		
		ss.dates.add(histArray[0]);
		
		if(ss.totRev != 0) {ss.countRev++;}
		if(ss.totTest != 0) {ss.countTest++;}
	}
	
	
	public static void calculateHistory(SessionState ss)
	{
		if(ss.countRev == 0) {ss.countRev = 1;}
		if(ss.countTest == 0) {ss.countTest = 1;}
		
		ss.first = ss.dates.get(0).toString();
		ss.last = ss.dates.get(ss.dates.size() - 1).toString();
		ss.perRev = ss.perRevPrev / ss.countRev;
		ss.perTest = ss.perTestPrev / ss.countTest;
	}


    // for
	
	
	// for DisplayLists.java
	

    public static String prntDLT3(SessionState ss)
    {
        String s ="";

        if(ss.threeCols)
        {
            int clspn1 = 3;
            int clspn2 = 6;
            s = "<td colspan="+clspn1+"><p class='study_word_right'>"+ss.exp.getExp27()+
					"</td><td class='center' valign='middle'>"+Selections.getSelect14(ss)+
                    "</p></td>"+Buttons.roundButton(Buttons.getBut22(ss), ss)+
                    "<tr><td colspan="+clspn2+">&nbsp;</td></tr>";
        }
        else
        {
            int clspn1 = 2;
            int clspn2 = 5;
            s = "<td colspan="+clspn1+"><p class='study_word_right'>"+ss.exp.getExp27()+
					"</td><td class='center' valign='middle'>"+Selections.getSelect14(ss)+
                    "</p></td>"+Buttons.roundButton(Buttons.getBut22(ss), ss)+
                    "<tr><td colspan="+clspn2+">&nbsp;</td></tr>";
        }

        return s;
    }


    public static String prntDLT4(SessionState ss)
    {
        String s = "";

        if(ss.threeCols)
        {
            s = "<tr><th>Type</th><th>English</th><th>Meaning</th><th>Reading</th><td></td></tr>";

            //for(WordBean wBean : ss.wordList)
            for(int y = 0; y < ss.wordList.size(); y++)
            {
                WordBean wBean = new WordBean();

                wBean = ss.wordList.get(y);
                String x = String.valueOf(y);
                String wbType = wBean.getTyp();

                if(ss.role.equals("teacher"))
                {
                    if(ss.prevList.equals("maybeKnowList") || ss.prevList.equals("knowList"))
                    { } // IMPORTANT: not sure how to use the Logical Negation (!) Operator with so many choices; ask Galt
                    else
                    {
                        if(wBean.id != -1)
                        {
                            ss.setTyp(wbType);
                            ss.setLists();
                            if(!ss.teachList.contains(wBean.rowID)) { ss.teachList.add(wBean.rowID); }
                        }
                    }
                }

                s = s+"<tr style='padding: 0.1cm 0.1cm'><td><p class='typ'>"+wBean.getTyp()+
                    "</p></td><td class='eng'>"+wBean.getEng()+
                    "</td><td class='kan'>"+wBean.getKan()+
                    "</td><td class='yom'>"+wBean.getYom()+
                    "</td><td align='center'><input type='checkbox' name='"+x+
                    "check'></td></tr><input type='hidden' name='wbType' value='"+wbType+"' >";

                ss.rnum++;
                    //ss.dbgApp("got here wbType = "+wbType); //debug
            }
        }
        else
        {
            s = "<tr><th>Type</th><th>English</th><th>Meaning</th><td></td></tr>";

            //for(WordBean wBean : ss.wordList)
            //{
                //String x = String.valueOf(wBean.rowID);
                //String wbType = wBean.getTyp();
            for(int y = 0; y < ss.wordList.size(); y++)
            {
                WordBean wBean = new WordBean();

                wBean = ss.wordList.get(y);
                String x = String.valueOf(y);
                String wbType = wBean.getTyp();


                s = s+"<tr style='padding: 0.1cm 0.1cm'><td><p class='typ'>"+wBean.getTyp()+
                    "</p></td><td class='eng'>"+wBean.getEng()+
                    "</td><td class='yom'>"+wBean.getKan()+
                    "</td><td align='center'><input type='checkbox' name='"+x+
                    "check'></td></tr><input type='hidden' name='wbType' value='"+wbType+"' >";
                
                ss.rnum++;
                    //ss.dbgApp("got here wbType = "+wbType); //debug
            }
        }
        
        return s;
    }


   	public static void handleDisp(SessionState ss)
	{
		ss.search = false;
		
		try
		{
			//ss.dbHand = new DBHandler(); // is this necessary?
			DBHandler.fetchListRows(ss); // fetches all of the rows in ss.table that are in ss.list, 
			// assigns the values to a WordBean, which is added to ss.wordList
		}
		catch(Exception ex) { ss.dbgApp("bkMeth.handleDisp Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		
		ss.message = Messages.getMess1(ss.prevList, ss);
	} // handles fetching ss.list rows and adding the wordbean to ss.wordList for display
	
	
	public static void handleDispMove(String moveID, SessionState ss)
	{
		int moveIDX = 0;
		
		ss.message = Messages.getMess24(moveID, ss);
		
		try { moveIDX = Integer.parseInt(moveID); } // 
		catch(NumberFormatException e) { System.err.println("bkMeth.handleDispMove NumberFormatException: "+ e.getMessage()); }
		
		ss.type = DBHandler.checkRowType(moveIDX, ss);
			//ss.dbgApp("got here DispMove 1: ss.type = "+ss.type+", ss.listGroup = "+ss.listGroup+", moveID = "+moveID); //debug
			
		ss.setLists();
			//ss.dbgApp("got here DispMove 2: ss.type = "+ss.type+", ss.typ = "+ss.typ); //debug
					
		if(ss.listGroup.equals("maybeList")) 
		{
			ss.listName = Lists.MAYBELIST.getListName(ss.getTyp());
			
			if(!ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.addToMapVal(ss.listName, moveID, ss); }
			
			ss.listName = Lists.KNOWLIST.getListName(ss.getTyp());
			
			if(ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.removeMapElem(ss.listName, moveID, ss); }
			
			ss.listName = Lists.MYLIST.getListName(ss.getTyp());
			
			if(ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.removeMapElem(ss.listName, moveID, ss); }
	
		}
		else if(ss.listGroup.equals("knowList")) 
		{
			ss.listName = Lists.KNOWLIST.getListName(ss.getTyp());
			
			if(!ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.addToMapVal(ss.listName, moveID, ss); }
			
			ss.listName = Lists.MAYBELIST.getListName(ss.getTyp());
			
			if(ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.removeMapElem(ss.listName, moveID, ss); }
			
			ss.listName = Lists.MYLIST.getListName(ss.getTyp());
			
			if(ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.removeMapElem(ss.listName, moveID, ss); }
	
		}
		else if(ss.listGroup.equals("myList")) 
		{
			ss.listName = Lists.MYLIST.getListName(ss.getTyp());
			
			if(!ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.addToMapVal(ss.listName, moveID, ss); }
			
			ss.listName = Lists.MAYBELIST.getListName(ss.getTyp());
			
			if(ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.removeMapElem(ss.listName, moveID, ss); }
			
			ss.listName = Lists.KNOWLIST.getListName(ss.getTyp());
			
			if(ss.mapKeyCheckSubKey(ss.listName, moveID, ss)) 
				{ ss.removeMapElem(ss.listName, moveID, ss); }
	
		}
		
					//ss.dbgApp("got here DispMove 3: ss.type = "+ss.type+", ss.typ = "+ss.typ); //debug
		ss.setTypes();
		
					//ss.dbgApp("got here DispMove 4: ss.type = "+ss.type+", ss.typ = "+ss.typ); //debug

        DBHandler.saveState(ss);
        /* this seems to not need a catch
		try { DBHandler.saveState(ss); }
		catch(SQLException e) {System.err.println("bkMeth.handleDispMove SQLException: " +e.getMessage());}
		catch(Exception e) {System.err.println("bkMeth.handleDispMove Exception: " +e.getMessage());}
		*/
					//ss.dbgApp("got here DispMove 5: ss.type = "+ss.type+", ss.typ = "+ss.typ); //debug
	} // adds the chosen radio row to the correct list (knowlist or maybelist) of the correct type,
		// removes the row from the other list, and saves it all.
	
	
	
	// for GetSessionState.java	
	
	
	// Handles a sampler
	public static boolean handleSample(String set, String typ, SessionState ss)
	{
		try
		{
				//ss.dbgApp("got here handleSample 1: set/vocList = "+set); //debug
				//ss.dbgApp("got here handleSample 2: type = "+typ); //debug
			
			handleSet(set, typ, ss);
			DBHandler.addWordSets(ss);
			
			ss.loggedIn = true;
			
			return true;
		}
		catch(Exception e) {System.err.println("bkMeth.handleSample Exception: " +e.getMessage()); return false;}
	}
	
	// for LearningOptions
	
	//
   	public static void handleSet(String vocList, String typ, SessionState ss)
	{
			//ss.dbgApp("got here handleSet A: typ = "+typ); //debug
			//ss.dbgApp("got here handleSet B: vocList = "+vocList); //debug
			
		if(typ != null)
		{
			Selections.setTypList(typ, ss); // sets ss.type, ss.typ and ss.prevType
				//ss.dbgApp("got here handleSet 1: ss.typ = "+ss.typ); //debug
				//ss.dbgApp("got here handleSet 2: ss.type = "+ss.type); //debug
				//ss.dbgApp("got here handleSet 3: ss.prevType = "+ss.prevType); //debug
				//ss.dbgApp("got here handleSet 3A: ss.prevSelect = "+ss.prevSelect); //debug
			
		
		}
		
		if(vocList != null)
		{
			Selections.setPrevSelect(vocList, ss); // takes the vocList, determines if in Lists or is a Chapter, and sets ss.listGroup and ss.preSelect or ss.listName
		// Also sets columns for ss.japanese
				//ss.dbgApp("got here handleSet 3B: ss.prevSelect = "+ss.prevSelect); //debug
				//ss.dbgApp("got here handleSet 4: ss.whereCol = "+ss.whereCol); //debug
				//ss.dbgApp("got here handleSet 5: ss.whereVal = "+ss.whereVal); //debug
			
		}
		
		ss.setTypes(); // sets the type for myList, maybeList, knowList, etc.
	}
	
	
	
	// Clears the HashMap Elem out of ss.map at the appropriate key through ss.clearMapElem(key, ss);
	// also updates the appropriate row in table lists, setting the csvlist to ""
	public static void handleClear(String clearList, String typ, SessionState ss)
	{
		if(typ != null)
		{
			Selections.setTypList(typ, ss);
		}
		
		if(clearList != null)
		{
			Selections.setSSList(clearList, ss);
		}
		
		for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
		{
			String name = list.getName();
			
				//ss.dbgApp("ss.bkMeth.handleClear A: listGroup = "+name); //debug
			if(name.equals(ss.listGroup))
			{
				String key = list.getListName(ss.type);
					//ss.dbgApp("ss.bkMeth.handleClear B; listGroup found: key = "+key); //debug
				ss.clearMapElem(key, ss);
				return;
			}
		}
		
		ss.setTypes();
		
		ss.message = Messages.getMess23(clearList, ss);
	}
	
	
	
	// for EditListNames.java


    public static String prntChapNmLst(SessionState ss)
    {
        String s = "";

		TreeSet<String> tree = new TreeSet<String>();
		Set< String > mapKeys = ss.chapNameList.keySet();

		for(String chap : mapKeys)
		{
			tree.add(chap);
		}

		for(String c : tree)
		{
			String x = String.valueOf(ss.chapNameList.get(c));

			s = s+"<tr><td><input type = 'text' style='width: 150px' maxlength='20' name = '"+x+"' value = \""+c+"\"/>"+
                "</td><td align='center'><input type = 'radio' name = 'radio' value = '"+x+"'></td></tr>";
		}

		// s = s+"<tr><td colspan='2'>"+ss.chapterList+"</td></tr>";

        return s;
    }
	
	
	public static void addNameToList(SessionState ss)
	{
		if(ss.chapNameList.containsKey((String)ss.chapter))
		{
			ss.message = Messages.getMess33(ss);
			
			return;
		}
		else
		{
			DBHandler.insertList(ss.userID, ss.chapter, "chap", "", ss);
			
			if(!ss.role.equals("admin"))
			{
				DBHandler.fetchChapNames(ss);
			}
				//ss.dbgApp("got to addNameToList 1: ss.chapNameList = "+ss.chapNameList); //debug
			
			ss.message = Messages.getMess34(ss);
				//ss.dbgApp("got to addNameToList 2: ss.chapNameList = "+ss.chapNameList+", ss.chapterList = "+ss.chapterList); //debug
		}
	}
	
	
	public static void editChapList(String listID, SessionState ss)
	{
		if(ss.chapNameList.containsKey((String)ss.chapter))
		{
			ss.message = Messages.getMess33(ss);
			
			return;
		}
		else
		{
			DBHandler.updateDBListName(ss.userID, ss.chapter, listID, ss);
			
			if(!ss.role.equals("admin"))
			{
				DBHandler.fetchChapNames(ss);
			}
				//ss.dbgApp("got to addNameToList 1: ss.chapNameList = "+ss.chapNameList); //debug
			
			// GET THE RIGHT MESSAGE
			//ss.message = ss.mess.getMess34(ss);
				//ss.dbgApp("got to addNameToList 2: ss.chapNameList = "+ss.chapNameList+", ss.chapterList = "+ss.chapterList); //debug
		}
	}
	
	
	
	// for EditTLists.java


    public static String prntETL(SessionState ss)
    {
        String s = "";

        for(int y = 0; y < ss.wordList.size(); y++)
        {
            WordBean wb = new WordBean();

            wb = ss.wordList.get(y);

            String x = String.valueOf(y);
            ss.typSel = x;
            ss.prevType = wb.typ;
            //ss.bkMeth.setPrevType(wb.typ, ss);

            s = s+"<tr><td><p align='center'>"+wb.rowID+"</p><input type='hidden' name='"+x+"ID' value='"+wb.rowID+
                "'/><td class='center'>"+Selections.getSelect19(ss)+
                "</td><td><input type='text' style='width:140px' name='"+x+"eng' value=\""+wb.eng+"\"/>"+
                "</td><td><input type='text' style='width:140px' name='"+x+"kan' value=\""+wb.kan+"\"/>"+
                "</td><td><input type='text' style='width:140px' name='"+x+"yom' value=\""+wb.yom+"\"/></td>"+
                "<td align='center'><input type='checkbox' name='"+x+"check'></td></tr>";
        }

        return s;
    }

	
	// probably not being used
	public static void setPrevType(String typ, SessionState ss)
	{
		if(typ.equals("verb")) ss.prevType = "Verbs";
		else if(typ.equals("noun")) ss.prevType = "Nouns";
		else if(typ.equals("adjective")) ss.prevType = "Adjectives";
		else if(typ.equals("misc")) ss.prevType = "Misc";
		else ss.prevType = typ;
	}
	
	
	
	// for EditRows.java / EditLists.java


    public static String prntELSel(SessionState ss)
    {
        String s = "";

        s = "<tr><td colspan='3'><p class='study_word'>"+ss.exp.getExp40()+"</p></td>";

        // put in translations
        if(ss.role.equals("teacher"))
        {
            s = s+"<td class='center' ><p>"+Selections.getSelect4(ss)+"</p></td>"+
                Buttons.roundButton(Buttons.getBut3(ss), ss)+"</tr>";

            s = s+"<tr><td colspan='2'><p>Language: "+ss.table+" </p></td><td><p> List Size: "+ss.wordList.size()+
                "</p></td><td colspan='3' align='right'>"+ss.message+"</td></tr>";

            //s = s+"<tr><td></td><td colspan='5'><p class='study_word'>"+ss.exp.getExp61(ss)+"</p></td></tr>");

            s = s+"<tr><td colspan='3'><p class='study_word'>"+ss.exp.getExp41()+"</p></td>";

            s = s+"<td colspan='3'><p class='study_word'>"+ss.exp.getExp42()+"</p></td></tr>";
        }
        else if(ss.role.equals("admin"))
        {
            s = "<td class='center'><p>"+Selections.getSelect2(ss)+"</p></td><td class='center'><p>"+Selections.getSelect4(ss)+"</p></td>"+
                Buttons.roundButton(Buttons.getBut3(ss), ss)+"</tr>";

            s = s+"<tr><td colspan='2'><p>Language: "+ss.table+"</p></td><td><p>List Size: "+ss.wordList.size()+
                "</p></td><td colspan='3' align='right'>"+ss.message+"</td></tr>";

            s = s+"<tr><td></td><td colspan='5'><p class='study_word'>"+ss.exp.getExp41A()+"</p></td></tr>";
        }

        return s;
    }


    public static String prntELChoice1(SessionState ss)
    {
        String s = "";

			s = "<tr><td>"+ss.exp.getExp63()+"</td><td>&nbsp;</td>"+
                Buttons.roundButton(Buttons.getBut4(ss), ss)+
				Buttons.roundButton(Buttons.getBut5(ss), ss)+
                Buttons.roundButton(Buttons.getBut24(ss), ss)+
                "<td>&nbsp;</td></tr>";

        return s;
    }


    public static String prntELChoice2(SessionState ss)
    {
        String s = "";

        s = "<tr><td align='center'>"+ss.selection+"</td><td align='center'><p>"+Selections.getSelect2(ss)+"</p></td>"+
                "<td align='center'><input type = 'text' style='width:100px' maxlength='30' name='eng' value = \""+ss.eng+"\" /></td>"+
                "<td align='center'><input type = 'text' style='width:100px' maxlength='30' name='kan' value = \""+ss.kan+"\" /></td>";

        if(ss.threeCols)
        {
            s = s+"<td align='center'><input type = 'text' style='width:100px' maxlength='30' name='yom' value = \""+ss.yom+"\" /></td>";
        }

        s = s+"<td align='center'>"+ss.listName+"</td></tr>";

        return s;
    }


    public static String prntELRad(SessionState ss)
    {
		int i = 0;
        String s = "";

        if(ss.language.equals("japanese"))
        {
            s = "<tr><th>ID</th><th>Type</th><th>English</th><th>Kanji</th>";

            if(ss.threeCols)
            {
                s = s+"<th>Yomi</th>";
            }
        }
        else
        {
            s = "<tr><th>ID</th><th>Type</th><th>English</th><th>"+ss.language+"</th>";
        }

        s = s+Buttons.roundButton(Buttons.getBut6(ss), ss)+"</tr>";

        if(ss.role.equals("admin"))
        {
            ss.hList = "";

            for(int n = ss.wordList.size()-1; n >= 0; n--)
            {
                WordBean wBean = new WordBean();

                wBean = ss.wordList.get(n);

                String x = String.valueOf(wBean.getID());

                s = s+"<tr style='padding: 0.1cm 0.1cm'>";

                if(wBean.edit == 0) { s = s+"<td class='id'>"+wBean.getID()+"</td>"; }
                else { s = s+"<td class='id2'>"+wBean.getID(); }

                s = s+"<td class='typ'>"+wBean.getTyp()+"</td>";
                s = s+"<td class='eng'>"+wBean.getEng()+"</td>";
                s = s+"<td class='kan'>"+wBean.getKan()+"</td>";

                if(ss.threeCols)
                {
                    s = s+"<td class='yom'>"+wBean.getYom()+"</td>";
                }

                s = s+"<td align='center'><input type = radio name = 'radio' value = '"+x+"'></td></tr>";

                ss.hList = ss.hList+x+",";

                i++;
            }

            //if(!(ss.prevSelect.equals("Vocab 1")) || (ss.prevSelect.equals("vocab1"))) bkMeth.updateHartList(ss;
        }
        else if(ss.role.equals("teacher"))
        {
            // IMPORTANT: change this as teachlist is gone
            //ss.dbgApp("got to ER addToTList 1: ss.teachListVerbs = "+ss.teachListVerbs+", ss.teachListNouns = "+ss.teachListNouns+", ss.teachListAdjs = "+ss.teachListAdjs+", ss.teachListMisc = "+ss.teachListMisc; //debug

            for(int n = ss.wordList.size()-1; n >= 0; n--)
            {
                WordBean wBean = new WordBean();

                wBean = ss.wordList.get(n);

                String x = String.valueOf(wBean.getID());

                if(wBean.id != -1 && wBean.id != 0)
                {
                    String wbType = wBean.getTyp();

            // IMPORTANT: change this as teachlist is gone
            //ss.dbgApp("got to ER addToTList 1A: ss.type = "+ss.type; //debug

                    ss.setTyp(wbType);
                    ss.setLists();
            // IMPORTANT: maybe need this but probably don't!!!
                    if(!ss.teachList.contains(wBean.rowID)) { ss.teachList.add(wBean.rowID); }
                }

                s = s+"<tr style='padding: 0.1cm 0.1cm'>";

                if(wBean.edit == 0) { s = s+"<td class='id'>"+wBean.getID()+"</td>"; }
                else { s = s+"<td class='id2'>"+wBean.getID()+"</td>"; }

                s = s+"<td class='typ'>"+wBean.getTyp()+"</td>";
                s = s+"<td class='eng'>"+wBean.getEng()+"</td>";
                s = s+"<td class='kan'>"+wBean.getKan()+"</td>";

                if(ss.threeCols)
                {
                    s = s+"<td class='yom'>"+wBean.getYom()+"</td>";
                }

                s = s+"<td align='center'><input type = radio name = 'radio' value = '"+x+"'></td></tr>";

                i++;
            }

            // IMPORTANT: change this as teachlist is gone
            //ss.dbgApp("got to ER addToTList 2: ss.teachListVerbs = "+ss.teachListVerbs+", ss.teachListNouns = "+ss.teachListNouns+", ss.teachListAdjs = "+ss.teachListAdjs+", ss.teachListMisc = "+ss.teachListMisc; //debug

        }

        return s;
    }

	
	public static void createHartList(String x, SessionState ss)
	{
		String s = x+",";
		
		ss.hartList.add(s);
	}
	
	
	// maybe won't use (if teachers move row to another list before removing from first one, teachList will not have it at all)
	public static void handleRemove(SessionState ss) 
	{
		ss.type = ss.typ;
		ss.setLists();
		
		for(String y : ss.teachList)
		{
			int d = Integer.parseInt(y);
			
			if(d == ss.selection) 
				ss.teachList.remove(d);
		}
	}
	
	
	
	// for examp_sent_popup.jsp
	public static void checkExamp(String examp, SessionState ss)
	{
		
	}

    // for review.jsp

    public static String deBugReview(SessionState ss)
    {
        String dbg = "empty";

		/* debugging stuff
		for(int i = 0; i < ss.a.length; i++)
		{
			dbg = dbg+"<p>"+i+" "+ss.a[i].getEng()+" "+ss.a[i].getKan()+
				" "+ss.a[i].getYom()+"</p>";
		}

		dbg = dbg+"<p>Hide List</p>");
		while(tempHideList2Iter.hasNext())
	   {
		   WordBean hBean = new WordBean();
		   hBean = (WordBean) tempHideList2Iter.next();
		   dbg = dbg+"<p>"+f+" "+hBean.getID()+" "+hBean.rowID+" "+hBean.getEng()+" "+hBean.getKan()+" "+hBean.getYom()+"</p>";
		   f++;
		}

		dbg = dbg+"<p>Test List</p>";
		while(useListIter.hasNext())
	   {
		   WordBean tBean = new WordBean();
		   tBean = (WordBean) useListIter.next();
		   tBean.setID(listCount);
		   dbg = dbg+"<p>"+tBean.getID()+" "+tBean.rowID+" "+tBean.getEng()+" "+tBean.getKan()+" "+tBean.getYom()+"</p>";
		   listCount++;
		}

		dbg = dbg+"<p>Type: "+ss.type+"</p>";
		dbg = dbg+"<p>MyList: "+ss.myList+"</p>";
		dbg = dbg+"<p>MaybeList: "+ss.maybeList+"</p>";
		dbg = dbg+"<p>KnowList: "+ss.knowList+"</p>";
		*/

        return dbg;
    }


    // for EditTables


    public static String prntETButts(SessionState ss)
    {
        String s = "";

			s = "<tr><td><p>"+ss.radioID+" "+ss.tableRow.size()+"</p></td>"+Buttons.roundButton(Buttons.getBut33(ss), ss)+
				Buttons.roundButton(Buttons.getBut5(ss), ss)+Buttons.roundButton(Buttons.getBut4(ss), ss)+"<td>&nbsp;</td></tr>"+
                "<tr>"+DBHandler.getInputs(ss)+"</tr>";

        return s;
    }


    public static String prntETRadio(SessionState ss)
    {
        String s = "";

        s = "<tr>"+DBHandler.getHeads(ss)+Buttons.roundButton(Buttons.getBut6(ss), ss)+"</tr>";

        if(ss.tableRow != null || !ss.tableRow.isEmpty())
        {
            for(TableRow tr : ss.tableRow)
            {
                s = s+"<tr style='padding: 0.1cm 0.1cm'>"+DBHandler.getDispCols(tr, ss)+
                    "<td align='center'><input type = radio name = 'radio' value = '"+tr.cols[0]+"'></td></tr>";
            }
        }

        return s;
    }

    public static boolean checkC(String c)
    {
        if(c.equals("c"))
            return true;
        else
            return false;
    }


    // for test.jsp


    // probably do not need as handled in Test.java
    public static void getStats(SessionState ss)
    {
		if(!ss.langSet)
		{
			ss.round++;

			ss.rightGuessTest = ss.ansList.size() - ss.checkList.size();
			ss.totalCorrectTest = ss.totalCorrectTest + ss.rightGuessTest;
			ss.totalPossibleTest = ss.totalPossibleTest + ss.ansList.size();

			if(ss.numOfRounds == 0) {ss.round = 1;}
			else if(ss.totalPossibleTest != 0) {ss.percentCorrectTest = ((ss.totalCorrectTest * 100) / ss.totalPossibleTest);}
		}
    }

    public static String checkRound(SessionState ss)
    {
        String chk = "";
		if(ss.round >= 1) {chk = "<h4 class='section_header'>"+ss.exp.getExp24(ss)+"</h4>";}
        return chk;
    }

    public static String getTempList(SessionState ss)
    {
        String tmp = "";

		for(WordBean wb : ss.tempList)
		{
			if(ss.getMeaning().equals("kanE") || ss.getMeaning().equals("yomE"))
			{
				if(wb.typ.equals("verb"))
				{
					tmp = tmp+"<tr><td><h2 class='study_word2'>to <input type='text' name='"+wb.eng+"' size='30' maxlength='30' /></h2></td></tr>";
				}
				else
				{
					tmp = tmp+"<tr><td><input type='text' name='"+wb.eng+"' size='30' maxlength='30' /></td></tr>";
				}
			}
			else if(ss.getMeaning().equals("Ekan") || ss.getMeaning().equals("Eyom"))
			{
				tmp = tmp+"<tr><td><input type='text' name='"+wb.yom+"' size='30' maxlength='30' /></td></tr>";
			}
			/*else if(ss.getMeaning().equals("yk"))
			{
				tmp = tmp+"<tr><td><input type='text' name='"+wb.kan+"' size='30' /></td></tr>"; break;
			}
			else if(ss.getMeaning().equals("ky"))
			{
				tmp = tmp+"<tr><td><input type='text' name='"+wb.yom+"' size='30' /></td></tr>"; break;
			}*/
		}

        return tmp;
    }


    public static String getAnsList(SessionState ss)
    {
        String ans = "";

		for(WordBean wb : ss.ansList)
		{
			if(ss.threeCols)
			{
				ans = ans+"<tr><td><p class='study_word2'>["+wb.eng+ss.exp.getExp26A()+wb.kan+" ("+wb.yom+")]</p></td></tr>";
			}
			else
			{
				ans = ans+"<tr><td><p class='study_word2'>["+wb.eng+ss.exp.getExp26A()+wb.kan+"]</p></td></tr>";
			}

		}

        return ans;
    }


    public static String getResList(SessionState ss)
    {
        String res = "";

		for(String s : ss.resList) {res = res+"["+s+"]";}

        return res;
    }

	// for the Learning.java
	
	
	public static void handleLearn(String learn, SessionState ss)
	{
		ss.numOfRounds = 0;
		
		if(learn.equals("Start Study") || learn.equals("勉強スタート")) 
        {
            ss.learn = "study";
            ss.postPage = "study.jsp";
        }
		else if(learn.equals("Start Review") || learn.equals("復習スタート")) 
        {
            ss.learn = "review";
            ss.postPage = "review.jsp";
        }
		else if(learn.equals("Start Test") || learn.equals("テストスタート")) 
        {
            ss.learn = "test";
            ss.postPage = "test.jsp";
        }
	}
	
	
   	public static void handleDisplay(SessionState ss) 
	{
		ss.message = Messages.getMess11(ss);
		
		Selections.setSSList(ss.displayList, ss); // sets ss.list to the chosen list
		
		startLearning(ss); // decides which style of learning (study, review, test) and sets the related variables
		
	} // takes the chosen list and uses it for the learning selections
    
	
   	public static void handleChaps(SessionState ss) 
	{
		ss.message = Messages.getMess11(ss);
		
		ss.setLists();
		
		ss.list = StringHandler.setArrayListInteger(ss.chapter, ss.chapterList, ss);
		
		startLearning(ss); // decides which style of learning (study, review, test) and sets the related variables
	}


    // for study.jsp


    public static String prntStdy1(SessionState ss)
    {
        String s = "";

        for(int i = 0; i < ss.tempList.size(); i++)
        {
            WordBean wb = ss.tempList.get(i);

		// for(WordBean wb : ss.tempList)
		// {"+ss.html.getRollOverVar(i, ss)+"
			if(ss.getMeaning().equals("kanE") || ss.getMeaning().equals("yomE"))
			{
				s = s+"<tr><td onmouseover='mouseOver("+ss.html.getRollOverVar(i, ss)+")' onmouseout='mouseOut()' ><h2 class='study_word2' >"+wb.eng+"</h2></td></tr>";
			}
			else if(ss.getMeaning().equals("Ekan") || ss.getMeaning().equals("Eyom"))
			{
				if(ss.threeCols)
				{
					s = s+"<tr><td onmouseover='mouseOver("+ss.html.getRollOverVar(i, ss)+")' onmouseout='mouseOut()' ><h2 class='study_word2' >"+wb.kan+" ( "+wb.yom+" )</h2></td></tr>";
				}
				else
				{
					s = s+"<tr><td onmouseover='mouseOver("+ss.html.getRollOverVar(i, ss)+")' onmouseout='mouseOut()' ><h2 class='study_word2' >"+wb.kan+"</h2></td></tr>";
				}
			}
			/*else if(ss.getMeaning().equals("yk"))
			{
				s = s+"<tr><td><h2 class='study_word2'>"+wb.kan+"</h2></td></tr>"; break;
			}
			else if(ss.getMeaning().equals("ky"))
			{
				s = s+"<tr><td><h2 class='study_word2'>"+wb.yom+"</h2></td></tr>"; break;
			}*/
		}

        return s;
    }


    public static String prntStdy2(SessionState ss)
    {
        String s = "";

		for(WordBean wb : ss.tempList)
		{
			if(ss.threeCols)
			{
				s = s+"<tr><td><p>"+wb.eng+" "+wb.kan+" "+wb.yom+"</p></td></tr>";
			}
			else
			{
				s = s+"<tr><td><p>"+wb.eng+" "+wb.kan+"</p></td></tr>";
			}
		}

        return s;
    }


	
	// IMPORTANT: THIS REALLY NEEDS TO BE POLISHED UP; SEEMS TO REPEAT A NUMBER OF STEPS OR MAKE SOME USELESS ONES
	public static void handleStudy(SessionState ss) throws ServletException
	{
		WordBean wBean = new WordBean();

        ss.numOfRounds++;

		if(ss.numOfRounds < ss.useList.size()) 
		{
			for(WordBean wb : ss.tempList) // ss.tempList was created in Options.java
			{ // IMPORTANT: Why are we running through this every time? Because it is a short list of rows that share a meaning.
				if(!ss.tempStudyList.contains(wb.rowID)) {ss.tempStudyList.add(wb.rowID);}

                if(!ss.tempStudyList2.contains(wb.rowID)) {ss.tempStudyList2.add(wb.rowID);}
                // tempStudyList2 is used to set doNotUseList in DBHandler.selectList
				// IMPORTANT: Then, where is doNotUseList used?
			}

            // This next part was moved up from the lower redundant(?) parts.

            wBean = ss.useList.get(ss.numOfRounds);

            setStudy(wBean, ss); // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom


			// maybe this should be randomized :: done
			// proably donot need the if part, so removed.
            // if(ss.numOfRounds < ss.useList.size()) {wBean = ss.useList.get(ss.numOfRounds);}

            // IMPORTANT: it looks like we are incrementing unnecessarily; try to remove.
            /*
			if(ss.tempStudyList.contains(wBean.rowID) && ss.numOfRounds < ss.useList.size())
			{
				ss.numOfRounds++;
				if(ss.numOfRounds < ss.useList.size()) {wBean = ss.useList.get(ss.numOfRounds);}
			}
			*/

            // IMPORTANT: This next part looks redundant, so removed
            /*
			if(ss.numOfRounds < ss.useList.size()) 
			{
				setStudy(wBean, ss); // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
			}
			else 
			{
				ss.message = Messages.getMess12(ss);
				
				ss.numOfRounds = 0;
				ss.tempStudyList.clear();
				wBean = ss.useList.get(ss.numOfRounds);
				setStudy(wBean, ss); // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
			}
            */
		}
		else 
		{
			ss.message = Messages.getMess12(ss);
				
			ss.numOfRounds = 0;
			ss.tempStudyList.clear();
			wBean = ss.useList.get(ss.numOfRounds);
			setStudy(wBean, ss); // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
		}
	}
	
	
	public static void handleTest(HttpServletRequest req, SessionState ss) throws ServletException
	{
		ss.resList.clear();
		ss.checkList.clear();
		
		if(ss.getMeaning().equals("kanE") || ss.getMeaning().equals("yomE"))
		{
			for(WordBean wb : ss.tempList) 
			{
				String eng = StringHandler.adjustString(req.getParameter(wb.eng));
				
				if(wb.typ.equals("verb")) { eng = StringHandler.addTo(eng, ss); }
				
				ss.resList.add(eng);
				ss.checkList.add(eng);
			}
			
			for(WordBean wb : ss.tempList) // what is this for? do I need it?
			{ if(ss.checkList.contains(wb.eng)) { ss.checkList.remove(wb.eng); } }
		
			ss.ansList = ss.tempList;
		}
		else if(ss.getMeaning().equals("Ekan") || ss.getMeaning().equals("Eyom"))
		{
			for(WordBean wb : ss.tempList) 
			{
				String yom = StringHandler.adjustString(req.getParameter(wb.yom));
				
				ss.resList.add(yom);
				ss.checkList.add(yom);
			}
			for(WordBean wb : ss.tempList) 
			{if(ss.checkList.contains(wb.yom)){ss.checkList.remove(wb.yom);}}
		
			ss.ansList = ss.tempList;
		}
		
		
		WordBean wBean = new WordBean();
		
		if(ss.numOfRounds < ss.useList.size()) 
		{
			ss.numOfRounds++;
			
			for(WordBean wb : ss.tempList) 
			{
				if(!ss.tempTestList.contains(wb.rowID)) 
				{
					ss.tempTestList.add(wb.rowID);
					ss.tempTestList2.add(wb.rowID);
				}
			}
			
			if(ss.numOfRounds < ss.useList.size()) {wBean = ss.useList.get(ss.numOfRounds);}
			
			while(ss.tempTestList.contains(wBean.rowID) && ss.numOfRounds < ss.useList.size()) 
			{
				ss.numOfRounds++;
				if(ss.numOfRounds < ss.useList.size()) {wBean = ss.useList.get(ss.numOfRounds);}
			}
			
			if(ss.numOfRounds < ss.useList.size()) {setStudy(wBean, ss);} // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
			else 
			{
				ss.message = Messages.getMess13(ss);
				
				ss.numOfRounds = 0;
				ss.tempTestList.clear();
				wBean = ss.useList.get(ss.numOfRounds);
				setStudy(wBean, ss); // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
			}
		}
		else 
		{
			ss.message = Messages.getMess13(ss);
			
			ss.numOfRounds = 0;
			ss.tempTestList.clear();
			wBean = ss.useList.get(ss.numOfRounds);
			setStudy(wBean, ss); // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
		}
	}


    // for review.jsp


    public static String prntRvw1A(SessionState ss)
    {
        String s = "";

		if(ss.numOfRounds != 0)
		{
			if(ss.correct)
			{
                s = "<p class='p4'>"+ss.exp.getExp16()+"</p>";
			}
			else
			{
				s = "<p class='p5'>"+ss.exp.getExp18()+"</p>";
			}
		}

        return s;
    }


    public static String prntRvw1B(SessionState ss)
    {
        String s = "";

		if(ss.numOfRounds != 0)
		{
			if(ss.correct)
			{
				if(ss.language.equals("japanese"))
				{
					s = "<p class='study_word_lg'>\""+ss.wBean.getEng()+
						"\"  "+ss.wBean.getKan()+" ( "+ss.wBean.getYom()+" )</p>";
				}
				else
				{
					s = "<p class='study_word_lg'>\""+ss.wBean.getEng()+
						"\"  "+ss.wBean.getKan()+"</p>";
				}
			}
			else
			{
				s = "<p class='study_word_lg'>"+ss.exp.getExp19(ss)+"</p>";
			}
		}

        return s;
    }


    public static String prntRvw2(SessionState ss)
    {
        String s = "";

		if(ss.wBean.getID() == -1)
		{
			s = "<!-- User ID, Right Cell --><td class='center'></td><!-- Drop Box/Button, Center Cell --><td class='center'></td>"; // valign='middle'
		}
		else
		{
			s = "<!-- User ID, Right Cell --><td width='230' class='center'><p class='p'>"+ss.exp.getExp21()+
				"</p><p class='smalltext'>"+ss.exp.getExp20(ss)+"</p></td>"; // </tr><tr>

			s = s+"<!-- Drop Box/Button, Center Cell --><td class='center'><table cellpadding='3' cellspacing='0' border='0'><tr>"+
				Buttons.roundButton(Buttons.getBut19(ss), ss)+"</tr><tr><td>"+Selections.getMoveSet(ss)+"</td></tr></table></td>";
		}

        return s;
    }


	
	public static void handleMove(String moveset, SessionState ss)
	{
		if(ss.useList.size() == 0) { return; }
		else if(ss.justHid) { return; }
		else
		{
			ss.pid = ss.getAnsID();
			ss.wBean.setHid(moveset); // sets WordBean.mov variable with name of the list to be moved to.
			
			String tempType = ss.type;
			
			ss.type = ss.wBean.typ;
			
			ss.setLists();
			
			ss.message = Messages.getMess14A(ss);
			
			if(moveset.equals("maybeList"))
			{
				ss.message = Messages.getMess14(ss);
				
				try 
				{
					ss.listName = Lists.MAYBELIST.getListName(ss.ansBean.getTyp());
					
					if(!ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.addToMapVal(ss.listName, ss.wBean.rowID, ss); }
					
					ss.listName = Lists.KNOWLIST.getListName(ss.ansBean.getTyp());
					
					if(ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.removeMapElem(ss.listName, ss.wBean.rowID, ss); }
					
					ss.listName = Lists.MYLIST.getListName(ss.ansBean.getTyp());
					
					if(ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.removeMapElem(ss.listName, ss.wBean.rowID, ss); }
				}
				catch(Exception ex) { ss.dbgApp("bkMeth.handleMove Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
			}
			else if(moveset.equals("knowList"))
			{
				ss.message = Messages.getMess14(ss);
				
				try 
				{
					ss.listName = Lists.KNOWLIST.getListName(ss.ansBean.getTyp());
					
					if(!ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.addToMapVal(ss.listName, ss.wBean.rowID, ss); }
					
					ss.listName = Lists.MAYBELIST.getListName(ss.ansBean.getTyp());
					
					if(ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.removeMapElem(ss.listName, ss.wBean.rowID, ss); }
					
					ss.listName = Lists.MYLIST.getListName(ss.ansBean.getTyp());
					
					if(ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.removeMapElem(ss.listName, ss.wBean.rowID, ss); }
				}
				catch(Exception ex) { ss.dbgApp("bkMeth.handleMove Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
			}
			else if(moveset.equals("myList"))
			{
				ss.message = Messages.getMess14(ss);
				
				try 
				{
					ss.listName = Lists.MYLIST.getListName(ss.ansBean.getTyp());
					
					if(!ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.addToMapVal(ss.listName, ss.wBean.rowID, ss); }
					
					ss.listName = Lists.MAYBELIST.getListName(ss.ansBean.getTyp());
					
					if(ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.removeMapElem(ss.listName, ss.wBean.rowID, ss); }
					
					ss.listName = Lists.KNOWLIST.getListName(ss.ansBean.getTyp());
					
					if(ss.mapKeyCheckSubKey(ss.listName, ss.wBean.rowID, ss)) 
						{ ss.removeMapElem(ss.listName, ss.wBean.rowID, ss); }
				}
				catch(Exception ex) { ss.dbgApp("bkMeth.handleMove Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
			}
			
			ss.setTypes();
			
			ss.type = tempType;
			
			if(!ss.tempHideList.contains(ss.wBean.rowID))
			{
				ss.tempHideList.add(ss.wBean.rowID);
				ss.tempHideList2.add(ss.wBean);
				if(ss.useList.size() != 0 || ss.useList != null) { ss.useList.remove((WordBean)ss.wBean); }
					//ss.dbgApp("bkMeth.handleMove removed "+ss.wBean+" from ss.useList; ss.useList.size() = "+ss.useList.size());
					
				for(int i = 0; i < ss.useList.size(); i++)
				{
					WordBean tBean = ss.useList.get(i);
					tBean.setID(i);
				}
				ss.hideSize++;
				ss.justHid = true;
			}
		}
	}
	
	
	public static void handleForm(SessionState ss)
	{
		ss.justHid = false;
		
		ss.wBean = ss.ansBean;
	
		if(ss.guess.equals(ss.getAns()))
		{
			ss.correct = true;
			ss.rightGuessRev++;
		}
		else
		{
			ss.correct = false;
			ss.wrongGuessRev++;
		}
		
		setArray(ss); // sets the value of the 6 elements in ss.b array (buttons) from the wordBeans
		setButtons(ss); // sets the values of the 6 buttons, ss.setAns(), ss.setAnsID(), 
			// ss.setEngAns(), ss.setKanAns(), ss.setYomAns(), ss.ansBean and adds the answer to the answer array (ss.a)
		
		ss.numOfRounds++;
		ss.totalPossibleRev++;
	}// Handles the user's choice from the form. Also sets the DisplayGuess variables.
	
	
	private static void startLearning(SessionState ss)
	{
		try
		{
			//DBHandler.saveState(ss);
			//ss.wholeList
			DBHandler.selectList(ss);
			
			if(ss.learn.equals("study")) 
			{
				ss.numOfRounds = 0;
				setUseList(ss); // creates ss.useList from ss.wordList
                if(!ss.wdlist) return;

				WordBean wb = ss.useList.get(ss.numOfRounds);
				setStudy(wb, ss); // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
			}
			else if(ss.learn.equals("review")) 
			{
				setUseList(ss); // creates ss.useList from ss.wordList
                if(!ss.wdlist) return;

				setArray(ss); // sets the value of the 6 elements in ss.b array from the wordBeans
				setButtons(ss); // sets the values of the 6 buttons, ss.setAns(), ss.setAnsID(), 
					// ss.setEngAns(), ss.setKanAns(), ss.setYomAns(), ss.ansBean and adds the answer to the answer array (ss.a)
			}
			else if(ss.learn.equals("test")) 
			{
				ss.numOfRounds = 0;
				setUseList(ss); // creates ss.useList from ss.wordList
                if(!ss.wdlist) return;

				if(ss.useList.size() == 0)
				{
						//ss.dbgApp("got to startLearning for Test: ss.useList.size() = "+ss.useList.size()); //debug
					ss.message = Messages.getMess17(ss);
				}
				else
				{
					WordBean wb = ss.useList.get(ss.numOfRounds);
					setStudy(wb, ss); // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
				}
			}
		}
		catch(ServletException ex) { ss.dbgApp("bkMeth.startLearning ServletException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		catch(Exception ex) { ss.dbgApp("bkMeth.startLearning Exception: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		// catch(SQLException ex) { ss.dbgApp("bkMeth.startLearning SQLException: " + ex.getMessage()); ex.getStackTrace(); ex.printStackTrace(); }
		ss.hideSize = 0;
	} // decides which style of learning (study, review, test) and sets the related variables
	
	
	private static void setUseList(SessionState ss)
	{
		ss.useList = new ArrayList<WordBean>();
			//ss.dbgApp("got to setUseList 1: ss.wordList.size() = "+ss.wordList.size());
			
		if(ss.wordList.size() == 0)
		{
			ss.useList = ss.wordList;
				//ss.dbgApp("got to setUseList 2A: ss.useList.size() = "+ss.useList.size()); //debug
			ss.message = Messages.getMess40(ss);
            ss.wdlist = false;
		}
		else if(ss.wordList.size() <= 50)
		{
            ss.wdlist = true;

			for(int i = 0; i < ss.wordList.size(); i++)
			{
				ss.wb1 = ss.wordList.get(i);
				ss.wb1.setListID(i);
				ss.useList.add(ss.wb1);
			}
			ss.message = Messages.getMess16(ss);
			//ss.dbgApp("got to setUseList 2B: ss.useList.size() = "+ss.useList.size());
		}
		else
		{
            ss.wdlist = true;

			for(int i = 0; i < 50; i++)
			{
				do {setWordBean(ss.wordList, ss);} // sets ss.wb1 by using a random int to retrieve a WordBean from ss.wordList
				while(!(checkUseList(ss)));
				ss.wb1.setListID(i);
				ss.useList.add(ss.wb1);
			}
			//ss.dbgApp("got to setUseList 2C: ss.useList.size() = "+ss.useList.size());
		}
	} // creates ss.useList from ss.wordList
	
	
	// yes, it is necessary for building ss.tempList
	// CHECK IF NEED FETCHTEMP FOR SOMETHING HERE 
	private static void setStudy(WordBean wb, SessionState ss) throws ServletException
	{
		if(ss.getMeaning().equals("kanE") || ss.getMeaning().equals("yomE")) 
		{
			ss.tempCol = "meaning";
			DBHandler.fetchTemp(wb.kan, ss); // fetches all of the words in the DB with the same meaning
			
			if(ss.threeCols)
				ss.word = wb.kan+" ( "+wb.yom+" )";
			else
				ss.word = wb.kan;
		}
		else if(ss.getMeaning().equals("Ekan") || ss.getMeaning().equals("Eyom")) 
		{
			ss.tempCol = "english";
			DBHandler.fetchTemp(wb.eng, ss);
			ss.word = wb.eng;
		}
	} // sets ss.word, ss.tempCol and builds ss.tempList depending on kanE / yomE or Ekan / Eyom
	
	
	private static void setArray(SessionState ss)
	{
			//ss.dbgApp("got to setArray 1: ss.useList.size() = "+ss.useList.size()); //debug
		for(int i=0; i < ss.b.length; i++)
		{
				//ss.dbgApp("got to setArray 2: i = "+i); //debug
			getWordBean(i, ss); // ensures that ss.wb1 is set appropriately by comparing the selected WordBean to other buttons and previous answers
			ss.b[i] = ss.wb1;
		}
			//ss.dbgApp("got to setArray 3: ss.useList.size() = "+ss.useList.size()); //debug
	} // sets the value of the 6 elements in ss.b array from the wordBeans
	
	
	private static void getWordBean(int z, SessionState ss)
	{
		if(ss.useList.size() < 0)
		{
			//ss.dbgApp("got to getWordBean 1: Can't getWB as ss.useList.size() is negative.");
			//ss.dbgApp("got to getWordBean 2: ss.useList.size() = "+ss.useList.size()); //debug
		}
		else if(ss.useList.size() == 0)
		{
				//ss.dbgApp("got to getWordBean 3A: ss.useList.size() = "+ss.useList.size()); //debug
			WordBean congratBean = new WordBean();
			congratBean.setID(0);
			congratBean.setEng("Congrats");
			congratBean.setKan("おめでとう");
			congratBean.setYom("おめでとう");
			
			ss.message = Messages.getMess17(ss);
			ss.wb1 = congratBean;
		} // sets the congratBean variables
		else if(ss.useList.size() < 7) // was 7
		{
				//ss.dbgApp("got to getWordBean 3B: ss.useList.size() = "+ss.useList.size()); //debug
			setWordBean(ss.useList, ss);
		} // sets ss.wb1 by using a random int to retrieve a WordBean from ss.useList
		else if(ss.useList.size() < 20)
		{
				//ss.dbgApp("got to getWordBean 3C: ss.useList.size() = "+ss.useList.size()); //debug
			do {setWordBean(ss.useList, ss);} // sets ss.wb1 by using a random int to retrieve a WordBean from ss.useList
			while (!(checkButtons(z, ss)));
		}
		else
		{
				//ss.dbgApp("got to getWordBean 3D: ss.useList.size() = "+ss.useList.size()); //debug
			do {setWordBean(ss.useList, ss);} // sets ss.wb1 by using a random int to retrieve a WordBean from ss.useList
			while (!(checkButtons(z, ss) && checkAnswers(ss)));
		}
	} // ensures that ss.wb1 is set appropriately by comparing the selected WordBean to other buttons and previous answers
	
	
	private static void setWordBean(ArrayList w, SessionState ss)
	{
		Random randomInt = new Random();
		int id = randomInt.nextInt(w.size());
		ss.wb1 = (WordBean)w.get(id);
				//ss.dbgApp("got to setWordBean: id = "+id); //debug
	} // sets ss.wb1 by using a random int to retrieve a WordBean from an ArrayList
	
	
	private static void setButtons(SessionState ss)
	{
		int m = 1;
		int ans = 0;
		Random ranAns = new Random();
		ans = ranAns.nextInt(6);			
		
		if(ss.getMeaning().equals("kanE") || ss.getMeaning().equals("yomE")) {m = 0;}
		else if(ss.getMeaning().equals("Ekan")) {m = 1;} // || ss.getMeaning().equals("yk")
		else if(ss.getMeaning().equals("Eyom")) {m = 2;} // || ss.getMeaning().equals("ky")
		
		ss.setBut1(ss.b[0].getWord(m));
		ss.setBut2(ss.b[1].getWord(m));
		ss.setBut3(ss.b[2].getWord(m));
		ss.setBut4(ss.b[3].getWord(m));
		ss.setBut5(ss.b[4].getWord(m));
		ss.setBut6(ss.b[5].getWord(m));
		
		ss.setAns(ss.b[ans].getWord(m));
		ss.setAnsID(ss.b[ans].getListID());
		ss.setEngAns(ss.b[ans].getEng());
		ss.setKanAns(ss.b[ans].getKan());
		ss.setYomAns(ss.b[ans].getYom());
		
		ss.ansBean = ss.b[ans];
		
		addAnswer(ss.b[ans], ss); // adds answer to ss.a array of answers
			//ss.dbgApp("got to setButtons: Buttons were set.");
	} // sets the values of the 6 buttons, ss.setAns(), ss.setAnsID(), 
	// ss.setEngAns(), ss.setKanAns(), ss.setYomAns(), ss.ansBean and adds the answer to the answer array (ss.a)
	
	
	private static boolean checkButtons(int z, SessionState ss)
	{
		for(int i = 0; i < z; i++)
		{
			if (!differentWBeans(ss.b[i], ss))
				return false;
		}
		return true;
	}
	
	
	private static boolean checkAnswers(SessionState ss)
	{
		for(WordBean wb2 : ss.a)
		{
			if (!differentWBeansAny(wb2, ss))
				return false;
		}
		return true;
	}
	
	
	private static boolean checkUseList(SessionState ss)
	{
		if(ss.useList.isEmpty() || ss.useList.size() == 0) {return true;}
		else
		{
			Iterator useListIter = ss.useList.iterator();
			while(useListIter.hasNext())
			{
				WordBean tBean = new WordBean();
				tBean = (WordBean) useListIter.next();
				if(!differentWBeansAny(tBean, ss)) {return false;}
			}
		return true;
		}
	}
	
	
	private static boolean differentWBeans(WordBean wb2, SessionState ss)
	{
		String eng1 = ss.wb1.getEng();
		String eng2 = wb2.getEng();
		if(eng1.equals(eng2)) return false;
		
		String kan1 = ss.wb1.getKan();
		String kan2 = wb2.getKan();
		if(kan1.equals(kan2)) return false;
		
		if(ss.threeCols)
		{
			String yom1 = ss.wb1.getYom();
			String yom2 = wb2.getYom();
			if(yom1.equals(yom2)) return false;
		}
			
		return true;
	}
	
	
	private static boolean differentWBeansAny(WordBean wb2, SessionState ss)
	{
		if(ss.threeCols)
		{
			String eng1 = ss.wb1.getEng();
			String eng2 = wb2.getEng();
			String kan1 = ss.wb1.getKan();
			String kan2 = wb2.getKan();
			String yom1 = ss.wb1.getYom();
			String yom2 = wb2.getYom();
			
			if(eng1.equals(eng2) && kan1.equals(kan2) && yom1.equals(yom2)) return false;
		}
		else
		{
			String eng1 = ss.wb1.getEng();
			String eng2 = wb2.getEng();
			String kan1 = ss.wb1.getKan();
			String kan2 = wb2.getKan();
			
			if(eng1.equals(eng2) && kan1.equals(kan2)) return false;
		}
			
		return true;
	}
	
	
	private static void addAnswer(WordBean wb, SessionState ss)
	{
		for(int i = 5; i > 0; i--) {ss.a[i] = ss.a[i-1];}
		
		ss.a[0] = wb;
	} // adds answer to ss.a array of answers
	
	
	//CHECK
	//maybe do not need at all; if necessary, add mylist
	private static void checkTeachList(ArrayList<String> tList, SessionState ss)
	{
		ss.list = new ArrayList<Integer>();
		
		for(String i : tList)
		{
			if(ss.maybeListVerbs.contains(i)) continue;
			else if(ss.maybeListNouns.contains(i)) continue;
			else if(ss.maybeListAdjs.contains(i)) continue;
			else if(ss.maybeListMisc.contains(i)) continue;
			else if(ss.knowListVerbs.contains(i)) continue;
			else if(ss.knowListNouns.contains(i)) continue;
			else if(ss.knowListAdjs.contains(i)) continue;
			else if(ss.knowListMisc.contains(i)) continue;
			
			ss.list.add(Integer.parseInt(i));
		}
	}
	
	
	// for switch_lang.jsp (Probably don't need)
	public static void handleLangSwitch(String tongue, String typ, SessionState ss)
	{
		if(typ.equals("three")) ss.threeCols = true;
		else ss.threeCols = false;
		
		ss.table = tongue;
	}


    // templates


    public static String prntExample(SessionState ss)
    {
        String s = "";

        return s;
    }

}
