package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/user/cabinet")
public class CabinetController {

    private final UserService userService;

    @Autowired
    public CabinetController(UserService userService){
        this.userService = userService;
    }

    @GetMapping ("/page")
    public String reDirectCabPage(HttpServletRequest req,HttpServletResponse resp, Model model){
        int id = (int)(req.getSession().getAttribute("id"));
        if(id!=0){
            Optional<User> first = userService.getUserList().stream().filter(user -> user.getId()==id).findFirst();
            if(first.isPresent()) {
                model.addAttribute("name", first.get().getName()+"'s personal info");
                model.addAttribute("text", first.get().toString());
                model.addAttribute("text1",first.get().getName()+"'s personal Cabinet");
                //model.addAttribute("id",first.get().getId());
            }
        }
        return "user/cabinet";
    }


}
