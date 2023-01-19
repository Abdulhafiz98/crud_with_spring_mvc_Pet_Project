package org.example.dao;

import org.example.dao.mapper.ProductMapper;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.List;

public class ProductDao implements BaseDao<Product> {


    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product getById(int id) {
        return null;
    }

    @Override
    public List<Product> getList() {
        return jdbcTemplate.query("select * from product", new ProductMapper());
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean add(Product product) {
        return jdbcTemplate.update(
                "insert into product(name, url, price, quantity, category_id, info) values (?,?,?,?,?,?)",
                new Object[]{product.getName(), product.getProductUrl(), product.getPrice(), product.getQuantity(), product.getCategoryId(), product.getInfo()}
        ) > 0;
    }
    public List<Product> getListOrder(Long chatId){return jdbcTemplate.query("select * from get_order_list(?)",new Object[]{chatId},new ProductMapper());}
    public List<Product> getListBasket(Long chatId){return jdbcTemplate.query("select * from get_basket_list(?)",new Object[]{chatId},new ProductMapper());}

    public boolean add_order(long chatId, String product){

//            int update = jdbcTemplate.update("select * from add_order(?,?)",new Object[]{chatId,product});
//            return update>0;
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withFunctionName("add_order");
            SqlParameterSource in = new MapSqlParameterSource().addValue("i_user", chatId).addValue("i_product", product);
            Boolean aBoolean = jdbcCall.executeFunction(boolean.class, in);
            System.out.println(aBoolean);
            return aBoolean;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean buyProduct(long chatId, int productId) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withFunctionName("buy_product");
            SqlParameterSource in = new MapSqlParameterSource().addValue("i_chat_id", chatId).addValue("i_product_id", productId);
            return jdbcCall.executeFunction(boolean.class,in);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(long chatId, String product){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withFunctionName("deleteproduct_order");
        SqlParameterSource in = new MapSqlParameterSource().addValue("i_chat_user", chatId).addValue("i_product", product);
        return jdbcCall.executeFunction(boolean.class, in);

    }
}
