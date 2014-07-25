<%-- 
    Document   : review
    Created on : Feb 26, 2009
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
        <title>Review</title>
        <link href="hill_stylesheet.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript"><%= ss.html.getPopUpScript() %></script>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->

<% 
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

    ss.html.setSrcPage("review.jsp", ss);
    ss.setLang();
    ss.helpInt = 3;
	ss.checkSave();
	// ss.message = "";
    // DBHandler.saveState(ss);

	// ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
            // through ss.dbgApp

        // IMPORTANT: maybe all these variables should be ss or local
		int f = 1;
		int listCount = 0;

		// Iterator tempHideList2Iter = ss.tempHideList2.iterator(); used for debugging
		// Iterator useListIter = ss.useList.iterator();

        // IMPORTANT: move all this stuff below into BookMethods
		String answer = ss.getAns();
		String a[] = new String[3];

		a[0] = ss.getEngAns();
		a[1] = ss.getKanAns();
		a[2] = ss.getYomAns();

		if(ss.getMeaning().equals("Ekan") || ss.getMeaning().equals("Eyom")) {answer = a[0];}
		else if(ss.getMeaning().equals("kanE") || ss.getMeaning().equals("kanyom")) {answer = a[1];}
		else if(ss.getMeaning().equals("yomE") || ss.getMeaning().equals("yomkan")) {answer = a[2];}

        if(ss.numOfRounds == 0) {ss.round = 1;}
%>

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
    <!-- Logged In Navigation -->
	<%= ss.html.homeNav(ss) %>
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>
        
	<%= ss.html.begTable() %>
	
                <tr><!-- User ID, Left Cell -->
                    <td>

                        <%= BookMethods.prntRvw1A(ss) %>
                        
                    </td>
                </tr>
            
	<%= ss.html.endTable() %>        
        
    <%= ss.html.endRow() %>
    <!-- End Body Top Content --> 
  
    <!-- Start Main Body Content -->
	<%= ss.html.mainBodyRow() %>        

	<%= ss.html.begTable() %>

                <tr><!-- User ID, Left Cell -->
                    <td>

                        <%= BookMethods.prntRvw1B(ss) %>

                    </td>
                </tr>

	<%= ss.html.endTable() %>

    <!-- IMPORTANT: find out what onsubmit does here -->
            <form name='review' id='review' action='/<%= ss.folder %>/Review' method='post' accept-charset='UTF-8' onSubmit='return OnSubmitForm();'>
	<%= ss.html.begTable() %>
    
                <tr>
                    <td></td>
                    <td colspan='2' rowspan='2'>
                        <h3 align='center'><%= Buttons.roundButtonReview(Buttons.getBut13(ss), ss) %></h3>
                    </td>
                    <td colspan='2' rowspan='2'>
                        <h3 align='center'><%= Buttons.roundButtonReview(Buttons.getBut14(ss), ss) %></h3>
                    </td>
                    <td></td>
                </tr>
                <tr>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td colspan='2' rowspan='4'>
                        <h2 <%= Buttons.adjustAnsFontClass(answer, ss) %>><%= answer %></h2>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                </tr>
                <tr>
                    <td colspan='2' rowspan='2'>
                        <h3 align='center'><%= Buttons.roundButtonReview(Buttons.getBut15(ss), ss) %></h3>
                    </td>
                    <td colspan='2' rowspan='2'>
                        <h3 align='center'><%= Buttons.roundButtonReview(Buttons.getBut16(ss), ss) %></h3>
                    </td>
                </tr>
                <tr>
                </tr>
                <tr>
                    <td></td>
                    <td colspan='2' rowspan='2'>
                        <h3 align='center'><%= Buttons.roundButtonReview(Buttons.getBut17(ss), ss) %></h3>
                    </td>
                    <td colspan='2' rowspan='2'>
                        <h3 align='center'><%= Buttons.roundButtonReview(Buttons.getBut18(ss), ss) %></h3>
                    </td>
                    <td></td>
                </tr>
                <tr>
                </tr>

	<%= ss.html.endTable() %>
            </form>

		<!-- End Main Body Content 1--><%= ss.html.endRow() %>

		<!-- Start Main Body Content Rows 2--><%= ss.html.mainBodyRow() %>

            <form name='movestuff' id='movestuff' action='/<%= ss.folder %>/Review' method='post' accept-charset='UTF-8'>
		<table cellpadding='0' cellspacing='0' border='0' width='650'>
            <tr>

            <%= BookMethods.prntRvw2(ss) %>

		<!-- Explanation, Left Cell -->
                <td align='right' class='body_top_content_right'>
                    <table cellpadding='0' cellspacing='0' border='0'>
                        <tr>
                            <td>
                                <p><%= ss.message %></p>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        </form>

		<!-- End Main Body Content 2--><%= ss.html.endRow() %>

		<!-- Start Main Body Content Rows 3--><%= ss.html.mainBodyRow() %>

		<p class='study_word_sentence'><%= ss.exp.getExp22(ss) %></p>

		<p class='p'><%= ss.exp.getExp43() %></p>

        <!-- Uncomment section in deBugReview to see info -->
        <%= BookMethods.deBugReview(ss) %>
		
        <!-- IMPORTANT: put all this rs.close and debug stuff in the BookMethods and DBHandler classes: done -->

	<%= ss.html.endRow() %>
    <!-- End Main Body Content -->
	
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
