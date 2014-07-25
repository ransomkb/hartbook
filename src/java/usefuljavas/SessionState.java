// Altered on 2006-05-30

// A Class that provides variables, sets and gets for Sessions, SQL connections and Book classes.

package usefuljavas;

import usefulbeans.TableRow;
import usefulbeans.WordBean;
import java.util.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
// import java.sql.SQLException;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.ServletException;

import java.security.SecureRandom;

/**
 * This class handles and stores session state for each web user.
 *
 *  project: Web eigo
 *
 * @author Ransom Barber 2005/05/23
 */

public final class SessionState implements HttpSessionBindingListener
{
 /**
  * One instance of this object will be created for each
  * live session.  When the session is ended, either because user finishes or 
  * timeout, then the instance will be destroyed.
  *
  * We will use public non-local variables to store the state
  *  for each session.  Then just the session id can be put 
  *  in either a cookie or a hidden form variable or even 
  *  on the URL as a querystring param.
  *
  * Then doGet() will match the id to a valid live session instance
  *  and that will be used throughout the rest of the request response.
  * 
  */

	// class vars shared by all instances
	static Hashtable sessions = new Hashtable();  //shared
	
	// to make sure only one session with a given SSN is logged in.
	static Hashtable userIDs = new Hashtable();  //shared  
	
        // ASK G: should this be private?
	// to hold the names of user's chapter lists and the listID from table lists
	public HashMap chapNameList = new HashMap();
	
	// SHOULD THIS BE FINAL?
	// to handle all the HashMap knows and maybeknows for the user and used to save the data
	public HashMap< String, HashMap< String, Integer > > map = new HashMap< String, HashMap< String, Integer > >();
	
	
	// instance vars (non-local) one per instance - session data.
	public String sessionId = "";
	public long sessionStart    = System.currentTimeMillis();
	public long sessionLastUsed = System.currentTimeMillis();
	
 /**
 *System.currentTimeMillis()
 *returns the # of milliseconds since Jan 1. 1970 GMT or something like that.
 *we can certainly use it to track the # of seconds elapsed since last
 *browser hit and then expire the session.
 */
	
	public String randomContextCheck = "";
	
	private static SecureRandom random = new SecureRandom(); 
	
	private static long sesTimeout = 20;
	// 20 minutes default timeout for sessions. Now configured in web.xml servlet properties!
	
	// example of making a constant:
	//private static final int maxItemPoolSize = 512;  //  max # items in any pool.
	
	
	// --  need these perhaps
	public Calendar TimeStarted = null;  // record time that began.
	public Calendar TimeEnded   = null;  // record time that finished.
	public Calendar TimeNow     = null;  // time at post event reception.
	
	// -- These vars are saved in session table: --
	
	public String phase = "newborn";  	
	public String errMsg = "";
	
	// ---
	
	//public User user = null;	
	public String dbgMsg = "";	
	public String postedRC = "";    // it was just put here so it's easier to pass around
									  // this is really the randomContextCheck from the previous
									  // page that is now sending a post request.
									  // by storing this we can check that they are not
									  // using the browser's back button which causes problems
									  // for dynamic applications since you can't always return
									  // to some previous state - which might not exist anymore!
	

	public int rnum = 0;
	public int save = 0;
	public int round = -1;
	public int numOfRounds = 0;
	public int rowCount = 0;
	public int numOfCols = 0;
	public int numOfRows = 0;
	public int quizNum = 0;
	
	public int rightGuessRev = 0;
	public int rightGuessTest = 0;
	public int wrongGuessRev = 0;
	public int wrongGuessTest = 0;
	public int percentCorrectRev = 0;
	public int percentCorrectTest = 0;
	public int totalCorrectRev = 0;
	public int totalCorrectTest = 0;
	public int totalPossibleRev = 0;
	public int totalPossibleTest = 0;
	
