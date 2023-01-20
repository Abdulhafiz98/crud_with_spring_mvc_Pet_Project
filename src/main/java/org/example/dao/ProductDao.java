package org.example.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dao.mapper.ProductMapper;
import org.example.model.Info;
import org.example.model.Product;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
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
        return null;
    }

    public List<Info> getInfoList(int i_id){
        Product product = jdbcTemplate.queryForObject("select * from product where id=?", new Object[]{i_id}, new ProductMapper());
        return product.getInfo();
    }
    @Override
    public List<Product> getList() {
        return jdbcTemplate.query("select * from product order by discount desc", new ProductMapper());
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
                "insert into product(name, product_url, price, quantity, category_id, info) values (?,?,?,?,?,?)",
                product.getName(), product.getProductUrl(), product.getPrice(), product.getQuantity(), product.getCategoryId(), product.getInfo()) > 0;

    }

    public List<Product> getProductCategoryIdList(String  name) {
        return jdbcTemplate.query("select * from product where name=?", new Object[]{name}, new ProductMapper());
    }
    public List<Product> getProductCategoryIdList(int id){
        return jdbcTemplate.query("select * from product p where p.category_id = ?",new Object[]{id}, new ProductMapper());

    }
}
