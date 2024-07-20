package com.loginbank.registration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.loginbank.registration.dao.userdao;

@WebServlet("/userloginv")
public class userloginv extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession(true);
        session.setAttribute("accountNumber", accountNumber);

        if (accountNumber == null || password == null || accountNumber.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Please enter both account number and password.");
            response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
            return;
        }

        userdao userDAO = new userdao();
        boolean isValidUser = userDAO.validateUser(accountNumber, password);

        if (isValidUser) {
            String fullName = userDAO.getFullName(accountNumber, password);
            int initialBalance = userDAO.getInitialBalance(accountNumber, password);
            session.setAttribute("fullName", fullName);
            session.setAttribute("initialbalance", initialBalance);

            boolean isLoggedIn = userDAO.checkIsLoggedInStatus(accountNumber); // fetch isLoggedIn status again
            if (isLoggedIn) {
                response.sendRedirect(request.getContextPath() + "/userdashboard.jsp"); // redirect to user dashboard page
            } else {
                response.sendRedirect(request.getContextPath() + "/passwordreset.jsp"); // redirect to password reset page
            }
        } else {
            request.setAttribute("error", "Wrong username or password");
            response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/userlogin.jsp");
    }
}