	public int id = -1;
	public int pid = -1;
	public int rowID = 1;
	public int idAns = 0;
	public int idAns2 = 1;
	public int hideSize = 0;
	public int selection = 0;
	public int studMin = 0;
	public int lang = 0; // determines whether html output should be in E or J
	public int edit = 0;
	public int startApp = 0;
	public int endApp = 0;
	public int helpInt = 0;
	public int studyInt = 0;
	
	public int studMinPrev = 0, perRevPrev = 0, totRevPrev = 0, perTestPrev = 0, totTestPrev = 0;
	public int studMinutes = 0, perRev = -1, totRev = -1, perTest = -1, totTest = -1, countRev = 0, countTest = 0;
	
	// NEED TO MAKE COMPLETE USE OF THE FOLLOWING; DOUBLE CHECK RELEVANT CLASSES
	//public static String DRIVER = "com.mysql.jdbc.Driver";
	//public static String URL = "jdbc:mysql://localhost/Hart?autoReconnect=true&?useUnicode=true&characterEncoding=utf8";
	//public static String USERNAME = "root";
	//public static String PASSWORD = "yumi@tako";
	//public static String DBNAME = "Hart";
	
    public static SqlUtil mySqlUtil = null;
        
	public HTMLStuff html = new HTMLStuff();
	public Explanations exp = new Explanations();
	public CalculateHistory calcHist = new CalculateHistory();

        //public static BookMethods bkMeth = new BookMethods();
        //public static StringHandler strHand = new StringHandler();	
	//public static DBHandler dbHand = null;
	//public static Messages mess = new Messages();
	//public static Selections sel = new Selections();
        //public HelpMess helpPage = new HelpMess();
	//public Buttons but = new Buttons();
        
	public ResultSet rs = null;
	public ResultSetMetaData metaData = null;
	
	public String hList = ""; // for creating hartlists table
	public String col = "";
	public String var = "";
	public String check = "";
	
	public String userID = "";
	public String psswd = "";
	public String familyName = "";
	public String firstName = "";
	public String contact = "";
	public String myteacher = "";
	public String prevType = "noun";
	public String prevList = "My List";
	public String prevSelect = "eiken4";
	public String intList = "";
	public String word = "";
	public String help = "all_help.jsp";
	public String language = "japanese";
	
	// for getting started automatically from MyLearning's Review section :: OLD; REVAMP
	public String table = "japanese";
	public String tempTable = "japanese";
	public String learn = "review";
	public String type = "noun"; // for setting type; don't forget that there is also typ handling wordBean and SQL (which may sometimes use type?)
	public String displayList = "myList";
	public String whereCol = "eiken";
	public String whereVal = "eiken4";
	public String reading = "kan";
	public String engEx = "No Example Yet";
	public String langEx = "まだ例文がありません";
	public String engExOrig = "No Example Yet";
	public String langExOrig = "まだ例文がありません";
    public String engExStudy = "";
	public String langExStudy = "";
	//public String meaning = "E";

	public String postPage = "/Study";
	public String srcPage = "/hartbook/front_page.jsp";
	public String goBack = "/hartbook/login.jsp";
	
	public String fullHelp = "help_popup.jsp";
	public String typSel = "";
	public String searchVar = "";
	public String definition = "";
	public String clobPieceA = "";
	public String clobPieceB = "";
	public String typString = "";
	public String typStringFin = "";
	public String folder = "hartbook";
	public String links = "";
	public String chapter = "No Chapter Set";
	public String role = "";
	public String teacher = "";
	public String studID = "";
	public String printChoice = "eng";
	public String strCSV = "Empty";
	
	public String scores = "Scores";
	public String history = "History";
	
	public String mainString = "";
	public String myString = "";
	public String myClobString = "";
	public String maybeString = "";
	public String maybeClobString = "";
	public String knowString = "";
	public String knowClobString = "";
	public String teachString = "";
	public String tchClobString = "";
	public String chapClobString = "";
	public String adminClobString = "";
	
