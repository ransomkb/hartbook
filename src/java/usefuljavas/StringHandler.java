// Created on 2007-07-08
// A Class that provides ways to manipulate csv strings and ArrayLists

package usefuljavas;

import java.util.*;


public class StringHandler
{
	
	// for assigning ArrayList<String> elements to a ArrayList<Integer> 
	public static ArrayList<Integer> makeList(ArrayList<String> sList, SessionState ss)
	{
		ArrayList<Integer> iList = new ArrayList<Integer>();
		
		for(int i = 0; i < sList.size(); i++)
		{
			iList.add(Integer.parseInt(sList.get(i)));
		}
		
		return iList;
	}
	
	
	public static ArrayList<String> setIntList(String typString)
	{
		ArrayList<String> intList = new ArrayList<String>();
		String[] typList = typString.split(",");
		
		for(int i = 0; i < typList.length; i++)
		{
			if(!intList.contains(typList[i])) {intList.add(typList[i]);}
		}
		
		return intList;
	}
	
	
	public static String adjustString(String str)
	{
		if((str.length() == 0) || (str == null)) { return "NG"; }
		
		str.trim();
		//str.toLowerCase(); problem for creating chapter lists
		
		if(str.contains(",") || str.contains(".")) { return "NG"; }
		
		if(str.contains("'")) { str = str.replace("'", "''"); }
		
		if(str.contains("\"")) { str = str.replace("\"", "''"); }
		
		if(str.contains("\\")) { str = str.replace("\\", "\\\\"); }
		
		return str;
	}
	
	
	// Not Sure Where This Is Used
	public static String adjustStringA(String str)
	{
		if((str.length() == 0) || (str == null)) { return "NG"; }
		
		str.trim();
		
		// maybe need to make the next line available?
		str.toLowerCase(); // problem for creating chapter lists
		
		if(str.contains(",")) { return "NG"; }
		
		if(str.contains("'")) { str = str.replace("'", "''"); }
		
		if(str.contains("\"")) { str = str.replace("\"", "''"); }
		
		if(str.contains("\\")) { str = str.replace("\\", "\\\\"); }
		
		return str;
	}
	
	
	public static String addTo(String str, SessionState ss)
	{
		if(str.startsWith("to ")) return str;
		else 
		{
			str = "to "+str;
			return str;
		}
	}
	
	
	// check with GALT if need ss
	// builds a String csv from an ArrayList<Integer>
	public static String buildCSV(ArrayList<Integer> aList, SessionState ss)
	{
		String o = "", s = "";
		
		for(int i : aList)
		{
			//s = String.valueOf(i);
			//o = o + "," + s;
			o = o + "," + String.valueOf(i);
		}
		
		return o;
	}
	
	
	// replaces adminString & chapterString
	// for creating a ; separated value list from an ArrayList
	public static String makeFinalString(ArrayList<String> aList, SessionState ss)
	{
		String finalString = "";
		
		for(String s : aList)
		{
			finalString = finalString + s + ";";
		}
		
			//ss.dbgApp("got to chapterString 1: ss.chapNameList = "+ss.chapNameList+", ss.chapterList = "+ss.chapterList); //debug
		
		return finalString;
	}
	
	
	// replaces subChapString
	// for creating and returning a csv list with the list name and String nums from ArrayList<Integer> iList
	public static String buildNameCSV(String name, ArrayList<Integer> iList, SessionState ss)
	{
		String finalString = "";
		
		for(int x : iList)
		{
			finalString = finalString + String.valueOf(x) + ",";
		}
		
		finalString = name + "," + finalString;
		
		return finalString;
	}
	
	// returns ArrayList<String> splitList which is filled with elements split around semi-colons; used in CalculateHistory.fetchHistoryList();
	public static ArrayList<String> splitFirstString(String str)
	{
		ArrayList<String> splitList = new ArrayList<String>();
		
		String[] firstSplit = str.split(";");
		
		for(int i = 0; i < firstSplit.length; i++)
		{
			String list = firstSplit[i];
			splitList.add(list);
		}
		
		return splitList;
	}
	
