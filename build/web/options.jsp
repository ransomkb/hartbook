<%-- 
    Document   : options.jsp
    Created on : Feb 16, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
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
        <title>Options</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("options.jsp", ss);
    // ss.postPage = "/Options"; may not need this as it should have been set already by the earlier StartButtons
    ss.setLang();
    ss.helpInt = 1;
	// ss.message = "";
	ss.teachChapList = false; // Investigate: why is this needed?

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
                    <!-- User ID, Left Cell -->
                    <td width='250' valign='middle' class='body_top_content_left'>
                        <p>Choose some options, <%= ss.userID %>!</p>
                        <p><%= ss.message %></p>
                    </td>
                    <!-- Message, Right Cell -->
                    <td align='right'>
                        <%= ss.exp.getExp83(ss) %>
                    </td>
                </tr>
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>        
        
	<%= ss.html.begTable() %>        
            <form name="studyOpts" id="studyOpts" action='/<%= ss.folder %>/Options' method='post' accept-charset='UTF-8'>
                <tr>
                    <td colspan='3'>
                        <h1 class='section_header'><%= ss.exp.getExp5() %></h1>
                    </td>
                </tr>
                <tr>
                    <td colspan='3'>
                        <p class='study_word'><%= ss.exp.getExp6() %></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%= Selections.getSelect9(ss) %>
                    </td>
                    <td>
                        <%= ss.exp.getExp35(ss) %> <%= ss.exp.getExp34B() %>
                    </td>
                    <td>
                        <%= ss.exp.getExp36() %> <%= ss.exp.getExp34A() %>
                    </td>
                </tr>
     <%
		if(ss.learn.equals("review") && ss.language.equals("japanese"))
		{
      %>
                <tr>
                    <td colspan='4'>
                        <p class='study_word'><%= ss.exp.getExp7() %></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%= Selections.getSelect2(ss) %>
                    </td>
                    <td>
                        <%= ss.exp.getExp33() %>
                    </td>
                    <td>
                        <%= ss.exp.getExp34() %>
                    </td>
                    <%= Buttons.roundButton(Buttons.getBut30(ss), ss) %>
                </tr>
     <%
        }
		else
		{
	 %>
                <tr>
                    <td colspan='3'>
                        <p class='study_word'><%= ss.exp.getExp7() %></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <%= Selections.getSelect2(ss) %>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        &nbsp;
                    </td>
                    
                    <%= Buttons.roundButton(Buttons.getBut30(ss), ss) %>
                </tr>
    <%
        }
	%>
            </form>
	<%= ss.html.endTable() %>        
        
	<%= ss.html.begTable() %>
        
            <tr>
                <td class='p2' >
                    <%= ss.exp.getExp62() %>
                </td>
            </tr>
        
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
