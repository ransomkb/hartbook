<%-- 
    Document   : loggedout.jsp
    Created on : Nov 6, 2008, 1:11:25 PM
    Author     : BarberRansom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.DBHandler" %>
<%@ page import="usefuljavas.HTMLStuff" %>

<%@ page session="true" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    SessionState ss = new SessionState();
	ss = (SessionState) session.getAttribute("Session");
	if (ss != null)
	{
		ss.removeUserId();
		DBHandler.saveState(ss);
	}
	else
	{
%>
	<jsp:forward page = "login.jsp" />
<%
	}
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logged Out</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
    </head>
    
<body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Top Banner --> 
	<%= ss.html.banner() %>
    <!-- Logged Out Navigation -->
	<%= ss.html.sampNav() %>
        
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
	  <%= ss.html.begTable() %>
            <tr> 
              <!-- Left Cell --> 
              <td width="325" valign="middle" align="right" class="body_top_content_left"> 
                &nbsp; <!--  Welcome to Vocab Book --> 
              </td>
              <!-- Right Cell --> 
              <td width="325" valign="top" align="right"> &nbsp; <!--  <td>Message</td> --> 
              </td>
            </tr>
	  <%= ss.html.endTable() %>
	  
        <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>
        
        <!-- Some text -->
            <p class="section_header_center"><%= ss.exp.getExp81() %></p>

            <p class="study_word" align="center"><%= ss.exp.getExp82() %></p>
            
	<%= ss.html.endRow() %>
    <!-- End Main Body Content-->
	
    <!-- Start Footer Content Nested Table, with Left and Right Cells -->
	<%= ss.html.sampFooter() %>
    <!-- End Footer Content, Left and Right Cells -->
        
        
    <!-- End Main Table -->
	<%= ss.html.endTable() %>
        
    <%
        ss = null;
    %>
    
    </body>
</html>
