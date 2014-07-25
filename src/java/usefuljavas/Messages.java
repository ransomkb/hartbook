// Altered on 2006-05-21

// A Class that provides English Messages for Book classes.

package usefuljavas;


public final class Messages 
{

    // returns a mess of which sets and types are being displayed
	public static String getMess0(SessionState ss)
	{
		String[] s = {"Check here for messages.",
			"Translate"};
		return s[ss.lang];
	}

    // returns a mess of which sets and types are being displayed
	public static String getMess1(String set, SessionState ss)
	{
		String[] s = {"Displaying "+ss.type+"s from "+set, 
			set+"　から　"+ss.type+"　を表示しています"};
		return s[ss.lang];
	}
	
	// returns a mess of Blank Fields
	public static String getMess2(SessionState ss)
	{
		String[] s = {"All the Fields Were Blank", 
			"未入力のままです"};
		return s[ss.lang];
	}
	
	// returns a mess of Row Added
	public static String getMess3(SessionState ss)
	{
		String[] s = {"Row Added", 
			"一欄に追加"};
		return s[ss.lang];
	}
	
	// returns a mess of Words Already Entered
	public static String getMess4(SessionState ss)
	{
		String[] s = {"Words Already Entered", 
			"既に入力済み"};
		return s[ss.lang];
	}
	
	// IMPORTANT: changed, needs translation maybe not using; maybe a bit redundant
    // returns a mess of Blank Field
	public static String getMess5(SessionState ss)
	{
		String[] s = {"Please Do Not Leave a Field Blank", 
			"空白のままにしないで下さい"};
		return s[ss.lang];
	}
	
	// returns a mess of Searched for Kanji
	public static String getMess6(SessionState ss)
	{
		String[] s = {"Searched for "+ss.kan, 
			"検索しました "+ss.kan};
		return s[ss.lang];
	}
	
	// returns a mess of Searched for Hiragana
	public static String getMess7(SessionState ss)
	{
		String[] s = {"Searched for "+ss.yom, 
			"検索しました "+ss.yom};
		return s[ss.lang];
	}
	
	// returns a mess of Disp. Chosen Set
	public static String getMess8(SessionState ss)
	{
		String[] s = {"Displaying Chosen Set of Words", 
			"選択した単語を表示する"};
		return s[ss.lang];
	}

    // IMPORTANT: may be redundant
	// returns a mess of Blank Field
	public static String getMess9(SessionState ss)
	{
		String[] s = {"Please Do Not Leave a Blank Field", 
			"空白のままで残さないで下さい"};
		return s[ss.lang];
	}
	
	// returns a mess of Row Updated
	public static String getMess10(SessionState ss)
	{
		String[] s = {"Row Updated", 
			"一欄が更新されました"};
		return s[ss.lang];
	}
	
	// returns a mess of Using ss.displaylist
	public static String getMess11(SessionState ss)
	{
		String[] s = {"Using "+ss.displayList, 
			"使用中 "+ss.displayList};
		return s[ss.lang];
	}
	
	// returns a mess of Finished Study List
	public static String getMess12(SessionState ss)
	{
		String[] s = {"You have studied all of the words in the Study List.<br />"+
					"Click on the Home link to start over or on the button to study the same list.",
			"勉強リストにある全ての単語を勉強しました・このまま続けるか又は私のリストのリンクへ行って他の単語を勉強して下さい"};
		return s[ss.lang];
	}
	
	// returns a mess of Finished Test List
	public static String getMess13(SessionState ss)
	{
		String[] s = {"You have been tested on all of the words in the Test List.<br />"+
					"Click on the Home link to start over or on the button to take the same test.",
			"テストリストの中にある全ての単語のテストを行いました・このまま続けるか又は私のリストのリンクへ行って他のテストを行って下さい"};
		return s[ss.lang];
	}
	