	public String listGroup = "knowList";
	public String listName = "knowListNouns";
	public String tempCol = "";
	public String stateCol = "";
	public String radioID = "";
	public String dbTable = "userlist";
	public String debugString = "";
	//public String displaychap = "";
	public String answer = "No Answer Yet";
	public String guess = "No Guess Yet";
	
	public String message = "";
	public String logmes = "";
	public String hideSizeMessage = "No Message";
	public String ans = "", typ = "noun", eng = "", kan = "", yom = "";
	public String engAns = "No English Yet", kanAns = "No Kanji Yet", yomAns = "No Yomi Yet";
	public String but1 = "Start", but2 = "Start", but3 = "Start", but4 = "Start", but5 = "Start", but6 = "Start", but7 = "Start";
	
	public String first = "", last = "";
	
	public TableRow tr = new TableRow();
	
	public WordBean wb1 = new WordBean();
	public WordBean wBean = new WordBean();
	public WordBean ansBean = new WordBean();
	
	//public String[] listOpt = {"", "", "", ""};
	//public String[] listSel = {"My List", "Know List", "Maybe Know List", "Teacher List"};
	//public String[] typOpt = {"", "", "", ""};
	//public String[] wType = {"verb", "noun", "adjective", "misc"};
	//public String[] option = {"", "", "", "", "", "", "", "", ""};
	//public String[] select = {"Eiken Pre 2", "Eiken 3", "Eiken 4", "Business 1", "JTest 1", "Vocab 1"};
	public String[] histArray = new String[6]; // {"", "", "", "", "", ""}
	public String[] cols = {"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", "c15"};
	
	//public String[][] wordType = {{"Verbs", "Nouns", "Adjectives", "Misc"}, {"動詞", "名詞", "形容詞", "雑多詞"}};
	public String[][] lists = new String[4][4];
	
	public WordBean[] a = {wBean, wBean, wBean, wBean, wBean, wBean}; // Array of answers
	public WordBean[] b = {wBean, wBean, wBean, wBean, wBean, wBean}; // Array of buttons
	
	public ArrayList<Integer> list = new ArrayList<Integer>();
	public ArrayList<Integer> mainList = new ArrayList<Integer>();
	
	public ArrayList<String> ownLists = new ArrayList<String>();
	public ArrayList<String> splitList = new ArrayList<String>();
	public ArrayList<String> everyList = new ArrayList<String>();
						
	public ArrayList<String> rVars = new ArrayList<String>();
	public ArrayList<String> dates = new ArrayList<String>();
	public ArrayList<String> tables = new ArrayList<String>();
	public ArrayList<String> students = new ArrayList<String>();
	public ArrayList<String> resList = new ArrayList<String>();
	public ArrayList<String> hartList = new ArrayList<String>();
	
	// maybe don't need
	/*public ArrayList<String> myLangList = new ArrayList<String>();
	public ArrayList<String> knowLangList = new ArrayList<String>();
	public ArrayList<String> maybeLangList = new ArrayList<String>();
	public ArrayList<String> teachLangList = new ArrayList<String>();*/
	
	public ArrayList<String> typeList = new ArrayList<String>();
	public ArrayList<String> vocabList = new ArrayList<String>(); // for bkMeth.fillLists for japanese lists
	public ArrayList<String> checkList = new ArrayList<String>();
	public ArrayList<String> adminList = new ArrayList<String>(); // for creating the hartlists (maybe unnecessary)
	public ArrayList<String> historyList = new ArrayList<String>();
	public ArrayList<String> chapterList = new ArrayList<String>();
	//public ArrayList<String> chapNameList = new ArrayList<String>(); // changed to HashMap
	
	public ArrayList<String> adminNameList = new ArrayList<String>(); // maybe unnecessary
	
	public ArrayList<String> myList = new ArrayList<String>();
	
	public ArrayList<String> myListVerbs = new ArrayList<String>();
	public ArrayList<String> myListNouns = new ArrayList<String>();
	public ArrayList<String> myListAdjs = new ArrayList<String>();
	public ArrayList<String> myListMisc = new ArrayList<String>();
	
