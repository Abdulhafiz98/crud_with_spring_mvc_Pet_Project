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
        String password = (String) session.getAttribute("password");
        if (aNew && !request.getServletPath().equals("/login") && password == null) {
            response.sendRedirect("/login");
        }
        return true;
    }

    public static List<Integer> idList = new ArrayList<>();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CookieService cookieService = new CookieService();
        String id = request.getParameter("id");
        String[] split = id.split("/");
        int productId = Integer.parseInt(split[1]);
        if (split[0].equals("add")) {
            cookieService.addOrDeleteCookie(request, response, productId);
            response.sendRedirect("basket/basket");
        } else {
            idList = cookieService.getProductIdFromCookie(request);
            response.sendRedirect("basket/basket");
        }
    }
}
