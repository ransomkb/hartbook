// Altered on 2009-02-15
// A Class that retrieves and sets the language variables in a SessionState object
package hartservlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import usefuljavas.*;


public final class EditTables extends HttpServlet
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
            ss.message = Messages.getMess32(ss);

            if(req.getParameter("history") != null) // button set in Buttons.getBut33
            {
                ss.html.setSrcPage("allHistories.jsp", ss);
            }
            else if(req.getParameter("addrow") != null) // button set in Buttons.getBut4
            {
                ss.selection = 0;

                for(int i = 0; i < ss.numOfCols; i++)
                {
                    String c = DBHandler.getColumnTypeName(i, ss);

                    if(BookMethods.checkC(c))
                    {
                        ss.message = "C was entered for a value.";
                        return;
                    }

                    if(c.equalsIgnoreCase("varchar"))
                    {
                        String value = req.getParameter(ss.cols[i]);
                        value.trim();

                        if(value.length() == 0 || value == null)
                        {
                            ss.message = Messages.getMess9(ss);
                            return;
                        }

                        ss.cols[i] = value;
                    }
                }

                DBHandler.handleAddRow(ss);
            }
            else if(req.getParameter("update") != null) // button set in Buttons.getBut5
            {
                for(int i = 0; i < ss.numOfCols; i++)
                {
                    String c = DBHandler.getColumnTypeName(i, ss);

                    if(BookMethods.checkC(c))
                    {
                        ss.message = "C was entered for a value.";
                        return;
                    }

                    if(c.equalsIgnoreCase("varchar"))
                    {
                        String value = req.getParameter(ss.cols[i]);
                        value.trim();

                        if(value.length() == 0 || value == null)
                        {
                            ss.message = Messages.getMess9(ss);
                            return;
                        }

                        ss.cols[i] = value;
                            //ss.dbgApp("paramenter value of ss.cols["+i+"] equals "+ss.cols[i]);
                    }
                }

                DBHandler.handleUpdateTable(ss);
            }
            else if(req.getParameter("radio") != null) // radio insert in BookMethods.prntETRadio
            {
                ss.radioID = req.getParameter("radio");

                DBHandler.handleRowSelection(ss);

                ss.message = Messages.getMess20(ss);
                ss.search = false;
            }
            else if(req.getParameter("tables") != null) // set in Selections.getSelect15C
            {
                ss.dbTable = req.getParameter("tables");

                DBHandler.handleTable(ss);
            }

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
