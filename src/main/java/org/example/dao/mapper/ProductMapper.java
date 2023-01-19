package org.example.dao.mapper;

import org.example.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

        Product product = new Product(
                rs.getString("name"),
                rs.getString("product_url"),
                rs.getDouble("price"),
                rs.getInt("quantity"),
                rs.getString("info"),
                rs.getInt("category_id"),
                rs.getDouble("discount")
        );
        product.setId(rs.getInt("id"));
        return product;
    }
}
