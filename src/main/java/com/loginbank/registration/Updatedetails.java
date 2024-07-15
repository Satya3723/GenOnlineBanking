package com.loginbank.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/UpdateUserDetailsServlet")
public class Updatedetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        try {
        	try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM custdetails WHERE accno =?");
            pstmt.setString(1, accountNumber);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("Query executed successfully");

            if (rs.next()) {
                System.out.println("ResultSet has data");

                String name = rs.getString("fullname");
                String email = rs.getString("emailid");
                String phone = rs.getString("mobileno");
                String address = rs.getString("address");

                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.println("Address: " + address);

                request.setAttribute("fullname", name);
                request.setAttribute("email", email);
                request.setAttribute("mobileno", phone);
                request.setAttribute("address", address);
                request.setAttribute("accountNumber", accountNumber);

                request.getRequestDispatcher("updateUserDetailsForm.jsp").forward(request, response);
            } else {
                System.out.println("No user found with account number " + accountNumber);
                request.setAttribute("error", "No user found with account number " + accountNumber);
                request.getRequestDispatcher("UpdateUser.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("updateUserDetailsForm.jsp").forward(request, response);
        }
    }}