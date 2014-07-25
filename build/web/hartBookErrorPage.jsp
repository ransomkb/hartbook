<%-- 
    Document   : index
    Created on : Nov 6, 2008, 1:11:25 PM
    Author     : BarberRansom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.HTMLStuff" %>
<%@ page session="true" %>

<jsp:useBean id="ss" scope="session" class="usefuljavas.SessionState" />
<jsp:useBean id="htmlstf" scope="page" class="usefuljavas.HTMLStuff" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
	ss = (SessionState) session.getAttribute("Session");  
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error!</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= htmlstf.getPopUpScript() %></script>
    </head>
    
<body bgcolor="#<%= htmlstf.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	String language = "";
	
	request.setCharacterEncoding("UTF-8");
	
	if(request.getParameter("language") != null) 
	{
		language = request.getParameter("language");
		
		if(language.equals("English"))
		{ htmlstf.lang = 0; ss.lang = 0; }
		else { htmlstf.lang = 1; ss.lang = 1; }
	}
%>

    <!-- Begin Main Table --> 
	<%= htmlstf.begMainTable() %>
    <!-- Top Banner --> 
	<%= htmlstf.banner() %>
    <!-- Top Banner --> 
	<%= htmlstf.frontPageNav() %>
    <!-- Start Top Body Content -->
	<%= htmlstf.mainBodyTopRow() %>
        
	<%= htmlstf.begTable() %>
        <tr> <!-- Left Cell --> 
          <td width="325" valign="middle" align="right" class="body_top_content_left"> 
			</td>
          <!-- Right Cell --> 
          <td width="325" valign="middle" align="right"> 
          </td>
        </tr>
        <%= htmlstf.endTable() %>
   
        <%= htmlstf.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= htmlstf.mainBodyRow() %>        
        
	<!-- Section Header -->
	<%= htmlstf.begTable() %>
		
          <tr> 
            <td>
              <p class="bigRed">
                    <%
                            if(exception instanceof SQLException)
                            {
                    %>
                            A SQLException
                    <%
                            }
                            else if(exception instanceof ServletException)
                            {
                    %>
                            A ServletException
                    <%
                            }
                            else if(exception instanceof ClassNotFoundException)
                            {
                    %>
                            A ClassNotFoundException
                    <%
                            }
                            else 
                            {
                    %>
                            An Exception
                    <%
                            }
                    %>
                            occurred while interacting with the HartBook database.
              </p>
            </td>
          </tr>
          <tr> 
            <td> 
              <p class="bigRed">
                    The error message was: 
                    <%= exception.getMessage() %>
              </p>
            </td>
          </tr>
	
	<%= htmlstf.endTable() %>
        
	<%= htmlstf.endRow() %>
    <!-- End Main Body Content-->
	
    <!-- Start Footer Content Nested Table, with Left and Right Cells -->
	<%= htmlstf.sampFooter() %>
    <!-- End Footer Content, Left and Right Cells -->
        
        
    <!-- End Main Table -->
	<%= htmlstf.endTable() %>
    </body>
</html>
