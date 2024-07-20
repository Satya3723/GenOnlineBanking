package com.loginbank.registration;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loginbank.registration.dao.updatedetailsdao;

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

        updatedetailsdao updetailsDAO = new updatedetailsdao();

        try {
            int rowsUpdated = updetailsDAO.updateUserDetails(name, email, phone, address, accountNumber);

            if (rowsUpdated > 0) {
                request.getSession().setAttribute("updateMessage", "Successfully updated!"); 
                response.sendRedirect("updateUserDetailsForm.jsp"); // Redirect to a success page
                
            } else {
                request.getSession().setAttribute("message", "Update failed.");
                response.sendRedirect("error.jsp"); // Redirect to an error page
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}