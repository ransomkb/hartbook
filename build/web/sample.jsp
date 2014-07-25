<%-- 
    Document   : index
    Created on : Nov 6, 2008, 1:11:25 PM
    Author     : BarberRansom
    Purpose    : Gets input from users and sends it to the Sample servlet
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page errorPage="hartBookErrorPage.jsp" %>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.Selections" %>
<%@ page session="true" %>

<jsp:useBean id="sel" scope="page" class="usefuljavas.Selections" />

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
        <title>Sample</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
    </head>
    
<body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	int x = 0;
        
	request.setCharacterEncoding("UTF-8");
    ss.html.setSrcPage("sample.jsp", ss);
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
	
		<!-- Left Cell --> 
        <tr> 
            <td class="section_header">
                <%= ss.html.getSampPage(1) %><!-- returns the title page -->
            </td>
        </tr>
			
	<%= ss.html.endTable() %>
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>        
        
	<!-- Section Header -->
      <form name="sample" method="post" accept-charset="UTF-8" action="/<%= ss.folder %>/Sample">
       
        <table width="650" border="0" cellpadding="0" align="center" >
            <tr>
                <td>
                        <p class="center"><%= ss.exp.getExp47() %></p><!-- returns the word Add -->
                </td>
                <td class="center">
                        <%= sel.getSelect8(x) %><!-- returns a choice of Verbs, Nouns, etc. -->
                </td>
                <td>
                        <p class="center"><%= ss.exp.getExp48() %></p><!-- returns the word From -->
                </td>
                <td class="center">
                        <%= sel.getSelect11(x) %><!-- returns a choice of Step Test / Eiken levels -->
                </td>
                <td>
                        <p class="center"><%= ss.exp.getExp49() %></p><!-- returns the words To My List -->
                </td>
                <td>
                        <%= ss.html.roundButton(ss.html.getBut10()) %><!-- returns input value = Try It Out -->
                </td>
            </tr>
        </table>
      </form>
      
	<%= ss.html.endRow() %>
    <!-- End Main Body Content-->
	
    <!-- Start Footer Content Nested Table, with Left and Right Cells -->
	<%= ss.html.sampFooter() %>
    <!-- End Footer Content, Left and Right Cells -->
        
        
    <!-- End Main Table -->
	<%= ss.html.endTable() %>
    </body>
</html>
