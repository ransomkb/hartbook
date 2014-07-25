<%-- 
    Document   : edit_lists.jsp
    Created on : Mar 9, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
<%@ page import="usefuljavas.Selections" %>
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
        <title>Edit Lists</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>" onLoad='document.selection.eng.focus();'> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("edit_lists.jsp", ss);
    ss.setLang();
    ss.helpInt = 5;
	// ss.message = "";
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
	
                <tr><!-- User ID, Left Cell -->
                    <td width='325' valign='middle' align='right' class='body_top_content_left'>
                        <h2 class='section_header'>Create Lists</h2>
                    </td><!-- Message, Right Cell -->
                    <%= BookMethods.prntSearch(ss) %>
                </tr>
            
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>

    <!-- Start Body Table 1 -->
        <form action='/<%= ss.folder %>/EditLists' method='post' name='selection' id='selection' accept-charset='UTF-8' >
	<%= ss.html.begTable() %>

            <%= BookMethods.prntELSel(ss) %>

    <%= ss.html.endTable() %>
        </form>

    <!-- Start Body Table 2 -->
        <form action='/<%= ss.folder %>/EditLists' method='post' name='db_choice' id='db_choice' accept-charset='UTF-8' >
	<%= ss.html.begTable() %>

            <%= BookMethods.prntELChoice1(ss) %>

	<%= ss.html.endTable() %>

    <!-- Start Body Table 3 -->
    <%= ss.html.begBordSpTable() %>

            <%= BookMethods.prntELChoice2(ss) %>

	<%= ss.html.endTable() %>
        </form>

    <!-- Start Body Table 4 -->
        <form action='/<%= ss.folder %>/EditLists' method='post' name='radio_butts' id='radio_butts' accept-charset='UTF-8' >
	<%= ss.html.begBordSpTable() %>

            <%= BookMethods.prntELRad(ss) %>

	<%= ss.html.endTable() %>
        </form>

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
