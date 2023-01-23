package org.example.controller;

import org.example.model.Favorite;
import org.example.model.OrderStatus;
import org.example.model.Product;
import org.example.model.User;
import org.example.service.FavoriteService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/user/favorite")
public class FavoriteController {

    private final FavoriteService favoriteProductService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteProductService, UserService userService) {
        this.favoriteProductService = favoriteProductService;
        this.userService = userService;
    }

    @GetMapping("")
    public String getList(
            Model model, HttpServletRequest request, HttpServletResponse response
    ) {
        Integer id = (Integer) ( request.getSession().getAttribute("userId"));
        if(id!=0){
            Optional<User> first = userService.getUserList().stream().filter(user -> user.getId()==id).findFirst();
            if(first.isPresent()) {
                List<Product> userFavorites = favoriteProductService.getUserFavorites(id);
                System.out.println(userFavorites);
                model.addAttribute("favorites",userFavorites);
                model.addAttribute("userId",id);
            }
        }
        return "user/favorite";
    }

    @RequestMapping(value = "/{id}/{userId}",method = RequestMethod.GET)
    public boolean addOrDeleteFavorite(@PathVariable int id, @PathVariable int userId){
        return favoriteProductService.addOrDelete(id, userId);
    }
}
