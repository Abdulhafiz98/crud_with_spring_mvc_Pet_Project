package org.example.controller.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionConfigInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        boolean isNew = session.isNew();
        if (request.getServletPath().equals("/login")){
            return true;
        }
        if (!isNew){
            session.setMaxInactiveInterval(30);
            return true;
        } else{
            response.sendRedirect("/login");
            return false;
        }
    }
}
