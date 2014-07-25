// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import usefuljavas.*;


public final class EditLists extends HttpServlet
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

            if(req.getParameter("edit") != null) // button in Buttons.getBut6
			{
				String selection = req.getParameter("radio"); // radio input in BookMethods.prntELRad

                if(selection == null)
                {
                    ss.message = Messages.getMess42A(ss);
                    return;
                }

				ss.message = Messages.getMess42(ss);
				ss.selected = true;
				ss.search = false;

				try { ss.selection = Integer.parseInt(selection); }
				catch(NumberFormatException e) {e.getMessage();}

				ss.rowID = ss.selection;

				//BookMethods.setMainList(ss);
				//ss.list = ss.mainList;
			}
			else if(req.getParameter("remove") != null) // button in Buttons.getBut24
			{
				DBHandler.updateDBList(ss.userID, ss.listName, StringHandler.removeMainList(ss), ss);

				ss.message = Messages.getMess35(ss);
				ss.selected = true;
				ss.search = false;

				//BookMethods.setMainList(ss);
				//ss.list = ss.mainList;
			}
			else if(req.getParameter("update") != null) // button in Buttons.getBut5
			{
				//String wordSets = "";
				String[] reqVars;

				if(ss.threeCols)
				{
					String[] r = { "eng", "kan", "yom" };
					reqVars = r;
				}
				else
				{
					String[] r = { "eng", "kan" };
					reqVars = r;
				}

				//String typ = req.getParameter("type");
				//BookMethods.handleType(typ, ss);
				Selections.setTypList(req.getParameter("type"), ss); // set in Selections.getSelect2
                // sets ss.type, ss.typ and ss.prevType

				if(BookMethods.adjustReq(req, reqVars, ss))
				{
					ss.eng = ss.rVars.get(0); //BookMethods.adjustString(eng);
					ss.kan = ss.rVars.get(1); //BookMethods.adjustString(kan);

					if(ss.threeCols)
						ss.yom = ss.rVars.get(2); //BookMethods.adjustString(yom);
				}
				else return;

				//if(req.getParameter("dispvoclist") != null)
					//{ Selections.setPrevSelect(req.getParameter("dispvoclist"), ss); } // takes the vocList, determines if in Lists or is a Chapter, and sets ss.listGroup and ss.preSelect, or ss.listName
					// Also sets columns for ss.japanese

				DBHandler.handleUpdate(ss);
				ss.selected = true;
				ss.search = false;

				if(ss.role.equals("admin"))
				{
					if(ss.table.equals("japanese"))
					{
						if(ss.selection != 0)
							DBHandler.updateDBList(ss.userID, ss.listName, StringHandler.rebuildMainList(ss), ss);
					}
					else
					{
						ss.listGroup = "No List";
					}
				}
				else
				{
					if(ss.selection != 0)
						DBHandler.updateDBList(ss.userID, ss.listName, StringHandler.rebuildMainList(ss), ss);
				}

				//BookMethods.setMainList(ss);
				//ss.list = ss.mainList;

				ss.typ = "";
				ss.eng = "";
				ss.kan = "";
				ss.yom = "";
				ss.edit = 0;
				ss.selection = 0;
			}
			else if(req.getParameter("addrow") != null) // button in Buttons.getBut4
			{
				//String set = "";
				String[] reqVars;

				if(ss.threeCols)
				{
					String[] r = { "eng", "kan", "yom" };
					reqVars = r;
				}
				else
				{
					String[] r = { "eng", "kan" };
					reqVars = r;
				}

				ss.selection = 0;

				//String typ = req.getParameter("type");
				//BookMethods.handleType(typ, ss);
				Selections.setTypList(req.getParameter("type"), ss); // set in Selections.getSelect2
                // sets ss.type, ss.typ and ss.prevType

				if(BookMethods.adjustReq(req, reqVars, ss))
				{
					ss.eng = ss.rVars.get(0);
					ss.kan = ss.rVars.get(1);

					if(ss.threeCols)
						ss.yom = ss.rVars.get(2);
				}
				else return;

				if(ss.typ.equals("verb") && ss.eng.length() != 0) { ss.eng = StringHandler.addTo(ss.eng, ss); }

				//if(req.getParameter("dispvoclist") != null)
					//{ Selections.setPrevSelect(req.getParameter("dispvoclist"), ss); }

				DBHandler.handleInsert(ss);
				ss.selected = true;
				ss.search = false;

				if(ss.role.equals("admin"))
				{
					if(ss.table.equals("japanese"))
					{
						if(ss.selection != 0)
							DBHandler.updateDBList("admin", ss.listName, StringHandler.rebuildMainList(ss), ss);
					}
					else
					{
						ss.listName = "No List";
					}
				}
				else
				{
					if(ss.selection != 0)
						DBHandler.updateDBList(ss.userID, ss.listName, StringHandler.rebuildMainList(ss), ss);
				}

				//BookMethods.setMainList(ss);
				//ss.list = ss.mainList;
			}
			else if(req.getParameter("display") != null) // button in Buttons.getBut3
			{
				if(ss.role.equals("admin"))
				{
					Selections.setTypList(req.getParameter("type"), ss); // set in Selections.getSelect2
                    // sets ss.type, ss.typ and ss.prevType

					Selections.setPrevSelect(req.getParameter("dispvoclist"), ss); // set in Selections.getSelect3
                    // sets chapter or ss.listGroup, and columns
				}
				else if(ss.role.equals("teacher"))
				{
					Selections.setSSList(req.getParameter("displaychap"), ss); // set in Selections.getSelect4
				}

				ss.selected = false;
				ss.search = false;
				ss.message = Messages.getMess8(ss);
			}
			else
			{
				ss.listGroup = "NO LIST CHOSEN";
				ss.listName = "NO LIST CHOSEN";
				ss.list = new ArrayList<Integer>();

				Selections.getSelect4(ss);
				Selections.setSSList(ss.prevList, ss);
			}

			/*if(ss.role.equals("admin"))
			{
				if(req.getParameter("display") != null)
				{
					//String typ = req.getParameter("type");
					//String displaySet = req.getParameter("displayset");
					//ss.prevSelect = displaySet;

					// BookMethods.handleType(typ, ss);
					Selections.setTypList(req.getParameter("type"), ss); // sets ss.type, ss.typ and ss.prevType

					Selections.setPrevSelect(req.getParameter("dispvoclist"), ss); // sets chapter or ss.listGroup, and columns
					//BookMethods.handleDropList(displaySet, ss);

					ss.selected = false;
					ss.search = false;
					ss.message = Messages.getMess8(ss);
				}

				// NEED TO CHANGE THIS SO CAN GET EIKEN STUFF :: done
				// ss.list = StringHandler.setArrayListInteger(ss.prevSelect, ss.adminList, ss);
				BookMethods.setMainList(ss);
				ss.list = ss.mainList;
			}
			else if(ss.role.equals("teacher"))
			{
				if(req.getParameter("display") != null)
				{
					Selections.setSSList(req.getParameter("displaychap"), ss);

					ss.selected = false;
					ss.search = false;
					ss.message = Messages.getMess8(ss);
				}
				else
				{
					// NEED TO CHANGE THIS SO CAN GET EIKEN STUFF :: done
					// BookMethods.handleChap(ss);
					BookMethods.setMainList(ss);
					ss.list = ss.mainList;
				}
			}*/

			DBHandler.checkSelect(ss);
			DBHandler.fetchWordList(ss);

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