	public ArrayList<String> maybeList = new ArrayList<String>();
	
	public ArrayList<String> maybeListVerbs = new ArrayList<String>();
	public ArrayList<String> maybeListNouns = new ArrayList<String>();
	public ArrayList<String> maybeListAdjs = new ArrayList<String>();
	public ArrayList<String> maybeListMisc = new ArrayList<String>();
	
	public ArrayList<String> knowList = new ArrayList<String>();
	
	public ArrayList<String> knowListVerbs = new ArrayList<String>();
	public ArrayList<String> knowListNouns = new ArrayList<String>();
	public ArrayList<String> knowListAdjs = new ArrayList<String>();
	public ArrayList<String> knowListMisc = new ArrayList<String>();
	
	public ArrayList<String> teachList = new ArrayList<String>();
	
	public ArrayList<String> teachListVerbs = new ArrayList<String>();
	public ArrayList<String> teachListNouns = new ArrayList<String>();
	public ArrayList<String> teachListAdjs = new ArrayList<String>();
	public ArrayList<String> teachListMisc = new ArrayList<String>();
	
	public ArrayList<String> tempHideList = new ArrayList<String>();
	public ArrayList<String> tempStudyList = new ArrayList<String>();
	public ArrayList<String> tempStudyList2 = new ArrayList<String>();
	public ArrayList<String> tempTestList = new ArrayList<String>();
	public ArrayList<String> tempTestList2 = new ArrayList<String>();
	public ArrayList<String> tempReviewList = new ArrayList<String>();
	
	public ArrayList<WordBean> useList = new ArrayList<WordBean>();
	public ArrayList<WordBean> ansList = new ArrayList<WordBean>();
	public ArrayList<WordBean> tempList = new ArrayList<WordBean>();
	public ArrayList<WordBean> wordList = new ArrayList<WordBean>();
	public ArrayList<WordBean> printList = new ArrayList<WordBean>();
	public ArrayList<WordBean> tempHideList2 = new ArrayList<WordBean>();
	
	public ArrayList<TableRow> tableRow = new ArrayList<TableRow>();
	
	public boolean debug = false;   // whether we see extra debugging info.
	public boolean hsNew = false;
	public boolean search = false;
	public boolean correct = false;
	public boolean justHid = false;
	public boolean meaning = false;   
	
	public boolean loggedIn = false;
	public boolean wdlist = false; //true: BookMethods.startLearning proceeds; false: redirect to options.jsp
	public boolean quizList = false;
	public boolean langSet = false;
	public boolean okExamp = false;
	public boolean threeCols = false;
	public boolean selected = false;
	public boolean wholeList = false;
	public boolean registered = false;
	public boolean teachChapList = false; // find out how this is used
	
	//-------------------
	
	
	
