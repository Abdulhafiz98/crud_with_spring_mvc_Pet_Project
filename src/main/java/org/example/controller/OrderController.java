package org.example.controller;

import org.example.dao.UserDao;
import org.example.model.Category;
import org.example.service.CookieService;
import org.example.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    private final CookieService cookieService;
    private final OrderService orderService;

    public OrderController(CookieService cookieService, OrderService orderService) {
        this.cookieService = cookieService;
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String getOrderList(Model model,
                               HttpServletRequest httpServletRequest,
                               UserDao userDao) {
        //   HttpSession session = httpServletRequest.getSession();
        //     Integer userId = (Integer)session.getAttribute("user_id");
        //       model.addAttribute("userRole",userDao.getById(userId).getUserRole().name());
        model.addAttribute("orderList", orderService.sortOrders(0));
        return "admin/order";
    }

    @GetMapping("/order-item/{id}")
    public String orderItemList(
            Model model,
            @PathVariable("id") int id
    ) {
        model.addAttribute("orderItemList", orderService.getOrderItemList(id));
        return "admin/order-item";
    }

    @GetMapping(value = "/pay")
    public String buy(Model model, HttpServletRequest request) {
        List<Integer> productIdFromCookie = cookieService.getProductIdFromCookie(request);
        if (orderService.addOrder(productIdFromCookie, request)) {
            cookieService.deleteCookie(request);
            model.addAttribute("massage", "Your order successfully completed." +
                    "You can see orders from your account");
        } else {
            model.addAttribute("massage", "Something wrong ");
        }
        return "basket/basket";
    }

//    @GetMapping(value = "/edit-status/{id}/{status}")
//    public boolean sortNum(@PathVariable String id, Model model, @PathVariable String status){
//        return true;
//    }

    @ResponseBody
    @GetMapping(
            value = "/edit-status/{id}/{status}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean status(@PathVariable int id, @PathVariable String status) {
        return orderService.editStatus(id, status);
    }
}
