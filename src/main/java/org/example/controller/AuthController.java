package org.example.controller;

import org.example.dao.UserDao;
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
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserDao userDao;

    @Autowired
    public AuthController(AuthService authService, UserDao userDao) {
        this.authService = authService;
        this.userDao = userDao;
    }


    @PostMapping("/register")
    public String register(
            Model model,
            @ModelAttribute UserRegisterRequest userRegisterRequest
    ) {
        boolean isSuccess = authService.addUser(userRegisterRequest);
        return isSuccess ? "login" : "register";
    }
    @PostMapping("/admin/register")
    public String registerAdmin(
            Model model,
            @ModelAttribute UserRegisterRequest userRegisterRequest,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession();
        Integer userId = (Integer)session.getAttribute("userId");
        model.addAttribute("userRole",userDao.getById(userId).getUserRole().name());
        boolean isSuccess = authService.addAdmin(userRegisterRequest);

        return "admin/index";
    }
    @PostMapping("/seller/register")
    public String registerSeller(
            Model model,
            @ModelAttribute UserRegisterRequest userRegisterRequest,
            HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession();
        Integer userId = (Integer)session.getAttribute("userId");
        model.addAttribute("userRole",userDao.getById(userId).getUserRole().name());
        boolean isSuccess = authService.addSeller(userRegisterRequest);

        return "admin/index";
    }

    @PostMapping("/login")
    public String login(
            Model model,
            HttpServletRequest httpServletRequest,
            @ModelAttribute UserLoginRequest loginRequest

    ) {
        boolean condition;
        User currentUser = authService.login(loginRequest);
        if(currentUser==null){
            condition = true;
            model.addAttribute("condition",condition);
            return "/index";
        }
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userId",currentUser.getId());
        model.addAttribute("userRole", currentUser.getUserRole().name());
        model.addAttribute("message", "username or password is incorrect");
        model.addAttribute("isSuperAdmin", currentUser.getUserRole() != null && currentUser.getUserRole().name().equals(UserRole.SUPER_ADMIN.name()));
        model.addAttribute("user", currentUser);
        return currentUser.getUserRole().name().equals("USER") ? "web/index" : "admin/index";
    }
}