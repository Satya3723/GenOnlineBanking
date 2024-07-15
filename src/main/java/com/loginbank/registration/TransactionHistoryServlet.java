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

@WebServlet("/transactionhis")
public class TransactionHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system");
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM transaction_history");
            ResultSet result = pstmt.executeQuery();

            request.setAttribute("result", result);
            request.getRequestDispatcher("transactionhis.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}