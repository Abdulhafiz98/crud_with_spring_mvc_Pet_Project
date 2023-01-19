package org.example.controller.filter;

import org.example.dao.UserDao;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class SessionConfigInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        boolean aNew = session.isNew();
        String password = (String) session.getAttribute("password");
        if (aNew && !request.getServletPath().equals("/login") && password == null) {
            response.sendRedirect("/login");
        }
        return true;

    }
}
