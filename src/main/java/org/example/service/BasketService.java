package org.example.service;

import org.example.dao.ProductDao;
import org.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public class BasketService {
    private ProductDao productDao;

    public BasketService() {
    }

    @Autowired
    public BasketService(ProductDao productDao) {
        this.productDao = productDao;
    }

}
