package com.loginbank.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class updatedetailsdao {
	public ResultSet getUserDetails(String accountNumber) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DBUtil.getConnection();
	        pstmt = conn.prepareStatement("SELECT * FROM custdetails WHERE accno = ?");
	        pstmt.setString(1, accountNumber);

	        rs = pstmt.executeQuery();
	    } catch (SQLException e) {
	        throw e;
	    }

	    return rs;
	}
	 public int updateUserDetails(String name, String email, String phone, String address, String accountNumber) throws SQLException {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            conn = DBUtil.getConnection();
	            String updateQuery = "UPDATE custdetails SET fullname = ?, emailid = ?, mobileno = ?, address = ? WHERE accno = ?";
	            pstmt = conn.prepareStatement(updateQuery);

	            pstmt.setString(1, name);
	            pstmt.setString(2, email);
	            pstmt.setString(3, phone);
	            pstmt.setString(4, address);
	            pstmt.setString(5, accountNumber);

	            return pstmt.executeUpdate();
	        } catch (SQLException e) {
	            throw e;
	        } finally {
	            try {
	                if (pstmt != null) {
	                    pstmt.close();
	                }
	                if (conn != null) {
	                    conn.close();
	                }
	            } catch (SQLException e) {
	                // Handle exception
	            }
	        }
	    }
	}	

class DBUtil {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Handle exception
        }

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system");
    }
}