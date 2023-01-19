package org.example.controller;

import org.example.dto.UserLoginRequest;
import org.example.dto.UserRegisterRequest;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

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
        HttpSession session = httpServletRequest.getSession();
        if (currentUser != null) {
            session.setAttribute("id",currentUser.getId());
            session.setMaxInactiveInterval(5*30);
        }
        model.addAttribute("message", "username or password is incorrect");
        model.addAttribute("isSuperAdmin", currentUser.getUserRole() != null && currentUser.getUserRole().name().equals(UserRole.SUPER_ADMIN.name()));
        model.addAttribute("user", currentUser);
        return currentUser.getUserRole().name().equals("USER") ? "user/home" : "admin/index";

    }
}