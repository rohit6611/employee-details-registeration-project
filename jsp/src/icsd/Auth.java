 package icsd;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import icsd.DBhandler;


@WebServlet("/Auth")
public class Auth extends HttpServlet {
	DBhandler objDH;
    public Auth() {
        super();
    
    }
    
    public void init(ServletConfig config) throws ServletException
    {
    	objDH=new DBhandler();
    	
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strUnm,strPwd;
		strUnm=request.getParameter("txtUnm");
		strPwd=request.getParameter("txtPwd");
		boolean res =objDH.isValidUser(strUnm, strPwd);
		if(res==true)
		{
			HttpSession session=request.getSession();
			session.setAttribute("unm", strUnm);
			response.sendRedirect(request.getContextPath()+"/NewFile.html");
		}
		else
		{
			response.sendRedirect(request.getContextPath()+"/ErrorPage");
		}
	}

}
	