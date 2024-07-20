package com.loginbank.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class withdrawdao {
    private Connection connection;

    public withdrawdao(Connection connection) {
        this.connection = connection;
    }

    public void withdrawFunds(String accountNumber, int withdrawalAmount) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =?")) {
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

    public void storeTransactionInHistory(String accountNumber, int amount, String transactionType, String status) throws SQLException {
        try (PreparedStatement selectPreparedStatement = connection.prepareStatement("SELECT * FROM transaction_history WHERE accno =? AND trans_type =? AND amount =? AND status =?");
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

    public int getBalance(String accountNumber) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =?")) {
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


public class DBUtil {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system");
    }}
}