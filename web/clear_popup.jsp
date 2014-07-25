<%-- 
    Document   : clear_popup.jsp
    Created on : May 3, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
<%@ page import="usefuljavas.Selections" %>
<%@ page import="usefuljavas.DBHandler" %>
<%@ page import="usefuljavas.Buttons" %>
<%@ page session="true" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
	SessionState ss = new SessionState();
	ss = (SessionState)session.getAttribute("Session");
        
	if (ss != null)
	{
		DBHandler.saveState(ss);
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
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Clear Rows in a List</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript">
        <!--
			function targetopener(mylink, closeme, closeonly)
			{
				if(! (window.focus && window.opener))return true;

				window.opener.focus();

				if(! closeonly)window.opener.location.href=mylink.href;
				if(closeme)window.close();

				return false;
			}
		//-->
        </script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<%
    try
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //ss.message = "";

        if(request.getParameter("clearlist") != null)
        {
            String clearList = request.getParameter("clearlist");
            String typ = request.getParameter("type");

            BookMethods.handleClear(clearList, typ, ss);
        }
    }
    catch(Exception e) {System.err.println("Exception: " +e.getMessage());}
%>
    
    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
            
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
    
                <form method='post' name='add' accept-charset="UTF-8">
					<table width='100%'>
						<tr>
							<td>
								<p class='center'><%= ss.exp.getExp11() %></p>
							</td>
							<td class='center'>
								<%= Selections.getSelect2(ss) %>
							</td>
							<td>
								<p class='center'><%= ss.exp.getExp10() %></p>
							</td>
							<td class='center'>
								<%= Selections.getSelect13(ss) %>
							</td>

								<%= Buttons.roundButton(Buttons.getBut11(ss), ss) %>

						</tr>
					</table>
                </form>

    <%= ss.html.endRow() %>

			<tr>
				<td width='650' height='75' class='footer'>
					<p><%= ss.message %></p>
					<p>Click
						<A HREF='/<%= ss.folder %>/mylists' onClick='return targetopener(this,true,true)' > here </A>
						to return to the Options window
					</p>
				</td>
			</tr>

	<%= ss.html.endTable() %>
        
    </body>
</html>
