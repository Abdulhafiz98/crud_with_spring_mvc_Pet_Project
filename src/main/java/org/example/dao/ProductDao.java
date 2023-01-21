package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dao.mapper.ProductMapper;
import org.example.model.Info;
import org.example.model.Product;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
public class ProductDao implements BaseDao<Product> {


    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product getById(int id) {
        List<Product> productList = jdbcTemplate.query("select * from product where id = ?", new Object[]{id}, new ProductMapper());
        return productList.stream().findFirst().orElse(null);
    }

    public List<Info> getInfoList(int i_id){
        Product product = jdbcTemplate.queryForObject("select * from product where id=?", new Object[]{i_id}, new ProductMapper());
        return product.getInfo();
    }
    @Override
    public List<Product> getList() {
        return jdbcTemplate.query("select * from product", new ProductMapper());
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public List<Product> getProductListByName(String productName){
        return jdbcTemplate.query("select * from product where product.name ilike ?", new Object[]{productName}, new ProductMapper());
    }

    @Override
    public boolean add(Product product) {
        String s = new Gson().toJson(product.getInfo());
        return jdbcTemplate.update(
                "insert into product(created_by, created_date, updated_date, name, product_url,price, quantity, info, category_id) values (?,?,?,?,?,?,?,?,?)",
                new Object[]{product.getCreatedBy(),product.getCreatedDate(), product.getUpdatedDate(), product.getName(), product.getProductUrl(), product.getPrice(), product.getQuantity(),product.getInfo(),product.getCategoryId()}
        ) > 0;
           //  check must have into database   "insert into product(name, product_url, price, quantity, category_id, info) values (?,?,?,?,?,?)",
              //  product.getName(), product.getProductUrl(), product.getPrice(), product.getQuantity(), product.getCategoryId(), product.getInfo()) > 0;

    }

    public List<Product> getProductCategoryIdList(String  name) {
        return jdbcTemplate.query("select * from product where name=?", new Object[]{name}, new ProductMapper());
    }
    public List<Product> getListOrder(Long chatId){return jdbcTemplate.query("select * from get_order_list(?)",new Object[]{chatId},new ProductMapper());}
    public List<Product> getListBasket(Long chatId){return jdbcTemplate.query("select * from get_basket_list(?)",new Object[]{chatId},new ProductMapper());}

    public boolean add_order(long chatId, String product){
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

    public boolean deleteProduct(long chatId, String product){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withFunctionName("delete_product_order");
        SqlParameterSource in = new MapSqlParameterSource().addValue("i_chat_user", chatId).addValue("i_product", product);
        return jdbcCall.executeFunction(boolean.class, in);

    }
    public List<Product> getProductCategoryIdList(int id){
        return jdbcTemplate.query("select * from product p where p.category_id = ?",new Object[]{id}, new ProductMapper());

    }

    public List<Product> getProductList(int categoryId, int pageCount){
        return jdbcTemplate.query("select * from get_product(?,?)",new Object[]{categoryId,pageCount},new ProductMapper());
    }

    public boolean addFavourites(long userChatId, int productId){
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withFunctionName("add_favourites");
            SqlParameterSource in = new MapSqlParameterSource().addValue("i_chat_id",userChatId).addValue("i_product_id",productId);
            return jdbcCall.executeFunction(boolean.class,in);
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
}
