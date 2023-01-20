package org.example.controller.admin;

import org.example.dto.response.ProductResponseDto;
import org.example.model.Category;
import org.example.model.Info;
import org.example.model.Product;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/category/{id}")
    public String getCategoryList(Model model, @PathVariable("id") int id){
        List<Product> productCategoryIdList = productService.getProductCategoryIdList(id);
        model.addAttribute("productCategoryIdList",productCategoryIdList);
        return "product/product-category-id";
    }

    @GetMapping("/info")
    public String getInfoList(Model model){
        List<Info> infoList = productService.getInfo(2);
        model.addAttribute("infoList",infoList);
        return "product/info";
    }
}
