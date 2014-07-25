<%-- 
    Document   : Admin
    Created on : Nov 6, 2008, 1:11:25 PM
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page session="true" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    SessionState ss = new SessionState();
	ss = (SessionState) session.getAttribute("Session");
	
	if (ss != null)
	{
		ss.help = "help_popup.jsp";
        //ss.message = "";
	}
	else
	{
%>
	<jsp:forward page = 'GetSessionState' />
<%
	}
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administration</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>        
    </head>
    
<body bgcolor='#<%= ss.html.bgcolor %>'> <!-- e7eddf (green), f5f3ea (cream) -->

		
    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Logged In Navigation -->
	<%= ss.html.mainNav(ss) %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
	  <%= ss.html.begTable() %>
            <tr> <!-- Left Cell --> 
              <td width='325' valign='middle' align='right' class='body_top_content_left'> 
                <h2 class='section_header'>Admin</h2>
              </td>
              <!-- Right Cell --> 
              <td width='325' valign='top' align='right'> &nbsp; <!--  <td>Message</td> --> 
              </td>
            </tr>
	  <%= ss.html.endTable() %>
	  
        <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>     
   
        <!-- Section Header -->
	  <%= ss.html.begTable() %>

            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/edit_tables.jsp' target='_self'>Edit Tables</a> to
                                    make changes to the information in all tables</h2>
                    </td>
            </tr>
            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/edit_tlists.jsp' target='_self'>Edit Teacher Lists</a> to
                                    make changes to all unapproved rows</h2>
                    </td>
            </tr>
            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/find_same.jsp' target='_self'>Find Same</a> to
                                    make changes to all repeating or similar rows</h2>
                    </td>
            </tr>
            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/userinfo.jsp' target='_self'>User's Info</a> to 
                                    see and edit the user's personal information</h2>
                    </td>
            </tr>
            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/notes.jsp' target='_self'>Notes</a> to 
                                    add notes to the notepad table</h2>
                    </td>
            </tr>
            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/loggedOn.jsp' target='_self'>Logged On</a> to 
                                    see who is logged on at the moment</h2>
                    </td>
            </tr>
            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/viewRows.jsp' target='_self'>View Rows</a> to 
                                    see the selected rows in a comma and semi-colon separated list</h2>
                    </td>
            </tr>
            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/dbtable.jsp' target='_self'>
                                    DBTable Manipulation</a> to	see the selected rows in a comma and semi-colon separated list</h2>
                    </td>
            </tr>
            <tr>
                    <td>
                            <h2 class='section_header'>Click on <a href='/<%= ss.folder %>/dbdebug.jsp' target='_self'>DB Debug</a> to
                                    see your debugging information</h2>
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
        
    <!-- End Main Table -->
	<%= ss.html.endTable() %>
    </body>
</html>
