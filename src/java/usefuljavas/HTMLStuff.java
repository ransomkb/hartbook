package usefuljavas;

/**
 *
 * @author BarberRansom
 */

import java.util.*;

import usefulbeans.WordBean;

public class HTMLStuff
{

    public int lang = 1;
    private int bord = 0;

    //public String check = "Don't Got It";
    //private String style = " style='font-family: HGP明朝E, sans-serif; font-size: 12pt'";
    
    public String srcpage = "/front_page.jsp";

    // make some of these static and make the others so don't need to be synchronized
    public String folder = "hartbook";
    public String bgcolor = "dbf1bb"; // fffacd (lemon chiffon) ; f5deb3 (wheat) ; ffefd5 (papaya whip) ; f5f5dc (beige) ; ffe4b5 (moccasin) ; f5f3ea (cream) ; e7eddf (light green-blue) ; dbf1bb (light green) ; f0f8ff (Alice Blue) ; faebd7 (Antique White) ;
    private String links = "";

    private String[] linkArraySamp = {"c0", "c1", "c2", "c3"}; // place holders
    private String[] linkArray = {"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8","c9", "c10", "c11"}; // place holders
    private String[][] sampPage = {{"Front Page", "前面"}, {"Sample", "サンプル"}, {"Log In", "ログイン"}, {"Register", "登録"}};

    public HTMLStuff()
    {
        this.srcpage = "front_page.jsp";
        this.lang = 1;
    } 
   
    
    public void setSrcPage(String url, SessionState ss)
    {
        this.folder = ss.folder;
        ss.exp.folder = this.folder;
        ss.srcPage = "/"+this.folder+"/"+url;
        this.srcpage = ss.srcPage;
    }
    

    public String getSrcPage()
    {
        return this.srcpage;
    }

    public void setLang(SessionState ss)
    {
            this.lang = ss.lang;
    }    

    // returns English or 日本語
    public String getLang()
    {
            if(this.lang == 1) return "English";
            else return "日本語";
    }

    // returns E or J version of the front_page.jsp
    private String getVersion()
    {
            if(lang == 1) return "<a href='/"+folder+"/front_page.jsp'>English Version</a>";
            else return "<a href='/"+folder+"/front_page.jsp'>日本語訳文</a>";
    }

    // returns the title of the page
    public String getSampPage(int x)
    {
            return sampPage[x][lang];
    }

    // returns javascript for popups
    public String getPopUpScript()
    {
            String s = "function popup(mylink, windowname, w, h){if (! window.focus)return true;var href;"+
                    "if (typeof(mylink) == 'string')href=mylink; else href=mylink.href;"+
                    "window.open(href, windowname, 'width='+w+',height='+h+',scrollbars=yes'); return false;} ";

            return s;
    }

    // returns a variable for getRollOver; used in Study
    public String getRollOverVar (int x, SessionState ss)
    {
        ss.studyInt = x;

        String s = String.valueOf(x);

        return s;
    }

    // returns javascript for rollover needs; used in Study
    public String getRollOver(SessionState ss)
    {
        String s = "function mouseOver(s)"+
            "{document.getElementById('engEx').innerHTML='"+ss.html.getMouseOverEng(ss)+"'; document.getElementById('langEx').innerHTML='"+ss.html.getMouseOverLang(ss)+"';} "+
            "function mouseOut()"+
            "{document.getElementById('engEx').innerHTML='English Example'; document.getElementById('langEx').innerHTML='Language Example';}";

        return s;
    }

    // returns and sets variables in SS for rollover needs in ; used in Study
    public String getMouseOverEng(SessionState ss)
    {
        WordBean wBean = new WordBean();
        wBean = ss.tempList.get(ss.studyInt);
        ss.engExStudy = wBean.getEngEx();

        return ss.engExStudy;
    }

    // returns and sets variables in SS for rollover needs in ; used in Study
    public String getMouseOverLang(SessionState ss)
    {
        WordBean wBean = new WordBean();
        wBean = ss.tempList.get(ss.studyInt);
        ss.langExStudy = wBean.getLangEx();

        return ss.langExStudy;
    }

    // returns html for creating the main table
    public String begMainTable()
    {
            String s =  "<table cellpadding='0' cellspacing='0' border='"+bord+"' align='center' width='690' class='margin_set'>";
            return s;
    }


    public String begTable()
    {
            String s =  "<table width='650' cellpadding='0' cellspacing='0' border='"+bord+"' align='center'>";
            return s;
    }


    public String begBordSpTable()
    {
            String s =  "<table width='650' cellpadding='0' cellspacing='2' border='"+bord+"' align='center'>";
            return s;
    }


