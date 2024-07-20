package com.loginbank.registration;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.loginbank.registration.dao.transactiondao;

@WebServlet("/transactionhis")
public class TransactionHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        transactiondao transactionDAO = new transactiondao();
        ResultSet result = transactionDAO.getTransactionHistory();
        List<Transaction> transactions = new ArrayList<>();

        try {
            while (result.next()) {
                Transaction transaction = new Transaction();
                transaction.setAmount(result.getString("amount"));
                transaction.setStatus(result.getString("status"));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            // Handle exception
        } finally {
            try {
                result.close();
            } catch (Exception e) {
                // Handle exception
            }
        }

        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("transactionhis.jsp").forward(request, response);
    }
}

class Transaction {
    private String amount;
    private String status;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}