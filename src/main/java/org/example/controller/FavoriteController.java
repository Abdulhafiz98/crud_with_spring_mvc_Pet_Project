package org.example.controller;

import org.example.model.Favorite;
import org.example.service.FavoriteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/favorite-product")
public class FavoriteController {

    private final FavoriteService favoriteProductService;

    public FavoriteController(FavoriteService favoriteProductService) {
        this.favoriteProductService = favoriteProductService;
    }

    @GetMapping("/list")
    public String getList(
            Model model, HttpServletRequest request, HttpServletResponse response
    ) {
        Cookie[] cookies = request.getCookies();
        Cookie signedInUser = Arrays.stream(cookies).filter(
                        cookie -> cookie.getName().equals("SignedUser"))
                .findFirst().orElse(null);

        List<Favorite> favoriteProducts = new ArrayList<>();
        List<String> favorites = new ArrayList<>();
        if (signedInUser != null) {
            favoriteProducts = favoriteProductService.getUserFavorites(
                    Integer.parseInt(signedInUser.getValue()));
            if (favoriteProducts.isEmpty()) {
                model.addAttribute("message","You do not have any favorite product yet!!");
            }
        } else {
            Cookie unsignedUser = Arrays.stream(cookies).filter(
                            cookie -> cookie.getName().equals("UnsignedUser"))
                    .findFirst().orElse(null);
            if (Objects.isNull(unsignedUser)) {
                // in this case unsigned user do not have any favorite product
                // we should print a message in meaning - "There is no any favorite product."
                model.addAttribute("message", "You do not have any favorite product yet!!");
            } else {
                // in this case unsigned user have some favorite products in a temporary cookie.
                String value = unsignedUser.getValue();
                String[] favoriteProductList = value.split("/");
                favorites = new ArrayList<>(List.of(favoriteProductList));
            }
        }
        if (favoriteProducts.size() > 0 || favorites.size() > 0) {
            model.addAttribute("favorite-product",
                    favoriteProducts.isEmpty() ? favorites : favoriteProducts);
        }
        return "admin/favorite-product";
    }

}
