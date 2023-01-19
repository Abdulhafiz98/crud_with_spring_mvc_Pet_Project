package org.example.controller;

import org.example.dao.ProductDao;
import org.example.dto.response.ProductResponseDto;
import org.example.file.FileUtils;
import org.example.model.Product;
import org.example.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getProductList(
            Model model
    ) {
        List<ProductResponseDto> productList = productService.getProductList();
        model.addAttribute("productList", productList);
        return "admin/product";
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addProduct() {
        return "admin/addproduct";
    }
@RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addProduct(
         HttpServletRequest request) {

    String filee = FileUtils.filee(request);
    System.out.println(filee);
    //String url= FileUtils.saveFile(request);
  //  System.out.println(url);
   ProductResponseDto product=ProductResponseDto.builder()
           .name(request.getParameter("name"))
           .productUrl(filee)
          .categoryName((request.getParameter("CategoryName")))
           .info(request.getParameter("info"))
           .quantity(Integer.parseInt(request.getParameter("quantity")))
           .price(Double.parseDouble(request.getParameter("price")))
           .build();
   productService.addProduct(product);

    return "admin/addproduct";
    }
    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    public String deleteUser(
            Model model,
            @PathVariable int id
    ) {
        productService.deleteUser(id);
        return getProductList(model);
    }


}
