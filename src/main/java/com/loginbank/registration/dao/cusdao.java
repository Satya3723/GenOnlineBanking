package com.loginbank.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class cusdao {
    public boolean addCustomer(String customerid, String fullname, String address, String mobileno, String emailid, String acctype, String initialbalance, String idproof, String accno, String password, String status, String isloggedin) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system");

            String query = "INSERT INTO custdetails (id, fullname, address, mobileno, emailid, acctype, initialbalance, idproof, accno, password, isloggedin) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, customerid);
            pstmt.setString(2, fullname);
            pstmt.setString(3, address);
            pstmt.setString(4, mobileno);
            pstmt.setString(5, emailid);
            pstmt.setString(6, acctype);
            pstmt.setString(7, initialbalance);
            pstmt.setString(8, idproof);
            pstmt.setString(9, accno);
            pstmt.setString(10, password);
            pstmt.setString(11, status);
            pstmt.setString(11, "no");
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}