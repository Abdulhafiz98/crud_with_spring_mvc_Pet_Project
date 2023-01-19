package org.example.controller;

import org.example.dto.response.ProductResponseDto;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String main(
            Model model
    ){
        List<ProductResponseDto> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}

