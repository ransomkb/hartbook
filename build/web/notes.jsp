<%-- 
    Document   : notes.jsp
    Created on : Oct 20, 2009
    Author     : Barber Ransom
--%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="usefuljavas.SessionState" %>
<%@ page import="usefuljavas.BookMethods" %>
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
        <title>Notepad</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("notes.jsp", ss);
    ss.setLang();
    ss.helpInt = 1;
	// ss.message = "";

    if(request.getParameter("note") != null)
    {
        String note = request.getParameter("note");
        DBHandler.saveNote(note, ss);
    }
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
                        <h2 class='section_header'>Notes</h2>
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

        <form method='post' name='notes' id='notes' accept-charset='UTF-8' >

	<%= ss.html.begTable() %>

        <tr>
            <td><p class='section_header'>Type in some notes and hit Save</p></td>
        </tr>
        <tr>
            <td>
                <textarea name='note' rows='10' cols='80'></textarea>
            </td>
        </tr>
        <tr>
            <td align='right'>
                <table cellpadding='0' cellspacing='0' border='0'>
                    <tr>
                        <td class='button-lg-left'>&nbsp;</td>
                        <td class='button-lg-mid' >
                            <input type='submit' name='save' value='Save' class='button-lg-mid' >
                        </td>
                        <td class='button-lg-right'>&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>

	<%= ss.html.endTable() %>        

        </form>

	<%= ss.html.begTable() %>
    
    <%
        String[][] nn = ss.calcHist.getNotes(ss);

        BookMethods.handleNotes(nn[0][1], ss);
    %>
        <tr>
            <td>
                <p><%= nn[0][1] %></p>
            </td>
        </tr>
        <tr>
            <td>
                <p><%= ss.strCSV %></p>
            </td>
        </tr>
        <tr>
            <td>
                <p>To update notepad table, type update;(some noteID);(some new text)</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>To fetch type, eng, kan and yom from vocab1 table, type fetchMisplaced;(some number to start from)</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>To add to vocab1 table, type insert;(some CSV of type,eng,kan,yom;etc.)</p>
            </td>
        </tr>
    <%
        for(int x = 0; x < nn.length; x++)
        {
            String ID = nn[x][0], note = nn[x][1];
    %>
        <tr>
            <td>
            <table width='650'>
                <tr>
                    <td>
                        <p><%= ID %></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p><%= note %></p>
                    </td>
                </tr>
            </table>
        </td>
        </tr>
    <% } %>

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
