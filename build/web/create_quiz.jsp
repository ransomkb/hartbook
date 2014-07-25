<%-- 
    Document   : create_quiz.jsp
    Created on : Mar 6, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
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
        <title>Create Quiz</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %><%= BookMethods.getPopUpScriptForm() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("create_quiz.jsp", ss);
    ss.setLang();
    ss.helpInt = 8;
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
                        <p>Welcome <%= ss.userID %>!</p>
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
        
	<%= ss.html.begTable() %>        
            <form action='/<%= ss.folder %>/CreateQuiz' method='post' name='createquizzer' id='createquizzer'>
                <tr>
                    <td colspan='4'><h2 class='section_header'>Create a Quiz</h2></td>
                </tr>
                <tr>
                    <td colspan='3'>
                        <h4 class='study_word'>Choose the list you would like to make a quiz from: </h4>
                    </td>
                    <td>
                        <%= Selections.getSelect4(ss) %>
                    </td>
                </tr>
                <tr>
                    <td colspan='3'>
                        <h4 class='study_word'>and enter the number of questions: <input type = 'text' style='width:25px' name='quizNum' /></h4>
                    </td>
                    <%= Buttons.roundButton(Buttons.getBut25(ss), ss) %>
                </tr>
            </form>
                <tr>
                    <%= BookMethods.prntQzzr(ss) %>                
                </tr>
                <tr>
                    <%= BookMethods.prntQOpts(ss) %>                
                </tr>
                <tr>
                    <%= BookMethods.prntQAns(ss) %>                
                </tr>

	<%= ss.html.endTable() %>        
    
	<%= ss.html.endRow() %>
    <!-- End Main Body Content-->

        <% ss.quizList = false; %>
                <!-- IMPORTANT: make sure ss.rs is getting closed -->
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
