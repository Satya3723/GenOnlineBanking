package com.loginbank.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DepositServlet", urlPatterns = {"/DepositServlet"})
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("DepositServlet: Started");
        String amount = request.getParameter("amount");
        String accountNumber = request.getParameter("accountNumber");

        // Validate input
        if (amount == null || accountNumber == null || amount.trim().isEmpty() || accountNumber.trim().isEmpty()) {
            response.sendRedirect("deposit.jsp?error=invalid_input");
            return;
        }

        // Connect to database
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen?socketTimeout=300000", "root", "system");

            // Retrieve current balance
            PreparedStatement stmt = conn.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =?");
            stmt.setString(1, accountNumber);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                double currentBalance = result.getDouble("initialbalance");
                double depositAmount = Double.parseDouble(amount);

                // Update balance
                stmt = conn.prepareStatement("UPDATE custdetails SET initialbalance =? WHERE accno =?");
                stmt.setDouble(1, currentBalance + depositAmount);
                stmt.setString(2, accountNumber);
                stmt.executeUpdate();

                // Get updated balance
                stmt = conn.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =?");
                stmt.setString(1, accountNumber);
                result = stmt.executeQuery();
                result.next();
                double updatedBalance = result.getDouble("initialbalance");

                // Insert transaction details into transaction_history table
                stmt = conn.prepareStatement("INSERT INTO transaction_history (accno, trans_date, trans_type, amount, status) VALUES (?, NOW(), 'Deposit', ?, 'Success')");
                stmt.setString(1, accountNumber);
                stmt.setDouble(2, depositAmount);
                stmt.executeUpdate();

                // Set session attribute for updated balance
                HttpSession session = request.getSession();
                session.setAttribute("balance", updatedBalance);
                session.setAttribute("initialbalance", updatedBalance);

                // Redirect to user dashboard with sweet alert
                response.sendRedirect("userdashboard.jsp?deposit_success=true");
            } else {
                response.sendRedirect("deposit.jsp?error=account_not_found");
            }
        } catch (SQLException e) {
            response.sendRedirect("deposit.jsp?error=database_error");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // Ignore
                }
            }
        }
    }
}