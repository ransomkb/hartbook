<%-- 
    Document   : print_quiz.jsp
    Created on : Mar 11, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
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
        <title>Printable Quiz</title>
        <script type="text/javascript">
			window.focus();
        </script>
    </head>
    
    <body>

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
    
    String quizRows = "";

    if(request.getParameter("printQuizzer") != null)
    {
        ss.printChoice = request.getParameter("printQuizzer");
    }

    quizRows = BookMethods.getPrintQz(ss);
%>

		<h3 align='center'>Quiz: ____________  &nbsp;&nbsp;Date: __________ &nbsp;&nbsp;Name: __________________ </h3>
        <p align='center'>Year: ___________ &nbsp;&nbsp;Class: ___________ &nbsp;&nbsp;Student No.: ______________ &nbsp;&nbsp;Score: __________</p>
		<HR width='100%' NOSHADE>

		<table>
			<%= quizRows %>
		</table>
    </body>
</html>
