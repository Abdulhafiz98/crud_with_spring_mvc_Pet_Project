package org.example.service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CookieService {
    public CookieService() {
    }
    Cookie[] cookies =null;
    public boolean addOrDeleteCookie(HttpServletRequest request, HttpServletResponse response, int productId) {
        if (!isExist(request,response,productId)){
            String value = "" + productId + "/";
            Cookie newCookie = new Cookie("product", value);
            newCookie.setMaxAge(60 * 60 * 24 + 100);
            response.addCookie(newCookie);
        }
        return true;
    }

    public List<Integer> getProductIdFromCookie(HttpServletRequest request) {
        List<Integer> productIdList = new ArrayList<>();
        for (Cookie cookie : cookies) {
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

    public boolean deleteCookie(HttpServletRequest request){
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("product")) {
                cookie.setMaxAge(0);
            }
        }
        return false;
    }

    private boolean isExist(HttpServletRequest request ,HttpServletResponse response, int productId ){
       cookies= request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("product")) {
                String value = cookie.getValue();
                String[] split = value.split("/");
                String id = String.valueOf(productId);
                List<String> list = Arrays.stream(split).toList();
                String val ="";
                 if (list.contains(id)){
                     for (String s : list) {
                         if (!s.equals(id)){
                             val+=s+"/";
                         }
                     }
                     cookie.setValue(val);
                     response.addCookie(cookie);
                     return true;
                 }
                String value1 = cookie.getValue();
                value1 += productId + "/";
                cookie.setValue(value1);
                response.addCookie(cookie);
                return true;
            }
        }
        return false;
    }
}
