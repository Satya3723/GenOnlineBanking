package com.loginbank.registration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.loginbank.registration.dao.admindao;

@WebServlet("/loginv")
public class loginv extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        admindao adminDAO = new admindao();
        boolean isValidAdmin = adminDAO.validateAdmin(username, password);

        if (isValidAdmin) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("/loginbank/admindsh.jsp");
        } else {
            request.setAttribute("status", "failed");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}