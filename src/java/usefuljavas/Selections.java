// Altered on 2006-05-21

// A Class that provides DropDown Lists for Book classes.

package usefuljavas;

import java.util.*;
import java.sql.SQLException;
import javax.servlet.ServletException;



public final class Selections
{
	private static String style = "class='selection' ";
	
	public static String[] t = {"verb", "noun", "adjective", "misc"};
	public static String[] u = {"knowList", "maybeKnowList","tempHide"};
	public static String[] v = {"eikenPre2", "eiken3", "eiken4", "business1", "jTest1", "japanese"};
	
	public static String[][] tt = {{"Verbs", "動詞"}, {"Nouns", "名詞"}, {"Adjectives", "形容詞"}, {"Misc", "雑多詞"}};
	public static String[][] uu = {{"Know List", "知っているリスト"}, {"Maybe Know List", "たぶん知っているリスト"}, {"Temporary Hide", "一時的に隠す"}}; 
	public static String[][] vv = {{"Step Pre 2", "英検準2級"}, {"Step 3", "英検3級"}, {"Step 4", "英検4級"}, {"Business 1", "企業1"}, {"JTest 1", "日本語テスト1"}, {"Japanese", "単語"}};
	
	
	
	public static String adjustTypList(String typ, SessionState ss)
	{		//ss.dbgApp("got to sel.adjustTypList 1: typ = "+typ);
		for(Types type : Types.values())
		{
			if(typ.equals(type.getTypValue(ss.lang)))
			{
				//ss.dbgApp("got to sel.adjustTypList 1: type name = "+type.getName());
				return type.getName();
			}
		}
		
		return typ;
	} // returns the type in lowercase after adjusting for E or J
	
	
	public static String adjustUseList(String useList, SessionState ss)
	{
			//ss.dbgApp("got to sel.adjustUseList 1: useList = "+useList);
		for(Lists list : Lists.values())
		{
			if(useList.equals(list.getListValue(ss.lang)))
			{
				//ss.dbgApp("got to sel.adjustUseList 2: list name = "+list.getName());
				return list.getName();
			}
		}
		
		return null;
	} // runs through Lists and returns the list.getName() if there, else returns null
	
	
	public static void setTypList(String typ, SessionState ss)
	{
		String typList = adjustTypList(typ, ss); // returns the type in lowercase after adjusting for E or J
		
		ss.setTyp(typList);
		ss.typ = ss.type;
		ss.prevType = ss.typ;
			//ss.dbgApp("got to sel.setTypList 1: typ = "+typ+", ss.typ = "+ss.typ+", ss.prevType = "+ss.prevType);
			
		ss.setLists();
	} // sets ss.type, ss.typ and ss.prevType
	
	
	public static void setSSList(String list, SessionState ss)
	{
				//ss.dbgApp("got to sel.setSSList 1: list = "+list);
		ss.listGroup = adjustUseList(list, ss); // runs through Lists and returns the list.getName() if there, else returns null
		
				//ss.dbgApp("got to sel.setSSList 2: ss.listGroup = "+ss.listGroup);
		ss.setLists();
		
		if(ss.listGroup != null)
		{
			BookMethods.setMainList(ss); // sets ss.mainList by finding the right list in the DB or ss.map
			
			ss.list = ss.mainList;
			
			ss.prevList = ss.listGroup;
				//ss.dbgApp("got to sel.setSSList 3A: list = "+list+", ss.list = "+ss.list+", ss.prevList = "+ss.prevList);
		}
		else 
		{
			ss.teachChapList = true;
			ss.chapter = list;
			ss.listGroup = list;
			ss.listName = list;
			
			BookMethods.setMainList(ss); // sets ss.mainList by finding the right list in the DB or ss.map
			
			ss.list = ss.mainList;
			
			ss.prevList = list;
				ss.dbgApp("got to sel.setSSList 3B: list = "+list+", ss.chapter = "+ss.chapter+", ss.list = "+ss.list+", ss.prevList = "+ss.prevList);
		}
	} // takes the useList, determines if in Lists or is a Chapter, and sets ss.listGroup, ss.list, ss.mainList and ss.prevList
	
	
	// may need to check about setting colums for other language tables as ss.table
	public static void setPrevSelect(String vocList, SessionState ss)
	{
		ss.prevSelect = adjustUseList(vocList, ss);
		
		if(ss.prevSelect == null)
		{
			ss.listName = vocList;
		}
		else
		{
			ss.listGroup = ss.prevSelect;
		}
		
		if(ss.prevSelect.equals("kokugo"))
		{
			ss.whereVal = ss.table;
		}
		else if(ss.prevSelect.equals("business1"))
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
	} // takes the vocList, determines if in Lists or is a Chapter, and sets ss.listGroup and ss.preSelect or ss.listName
		// Also sets columns for ss.japanese 
	
