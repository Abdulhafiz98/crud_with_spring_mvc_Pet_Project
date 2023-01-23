package org.example.dao;

import org.example.dto.CategoryRequest;
import org.example.model.Category;
import org.example.dao.mapper.CategoryMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return jdbcTemplate.update("delete from category where id = ?",id)>0;
    }

    @Override
    public boolean add(Category category) {
        return false;
    }

    public boolean updateCategory(CategoryRequest cRequest, int id){
        return jdbcTemplate.update("update category set name = ?, parent_id = ? where id = ?",
                new Object[]{cRequest.getName(), cRequest.getParentId(), id}) > 0;
    }


    public boolean addCat(CategoryRequest category) {
       try {
           System.out.println(category.getName());
           System.out.println(category.getParentId());
           return jdbcTemplate.update(
                   "insert into category(name, parent_id) values (?,?)",
                   category.getName(),category.getParentId()) > 0;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }
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