    public String begHalfTable()
    {
            String s =  "<table width='300' cellpadding='0' cellspacing='0' border='"+bord+"' align='center'>";
            return s;
    }


    public String mainBodyTopRow()
    {
            String s =  "<tr><td width='650' class='body_content' align='center'>"; //  width='650' height='66'
            return s; // original: "<tr><td class='body_top' align='center'>"
    }


    public String mainBodyRow()
    {
            String s =  "<tr><td width='650' class='body_content'>";
            return s;
    }


    public String begRow()
    {
            String s =  "<tr><td width='650' class='body_content'>";
            return s;
    }


    public String begHalfRow()
    {
            String s =  "<tr><td width='300'>";
            return s;
    }


    public String endRow()
    {
            String s =  "</td></tr>";
            return s;
    }


    public String endTable()
    {
            String s =  "</table>";
            return s;
    }

    // returns html for ending a table, the body and the html
    public String endMainTBH()
    {
            String s =  "</table></body></html>";
            return s;
    }

    // returns html for ending the body and the html
    public String endBodyHtml()
    {
            String s =  "</body></html>";
            return s;
    }


    public String banner()
    {
            String s =  "<tr><td width='650' height='107'>"+
                            "<img src='images/top_bannerg.gif' alt='Banner' width='690' height='97' /></td></tr>";
            return s;
    }

    // returns a form for handling the whether the page is displayed in E or J, and prints the links
    public String mainNav(SessionState ss)
    {
            String s =  "<form name='lang' method='post' accept-charset='UTF-8' action='/hartbook/Language'><tr><td class='body_top' width='650'>"+
                            "<table width='650' cellpadding='0' cellspacing='0' border='"+bord+"' align='center' class='margin_set'><tr>"+
                            Buttons.roundButton(Buttons.getBut21(ss), ss)+
                            "<td width='550' align='center'>"+getLinks(ss)+
                            "</td></tr></table></td></tr></form>";
            return s;
    }

    // returns a form for handling the whether the page is displayed in E or J, and prints the links
    public String homeNav(SessionState ss)
    {
            String s =  "<form name='lang' method='post' accept-charset='UTF-8' action='/hartbook/Language'><tr><td class='body_top' width='650'>"+
                            "<table width='650' cellpadding='0' cellspacing='0' border='"+bord+"' align='center' class='margin_set'><tr>"+
                            Buttons.roundButton(Buttons.getBut21(ss), ss)+
                            "<td width='550' align='center'>"+getHomeLink(ss)+
                            "</td></tr></table></td></tr></form>";
            return s;
    }

    // returns the html for printing the links
    public String frontPageNav()
    {
            String s =  "<tr><td width='650'>"+
                            "<table width='650' cellpadding='0' cellspacing='0' border='"+bord+"' align='center' class='margin_set'><tr>"+
                            "<td width='150' align='center'>"+getVersion()+"</td>"+
                            "<td width='500' align='center'>"+getSampLinks()+
                            "</td></tr></table></td></tr>";
            return s;
    }

    // returns a form for handling the whether the page is displayed in E or J, and prints the links
    public String sampNav()
    {
            String s =  "<form name='lang' method='post' accept-charset='UTF-8' action='/hartbook/Language'><tr><td class='body_top' width='650'>"+
                            "<table width='650' cellpadding='0' cellspacing='0' border='"+bord+"' align='center' class='margin_set'><tr>"+
                            "<td align='center'><table cellpadding='0' cellspacing='0' border='"+bord+"'><tr>"+
                            "<td class='button-lg-left' cellpadding='1'>&nbsp;</td><td class='button-lg-mid' >"+
                            "<input type='submit' name='language' value='"+getLang()+"' class='button-lg-mid' ></td>"+
                            "<td class='button-lg-right' cellpadding='1'>&nbsp;</td></tr></table></td>"+
                            "<td width='550' align='center'>"+getSampLinks()+
                            "</td></tr></table></td></tr></form>";
            return s;
    } // +"<input type='hidden' name='srcPage' value='"+getSrcPage()+"'>


