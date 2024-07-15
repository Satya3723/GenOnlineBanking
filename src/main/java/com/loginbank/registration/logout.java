package com.loginbank.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class logout
 */
@WebServlet("/logout")
public class logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        String referrer = request.getHeader("Referer");
        if (referrer != null) {
            if (referrer.contains("userdashboard.jsp")) {
                response.sendRedirect("userlogin.jsp");
            } else if (referrer.contains("admindash.jsp")) {
                response.sendRedirect("adminlogin.jsp");
            } else {
                response.sendRedirect("login.jsp"); // default redirect
            }
        } else {
            response.sendRedirect("login.jsp"); // default redirect if referrer is null
        }
    }
}