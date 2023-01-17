package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String login(
            Model model
    ){
//        model.addAttribute("text","Good");
        return "admin/index";
    }
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView =
                new ModelAndView("login");
        return modelAndView;
    }
    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView modelAndView =
                new ModelAndView("register");
        return modelAndView;
    }


}