    public String sampFooter()
    {
            String s =  "<tr><td width='650' height='75' class='footer'>"+
                    "<table cellpadding='0' cellspacing='0' border='"+bord+"' width='650' align='center'>"+
                    "<!-- Footer Nav, Left Cell --><tr><td width='325' valign='bottom'>"+getTinySampLinks()+"</td>"+
                    "<!-- Copyright, Right Cell --><td width='325' valign='bottom' align='right'>"+
                    "<span class='small'>Copyright © 2006 Hart  Book. All Rights Reserved.</span></td></tr></table>"+
                    "<!-- End Footer Content, Left and Right Cells --></td></tr>";
            return s;
    }

    
    public String footer(SessionState ss)
    {
            String s =  "<tr><td width='650' height='75' class='footer'>"+
                    "<table cellpadding='0' cellspacing='0' border='"+bord+"' width='650' align='center'>"+
                    "<!-- Footer Nav, Left Cell --><tr><td width='325' valign='bottom'>"+getTinyLinks(ss)+"</td>"+
                    "<!-- Copyright, Right Cell --><td width='325' valign='bottom' align='right'>"+
                    "<a href='mailto:rkbarber@hartvocab.com' class='tinylink'>"+ss.exp.getExp39()+"</a><br />"+
                    "<span class='small'>Copyright © 2006 Hart  Book. All Rights Reserved.</span></td></tr></table>"+
                    "<!-- End Footer Content, Left and Right Cells --></td></tr>";
            return s;
    }


    // returns a string for debugging; use this to send to log
    public String deBug(SessionState ss)
    {
            //System.out.println(ss.debugString);
            String s =  "</table><table><tr><td><pre>"+ss.debugString+"</pre></td></tr></table>";
            ss.debugString = "";
            return s;
    }
    

    private void setHomeLink(SessionState ss)
    {
            String[] linkArrayE = {"<a href='/"+folder+"/Home'>Home</a>",
                    "<a href='/"+folder+"/"+ss.help+"' onClick='return popup(this, \"help\", 750, 600)'>Help</a>"};

            String[] linkArrayJ = {"<a href='/"+folder+"/Home'>ホーム</a>",
                    "<a href='/"+folder+"/"+ss.help+"' onClick='return popup(this, \"help\", 750, 600)'>ヘルプ</a>"};

            if(lang == 0) linkArraySamp = linkArrayE;
            else linkArraySamp = linkArrayJ;
    }


    private void setSampLinks()
    {
            String[] linkArrayE = {"<a href='/"+folder+"/front_page.jsp'>Front Page</a>",
                    "<a href='/"+folder+"/sample.jsp'>Sample</a>",
                    "<a href='/"+folder+"/register.jsp'>Register</a>",
                    "<a href='/"+folder+"/login.jsp'>Log In</a>",
                    "<a href='/"+folder+"/paypalbutton.jsp'>Create Lists</a>"};

            String[] linkArrayJ = {"<a href='/"+folder+"/front_page.jsp'>前面</a>",
                    "<a href='/"+folder+"/sample.jsp'>サンプル</a>",
                    "<a href='/"+folder+"/register.jsp'>登録</a>",
                    "<a href='/"+folder+"/login.jsp'>ログイン</a>",
                    "<a href='/"+folder+"/paypalbutton.jsp'>リストの作成</a>"};

            if(lang == 0) linkArraySamp = linkArrayE;
            else linkArraySamp = linkArrayJ;
    }


    private void setTinySampLinks()
    {
            String[] smLinkArrayE = {"<a href='/"+folder+"/front_page.jsp' class='tinylink'>Front Page</a>",
                    "<a href='/"+folder+"/sample.jsp' class='tinylink'>Sample</a>",
                    "<a href='/"+folder+"/register.jsp' class='tinylink'>Register</a>",
                    "<a href='/"+folder+"/login.jsp' class='tinylink'>Log In</a>",
                    "<a href='/"+folder+"/paypalbutton.jsp' class='tinylink'>Create Lists</a>"};

            String[] smLinkArrayJ = {"<a href='/"+folder+"/front_page.jsp' class='tinylink'>前面</a>",
                    "<a href='/"+folder+"/sample.jsp' class='tinylink'>サンプル</a>",
                    "<a href='/"+folder+"/register.jsp' class='tinylink'>登録</a>",
                    "<a href='/"+folder+"/login.jsp' class='tinylink'>ログイン</a>",
                    "<a href='/"+folder+"/paypalbutton.jsp' class='tinylink'>リストの作成</a>"};


            if(lang == 0) linkArraySamp = smLinkArrayE;
            else linkArraySamp = smLinkArrayJ;
    }


    private void setLinks(SessionState ss)
    {// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            String[] linkArrayE = {"<a href='/"+folder+"/forlang.jsp' onClick='return popup(this, \"help\", 750, 600)'>Change Language</a>",
                    "<a href='/"+folder+"/start_buttons.jsp'>Home</a>",
                    "<a href='/"+folder+"/edit_lists.jsp'>Create Lists</a>",
                    "<a href='/"+folder+"/display_lists.jsp'>Display Lists</a>",
                    "<a href='/"+folder+"/studyhistory.jsp'>History</a>",
                    "<a href='/"+folder+"/"+ss.help+"' onClick='return popup(this, \"help\", 750, 600)'>Help</a>",
                    "<a href='/"+folder+"/loggedout.jsp'>Log Out</a>",
                    "<a href='/"+folder+"/admin.jsp'>Admin</a>"};

