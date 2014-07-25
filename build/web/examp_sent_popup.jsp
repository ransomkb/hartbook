<%-- 
    Document   : examp_sent_popup.jsp
    Created on : Nov 7, 2009
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
        <title>Example Sentences</title>
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
        <%
            try
            {
                ss.debugString = "Hello";

                String engExamp = ss.engEx; // sets variable for the english example sentence
                String langExamp = ss.langEx; // sets variable for the language example sentence

                // ss.message = "";

                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");

                if(request.getParameter("update") != null)
                {
                    engExamp = request.getParameter("engExamp"); // resets variable to that input in the text field
                    langExamp = request.getParameter("langExamp"); // resets variable to that input in the text field

                        ss.dbgApp("got here 1 "+engExamp); //debug
                    // CHECK STRINGS!!!
                    /*
                    ss.okExamp = ss.bkMeth.checkExamp(engExamp, ss); // checks that the inputs are acceptable; returns boolean

                    if(ss.okExamp)
                    {
                        ss.okExamp = ss.bkMeth.checkExamp(langExamp, ss); // checks that the inputs are acceptable; returns boolean
                    }
                    */

                    // REMEMBER TO TAKE THIS NEXT LINE OUT ONCE YOU CAN CHECK!!!!
                    ss.okExamp = true;

                    if(ss.okExamp)
                    {
                        ss.engEx = engExamp;
                        ss.langEx = langExamp;
                            ss.dbgApp("got here 2 "); //debug

                        DBHandler.updateExampSent(ss); // sent to method for updating db if example boolean is true
                    }

                    ss.okExamp = false;
                }

                DBHandler.fetchExampSent(ss); // sets ss example sentences to the ones in the db

            }
            catch(Exception e) {System.err.println("Exception: " +e.getMessage());}
        %>
    </head>
    
    <body bgcolor="#<%= ss.html.bgcolor %>"> <!-- e7eddf (green), f5f3ea (cream) -->
     <!-- Start Top Body Content Nested Table, with Left and Right Cells -->
	<%= ss.html.mainBodyTopRow() %>
	<!-- <%= ss.debugString %> -->
	<%= ss.html.endRow() %>
  <!-- End Body Top Content, Left and Right Cells -->

    <!-- Begin Main Table --> 
	<%= ss.html.begMainTable() %>
            
    <!-- Start Top Body Content -->
	<%= ss.html.mainBodyTopRow() %>

    <form method='post' name='examples' accept-charset="UTF-8">
        <table width='100%' border='1'>
            <tr>
                <td>
                    <p class='center'><%= ss.rowID %></p>
                </td>
                <%= Buttons.roundButton(Buttons.getBut5(ss), ss) %>
            </tr>
            <tr>
                <td>
                    <p class='center'><%= ss.engEx %></p>
                </td>
                <td>
                    <p class='center'><%= ss.html.textField(200, "engExamp", ss.engEx, ss) %></p>
                </td>
            </tr>
            <tr>
                <td>
                    <p class='center'><%= ss.langEx %></p>
                </td>
                <td>
                    <p class='center'><%= ss.html.textField(200, "langExamp", ss.langEx, ss) %></p>
                </td>
            </tr>
        </table>
    </form>

    <%= ss.html.endRow() %>

			<tr>
				<td width='650' height='75' class='footer'>
					<p><%= ss.message %></p>
					<p>Click
						<A HREF='/<%= ss.folder %>/editrows' onClick='return targetopener(this,true,true)' > here </A>
						to return to the My Lists window
					</p>
				</td>
			</tr>

	<%= ss.html.endTable() %>
    <!-- Adds Debug stuff
	<%= ss.html.deBug(ss) %> -->
    </body>
</html>
