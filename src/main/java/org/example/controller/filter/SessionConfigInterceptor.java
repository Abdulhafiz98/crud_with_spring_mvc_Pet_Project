package org.example.controller.filter;

import org.example.service.CookieService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class SessionConfigInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        boolean aNew = session.isNew();
        if (aNew && !request.getServletPath().equals("/")&&!request.getServletPath().equals("/login")) {
            response.sendRedirect("/login");
        }
        return true;
    }
}
