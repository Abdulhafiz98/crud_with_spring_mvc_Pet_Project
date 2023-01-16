package org.example.controller;


import org.example.model.User;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getUserList(Model model) {
        model.addAttribute("userList", userService.getUserList());
        return "admin/index";
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
        model.addAttribute("userList", userService.getUserList());        return "admin/index";
    }

}
