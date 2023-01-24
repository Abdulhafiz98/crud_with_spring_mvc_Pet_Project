package org.example.dao;

import org.example.model.Category;
import org.example.dao.mapper.CategoryMapper;
import org.example.model.User;
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
        return jdbcTemplate.update("delete category where id = ?", new Object[]{id}) > 0 ;
    }

    public boolean updateCategory(Category cRequest, int id){
        return jdbcTemplate.update("update category set name = ?, parent_id = ? update_date = ? where id = ?",
                new Object[]{cRequest.getName(), cRequest.getParentId(), cRequest.getUpdatedDate() ,id}) > 0;
    }


    @Override
    public boolean add(Category category) {
        return false;
    }

    public boolean addCat(Category category, User user) {
        return jdbcTemplate.update(
                "insert into category(name, parent_id, created_by) values (?,?,?)",
                new Object[] {category.getName(),category.getParentId(), user.getFirstName()}) > 0;
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
