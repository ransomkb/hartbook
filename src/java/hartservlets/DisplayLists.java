// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import usefuljavas.*;
import usefulbeans.*;


public final class DisplayLists extends HttpServlet
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

		//ss.message = "";

		ss.message = Messages.getMess0(ss);

		if(req.getParameter("display") != null)
		{
			//String set = req.getParameter("displayset");
			//String typ = req.getParameter("type");

			Selections.setTypList(req.getParameter("type"), ss); // sets ss.type, ss.typ and ss.prevType
			//BookMethods.handleType(req.getParameter("type"), ss);
			BookMethods.handleDropList(req.getParameter("displayset"), ss); // sets ss.listGroup, ss.list, ss.mainList and ss.prevList

			BookMethods.handleDisp(ss); // handles fetching ss.list rows and adding the wordbean to ss.wordList for display
		}
		else if(req.getParameter("move") != null) // radiobutton input name in BookMethods
		{
			String moveList = req.getParameter("movelist"); // selections input name
			//String moveID = req.getParameter("moverow");
			//String type = req.getParameter("wbType");
			//String set = moveList;

                 //ss.dbgApp("got here DispLists 0: movelist = "+moveList); //debug
			// BookMethods.handleType(req.getParameter("wbType"), ss);
			Selections.setTypList(req.getParameter("wbType"), ss);  // hidden input name in BookMethods
                 //ss.dbgApp("got here DispLists 1: typ = "+req.getParameter("wbType")); //debug
            // sets ss.type, ss.typ and ss.prevType
			BookMethods.handleDropList(moveList, ss); // sets ss.listGroup, ss.list, ss.mainList and ss.prevList


            for(int y = 0; y < ss.wordList.size(); y++)
            {
                String x = String.valueOf(y);

                if(req.getParameter(x+"check") != null)
                {
                    WordBean wBean = new WordBean();

                    wBean = ss.wordList.get(y);
                        //ss.dbgApp("got here DispLists 2: ss.type = "+ss.type+", ss.listGroup = "+ss.listGroup+", moveID = "+wBean.rowID); //debug

                    BookMethods.handleDispMove(wBean.rowID, ss);
                    // adds the checked row to the correct list (knowlist or maybelist) of the correct type,
                    // removes the row from the other list, and saves it all.
                }
            }

			BookMethods.handleDropList(moveList, ss); // sets ss.listGroup, ss.list, ss.mainList and ss.prevList
			// repeated to reset ss.list after move for ss.wordList

			BookMethods.handleDisp(ss); // handles fetching ss.list rows and adding the wordbean to ss.wordList for display
        }

        res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
        return; // ask Galt if these returns are necessary
    }
}
