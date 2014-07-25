<%-- 
    Document   : edit_list_names.jsp
    Created on : Mar 9, 2009
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
        <title>Edit List Names</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %><%= BookMethods.getPopUpScriptForm() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("edit_list_names.jsp", ss);
    ss.setLang();
    ss.helpInt = 1;
	// ss.message = "";
    // DBHandler.saveState(ss); IMPORTANT: maybe need this for this jsp

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
                        <p class='p'><%= ss.message %></p>
                    </td><!-- Message, Right Cell -->
                    <td align='right'>
                        Welcome <%= ss.userID %>!
                    </td>
                </tr>
            
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>

        <form action='/<%= ss.folder %>/EditListNames' method='post' name='create_new_list' id='create_new_list' accept-charset='UTF-8' >

        <table cellpadding='0' cellspacing='0' border='0' width='300'>
            <tr>
                    <td>
                        <p class='section_header'>Name: <input type = 'text' name='newchap' style='width: 100px' maxlength='20' /></p>
                    </td>
                    <%= Buttons.roundButton(Buttons.getBut29(ss), ss) %>
                </tr>
                <tr>
                    <td colspan='2'>
                        <p class='study_word'>Click <a href='/<%= ss.folder %>/edit_lists.jsp'>here</a> to return to Create Lists.</p>
                    </td>
                </tr>
                <tr>
                    <th>List Name</th>
                    <%= Buttons.roundButton(Buttons.getBut6(ss), ss) %>
                </tr>

                <%= BookMethods.prntChapNmLst(ss) %>

        </table>

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
