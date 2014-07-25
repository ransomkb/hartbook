<%-- 
    Document   : study.jsp
    Created on : Feb 26, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
<%@ page import="usefuljavas.DBHandler" %>
<%@ page import="usefuljavas.Buttons" %>
<%@ page import="usefulbeans.WordBean" %>
<%@ page session="true" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
	SessionState ss = new SessionState();
	ss = (SessionState)session.getAttribute("Session");
        
	if (ss == null)
	{
%>
            <jsp:forward page = 'GetSessionState' />
<%
	}
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Study</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %><%= ss.html.getRollOver(ss) %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("study.jsp", ss);
    ss.setLang();
    ss.helpInt = 2;

	// ss.message = "";
    // DBHandler.saveState(ss);
	// ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
            // through ss.dbgApp

    if(ss.numOfRounds == 0) {ss.round = 1;}
%>

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Logged In Navigation -->
	<%= ss.html.homeNav(ss) %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>

        <form name="studyform" action='/<%= ss.folder %>/Study' method='post' accept-charset='UTF-8'>

	<%= ss.html.begTable() %>
	
                <tr><!-- User ID, Left Cell -->
                    <td width='325' valign='middle' align='right' class='body_top_content_left'><%= ss.message %></td>
                    <!-- Button, Right Cell -->
                    <td>
                        <p><%= ss.exp.getExp14() %><%= ss.round %></p>
                    </td>
                    <%= Buttons.roundButton(Buttons.getBut12(ss), ss) %>
                </tr>
            
	<%= ss.html.endTable() %>        

    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>        
        
	<%= ss.html.begTable() %>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>
                                <p id="engEx">English Example</p>
                            </td>
                        <tr>
                        </tr>
                            <td>
                                <p id="langEx">Language Example</p>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <h2 class='focus_word'><%= ss.word %></h2>
                </td>
                <td>
                    <table border='0' align='center' width='100%'>

                        <%= BookMethods.prntStdy1(ss) %>
                        
                    </table>
                </td>
            </tr>

	<%= ss.html.endTable() %>

	<%= ss.html.begTable() %>

            <tr>
                <td class='p2' >
                    <%= ss.exp.getExp62() %>
                </td>
            </tr>

            <%= BookMethods.prntStdy2(ss) %>

	<%= ss.html.endTable() %>        

        </form>

	<%= ss.html.endRow() %>
    <!-- End Main Body Content -->

    <!-- IMPORTANT: should I place an rs.close try-statement here? -->

    <!-- Start Footer Content Nested Table, with Left and Right Cells -->
	<%= ss.html.footer(ss) %>
    <!-- End Footer Content, Left and Right Cells -->

    <!-- Banner -->
	<%= ss.html.banner() %>
        
        
    <!-- End Main Table : for when debugging is not needed -->
	<%= ss.html.endTable() %>        
    
    <!-- EndTableAndDeBug <%= ss.html.deBug(ss) %> Just shift this end arrow -->
        
    </body>
</html>