	// returns array secSplit after splitting the passed string around commas; used in CalculateHistory.getHistoryList();
	public static String[] splitSecondString(String str)
	{
		String[] secSplit = str.split(",");
		
		return secSplit;
	}
	
	
	// replaces findAdminString & findChapString
	// finds the given name in the ArrayList<String> aList by splitting each string,
	// then assigns all of that name's String nums to ArrayList<Integer> iList and returns it
	public static ArrayList<Integer> setArrayListInteger(String name, ArrayList<String> aList, SessionState ss) // used to add ss.chapter-string IDs to ss.list, which will be fetched
	{
		for(String str : aList)
		{
			String[] c = splitSecondString(str);
			
			if(c[0].equals(name)) 
			{
				ArrayList<Integer> iList = new ArrayList<Integer>();
				
				for(int i = 1; i < c.length; i++)
				{
					if(c[i].length() != 0 || c[i] != null)
					{
						int x = Integer.parseInt(c[i]);
						ss.list.add(x);
					}
				}
				return iList;
			}
		}
		ss.message = Messages.getMess28(ss);
		return null;
	}
	
	
	// replaces buildAdminString & buildChapString
	// finds the name in ArrayList<String> aList by splitting each string,
	// then adds a String num to all String nums and reassigns the new string to the name position
	public static ArrayList<String> addToALString(int k, String name, ArrayList<String> aList, SessionState ss) // used to add an ID to a teacher's list
	{
		for(int m = 0; m < aList.size(); m++)
		{
			String str = aList.get(m);
			String[] c = splitSecondString(str);
			
			if(c[0].equals(name)) 
			{
				String strInt = String.valueOf(k);
				
				for(int i = 0; i < c.length; i++) 
				{
					if(c[i].length() != 0 && c[i] != null)
					{
						if(c[i].equals(strInt)) return aList; // if already in list, don't need to add it.
						else if(c[i].equals("remove")) continue; // probably, this is used by the Remove button in EditRows
					}
				}
				
				str = str + strInt + ",";
				
				aList.set(m, str);
				
				ss.message = Messages.getMess28(ss);
			}
		}
		
			//ss.dbgApp("got to buildAdminString 1: ss.adminNameList = "+ss.adminNameList+", ss.adminList = "+ss.adminList); //debug
		
		return aList;
	}
	
	
	// finds the admin name in the adminList by splitting each string,
	// then removes a String num from all String nums and reassigns the new string to the admin name position
	public static ArrayList<String> rebuildALString(String name, ArrayList<String> aList, SessionState ss) // used to remove an ID from a teacher's list
	{
		for(int m = 0; m < aList.size(); m++)
		{
			String str = aList.get(m);
			String[] c = splitSecondString(str);
			str = name+",";
			
			if(c[0].equals(name)) 
			{
				for(int i = 1; i < c.length; i++) 
				{
					if(c[i].length() != 0 && c[i] != null)
					{
						int x = Integer.parseInt(c[i]);
						
						if(x == ss.selection) continue;
						
						str = str + c[i] + ",";
					}
				}
				
				aList.set(m, str);
			}
		}
		ss.message = Messages.getMess28(ss);
		
		return aList;
	}
	
	
	// used to add ss.selection to ss.mainList
	public static String rebuildMainList(SessionState ss)
	{
		String str = "";
			//ss.dbgApp("got to rebuildMainList 1: ss.listName = "+ss.listName+", ss.selection = "+ss.selection+", string value = "+String.valueOf(ss.selection)); //debug
				
		if(!ss.mainList.contains((Integer)ss.selection)) 
		{
			String select = String.valueOf(ss.selection);
				//ss.dbgApp("got to rebuildMainList 2: ss.listName = "+ss.listName+", ss.selection = "+ss.selection+", string value = "+select); //debug
				
			ss.mainList.add((Integer)ss.selection);
		}
		
		for(int x : ss.mainList)
		{
			str = str + String.valueOf(x) + ",";
		}
		
		
		// CHANGE THIS MESSAGE probably don't need
		//ss.message = ss.mess.getMess28(ss); // says Row added to chapter list
		// no good for Adding and Admin
		
		return str;
	}
	
	
	// used to remove ss.selection from ss.mainList
	public static String removeMainList(SessionState ss)
	{
		String str = "";
		
		for(int m = 0; m < ss.mainList.size(); m++)
		{
			int x = ss.mainList.get(m);
			
			if(x == ss.selection) 
			{
				ss.mainList.remove(m);
			}
		}
		
		for(int x : ss.mainList)
		{
			str = str + String.valueOf(x) + ",";
		}
		// CHANGE THIS MESSAGE
		ss.message = Messages.getMess35(ss);
		
		return str;
	}
	
	
	// TRY TO CHANGE THIS TO GET ALL THE NAMES FROM TABLE LISTS
	// may be unnecessary
	// creates the adminNameList by splitting the AdminList
	public static void fillNameList(SessionState ss) // used to create an ArrayList full of just the names of the chapters (and place them in alphabetical order)
	{
		String admin = "";
		
		TreeSet<String> tree = new TreeSet<String>();
		
		ss.adminNameList = new ArrayList<String>();
		
		for(String str : ss.adminList)
		{
			String[] c = splitSecondString(str);
			
			admin = c[0];
			tree.add(admin);
		}
			//ss.dbgApp("got to fillAdminNameList 1: tree = "+tree+", ss.adminNameList = "+ss.adminNameList+", ss.adminList = "+ss.adminList); //debug
		
		for(String s : tree)
		{
			ss.adminNameList.add(s);
		}
			//ss.dbgApp("got to fillAdminNameList 2: tree = "+tree+", ss.adminNameList = "+ss.adminNameList); //debug
	}
}
