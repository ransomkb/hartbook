// Altered on 2006-05-21

// A Class that provides Buttons for Book classes.

package usefuljavas;


public final class Buttons
{
	
	private static String style = "class='button-lg-mid'", styleReview = "class='review_button-lg-mid'";
	
	// UNNECESSARY?
	public static String adjustAnsFontClass(String answer, SessionState ss)
	{
		String styleReview1 = "class='focus_word'";
		
		if(answer.length() < 10)
			styleReview1 = "class='focus_word-short'";
		else if(answer.length() < 15)
			styleReview1 = "class='focus_word-med'";
		else if(answer.length() <= 22)
			styleReview1 = "class='focus_word-long'";
		else if(answer.length() > 22)
			styleReview1 = "class='focus_word-very-long'";
		
		return styleReview1; // = "class='review_button-lg-mid'";
	}
	

	// UNNECESSARY?
	private static  String adjustFont(String button, SessionState ss)
	{
		String styleReview2 = "class='review_button-lg-mid'";
		
		if(button.length() < 10)
			styleReview2 = "class='review_button-lg-mid-short'";
		else if(button.length() < 15)
			styleReview2 = "class='review_button-lg-mid-med'";
		else if(button.length() < 20)
			styleReview2 = "class='review_button-lg-mid-long'";
		else if(button.length() <= 25)
			styleReview2 = "class='review_button-lg-mid-very-long'";
		
		return styleReview2; // = "class='review_button-lg-mid'";
	}
	

	// UNNECESSARY?
	public static String roundButton(String button, SessionState ss)
	{
		String roundBut = "<td align='center'><table cellpadding='0' cellspacing='0' border='0'><tr>"+
			"<td class='button-lg-left' cellpadding='1'>&nbsp;</td>"+
			"<td class='button-lg-mid'>"+button+"</td>"+
			"<td class='button-lg-right' cellpadding='1'>&nbsp;</td>"+
			"</tr></table></td>"; // cellpadding='1'
			
		return roundBut;
	}
	
	
	public static String roundButtonReview(String button, SessionState ss)
	{
		String roundBut = "<table align='center' cellpadding='0' cellspacing='0' border='0' ><tr>"+
			"<td class='review_button-lg-left' cellpadding='1'>&nbsp;</td>"+
			"<td class='review_button-lg-mid'>"+button+"</td>"+
			"<td class='review_button-lg-right' cellpadding='1'>&nbsp;</td></tr></table>";
			
		return roundBut;
	}

    // returns input value=Display
	public static String getBut1(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='display' value='Display' "+style+" />",
			"<input type='submit' name='display' value='表示' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Search
	public static String getBut2(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='search' value='Search' "+style+" />",
			"<input type='submit' name='search' value='検索' "+style+" />"};
		return s[ss.lang];
	}

