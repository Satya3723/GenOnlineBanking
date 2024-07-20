package com.loginbank.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.loginbank.registration.dao.userdao;

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

        userdao userDAO = new userdao();
        double initialBalance = userDAO.getInitialBalance2(accno);

        if (initialBalance == 0) {
            if (userDAO.deleteAccount(accno)) {
                // Invalidate the session to log out the user
                session.invalidate();

                response.setContentType("text/html");
                response.getWriter().println("<script>alert('Account deleted successfully.'); window.location.href='userlogin.jsp';</script>");
            } else {
                response.setContentType("text/html");
                response.getWriter().println("<script>alert('Error occurred while deleting account. Please try again.'); window.location.href='index.jsp';</script>");
            }
        } else {
            // Prompt sweet alert to withdraw all amount before deleting account
            response.setContentType("text/html");
            response.getWriter().println("<script>alert('You cannot delete your account until you withdraw all your balance. Please withdraw your balance and try again.'); window.location.href='withdrawl.jsp';</script>");
        }
    }
}