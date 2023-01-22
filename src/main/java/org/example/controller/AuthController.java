package org.example.controller;

import org.example.dao.UserDao;
import org.example.dto.UserLoginRequest;
import org.example.dto.UserRegisterRequest;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class AuthController {
    //TODO @Asror auth service
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public String register(
            Model model,
            @ModelAttribute UserRegisterRequest userRegisterRequest
    ) {
        boolean isSuccess = authService.addUser(userRegisterRequest);
        return isSuccess ? "login" : "register";
    }

    @PostMapping("/login")
    public String login(
            Model model,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            @ModelAttribute UserLoginRequest loginRequest
    ) {

        User currentUser = authService.login(loginRequest);

        if (currentUser != null) {
            addSession(httpServletRequest, httpServletResponse);
        }
        model.addAttribute("message", "username or password is incorrect");
        model.addAttribute("isSuperAdmin", currentUser.getUserRole() != null && currentUser.getUserRole().name().equals(UserRole.SUPER_ADMIN.name()));
        model.addAttribute("user", currentUser);

            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("password",currentUser.getPassword());
//            addSession(httpServletRequest, httpServletResponse);
            model.addAttribute("user", currentUser);
            if (currentUser.getUserRole().equals(UserRole.USER)) return "web/index";
            else  return "admin/index";
        }
//        return "login";


    private void addSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        session.setMaxInactiveInterval(30);
    }


}