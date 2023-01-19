package org.example.dao;

import org.example.dao.mapper.ProductMapper;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;

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
    public List<Product> getProductCategoryIdList(int id){
        return jdbcTemplate.query("select * from get_product_category_id(?)",new Object[]{id}, new ProductMapper());
    }
}
