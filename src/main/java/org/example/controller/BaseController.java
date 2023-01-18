package org.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface BaseController {
    default boolean isvValidSession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        boolean isNew = session.isNew();
        if (!isNew) {
            session.setMaxInactiveInterval(2 * 60);
        }
        return isNew;
    }
}
