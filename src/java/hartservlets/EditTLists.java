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


public final class EditTLists extends HttpServlet
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

		if(ss.role.equals("admin"))
		{
			//ss.message = "";

            ss.message = Messages.getMess0(ss);

			if(req.getParameter("update") != null) // button in Buttons.getBut5
			{
				for(int y = 0; y < ss.wordList.size(); y++)
				{
					String x = String.valueOf(y);

					if(req.getParameter(x+"check") != null)
					{
						String eng = req.getParameter(x+"eng");
						if(eng == null || eng.length() == 0) continue;

						String kan = req.getParameter(x+"kan");
						if(kan == null || kan.length() == 0) continue;

						String yom = req.getParameter(x+"yom");
						if(yom == null || yom.length() == 0) continue;

						WordBean wb = new WordBean();

						wb.rowID = req.getParameter(x+"ID");
						wb.id = Integer.parseInt(wb.rowID);

						String typ = req.getParameter(x+"type");

						//ss.bkMeth.handleType(typ, ss);
						Selections.setTypList(typ, ss);
                        // sets ss.type, ss.typ and ss.prevType

						wb.typ = ss.type;
						wb.eng = StringHandler.adjustString(eng);
						wb.kan = StringHandler.adjustString(kan);
						wb.yom = StringHandler.adjustString(yom);

						DBHandler.updateTLists(wb, ss);
					}
				}

				DBHandler.fetchTeachEdits(ss);
				ss.message = Messages.getMess43(ss);
			}
			else if(req.getParameter("approve") != null) // button in Buttons.getBut31
			{
				for(int y = 0; y < ss.wordList.size(); y++)
				{
					String x = String.valueOf(y);

                    String eng = req.getParameter(x+"eng");
                    if(eng == null || eng.length() == 0) continue;

                    String kan = req.getParameter(x+"kan");
                    if(kan == null || kan.length() == 0) continue;

                    String yom = req.getParameter(x+"yom");
                    if(yom == null || yom.length() == 0) continue;

					if(req.getParameter(x+"check") != null)
                        DBHandler.updateApproved(y, ss);
				}

				ss.message = Messages.getMess43(ss);

				DBHandler.fetchTeachEdits(ss);
			}
			else
			{
				DBHandler.fetchTeachEdits(ss);

				ss.message = Messages.getMess44(ss);
			}

            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
		}
		else
        {
            ss.message = Messages.getMess45(ss);
            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
        }
    }
}
