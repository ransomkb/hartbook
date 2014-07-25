// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import usefuljavas.*;


public final class Test extends HttpServlet
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
        
		if(req.getParameter("nexttest") != null) // button found in Buttons.getBut20.
        {
            BookMethods.handleTest(req, ss);
            
			ss.round++;

			ss.rightGuessTest = ss.ansList.size() - ss.checkList.size();
			ss.totalCorrectTest = ss.totalCorrectTest + ss.rightGuessTest;
			ss.totalPossibleTest = ss.totalPossibleTest + ss.ansList.size();

			if(ss.numOfRounds == 0) {ss.round = 1;}
			else if(ss.totalPossibleTest != 0) {ss.percentCorrectTest = ((ss.totalCorrectTest * 100) / ss.totalPossibleTest);}

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
