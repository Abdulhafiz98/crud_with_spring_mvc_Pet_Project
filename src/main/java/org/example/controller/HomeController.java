package org.example.controller;

import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("")
    public String main(Model model) {
        model.addAttribute("productList",productService.getProductList());
        return "index";
    }


    @GetMapping("/category")
    public String category(Model model){
        model.addAttribute("categoryList", categoryService.getCategoryById(0));
        return "web/categories";
    }

    @GetMapping("/category/{id}")
    public String categoryId(Model model, @PathVariable int id){
        model.addAttribute("categoryList", categoryService.getCategoryById(id));
        return "web/categories";
    }
        @GetMapping("login")
        public String login () {

            return "login";
        }
        @GetMapping("register")
        public String register () {
            return "register";
        }
}