	public SessionState() throws Exception // need an empty constructor or jsp has a problem creating a useBean; need to check this
	{     // constructor   throws SecureRandom.NoSuchAlgorithmException
		
		//sessionId = "Empty Constructor";
		//sessions.put(sessionId, this);
	}
	
	
	public SessionState(String sessID) throws ServletException, Exception
	{   
		// map = new HashMap< String, ArrayList<String> >();
		sessionId = sessID;
		// old way: sessions.put(sessionId, this);
		accessSharedHash(sessions, sessionId, this, "add");
		//createMap();
	}
        
        
        public void setLang()
        {            
            this.html.setLang(this);
            this.exp.setLang(this);
        }
	
	
	public static synchronized SessionState accessSharedHash(Hashtable hTable, String hTKey, SessionState ss, String Op) 
	{  //-- synchronized in case multiple threads running since userIDs is a shared class var.
		if (Op.equals("add"))
		{
			if (hTable.containsKey(hTKey))
			{
				return null;
			}
			else
			{
			    hTable.put(hTKey, ss);
			    return ss;
			}
		}
		else if (Op.equals("remove"))
		{
			ss = (SessionState)hTable.get(hTKey);
			hTable.remove(hTKey);
			return ss;
		}
		else if (Op.equals("get"))
		{
			return (SessionState)hTable.get(hTKey);
		}
		else
		{
			// probably in a perfect world we'd actually raise an error here
			return null;
		}
	}
	
	
	public static SessionState getInstance(String target) 
	{
		// old way: //return (SessionState)sessions.get(target);
	    return (accessSharedHash(sessions, target, null, "get"));
	}
	
	
	public boolean checkUserIds(String userid)
	{
		// old way: return userIDs.containsKey(userid);
		return (accessSharedHash(userIDs, userid, null, "get") != null);
	}
	
	
	public boolean registerUserId(String uid) 
	{  
		//-- synchronized in case multiple threads running since userIDs is a shared class var.
		return (accessSharedHash(userIDs, uid, this, "add") != null);
	}
	
	
	// old
	public void removeUserId()
	{
		// old way: userIDs.remove(userID);
		accessSharedHash(userIDs, userID, null, "remove");
                registered = false;
                loggedIn = false;
	}
	
	
	public void cleanUp() throws ServletException // , SQLException
	{
		accessSharedHash(userIDs, userID, null, "remove");
		accessSharedHash(sessions, sessionId, null, "remove");
	}	
	
	
	public void valueBound(HttpSessionBindingEvent be) 
	{
		if (be.getName().equals("Session"))
		{
		// actually, we have a constructor already,
		//  so maybe do nothing for the bind event
		}
	}
	
	
	public void valueUnbound(HttpSessionBindingEvent be) 
	{
		if (be.getName().equals("Session"))
		{   
			try
			{
				cleanUp(); // remove references in hash right away (this method is synchronized)
			}
			catch(ServletException ex) {dbgApp("ServletException: " + ex.getMessage());}
		}
	}
	
	
	// CHECK WITH GALT WHETHER THE ss SHOULD BE "THIS"
	// returns true if HashMap map contains key (my, maybe, know)
	public boolean mapKeyCheck(String key, SessionState ss)
	{
		return map.containsKey(key);
	}
	
	
	// returns true if the HashMap at key in map contains subKey
	public boolean mapKeyCheckSubKey(String key, String subKey, SessionState ss)
	{
		boolean isThere = false;
		
		HashMap listMap = map.get(key);
		
		if(listMap != null)
			isThere = listMap.containsKey((String)subKey);
		
			//ss.dbgApp("got to mapKeyCheck 1: listMap = "+listMap+", key = "+key+", subKey = "+subKey); //debug
				
		return isThere;
	}
	
	
	// returns true if any HashMap in map contains subKey
	public boolean mapKeyCheckAll(String subKey, SessionState ss)
	{
		for(HashMap listMap : map.values())
		{
			if(listMap != null)
				if(listMap.containsKey(subKey))
					return true;
		}
		
		return false;
	}
	
	
	public void addToMapVal(String key, String subKey, SessionState ss)
	{
		HashMap listMap = map.get(key);
			//ss.dbgApp("ss.addToMapVal listMap key = "+key+", subKey = "+subKey);
			
		if(listMap == null)
		{
			return;
		}
		else
			listMap.put(subKey, Integer.valueOf(subKey));
		
			//ss.dbgApp("ss.addToMapVal listMap = "+listMap);
	}
	
	
	public void removeMapElem(String key, String subKey, SessionState ss)
	{
		HashMap listMap = map.get(key);
		
		if(listMap == null)
		{
			return;
		}
		else
			listMap.remove(subKey);
	}
	
	
	public void clearMapElem(String key, SessionState ss)
	{
		HashMap< String, Integer > listMap = new HashMap< String, Integer >();
		
		if(map == null)
			return;
		else
		{
			map.remove(key);
			
				allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
				
			map.put(key, listMap);
			
			DBHandler.updateDBList(this.userID, key, "", this);
			
				allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
		}
	}
	
	
	public ArrayList< String > mapKeyToALStr(String key, SessionState ss)
	{
		ArrayList< String > aList = new ArrayList< String >();
		
		HashMap listMap = map.get(key);
		
		if(listMap != null)
		{
			Set< String > listKeys = listMap.keySet();
			
			for(String s : listKeys)
			{
				aList.add(s);
			}
		}
		
		return aList;
	} // returns an ArrayList< String > created from finding listMap in map and adding all the keys to the aList
	
	
	public ArrayList< Integer > mapKeyToALInt(String key, SessionState ss)
	{
		ArrayList< Integer > aList = new ArrayList< Integer >();
		
		HashMap listMap = map.get(key);
			
		
		if(listMap != null)
		{
			Set< String > listKeys = listMap.keySet();
			
			for(String s : listKeys)
			{
				Integer x = (Integer)listMap.get(s);
				
				if(x != null)
				{
					aList.add(x);
				}
			}
		}
			
		return aList;
	} // returns an ArrayList< Integer > created from finding listMap in map and adding all the values to the aList
	
	
	public String mapValueToCSV(String key, SessionState ss)
	{
		String o = "";
		
		HashMap listMap = map.get(key);
			
		if(listMap != null)
		{
			Set< String > listKeys = listMap.keySet();
			
			for(String s : listKeys)
			{
				if(s != null && s.length() != 0)
					o = o+s+",";
			}
		}
		
		return o;
	} // returns the csv string created from finding listMap in map and concating all the keys
	
	
	public void allMapValuesToCSV(SessionState ss)
	{
		Set< String > mapKeys = map.keySet();
		
		for(String k : mapKeys)
		{
			String o = "";
			
			HashMap listMap = map.get(k);
				
			if(listMap != null)
			{
				Set< String > listKeys = listMap.keySet();
				
				for(String s : listKeys)
				{
					if(s != null && s.length() != 0)
						o = o+s+",";
				}
			}
				
			//ss.dbgApp("ss.allMapValuesToCSV in ss.map, mapKey: "+k+" has HashMap of values: "+o);
		}
	} // used to display all of the values in the HashMaps stored in ss.map
	
	
	public void checkMap(SessionState ss)
	{
		if(map.isEmpty())
		{
			for(Lists mylist : EnumSet.range(Lists.MYLIST, Lists.MAYBELIST))
			{
				for(Types typeval : Types.values())
				{
					HashMap< String, Integer > own = new HashMap< String, Integer >();
					
					listName = mylist.getListName(typeval.getName());
					map.put(listName, own);
				}
			}
		}
		else return;
	}
	
	
	public synchronized void checkStaticVars() throws ServletException
	{
		//if(dbHand == null) dbHand = new DBHandler();
		if(mySqlUtil == null) mySqlUtil = new SqlUtil();
	}
	
	
	public void checkSave() throws ServletException
	{
        save = save % 10;
        if(save == 0) { DBHandler.saveState(this); }

        save++;
        return;
        /*
		try
		{
         * seems we don't need this
		}
		catch(SQLException ex) {dbgApp("SQLException: " + ex.getMessage());}
        */
	}
	
	
	public static void setTimeout(long timeout)
	{  // in minutes
		sesTimeout = timeout;
	}
	
	
	public static long getTimeout()
	{  // in minutes
		return sesTimeout;
	}
	
	
	public String updateRandomContext() 
	{
		return randomContextCheck = MyStrUtil.padZerosLeft(String.valueOf(Math.abs(random.nextLong())).substring(0,10),10);
	}
	
	
	public boolean verifyRandomContext(String rc) { return (rc.compareTo(randomContextCheck) == 0); }
	
	
	// for loggedOn.jsp
	
	
	public String[] getUserIDHashKeys()
	{
		int x = 0;
		String setArray[];
		
		Set set = userIDs.keySet();
		Iterator setIter = set.iterator();
		
		setArray = new String[set.size()];
		
		while(setIter.hasNext())
		{
			setArray[x] = (String) setIter.next();
			x++;
		}
		
		return setArray;
	}
	
	
	// old stuff
	public String[] getSessHashKeys()
	{
		int x = 0;
		String setArray[];
		
		Set set = getKeySet(sessions);
		Iterator setIter = set.iterator();
		
		setArray = new String[set.size()];
		
		while(setIter.hasNext())
		{
			setArray[x] = (String) setIter.next();
			x++;
		}
		
		return setArray;
	}
	
	
	// old junk
	public static synchronized String removeOldSessions(Hashtable hTable, String hTKey)
	{
		SessionState ss = null;
		
		ss = getInstance(hTKey);
		
		if(ss == null) 
		{
			hTable.remove(hTKey);
			return "Session Removed";
		}
		else return hTKey;
	}
	
	
	// old stuff
	private Set getKeySet(Hashtable hTable)
	{
		Set set = hTable.keySet();
		return set;
	}
	
	
	// old
	public synchronized void destroy() 
	{
		sessions.clear();
		userIDs.clear();
	}
	
	
	/*public synchronized void finalize() 
	{
		sessions.clear();
		userIDs.clear();
	}*/
	
	
	
