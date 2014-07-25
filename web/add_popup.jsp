<%-- 
    Document   : add_popup.jsp
    Created on : April 30, 2009
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
        <title>Add Rows to a List</title>
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

        if(request.getParameter("addwordsets") != null) // from getBut10
        {
            String set = request.getParameter("dispvoclist"); // returns a choice of vocab options (or limited voc opts if user is not admin)
            String typ = request.getParameter("type"); // returns a choice of types

            BookMethods.handleSet(set, typ, ss); // sets ss.type, ss.typ and ss.prevType and
                // takes the vocList, determines if in Lists or is a Chapter, and sets ss.listGroup and ss.preSelect or ss.listName
                    // Also sets columns for ss.japanese
            //BookMethods.findAdminString(ss);
            DBHandler.addWordSets(ss); // Selects the eiken list, then chooses 50 of those IDs that are not already in map's my, know or maybe
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
								<p class='center'><%= ss.exp.getExp9() %></p><!-- returns word Add -->
							</td>
							<td class='center'>
								<%= Selections.getSelect2(ss) %><!-- returns a choice of types -->
							</td>
							<td>
								<p class='center'><%= ss.exp.getExp10() %></p><!-- returns word From -->
							</td>
							<td class='center'>
								<%= Selections.getSelect3(ss) %><!-- returns a choice of vocab options (or limited voc opts if user is not admin) -->
							</td>
							<td>
								<p class='center'><%= ss.exp.getExp10A() %></p><!-- returns word To My List -->
							</td>

								<%= Buttons.roundButton(Buttons.getBut10(ss), ss) %><!-- returns input value=ADD -->

						</tr>
					</table>
                </form>

    <%= ss.html.endRow() %>

			<tr>
				<td width='650' height='75' class='footer'><!-- message for returning to Options window -->
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
