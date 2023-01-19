package org.example.dao.mapper;

import org.example.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .productUrl(rs.getString("url"))
                .price(rs.getDouble("price"))
                .quantity(rs.getInt("quantity"))
                .info(rs.getString("info"))
                .categoryId(rs.getInt("category_id"))
                .build();
        return product;
    }
}
