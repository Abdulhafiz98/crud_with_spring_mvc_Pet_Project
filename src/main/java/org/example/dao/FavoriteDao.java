package org.example.dao;

import org.example.dao.mapper.ProductMapper;
import org.example.model.Favorite;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.List;

public class FavoriteDao {
    private final JdbcTemplate jdbcTemplate;

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Product> getUserfavorites(int userId) {
        return jdbcTemplate.query("select * from product p inner join favorite f on f.product_id = p.id where f.user_id = ?",new Object[]{userId},new ProductMapper());
    }

    public boolean addOrDeleteFavorite(int productId,int userId){
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withFunctionName("add_or_delete_favorite");
            SqlParameterSource in = new MapSqlParameterSource().addValue("i_product_id",productId).addValue("i_user_id",userId);
            return jdbcCall.executeFunction(boolean.class,in);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
