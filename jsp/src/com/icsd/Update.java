package com.icsd;

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


@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
        super();
        // TODO Auto-generated constructor stub
    }
    Connection con=null;
   	PreparedStatement stmt=null;
   	ResultSet rset=null;
        Connection getdbcon() {
     	try {
   			OracleDataSource ods =new OracleDataSource();
   			ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
   			con=ods.getConnection("Rohit12","icsd");
   		} catch (SQLException e) {
   			e.printStackTrace();
   		}
     	
     	
     	return con;
     }
       public void init(ServletConfig config) {
         	con=getdbcon();
         	try {
       			stmt=con.prepareStatement("update javaemployees set empno=? where empname=?");
       		} catch (SQLException e) {
       			e.printStackTrace();
       		}
         	
         	
         }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		int eid=Integer.parseInt(request.getParameter("eid"));	
//		int empname=Integer.parseInt(request.getParameter("ename"));
		   try {
		   stmt.setInt(1, eid);
//		   stmt.setInt(2, empname);
		   stmt.executeUpdate();
		   }
		   catch (SQLException e) {
			   e.printStackTrace();
			// TODO: handle exception
		}
		   PrintWriter out=response.getWriter();
		   out.println("data is updated successfully");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void destroy() {
	  	  
		 try {
			 if(con!=null) {
			con.close();
			 }
			 if(stmt!=null) {
				 stmt.close();
			 }
			 if(rset!=null) {
				 rset.close();
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
