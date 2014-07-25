// Altered on 2007-02-14

// A Class that provides Help Messages for Book classes.

package usefuljavas;


public final class HelpMess 
{   
    
    public static String getHelpMess(SessionState ss)
    {        
	// HOME(0), GETLIST(1), STUDY(2), REVIEW(3), TEST(4), EDITROWS(5), DISPLAY(6), HISTORY(7), QUIZ(8);
        String helpMe = "";

        if(ss.helpInt == 0)
        {// HOME(0)
            return helpMe = "<!-- Home, About Links --><tr><td>"+
                    "<p class='study_word'>"+getHelp2A(ss)+
                    "</p><p class='p'>"+getHelp66(ss)+
                    "</p><p class='p'>"+getHelp67(ss)+
                    "</p><p class='study_word'>"+getHelp2(ss)+
                    "</p><p class='p'>"+getHelp3(ss)+
                    "</p><p class='p'>"+getHelp6(ss)+
                    "</p><p class='p'>"+getHelp7(ss)+
                    "</p><p class='p'>"+getHelp8(ss)+
                    "</p><p class='p'>"+getHelp108(ss)+
                    "</p></td></tr>";
        }//
        else if(ss.helpInt == 1)
        {//GETLIST(1)
            return helpMe = "<!-- About Learning Opts --><tr><td>"+
                    "<p class='study_word'>"+getHelp9(ss)+
                    "</p><p class='p'>"+getHelp68(ss)+
                    "</p><p class='p'>"+getHelp69(ss)+
                    "</p><p class='p'>"+getHelp70(ss)+
                    "</p><p class='p'>"+getHelp71(ss)+
                    "</p><p class='p'>"+getHelp72(ss)+
                    "</p></td></tr>";
        }
        else if(ss.helpInt == 2)
        {//STUDY(2)
            return helpMe = "<!-- About Study --><tr><td>"+
                    "<p class='study_word'>"+getHelp20(ss)+
                    "</p><p class='p'>"+getHelp73(ss)+
                    "</p><p class='p'>"+getHelp74(ss)+
                    "</p><p class='p'>"+getHelp75(ss)+
                    "</p></td></tr>";
        }
        else if(ss.helpInt == 3)
        {//REVIEW(3)
            return helpMe = "<!-- About Review --> <tr><td>"+
                    "<p class='study_word'>"+getHelp28(ss)+
                    "</p><p class='p'>"+getHelp76(ss)+
                    "</p><p class='p'>"+getHelp77(ss)+
                    "</p><p class='p'>"+getHelp78(ss)+
                    "</p><p class='p'>"+getHelp79(ss)+
                    "</p></td></tr>";
        }
        else if(ss.helpInt == 4)
        {//TEST(4)
            return helpMe = "<!-- About Test --><tr><td>"+
                    "<p class='study_word'>"+getHelp23(ss)+
                    "</p><p class='p'>"+getHelp80(ss)+
                    "</p><p class='p'>"+getHelp81(ss)+
                    "</p><p class='p'>"+getHelp82(ss)+
                    "</p><p class='p'>"+getHelp83(ss)+
                    "</p><p class='p'>"+getHelp84(ss)+
                    "</p></td></tr>";
        }
        else if(ss.helpInt == 5)
        {//EDITROWS(5)
            return helpMe = "<!-- About Edit Rows --><tr><td>"+
                    "<p class='study_word'>"+getHelp50(ss)+
                    "</p><p class='p'>"+getHelp89(ss)+
                    "</p><p class='p'>"+getHelp90(ss)+
                    "</p><p class='p'>"+getHelp91(ss)+
                    "</p><p class='p'>"+getHelp92(ss)+
                    "</p><p class='p'>"+getHelp93(ss)+
                    "</p><p class='p'>"+getHelp94(ss)+
                    "</p><p class='p'>"+getHelp95(ss)+
                    "</p><p class='p'>"+getHelp96(ss)+
                    "</p><p class='p'>"+getHelp97(ss)+
                    "</p></td></tr>";
        }
        else if(ss.helpInt == 6)
        {//DISPLAY(6)
            return helpMe = "<!-- About Display Lists --><tr><td>"+
                    "<p class='study_word'>"+getHelp40(ss)+
                    "</p><p class='p'>"+getHelp85(ss)+
                    "</p><p class='p'>"+getHelp86(ss)+
                    "</p><p class='p'>"+getHelp87(ss)+
                    "</p><p class='p'>"+getHelp88(ss)+
                    "</p></td></tr>";
        }
        else if(ss.helpInt == 7)
        {//HISTORY(7)
            return helpMe = "<!-- About Study History --><tr><td>"+
                    "<p class='study_word'>"+getHelp45(ss)+
                    "</p><p class='p'>"+getHelp98(ss)+
                    "</p><p class='p'>"+getHelp99(ss)+
                    "</p><p class='p'>"+getHelp100(ss)+
                    "</p><p class='p'>"+getHelp101(ss)+
                    "</p><p class='p'>"+getHelp102(ss)+
                    "</p></td></tr>";
        }
        else if(ss.helpInt == 8)
        {//QUIZ(8)
            return helpMe = "<!-- About Create Quiz --><tr><td>"+
                    "<p class='study_word'>"+getHelp60(ss)+
                    "</p><p class='p'>"+getHelp103(ss)+
                    "</p><p class='p'>"+getHelp104(ss)+
                    "</p><p class='p'>"+getHelp105(ss)+
                    "</p><p class='p'>"+getHelp106(ss)+
                    "</p></td></tr>";
        }
        else
        {
            return helpMe = "<!-- when ss.helpInt is another integer -->"+
                    "<tr><td><p class='p'>"+getHelp107(ss)+"</p></td></tr>";
        }
    }
		
	// Link to return to the original page
	public static String getHelp1A(SessionState ss)
	{
		String[] s = {"Click<A HREF='/"+ss.folder+"/"+ss.fullHelp+"' onClick='return popup(this, \"help\", 750, 600)' > here </A>"+
				"to see the full help page in English", 
			"全てのヘルプページを英語で見るには<A HREF='/"+ss.folder+"/"+ss.fullHelp+"' onClick='return popup(this, \"help\", 750, 600)' >ここ</A>"+
				"をクリックして下さい。"};
		return s[ss.lang];
	}
	
	// Link to return to the original page
	public static String getHelp1(SessionState ss)
	{
		String[] s = {"Click<A HREF='/"+ss.folder+"/editrows' onClick='return targetopener(this,true,true)' > here </A>"+
				"to return to the original window", 
			"オリジナル画面に戻るには<A HREF='/"+ss.folder+"/editrows' onClick='return targetopener(this,true,true)' >ここ</A>"+
				"をクリックして下さい。"};
		return s[ss.lang];
	}
	
	// Home About the buttons
	public static String getHelp2A(SessionState ss)
	{
		String[] s = {"<u>Home</u>", 
			"<u>ホーム</u>"};
		return s[ss.lang];
	}
	
