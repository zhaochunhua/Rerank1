package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao_Con {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/tags";
	private static final String UNAME = "root";
	private static final String UPWD = "1005";
	
	protected  Connection con;
	
	public Connection getConnection() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(URL,UNAME,UPWD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	protected void closeCon(){
		try {
			if (con != null && con.isClosed() == false) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
