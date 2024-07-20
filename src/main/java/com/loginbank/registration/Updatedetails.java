package com.loginbank.registration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loginbank.registration.dao.updatedetailsdao;

@WebServlet("/UpdateUserDetailsServlet")
public class Updatedetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");

        updatedetailsdao updatedetailsDAO = new updatedetailsdao();
        ResultSet rs = null;

        try {
            rs = updatedetailsDAO.getUserDetails(accountNumber);

            if (rs != null && rs.next()) {
                String name = rs.getString("fullname");
                String email = rs.getString("emailid");
                String phone = rs.getString("mobileno");
                String address = rs.getString("address");

                request.setAttribute("fullname", name);
                request.setAttribute("email", email);
                request.setAttribute("mobileno", phone);
                request.setAttribute("address", address);
                request.setAttribute("accountNumber", accountNumber);

                request.getRequestDispatcher("updateUserDetailsForm.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No user found with account number " + accountNumber);
                request.getRequestDispatcher("UpdateUser.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("updateUserDetailsForm.jsp").forward(request, response);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // Handle exception
            }
        }
    }}