	// returns a mess of Moved Meanings
	public static String getMess14A(SessionState ss)
	{
		String[] s = {"Meanings have been moved",
			"選択した単語は移動されました"};
		return s[ss.lang];
	}

    // IMPORTANT: may be redundant
	// returns a mess of Moved Meanings
	public static String getMess14(SessionState ss)
	{
		String[] s = {"Meanings have been moved",
			"選択した単語は移動されました"};
		return s[ss.lang];
	}
	
	// returns a mess of Wordlist size under 25
	public static String getMess15(SessionState ss)
	{
		String[] s = {"WordList size is less than 25", 
			"単語リストのサイズは２５以下です"};
		return s[ss.lang];
	}
	
	// returns a mess of Wordlist size is under or equal to 50
	public static String getMess16(SessionState ss)
	{
		String[] s = {"WordList size is less than or equal to 50", 
			"単語リストのサイズは５０又はそれ以下です"};
		return s[ss.lang];
	}
	
	// returns a mess of Finished List
	public static String getMess17(SessionState ss)
	{
		String[] s = {"You Finished the List!", 
			"リストを終了されました！"};
		return s[ss.lang];
	}

    // IMPORTANT: may no longer be needed
	// returns a mess of All types in ss.whereval placed in Know or Maybe
	public static String getMess18(SessionState ss)
	{
		String[] s = {"All the "+ss.type+"s in "+ss.whereVal+" have been placed in your Know List or Maybe Know List", 
			"全ての　"+ss.whereVal+"　の "+ss.type+"　の単語は知っているリスト又はたぶん知っているリストに移動されました"};
		return s[ss.lang];
	}
	
	// returns a mess of how many sets of what type added to My List
	public static String getMess19(int i, SessionState ss)
	{
		String[] s = {i+" sets of "+ss.type+"s have been added to My List", 
			ss.type+"　の　"+i+" セットは私のリストに追加されました"};
		return s[ss.lang];
	}
	
	// returns a mess of IncorrectNumber Input
	public static String getMess20(SessionState ss)
	{
		String[] s = {"You did not type in a correct number", 
			"正しい数字が入力されていません"};
		return s[ss.lang];
	}
	
	// returns a mess of Request for Type and Meaning
	public static String getMess21(SessionState ss)
	{
		String[] s = {"Please Enter a Type and Meaning", 
			"タイプと単語を入力してください"};
		return s[ss.lang];
	}
	
	// returns a mess of Request for List Choice
	public static String getMess22(SessionState ss)
	{
		String[] s = {"Choose a List to See, or Add a Row, or Update a Row", 
			"リストを見るか一欄に追加するか一欄を更新するかのどれかを選択してください"};
		return s[ss.lang];
	}
	
	// returns a mess of ss.type from clearlist cleared
	public static String getMess23(String clearList, SessionState ss)
	{
		String[] s = {ss.type+"s from "+clearList+" were Cleared", 
			clearList+"　の　"+ss.type+" は消去されました"};
		return s[ss.lang];
	}
	
	// returns a mess of Row Moved to ...
	public static String getMess24(String moveID, SessionState ss)
	{
		String[] s = {"Row was moved to "+ss.listGroup+" "+moveID, 
			moveID+" "+ss.listGroup+"に一欄は移動されました"};
		return s[ss.lang];
	}
	
	// returns a mess of Request for Search Input
	public static String getMess25(SessionState ss)
	{
		String[] s = {"Please enter a word to search for", 
			"検索したい単語を入力してください"};
		return s[ss.lang];
	}
	
	// returns a mess of Found Rows for ss.searchvar
	public static String getMess26(SessionState ss)
	{
		String[] s = {"Found Row(s) for "+ss.searchVar, 
			"一欄が見つかりました "+ss.searchVar};
		return s[ss.lang];
	}
	
	// returns a mess of Unsuccessful Search
	public static String getMess27(SessionState ss)
	{
		String[] s = {"Search for "+ss.searchVar+" unsuccessful", 
			ss.searchVar+"　の検索は 失敗しました"};
		return s[ss.lang];
	}
	
