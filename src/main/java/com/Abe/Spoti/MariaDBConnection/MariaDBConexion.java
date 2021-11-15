package com.Abe.Spoti.MariaDBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBConexion {

	private static Connection con=null;
	private static String uri="jdbc:mysql://localhost/spoti5";
	private static String user="root";
	private static String password="";
	
	public static Connection getConexion() {
		if(con==null) {
			try {
				con=DriverManager.getConnection(uri,user,password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				con=null;
			}
		}
		return con;
	}
	
	public void close() {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
