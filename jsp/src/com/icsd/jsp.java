package com.icsd;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import icsd.authentication.ICSDAuthException;
import icsd.authentication.ICSDAuthentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PrimitiveIterator.OfDouble;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.pool.OracleDataSource;

@WebServlet("/jsp")
public class jsp extends HttpServlet {
    Connection con=null;
    PreparedStatement stmt=null;
    ResultSet rset=null;
    
	public jsp() {
        super();
    }

	public Connection GetDBCon() throws SQLException
	{
		OracleDataSource ods=new OracleDataSource();
		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
		con=ods.getConnection("Rohit12","icsd");
		System.out.println("connection established");
		return con;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			ICSDAuthentication.checkauth(request);
		}
		
			
			 catch (ICSDAuthException e) {
				PrintWriter out=response.getWriter();
				out.println("<html><head><title>Category page</title></head><body>");
				out.println("<a href='homepage.html'>"+e.getMessage()+"</a>");
				out.println("</body>");
				out.println("</html>");
		}
		


			
	String strbtn=request.getParameter("view");
		String action=request.getParameter("insert");
	
		PrintWriter out=response.getWriter();
		String strnum,strEnm,stradd,strEage,strEsal,stregen,strhra,strdesi,strgenfe;
		String[] strall;
		String strmng,strSmng;
		int inum,iage,isal,ihra;
	strEnm=request.getParameter("ename");
	inum=Integer.parseInt(request.getParameter("enum"));
	stradd=request.getParameter("eadd");
	iage=Integer.parseInt(request.getParameter("eage"));
	isal=Integer.parseInt(request.getParameter("esal"));
	stregen=request.getParameter("gender");
	strall=request.getParameterValues("chkbox");
	ihra=Integer.parseInt(request.getParameter("empHRA"));
	//strSmng=request.getParameter("senior.manager");
	strmng=request.getParameter("desig");

	if("male".equals(stregen))
	{
		stregen="male";
	}
	else if("female".equals(stregen))
	{
		stregen="female";
	}
	out.println("values inserted successfully");
//	out.println(strEnm+"  "+stradd+"   "+strmng+" "+stregen+" "+strall.length);
//	for(String names:strall)
//	{
//		out.print(names+" ");
//	}
	

	try {
		con=GetDBCon();
	
	stmt=con.prepareStatement("insert into javaemployees values(?,?,?,?,?,?,?,?,?)");
    stmt.setLong(1, inum);
    stmt.setString(2, strEnm);
    
    stmt.setLong(3,iage);
    stmt.setString(4, stradd);
    stmt.setLong(5,isal);
    stmt.setString(6,stregen);
    stmt.setLong(7,ihra);
    for(String names: strall)
    {
    stmt.setString(8,names);
    }
   
    stmt.setString(9, strmng);
    stmt.executeUpdate();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}


	public void destroy()
	{
		System.out.println("auth destroy called");
		try {
			if(con!=null)
			{
				con.close();
			}
			if(stmt!=null)
			{
				stmt.close();
			}
			if(rset!=null)
			{
				rset.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}