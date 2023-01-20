package org.example.controller.admin;

import org.example.dao.UserDao;
import org.example.model.Category;
import org.example.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
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
}
