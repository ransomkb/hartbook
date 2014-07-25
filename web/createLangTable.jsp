<%-- 
    Document   : createLangTable.jsp
    Created on : Dec 3, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
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
        <title>Create Language Tables</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("createLangTable.jsp", ss);
    ss.setLang();
    ss.helpInt = 1; // IMPORTANT: fix this if others will use it
	// ss.message = "";

	// ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
            // through ss.dbgApp
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
                        <h2 class='section_header'>Create Language Tables</h2>
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

    <form action='/<%= ss.folder %>/CreateLangTable' method='post' name='switch' id='switch' accept-charset='UTF-8' >

	<!-- MB Table 1 -->
	<%= ss.html.begTable() %>
        <tr>
			<td>
				<p class='study_word'>Current Language = <%= ss.table %></p>
			</td>
		</tr>
	<%= ss.html.endTable() %>        

	<%= ss.html.begTable() %>
        <tr>
			<td>
				<p class='study_word_right'>Languages in the Database:</p>
			</td>
			<td class='center'>
				<%= Selections.getSelect15B(ss) %>
			</td>
			<td>
				<%= Buttons.roundButton(Buttons.getBut34(ss), ss) %>
			</td>
		</tr>
	<%= ss.html.endTable() %>        

    </form>

    <form action='/<%= ss.folder %>/CreateLangTable' method='post' name='add' id='add' accept-charset='UTF-8' >

	<!-- MB Table 2 -->
	<%= ss.html.begTable() %>

		<tr>
			<td>
				<p class='study_word'>Choose a<br/>Language Type:</p>
			</td>
			<td>
				<p class='study_word_sentence'><input type=radio name='radio' value='three'>
					3 Meanings: <ul class='study_word_sentence'><li>English Meaning</li><li>Pictograph-Meaning</li><li>Reading</li></ul>
				</p>
			</td>
			<td>
				<p class='study_word_sentence'><input type=radio name='radio' value='two' checked>
					2 Meanings: <ul class='study_word_sentence'><li>English Meaning</li><li>Language Meaning</li></ul>
				</p>
			</td>
		</tr>

	<%= ss.html.endTable() %>

	<!-- MB Table 3 -->
	<%= ss.html.begTable() %>

		<tr>
			<td>
				<p class='study_word_right'>Enter a New Language Name:</p>
			</td>
			<td align='right'>
				<p>
					<input type = 'text' style='width:100px' maxlength='20' name='newTName' />
				</p>
			</td>
			<td>
				<%= Buttons.roundButton(Buttons.getBut38(ss), ss) %>
			</td>
		</tr>

	<%= ss.html.endTable() %>

	<!-- MB Table 4 -->
	<%= ss.html.begTable() %>

		<tr>
			<td width='650'>
				<p class='p'><%= ss.message %></p>
				<p class='study_word_sentence'>
					Click<A HREF='/<%= ss.folder %>/edit_lists.jsp'> here </A>to go to the Create Lists window
				</p>
			</td>
		</tr>

	<%= ss.html.endTable() %>

	</form>

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
