package org.example.dao.mapper;

import org.example.model.Category;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category(
                rs.getString("name"),
                rs.getString("parent_name")
        );
        category.setId(rs.getInt("id"));
        return category;
    }
}
