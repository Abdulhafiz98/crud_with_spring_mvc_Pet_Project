package org.example.controller.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionConfigInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        boolean aNew = session.isNew();
        String password = (String)session.getAttribute("password");
        if (aNew&&!request.getServletPath().equals("/login")&&password==null){
            response.sendRedirect("/login");
        }
        return true;

    }
}
