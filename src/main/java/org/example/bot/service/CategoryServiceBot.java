package org.example.bot.service;

import org.example.dao.CategoryDao;
import org.example.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public class CategoryServiceBot extends BotService {

    public List<Category> getCategoryList(int parentId) {
        CategoryDao categoryDao = new CategoryDao(new JdbcTemplate(dataSource()));
        List<Category> categoryList = categoryDao.getCategoryList(parentId);
        return categoryList;
    }
    public List<Category> getBackList(int parentId) {
        CategoryDao categoryDao = new CategoryDao(new JdbcTemplate(dataSource()));
        List<Category> categoryList = categoryDao.getBackList(parentId);
        return categoryList;
    }
}