package org.example.controller;

import org.example.dto.response.ProductResponseDto;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String  getProductList(
            Model model
    ){
        List<ProductResponseDto> productList = productService.getProductList();
        model.addAttribute("productList",productList);
        return "admin/product";
    }

}
