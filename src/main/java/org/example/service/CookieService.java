package org.example.service;

import org.example.model.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class CookieService {
    public CookieService() {
    }
    public boolean addOrDeleteCookie(HttpServletRequest request, HttpServletResponse response, int productId) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("product")) {
                String value = cookie.getValue();
                String[] split = value.split("/");
                String id = String.valueOf(productId);
                for (int i = 0; i < split.length; i++) {
                    if (id.equals(split[i])) {

                        cookie.setValue("");
                        response.addCookie(cookie);
                        return true;
                    }
                }
                String value1 = cookie.getValue();
                value1 += productId + "/";
                cookie.setValue(value1);
            }
        }
        String value = "" + productId + "/";
        Cookie newCookie = new Cookie("product", value);
        newCookie.setMaxAge(60 * 60 * 24 + 100);
        response.addCookie(newCookie);
        return true;
    }

    public List<Integer> getProductIdFromCookie(HttpServletRequest request) {
        List<Integer> productIdList = new ArrayList<>();
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().contains("product")) {
                String value = cookie.getValue();
                String[] split = value.split("/");
                for (int i = 0; i < split.length; i++) {
                    Integer product_id = Integer.valueOf(split[i]);
                    productIdList.add(product_id);
                }
            }
        }
        return productIdList;
    }
}
