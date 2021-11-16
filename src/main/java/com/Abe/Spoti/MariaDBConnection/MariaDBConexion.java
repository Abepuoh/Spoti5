package com.Abe.Spoti.MariaDBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MariaDBConexion {
	private final static String server = xmlConnection.getConectionInfo("server");
	private final static String database = xmlConnection.getConectionInfo("database");
	private final static String username = xmlConnection.getConectionInfo("username");
	private final static String password = xmlConnection.getConectionInfo("password");
	
	private static Connection con;
	
	public static void conecta() throws ClassNotFoundException {
		 try {
			con=DriverManager.getConnection(server+"/"+database,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			con=null;
			e.printStackTrace();
		}
	}
	
	public static Connection getConexion() {
		if(con==null) {
			try {
				conecta();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
	}
	
	public static List<String[]> ejecutaSelect(String query){
		List<String[]> resultado=new ArrayList<>();
		try {
			Statement st=con.createStatement();
			ResultSet rs= st.executeQuery(query);
			ResultSetMetaData rsmd=(ResultSetMetaData)rs.getMetaData();
			int ncolumns=rsmd.getColumnCount();
			while(rs.next()) {
				String[] fila=new String[ncolumns];
				int i=1;
				while(i<=ncolumns) {
					fila[i-1]=rs.getString(i);
					i++;
				}
				resultado.add(fila);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
	
}