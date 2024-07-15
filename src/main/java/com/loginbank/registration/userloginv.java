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

@WebServlet("/userloginv")
public class userloginv extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gen";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "system";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // SQL query to check account number and password
    private static final String QUERY = "SELECT isloggedin FROM custdetails WHERE accno =? AND password =?";
    private static final String QUERY2 = "SELECT fullname,initialbalance FROM custdetails WHERE accno =? AND password =?";

    // Initialize the servlet
    public void init() throws ServletException {
        try {
            // Load the database driver
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ServletException("JDBC Driver not found.", e);
        }
    }

    // Handle POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get account number and password from request
        String accountNumber = request.getParameter("username");
        String password = request.getParameter("password");

        // Create a session and set attributes
        HttpSession session = request.getSession(true);
        session.setAttribute("accountNumber", accountNumber);

        // Validate input
        if (accountNumber == null || password == null || accountNumber.isEmpty() || password.isEmpty()) {
        	
            request.setAttribute("error", "Please enter both account number and password.");
            response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
             PreparedStatement preparedStatement2 = connection.prepareStatement(QUERY2)) {

            // Set parameters for the SQL query
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, password);
            
            preparedStatement2.setString(1, accountNumber);
            preparedStatement2.setString(2, password);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            if (resultSet.next()) {
                // Check the isloggedin field
                String isLoggedIn = resultSet.getString("isloggedin");
                //String newPass = resultSet.getString("newpass");
                if (resultSet2.next()) {
                    String fullName = resultSet2.getString("fullname");
                    session.setAttribute("fullName", fullName);
                    int accbalance = resultSet2.getInt("initialbalance");
                    session.setAttribute("initialbalance", accbalance);
                }

                if ("no".equalsIgnoreCase(isLoggedIn)) {
                    // Redirect to passwordreset.jsp
                    response.sendRedirect(request.getContextPath() + "/passwordreset.jsp");
                } else {
                    // Check if entered password matches newpass
                    
                        // Redirect to success.jsp
                        response.sendRedirect(request.getContextPath() + "/userdashboard.jsp");
                }
            } else {
                // Account number and password do not match
                request.setAttribute("error", "Wrong username or password");
                response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error.", e);
        }
    }

    // Handle GET requests (redirect to login page)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
    }
}