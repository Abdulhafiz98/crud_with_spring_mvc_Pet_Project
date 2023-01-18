package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String main(){
        return "web/index";
    }
    @GetMapping("login")
    public String login(){

        return "login";
    }
    @GetMapping("register")
    public String register(){
        return "register";
    }

}