	// returns a mess of Row added to chap list
	public static String getMess28(SessionState ss)
	{
		String[] s = {"Row added to the chapter list", 
			"チャプターリストに追加されました"};
		return s[ss.lang];
	}
	
	// returns a mess of Incorrect Role
	public static String getMess29(SessionState ss)
	{
		String[] s = {"You do not have the correct role for this action", 
			"こちらにアクセスする権利がありません"};
		return s[ss.lang];
	}
	
	// returns a mess of Uneditable Row
	public static String getMess30(SessionState ss)
	{
		String[] s = {"This row cannot be edited", 
			"この一欄は編集する事が出来ません"};
		return s[ss.lang];
	}
	
	// returns a mess of Clearance Lack
	public static String getMess31(SessionState ss)
	{
		String[] s = {"You do not have clearance", 
			"権利がありません"};
		return s[ss.lang];
	}
	
	// returns a mess of Request for Table Input
	public static String getMess32(SessionState ss)
	{
		String[] s = {"Choose a Table to See, or Add a Row, or Update a Row", 
			"テーブルを見るか一欄に追加するか一欄を更新するか選択して下さい"};
		return s[ss.lang];
	}
	
	// returns a mess of New set name already on list
	public static String getMess33(SessionState ss)
	{
		String[] s = {"New set name was already on the list", 
			"新しいセット名は既にリストに存在します"};
		return s[ss.lang];
	}
	
	// returns a mess of New set name added to list
	public static String getMess34(SessionState ss)
	{
		String[] s = {"New set name was added to the list", 
			"新しいセット名はリストに追加されました"};
		return s[ss.lang];
	}
	
	// returns a mess of ss.selection removed from ss.listname
	public static String getMess35(SessionState ss)
	{
		String[] s = { ss.selection+" was removed from "+ss.listName, 
			ss.selection+" から削除されました "+ss.listName};
		return s[ss.lang];
	}
	
	// returns a mess of excessive number request
	public static String getMess36(SessionState ss)
	{
		String[] s = {"The number of questions you asked for is larger than the list you chose", 
			"質問の量が多すぎて選択されたリストを上回っています"};
		return s[ss.lang];
	}
	
	// returns a mess of quiz list creation and exp
	public static String getMess37(SessionState ss)
	{
		String[] s = {"A quiz list was created. Choose the column and click Print Quiz Page to go to a printable page, or try again", 
			"クイズリストが作成されました・印刷する場合はトップに行って下さい又はもう一度トライして下さい"};
		return s[ss.lang];
	}
	
	// returns a mess of New List Name creation
	public static String getMess38(SessionState ss)
	{
		String[] s = {"A new List Name has been created", 
			"新しいリスト名が作成されました"};
		return s[ss.lang];
	}
	
	// returns a mess of Request for New list name input
	public static String getMess39(SessionState ss)
	{
		String[] s = {"Type in a Name for your new List", 
			"新しいリストの名前をタイプして下さい"};
		return s[ss.lang];
	}
	
	// returns a mess of empty chosen list and request to add more words
	public static String getMess40(SessionState ss)
	{
		String[] s = {"Sorry, but the List you chose is empty.<br />Please add more words to it or choose another List.", 
			"選択されたリストは空です"};
		return s[ss.lang];
	}
	
	// returns a mess of a not being inserted into notepad table
	public static String getMess41(SessionState ss)
	{
		String[] s = {"A note was inserted into table notepad", 
			"テーブルノートパッドにインサートされました"};
		return s[ss.lang];
	}
	
	
	// returns a mess of able to edit row
	public static String getMess42(SessionState ss)
	{
		String[] s = {"This row is ready to be edited", 
			"この単語は編集可能です"};
		return s[ss.lang];
	}

