package org.example.service;

import lombok.NoArgsConstructor;
import org.example.dao.CategoryDao;
import org.example.dao.ProductDao;
import org.example.dao.mapper.ProductMapper;
import org.example.dto.response.ProductResponseDto;
import org.example.model.Category;
import org.example.model.Product;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductDao productDao;
    private final CategoryDao categoryDao;

    public ProductService(ProductDao productDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }
    public  boolean update(Product product){
        return productDao.update(product);
    }
    public boolean addProduct(final ProductResponseDto responseDto) {
        Product product = Product.builder()
                .name(responseDto.getName())
                .productUrl(responseDto.getProductUrl())
                .price(responseDto.getPrice())
                .quantity(responseDto.getQuantity())
                .info(responseDto.getInfo())
                .categoryId(getCategory(responseDto.getCategoryName()))
                .build();
        return productDao.add(product);
    }

    public int getCategory(String name) {
        List<Category> categoryList = categoryDao.getList();
        for (Category category : categoryList) {
            if (category.getName().equals(name)) {
                return category.getId();
            }
        }
        return -1;
    }

    public List<ProductResponseDto> getProductList() {
        List<Product> productList = productDao.getList();
        List<Category> categoryList = categoryDao.getList();

        return productList.stream().parallel().map((product) -> {
            Category category1 = categoryList.stream()
                    .filter(
                            (category -> product.getCategoryId() == category.getId())
                    ).findFirst()
                    .orElse(null);

            return new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getProductUrl(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getInfo(),
                    category1 == null ? "" : category1.getName()
            );
        }).toList();
    }
    public boolean deleteUser(int id){
        return productDao.delete(id);
    }
    public Product getById(int id){return productDao.getById(id);}



}
