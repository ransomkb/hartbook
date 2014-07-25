<%-- 
    Document   : index
    Created on : Jan 14, 2009, 5:11 PM
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
<body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
        ss.html.setSrcPage("front_pageE.jsp", ss);
        ss.setLang();
%>

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Top Banner --> 
	<%= ss.html.banner() %>
    <!-- Top Banner --> 
	<%= ss.html.frontPageNav() %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
        <table align="center" border="0">
            <tbody>
                <tr>
                    <td><h3>Hello World!</h3></td>
                </tr>
            </tbody>
        </table>       
        
        <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>        
        
        <table align="center" border="0">
            <tbody>
                <tr>
                    <td><h3>Hello World!</h3></td>
                </tr>
            </tbody>
        </table>       
        
	<%= ss.html.endRow() %>
    <!-- End Main Body Content-->
	
    <!-- Start Footer Content Nested Table, with Left and Right Cells -->
	<%= ss.html.sampFooter() %>
    <!-- End Footer Content, Left and Right Cells -->
        
        
    <!-- End Main Table -->
	<%= ss.html.endTable() %>
    </body>
</html>
