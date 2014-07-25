<%-- 
    Document   : index
    Created on : Nov 6, 2008, 1:11:25 PM
    Author     : Barber　Ransom
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
        <title>Log In</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>" onLoad='document.loginform.userid.focus();'> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	int x = 0;
        
	request.setCharacterEncoding("UTF-8");	
    ss.html.setSrcPage("login.jsp", ss);
    ss.setLang();
        
	x = ss.lang;
%>
	
    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Top Banner --> 
	<%= ss.html.banner() %>
    <!-- Top Banner --> 
	<%= ss.html.sampNav() %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
	<%= ss.html.begTable() %>
	
		<tr> <!-- Left Cell -->
          <td width='325' valign='middle' align='right' class='body_top_content_left'>
            <p class='section_header'>
                <%= ss.html.getSampPage(2) %>
            </p>
          </td>
          <!-- Right Cell -->
          <td width='325' valign='middle' align='right'>
		  	<p class='study_word'>
                <%= ss.logmes %>
            </p>
          </td>
        </tr>
        
		    
	<%= ss.html.endTable() %>
        
        <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>        
        
      <form name='loginform' method='post' accept-charset='UTF-8' action='/hartbook/FirstHTTPHandler'>
       
        <table width="300" height="20" border="0" cellpadding="0" align='center' >
          <tr>
            <td colspan="2">
              <p class='section_header'><%= ss.exp.getExp52() %></p>
            </td>
          </tr>
          <tr> 
            <td> 
              <p class='study_word'><%= ss.exp.getExp53() %></p>
              <p class='study_word'><%= ss.exp.getExp54() %></p>
            </td>
            <td> 
              <p align='center'><input type='text' name='userid' style='width:150px' maxlength='20' /></p>
              <p align='center'><input type='password' name='password' style='width:150px' maxlength='20' /></p>
            </td>
          </tr>
          <tr> 
            <td>&nbsp;</td>
            <td align='right'> 
              <table border='0'>
                <tr> 
                  <%= ss.html.roundButton(ss.html.getBut11()) %>
                </tr>
              </table>
            </td>
          </tr>
        </table>
		
      </form>
	
	<%= ss.html.endRow() %>
    <!-- End Main Body Content-->
    
    <% ss.logmes = ""; %>
	
    <!-- Start Footer Content Nested Table, with Left and Right Cells -->
	<%= ss.html.sampFooter() %>
    <!-- End Footer Content, Left and Right Cells -->
        
        
    <!-- End Main Table -->
	<%= ss.html.endTable() %>
    </body>
</html>
