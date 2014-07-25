<%-- 
    Document   : test.jsp
    Created on : Mar 2, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
<%@ page import="usefuljavas.DBHandler" %>
<%@ page import="usefuljavas.Buttons" %>
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
        <title>Test</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("test.jsp", ss);
    ss.setLang();
    ss.helpInt = 4;
	ss.checkSave();
	// ss.message = "";
    // BookMethods.getStats(ss); seems to be handled by Test.java
    // DBHandler.saveState(ss);

    // if(ss.numOfRounds == 0) {ss.round = 1;}; seems to be handled by Test.java

	// ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
            // through ss.dbgApp
%>

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Logged In Navigation -->
	<%= ss.html.homeNav(ss) %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>

    <!-- IMPORTANT: find out what onsubmit does here -->
            <form name='review' action='/<%= ss.folder %>/Test' method = 'post' accept-charset='UTF-8' onSubmit='return OnSubmitForm();'>

	<%= ss.html.begTable() %>
	
                <tr><!-- Message, Left Cell -->
                    <td width='325' valign='middle' align='right' class='body_top_content_left'>
                        <p class='p'><%= ss.message %></p>
                    </td>
                    <!-- Button, Right Cell -->
                    <td>
                        <p><%= ss.exp.getExp25A() %></p>
                    </td>
                    <%= Buttons.roundButton(Buttons.getBut20(ss), ss) %>
                </tr>
            
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>        

	<%= BookMethods.checkRound(ss) %>

	<%= ss.html.begTable() %>        

		<tr>
            <td>
                <h2 class='focus_word'><%= ss.word %></h2>
            </td>
            <td>
                <table border='0' align='center' width='100%'>

	<%= BookMethods.getTempList(ss) %>

                </table>
            </td>
        </tr>
		<tr>
            <td colspan='2'>
                <table>
                    <tr>
                        <td>
                            <p class='study_word'><%= ss.exp.getExp25() %></p>
                        </td>
                    </tr>

	<%= BookMethods.getAnsList(ss) %>

                </table>
            </td>
        </tr>
        <tr>
            <td colspan='2'>
                <p class='study_word'><%= ss.exp.getExp26() %>

	<%= BookMethods.getResList(ss) %>

                </p>
            </td>
        </tr>
        <!-- for printing some debug stuff: <p><%= ss.type %> <%= ss.checkList %> <%= ss.resList %></p> -->
		
	<%= ss.html.endTable() %>        
        </form>
	<%= ss.html.endRow() %>
    <!-- End Main Body Content -->
	
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
