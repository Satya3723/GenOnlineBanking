package com.loginbank.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/cusdet"})
public class cusdet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerid = request.getParameter("id");
        String fullname = request.getParameter("fullname");
        String address = request.getParameter("address");
        String mobileno = request.getParameter("mobileno");
        String emailid = request.getParameter("emailid");
        String acctype = request.getParameter("acctype");
        String initialbalance = request.getParameter("initialbalance");
        String idproof = request.getParameter("idproof");
        String accno = request.getParameter("accno");
        String password = request.getParameter("password");
        String status = request.getParameter("status");
        String isloggedin = "no";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "system");

            String query = "INSERT INTO custdetails (id, fullname, address, mobileno, emailid, acctype, initialbalance, idproof, accno, password, isloggedin) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, customerid);
            pstmt.setString(2, fullname);
            pstmt.setString(3, address);
            pstmt.setString(4, mobileno);
            pstmt.setString(5, emailid);
            pstmt.setString(6, acctype);
            pstmt.setString(7, initialbalance);
            pstmt.setString(8, idproof);
            pstmt.setString(9, accno);
            pstmt.setString(10, password);
            pstmt.setString(11, status);
            pstmt.setString(11, "no");
            pstmt.executeUpdate();

            response.sendRedirect("success.jsp"); // redirect to success page
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // redirect to error page
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // redirect to error page
        }
    }
}