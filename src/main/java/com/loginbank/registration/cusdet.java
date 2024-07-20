package com.loginbank.registration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loginbank.registration.dao.cusdao;

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

        cusdao customerDAO = new cusdao();
        boolean isCustomerAdded = customerDAO.addCustomer(customerid, fullname, address, mobileno, emailid, acctype, initialbalance, idproof, accno, password, status, isloggedin);

        if (isCustomerAdded) {
            response.sendRedirect("success.jsp"); // redirect to success page
        } else {
            response.sendRedirect("error.jsp"); // redirect to error page
        }
    }
}