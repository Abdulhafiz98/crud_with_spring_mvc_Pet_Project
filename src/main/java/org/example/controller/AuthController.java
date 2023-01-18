package org.example.controller;

import org.example.dto.UserLoginRequest;
import org.example.dto.UserRegisterRequest;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        return isSuccess ?"login":"register";
    }

    @PostMapping("/login")
    public String login(
            Model model,
            @ModelAttribute UserLoginRequest loginRequest
    ) {
        User currentUser = authService.login(loginRequest);
        model.addAttribute("message", "username or password is incorrect");
        model.addAttribute("isSuperAdmin", currentUser.getUserRole()!=null&&currentUser.getUserRole().name().equals(UserRole.SUPER_ADMIN.name()));
        model.addAttribute("user", currentUser);
        return "admin/index";

    }


}