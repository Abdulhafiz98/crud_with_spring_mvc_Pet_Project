package org.example.dao;

import org.example.dao.mapper.FavoriteMapper;
import org.example.model.Favorite;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FavoriteDao implements BaseDao<Favorite>{
    private final JdbcTemplate jdbcTemplate;

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Favorite getById(int id) {
        return null;
    }

    @Override
    public List<Favorite> getList() {
        return jdbcTemplate.query("select * from favorite",new FavoriteMapper());
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("delete from favorite where id = ?",id) > 0;
    }

    public boolean add(Favorite favoriteProduct) {
        return jdbcTemplate.update(
                "insert into favorite(product_id,user_id) values (?,?)",
                new Object[]{favoriteProduct.getProductId(),favoriteProduct.getUserId()}
        ) > 0;
    }
}
