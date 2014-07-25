// Altered on 2006-06-18
// A Class that provides sets and gets for just
// the id, typ, english, kanji and reading variables.

package usefulbeans;
	
public class WordBean
{
	public int id = -1, edit = 0, listID = 0;
	public String rowID = "";
	public String typ = "Nothing", eng = "Nothing", kan = "Nothing", yom = "Nothing", mov = "Nothing";
	public String bus = "Nothing", eiken = "Nothing", jtest = "Nothing", teachList = "Not in List";
    public String engEx = "No Example Sentence.", langEx = "No Example";
	
	public String getWord(int x)
	{
		String word = "eng";
		
		if(x == 0)
		{word = eng;}
		else if (x == 1)
		{word = kan;}
		else if (x == 2)
		{word = yom;}
	
		return word;
	}

	public void setID(int i) {id = i;}
	
	public int getID() {return id;}	
	
	public void setListID(int i) {listID = i;}
	
	public int getListID() {return listID;}	
	
	public void setTyp(String t) {typ = t;}
	
	public String getTyp() {return typ;}
	
	public void setEng(String e) {eng = e;}
	
	public String getEng() {return eng;}
		
	public void setKan(String k) {kan = k;}
	
	public String getKan() {return kan;}
		
	public void setYom(String y) {yom = y;}
	
	public String getYom() {return yom;}
		
	public void setHid(String m) {mov = m;}
	
	public String getHid() {return mov;}

    public void setEngEx(String ex) {engEx = ex;}

    public String getEngEx() {return engEx;}

    public void setLangEx(String ex) {langEx = ex;}

    public String getLangEx() {return langEx;}
}
