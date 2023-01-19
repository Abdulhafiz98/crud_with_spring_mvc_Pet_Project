package org.example.controller;

import org.example.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
   OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String getOrderList(Model model) {
        model.addAttribute("orderList", orderService.getList());
        return "admin/order";
    }

    @GetMapping("/order-item/{id}")
    public String orderItemList(
            Model model,
            @PathVariable("id") int id
    ) {
        model.addAttribute("orderItemList",orderService.getOrderItemList(id));
        return "admin/order";
    }

//@GetMapping("/order-item")
//public String getOrderItemList(Model model) {
//    model.addAttribute("orderItemList",orderService.getOrderItemList(1));
//    return "admin/order";
//}
}
