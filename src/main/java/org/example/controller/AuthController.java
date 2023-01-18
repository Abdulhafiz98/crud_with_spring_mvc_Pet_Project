package org.example.controller;

import org.example.dto.UserLoginRequest;
import org.example.dto.UserRegisterRequest;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {
    //TODO @Asror auth service
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("/register")
    public String main() {
        return "admin/register";
    }


    @PostMapping("/register")
    public ModelAndView register(
            @ModelAttribute UserRegisterRequest userRegisterRequest
    ) {
        boolean isSuccess = authService.addUser(userRegisterRequest);
        ModelAndView modelAndView =
                new ModelAndView("index");
        modelAndView.addObject("isSuccess", isSuccess);
        return modelAndView;
    }

    @PostMapping("/login")
    public String login(
            Model model,
            @ModelAttribute UserLoginRequest loginRequest,
            HttpServletResponse resp
            ) {
        User currentUser = authService.login(loginRequest);
        model.addAttribute("message", "username or password is incorrect");
        model.addAttribute("isSuperAdmin", currentUser.getUserRole().equals(UserRole.SUPER_ADMIN));
        model.addAttribute("user", currentUser);
        Cookie cookie = new Cookie("u_phone", currentUser.getPhoneNumber());
        cookie.setMaxAge(5000);
        resp.addCookie(cookie);
        return currentUser.getUserRole().name().equals("USER") ? "user/home" : "admin/index";
    }


}