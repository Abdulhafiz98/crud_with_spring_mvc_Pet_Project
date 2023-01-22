package org.example.service;

import org.example.dao.CategoryDao;
import org.example.model.Category;

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
}
