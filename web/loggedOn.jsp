<%-- 
    Document   : loggedOn
    Created on : Feb 12, 2009
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
        <title>Logged On</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
<body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
        ss.html.setSrcPage("start_buttons.jsp", ss);
        ss.setLang();
        
	//ss.message = "";
	ss.helpInt = 0;

    // Do we need this stuff below?
	ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
            // through ss.dbgApp
	ss.teachChapList = false;			
%>

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Logged In Navigation -->
	<%= ss.html.mainNav(ss) %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
	<%= ss.html.begTable() %>

        <tr> <!-- Left Cell -->
          <td width='325' valign='middle' align='right' class='body_top_content_left'>
           	<h2 class='section_header'>These are the users Logged On Now:</h2>
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
        
	<%= ss.html.begTable() %>        

			<tr>
				<td>
	  <%= ss.html.begHalfTable() %>
		<%
			String[] sess = ss.getSessHashKeys();

		%>
	  				<tr>
						<th class='p'>Sessions in Hashtable<br />Total: <%= sess.length %>
						</th>
					</tr>
		<%
			for(int x = 0; x < sess.length; x++)
			{
		%>
	  <%= ss.html.begHalfRow() %>
					<h2 class='study_word_sentence'><%= sess[x] %></h2>
	  <%= ss.html.endRow() %>
		<%
			}
		%>
	  <%= ss.html.endTable() %>
				</td>
				<td>
	  <%= ss.html.begHalfTable() %>
		<%
			String[] users = ss.getUserIDHashKeys();

		%>
	  				<tr>
						<th class='p'>Users in Hashtable<br />Total: <%= users.length %>
						</th>
					</tr>
		<%
			for(int x = 0; x < users.length; x++)
			{
		%>
	  <%= ss.html.begHalfRow() %>
					<h2 class='study_word_sentence'><%= users[x] %></h2>
	  <%= ss.html.endRow() %>
		<%
			}
		%>
	  <%= ss.html.endTable() %>
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
        
    </body>
</html>
