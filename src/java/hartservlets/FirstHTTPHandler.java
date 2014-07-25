// Altered on 2008-03-15
// A Class that retrieves and confirms the SessionState object, then handles language choice
package hartservlets;

import usefuljavas.*;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletException;
import java.io.IOException;


public final class FirstHTTPHandler extends HttpServlet
{
        // Don't know why the under stuff is saying this. Doesn't ssem to be true but may want to check.
        // No longer used because all goes through BookMethods
	// private BookMethods bkMeth = new BookMethods();
	
	// fill in override comment
	// Dispatch back to doGet().
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		doGet(req, res);
	}
	
	
	// Handles Requests and Prints the html content.
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		
	
		SessionState ss = null;
		HttpSession hsession = null;
		
		hsession = req.getSession();
		
		if (hsession.isNew()) 
		{ 
			hsession.setMaxInactiveInterval((int)SessionState.getTimeout()*60);
            res.sendRedirect("GetSessionState"); // redirects to first page of site
		}  // 20 minutes (20 sec * 60) [(int)ss.getTimeout()*]
                
		
		try
		{
			// support using session Cookie automatic support in Tomcat container
			ss = (SessionState) hsession.getAttribute("Session");  
			
			if(ss == null)
			{
                ss.message = "Something went wrong. Try again.";
                res.sendRedirect("GetSessionState"); // redirects to first page of site
                return;
			}
			
			ss.TimeNow = Calendar.getInstance();  // record current datetime
			
		}
		catch(Exception e) {System.err.println("Exception: " +e.getMessage());}
		
		if(handleRequest(req, res, ss))
		{
			ss.threeCols = true;
			ss.table = "japanese";
			ss.language = "japanese";
                        
			if(!ss.role.equals("sampler"))
			{
				if(ss.hsNew)
				{
					try
					{
						DBHandler.fetchHistoryLists(ss);
						DBHandler.updateHistoryClob(ss);
					} 
					catch(Exception e) {e.getMessage();}
				}
				
				ss.historyList = StringHandler.splitFirstString(ss.history);
				ss.histArray = StringHandler.splitSecondString(ss.scores);
				
				
				/*
				//try { ss.dbHand.fetchUserLists(ss); }
				//catch(Exception e) { e.getMessage(); }
				
				//BookMethods.fillLists(ss);
				//BookMethods.fillAdminNameList(ss);
				
				//if(!ss.role.equals("admin"))
					//BookMethods.fillChapNameList(ss);
				
				//ss.dbHand.fetchID(ss);
				
				// MAYBE WON'T USE THIS
				// ss.ownLists = new ArrayList<String>();
				// ss.ownLists = 
				*/
				
				
				// use these to change tables for update as of 2007/11/05
				/*
				ss.dbHand.createListsTable(ss);
				ss.dbHand.fetchEiken(ss);
				ss.dbHand.fetchUserIDs(ss);
				ss.dbHand.fetchUserCSVLists(ss);
				ss.dbHand.fetchChap(ss);
				*/
				
				// ss.dbHand.fetchTeach(ss); PROBABLY DO NOT NEED AS WON'T USE TEACHLIST
				
				
				// use this below to run normally
				
				DBHandler.fetchAllOwnLists(ss);
				
				if(!ss.role.equals("admin"))
				{
					DBHandler.fetchChapNames(ss);
				}
				/**/
				
			}
			
			// BookMethods.handleDisplay(ss); PROBABLY DO NOT NEED THIS HERE
			
			ss.hsNew = false;
			
			res.sendRedirect("start_buttons.jsp");
		}
		else 
		{
			res.sendRedirect(ss.goBack); // redirects to regAgain or tryAgain jsp
		}
		
	}
	
	
	// Handles the user's choice from the Log-In form. It sets the Appropriate Response.
	private boolean handleRequest(HttpServletRequest req, HttpServletResponse res, SessionState ss)
		throws ServletException, IOException
	{
		ss.logmes = "";
		
		boolean handle = false;
		
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");

        /*
		if(req.getParameter("language") != null)
		{
            String language = "";

            language = req.getParameter("language");
            ss.goBack = req.getParameter("srcpage");

            if(language.equals("English")) {ss.lang = 0;}
            else {ss.lang = 1;}

            handle = false;
        }
        else */

        if(req.getParameter("login") != null)
		{ // fix this return so it goes back correctly immediately
			ss.goBack = "/"+ss.folder+"/login.jsp";
			
			String userid = req.getParameter("userid");
			String password = req.getParameter("password");
			
			if((userid.length() == 0) || (userid == null))
            {
                ss.logmes = Messages.getMess53(ss);
                return false;
            }
			else if((password.length() == 0) || (password == null))
            {
                ss.logmes = Messages.getMess53(ss);
                return false;
            }
			
			userid = StringHandler.adjustString(userid);
			password = StringHandler.adjustString(password);
			
			ss.loggedIn = ss.checkUserIds(userid);

			handle = DBHandler.handleLogIn(userid, password, ss);
		}
		else if(req.getParameter("register") != null)
		{
			ss.goBack = "/"+ss.folder+"/register.jsp";
			
			String familyName = StringHandler.adjustString(req.getParameter("familyname"));
			
				if(familyName.equals("NG")) { ss.logmes = Messages.getMess5(ss); return false; }
				
				ss.familyName = familyName;
				
			String firstName = StringHandler.adjustString(req.getParameter("firstname"));
				
				if(firstName.equals("NG")) { ss.logmes = Messages.getMess5(ss); return false; }
				
				ss.firstName = firstName;
				
			String email = StringHandler.adjustStringA(req.getParameter("email"));
			
				if(email.equals("NG")) { ss.logmes = Messages.getMess5(ss); return false; }
				
				ss.contact = email;
				
			String userid = StringHandler.adjustStringA(req.getParameter("userid"));
			
				if(userid.equals("NG")) { ss.logmes = Messages.getMess5(ss); return false; }
				
			String password1 = StringHandler.adjustStringA(req.getParameter("password1"));
			
				if(password1.equals("NG")) { ss.logmes = Messages.getMess5(ss); return false; }
				
			String password2 = StringHandler.adjustStringA(req.getParameter("password2"));
			
				if(password2.equals("NG")) { ss.logmes = Messages.getMess5(ss); return false; }
				else if(!(password1.equals(password2)))
				{
					ss.logmes = Messages.getMess51(ss);
					return false;
				}
				
			handle = DBHandler.handleRegistration(res, familyName, firstName, email, userid, password1, ss);
		}		
		else 
		{
			ss.goBack = "/"+ss.folder+"/front_page.jsp";
            ss.message = "Something went wrong. Try again.";
			
			handle = false;
		}
		
		// Case 4: You want call the third-party servlet directly from HTML without using a form. 
		// Append the required parameters to the URL:<a href="/servlet/some.ThirdPartyServlet?param_name=somevalue">Clickable Text</a>

		
		return handle;
	}
}
