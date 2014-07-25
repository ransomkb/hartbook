<%-- 
    Document   : allHistories.jsp
    Created on : Oct 20, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
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
        <title>All Histories</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("allHistories.jsp", ss); // sets the source-page variable to return to this page in case of errors.
    ss.setLang(); // sets the HTMLStuff and Explanations setLang variables to ss.lang (0 for Eng and 1 for J)
    ss.helpInt = 1; // sets the variable to retrieve the correct messages from HelpMess.java
	// ss.message = ""; // clears ss.message variable of previous messages

	// ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
            // through ss.dbgApp
%>

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Logged In Navigation -->
	<%= ss.html.mainNav(ss) %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
	<%= ss.html.begTable() %>
	
                <tr><!-- Pagee Title, Left Cell -->
                    <td width='325' valign='middle' align='right' class='body_top_content_left'>
                        <h2 class='section_header'>All Users' Histories</h2>
                    </td><!-- Message Display, Right Cell -->
                    <td align='right'>
                        <%= ss.message %>
                    </td>
                </tr>
            
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>

        <form action='/<%= ss.html.folder %>/oneHistory.jsp' method='post' name='oneStudent' id='oneStudent' accept-charset='UTF-8' >

	<%= ss.html.begTable() %>
        <tr>
            <td width='500' valign='middle' class='body_top_content_left'>
                <!-- Section Header -->
                <p class='section_header'>All Users' Histories</p>
            </td>
            <td width='150' valign='middle' align='right'>
                <table>
                    <tr>
                        <%= Buttons.roundButton(Buttons.getBut28(ss), ss) %> <!-- returns input value = See Students History; -->
                    </tr>
                </table>
            </td>
        </tr>
	<%= ss.html.endTable() %>        

        </form>

	<%= ss.html.endRow() %>

	<%= ss.html.begRow() %>

	<!-- Section Header -->
	  <%= ss.html.begBordSpTable() %>

        <tr>
            <th>UserID</th>
            <th>Date Started</th>
            <th>Minutes</th>
            <th>Correct<br />Review (%)</th>
            <th>Rev Total</th>
            <th>Correct<br />Test (%)</th>
            <th>Test Total</th>
        </tr>
    <%
        DBHandler.fetchAllIDs(ss); // sets elements of ArrayList ss.students with Selected userIDs from table userlist

        for(int i = 0; i < ss.students.size(); i++)
        {
            String u = (String)ss.students.get(i);
            String[] studHist = ss.calcHist.getHistoryList(u, ss); // returns String[] totArray,
                // which is filled with html elements for a table of the user's history;
    %>
        <%= studHist[1] %> <!-- Header -->
    <%
        }
    %>
        
	<%= ss.html.endTable() %>        
        
	<%= ss.html.endRow() %>
    <!-- End Main Body Content-->
	
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
