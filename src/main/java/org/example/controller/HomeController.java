package org.example.controller;

import org.example.service.CategoryService;
import org.example.service.FavoriteService;
import org.example.service.ProductService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class HomeController {

    private final FavoriteService favoriteProductService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    public HomeController(FavoriteService favoriteProductService, CategoryService categoryService, ProductService productService, UserService userService) {
        this.favoriteProductService = favoriteProductService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("")
    public String main(Model model, HttpServletRequest request) {
        // there may be the case that user did not signed in;

        model.addAttribute("productList", productService.getProductList());
        model.addAttribute("userId",userService.getUserIdFromSession(request));
        return "index";
    }


    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categoryList", categoryService.getCategoryById(0));
        return "web/categories";
    }

    @GetMapping("/category/{id}")
    public String categoryId(Model model, @PathVariable int id) {
        model.addAttribute("categoryList", categoryService.getCategoryById(id));
        return "web/categories";
    }

    @GetMapping("login")
    public String login() {

        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "favorited/{id}/{userId}", method = RequestMethod.GET)
    public boolean addOrDeleteFavorite(@PathVariable int id, @PathVariable int userId) {
        return favoriteProductService.addOrDelete(id, userId>0?userId:0);
    }
}


