<%-- 
    Document   : oneHistory.jsp
    Created on : May 5, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.DBHandler" %>
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
        <title>One Student's History</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("oneHistory.jsp", ss);
    ss.setLang();
    ss.helpInt = 7;
	//ss.message = "";

    String[] studHist = new String[4];

    if(request.getParameter("radio") != null)
    {
        ss.studID = request.getParameter("radio");
    }

    studHist = ss.calcHist.getHistoryList(ss.studID, ss);

    // DBHandler.saveState(ss);

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

        <tr>
            <td width='650' valign='middle' align='right' class='body_top_content_left'>
                <p class='section_header'><%= ss.studID %>'s Study History</p>
            </td>
        </tr>
        
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>

    <%= ss.html.begBordSpTable() %>

            <%
                if(ss.studID != null)
                {
            %>
                <%= studHist[2] %>
                <%= studHist[3] %>
                <%= studHist[0] %>
            <%
                }
                else
                {
            %>
                <tr>
                    <td colspan='6'>Sorry, the Student's ID was NULL</td>
                </tr>
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
