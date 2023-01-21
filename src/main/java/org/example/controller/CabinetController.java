package org.example.controller;

import org.example.dao.OrderDao;
import org.example.dto.response.OrderItemDto;
import org.example.model.User;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/cabinet")
public class CabinetController {

    private final UserService userService;
    private final OrderDao orderService;

    @Autowired
    public CabinetController(UserService userService,OrderDao orderService){
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping ("/page")
    public String reDirectCabPage(HttpServletRequest req,HttpServletResponse resp, Model model){
        Integer id = (Integer) ( req.getSession().getAttribute("userId"));
        if(id!=0){
            Optional<User> first = userService.getUserList().stream().filter(user -> user.getId()==id).findFirst();
            if(first.isPresent()) {
                model.addAttribute("name", first.get().getName()+"'s personal info");
                model.addAttribute("user", first.get());
                model.addAttribute("text1",first.get().getName()+"'s personal Cabinet");
                model.addAttribute("orderList", orderService.getOrdersBySort(0)
                        .stream()
                        .filter(orderDto -> orderDto.getPhoneNumber().equals(first.get().getPhoneNumber()))
                        .toList());
            }
        }
        return "user/cabinet";
    }
}
