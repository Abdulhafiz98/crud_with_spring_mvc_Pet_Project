package org.example.dao;

import org.example.model.Category;
import org.example.dao.mapper.CategoryMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDao implements BaseDao<Category> {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Category getById(int id) {
        return null;
    }

    @Override
    public List<Category> getList() {
        return jdbcTemplate.query("select * from category", new CategoryMapper());
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean add(Category category) {
        return jdbcTemplate.update(
                "insert into category(name, parent_id) values (?,?)",
                new Object[]{category.getName(),category.getParentId()}
                ) > 0;
    }

    public List<Category> getCategoryById(int id){
        return jdbcTemplate.query("select * from category where parent_id="+"'"+id+"'", new CategoryMapper());
    }

    public List<Category> getCategoryList(int parentId){
        return jdbcTemplate.query("select * from get_category_list(?)", new Object[]{parentId}, new CategoryMapper());
    }
    public List<Category> getBackList(int parentId){
        return jdbcTemplate.query("select * from get_back_list(?)",new Object[]{parentId},new CategoryMapper());
    }

}
