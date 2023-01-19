package org.example.dao;

import org.example.dao.mapper.FavoriteProductMapper;
import org.example.model.FavoriteProduct;
import org.example.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FavoriteProductDao  implements BaseDao<FavoriteProduct>{
    private final JdbcTemplate jdbcTemplate;

    public FavoriteProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public FavoriteProduct getById(int id) {
        return null;
    }

    @Override
    public List<FavoriteProduct> getList() {
        return jdbcTemplate.query("select * from favorite_product",new FavoriteProductMapper());
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("delete from favorite_product where id = ?",id) > 0;
    }

    public boolean add(FavoriteProduct favoriteProduct) {
        return jdbcTemplate.update(
                "insert into favorite_product(product_id,user_id) values (?,?)",
                new Object[]{favoriteProduct.getProductId(),favoriteProduct.getUserId()}
        ) > 0;
    }
}
