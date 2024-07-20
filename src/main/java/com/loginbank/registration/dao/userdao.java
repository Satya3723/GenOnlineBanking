package com.loginbank.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userdao {
    private Connection conn;

    public userdao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateUser(String accountNumber, String password) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT isloggedin FROM custdetails WHERE accno =? AND password =?");
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getFullName(String accountNumber, String password) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT fullname FROM custdetails WHERE accno =? AND password =?");
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("fullname");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getInitialBalance(String accountNumber, String password) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =? AND password =?");
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("initialbalance");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public boolean checkIsLoggedInStatus(String accountNumber) {
        try {
            String query = "SELECT isloggedin FROM custdetails WHERE accno =?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, accountNumber);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String isloggedin = rs.getString("isloggedin");
                if (isloggedin.equals("no")) {
                    return false;
                } else if(isloggedin.equals("yes")){
                    return true;
                }
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public boolean deleteAccount(String accno) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM custdetails WHERE accno =?");
            pstmt.setString(1, accno);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public double getInitialBalance2(String accno) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =?");
            pstmt.setString(1, accno);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("initialbalance");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public boolean updateBalance(String accno, double amount) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE custdetails SET initialbalance = initialbalance + ? WHERE accno =?");
            pstmt.setDouble(1, amount);
            pstmt.setString(2, accno);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertTransactionHistory(String accno, double amount) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO transaction_history (accno, trans_date, trans_type, amount, status) VALUES (?, NOW(), 'Deposit', ?, 'Success')");
            pstmt.setString(1, accno);
            pstmt.setDouble(2, amount);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updatePassword(String accno, String newPassword) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE custdetails SET password =?, isloggedin = 'yes' WHERE accno =?");
            pstmt.setString(1, newPassword);
            pstmt.setString(2, accno);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}    