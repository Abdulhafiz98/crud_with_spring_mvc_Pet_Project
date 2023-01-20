package org.example.service;

import org.example.dao.CategoryDao;
import org.example.dao.ProductDao;
import org.example.dto.response.ProductResponseDto;
import org.example.model.Category;
import org.example.model.Info;
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
    public List<Info> getInfo(int id) {
     return productDao.getInfoList(id);
    }

    public boolean addProduct(Product product) {
        return productDao.add(product);
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
                    product.getName(),
                    product.getProductUrl(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getInfo(),
                    category1 == null ? "" : category1.getName(),
                    product.getDiscount()
            );
        }).toList();
    }
    public List<Product> getProductCategoryIdList(int id){
       return productDao.getProductCategoryIdList(id);
    }

}