    // Double with getBut1
	// returns input value=Display
	public static String getBut3(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='display' value='Display' "+style+" />",
			"<input type='submit' name='display' value='表示' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Add Row
	public static String getBut4(SessionState ss)
	{
		String[] s =  {"<input type=submit name='addrow' value='Add Row' "+style+" />",
			"<input type=submit name='addrow' value='リストに追加' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Update
	public static String getBut5(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='update' value='Update' "+style+" />",
			"<input type='submit' name='update' value='更新' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Edit
	public static String getBut6(SessionState ss)
	{
		String[] s =  {"<input type=submit name='edit' value='Edit' "+style+" />",
			"<input type=submit name='edit' value='編集' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Start Study
	public static String getBut7(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='learn' value='Start Study' "+styleReview+" />",
			"<input type='submit' name='learn' value='勉強スタート' "+styleReview+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Start Review
	public static String getBut8(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='learn' value='Start Review' "+styleReview+" />",
			"<input type='submit' name='learn' value='復習スタート' "+styleReview+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Start Test
	public static String getBut9(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='learn' value='Start Test' "+styleReview+" />",
			"<input type='submit' name='learn' value='テストスタート' "+styleReview+" />"};
		return s[ss.lang];
	}
	
	// returns input value=ADD; used in Sample.jsp & add_popup.jsp
	public static String getBut10(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='addwordsets' value='Add' "+style+" />",
			"<input type='submit' name='addwordsets' value='追加' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Clear
	public static String getBut11(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='clear' value='Clear' "+style+" />",
			"<input type='submit' name='clear' value='消去する' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Next Set
	public static String getBut12(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='nextstudy' value='Next Set' "+style+" />",
			"<input type='submit' name='nextstudy' value='次のセット' "+style+" />"};
		return s[ss.lang];
	}
	
	// Review button 1
	public static String getBut13(SessionState ss)
	{
		return "<input type='submit' name='guess' value=\""+ss.getBut1()+"\" "+adjustFont(ss.getBut1(), ss)+" "+System.currentTimeMillis()+"';\" />";
	}
	
	// Review button 2
	public static String getBut14(SessionState ss)
	{
		return "<input type='submit' name='guess' value=\""+ss.getBut2()+"\" "+adjustFont(ss.getBut2(), ss)+" "+System.currentTimeMillis()+"';\" />";
	}
	
	// Review button 3
	public static String getBut15(SessionState ss)
	{
		return "<input type='submit' name='guess' value=\""+ss.getBut3()+"\" "+adjustFont(ss.getBut3(), ss)+" "+System.currentTimeMillis()+"';\" />";
	}
	
	// Review button 4
	public static String getBut16(SessionState ss)
	{
		return "<input type='submit' name='guess' value=\""+ss.getBut4()+"\" "+adjustFont(ss.getBut4(), ss)+" "+System.currentTimeMillis()+"';\" />";
	}
	
	// Review button 5
	public static String getBut17(SessionState ss)
	{
		return "<input type='submit' name='guess' value=\""+ss.getBut5()+"\" "+adjustFont(ss.getBut5(), ss)+" "+System.currentTimeMillis()+"';\" />";
	}
	
	// Review button 6
	public static String getBut18(SessionState ss)
	{
		return "<input type='submit' name='guess' value=\""+ss.getBut6()+"\" "+adjustFont(ss.getBut6(), ss)+" "+System.currentTimeMillis()+"';\" />";
	}
	
	// returns input value=Move
	public static String getBut19(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='move' value='Move' "+style+" />",
			"<input type='submit' name='move' value='移動' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Next Test
	public static String getBut20(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='nexttest' value='Next Test' "+style+" />",
			"<input type='submit' name='nexttest' value='次のテスト' "+style+" />"};
		return s[ss.lang];
	}
	
	// do not change this
    // returns input value=some variable
	public static String getBut21(SessionState ss)
	{
            String str = "<input type='submit' name='language' value='"+ss.html.getLang()+"' class='button-lg-mid' >"+
                "<input type='hidden' name='srcpage' value='"+ss.html.getSrcPage()+"'></td>";
            
            return str;
            /*
		String[] s =  {"<input type='submit' name='japanese' value='日本語' valign='top' class='button-lg-mid' />",
			"<input type='submit' name='english' value='English' class='button-lg-mid' />"};
		return s[ss.lang];
             */
	}


	// Double getBut19 is also named move, so check if 19 and 22 conflict
    // returns input value=Move
	public static String getBut22(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='move' value='Move' "+style+" />",
			"<input type='submit' name='move' value='移動' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Get Table; No J so probably just for admin
	public static String getBut23(SessionState ss)
	{
		return "<input type='submit' value='Get Table' "+style+" />";
	}
	
	// returns input value=Remove
	public static String getBut24(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='remove' value='Remove' "+style+" />",
			"<input type='submit' name='remove' value='取り除く' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Get Quiz
	public static String getBut25(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='setquiz' value='Get Quiz' "+style+" />",
			"<input type='submit' name='setquiz' value='クイズを作る' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Print Quiz Page
	public static String getBut26(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='printquiz' value='Print Quiz Page' "+style+" />",
			"<input type='submit' name='printquiz' value='クイズページを印刷する' "+style+" />"};
		return s[ss.lang];
	}
	
	// needs to be translated / checked
    // returns input value=Print Answers Page
	public static String getBut27(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='printanswers' value='Print Answers Page' "+style+" />",
			"<input type='submit' name='printanswers' value='回答ページを印刷する' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=See Students History; used in allHistories.jsp
	public static String getBut28(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='oneHist' value='See Student's History' "+style+" />",
			"<input type='submit' name='oneHist' value='生徒の履歴を見る' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Submit
	public static String getBut29(SessionState ss)
	{
		String[] s =  {"<input type='submit' value='Submit' "+style+" />",
			"<input type='submit' value='出す' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=GO
	public static String getBut30(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='go' value='GO' "+style+" />",
			"<input type='submit' name='go' value='始め' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=Approve
	public static String getBut31(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='approve' value='Approve' "+style+" />",
			"<input type='submit' name='approve' value='認める' "+style+" />"};
		return s[ss.lang];
	}
	
	
	// maybe needs to be translated; probably just admin
    // returns input value=Get Repeats
	public static String getBut32(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='getRepeats' value='Get Repeats' "+style+" />",
			"<input type='submit' name='getRepeats' value='Get Repeats' "+style+" />"};
		return s[ss.lang];
	}
	
	// returns input value=History
	public static String getBut33(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='history' value='History' "+style+" />",
			"<input type='submit' name='history' value='履歴' "+style+" />"};
		return s[ss.lang];
	}
	
	
	// needs to be translated
    // returns input value=Switch
	public static String getBut34(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='switchTable' value='Switch' "+style+" />",
			"<input type='submit' name='switchTable' value='Switch' "+style+" />"};
		return s[ss.lang];
	}
	
	
	// needs to be translated
    // Important: same value as getBut36; maybe can use for both?
    // returns input value=Change; for changing T (Table?) Names
	public static String getBut35(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='changeTName' value='Change' "+style+" />",
			"<input type='submit' name='changeTName' value='Change' "+style+" />"};
		return s[ss.lang];
	}
	
	
	// needs to be translated
    // returns input value=Change; for changing C (Chapter?) Names
	public static String getBut36(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='changeCName' value='Change' "+style+" />",
			"<input type='submit' name='changeCName' value='Change' "+style+" />"};
		return s[ss.lang];
	}
	
	
	// needs to be translated
    // returns input value=Shift
	public static String getBut37(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='shiftCol' value='Shift' "+style+" />",
			"<input type='submit' name='shiftCol' value='Shift' "+style+" />"};
		return s[ss.lang];
	}
	
	
	// needs to be translated
    // returns input value=Create
	public static String getBut38(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='createTable' value='Create' "+style+" />",
			"<input type='submit' name='createTable' value='Create' "+style+" />"};
		return s[ss.lang];
	}
	
	
	// needs to be translated
    // returns input value=NOTYETSET
	public static String getBut(SessionState ss)
	{
		String[] s =  {"<input type='submit' name='' value='' "+style+" />",
			"japanese<input type='submit' name='' value='' "+style+" />"};
		return s[ss.lang];
	}
	
}
