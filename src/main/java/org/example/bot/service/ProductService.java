package org.example.bot.service;

import lombok.Data;
import org.example.dao.ProductDao;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.File;
import java.util.List;
public class ProductService extends BotService{
    public List<Product> getProductList(int parentId, int pageNumber) {
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        return productDao.getProductList(parentId, pageNumber);
    }
    public Product getProductById(int productId){
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        Product byId = productDao.getById(productId);
        return byId;
    }

    public SendPhoto getProductInfo(long chatId,int productId){
        Product product = getProductById(productId);
        File file=new File("web/WEB-INF/image/" + product.getProductUrl());
        System.out.println(file);
        InputFile inputFile = new InputFile(file, product.getProductUrl());
        SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId),inputFile);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Product Name: " + product.getName() + "\n");
        stringBuilder.append("Product Price: " + product.getPrice() + "\n");
        stringBuilder.append("Product Info: " + product.getInfo() + "\n");
        stringBuilder.append("Product quantity: " + product.getQuantity());
        InlineKeyboardMarkup back = new InlineKeyboardMarkup(BotService.getBack(product.getCategoryId(), productId));
        sendPhoto.setReplyMarkup(back);
        sendPhoto.setCaption(stringBuilder.toString());

        return sendPhoto;
    }
    public boolean addFavourites(long chatId, int productId){
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        return productDao.addFavourites(chatId, productId);
    }
    public boolean buyProduct(long chatId, int productId){
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        return productDao.buyProduct(chatId, productId);
    }
}