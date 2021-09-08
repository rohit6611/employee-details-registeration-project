package comi.icsd;

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

@WebServlet("/DeleteData")
public class DeleteData extends HttpServlet {
	Connection con=null;
	PreparedStatement stmt=null;
	ResultSet rSet=null;
	public DeleteData() {
        super();
    }
	Connection getDBcon() throws SQLException
	{
		 OracleDataSource ods=new OracleDataSource();
		 ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
		 con=ods.getConnection("Rohit12","icsd");
		 System.out.println("connection establish");
		return con;
	}
	public void init(ServletConfig config)
	{
		System.out.println("init called delete");
		try {
			con=getDBcon();
			stmt=con.prepareStatement("delete from javaemployees where empno=?");
		}
		catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   int eid=Integer.parseInt(request.getParameter("eid"));	
	   try {
	   stmt.setInt(1, eid);
	   stmt.executeUpdate();
	   }
	   catch (SQLException e) {
		   e.printStackTrace();
		// TODO: handle exception
	}
	   PrintWriter out=response.getWriter();
	   out.println("data is deleted successfully");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    public void destroy()
    {
    	System.out.println("delete destroy called");
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
		}
    	}
    }