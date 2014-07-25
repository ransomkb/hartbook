// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import usefuljavas.*;


public final class CreateLangTable extends HttpServlet
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
        
        ss.dbTable = "japanese";
		ss.message = "Examples of Pictograph Languages: Chinese / Japanese (house 家 いえ)";

        if(req.getParameter("createTable") != null)
		{
			String typ = req.getParameter("radio");
			String newTable = req.getParameter("newTName");

			if(newTable != null && newTable.length() != 0)
				ss.tempTable = newTable;

			if(typ.equals("three")) ss.threeCols = true;
			else ss.threeCols = false;

			DBHandler.handleCLT(ss);

            // IMPORTANT: should this be ss.dbTable?
			ss.language = ss.table; 

			DBHandler.fetchAllOwnLists(ss);
			DBHandler.fetchChapNames(ss);
		}
		else if(req.getParameter("switchTable") != null)
		{
			ss.table = req.getParameter("tables");

			DBHandler.setThreeCols(ss);

            // IMPORTANT: should this be ss.dbTable?
			ss.language = ss.table;

			DBHandler.fetchAllOwnLists(ss);
			DBHandler.fetchChapNames(ss);
		}

        res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
        return; // ask Galt if these returns are necessary
    }
}
