// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import usefuljavas.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;


public final class Search extends HttpServlet
{
	private CheckSessionState checkSS = new CheckSessionState();

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

		SessionState ss = checkSS.getSS(req, res);
		if (ss == null)
        {
            res.sendRedirect("GetSessionState");
            return;
        }

		ss.message = Messages.getMess0(ss);
        
        if(req.getParameter("search") != null)
		{
			ss.searchVar = StringHandler.adjustString(req.getParameter("searchvar"));

			BookMethods.handleSearch(ss);
			DBHandler.addToList(ss);
			ss.search = true;

            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcPage, which should be the same page
            return; // ask Galt if these returns are necessary
		}
        else
        {
            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcPage, which should be the same page
            return; // ask Galt if these returns are necessary
        }

        /*
        if(req.getParameter("searchvar") != null)
        { // maybe some parameters are arrays, and this sends an array to be adjusted one by one
            // Still, this should probably be fixed for searching
            // Try to use the method from Display Lists

            // ss.typ = req.getParameter("typ");
            String[] reqVars = {"searchvar"};

            if(BookMethods.adjustReq(req, reqVars, ss))
            {
                ss.searchVar = ss.rVars.get(0); //ss.bkMeth.adjustString(eng);
            }
            else return;

            //ss.searchVar = ss.bkMeth.adjustString(req.getParameter("searchvar"));

            if(ss.searchVar != null)
            {
                BookMethods.handleSearch(ss);

                ss.selected = false;
                ss.search = true;
            }
            else { ss.message = Messages.getMess49(ss); }

            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcPage, which should be the same page
            return; // ask Galt if these returns are necessary
        }
        if(req.getParameter("language") != null)
        {
            String language = "";
            
            language = req.getParameter("language");
            
            if(language.equals("English")) {ss.lang = 0;}
            else {ss.lang = 1;} // sets language for displaying html stuff
            
            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcPage, which should be the same page
            return; // ask Galt if these returns are necessary
        }*/
    }
}
