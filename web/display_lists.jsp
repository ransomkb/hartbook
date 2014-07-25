<%-- 
    Document   : display_lists.jsp
    Created on : Mar 4, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
<%@ page import="usefuljavas.Selections" %>
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
        <title>Display Lists</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("display_lists.jsp", ss);
    ss.setLang();
    ss.helpInt = 6;
    ss.rnum = 1;
	// ss.message = "";
    // DBHandler.saveState(ss);

	// ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
            // through ss.dbgApp
%>

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Top Nav -->
	<%= ss.html.mainNav(ss) %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
	<%= ss.html.begTable() %>
	
                <tr><!-- User ID, Left Cell -->
                    <td width='325' valign='middle' align='right' class='body_top_content_left'>
                        <h2 class='section_header'><%= ss.exp.getExp4A() %></h2>
                    </td><!-- Message, Right Cell -->
                    <%= BookMethods.prntSearch(ss) %>
                </tr>
            
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>

        <form action='/<%= ss.folder %>/DisplayLists' method='post' name='display' id='display' accept-charset='UTF-8' >
    <!-- Start Main Body Table 1 -->
	<%= ss.html.begTable() %>        
            <tr>
                <td colspan='5'>
                    <p class='study_word'><%= ss.exp.getExp4() %></p>
                </td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td class='center'>
                    <%= Selections.getSelect2(ss) %>
                </td>
                <td class='center'>
                    <%= Selections.getSelect1(ss) %>
                </td>
                <%= Buttons.roundButton(Buttons.getBut1(ss), ss) %>
            </tr>
	<%= ss.html.endTable() %>
            </form>

    <!-- Start Main Body Table 2 -->
	<%= ss.html.begTable() %>
            <tr>
                <td colspan='6'>&nbsp;</td>
            </tr>
            <tr>
                <td colspan='2'>
                    <p>List Size: <%= ss.wordList.size() %></p>
                </td>
                <td align='right' colspan='4'>
                    <%= ss.message %>
                </td>
            </tr>
            <tr>
                <td colspan='6'>&nbsp;</td>
            </tr>
	<%= ss.html.endTable() %>

        <form action='/<%= ss.folder %>/DisplayLists' method='post' name='move' id='move' accept-charset='UTF-8' >

    <!-- Start Main Body Table 3 -->
	<%= ss.html.begTable() %>
            <tr>
                <%= BookMethods.prntDLT3(ss) %>
            </tr>
	<%= ss.html.endTable() %>

    <!-- Start Main Body Table 4 -->
	<%= ss.html.begBordSpTable() %>
            <tr>
                <%= BookMethods.prntDLT4(ss) %>
            </tr>        
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
