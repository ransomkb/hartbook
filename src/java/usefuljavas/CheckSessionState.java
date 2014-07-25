// Created on 2007-03-14

// A Class that retrieves and confirms the SessionState object

package usefuljavas;

import java.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletException;
import java.io.IOException;



public final class CheckSessionState extends HttpServlet
{
	public SessionState getSS(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
			
		SessionState ss = null;		
		HttpSession hsession = null;

		// support using session Cookie automatic support in Tomcat container
		hsession = req.getSession();
		ss = (SessionState) hsession.getAttribute("Session");  
		
		if(ss == null) 
		{
			res.sendRedirect("/Hart/front_page.jsp");
			return null;
		}
		
		ss.TimeNow = Calendar.getInstance();  // record current datetime
		
		return ss;
	}
}
