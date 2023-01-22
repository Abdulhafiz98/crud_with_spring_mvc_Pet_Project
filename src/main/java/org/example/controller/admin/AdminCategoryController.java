package org.example.controller.admin;

import org.example.dao.UserDao;
import org.example.dto.CategoryRequest;
import org.example.model.Category;
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




    @RequestMapping(method = RequestMethod.GET)
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


//    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @GetMapping("/delete/{id}")
    public String deleteCategory(
            HttpServletRequest request,
            @PathVariable("id") int id
    ) {
//        int id = Integer.parseInt(request.getParameter("id"));
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/update/{id}")
    public String updateCategory(
            Model model,
            @PathVariable("id") int id
    ) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", category);
        model.addAttribute("categoryList", categoryService.getCategoryList());
        return "/admin/category";
    }

    @GetMapping("/add")
    public String addCategory(
            @ModelAttribute CategoryRequest categoryRequest
    ) {
        categoryService.addCategory(categoryRequest);
        return "redirect:/admin/category";
    }

}
