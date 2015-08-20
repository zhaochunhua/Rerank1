package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.main.Rerank;
import com.persistance.User;

public class ISBN_ThingID_Dao {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/tags";
	private static final String UNAME = "root";
	private static final String UPWD = "1005";
	
	protected  Connection con;
	protected PreparedStatement pstm;
	protected ResultSet rs;
	
	protected void getConnection() throws Exception {
		Class.forName(DRIVER);
		con = DriverManager.getConnection(URL,UNAME,UPWD);
	}
	protected void closeAll(){

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			if (con != null && con.isClosed() == false) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*public int saveUser(User user){
		int row = -1;
		String sql = "insert into user values(?,?,?,?)";
		try {
			this.getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, user.getTag());
			pstm.setString(2, user.getProfile());
			pstm.setInt(3, user.getTag_count());
			pstm.setInt(4, user.getSource_count());
			
			row = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return row;
	}*/

}
