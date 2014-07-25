// Altered on 2009-02-16
// A Class that retrieves Start Button selection and sets the learn variables in a SessionState object
package hartservlets;

import usefuljavas.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;


public final class StartButtons extends HttpServlet
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
        
		// handles the request parameter from Start Buttons to set Study, Review or Test
		if(req.getParameter("learn") != null)
		{
			String learn = req.getParameter("learn");

			BookMethods.handleLearn(learn, ss);

            res.sendRedirect("options.jsp"); // redirects to jsp that presents options for studying
            return; // ask Galt if these returns are necessary
		}
        else
        {
            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcPage, which should be the same page
            return; // ask Galt if these returns are necessary
        }

		//ss.dbgApp("got to LearningOptions; ss.useList.size() = "+ss.useList.size()+", ss.meaning = "+ss.meaning+", ss.reading = "+ss.reading+", ss.type = "+ss.type); //deb
		//ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map
    }
}
