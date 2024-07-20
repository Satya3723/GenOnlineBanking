package com.loginbank.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.loginbank.registration.dao.userdao;


@WebServlet("/passwordresets")
public class passwordresets extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

        userdao userDAO = new userdao();
        if (userDAO.updatePassword(accountNumber, newPassword)) {
            // Password updated successfully
            request.setAttribute("success", "Password updated successfully.");
            request.getRequestDispatcher("userlogin.jsp").forward(request, response);
        } else {
            // Account number not found
            request.setAttribute("error", "Account number not found.");
            request.getRequestDispatcher("passwordreset.jsp").forward(request, response);
        }
    }
}