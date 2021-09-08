package icsd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;





import oracle.jdbc.pool.OracleDataSource;

public class DBhandler {
	
	public Connection getDBcon()
	{
		Connection con=null;	
		try {
			OracleDataSource ods=new OracleDataSource();
			ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
			con=ods.getConnection("rohit12","icsd");
			System.out.println("connection set successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	public boolean isValidUser(String strUnm,String strPwd)
	{
		boolean res=false;
		Connection con=getDBcon();
		try {
			PreparedStatement stmt=con.prepareStatement("select * from javauser where username=? and passwrd=?");
			stmt.setString(1, strUnm);
			stmt.setString(2, strPwd);
			ResultSet rset=stmt.executeQuery();
			if(rset.next())
			{
				res=true;
				System.out.println("valid user");
			}
			else
			{
				res=false;
				System.out.println("invalid user");
			}
		
			
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
		
	}
}