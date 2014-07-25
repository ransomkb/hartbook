// enum class created 2008-01-14

// this enum helps set variables for all of the lists

package usefuljavas;

public enum Lists
{
	EIKEN1(0, "eiken1", "Step 1", "英検1級"),
	EIKENPRE1(1, "eikenPre1", "Step Pre 1", "英検準1級"),
	EIKEN2(2, "eiken2", "Step 2", "英検2級"),
	EIKENPRE2(3, "eikenPre2", "Step Pre 2", "英検準2級"),
	EIKEN3(4, "eiken3", "Step 3", "英検3級"),
	EIKEN4(5, "eiken4", "Step 4", "英検4級"),
	MYLIST(6, "myList", "My List", "私のリスト"), 
	KNOWLIST(7, "knowList", "Know List", "知っているリスト"),
	MAYBELIST(8, "maybeList", "Maybe Know List", "たぶん知っているリスト"),
	KOKUGO(9, "kokugo", "Kokugo", "国語"),
	TEMPHIDE(10, "tempHide", "Temporary Hide", "一時的に隠す"),
	JTEST1(11, "jTest1", "JTest 1", "日本語テスト1");
	
	// BUSINESS1(12, "business1", "Business 1", "企業1")
	// TEACHLIST(9, "teachList", "Teacher List", "先生のリスト"),,
	// STUDYLIST(12, "studyList", "Study List", "勉強リスト"),
	// TESTLIST(13, "testList", "Test List", "テストリスト")
	
	private final int x;
	private final String name;
	private final String english;
	private final String japanese;
	
	private final String[][] listNames = {
		{"eiken1Verbs", "eiken1Nouns", "eiken1Adjs", "eiken1Misc"},
		{"eikenPre1Verbs", "eikenPre1Nouns", "eikenPre1Adjs", "eikenPre1Misc"},
		{"eiken2Verbs", "eiken2Nouns", "eiken2Adjs", "eiken2Misc"},
		{"eikenPre2Verbs", "eikenPre2Nouns", "eikenPre2Adjs", "eikenPre2Misc"},
		{"eiken3Verbs", "eiken3Nouns", "eiken3Adjs", "eiken3Misc"},
		{"eiken4Verbs", "eiken4Nouns", "eiken4Adjs", "eiken4Misc"},
		{"myVerbs", "myNouns", "myAdjs", "myMisc"},
		{"knowVerbs", "knowNouns", "knowAdjs", "knowMisc"},
		{"maybeVerbs", "maybeNouns", "maybeAdjs", "maybeMisc"}};
		
		// {"teachVerbs", "teachNouns", "teachAdjs", "teachMisc"}
	
	Lists(int i, String nam, String eng, String jpn)
	{
		x = i;
		name = nam;
		english = eng;
		japanese = jpn;
	}
	
	public int getListNum()
	{
		return x;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getListValue(int language)
	{
		if(language == 1)
			return japanese;
		else 
			return english;
	}
	
	public String getListName(String type)
	{
		if(type.equals("verb")) 
			return listNames[getListNum()][0];
		else if(type.equals("noun")) 
			return listNames[getListNum()][1];
		else if(type.equals("adjective")) 
			return listNames[getListNum()][2];
		else if(type.equals("misc")) 
			return listNames[getListNum()][3];
		else return "Invalid Type";
	}
}
