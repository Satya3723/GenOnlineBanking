package com.loginbank.registration.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class sessiondao {
    public void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}