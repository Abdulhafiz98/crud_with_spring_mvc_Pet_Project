package org.example.dao.mapper;

import org.example.model.Favorite;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteMapper implements RowMapper<Favorite> {

    @Override
    public Favorite mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Favorite.builder()
                .id(rs.getInt("id"))
                .productId(rs.getInt("product_id"))
                .userId(rs.getInt("user_id")).build();
    }
}