	// IMPORTANT: Needs J translation
	// returns a mess of no row selected
	public static String getMess42A(SessionState ss)
	{
		String[] s = {"Please click on the radio button of the row to be edited.",
			"J"};
		return s[ss.lang];
	}

	
	
	public static String getMess43(SessionState ss)
	{
		String[] s = {"The Teacher Lists were updated", 
			"先生の一覧は更新されました"};
		return s[ss.lang];
	}
	
	
	// Important: needs to be translated
    // returns a mess of fetched teacher rows
	public static String getMess44(SessionState ss)
	{
		String[] s = {"The Teacher Rows Were Fetched", 
			"japanese"};
		return s[ss.lang];
	}
	
	
	// returns a mess of lack of access to program
	public static String getMess45(SessionState ss)
	{
		String[] s = {"Sorry, you do not have access to this program", 
			"このプログラムにアクセス出来ません"};
		return s[ss.lang];
	}
	
	
	
	// Important: needs to be translated
    // returns a mess of Resetting of approved rows to 0
	public static String getMess46(SessionState ss)
	{
		String[] s = {"Edit Column of Approved Rows Were Set to 0", 
			"japaneseEdit Column of Approved Rows Were Set to 0"};
		return s[ss.lang];
	}
	
	
	// returns a mess of Updated Rows
	public static String getMess47(SessionState ss)
	{
		String[] s = {"Row(s) were updated", 
			"単語は更新されました"};
		return s[ss.lang];
	}
	
	
	// returns a mess of Fetched Repeating Rows
	public static String getMess48(SessionState ss)
	{
		String[] s = {"Got repeating rows", 
			"japaneseGot repeating rows"};
		return s[ss.lang];
	}
	
	
	// returns a mess of Request not to use commas or periods
	public static String getMess49(SessionState ss)
	{
		String[] s = {"Please do not use any commas (,) or periods (.)", 
			"コンマ(,)やピリオド(.)は使用しないでください"};
		return s[ss.lang];
	}
	
	
	// Important: needs to be translated
    // returns a mess of already used ID info
	public static String getMess50(SessionState ss)
	{
		String[] s = {"That UserID is already being used.<br />Please try another.", 
			"japanese That UserID is already being used.<br />Please try another."};
		return s[ss.lang];
	}
	
	
	// Important: needs to be translated
    // returns a mess of error for different password versions in register.jsp
	public static String getMess51(SessionState ss)
	{
		String[] s = {"Sorry, but the two versions<br />of your password were different.", 
			"japanese Sorry, but the two versions<br />of your password were different."};
		return s[ss.lang];
	}
	
	
	// Important: needs to be translated
    // returns a mess of UserID still logged in
	public static String getMess52(SessionState ss)
	{
		String[] s = {"That UserID is still logged in.<br />Please wait (up to 20 minutes)</br>for automatic log-out, or log out.",
			"japanese"};
		return s[ss.lang];
	}


	// Important: needs to be translated
    // returns a mess of blank ID or Pswd
	public static String getMess53(SessionState ss)
	{
		String[] s = {"Sorry, but your ID or Password was blank.</br> Please try again.",
			"japanese"};
		return s[ss.lang];
	}

	// Important: needs to be translated
    // returns a mess of No ID in Database
	public static String getMess54(SessionState ss)
	{
		String[] s = {"Sorry, but there was a problem logging in.</br>That id is not in the database.</br>Please check your spelling.",
			"japanese"};
		return s[ss.lang];
	}


	// Important: needs to be translated; may be redundant
    // returns a mess of error as Pswd and ID don't match
	public static String getMess55(SessionState ss)
	{
		String[] s = {"Sorry, but there was a problem logging in.</br>That password does not match the ID.",
			"japanese"};
		return s[ss.lang];
	}


	
	// Important: needs to be translated
    // returns a mess of
	public static String getMess(SessionState ss)
	{
		String[] s = {"", 
			"japanese"};
		return s[ss.lang];
	}
	
}
