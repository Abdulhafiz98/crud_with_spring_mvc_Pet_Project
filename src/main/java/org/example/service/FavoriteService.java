package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dao.FavoriteDao;
import org.example.model.Favorite;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteService {

    private FavoriteDao favoriteDao;

    public List<Favorite> getUserFavorites(int userId) {
        List<Favorite> favoriteProductList = favoriteDao.getList();
        return favoriteProductList.stream()
                .filter(favoriteProduct ->
                        favoriteProduct.getUserId() == userId)
                .toList();
    }

    public boolean addFavorite(Favorite favorite){
        return favoriteDao.add(favorite);
    }

    public boolean deleteFavorite(int id){
        return favoriteDao.delete(id);
    }
}