	// Home About the links
	public static String getHelp2(SessionState ss)
	{
		String[] s = {"<u>Links</u>", 
			"<u>リンク</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp3(SessionState ss)
	{
		String[] s = {"<b>Home</b> - A display of Links and Buttons.", 
			"<b>ホーム</b> - リンクとボタンの表示。"};
		return s[ss.lang];
	}
	
	// no longer used
	public static String getHelp4(SessionState ss)
	{
		String[] s = {"<b>My Lists</b> - First step in creating the user's own lists and then using those lists to study, to review or to test the user's understanding.", 
			"japanese<b>My Lists</b> - First step in creating the user's own lists and then using those lists to study, to review or to test the user's understanding."};
		return s[ss.lang];
	}
	
	
	// no longer used
	public static String getHelp5(SessionState ss)
	{
		String[] s = {"<b>Teacher Lists</b> (For students and teachers) - First step in choosing one of the student's teacher's lists to study, to review or to test the student's understanding.", 
			"japanese <b>Teacher Lists</b> (For students and teachers) - First step in choosing one of the student's teacher's lists to study, to review or to test the student's understanding."};
		return s[ss.lang];
	}
	
	
	public static String getHelp6(SessionState ss)
	{
		String[] s = {"<b>Display Lists</b> - A way to display the user's various lists, as well as to shift selected rows to other lists.", 
			"<b>表示リスト</b> - ユーザーの色々なリストを表示したり、選択した単語を他のリストに移動出来ます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp7(SessionState ss)
	{
		String[] s = {"<b>History</b> - A display of the user's history, showing the date and time of being logged on, as well as the results of the user's Review or Test activities.", 
			"<b>履歴</b> - ユーザーの履歴を表示して、ログオンの日時や復習やテストの活動の結果を見る事が出来ます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp8(SessionState ss)
	{
		String[] s = {"<b>Create Lists</b> (For teachers) - A page that allows teachers to create new lists (for students to study), and allows teachers to add rows of meanings to those lists.", 
			"<b>リストの作成</b> (先生の為) - このページでは先生が新しいリストを作成する事が出来ます（生徒が勉強する為の）、そして先生がそれぞれのリストに単語の意味を追加する事も出来ます。"};
		return s[ss.lang];
	}
	
	// About My Lists
	public static String getHelp9(SessionState ss)
	{
		String[] s = {"<u>My List</u>", 
			"<u>私のリスト</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp10(SessionState ss)
	{
		String[] s = {"Allows the user to create their own lists, clear those lists, and learn those lists by studying, reviewing and testing. ", 
			"japanese Allows the user to create their own lists, clear those lists, and learn those lists by studying, reviewing and testing. "};
		return s[ss.lang];
	}
	
	
	public static String getHelp11(SessionState ss)
	{
		String[] s = {"If the user has already created some lists, they can study the words in each row by choosing: <ol class='listings'><LI>to focus on the English meaning and learning the Japanese meaning (in Kanji or Yomi), or vice versa <LI>the type of words they would like to study (verbs, nouns, adjectives or miscellaneous), <LI>the list (My List, Know List or Maybe Know List) to study.</OL>", 
			"japanese If the user has already created some lists, they can study the words in each row by choosing to focus on the English meaning and learning the Japanese meaning (in Kanji or Yomi), or vice versa. The user will then choose the type of words they would like to study (verbs, nouns, adjectives or miscellaneous), and then the list (My List, Know List or Maybe Know List) to study."};
		return s[ss.lang];
	}
	
	
	public static String getHelp12(SessionState ss)
	{
		String[] s = {"<b>My List</b> - List of rows from the database that each contain an English meaning and a Japanese meaning (Kanji and Yomi). The list is separated by type (verbs, nouns, adjectives or miscellaneous). <u>This list is supposed to be used for words the user does not know well enough.</u>", 
			"japanese <b>My List</b> - List of rows from the database that each contain an English meaning and a Japanese meaning (Kanji and Yomi). The list is separated by type (verbs, nouns, adjectives or miscellaneous). <u>This list is supposed to be used for words the user does not know well enough.</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp13(SessionState ss)
	{
		String[] s = {"<b>Know List</b> - List of rows from the database that each contain an English meaning and a Japanese meaning (Kanji and Yomi). The list is separated by type (verbs, nouns, adjectives or miscellaneous). <u>This list is supposed to be used for words the user knows very well. Of course, rows here can still be studied.</u>", 
			"japanese <b>Know List</b> - List of rows from the database that each contain an English meaning and a Japanese meaning (Kanji and Yomi). The list is separated by type (verbs, nouns, adjectives or miscellaneous). <u>This list is supposed to be used for words the user knows very well. Of course, rows here can still be studied.</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp14(SessionState ss)
	{
		String[] s = {"<b>Maybe Know List</b> - List of rows from the database that each contain an English meaning and a Japanese meaning (Kanji and Yomi). The list is separated by type (verbs, nouns, adjectives or miscellaneous). <u>This list is supposed to be used for words the user knows well but might want to study further. Of course, rows here can still be studied.</u>", 
			"japanese <b>Maybe Know List</b> - List of rows from the database that each contain an English meaning and a Japanese meaning (Kanji and Yomi). The list is separated by type (verbs, nouns, adjectives or miscellaneous). <u>This list is supposed to be used for words the user knows well but might want to study further. Of course, rows here can still be studied.</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp15(SessionState ss)
	{
		String[] s = {"<b>Start Study</b> - The user can study the selected rows one after another. Hart Book will choose up to 50 rows at random.", 
			"japanese<b>Start Study</b> - The user can study the selected rows one after another. Hart Book will choose up to 50 rows at random."};
		return s[ss.lang];
	}
	
	
	public static String getHelp16(SessionState ss)
	{
		String[] s = {"<b>Start Review</b> - The user can review the selected rows in random order.  Hart Book will choose up to 50 rows at random and display them one at a time as the selected word surrounded by choices in a circular pattern of buttons. The user will click on the button that they believe to be the answer.", 
			"japanese<b>Start Review</b> - The user can review the selected rows in random order.  Hart Book will choose up to 50 rows at random and display them one at a time as the selected word surrounded by choices in a circular pattern of buttons. The user will click on the button that they believe to be the answer."};
		return s[ss.lang];
	}
	
	
	public static String getHelp17(SessionState ss)
	{
		String[] s = {"<b>Start Test</b> - The user can test their knowledge of the selected rows one after another. Hart Book will choose up to 50 rows at random and present blank text fields for the user to enter their answers, which will then be checked against the correct answers.", 
			"japanese<b>Start Test</b> - The user can test their knowledge of the selected rows one after another. Hart Book will choose up to 50 rows at random and present blank text fields for the user to enter their answers, which will then be checked against the correct answers."};
		return s[ss.lang];
	}
	
	
	public static String getHelp18(SessionState ss)
	{
		String[] s = {"<b>Add</b> - The user can create lists of verbs, nouns, adjectives and miscellaneous (anything else), and add more words as they like to each list. By selecting the type of words they would like from the desired set of words in the database table, the user causes Hart Book to fetch the set of words and check each row in order. If the row is not in any of the user's lists, it is added to the user's My List. Hart Book will add up to 50 rows of that type at a time. If there are less than 50 rows, all of them will be added. If all of the rows are in the user's lists, no more rows can be added.", 
			"japanese<b>Add</b> - The user can create lists of verbs, nouns, adjectives and miscellaneous (anything else), and add more words as they like to each list. By selecting the type of words they would like from the desired set of words in the database table, the user causes Hart Book to fetch the set of words and check each row in order. If the row is not in any of the user's lists, it is added to the user's My List. Hart Book will add up to 50 rows of that type at a time. If there are less than 50 rows, all of them will be added. If all of the rows are in the user's lists, no more rows can be added."};
		return s[ss.lang];
	}
	
	
	public static String getHelp19(SessionState ss)
	{
		String[] s = {"<b>Clear</b> - The user can clear the chosen list (My List, Know List or Maybe Know List) of all the chosen types (verbs, nouns, adjectives or miscellaneous). Afterwards, the rows can be added again just by clicking on the Add button (be sure to choose the desired type and set).", 
			"japanese<b>Clear</b> - The user can clear the chosen list (My List, Know List or Maybe Know List) of all the chosen types (verbs, nouns, adjectives or miscellaneous). Afterwards, the rows can be added again just by clicking on the Add button (be sure to choose the desired type and set)."};
		return s[ss.lang];
	}
	
	// About Study
	public static String getHelp20(SessionState ss)
	{
		String[] s = {"<u>Study</u>", 
			"<u>勉強</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp21(SessionState ss)
	{
		String[] s = {"This page allows the user to study the selected rows. For each row, the chosen focus meaning is presented along with all of the meanings for this focus that are in the database table. ", 
			"japaneseThis page allows the user to study the selected rows. For each row, the chosen focus meaning is presented along with all of the meanings for this focus that are in the database table. "};
		return s[ss.lang];
	}
	
	
	public static String getHelp22(SessionState ss)
	{
		String[] s = {"<b>Next Set</b> - The user clicks on the Next Set button to see the next focus meaning and all of the meanings. When the user has worked through the fifty meanings, the user is given a message that advises them to click on Next Set to repeat the list, or to click on the My List link to get another study list or learn another way.", 
			"japanese<b>Next Set</b> - The user clicks on the Next Set button to see the next focus meaning and all of the meanings. When the user has worked through the fifty meanings, the user is given a message that advises them to click on Next Set to repeat the list, or to click on the My List link to get another study list or learn another way."};
		return s[ss.lang];
	}
	
	// About Test
	public static String getHelp23(SessionState ss)
	{
		String[] s = {"<u>Test</u>", 
			"<u>テスト</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp24(SessionState ss)
	{
		String[] s = {"This is a page that allows the user to test their memory of the possible meanings of each focus meaning. The focus meaning of each row is presented on the left, and one text field for each meaning is presented on the right. The user should type in one possible meaning for each text field.", 
			"japaneseThis is a page that allows the user to test their memory of the possible meanings of each focus meaning. The focus meaning of each row is presented on the left, and one text field for each meaning is presented on the right. The user should type in one possible meaning for each text field."};
		return s[ss.lang];
	}
	
	
	public static String getHelp25(SessionState ss)
	{
		String[] s = {"<b>Next Test</b> - The user should click on Next Test to check their answers and to get the next focus meaning.", 
			"japanese<b>Next Test</b> - The user should click on Next Test to check their answers and to get the next focus meaning."};
		return s[ss.lang];
	}
	
	
	public static String getHelp26(SessionState ss)
	{
		String[] s = {"<b>Correct Answers</b> - At the bottom, the user will see all of the rows that contain meanings for the focus word.", 
			"japanese<b>Correct Answers</b> - At the bottom, the user will see all of the rows that contain meanings for the focus word."};
		return s[ss.lang];
	}
	
	
	public static String getHelp27(SessionState ss)
	{
		String[] s = {"<b>Your Answers</b> - At the bottom, the user will see all of the answers that they provided so they can check them against the correct answers.", 
			"japanese<b>Your Answers</b> - At the bottom, the user will see all of the answers that they provided so they can check them against the correct answers."};
		return s[ss.lang];
	}
	
	// About Review
	public static String getHelp28(SessionState ss)
	{
		String[] s = {"<u>Review</u>", 
			"<u>復習</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp29(SessionState ss)
	{
		String[] s = {"This page allows the user to review their knowledge of the meanings in the chosen list of rows. It chooses up to 50 rows from the list. Then, it randomly selects one row, places the focus meaning in the center and surrounds it with six buttons. The correct meaning will be randomly displayed on one of the buttons, and five meanings from other rows will be displayed randomly on the other buttons. The other meanings will <u>not</u> be one of the focus meaning's other (possible) meanings, but it might be a meaning from another focus meaning's row that is the same as that of the chosen focus meaning.", 
			"japaneseThis page allows the user to review their knowledge of the meanings in the chosen list of rows. It chooses up to 50 rows from the list. Then, it randomly selects one row, places the focus meaning in the center and surrounds it with six buttons. The correct meaning will be randomly displayed on one of the buttons, and five meanings from other rows will be displayed randomly on the other buttons. The other meanings will <u>not</u> be one of the focus meaning's other (possible) meanings, but it might be a meaning from another focus meaning's row that is the same as that of the chosen focus meaning."};
		return s[ss.lang];
	}
	
	
	public static String getHelp30(SessionState ss)
	{
		String[] s = {"<b>Move</b> - This button allows the user to remove the just reviewed row of meanings from the list that is being used to one of three (not the list being used) of the following lists:", 
			"japanese<b>Move</b> - This button allows the user to remove the just reviewed row of meanings from the list that is being used to one of three (not the list being used) of the following lists:"};
		return s[ss.lang];
	}
	
	
	public static String getHelp31(SessionState ss)
	{
		String[] s = {"<b>Temporary</b> - This choice will remove the row from circulation so its meanings will not appear again while the user is logged on.", 
			"japanese<b>Temporary</b> - This choice will remove the row from circulation so its meanings will not appear again while the user is logged on."};
		return s[ss.lang];
	}
	
	
	public static String getHelp32(SessionState ss)
	{
		String[] s = {"<b>My List</b> - This choice will remove the row from the list being reviewed and place it on My List. It will also place it on the Temporary list so it will not appear again while the user is logged on.", 
			"japanese<b>My List</b> - This choice will remove the row from the list being reviewed and place it on My List. It will also place it on the Temporary list so it will not appear again while the user is logged on."};
		return s[ss.lang];
	}
	
	
	public static String getHelp33(SessionState ss)
	{
		String[] s = {"<b>Know List</b> - This choice will remove the row from the list being reviewed and place it on Know List. It will also place it on the Temporary list so it will not appear again while the user is logged on.", 
			"japanese<b>Know List</b> - This choice will remove the row from the list being reviewed and place it on Know List. It will also place it on the Temporary list so it will not appear again while the user is logged on."};
		return s[ss.lang];
	}
	
	
	public static String getHelp34(SessionState ss)
	{
		String[] s = {"<b>Maybe Know List</b> - This choice will remove the row from the list being reviewed and place it on Maybe Know List. It will also place it on the Temporary list so it will not appear again while the user is logged on.", 
			"japanese<b>Maybe Know List</b> - This choice will remove the row from the list being reviewed and place it on Maybe Know List. It will also place it on the Temporary list so it will not appear again while the user is logged on."};
		return s[ss.lang];
	}
	
	// About Teacher Lists
	public static String getHelp35(SessionState ss)
	{
		String[] s = {"<u>Teacher Lists</u>", 
			"<u>先生のリスト</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp36(SessionState ss)
	{
		String[] s = {"This page allows the student to learn the meanings of the rows on their teacher's lists through pages for studying, reviewing and testing. The student can learn the meanings in each row by choosing to focus on the English meaning and learning the Japanese meaning (in Kanji or Yomi), or vice versa. Then, the student will choose which of their Teacher's lists they would like to learn.", 
			"japaneseThis page allows the student to learn the meanings of the rows on their teacher's lists through pages for studying, reviewing and testing. The student can learn the meanings in each row by choosing to focus on the English meaning and learning the Japanese meaning (in Kanji or Yomi), or vice versa. Then, the student will choose which of their Teacher's lists they would like to learn."};
		return s[ss.lang];
	}
	
	
	public static String getHelp37(SessionState ss)
	{
		String[] s = {"<b>Start Study</b> - The student can study the selected rows one after another. Hart Book will choose up to 50 rows at random.", 
			"japanese<b>Start Study</b> - The student can study the selected rows one after another. Hart Book will choose up to 50 rows at random."};
		return s[ss.lang];
	}
	
	
	public static String getHelp38(SessionState ss)
	{
		String[] s = {"<b>Start Review</b> - The student can review the selected rows in random order. Hart Book will choose up to 50 rows at random and display them one at a time as the selected word surrounded by choices in a circular pattern of buttons. The student will click on the button that they believe to be the answer.", 
			"japanese<b>Start Review</b> - The student can review the selected rows in random order. Hart Book will choose up to 50 rows at random and display them one at a time as the selected word surrounded by choices in a circular pattern of buttons. The student will click on the button that they believe to be the answer."};
		return s[ss.lang];
	}
	
	
	public static String getHelp39(SessionState ss)
	{
		String[] s = {"<b>Start Test</b> - The student can test their knowledge of the selected rows one after another. Hart Book will choose up to 50 rows at random and present blank text fields for the student to enter their answers in, which will then be checked against the correct answers.", 
			"japanese<b>Start Test</b> - The student can test their knowledge of the selected rows one after another. Hart Book will choose up to 50 rows at random and present blank text fields for the student to enter their answers in, which will then be checked against the correct answers."};
		return s[ss.lang];
	}
	
	// About Display Lists
	public static String getHelp40(SessionState ss)
	{
		String[] s = {"<u>Display Lists</u>", 
			"<u>表示リスト</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp41(SessionState ss)
	{
		String[] s = {"This page allows the user to see all of the rows that are in their various lists.", 
			"japaneseThis page allows the user to see all of the rows that are in their various lists."};
		return s[ss.lang];
	}
	
	
	public static String getHelp42(SessionState ss)
	{
		String[] s = {"<b>Display</b> - First the user will select the type of words (verbs, nouns, adjectives or miscellaneous) they would like to see, then the user will select which list (My List, Know List or Maybe Know List) [ if the user is a student or teacher, they can see a Teacher List (by type), but any other list their teacher has made will not be separated by the type selection] they would like to see. Finally, the user will click on the Dispaly button, which will cause Hart Book to display the chosen list.", 
			"japanese<b>Display</b> - First the user will select the type of words (verbs, nouns, adjectives or miscellaneous) they would like to see, then the user will select which list (My List, Know List or Maybe Know List) [ if the user is a student or teacher, they can see a Teacher List (by type), but any other list their teacher has made will not be separated by the type selection] they would like to see. Finally, the user will click on the Dispaly button, which will cause Hart Book to display the chosen list."};
		return s[ss.lang];
	}
	
	
	public static String getHelp43(SessionState ss)
	{
		String[] s = {"<b>Search</b> - This allows the user to enter a word into the text field, and when the Search button is clicked, if the word is in the database table, its row will be displayed. If it is not in the table, the message will say so.", 
			"japanese<b>Search</b> - This allows the user to enter a word into the text field, and when the Search button is clicked, if the word is in the database table, its row will be displayed. If it is not in the table, the message will say so."};
		return s[ss.lang];
	}
	
	
	public static String getHelp44(SessionState ss)
	{
		String[] s = {"<b>Move</b> - If one or more rows are being displayed, there will be a radio button at the end of the row. If the user clicks on this radio button, selects a list (My List, Know List or Maybe Know List) to add the row to (of course, if the selected list is the list being displayed, it will not have any effect to move the row), and then clicks on the Move button, the row will be removed from the displayed list and added to the selected list, separated by type.", 
			"japanese<b>Move</b> - If one or more rows are being displayed, there will be a radio button at the end of the row. If the user clicks on this radio button, selects a list (My List, Know List or Maybe Know List) to add the row to (of course, if the selected list is the list being displayed, it will not have any effect to move the row), and then clicks on the Move button, the row will be removed from the displayed list and added to the selected list, separated by type."};
		return s[ss.lang];
	}
	
	// About Study History
	public static String getHelp45(SessionState ss)
	{
		String[] s = {"<u>Study History</u>", 
			"<u>勉強の履歴</u>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp46(SessionState ss)
	{
		String[] s = {"This page allows the user to see a list of activity results from every time they have logged on to Hart Book (not including the current scores). Each row displays the date of logging on, the number of minutes the user studied, the <b>percent</b> of correct answers from the Review Section, the <b>number</b> of correct answers from the Review Section, the <b>percent</b> of correct answers from the Test Section, and the <b>number</b> of correct answers from the Test Section.", 
			"japaneseThis page allows the user to see a list of activity results from every time they have logged on to Hart Book (not including the current scores). Each row displays the date of logging on, the number of minutes the user studied, the <b>percent</b> of correct answers from the Review Section, the <b>number</b> of correct answers from the Review Section, the <b>percent</b> of correct answers from the Test Section, and the <b>number</b> of correct answers from the Test Section."};
		return s[ss.lang];
	}
	
	
	public static String getHelp47(SessionState ss)
	{
		String[] s = {"At the bottom of the list, the total number of minutes that the user has studied from the first time logging on to the most recent time logging on will be displayed. Also, the total of each column will be shown (percentages will be compiled together).", 
			"japaneseAt the bottom of the list, the total number of minutes that the user has studied from the first time logging on to the most recent time logging on will be displayed. Also, the total of each column will be shown (percentages will be compiled together)."};
		return s[ss.lang];
	}
	
	
	public static String getHelp48(SessionState ss)
	{
		String[] s = {"<b>All Students</b> (for teachers) - Clicking on this button will cause Hart Book to provide a list of all of the teacher's students' totals.", 
			"japanese<b>All Students</b> (for teachers) - Clicking on this button will cause Hart Book to provide a list of all of the teacher's students' totals."};
		return s[ss.lang];
	}
	
	
	public static String getHelp49(SessionState ss)
	{
		String[] s = {"<b>Student's History</b> (for teachers) - Entering the student's userID into the text field and clicking on the Student's History button will cause Hart Book to display that student's study history.", 
			"japanese<b>Student's History</b> (for teachers) - Entering the student's userID into the text field and clicking on the Student's History button will cause Hart Book to display that student's study history."};
		return s[ss.lang];
	}
	
	//About Edit Rows
	public static String getHelp50(SessionState ss)
	{
		String[] s = {"<u>Create Lists</u> (for teachers)",
			"<u>単語の編集</u> (先生のため)"}; // IMPORTANT: change J to Create from Edit
		return s[ss.lang];
	}
	
	
	public static String getHelp51(SessionState ss)
	{
		String[] s = {"This page allows the teacher to create a list for their students to study. It also allows the teacher to remove rows from that list and to create a quiz that can be printed for distribution in class.", 
			"japaneseThis page allows the teacher to create a list for their students to study. It also allows the teacher to remove rows from that list and to create a quiz that can be printed for distribution in class."};
		return s[ss.lang];
	}
	
	
	public static String getHelp52(SessionState ss)
	{
		String[] s = {"<b>Create a List Name</b> - Clicking on this link gets a popup window that allows the teacher to enter a name for a new list.", 
			"japanese<b>Create a List Name</b> - Clicking on this link gets a popup window that allows the teacher to enter a name for a new list."};
		return s[ss.lang];
	}
	
	
	public static String getHelp53(SessionState ss)
	{
		String[] s = {"<b>Create a Quiz</b> - Clicking on this link takes the user to a page that allows the teacher to create a quiz for a list.", 
			"japanese<b>Create a Quiz</b> - Clicking on this link takes the user to a page that allows the teacher to create a quiz for a list."};
		return s[ss.lang];
	}
	
	
	public static String getHelp54(SessionState ss)
	{
		String[] s = {"<b>Display</b> - Choosing the list you want to manipulate and clicking on the Display button causes Hart Book to display the selected list with a radio button at the end of each row.", 
			"japanese<b>Display</b> - Choosing the list you want to manipulate and clicking on the Display button causes Hart Book to display the selected list with a radio button at the end of each row."};
		return s[ss.lang];
	}
	
	
	public static String getHelp55(SessionState ss)
	{
		String[] s = {"<b>Edit</b> - Clicking on the radio button of the row that the teacher would like to edit and then clicking on the Edit button will cause Hart Book to fill the text field spaces with that row's information: i.e., the row's ID number will be displayed; the row's type will be displayed in the drop down list; the text field spaces for the English meaning, the Japanese meaning in Kanji, the Japanese meaning in Yomi will be shown; and the list that the teacher has chosen will also be provided as well.", 
			"japanese<b>Edit</b> - Clicking on the radio button of the row that the teacher would like to edit and then clicking on the Edit button will cause Hart Book to fill the text field spaces with that row's information: i.e., the row's ID number will be displayed; the row's type will be displayed in the drop down list; the text field spaces for the English meaning, the Japanese meaning in Kanji, the Japanese meaning in Yomi will be shown; and the list that the teacher has chosen will also be provided as well."};
		return s[ss.lang];
	}
	
	
	public static String getHelp56(SessionState ss)
	{
		String[] s = {"<b>Add Row</b> - If the teacher would like to add meanings to their list that are not in the database table, they can select the meaning's type, enter the English meaning, the Japanese meaning in Kanji and the Japanese meaning in Yomi and then click on the Add Row button. Hart Book will check to see if such a row of meanings already exists in the database table. If it exists, then that row will be added to the teacher's list. If not, Hart Book will create a new row with these meanings and add that row to the teacher's list.", 
			"japanese<b>Add Row</b> - If the teacher would like to add meanings to their list that are not in the database table, they can select the meaning's type, enter the English meaning, the Japanese meaning in Kanji and the Japanese meaning in Yomi and then click on the Add Row button. Hart Book will check to see if such a row of meanings already exists in the database table. If it exists, then that row will be added to the teacher's list. If not, Hart Book will create a new row with these meanings and add that row to the teacher's list."};
		return s[ss.lang];
	}
	
	
	public static String getHelp57(SessionState ss)
	{
		String[] s = {"<b>Search</b> - If the teacher would like to search for some meanings that they would like to add to their list, the teacher need only type in that one meaning in the appropriate text field (English for English, Kanji for kanji, and Yomi for yomi) and then click on the Add Row button. Hart Book will search the table for any rows with that meaning and display them. If the desired row of meanings is present, the teacher need only click on that row's radio button, click on the Edit button, and then click on the Update button to add it to the teacher's list (if it cannot be updated, still, the row will be added to the teacher's list). If the desired row of meanings is not present, the teacher can add it to the table in the normal way as stated in the <b>Add Row</b> section.", 
			"japanese<b>Search</b> - If the teacher would like to search for some meanings that they would like to add to their list, the teacher need only type in that one meaning in the appropriate text field (English for English, Kanji for kanji, and Yomi for yomi) and then click on the Add Row button. Hart Book will search the table for any rows with that meaning and display them. If the desired row of meanings is present, the teacher need only click on that row's radio button, click on the Edit button, and then click on the Update button to add it to the teacher's list (if it cannot be updated, still, the row will be added to the teacher's list). If the desired row of meanings is not present, the teacher can add it to the table in the normal way as stated in the <b>Add Row</b> section."};
		return s[ss.lang];
	}
	
	
	public static String getHelp58(SessionState ss)
	{
		String[] s = {"<b>Update</b> - The rows that teachers add to the database can be updated after the teacher has added the row's meanings to the text fields as stated in the <b>Edit</b> section. The teacher then makes any necessary changes and clicks the Update button. If the row has been approved and checked by the Administrator already, then it can no longer be altered (however, the row will be added to the teacher's list). If the row has not been approved yet, the changes will be made to the row's meanings and the row will be added to the teacher's list.", 
			"japanese<b>Update</b> - The rows that teachers add to the database can be updated after the teacher has added the row's meanings to the text fields as stated in the <b>Edit</b> section. The teacher then makes any necessary changes and clicks the Update button. If the row has been approved and checked by the Administrator already, then it can no longer be altered (however, the row will be added to the teacher's list). If the row has not been approved yet, the changes will be made to the row's meanings and the row will be added to the teacher's list."};
		return s[ss.lang];
	}
	
	
	public static String getHelp59(SessionState ss)
	{
		String[] s = {"<b>Remove</b> - The teacher can take an undesired row out of their list by displaying the list, clicking on the radio button at the end of the row they do not wish, clicking on the Edit button, and then using the Remove button after the row has appeared in the text fields. Since the row remains in the text fields, clicking on Add Row or Update will place that row on the teacher's list once more.", 
			"japanese<b>Remove</b> - The teacher can take an undesired row out of their list by displaying the list, clicking on the radio button at the end of the row they do not wish, clicking on the Edit button, and then using the Remove button after the row has appeared in the text fields. Since the row remains in the text fields, clicking on Add Row or Update will place that row on the teacher's list once more."};
		return s[ss.lang];
	}
	
	// About CreateQuiz
	public static String getHelp60(SessionState ss)
	{
		String[] s = {"<u>Creat Quiz</u> (for teachers)", 
			"<u>クイズの作成</u> (先生のため)"};
		return s[ss.lang];
	}
	
	
	public static String getHelp61(SessionState ss)
	{
		String[] s = {"This page allows the teacher to create a quiz and then to print out the quiz and the answers.", 
			"japaneseThis page allows the teacher to create a quiz and then to print out the quiz and the answers."};
		return s[ss.lang];
	}
	
	
	public static String getHelp62(SessionState ss)
	{
		String[] s = {"<b>Get Quiz</b> -  The teacher selects the list they would like to use from the drop down list, then enters the number of rows they would like to use for the quiz, then clicks on the Get Quiz button. This will cause Hart Book to randomly choose that number of rows from the chosen list. If the entered number is greater than the number of rows in the list, Hart Book will take all of the rows arranged randomly. These rows will be displayed, and if the teacher does not like these choices, they can try again. ", 
			"japanese<b>Get Quiz</b> -  The teacher selects the list they would like to use from the drop down list, then enters the number of rows they would like to use for the quiz, then clicks on the Get Quiz button. This will cause Hart Book to randomly choose that number of rows from the chosen list. If the entered number is greater than the number of rows in the list, Hart Book will take all of the rows arranged randomly. These rows will be displayed, and if the teacher does not like these choices, they can try again. "};
		return s[ss.lang];
	}
	
	
	public static String getHelp63(SessionState ss)
	{
		String[] s = {"<b>Print Quiz Page</b> - When the teacher is satisfied with the arrangement of rows, they can select which column of meaning they want the student to see (English, Kanji or Yomi), then click on the Print Quiz Page button to get a popup that displays a printable page.", 
			"japanese<b>Print Quiz Page</b> - When the teacher is satisfied with the arrangement of rows, they can select which column of meaning they want the student to see (English, Kanji or Yomi), then click on the Print Quiz Page button to get a popup that displays a printable page."};
		return s[ss.lang];
	}
	
	
	public static String getHelp64(SessionState ss)
	{
		String[] s = {"<b>Printable Quiz Page</b> - This popup page (close it to return to the original window) is just black and white. It has centered at the top the words Quiz, Date and Name (followed by blank spaces), and then on the line below Year, Class, Student No. and Score. This is followed by a line across the page. Finally, the chosen column (English, Kanji or Yomi) is printed line by line (with a blank space following each meaning). Of course, if the teacher would like to use a different format, they need only set up the desired heading in a text editor like Microsoft Word, and then copy and paste the desired quiz elements.", 
			"japanese<b>Printable Quiz Page</b> - This popup page (close it to return to the original window) is just black and white. It has centered at the top the words Quiz, Date and Name (followed by blank spaces), and then on the line below Year, Class, Student No. and Score. This is followed by a line across the page. Finally, the chosen column (English, Kanji or Yomi) is printed line by line (with a blank space following each meaning). Of course, if the teacher would like to use a different format, they need only set up the desired heading in a text editor like Microsoft Word, and then copy and paste the desired quiz elements."};
		return s[ss.lang];
	}
	
	
	public static String getHelp65(SessionState ss)
	{
		String[] s = {"<b>Printable Answers</b> - Clicking on this button will provide the teacher with a popup (close it to return to the original window) that displays in black and white all of the meanings in each row of the quiz.", 
			"japanese<b>Printable Answers</b> - Clicking on this button will provide the teacher with a popup (close it to return to the original window) that displays in black and white all of the meanings in each row of the quiz."};
		return s[ss.lang];
	}
	
	// Home
	public static String getHelp66(SessionState ss)
	{
		String[] s = {"Hart Book provides various lists to help you learn vocabulary. These lists consist of rows in the Hartbook database vocabulary table that include a type (verb, noun, adjective, or miscellaneous), an English meaning, a Japanese meaning in Kanji and a Japanese meaning in Hiragana or Katakana.", 
			"Hart Bookはさまざまなリストより単語の学習を提供しています。これらのリストには動詞、名詞、形容詞又その他が含まれています。漢字とひらがな又はかたかなで英語の意味が表示されます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp67(SessionState ss)
	{
		String[] s = {"Learn the words in each list of rows by choosing:<ul class='listings'><li><b>Start Study</b> - You can study randomly selected words (50 or less at a time) one after another. Hart Book will display all of the meanings available for each word.</li><li><b>Start Review</b> - You can review randomly selected words (50 or less at a time). Hart Book will display each word surrounded by possible answers.</li><li><b>Start Test</b> - You can test your knowledge of randomly selected words (50 or less at a time). Hart Book will present blank text fields for you to enter your answers.</li></ul>", 
			"それぞれのリストを選ぶ事によって単語の学習を行います。<ul class='listings'><li><b>勉強スタート</b>ーランダムに選択された単語（５０又はそれ以下）を次から次へと勉強出来ます。Hart Bookはそれぞれの単語の意味も表示します。</li><li><b>復習スタート</b>ーランダムに選択された単語（５０又はそれ以下）を復習出来ます。Hart Bookはいくつかの答えを表示します。</li><li><b>テストスタート</b>ーランダムに選択された単語(５０又はそれ以下)をテストして能力を試す事が出来ます。Hart Bookは回答を入力する為の白紙の書き込み欄を提供します。</li></ul>"};
		return s[ss.lang];
	}
	
	// GetLists
	public static String getHelp68(SessionState ss)
	{
		String[] s = {"This page allows you to select the list to be used. If your My List is empty, click on the Add link.", 
			"このページでは既に使用されたリストを選択出来ます。 私のリストが空の場合は追加のリンクをクリックして下さい"};
		return s[ss.lang];
	}
	
	
	public static String getHelp69(SessionState ss)
	{
		String[] s = {"<b>Add</b> - You can add 50 rows of verbs, nouns, adjectives or miscellaneous (anything else) from Eiken lists to My List. Hart Book will only choose rows that are not on one of your 3 main lists. If less than 50 rows are added, no more rows of that type in that Eiken list are available.", 
			"<b>追加</b>―５０個の動詞、名詞、形容詞又はその他の単語を英検リストから私のリストへ追加出来ます。ただし３つのメインリストの中に既に存在する単語は省かれます。追加される単語が５０以下の場合はそれ以上追加される新しい単語が存在しない事をさします。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp70(SessionState ss)
	{
		String[] s = {"<b>Clear</b> - You can clear any of your 3 main lists of all the rows of a type (verbs, nouns, adjectives or miscellaneous).", 
			"<b>消去</b>―３つのメインリスト内の全ての単語を消去することが出来ます。(動詞、名詞、形容詞又はその他)"};
		return s[ss.lang];
	}
	
	
	public static String getHelp71(SessionState ss)
	{
		String[] s = {"<b>Choose the Meanings:</b><ul class='listings'><li><b>My List</b> - List of rows from the database that is supposed to be <u>used for words you do not know well enough</u>.</li><li><b>Know List</b> - List of rows from the database that is supposed to be <u>used for words you know very well and do not want to review often</u>.</li><li><b>Maybe Know List</b> - List of rows from the database that is supposed to be <u>used for words you know well but might want to review once in a while</u>.</li><li><b>Teacher Generated List</b> – List(s) of rows that your teacher has created.</li><li><b>Japanese or English</b> – Select which language you would like the focus word to be.</li></ul>", 
			"<b>意味を選んでください</b><ul class='listings'><li><b>私のリスト</b>ーデータベースの中に存在する単語であまりよく知らない単語。</li><li><b>知っているリスト</b>ーデータベースの中に存在する単語でよく熟知している単語。</li><li><b>たぶん知っているリスト</b>ーデータベースの中に存在する単語で知ってはいるがまだ勉強したい単語。</li><li><b>先生作成リスト</b>ー貴方の先生が作成した単語のリスト。</li><li><b>日本語又は英語</b>―画面に表示される言葉が選択出来ます。</li></ul>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp72(SessionState ss)
	{
		String[] s = {"<b>Choose the Type:</b><ul class='listings'><li>If you are using one of your 3 main lists, select the type of meanings you would like to study.</li><li>If you are Reviewing, select Hiragana or Kanji for the Japanese meanings.</li><li>Click on the Go button.</li></ul>", 
			"<b>タイプを選択して下さい</b><ul class='listings'><li>３つのメインリストのうちの１つを使用している場合、勉強したいタイプの意味を選んで下さい。</li><li>もし復習しているのなら、日本語の意味にひらがなか漢字を選んで下さい。</li><li>スタートボタンをクリックして下さい。</li></ul>"};
		return s[ss.lang];
	}
	
	// Study
	public static String getHelp73(SessionState ss)
	{
		String[] s = {"This page allows you to study the randomly selected rows (50 or less at a time). For each row, the chosen focus word is presented along with all of the meanings for this focus that are in the database table.", 
			"このページでは単語（５０又はそれ以下）をランダムに勉強することが出来ます。それぞれの単語とそれらの意味はデータベースより表示されます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp74(SessionState ss)
	{
		String[] s = {"<b>Next Set</b> - Click on the Next Set button to see the next focus meaning and all of its meanings.", 
			"<b>次のセット</b>ー次のセットボタンをクリックして次の単語を勉強してください。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp75(SessionState ss)
	{
		String[] s = {"When you have worked through the fifty meanings, you are given a message that advises you to click on the Next Set button to repeat the list, or to click on the Home link.", 
			"５０の単語を勉強し終わったら次のような表示が自動的に現れます。次のセットボタンをクリックしてもう一度繰りかえすか又はホームに戻る。"};
		return s[ss.lang];
	}
	
	// Review
	public static String getHelp76(SessionState ss)
	{
		String[] s = {"This page allows you to review your knowledge of the meanings in the chosen list of rows. Hart Book chooses randomly selected rows (50 or less at a time) from the list. The focus word is surrounded by six buttons. The correct meaning will be randomly displayed on one of the buttons, and five meanings from other rows will be displayed randomly on the other buttons. (The other meanings will not be one of the focus word’s other meanings, <u>but two different focus words might share the same meaning</u>.)", 
			"このページでは貴方の能力を復習することが出来ます。Hart Bookはランダムに選ばれた単語(５０又はそれ以下)を六つのボタンからなる意味で囲みます。六つの内の一つだけが正解です。(他の五つの意味は<u>全く異なる</u>単語の意味ですが<u>同じ意味を持つ単語も存在します</u>。)"};
		return s[ss.lang];
	}
	
	
	public static String getHelp77(SessionState ss)
	{
		String[] s = {"<b>Move</b> - Click to remove the just reviewed row of meanings from the list that is being used to the following lists:<ul class='listings'><li><b>Temporary</b> - Removes the row from circulation so its meanings will not appear again while the user is logged on.</li><li><b>My List</b> - Removes the row from the list being reviewed and places it on My List and Temporary List.</li><li><b>Know List</b> - Removes the row from the list being reviewed and places it on Know List and Temporary List.</li><li><b>Maybe Know List</b> - Removes the row from the list being reviewed and places it on Maybe Know List and Temporary List.</li></ul>", 
			"<b>移動</b>―移動ボタンをクリックして次の単語を表示させます。<ul class='listings'><li><b>一時的</b>―ログオン中だけ表示したくない単語を移動出来ます。</li><li><b>私のリスト</b>ーリストの中の勉強したい単語を移動できます。</li><li><b>知っているリスト</b>ーリストの中の既に知っている単語を移動できます。</li><li><b>たぶん知っているリスト</b>ーリストの中のたぶん知っている単語を移動できます。</li></ul>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp78(SessionState ss)
	{
		String[] s = {"Usually, the same focus word will not be chosen again for at least 5 rounds; however, when the list is down to 20, a focus word might be repeated at any time.", 
			"だいたい５ラウンド中に同じ単語は表示されませんが、リストが２０以下になった時は同じ単語がリピートして現れる可能性があります。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp79(SessionState ss)
	{
		String[] s = {"When the list is down to 6, some buttons may have the same meanings. Clicking on any of them is fine.", 
			"リストが６以下になった時いくつかの解答に同じ意味が表示されるかもしれません。どちらをクリックされても大丈夫です。"};
		return s[ss.lang];
	}
	
	// Test
	public static String getHelp80(SessionState ss)
	{
		String[] s = {"This page allows you to test your memory of the randomly selected rows (50 or less at a time). The focus word is displayed on the left, and one text field for each meaning is displayed on the right.", 
			"このページではランダムに選ばれた単語（５０又はそれ以下）をテスト出来ます。単語は左側に表示されその意味は右側に表示されます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp81(SessionState ss)
	{
		String[] s = {"<b>Next Test</b> - Click on the Next Test button to check your answers and to get the next focus word.", 
			"<b>次のテスト</b>ー次のテストボタンをクリックして回答と次の単語を見れます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp82(SessionState ss)
	{
		String[] s = {"<b>Correct Answers</b> – Here you will see all of the rows that contain meanings for the focus word.", 
			"<b>正解</b>―表示された単語の正しい意味が表示されます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp83(SessionState ss)
	{
		String[] s = {"<b>Your Answers</b> – Here you will see all of the answers that you entered so you can check them against the correct answers.", 
			"<b>貴方の回答</b>―ここでは貴方が入力した全ての回答が表示されます。それらと正しい正解を照らし合わせる事が出来ます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp84(SessionState ss)
	{
		String[] s = {"When you have worked through the fifty rows, you are given a message that advises you to click on the Next Test button to repeat the list, or to click on the Home link.", 
			"５０の単語のテストが終了したら、次のような表示が自動的に現れます。次のテストボタンをクリックしてもう一度繰りかえすか又はホームに戻る。"};
		return s[ss.lang];
	}
	
	// Display Lists
	public static String getHelp85(SessionState ss)
	{
		String[] s = {"This page allows you to see all of the rows that are in your various lists.", 
			"このページではそれぞれのリストに存在する全ての単語を見る事が出来ます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp86(SessionState ss)
	{
		String[] s = {"<b>Search</b> – Type in English or Japanese to see if the word is in the database.", 
			"<b>検索</b>―既にデータベースに存在するか調べるには英語が日本語で単語を入力してください。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp87(SessionState ss)
	{
		String[] s = {"<b>Display</b> – To see the rows in a list:<ol class='listings'><li>Select the type of words (does not matter for teacher created lists).</li><li>Select the list.</li><li>Click on the Display button.</li></ol>", 
			"<b>表示</b>―リスト内の単語を見る。<ol class='listings'><li>言葉のタイプを選択して下さい。（先生作成リストからでも構いません。）</li><li>リストを選択して下さい。</li><li>表示ボタンをクリックして下さい。</li></ol>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp88(SessionState ss)
	{
		String[] s = {"<b>Move</b> – To remove a row from the displayed list and add it to one of the 3 main lists:<ol class='listings'><li>Click on the radio button at the end of the row.</li><li>Select a list to add the row to.</li><li>Click on the Move button.</li></ol>", 
			"<b>移動</b>―表示されているリストから単語を取り去る場合や、３つのメインリストの内の１つに移動させる事が出来ます。<ol class='listings'><li>単語の最後にあるラジオボタンをクリックして下さい。</li><li>単語を追加したいリストを選択して下さい。</li><li>移動ボタンをクリックして下さい。</li></ol>"};
		return s[ss.lang];
	}
	
	// Edit Rows
	public static String getHelp89(SessionState ss)
	{
		String[] s = {"This page allows you to create a list for your students to study. It also allows you to remove rows from that list and to create a quiz that can be printed for distribution in class.", 
			"このページでは生徒が勉強する為のリストを作成できます。リストの編集やクラスで使うクイズを作成して印刷も可能です。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp90(SessionState ss)
	{
		String[] s = {"<b>Create a List Name</b> – Click on this link to get a popup window that allows you to enter a name for a new list (as well as edit other list names).", 
			"<b>リストの名前の作成</b>―このリンクをクリックすると新しいリストの名前を入力できるポップアップ画面が現れます(他のリストの名前も編集出来ます)。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp91(SessionState ss)
	{
		String[] s = {"<b>Create a Quiz</b> - Click on this link to go to a page that allows you to create a quiz for a teacher generated list.", 
			"<b>クイズの作成</b>―このリンクをクリックしてクイズの作成をする事ができます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp92(SessionState ss)
	{
		String[] s = {"<b>Search</b> – Type in a word in English or Japanese to see all of its rows in the database.", 
			"<b>検索</b>―英語か日本語で入力してデータベースに存在する単語を表示出来ます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp93(SessionState ss)
	{
		String[] s = {"<b>Display</b> – Select one of your lists then click on Display to see its rows.", 
			"<b>表示</b>―リストの一つを選択して、単語を表示することができます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp94(SessionState ss)
	{
		String[] s = {"<b>Edit</b> – Rows that have not yet been approved are editable (those with red numbers).<ol class='listings'><li>Click on the radio button at the end of the row.</li><li>Click on the Edit button.</li></ol><p class='p'>Hart Book will fill the text field spaces with that row's information.</p>", 
			"<b>編集</b>―単語の編集が可能です(赤い数字で表示されている単語)。<ol class='listings'><li>列の最後にあるラジオボタンをクリックします。</li><li>編集ボタンをクリックします。</li></ol><p>Hart Bookはその単語の情報を入力します。</p>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp95(SessionState ss)
	{
		String[] s = {"<b>Add Row</b> - To add meanings to the database and your list:<ol class='listings'><li>Make sure you have selected the correct list and clicked on the Display button.</li><li>Select the type.</li><li>Enter <b>one</b> English meaning.</li><li>Enter <b>one</b> Japanese meaning in Kanji.</li><li>Enter <b>one</b> Japanese meaning in Hiragana or Katakana.</li><li>Click on the Add Row button.</li></ol><p class='p'>Hart Book will check whether such a row of meanings already exists in the database table. If it exists, then that row will just be added to your list. If not, Hart Book will create a new row with these meanings and add that row to your list.</p>", 
			"<b>単語の追加</b>―データベースとリストに新たな意味を追加します。<ol class='listings'><li>選択したリストが正しいか確かめてから表示ボタンをクリックします。</li><li>タイプを選択します。</li><li>6.	英語の意味を<b>一つ</b>入力します。</li><li>日本語の意味を漢字で<b>一つ</b>入力します。</li><li>日本語の意味をひらがな又はカタカナで<b>一つ</b>入力します。</li><li>単語の追加ボタンをクリックします。</li></ol><p>Hart Bookはその単語の意味が既にデータベースに存在するか否かを自動的に調べます。もし存在している場合は、貴方のリストにのみ追加します。そうでない場合Hart Bookは新しい単語とその意味を作成し貴方のリストに追加します。</p>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp96(SessionState ss)
	{
		String[] s = {"<b>Update</b> – To update an edited row, or to add an approved row to your list:<ol class='listings'><li>Place the row in the text fields as stated in the <b>Edit</b> section.</li><li>Make any necessary changes if it is editable.</li><li>Click the Update button.</li></ol>", 
			"<b>更新</b>―編集した単語を更新するか貴方のリストに追加することが出来ます。<ol class='listings'><li>編集セクションの中の空白に単語を入力します。</li><li>編集可能な限り変えることが出来ます。</li><li>更新ボタンをクリックします。</li></ol>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp97(SessionState ss)
	{
		String[] s = {"<b>Remove</b> - To take an undesired row out of your list:<ol class='listings'><li>Display the list.</li><li>Place the row in the text fields as stated in the <b>Edit</b> section.</li><li>Click on the Remove button after the row has appeared in the text fields.</li></ol><p class='p'>(Since the row remains in the text fields, clicking on Add Row or Update will place that row on the teacher's list once more.  If you would like to shift that row to another list, you have to display the new list first.)</p>", 
			"<b>削除</b>―貴方のリストから要らない単語を削除出来ます。<ol class='listings'><li>リストを表示します。</li><li>編集セクションの中の空白に単語を入力します。</li><li>空白に単語が現れてから削除ボタンをクリックします。</li></ol><p>(空白に単語がある間に追加又は更新ボタンを押すと、もう一度先生のリストに表示されます。もし他のリストに移したい場合は、初めに新しいリストを表示しなくてはなりません。)</p>"};
		return s[ss.lang];
	}
	
	// Study History
	public static String getHelp98(SessionState ss)
	{
		String[] s = {"This page allows you to see a list of scores from every time you have logged on to Hart Book (not including the current scores).", 
			"このページではHart Bookにログオンする度に得点リストを見る事が出来ます。（前回の得点は含みません）"};
		return s[ss.lang];
	}
	
	
	public static String getHelp99(SessionState ss)
	{
		String[] s = {"Each row displays the <b>date</b> of logging on, the <b>number</b> of minutes you studied, the <b>percent</b> of correct answers from the Review Section, the <b>number</b> of correct answers from the Review Section, the <b>percent</b> of correct answers from the Test Section, and the <b>number</b> of correct answers from the Test Section.", 
			"それぞれの一覧を表示する際ログオンした<b>合計分数</b>、勉強した時間、復習での正解の<b>確率</b>と正解の<b>数</b>、テストでの正解の<b>確率</b>と正解の<b>数</b>が表示されます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp100(SessionState ss)
	{
		String[] s = {"At the top of the list, the <b>total</b> number of minutes that you have studied is displayed, from the first time logging on to the most recent time logging on. Also, the <b>total</b> of each column will be shown (percentages are compiled together).", 
			"リストの最初に一番初めにログオンしてから前回ログオンまでの勉強した時間が表示されます。又それぞれの並びの合計も現れます。(確率は１つに合計されます)"};
		return s[ss.lang];
	}
	
	
	public static String getHelp101(SessionState ss)
	{
		String[] s = {"<b>All Students</b> (for teachers) - Clicking on the link displays a list of all of your students' totals.", 
			"<b>全ての生徒</b>(先生用)－リンクをクリックして全ての生徒の合計を表示する事が出来ます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp102(SessionState ss)
	{
		String[] s = {"<b>Student's History</b> (for teachers) – Clicking on a radio button and then the See Student button displays that student's study history.", 
			"<b>生徒の履歴</b>(先生用)－ラジオボタンをクリックして生徒の勉強履歴を見る事が出来ます。"};
		return s[ss.lang];
	}
	
	// Create Quiz
	public static String getHelp103(SessionState ss)
	{
		String[] s = {"This page allows you to create a quiz and then to print out the quiz and the answers.", 
			"このページではクイズを作成して問題と回答をプリントアウトする事が出来ます。"};
		return s[ss.lang];
	}
	
	
	public static String getHelp104(SessionState ss)
	{
		String[] s = {"<b>Get Quiz</b> – Hart Book will randomly choose a number of rows from the chosen list.<ol class='listings'><li>Select the list.</li><li>Enter the number of rows you would like to use for the quiz.</li><li>Click on the Get Quiz button.</li></ol><p class='p'>(If the entered number is greater than the number of rows in the list, Hart Book will display all of the rows arranged randomly. If you do not like these choices, you can try again.)</p>", 
			"<b>クイズ作成</b>―Hart Bookは選ばれたリストよりランダムに単語のナンバーを選択します。<ol class='listings'><li>リストを選択</li><li>クイズに使用する単語のナンバーを入力。</li><li>クイズ作成ボタンをクリックして下さい。</li></ol><p class='p'>（入力されたナンバーが存在する単語ナンバーを超えている場合は、Hart Bookはランダムに全ての単語を表示します。もしそれらが嫌な場合はやり直す事も出来ます。）</p>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp105(SessionState ss)
	{
		String[] s = {"<b>Print Quiz Page</b> - When you are satisfied with the arrangement of rows:<ol class='listings'><li>Select which column of meaning you want the student to see.</li><li>Click on the Print Quiz Page button.</li></ol><p class='p'>This popup is just a formatted, printable, black and white page. (Close it to return to the original window.)</p>", 
			"<b>クイズページ印刷</b>―単語の設定に満足出来たら、<ol class='listings'><li>生徒に見てもらいたいコラムの意味を選択して下さい。</li><li>クイズページ印刷ボタンをクリックして下さい。</li></ol><p class='p'>このポップアップ画面は印刷可能な白黒ページに構成されています。 (オリジナル画面に戻るには現画面を閉じてください。)</p>"};
		return s[ss.lang];
	}
	
	
	public static String getHelp106(SessionState ss)
	{
		String[] s = {"<b>Print Answers Page</b> - Click on this button to see a popup that displays the answers. Every meaning in every row will be displayed in black and white. (Close it to return to the original window.)", 
			"<b>回答ページ印刷</b>―このボタンでポップアップ画面で出てくる回答を表示出来ます。全ての単語と意味は白黒で表示されます。(オリジナル画面に戻るには現画面を閉じて下さい。)"};
		return s[ss.lang];
	}
	
	// needs translation
	public static String getHelp107(SessionState ss)
	{
		String[] s = {"<b>Sorry, but no Help Page is available.</b>", 
			"<b>Sorry, but no Help Page is available.</b>"};
		return s[ss.lang];
	}

	// needs translation
	public static String getHelp108(SessionState ss)
	{
		String[] s = {"<b>Change Language: </b> this link allows you to study rows of English words and their meanings in other languages.",
			"japanese"};
		return s[ss.lang];
	}


	// needs translation
	public static String getHelp(SessionState ss)
	{
		String l = "<ul class='listings'><li></li></ul><ol class='listings'><li></li></ol>";
		String[] s = {"", 
			"japanese"};
		return s[ss.lang];
	}
	
}
