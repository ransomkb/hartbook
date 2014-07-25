// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import usefuljavas.*;


public final class Review extends HttpServlet
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
        
        if(req.getParameter("moveset") != null) // set in Selections.getMoveSet
		{
			 // runs through Lists and returns the list.getName() if there, else returns null
			String moveset = Selections.adjustUseList(req.getParameter("moveset"), ss);

			if(moveset != null)
				BookMethods.handleMove(moveset, ss);
		} // moves ss.ansBean.rowID to the chosen list (knowlist or maybelist) and removes it from the other (if there)

			//ss.dbgApp("got to res.sendRedirect: ss.useList.size() = "+ss.useList.size()+", ss.meaning = "+ss.meaning+", ss.reading = "+ss.reading+", ss.type = "+ss.type); //deb

		if(ss.useList.size() == 0)
		{
			//ss.dbgApp("got to res.sendRedirect: ss.useList.size() = "+ss.useList.size()+", ss.meaning = "+ss.meaning+", ss.reading = "+ss.reading+", ss.type = "+ss.type); //deb

			//res.sendRedirect("/"+ss.folder+"/finishedlist.jsp");
            // IMPORTANT: fix redirect for when uselist is 0
            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
		}
		else if(req.getParameter("guess") != null) // button from Buttons.getBut13 to getBut18
		{
			ss.pid = ss.getAnsID();
			ss.guess = req.getParameter("guess");

			BookMethods.handleForm(ss);

            ss.round++;

            if(ss.numOfRounds==0) {ss.round = 0;}
            else {ss.percentCorrectRev = ((ss.rightGuessRev * 100) / ss.totalPossibleRev);}

			//ss.dbgApp("got to res.sendRedirect: ss.useList.size() = "+ss.useList.size()+", ss.meaning = "+ss.meaning+", ss.reading = "+ss.reading+", ss.type = "+ss.type); //deb

			//ss.allMapValuesToCSV(ss); // used to display all of the values in the HashMaps stored in ss.map

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
