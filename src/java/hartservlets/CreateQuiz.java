package hartservlets;

// Altered on 2009-03-11

// A Servlet that connects to the database, and displays words in a teacher's list.
// It uses DBHandler to get a connection and a ResultSet that is converted to an ArrayList.
// The ArrayList is used to display the words.

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import usefuljavas.*;

public final class CreateQuiz extends HttpServlet
{
	private CheckSessionState checkSS = new CheckSessionState();
	
	// Adds a new entry, then dispatches back to doGet()
	protected void doPost(HttpServletRequest req, HttpServletResponse res)   
		throws ServletException, IOException
	{	
		doGet(req, res);
	}
	
	
	// Handles request.
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        
		SessionState ss = checkSS.getSS(req, res);
		if (ss == null)
        {
            res.sendRedirect("GetSessionState");
            return;
        }

		// DBHandler.saveState(ss);

		ss.helpInt = 8;

		if(ss.role.equals("admin") || ss.role.equals("teacher"))
		{
			// ss.message = "";

            ss.message = Messages.getMess0(ss);

            // IMPORTANT: don't seem to need this
            // ss.postPage = "/create_quiz.jsp";

			if(req.getParameter("setquiz") != null)
			{
				String qNum = "";

				Selections.setSSList(req.getParameter("displaychap"), ss);

				qNum = StringHandler.adjustString(req.getParameter("quizNum"));

				if(qNum.equals("0") || qNum.equals("") || qNum == null || qNum.equals("NG"))
				{
					ss.message = Messages.getMess20(ss);
                    res.sendRedirect(ss.srcPage);
					return;
				}
				else
                {
					ss.quizNum = Integer.parseInt(qNum);
                    BookMethods.getQuizList(ss);
                }
			}
		}
		else
        {
            ss.message = Messages.getMess31(ss);
        }

        res.sendRedirect(ss.srcPage);
        return;
	}	
}