	// returns a choice of Type Options
	public static String typeOptions(SessionState ss)
	{
		String o = "";
		
		for(Types type : Types.values())
		{
			if(ss.prevType.equals(type.getName()))
				{ o = o + "<option selected>"+type.getTypValue(ss.lang)+"</option>"; }
			else 
				{ o = o + "<option>"+type.getTypValue(ss.lang)+"</option>"; }
		}
		
		return o;
	}
	
	// returns a choice of Eiken Options
	public static String eikenOptions(SessionState ss)
	{
		String o = "";
		
		for(Lists list : EnumSet.range(Lists.EIKEN2, Lists.EIKEN4))
		{
			String name = list.getName();
			
			if(ss.prevList.equals(list.getName()))
				{ o = o + "<option selected>"+list.getListValue(ss.lang)+"</option>"; }
			else 
				{ o = o + "<option>"+list.getListValue(ss.lang)+"</option>"; }
		}
		
		return o;
	} // returns a string of eiken selections
	
	// returns a choice of ListOptions (MyList, etc.)
	public static String listOptions(SessionState ss)
	{
		String o = "";
		
		for(Lists list : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
		{
			String name = list.getName();
			
			if(ss.prevList.equals(list.getName()))
				{ o = o + "<option selected>"+list.getListValue(ss.lang)+"</option>"; }
			else 
				{ o = o + "<option>"+list.getListValue(ss.lang)+"</option>"; }
		}
		
		return o;
	}
	
	// returns a choice of ChapterOptions (Names of user-generated lists)
	public static String chapterOptions(SessionState ss)
	{
		String o = "";
		String prev = "";
		boolean inList = false;
		boolean checkInList = false;
				
		TreeSet<String> tree = new TreeSet<String>();
		
		if(!ss.role.equals("user"))
		{
			Set< String > mapKeys = ss.chapNameList.keySet();
			
			for(String chap : mapKeys)
			{
				tree.add(chap);
			}
			
			for(String s : tree)
			{
				inList = false;
				if(s.equals(ss.prevList)) 
				{
					inList = true;
					checkInList = true;
				}
				
				if(inList) { o = o + "<option selected>"+s+"</option>"; }
				else { o = o + "<option>"+s+"</option>"; }
				
				prev = s;
			}
			
			if(!checkInList) ss.prevList = prev;
		}
		
		return o;
	}
	
	// returns a choice of Vocab Options
	public static String vocabOptions(SessionState ss)
	{
		String o = "";
		
		if(ss.language.equals("japanese"))
		{
			for(Lists list : EnumSet.range(Lists.EIKEN2, Lists.KOKUGO))
			{
				if(ss.prevSelect.equals(list.getName()))
					{ o = o + "<option selected>"+list.getListValue(ss.lang)+"</option>"; }
				else 
					{ o = o + "<option>"+list.getListValue(ss.lang)+"</option>"; }
			}
		}
		else
		{
			Lists list = Lists.KOKUGO;
			
				if(ss.prevSelect.equals(list.getName()))
					{ o = o + "<option selected>"+list.getListValue(ss.lang)+"</option>"; }
				else 
					{ o = o + "<option>"+list.getListValue(ss.lang)+"</option>"; }
		}
			
		return o;
	}
	
	// returns a choice of limited Vocab Options
	public static String vocabOptsLim(SessionState ss)
	{
		String o = "";
		
		for(Lists list : EnumSet.range(Lists.EIKEN2, Lists.EIKEN4))
		{
			if(ss.prevSelect.equals(list.getName()))
				{ o = o + "<option selected>"+list.getListValue(ss.lang)+"</option>"; }
			else 
				{ o = o + "<option>"+list.getListValue(ss.lang)+"</option>"; }
		}
		
		return o;
	}
	
	
	// returns the proper string for the language of either the type or the list groupname
	public static String adjustPrev(String prev, SessionState ss)
	{
			//ss.dbgApp("got to sel.adjustPrev 1: prev = "+prev);
		for(Types type : Types.values())
		{
			if(prev.equals(type.getName())) 
			{
				//ss.dbgApp("got to sel.adjustPrev 2: type = "+type.getTypValue(ss.lang));
				return type.getTypValue(ss.lang);
			}
		}
		
		for(Lists list : Lists.values())
		{
			if(prev.equals(list.getName()))
			{
				//ss.dbgApp("got to sel.adjustPrev 2: list = "+list.getListValue(ss.lang));
				return list.getListValue(ss.lang);
			}
		}
		
			//ss.dbgApp("got to sel.adjustPrev 3: prev = not here");
		return "not here";
	}
	
	// returns a choice of personal lists plus user-generated lists (plus eikenlists if administrator)
	public static String getSelect1(SessionState ss)
	{
		if(ss.role.equals("admin"))
		{
			String[] s =  {"<select name='displayset' "+style+">"+eikenOptions(ss)+listOptions(ss)+chapterOptions(ss)+"</select>",
				"<select name='displayset' "+style+">"+eikenOptions(ss)+listOptions(ss)+chapterOptions(ss)+"</select>"}; //japanese
			return s[ss.lang];
		}
		else
		{
			String[] s =  {"<select name='displayset' "+style+">"+listOptions(ss)+chapterOptions(ss)+"</select>",
				"<select name='displayset' "+style+">"+listOptions(ss)+chapterOptions(ss)+"</select>"}; //japanese
			return s[ss.lang];
		}
	}
	
	// returns a choice of types
	public static String getSelect2(SessionState ss)
	{
		String[] s =  {"<select name='type' "+style+">"+typeOptions(ss)+"</select>",
			"<select name='type' "+style+">"+typeOptions(ss)+"</select>"}; //japanese
		return s[ss.lang];
	}
	
	// returns a choice of vocab options (or limited voc opts if user is not admin)
	public static String getSelect3(SessionState ss)
	{
		if(ss.role.equals("admin"))
		{
			String[] s =  {"<select name = 'dispvoclist' "+style+">"+vocabOptions(ss)+"</select>",
				"<select name = 'dispvoclist' "+style+">"+vocabOptions(ss)+"</select>"}; //japanese
				
			return s[ss.lang];
		}
		else
		{
			String[] s =  {"<select name = 'dispvoclist' "+style+">"+vocabOptsLim(ss)+"</select>",
				"<select name = 'dispvoclist' "+style+">"+vocabOptsLim(ss)+"</select>"}; //japanese
			
			return s[ss.lang];
		}
	}
	
	// returns a choice of chapterOptions
	public static String getSelect4(SessionState ss)
	{
		if(ss.role.equals("teacher"))
		{
			String[] s =  {"<select name = 'displaychap' "+style+">"+chapterOptions(ss)+"</select>",
				"<select name = 'displaychap' "+style+">"+chapterOptions(ss)+"</select>"}; //japanese
				
			return s[ss.lang];
		}
		else if(ss.role.equals("admin"))
		{
			String[] s =  {"<select name = 'dispvoclist' "+style+">"+vocabOptions(ss)+"</select>",
				"<select name = 'dispvoclist' "+style+">"+vocabOptions(ss)+"</select>"}; //japanese
				
			return s[ss.lang];
		}
		else
		{
			String[] s =  {"Incorrect Role",
				"使用出来ません"};
				
			return s[ss.lang];
		}
	}
	
	// returns a choice of Verbs, Nouns, etc.; used in Sample.jsp
	public static String getSelect8(int x)
	{
		String[] s =  {"<select name='type' "+style+"><option selected>Verbs</option>"+
					"<option>Nouns</option><option>Adjectives</option><option>Misc</option>"+
					"</select>",
			"<select name='type' "+style+"><option selected>動詞</option>"+
					"<option>名詞</option><option>形容詞</option><option>雑多詞</option>"+
					"</select>"};
		return s[x];
	}
	
	// returns a choice of all options (including eiken if admin)
	public static String getSelect9(SessionState ss)
	{ // just in case you need this later : eikenOptions(ss)+
		if(ss.role.equals("user") || ss.role.equals("sampler"))
		{
			String[] s =  {"<select name='displaylist' "+style+">"+listOptions(ss)+"</select>",
				"<select name='displaylist' "+style+">"+listOptions(ss)+"</select>"}; //japanese
			return s[ss.lang];
		}
		else if(ss.role.equals("admin"))
		{
			String[] s =  {"<select name='displaylist' "+style+">"+eikenOptions(ss)+listOptions(ss)+chapterOptions(ss)+"</select>",
				"<select name='displaylist' "+style+">"+eikenOptions(ss)+listOptions(ss)+chapterOptions(ss)+"</select>"}; //japanese
			return s[ss.lang];
		}
		else
		{
			String[] s =  {"<select name='displaylist' "+style+">"+eikenOptions(ss)+listOptions(ss)+chapterOptions(ss)+"</select>",
				"<select name='displaylist' "+style+">"+eikenOptions(ss)+listOptions(ss)+chapterOptions(ss)+"</select>"}; //japanese
			return s[ss.lang];
		}
	}
	
	// returns a choice of Step Test / Eiken levels; used in Sample.jsp
	public static String getSelect11(int x)
	{
		String[] s =  {"<select name='displayset' "+style+"><option>Step Pre 2</option><option>Step 3</option>"+
					"<option selected>Step 4</option></select>",
			"<select name='displayset' "+style+"><option>英検準2級</option><option>英検3級</option>"+
					"<option selected>英検4級</option></select>"}; 
		return s[x];
	}
	
	// returns a choice of Lists (My, Know, and Maybe)
	public static String getSelect13(SessionState ss)
	{
		if(ss.role.equals("user"))
		{
			String[] s =  {"<select name='clearlist' "+style+">"+
						"<option selected>Know List</option><option>Maybe Know List</option>"+
						"</select>",
				"<select name='clearlist' "+style+">"+
						"<option selected>知っているリスト</option><option>たぶん知っているリスト</option>"+
						"</select>"}; //japanese
			return s[ss.lang];
		}
		else
		{
			String[] s =  {"<select name='clearlist' "+style+">"+listOptions(ss)+"</select>",
				"<select name='clearlist' "+style+">"+listOptions(ss)+"</select>"}; //japanese
			return s[ss.lang];
		}
	}
	
	// returns a choice of lists to move a row to
	public static String getSelect14(SessionState ss)
	{
		String[] s =  {"<select name='movelist' "+style+">"+listOptions(ss)+"</select>",
			"<select name='movelist' "+style+">"+listOptions(ss)+"</select>"}; //japanese
		return s[ss.lang];
	}
	
	
	// For EditTables - returns a choice of  all Tables except japanese
	public static String getSelect15(SessionState ss) throws ServletException, SQLException
	{
		String o = "";
		
		if(ss.tables == null || ss.tables.isEmpty()) { DBHandler.getTables(ss); }
		
		for( String s : ss.tables ) 
		{
			o = o + "<option>"+s+"</option>";
		}
		
		o = "<option selected>[New Table]</option>"+o;
		
		return "<select name='tables' "+style+">"+o+"</select>";
	}
	
	
	// Check 
	// For creating New Tables for Languages - a Link should be placed in Create Lists for teachers
	public static String getSelect15A(SessionState ss) throws ServletException, SQLException
	{
		String o = "";
		
		if(ss.tables == null || ss.tables.isEmpty()) { DBHandler.getTables(ss); }
		
		for( String s : ss.tables ) 
		{ 
			if(s.equals("hartlists") || s.equals("institutions") || s.equals("notepad") || s.equals("teachers") || s.equals("userlist")) continue;
			else
			{
				o = o + "<option>"+s+"</option>";
			}
			
			o = "<option selected>[New Table]</option>"+o;
		}
		
		return "<select name='tables' "+style+">"+o+"</select>";
	}
	
	
	// Check 
	// For Selecting Language Table - should be placed in all of 3 Learning Options if English
	public static String getSelect15B(SessionState ss) throws ServletException, SQLException
	{
		String o = "";
		TreeSet<String> tree = new TreeSet<String>();
		
		if(ss.tables == null || ss.tables.isEmpty()) { DBHandler.getTables(ss); }
		
		for( String s : ss.tables ) 
		{ 
			if(s.equals("lists") || s.equals("creators")  || s.equals("institutions") || s.equals("notepad") || s.equals("teachers") || s.equals("userlist")) continue;
			else
			{
				tree.add(s);
			}
		}
		
		for( String tr : tree ) 
		{
			{
				if(tr.equals(ss.language)) { o = o + "<option selected>"+tr+"</option>"; } 
				else { o = o + "<option>"+tr+"</option>"; }
			}
		}
		
		return "<select name='tables' "+style+">"+o+"</select>";
	}
	
	
	// For EditTables - Gets all Tables except japanese
	public static String getSelect15C(SessionState ss) throws ServletException, SQLException
	{
		String o = "";
		
		if(ss.tables == null || ss.tables.isEmpty()) { DBHandler.getTables(ss); }
		
		for( String s : ss.tables ) 
		{ 
			if(!s.equals("japanese"))
			{
				if(s.equals(ss.dbTable)) { o = o + "<option selected>"+s+"</option>"; }
				else { o = o + "<option>"+s+"</option>"; }
			}
		}
		
		return "<select name='tables' "+style+">"+o+"</select>";
	}
	
	// returns a choice of Eng, Kanji or Yomi
	public static String getSelect18(SessionState ss)
	{
		String[] s =  {"<select name = 'printQuizzer' "+style+"><option selected>English</option><option>Kanji</option><option>Yomi</option></select>",
			"<select name = 'printQuizzer' "+style+"><option selected>英語</option><option>漢字</option><option>読み</option></select>"}; //japanese
			
		return s[ss.lang];
	}
	
	// returns a choice of Types
	public static String getSelect19(SessionState ss)
	{
		String[] s =  {"<select name='"+ss.typSel+"type' "+style+">"+typeOptions(ss)+"</select>",
			"<select name='"+ss.typSel+"type' "+style+">"+typeOptions(ss)+"</select>"}; //japanese
		return s[ss.lang];
	}
	
	// returns a choice of chapters
	public static String getSelect(SessionState ss)
	{
		String[] s =  {"<select name = 'displaychap' "+style+">"+chapterOptions(ss)+"</select>",
			"<select name = 'displaychap' "+style+">"+chapterOptions(ss)+"</select>"}; //japanese
			
		return s[ss.lang];
	}
	
	// returns a choice of Move Options
	public static String getMoveSet(SessionState ss)
	{
		String move1 = "", move2 = "";
		
		if(ss.prevList.equals("maybeList")) 
		{
			move1 = Lists.KNOWLIST.getListValue(ss.lang);
			move2 = Lists.MYLIST.getListValue(ss.lang);
		}
		else if(ss.prevList.equals("knowList")) 
		{
			move1 = Lists.MAYBELIST.getListValue(ss.lang);
			move2 = Lists.MYLIST.getListValue(ss.lang);
		}
		else
		{
			move1 = Lists.KNOWLIST.getListValue(ss.lang);
			move2 = Lists.MAYBELIST.getListValue(ss.lang);
		}
		
		return "<select name='moveset' "+style+"><option selected>"+Lists.TEMPHIDE.getListValue(ss.lang)+"</option><option>"+move1+
			"</option><option>"+move2+"</option></select>";
	}
	
	
	// For EditTables - Gets all Tables except japanese
	public static String getSelRSCols(SessionState ss) throws ServletException, SQLException
	{
		String o = "";
		ss.dbTable = ss.table;
		String[] cols = DBHandler.getColNames(ss);
		
		for( String s : cols ) 
		{ 
			o = o + "<option>"+s+"</option>";
		}
		
		return "<select name='columns' "+style+">"+o+"</select>";
	}
	
	
	// not used yet?
	// For EditTables - Gets all Tables except japanese 
	public static String getSelectRS(SessionState ss) throws ServletException, SQLException
	{
		String o = "";
		
		if(ss.tables == null || ss.tables.isEmpty()) { DBHandler.getTables(ss); }
		
		for( String s : ss.tables ) 
		{ 
			if(!s.equals("japanese"))
			{
				if(s.equals(ss.dbTable)) { o = o + "<option selected>"+s+"</option>"; }
				else { o = o + "<option>"+s+"</option>"; }
			}
		}
		
		return "<select name='tables' "+style+">"+o+"</select>";
	}
	
}
