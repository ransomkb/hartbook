// Altered on 2008-03-15
// A Class that retrieves and confirms the SessionState object
package hartservlets;

import usefuljavas.*;
import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletException;
import java.io.IOException;


public final class GetSessionState extends HttpServlet
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
        }  // 20 minutes (20 sec * 60) [(int)ss.getTimeout()*]

        try
        {
            // support using session Cookie automatic support in Tomcat container
            ss = (SessionState) hsession.getAttribute("Session");  

            if(ss == null)
            {
                // not likely, but just in case (prevents dupes in sessions hash)
                ss = SessionState.getInstance(hsession.getId());  
                if (ss == null)
                { 
                    ss = new SessionState(hsession.getId());
                    ss.sessionId = hsession.getId();
                    ss.TimeStarted = Calendar.getInstance();  // record StartUp datetime
                    ss.checkStaticVars();
                    ss.hsNew = true;
                }
                hsession.setAttribute("Session", ss);   // store it
            }

            ss.lang = 0;

            ss.TimeNow = Calendar.getInstance();  // record current datetime
            
            res.sendRedirect("/"+ss.folder+"/front_page.jsp"); // redirects to first page of site
            return;
        }
        catch(Exception e) {System.err.println("Exception: " +e.getMessage());}		
    }	
}
