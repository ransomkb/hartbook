// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import usefuljavas.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;


public final class Options extends HttpServlet
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
        
        // move the below message to a better place
		ss.message = Messages.getMess11(ss);

		if(req.getParameter("listSize") != null) // radio input set in Explanations.getExp34A / getExp34B
		{
			 String listSize = req.getParameter("listSize");

			 if(listSize.equals("whole"))
				 ss.wholeList = true;
			 else ss.wholeList = false;
		} // sets the ss.wholeList boolean
        

		if(req.getParameter("reading") != null) // radio input set in Explanations.getExp33
		{
			ss.reading = req.getParameter("reading");
		} // sets the reading variable for E+reading (Eyom), reading+E (yomE), etc.


		if(req.getParameter("meaning") != null) // radio input set in Explanations.getExp35
		{
			String mean = req.getParameter("meaning");
			if(mean.equals("E")) ss.meaning = true;
			else ss.meaning = false;

			//ss.dbgApp("got to req.getParameter(meaning) 1: ss.useList.size() = "+ss.useList.size()+", ss.meaning = "+ss.meaning+", ss.reading = "+ss.reading+", ss.type = "+ss.type); //deb

			//BookMethods.handleMeaning(mean, ss);
		} // sets the ss.meaning boolean to decide if Eyom or yomE, etc.


		if(req.getParameter("type") != null) // option set in Selections.getSelect2
		{
			String typ = req.getParameter("type");

			Selections.setTypList(typ, ss); // sets ss.type, ss.typ and ss.prevType

			//ss.dbgApp("got to req.getParameter(type) 2: ss.useList.size() = "+ss.useList.size()+", ss.meaning = "+ss.meaning+", ss.reading = "+ss.reading+", ss.type = "+ss.type); //deb

			//BookMethods.handleType(typ, ss); OLD STYLE
		} // sets ss.type, ss.typ and ss.prevType


		if(req.getParameter("displaylist") != null)	// option set in Selections.getSelect9
		{
			ss.displayList = req.getParameter("displaylist");

			//ss.dbgApp("got to req.getParameter(displaylist) 3A: ss.displayList = "+ss.displayList); //deb

			BookMethods.handleDisplay(ss);
			//ss.dbgApp("got to req.getParameter(displaylist) 3B: ss.useList.size() = "+ss.useList.size()+", ss.meaning = "+ss.meaning+", ss.reading = "+ss.reading+", ss.type = "+ss.type); //deb

            if(!ss.wdlist)
                res.sendRedirect(ss.srcPage);
            else
                res.sendRedirect(ss.postPage); // redirects to value in ss.postPage, which should have been set in BookMethods.handleLearn, which was called in StartButtons
            
            return; // IMPORTANT: ask Galt if these returns are necessary

		} // takes the chosen list and uses it for the learning selections
        else
        {
            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
        }
    }
}
