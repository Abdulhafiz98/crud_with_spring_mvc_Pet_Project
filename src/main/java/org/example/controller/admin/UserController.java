package org.example.controller.admin;


import org.example.model.User;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getUserList(Model model) {
        model.addAttribute("userList", userService.getUserList());
        return "admin/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(
            Model model,
            @PathVariable("id") int id
    ) {
        userService.deleteUser(id);
        return getUserList(model);
    }

    @GetMapping("/update/{id}")
    public String updateUser(
            Model model,
            @PathVariable("id") int id
    ) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("userList", userService.getUserList());
        return "admin/admin";
    }

    @PostMapping("/update")
    public String updateUser(HttpServletRequest req, Model model){
        String resultText;
        User user = userService.getUser((int) req.getSession().getAttribute("id"));
        String name = req.getParameter("name");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        if(user.getPassword().equals(oldPassword)){
            user.setName(name);
            user.setPassword(newPassword);
            userService.updateUser(user);
            resultText = "User's info updated successfully";
        }else {
            resultText = "Previous password is wrong !!! Please try again";
        }
        model.addAttribute("resText",resultText);
        return "user/home";
    }

}
