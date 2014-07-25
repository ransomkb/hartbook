// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import usefuljavas.*;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletException;
import java.io.IOException;


public final class Language extends HttpServlet
{
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
                res.sendRedirect("GetSessionState"); // redirects to first page of site
                return;
			}

			ss.TimeNow = Calendar.getInstance();  // record current datetime

		}
		catch(Exception e) {System.err.println("Exception: " +e.getMessage());}

        if(req.getParameter("language") != null)
        {
            String language = "";

            language = req.getParameter("language");

            if(language.equals("English")) {ss.lang = 0;}
            else {ss.lang = 1;} // sets language for displaying html stuff

            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
        }
        else
        {
            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
        }
    }
}