	// My Stuff
	
	
	public void setLists()
	{
		if(type.equals("verb")) {myList = myListVerbs; maybeList = maybeListVerbs; knowList = knowListVerbs; teachList = teachListVerbs;}
		else if(type.equals("noun")) {myList = myListNouns; maybeList = maybeListNouns; knowList = knowListNouns; teachList = teachListNouns;}
		else if(type.equals("adjective")) {myList = myListAdjs; maybeList = maybeListAdjs; knowList = knowListAdjs; teachList = teachListAdjs;}
		else if(type.equals("misc")) {myList = myListMisc; maybeList = maybeListMisc; knowList = knowListMisc; teachList = teachListMisc;}
	}
	
	// sets the type for myList, maybeList, knowList, etc.
	public void setTypes()
	{
		if(type.equals("verb")) {myListVerbs = myList; maybeListVerbs = maybeList; knowListVerbs = knowList; teachListVerbs = teachList;}
		else if(type.equals("noun")) {myListNouns = myList; maybeListNouns = maybeList; knowListNouns = knowList; teachListNouns = teachList;}
		else if(type.equals("adjective")) {myListAdjs = myList; maybeListAdjs = maybeList; knowListAdjs = knowList; teachListAdjs = teachList;}
		else if(type.equals("misc")) {myListMisc = myList; maybeListMisc = maybeList; knowListMisc = knowList; teachListMisc = teachList;}
	}
	
	
	public void cleanSlate()
	{
		myList = new ArrayList<String>();
		myListVerbs = new ArrayList<String>();
		maybeListVerbs = new ArrayList<String>();
		knowListVerbs = new ArrayList<String>();
		teachListVerbs = new ArrayList<String>();
		
		myListNouns = new ArrayList<String>();
		maybeListNouns = new ArrayList<String>();
		knowListNouns = new ArrayList<String>();
		teachListNouns = new ArrayList<String>();
		
		myListAdjs = new ArrayList<String>();
		maybeListAdjs = new ArrayList<String>();
		knowListAdjs = new ArrayList<String>();
		teachListAdjs = new ArrayList<String>();
		
		myListMisc = new ArrayList<String>();
		maybeListMisc = new ArrayList<String>();
		knowListMisc = new ArrayList<String>();
		teachListMisc = new ArrayList<String>();
	}
	
