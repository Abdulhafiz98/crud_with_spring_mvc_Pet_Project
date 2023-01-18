package org.example.service;

import org.example.dao.FavoriteProductDao;
import org.example.model.FavoriteProduct;
import java.util.List;

public class FavoriteProductService {

    private FavoriteProductDao favoriteProductDao;

    public List<FavoriteProduct> getUserFavorites(int userId) {
        List<FavoriteProduct> favoriteProductList = favoriteProductDao.getList();
        return favoriteProductList.stream()
                .filter(favoriteProduct ->
                        favoriteProduct.getUserId() == userId)
                .toList();
    }

}
