package com.icsd;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import oracle.jdbc.pool.OracleDataSource;

@WebServlet("/Viewdata")
public class viewdata extends HttpServlet {
	Connection con=null;
	PreparedStatement stmt=null;
	ResultSet rSet=null;
    public viewdata() {
        super();
    }
    Connection getDBcon() throws SQLException
    {
    	OracleDataSource ods=new OracleDataSource();
    	ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
    	con=ods.getConnection("Rohit12","icsd");
    	System.out.println("viewdata connection establisd");
    	return con;
    }
    public void init(ServletConfig config)
    {
    	System.out.println("view data init called");
    	try{
    		con=getDBcon();
    		stmt=con.prepareStatement("select * from javaemployees where empno=?");
    		
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
			// TODO: handle exception
		}
    	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head><title>Employe data</title><head>");
		out.println("<body>");
		out.println("<table border=1px>");
		out.println("<tr>");
		out.println("<th>id</th>");
		out.println("<th>name</th>");
		out.println("<th>city</th>");
		out.println("<th>age</th>");
		out.println("<th>salary</th>");
		out.println("<th>gender</th>");
		out.println("<th>HRA</th>");
		out.println("<th>alloance</th>");
		out.println("<th>designation</th>");
		out.println("</tr>");
		
		int empnumber=Integer.parseInt(request.getParameter("enum"));
		if(request.getParameter("del") != null)
		{
		response.sendRedirect(request.getContextPath()+"/DeleteData?eid="+empnumber);
		}
//		if(request.getParameter("upd")!=null)
//		{
//			response.sendRedirect(request.getContentType()+"/Update?eid="+empnumber);
//		}
		try {
	    stmt.setLong(1, empnumber);
	    rSet=stmt.executeQuery();
		while(rSet.next())
		{
		  String strnm=rSet.getString("nameemp"),stradd=rSet.getString("address"),strgen=rSet.getString("gender"),strall=rSet.getString("allowances"),strdesi=rSet.getString("designation");
		  int inum=rSet.getInt("empno"),iage=rSet.getInt("age"),isal=rSet.getInt("salary"),iHRA=rSet.getInt("hra");
		  out.println("<tr>");
		  out.println("<td>"+inum+"</td>");
		  out.println("<td>"+strnm+"</td>");
		  out.println("<td>"+stradd+"</td>");
		  out.println("<td>"+iage+"</td>");
		  out.println("<td>"+isal+"</td>");
		  out.println("<td>"+strgen+"</td>");
		  out.println("<td>"+iHRA+"</td>");
		  out.println("<td>"+strall+"</td>");
		  out.println("<td>"+strdesi+"</td>");
		  out.println("</tr>");
		}
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
		
		}
		catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	    
	
	}
     public void destroy()
     {
    	 System.out.println("destroy called viewdata");
    	 
    	 try {
    		 if(con!=null)
    		 {
    			 con.close();
    		 }
    		 if(stmt!=null)
    		 {
    			 stmt.close();
    		 }
    		 if(rSet!=null)
    		 {
    			 rSet.close();
    		 }
    	 }
    	 catch (SQLException e) {
    		 e.printStackTrace();
			// TODO: handle exception
		}
    	 }
}