	/*public void setListSelections()
	{
		for(int x = 0; x < option.length; x++)
		{
			if(select[x].equals(prevSelect))
			{option[x] = "<option selected>"+select[x]+"</option>";}
			else
			{option[x] = "<option>"+select[x]+"</option>";}
		}
		
		for(int x = 0; x < typOpt.length; x++)
		{
			if(wordType[x].equals(prevType))
			{typOpt[x] = "<option selected>"+wordType[x]+"</option>";}
			else
			{typOpt[x] = "<option>"+wordType[x]+"</option>";}
		}
	}*/
	
	
	public void stringScores()
	{
		long ts = TimeStarted.getTimeInMillis();
		long tn = TimeNow.getTimeInMillis();
		long min = (tn-ts)/60000;
		studMin = (int)min;
		
		String tStart = TimeStarted.getTime().toString();
		String studTime = String.valueOf(studMin);
		
		String perCorRev = String.valueOf(percentCorrectRev);
		String perCorTest = String.valueOf(percentCorrectTest);
		
		String totPossRev = String.valueOf(totalPossibleRev);
		String totPossTest = String.valueOf(totalPossibleTest);
		
		scores = tStart+","+studTime+","+perCorRev+","+totPossRev+","+perCorTest+","+totPossTest;
	}
	
	
	public void stringHistory() 
	{
		if(scores == null || scores.length() == 0) 
		{
			stringScores();
			scores = "Scores;" + scores;
		}
		if(history == null) {history = "History";}
		
		history = history +";"+ scores; 
	}
	
	
	public void clearHistory()
	{
		studMinPrev = 0;
		perRevPrev = 0;
		totRevPrev = 0;
		perTestPrev = 0;
		totTestPrev = 0;
		countRev = 0;
		countTest = 0;
	}
	
	
// My methods from the Beans.
	public void setTable(String t) {table = t;}
	
