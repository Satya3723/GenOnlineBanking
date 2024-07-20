package com.loginbank.registration;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.loginbank.registration.dao.withdrawdao;
import com.loginbank.registration.dao.withdrawdao.DBUtil;

@WebServlet(name = "WithdrawServlet", urlPatterns = {"/loginbank/WithdrawServlet"})
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber");
        int withdrawalAmount = Integer.parseInt(request.getParameter("withdrawalAmount"));

        try {
            // Check if the withdrawal amount is valid
            if (withdrawalAmount <= 0) {
                response.setContentType("text/plain");
                response.getWriter().write("Invalid withdrawal amount. Please try again.");
                return;
            }

            withdrawdao withdrawDAO = new withdrawdao(DBUtil.getConnection());

            // Withdraw funds from database
            withdrawDAO.withdrawFunds(accountNumber, withdrawalAmount);

            // Store transaction details in transaction_history table
            withdrawDAO.storeTransactionInHistory(accountNumber, withdrawalAmount, "withdrawal", "success");

            // Get the new balance
            int newBalance = withdrawDAO.getBalance(accountNumber);
            session.setAttribute("initialbalance", newBalance);

            // Display new balance on a new page
            response.setContentType("text/plain");
            response.getWriter().write("Withdrawal successful. New balance: " + newBalance);
        } catch (SQLException e) {
            response.setContentType("text/plain");
            response.getWriter().write("Withdrawal failed. Please try again. Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}