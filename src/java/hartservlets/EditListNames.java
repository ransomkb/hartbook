// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import usefuljavas.*;


public final class EditListNames extends HttpServlet
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

		if(ss.role.equals("admin") || ss.role.equals("teacher"))
		{
			//ss.message = "";

            ss.message = Messages.getMess0(ss);

			if(req.getParameter("radio") != null) // radio input in BookMethods.prntChapNmLst
			{
				String listID = req.getParameter("radio");

				String name = StringHandler.adjustString(req.getParameter(listID));

				if(name == null || name.equals("NG") || name.length() == 0)
					{ ss.message = Messages.getMess49(ss); }
				else
				{
					//StringHandler.editName(name, x, ss);
					ss.chapter = name;

					BookMethods.editChapList(listID, ss);

					ss.message = Messages.getMess38(ss);
				}
			}
			else if(req.getParameter("newchap") != null) // text input in edit_list_names.jsp
			{
				String[] reqVars = {"newchap"};

				//ss.chapter = req.getParameter("newchap");

				if(BookMethods.adjustReq(req, reqVars, ss))
				{
					ss.chapter = ss.rVars.get(0);
				}
				else return;

				if(ss.chapter != null && ss.chapter.length() != 0)
				{
					BookMethods.addNameToList(ss);

					ss.message = Messages.getMess38(ss);
					//ss.chapter = BookMethods.adjustString(ss.chapter);

					/*if(ss.chapter != null)
					{
						BookMethods.addNameToList(ss);

						ss.message = Messages.getMess38(ss);
					}
					else { ss.message = Messages.getMess49(ss); }*/
				}
			}
			else { ss.message = Messages.getMess39(ss); }

            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
		}
		else
        {
            ss.message = Messages.getMess31(ss);
            res.sendRedirect(ss.srcPage); // redirects to value in ss.srcpage, which should be the same page
            return; // ask Galt if these returns are necessary
        }
    }
}
