package org.example.controller.admin;

import org.example.dto.response.ProductResponseDto;
import org.example.model.Product;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String getProductList(
            Model model
    ) {
        List<ProductResponseDto> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "admin/product";
    }

//    @GetMapping("/category/{id}")
//    public String getCategoryList(Model model, @PathVariable("id") int id) {
//        List<Product> productList = productService.getProductCategoryIdList(id);
//        model.addAttribute("productList", productList);
//        return "product/product";
//    }

    @GetMapping("/search")
    public String getSearchedProductList(@RequestParam("productName") String productName , Model model){

        List<ProductResponseDto> productResponseDtoList = productService.getProductListByName(productName);

        model.addAttribute("productList", productResponseDtoList);
        return "admin/product";
    }

}
