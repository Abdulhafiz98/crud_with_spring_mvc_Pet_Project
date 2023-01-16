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
    @GetMapping("sign-up")
    public ModelAndView signUp(){
        ModelAndView modelAndView =
                new ModelAndView("sign-up");
        return modelAndView;
    }


}
