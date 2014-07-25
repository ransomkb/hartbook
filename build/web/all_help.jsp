<!-- Updated on 2008-01-14 -->

<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<%@ page import = "usefuljavas.SessionState" %>
<%@ page import = "usefuljavas.HelpMess" %>
<%@ page import = "usefuljavas.DBHandler" %>

<%@ page session = "true" %>

<%
    SessionState ss = new SessionState();
    ss = (SessionState) session.getAttribute("Session");
    if (ss != null)
    {
        DBHandler.saveState(ss); //
    }
    else
    {
    %>
        <jsp:forward page = "GetSessionState" />
    <%
    }

    ss.fullHelp = "help_edit_rows2.jsp";
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Help</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <SCRIPT TYPE="text/javascript">
        <!--
            function targetopener(mylink, closeme, closeonly)
            {
                if(! (window.focus && window.opener))return true;

                window.opener.focus();

                if(! closeonly)window.opener.location.href=mylink.href;
                if(closeme)window.close();

                return false;
            }
        //-->
        </SCRIPT>
    </head>
    <body>
        <table cellpadding="0" cellspacing="0" border="0" align="center" width="690">
            <!-- Top Banner --> 
            <tr> 
                <td width="690" height="97" colspan="3">
                    <img src="images/top_banner.gif" alt="Banner" width="690" height="97" />
                </td>
            </tr>

            <!-- Start Top Body Content Nested Table, with Left and Right Cells --> 
            <tr> 
                <td width="690" valign="top" height="66" class="body_top" align="center" colspan="3"> 
                    <table cellpadding="0" cellspacing="0" border="0" width="650" height="66">
                        <tr> <!-- Left Cell --> 
                            <td width="325" valign="middle" align="right" class="body_top_content_left"> 
                                <p class="section_header">Help Page</p></td>
                            <!-- Right Cell --> 
                            <td width="325" valign="top" align="right">
                                <p class="study_word">&nbsp;</p>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <!-- End Body Top Content, Left and Right Cells --> 

            <!-- Start Main Body Content -->
            <tr>
                <td width="690" valign="top" class="body_content">
                <!-- Section Header --><!-- Some text --> 
                    <table width="100%">
                    <!-- Link to see the full Help page
                        <tr>
                            <td>
                                <p class="study_word"><%= HelpMess.getHelp1A(ss) %></p>
                            </td>
                        </tr> --> 
                        <!-- Link to return to the original --> 
                        <tr>
                            <td>
                                <p class="study_word"><%= HelpMess.getHelp1(ss) %></p>
                            </td>
                        </tr>

                        <%= HelpMess.getHelpMess(ss) %>

                        <!-- Link to return to the original --> 
                        <tr>
                            <td>
                                <p class="study_word"><%= HelpMess.getHelp1(ss) %></p>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <!-- End Main Body Content-->

            <!-- Start Footer Content Nested Table, with Left and Right Cells --> 
            <tr> 
                <td width="690" height="68" class="footer" valign="bottom" align="right" colspan="3"> 
                <!-- Copyright, Right Cell --> 
                    <span class="small">Copyright © 2006 Hart Book. All Rights Reserved.</span> 
                </td>
            </tr>
            <!-- End Footer Content, Left and Right Cells --> 

        </table>
    </body>
</html>
