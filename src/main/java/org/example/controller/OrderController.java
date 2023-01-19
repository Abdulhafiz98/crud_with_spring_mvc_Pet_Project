package org.example.controller;

import org.example.service.CookieService;
import org.example.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "basket/")
public class OrderController {
    private OrderService orderService;
    private CookieService cookieService;

    public OrderController(OrderService orderService, CookieService cookieService) {
        this.orderService = orderService;
        this.cookieService=cookieService;
    }

    public OrderController() {
    }

    @GetMapping(value = "/pay")
    public String buy(Model model, HttpServletRequest request) {
        List<Integer> productIdFromCookie = cookieService.getProductIdFromCookie(request);
        if (orderService.addOrder(productIdFromCookie, request)) {
            model.addAttribute("massage", "Your order successfully completed." +
                    "You can see orders from your account");
        } else {
            model.addAttribute("massage", "Something wrong ");
        }
        return "basket/basket";
    }
}
