package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dao.FavoriteProductDao;
import org.example.model.FavoriteProduct;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