                    // "<a href='/"+folder+"/teachlists'>Teacher Lists</a>",
                    // "<a href='/"+folder+"/edit_tables.jsp'>Edit Tables</a>",
                    // "<a href='/"+folder+"/dbdebug'>DB Debug</a>",
                    // "<a href='/"+folder+"/notepad.jsp'>Notepad</a>",

            String[] linkArrayJ = {"<a href='/"+folder+"/forlang.jsp' onClick='return popup(this, \"help\", 750, 600)'>他の外国語</a>",
                    "<a href='/"+folder+"/start_buttons.jsp'>ホーム</a>",
                    "<a href='/"+folder+"/edit_lists.jsp'>リストの作成</a>",
                    "<a href='/"+folder+"/display_lists.jsp'>リストの表示</a>",
                    "<a href='/"+folder+"/studyhistory.jsp'>履歴</a>",
                    "<a href='/"+folder+"/"+ss.help+"' onClick='return popup(this, \"help\", 750, 600)'>ヘルプ</a>",
                    "<a href='/"+folder+"/loggedout.jsp'>ログアウト</a>",
                    "<a href='/"+folder+"/admin.jsp'>Admin</a>"};

                    // "<a href='/"+folder+"/teachlists'>先生リスト</a>",
                    // "<a href='/"+folder+"/edit_tables.jsp'>編集テーブル</a>",
                    // "<a href='/"+folder+"/dbdebug'>DB Debug</a>",
                    // "<a href='/"+folder+"/notepad.jsp'>Notepad</a>",

