<%-- 
    Document   : edit_tables.jsp
    Created on : Mar 10, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
<%@ page import="usefuljavas.Selections" %>
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
        <title>Edit Tables</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("edit_tables.jsp", ss);
    ss.setLang();
    ss.helpInt = 1;
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
                        <h2 class='section_header'>Editable Table - <%= ss.dbTable %></h2>
                    </td><!-- Message, Right Cell -->
                    <td align='right'>
                        <%= ss.message %>
                    </td>
                </tr>
            
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>

        <form action='/<%= ss.folder %>/EditTables' method='post' name='edittable' id='edittable' accept-charset='UTF-8' >

	<%= ss.html.begTable() %>

			<tr>
                <td colspan='5'>
                    <p class='study_word'>Choose the table which you want to see: </p>
                </td>
            </tr>
			<tr>
                <td colspan='3'></td>
                <td class='center'>
                    <%= Selections.getSelect15C(ss) %>
                </td>
                <%= Buttons.roundButton(Buttons.getBut23(ss), ss) %>
            </tr>

	<%= ss.html.endTable() %>        

	<%= ss.html.begBordSpTable() %>

            <%= BookMethods.prntETButts(ss) %>

	<%= ss.html.endTable() %>

        </form>

        <form action='/<%= ss.folder %>/EditTables' method='post' name='radio' id='radio' accept-charset='UTF-8' >

	<%= ss.html.begBordSpTable() %>

            <%= BookMethods.prntETRadio(ss) %>
        
	<%= ss.html.endTable() %>        

        </form>

	<%= ss.html.endRow() %>
    <!-- End Main Body Content -->

    <!-- IMPORTANT: check if need to close ss.rs -->

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
