package org.example.controller;

import org.example.model.Product;
import org.example.service.CookieService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/basket")
public class BasketController {
    private final ProductService productService;
    private final CookieService cookieService;


    @Autowired
    public BasketController(ProductService productService, CookieService cookieService) {
        this.productService = productService;
        this.cookieService = cookieService;

    }

    //    test
    @GetMapping(value = "/basket")
    public String basket(Model model) {
        model.addAttribute("basket", productService.getProductList());
        return "basket/basket";
    }

    @GetMapping(value = "/order")
    public String order() {
        return "basket/order";
    }

    @GetMapping(value = "/buy")
    public String payment(Model model , HttpServletRequest request ) {
        double total= 0;
        for (Integer integer : cookieService.getProductIdFromCookie(request)) {
            Product product = productService.getProduct(integer);
          total+=product.getPrice();
        }
        model.addAttribute("total",total);
      return "basket/payment";
    }



//    do not delete
//    @GetMapping(value = "/basket")
//    public String productsFromBasket(Model model, HttpServletRequest httpServletRequest) {
//        List<Product> productList = new ArrayList<>();
//        for (Integer integer : cookieService.getProductIdFromCookie(httpServletRequest)) {
//            Product product = productService.getProduct(integer);
//            productList.add(product);
//        }
//        model.addAttribute("basket", productList);
//        return "basket/basket";
//    }
}
