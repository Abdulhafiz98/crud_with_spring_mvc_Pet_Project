package org.example.controller.admin;

import org.example.dao.CategoryDao;
import org.example.dao.UserDao;
import org.example.dto.CategoryRequest;
import org.example.model.Category;
import org.example.model.User;
import org.example.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = {"", "admin/category"})
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final UserDao userDao;

    public AdminCategoryController(CategoryService categoryService, UserDao userDao) {
        this.categoryService = categoryService;
        this.userDao = userDao;
    }




    @GetMapping("/list")
    public String getCategoryList(
            Model model,
            HttpServletRequest httpServletRequest
    ){
        HttpSession session = httpServletRequest.getSession();
        Integer userId = (Integer)session.getAttribute("userId");
        model.addAttribute("userRole",userDao.getById(userId).getUserRole().name());
        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "admin/category";
    }


    @GetMapping("/delete/{id}")
    public String deleteCategory(
            @PathVariable("id") int id
    ) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category/list";
    }

    @GetMapping("/update/{id}")
    public String updateCategory(
            Model model,
            @PathVariable("id") int id,
            @ModelAttribute Category category
    ) {
        categoryService.updateCategory(id, category);
        return "redirect:/admin/category/list";
    }

    @GetMapping("/add")
    public String addCategory(
            @ModelAttribute CategoryRequest categoryRequest
    ) {
        User user = new User();
        categoryService.addCategory(categoryRequest, user);
        return "redirect:/admin/category/list";
    }

}
