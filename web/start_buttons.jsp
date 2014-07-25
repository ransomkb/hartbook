<%-- 
    Document   : start_buttons
    Created on : Jan 27, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
<body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("start_buttons.jsp", ss);
    ss.setLang();
        
	// ss.message = "";
	ss.helpInt = 0;

	// ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
            // through ss.dbgApp
	// ss.teachChapList = false;
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
                        <h3 class='section_header'><%= ss.exp.getExp50() %></h3>
                    </td><!-- Message, Right Cell -->
                    <td align='right'>
                        
        <%	
            if(ss.role.equals("teacher") || ss.role.equals("admin"))
            {
        %>                    
                        <%= ss.exp.getExp61() %>
                        <p><%= ss.message %></p>
        <%
            }
            else
            {
        %>
                        <p><%= ss.message %></p>            
        <%
            }
        %>
                    
                    </td>
                </tr>
            
	<%= ss.html.endTable() %>        
        
        <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>
    
            <form action='/<%= ss.folder %>/StartButtons' method='post' name='start' id='start'>
	<%= ss.html.begTable() %>
                <tr>
                    <td>
                        <table align='center' border='0'>
                            <tr>
                                <td class='button-pad'>
                                    <%= Buttons.roundButtonReview(Buttons.getBut7(ss), ss) %>
                                </td>
                            </tr>
                            <tr>
                                <td class='button-pad'>
                                    <%= Buttons.roundButtonReview(Buttons.getBut8(ss), ss) %>
                                </td>
                            </tr>
                            <tr>
                                <td class='button-pad'>
                                    <%= Buttons.roundButtonReview(Buttons.getBut9(ss), ss) %>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
	<%= ss.html.endTable() %>        

            </form>
	<%= ss.html.begTable() %>
        
            <tr>
                <td class='p2' >
                    <%= ss.exp.getExp62() %>
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
        
        
    <!-- End Main Table : for when debugging is not needed -->
	<%= ss.html.endTable() %>        
    
    <!-- EndTableAndDeBug <%= ss.html.deBug(ss) %> Just shift this end arrow -->
    
    <!-- Resets ss.message -->
    <% ss.message = ""; %>
        
    </body>
</html>
