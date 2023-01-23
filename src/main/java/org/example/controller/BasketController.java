package org.example.controller;

import org.example.model.Product;
import org.example.model.User;
import org.example.service.CookieService;
import org.example.service.ProductService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/basket")
public class BasketController {
    private final ProductService productService;
    private final CookieService cookieService;
    private final UserService userService;

    @Autowired
    public BasketController(ProductService productService, CookieService cookieService,UserService userService) {
        this.productService = productService;
        this.cookieService = cookieService;
        this.userService =userService;

    }

    @GetMapping(value = "/order")
    public String payment(Model model , HttpServletRequest request ) {
        User user = userService.getUserById(request);
        if (user==null){
            return "redirect:../login";
        }
        double total= 0;
        for (Integer integer : cookieService.getProductIdFromCookie(request)) {
            Product product = productService.getProduct(integer);
          total+=product.getPrice();
        }
            model.addAttribute("user",user);
            model.addAttribute("total",total);
            return "basket/payment";
    }

    @GetMapping(value = "/basket")
    public String productsFromBasket(Model model, HttpServletRequest httpServletRequest) {
        List<Product> productList = new ArrayList<>();
        for (Integer integer : cookieService.getProductIdFromCookie(httpServletRequest)) {
            Product product = productService.getProduct(integer);
            productList.add(product);
        }
        model.addAttribute("basket", productList);
        return "basket/basket";
    }
    @GetMapping(value = "add/{id}")
    public void addBasket(HttpServletRequest request , HttpServletResponse response, @PathVariable int id){
        cookieService.addOrDeleteCookie(request, response, id);
    }
}
