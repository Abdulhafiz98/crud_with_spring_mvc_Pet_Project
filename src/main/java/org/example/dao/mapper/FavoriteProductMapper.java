package org.example.dao.mapper;

import org.example.dao.FavoriteProductDao;
import org.example.model.FavoriteProduct;
import org.example.model.Product;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteProductMapper implements RowMapper<FavoriteProduct> {

    @Override
    public FavoriteProduct mapRow(ResultSet rs, int rowNum) throws SQLException {

       return FavoriteProduct.builder().id(rs.getInt("id")).productId(rs.getInt("product_id"))
                .userId(rs.getInt("user_id")).build();
    }
}
