package com.loginbank.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updetails")
public class updetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String accountNumber = request.getParameter("accountNumber");
        String message = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Replace with your JDBC driver
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system"); // Replace with your database credentials

            String updateQuery = "UPDATE custdetails SET fullname = ?, emailid = ?, mobileno = ?, address = ? WHERE accno = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, accountNumber);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
            	
            	request.getSession().setAttribute("updateMessage", "Successfully updated!"); 
                response.sendRedirect("updateUserDetailsForm.jsp"); // Redirect to a success page
                
            } else {
            	request.getSession().setAttribute("message", "Update failed.");
                response.sendRedirect("error.jsp"); // Redirect to an error page
            }

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}