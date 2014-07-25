<%--
    Document   : switch_lang.jsp
    Created on : May 5, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html;charset=UTF-8" %>

<%@ page import = "usefuljavas.SessionState" %>
<%@ page import="usefuljavas.Selections" %>
<%@ page import="usefuljavas.DBHandler" %>
<%@ page import="usefuljavas.Buttons" %>

<%@ page session = "true" %>

<%
    SessionState ss = new SessionState();
    ss = (SessionState) session.getAttribute("Session");
    if (ss != null)
    {
        DBHandler.saveState(ss);
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
        <title>Switch Languages</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <SCRIPT TYPE="text/javascript">
        <!--
            function targetopener(mylink, closeme, closeonly)
            {
                if(! (window.focus && window.opener))return true;

                window.opener.focus();

                if(!closeonly)window.opener.location.href=mylink.href;
                if(closeme)window.close();

                return false;
            }
        //-->
        </SCRIPT>

		<%
			try
			{
				//ss.message = "";

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");

				if(request.getParameter("switchTable") != null)
				{
					ss.table = request.getParameter("tables");

					DBHandler.setThreeCols(ss);

					ss.language = ss.table;

					DBHandler.fetchAllOwnLists(ss);
					DBHandler.fetchChapNames(ss);
				}
			}
			catch(Exception e) {System.err.println("Exception: " +e.getMessage());}
		%>

    </head>
    
    <body>

    <!-- Begin Main Table -->
	<%= ss.html.begMainTable() %>

            <!-- Top Banner -->
            <tr>
                <td width="690" height="97" colspan="3">
                    <img src="images/top_banner.gif" alt="Banner" width="690" height="97" />
                </td>
            </tr>

             <!-- Start Top Body Content Nested Table, with Left and Right Cells -->
              <tr>
                <td width='690' valign='top' height='66' class='body_top' align='center'>
                  <table cellpadding='0' cellspacing='0' border='0' width='650'>
                    <tr> <!-- Left Cell -->
                      <td width='325' align='right' class='body_top_content_left'>
                        <p class='section_header'>Present Language:</p></td>
                      <!-- Right Cell -->
                      <td width='325' align='left'>
                        <p class='study_word'><%= ss.table %></p>
                        <!-- <p class='study_word'><%= ss.threeCols %></p> -->
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <!-- End Body Top Content, Left and Right Cells -->

          <!-- Start Main Body Content -->
          <tr>
            <td width='690' valign='top' class='body_content'>
            <!-- Section Header -->
                <form method='post' name='add' accept-charset="UTF-8">
                    <table width='650' border='0'>
                        <tr>
                            <td>
                                <p class='section_header'>Choose a Language:</p>
                            </td>
                            <td class='center'>
                                <%= Selections.getSelect15B(ss) %>
                            </td>
                            <td>
                                <%= Buttons.roundButton(Buttons.getBut34(ss), ss) %>
                            </td>
                        </tr>
                    </table>
                </form>
            </td>
          </tr>
          <!-- End Main Body Content-->

          <!-- Start Footer Content Nested Table, with Left and Right Cells -->
          <tr>
            <td width='690' height='75' class='footer' valign='bottom' align='center'>
                <table width='650' border='0'>
                    <tr>
                        <td>
                            <p class='p'><%= ss.message %></p>
                            <p class='study_word_sentence'>Click
                                <A HREF='/<%= ss.folder %>/startButtons' onClick='return targetopener(this,true,true)' > here </A>
                                to return to the Home window
                            </p>
                        </td>
                            <!-- Copyright, Right Cell -->
                        <td>
                            <span class='small'>Copyright © 2006 Hart Book. All Rights Reserved.</span>
                        </td>
                    </tr>
                </table>
            </td>
          </tr>
          <!-- End Footer Content, Left and Right Cells -->

	<%= ss.html.endTable() %>

    </body>
</html>
