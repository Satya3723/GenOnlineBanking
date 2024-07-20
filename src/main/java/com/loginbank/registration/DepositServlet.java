package com.loginbank.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.loginbank.registration.dao.userdao;

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

        userdao userDAO = new userdao();
        double currentBalance = userDAO.getInitialBalance2(accountNumber);

        if (currentBalance > 0) {
            double depositAmount = Double.parseDouble(amount);
            if (userDAO.updateBalance(accountNumber, depositAmount) && userDAO.insertTransactionHistory(accountNumber, depositAmount)) {
                // Set session attribute for updated balance
                HttpSession session = request.getSession();
                session.setAttribute("balance", currentBalance + depositAmount);
                session.setAttribute("initialbalance", currentBalance + depositAmount);

                // Redirect to user dashboard with sweet alert
                response.sendRedirect("userdashboard.jsp?deposit_success=true");
            } else {
                response.sendRedirect("deposit.jsp?error=database_error");
            }
        } else {
            response.sendRedirect("deposit.jsp?error=account_not_found");
        }
    }
}