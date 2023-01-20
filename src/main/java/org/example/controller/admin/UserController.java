package org.example.controller.admin;


import org.example.dao.UserDao;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;
private final UserDao userDao;
    public UserController(UserService userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
    }

    @GetMapping("/user/list")
    public String getUserList(Model model,
    HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession();
        Integer userId = (Integer)session.getAttribute("userId");
        model.addAttribute("userRole",userDao.getById(userId).getUserRole().name());

        model.addAttribute("userList", userService.getUserList());
        return "admin/user";
    }
    @GetMapping("/adminseller/list")
    public String getAdminSellerList(Model model,
                              HttpServletRequest httpServletRequest
    ) {
        HttpSession session = httpServletRequest.getSession();
        Integer userId = (Integer)session.getAttribute("userId");
        model.addAttribute("userRole",userDao.getById(userId).getUserRole().name());

        model.addAttribute("userList", userService.getUserList());
        return "admin/admin";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(
            Model model,
            HttpServletRequest httpServletRequest,
            @PathVariable("id") int id
    ) {
        userService.deleteUser(id);
        return getUserList(model,httpServletRequest);
    }

    @GetMapping("/user/update/{id}")
    public String updateUser(
            Model model,
            @PathVariable("id") int id
    ) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("userList", userService.getUserList());
        return "admin/admin";
    }

}
