package org.example.controller;

import org.example.dto.CategoryRequest;
import org.example.dto.UserRegisterRequest;
import org.example.model.Category;
import org.example.model.User;
import org.example.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getCategoryList(
            Model model
    ){
        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(
            Model model,
            @PathVariable("id") int id
    ) {
        categoryService.deleteCategory(id);
        return getCategoryList(model);
    }

    @GetMapping("/update/{id}")
    public String updateCategory(
            Model model,
            @PathVariable("id") int id
    ) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", category);
        model.addAttribute("categoryList", categoryService.getCategoryList());
        return "admin/admin";
    }

    @PostMapping("/add")
    public String register(
            Model model,
            @ModelAttribute CategoryRequest categoryRequest
    ) {
        categoryService.addCategory(categoryRequest);
        return "admin/admin";
    }



}
