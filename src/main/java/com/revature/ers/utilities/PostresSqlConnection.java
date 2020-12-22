package com.revature.ers.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostresSqlConnection {

	private static Connection connection;
	
	private PostresSqlConnection() {
	}
	
	//Local database connectivity
//	public static Connection getConnection() throws ClassNotFoundException, SQLException {
//		Class.forName(DbUtilProps.DRIVER);
//		String url=DbUtilProps.URL;			
//		String username=System.getenv("postgresUserName");
//		String password=System.getenv("postgresPassword");
//		connection=DriverManager.getConnection(url, username, password);
//		return connection;
//	}
	
	//AWS-RES connectivity
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DbUtilProps.DRIVER);
		String url=DbUtilProps.URL;			
		String username="postgres";
		String password="baohieu1984";
		connection=DriverManager.getConnection(url, username, password);
		return connection;
	}
	
}