            if(lang == 0) linkArray = linkArrayE;
            else linkArray = linkArrayJ;

    }


    private void setTinyLinks(SessionState ss)
    {// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            String[] smLinkArrayE = {"<a href='/"+folder+"/forlang.jsp' class='tinylink' onClick='return popup(this, \"help\", 750, 600)'>Change Language</a>",
                    "<a href='/"+folder+"/start_buttons.jsp' class='tinylink'>Home</a>",
                    "<a href='/"+folder+"/edit_lists.jsp' class='tinylink'>Create Lists</a>",
                    "<a href='/"+folder+"/display_lists.jsp' class='tinylink'>Display Lists</a>",
                    "<a href='/"+folder+"/studyhistory.jsp' class='tinylink'>History</a>",
                    "<a href='/"+folder+"/"+ss.help+"' class='tinylink' onClick='return popup(this, \"help\", 750, 600)'>Help</a>",
                    "<a href='/"+folder+"/loggedout.jsp' class='tinylink'>Log Out</a>",
                    "<a href='/"+folder+"/admin.jsp' class='tinylink'>Admin</a>"};

                    // "<a href='/"+folder+"/teachlists' class='tinylink'>Teacher Lists</a>",
                    // "<a href='/"+folder+"/edit_tables.jsp' class='tinylink'>Edit Tables</a>",
                    // "<a href='/"+folder+"/dbdebug' class='tinylink'>DB Debug</a>",
                    // "<a href='/"+folder+"/notepad.jsp' class='tinylink'>Notepad</a>",


            String[] smLinkArrayJ = {"<a href='/"+folder+"/forlang.jsp' class='tinylink' onClick='return popup(this, \"help\", 750, 600)'>他の外国語</a>",
                    "<a href='/"+folder+"/start_buttons.jsp' class='tinylink'>ホーム</a>",
                    "<a href='/"+folder+"/edit_lists.jsp' class='tinylink'>リストの作成</a>",
                    "<a href='/"+folder+"/display_lists.jsp' class='tinylink'>リストの表示</a>",
                    "<a href='/"+folder+"/studyhistory.jsp' class='tinylink'>履歴</a>",
                    "<a href='/"+folder+"/"+ss.help+"' class='tinylink' onClick='return popup(this, \"help\", 750, 600)'>ヘルプ</a>",
                    "<a href='/"+folder+"/loggedout.jsp' class='tinylink'>ログアウト</a>",
                    "<a href='/"+folder+"/admin.jsp' class='tinylink'>Admin</a>"};

                    // "<a href='/"+folder+"/teachlists' class='tinylink'>先生リスト</a>",
                    // "<a href='/"+folder+"/edit_tables.jsp' class='tinylink'>編集テーブル</a>",
                    // "<a href='/"+folder+"/dbdebug' class='tinylink'>DB Debug</a>",
                    // "<a href='/"+folder+"/notepad.jsp' class='tinylink'>Notepad</a>",


            if(lang == 0) linkArray = smLinkArrayE;
            else linkArray = smLinkArrayJ;
    }


    public String getHomeLink(SessionState ss)
    {
            setHomeLink(ss);

            links = linkArraySamp[0]+"&nbsp;|&nbsp;"+
                    linkArraySamp[1];

            //links = links+"&nbsp;|&nbsp;"+lang;

            return links;
    }


    public String getSampLinks()
    { 
            setSampLinks();

            links = linkArraySamp[0]+"&nbsp;|&nbsp;"+
                    linkArraySamp[1]+"&nbsp;|&nbsp;"+
                    linkArraySamp[2]+"&nbsp;|&nbsp;"+
                    linkArraySamp[3];// for paypalbutton.jsp +"&nbsp;|&nbsp;"+linkArraySamp[4]

            //links = links+"&nbsp;|&nbsp;"+lang;

            return links;
    }


    public String getTinySampLinks()
    { 
            setTinySampLinks();

            links = linkArraySamp[0]+"&nbsp;|&nbsp;"+
                    linkArraySamp[1]+"&nbsp;|&nbsp;"+
                    linkArraySamp[2]+"&nbsp;|&nbsp;"+
                    linkArraySamp[3]; // for paypalbutton.jsp +"&nbsp;|&nbsp;"+linkArraySamp[4]

            return links;
    }


    public String getLinks(SessionState ss)
    { 
            setLang(ss);

            setLinks(ss);

            if(ss.role.equals("admin"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[2]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6]+"&nbsp;|&nbsp;"+
                            linkArray[7];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("payer"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[2]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("user"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("sampler"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("teacher"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[2]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("student"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.length() == 0) {links = "Role is empty";}

            return links;
    }


    public String getTinyLinks(SessionState ss)
    {
            setLang(ss);

            setTinyLinks(ss);

            if(ss.role.equals("admin"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[2]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6]+"&nbsp;|&nbsp;"+
                            linkArray[7];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("payer"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[2]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("user"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("sampler"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("teacher"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[2]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.equals("student"))
            {
                    links = linkArray[1]+"&nbsp;|&nbsp;"+
                            linkArray[3]+"&nbsp;|&nbsp;"+
                            linkArray[4]+"&nbsp;|&nbsp;"+
                            linkArray[5]+"&nbsp;|&nbsp;"+
                            linkArray[6];
            }// 0=CHANGE-LANG, 1=HOME, 2=EDIT, 3=DISP, 4=HIST, 5=HELP, 6=LOGOUT, 7=ADMIN
            else if(ss.role.length() == 0) {links = "Role is empty";}

            return links;
    }
    

    public String roundButton(String button)
    {
            String roundBut = "<td align='center'><table cellpadding='0' cellspacing='0' border='"+bord+"'><tr>"+
                    "<td class='button-lg-left' cellpadding='1'>&nbsp;</td>"+
                    "<td class='button-lg-mid'>"+button+"</td>"+
                    "<td class='button-lg-right' cellpadding='1'>&nbsp;</td></tr></table></td>";

            return roundBut;
    }

    // returns input value = Try It Out; used in sample.jsp
    public String getBut10()
    {
            String[] s =  {"<input type='submit' name='addwordsets' value='Try It Out' class='button-lg-mid'/>",
                    "<input type='submit' name='addwordsets' value='やって見よう' class='button-lg-mid'/>"};
            return s[lang];
    }

    // returns input value = Log In; used in login.jsp
    public String getBut11()
    {
            String[] s =  {"<input type='submit' name='login' value='Log In' class='button-lg-mid'/>",
                    "<input type='submit' name='login' value='ログイン' class='button-lg-mid'/>"};
            return s[lang];
    }

    // returns input value = Register; used in register.jsp
    public String getBut12()
    {
            String[] s =  {"<input type='submit' name='register' value='Register' class='button-lg-mid'/>",
                    "<input type='submit' name='register' value='登録' class='button-lg-mid'/>"};
            return s[lang];
    }


	// IMPORTANT: Check with Galt
	// Is SessionState ss necessary? Should it be synchronized?
	public String textField(int length, String name, String val, SessionState ss)
	{		//ss.dbgApp("got to sel.adjustTypList 1: typ = "+typ);
		//String input = "";

		String input = "<input type='text' style='width:250px' maxlength=\""+length+"\" name=\""+name+"\" value=\""+val+"\" />";

		return input;
	} // returns a string for a text-field

}
