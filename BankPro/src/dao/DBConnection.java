package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
	
	public static Connection getConnection() {
		Properties props = new Properties();
		FileInputStream fis = null;
		Connection conn = null;
		
		try {
			fis = new FileInputStream("db.properties");
			props.load(fis);
			String driver = props.getProperty("driver");
			String url = props.getProperty("url");
			String username = props.getProperty("username");
			String password = props.getProperty("password");
			Class.forName(driver);
			conn=DriverManager.getConnection(url,username,password);
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			if(conn!=null) conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt!=null) stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}	
	
	public static void close(ResultSet rset) {
		try {
			if(rset!=null) rset.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
