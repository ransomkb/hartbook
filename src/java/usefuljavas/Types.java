// enum class created 2007-06-06

// this enum helps set variables for all of the standard lists

package usefuljavas;

public enum Types
{
	VERBS(0, "verb", "Verbs", "動詞"),
	NOUNS(1, "noun", "Nouns", "名詞"),
	ADJS(2, "adjective", "Adjectives", "形容詞"),
	MISC(3, "misc", "Misc", "雑多詞");
	
	private final int x;
	private final String name;
	private final String english;
	private final String japanese;
	
	Types(int i, String nam, String eng, String jpn)
	{
		x = i;
		name = nam;
		english = eng;
		japanese = jpn;
	}
	
	public int getTypNum()
	{
		return x;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getTypValue(int language)
	{
		if(language == 1)
			return japanese;
		else 
			return english;
	}
}
