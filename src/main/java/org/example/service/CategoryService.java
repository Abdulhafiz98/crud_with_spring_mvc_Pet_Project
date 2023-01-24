package org.example.service;

import org.example.dao.CategoryDao;
import org.example.dto.CategoryRequest;
import org.example.model.Category;
import org.example.model.User;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getCategoryList(){
        return categoryDao.getList();
    }

    public List<Category> getCategoryById(int id){
        return categoryDao.getCategoryById(id);
    }


    public boolean deleteCategory(int id){
        return categoryDao.delete(id);
    }

    public Category getCategory(int id){
        return categoryDao.getById(id);
    }

    public boolean updateCategory(int id, Category category){
        return categoryDao.updateCategory(category, id);
    }

    public boolean addCategory(final CategoryRequest categoryRequest, final User user){
        Category category1 = Category.builder()
                .name(categoryRequest.getName())
                .parentId(categoryRequest.getParentId()).build();
        return categoryDao.addCat(category1, user);
    }
}