	public String getTable() {return table;}
	
	/*public void setDriver(String d) {driver = d;}*/
	
	//public String getDriver() {return DRIVER;}
		
	/*public void setUrl(String u) {url = u;}*/
	
	//public String getUrl() {return URL;}
		
	/*public void setUsername(String un) {username = un;}*/
	
	//public String getUsername() {return USERNAME;}
		
	/*public void setPassword(String p) {password = p;}*/
	
	//public String getPassword() {return PASSWORD;}
	
	public void setTyp(String t) {type = t;}
	
	public String getTyp() {return type;}
	
	//public void setMeaning(String m) {meaning = m;}
	
	public String getMeaning() 
	{
		String setMean = "";
		if(meaning) setMean = "E"+reading;
		else setMean = reading+"E";
		
		return setMean;
	}
	
	public void setMessage(String mess) {message = mess;}
	
	public String getMessage() {return message;}
	
	public void setID(int i) {id = i;}
	
	public int getID() {return id;}
	
	public void setAnsID(int i) {idAns2 = i;}
	
	public int getAnsID() {return idAns2;}
			
	public void setEng(String e) {eng = e;}
	
	public String getEng() {return eng;}
	
	public void setKan(String k) {kan = k;}
	
	public String getKan() {return kan;}
	
	public void setYom(String y) {yom = y;}
	
	public String getYom() {return yom;}
	
	public void setAns(String a) {answer = a;}
	
	public String getAns() {return answer;}
	
	public void setEngAns(String e) {engAns = e;}
	
	public String getEngAns() {return engAns;}
	
	public void setKanAns(String k) {kanAns = k;}
	
	public String getKanAns() {return kanAns;}
	
	public void setYomAns(String y) {yomAns = y;}
	
	public String getYomAns() {return yomAns;}
		
	public void setGuess(String g) {guess = g;}
	
	public String getGuess() {return guess;}
	
	public void setBut1(String b) {but1 = b;}
	
	public String getBut1() {return but1;}
	
	public void setBut2(String b) {but2 = b;}
	
	public String getBut2() {return but2;}
	
	public void setBut3(String b) {but3 = b;}
	
	public String getBut3() {return but3;}
	
	public void setBut4(String b) {but4 = b;}
	
	public String getBut4() {return but4;}
	
	public void setBut5(String b) {but5 = b;}
	
	public String getBut5() {return but5;}
	
	public void setBut6(String b) {but6 = b;}
	
	public String getBut6() {return but6;}
	
	public void setBut7(String b) {but7 = b;}
	
	public String getBut7() {return but7;}
	
	public void dbgApp(String msg) {debugString += msg + '\n';}
}
