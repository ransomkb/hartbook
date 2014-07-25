<%-- 
    Document   : userinfo.jsp
    Created on : Nov 6, 2008, 1:11:25 PM
    Author     : Barber Ransom
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>User Info</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
        <%
            String userId = ss.userID, password = ss.psswd, contact = ss.contact;
        %>
    </head>
    
<body bgcolor='#<%= ss.html.bgcolor %>'> <!-- e7eddf (green), f5f3ea (cream) -->

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Logged In Navigation -->
	<%= ss.html.mainNav(ss) %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
	  <%= ss.html.begTable() %>
            <tr> 
              <!-- Left Cell --> 
              <td width='325' valign='middle' align='right' class='body_top_content_left'> 
                &nbsp; <!--  Welcome to Vocab Book --> 
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
                    <h2 class='section_header'>Your User ID is: <%= userId %></h2>
                </td>
            </tr>
            <tr>
                <td>
                    <h2 class='section_header'>Your Password is: <%= password %></h2>
                </td>
            </tr>
            <tr>
                <td>
                    <h2 class='section_header'>Your Phone Number or Email Address is: <%= contact %></h2>
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
