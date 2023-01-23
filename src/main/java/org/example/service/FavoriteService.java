package org.example.service;

import lombok.NoArgsConstructor;
import org.example.dao.FavoriteDao;
import org.example.model.Favorite;
import org.example.model.Product;

import java.util.List;

public class FavoriteService {

    private final FavoriteDao favoriteDao;

    public FavoriteService(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public List<Product> getUserFavorites(int userId) {
        return favoriteDao.getUserfavorites(userId);
    }

    public boolean addOrDelete(int productId, int userId){
        return favoriteDao.addOrDeleteFavorite(productId,userId);
    }
}
