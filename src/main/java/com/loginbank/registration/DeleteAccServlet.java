package com.loginbank.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeleteAccServlet", urlPatterns = {"/DeleteAccServlet"})
public class DeleteAccServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;  

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accno = request.getParameter("accno");
        System.out.println("Received accno: " + accno); // Add this line for debugging

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system")) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT initialbalance FROM custdetails WHERE accno =?");
            pstmt.setString(1, accno);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double initialBalance = rs.getDouble("initialbalance");
                if (initialBalance == 0) {
                    // Delete account logic here
                    pstmt = conn.prepareStatement("DELETE FROM custdetails WHERE accno =?");
                    pstmt.setString(1, accno);
                    pstmt.executeUpdate();

                    // Invalidate the session to log out the user
                    session.invalidate();

                    response.setContentType("text/html");
                    response.getWriter().println("<script>alert('Account deleted successfully.'); window.location.href='userlogin.jsp';</script>");
                } else {
                    // Prompt sweet alert to withdraw all amount before deleting account
                    response.setContentType("text/html");
                    response.getWriter().println("<script>alert('You cannot delete your account until you withdraw all your balance. Please withdraw your balance and try again.'); window.location.href='withdrawl.jsp';</script>");
                }
            } else {
                System.out.println("Account not found for accno: " + accno); // Add this line for debugging
                response.setContentType("text/html");
                response.getWriter().println("<script>alert('Account not found.'); window.location.href='userdashboard.jsp';</script>");
            }
        } catch (SQLException e) {
            response.setContentType("text/html");
            response.getWriter().println("<script>alert('Error occurred while deleting account. Please try again.'); window.location.href='index.jsp';</script>");
        }
    }
}