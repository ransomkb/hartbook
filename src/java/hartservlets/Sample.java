// Created 12-7-2009
// A Class that IMPORTANT variables in a SessionState object
package hartservlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import usefuljavas.*;


public final class Sample extends HttpServlet
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
        SessionState ss = checkSS.getSS(req, res);
        if (ss == null)
        {
            res.sendRedirect("GetSessionState");
            return;
        }
        
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");

		ss.message = Messages.getMess0(ss);
        
        if(req.getParameter("addwordsets") != null)
		{
			String typ = req.getParameter("type");
			String set = req.getParameter("displayset");
			// Check if using this
            //String lang = req.getParameter("hidLang");

			//ss.goBack = "/hartbook/front_pageE.jsp";
			ss.userID = ss.sessionId;
			ss.role = "sampler";

			ss.threeCols = true;
			ss.table = "japanese";
			ss.language = "japanese";

			ss.goBack = "/"+ss.folder+"/front_page.jsp";
            ss.srcPage = "start_buttons.jsp";
				//ss.dbgApp("got here getSessionState 1: set/vocList = "+set); //debug
				//ss.dbgApp("got here getSessionState 2: type = "+typ); //debug

		// FIX THIS SO CAN GET SAMPLER FROM EIKEN STUFF :: done?
			 boolean handle = BookMethods.handleSample(set, typ, ss); // kept for conveniend, but handle no longer used.

			ss.hsNew = false;

			res.sendRedirect(ss.srcPage);
            return; // ask Galt if these returns are necessary
        }
        else
        {
            res.sendRedirect(ss.goBack); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
        }
    }
}
