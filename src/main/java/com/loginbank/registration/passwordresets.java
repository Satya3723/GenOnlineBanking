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

@WebServlet("/passwordresets")
public class passwordresets extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gen";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "system";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // SQL query to update password and isloggedin
    private static final String UPDATE_QUERY = "UPDATE custdetails SET password =?, isloggedin = 'yes' WHERE accno =?";

    // Handle POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get account number and new password from request
        String accountNumber = request.getParameter("username");
        String newPassword = request.getParameter("newpassword");

        // Validate input
        if (accountNumber == null || newPassword == null || accountNumber.isEmpty() || newPassword.isEmpty()) {
            request.setAttribute("error", "Please enter both account number and new password.");
            request.getRequestDispatcher("passwordreset.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

            // Set parameters for the SQL query
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, accountNumber);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Password updated successfully
                request.setAttribute("success", "Password updated successfully.");
                request.getRequestDispatcher("userlogin.jsp").forward(request, response);
            } else {
                // Account number not found
                request.setAttribute("error", "Account number not found.");
                request.getRequestDispatcher("passwordreset.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error.", e);
        }
    }
}