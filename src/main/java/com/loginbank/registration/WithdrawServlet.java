package com.loginbank.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "WithdrawServlet", urlPatterns = {"/loginbank/WithdrawServlet"})
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gen";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "system";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

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

            // Withdraw funds from database
            withdrawFundsFromDatabase(accountNumber, withdrawalAmount);

            // Store transaction details in transaction_history table
            storeTransactionInHistory(accountNumber, withdrawalAmount, "withdrawal", "success");

            // Get the new balance
            int newBalance = getBalanceFromDatabase(accountNumber);
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

    private void withdrawFundsFromDatabase(String accountNumber, int withdrawalAmount) throws SQLException, ClassNotFoundException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =?")) {
            preparedStatement.setString(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int currentBalance = resultSet.getInt("initialbalance");
                    if (currentBalance < withdrawalAmount) {
                        throw new SQLException("Insufficient balance");
                    }
                    try (PreparedStatement updatePreparedStatement = connection.prepareStatement("UPDATE custdetails SET initialbalance = initialbalance -? WHERE accno =?")) {
                        updatePreparedStatement.setInt(1, withdrawalAmount);
                        updatePreparedStatement.setString(2, accountNumber);
                        int rowsAffected = updatePreparedStatement.executeUpdate();
                        if (rowsAffected == 0) {
                            throw new SQLException("Account not found");
                        }
                    }
                } else {
                    throw new SQLException("Account not found");
                }
            }
        }
    }

    private void storeTransactionInHistory(String accountNumber, int amount, String transactionType, String status) throws SQLException, ClassNotFoundException {
        try (Connection connection = getConnection();
             PreparedStatement selectPreparedStatement = connection.prepareStatement("SELECT * FROM transaction_history WHERE accno =? AND trans_type =? AND amount =? AND status =?");
             PreparedStatement insertPreparedStatement = connection.prepareStatement("INSERT INTO transaction_history (accno, trans_date, trans_type, amount, status) VALUES (?, NOW(),?,?,?)")) {
            selectPreparedStatement.setString(1, accountNumber);
            selectPreparedStatement.setString(2, transactionType);
            selectPreparedStatement.setInt(3, amount);
            selectPreparedStatement.setString(4, status);
            
            try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    // Insert a new entry if no existing entry is found
                    insertPreparedStatement.setString(1, accountNumber);
                    insertPreparedStatement.setString(2, transactionType);
                    insertPreparedStatement.setInt(3, amount);
                    insertPreparedStatement.setString(4, status);
                    insertPreparedStatement.executeUpdate();
                }
            }
        }
    }

    private int getBalanceFromDatabase(String accountNumber) throws SQLException, ClassNotFoundException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =?")) {
            preparedStatement.setString(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("initialbalance");
                } else {
                    throw new SQLException("Account not found");
                }
            }
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DB_DRIVER);
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}