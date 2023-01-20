package org.example.bot.service;

import org.example.dao.ProductDao;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BasketService extends BotService{

    public List<Product> getOrderList(Long chatId){
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        List<Product> listOrder = productDao.getListOrder(chatId);
        return listOrder;
    }
    public List<Product> getBasketList(Long chatId){
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        List<Product> listBasket = productDao.getListBasket(chatId);
        return listBasket;
    }
    public void put_buy(Update update, String back){
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        productDao.add_order(update.getCallbackQuery().getFrom().getId(), back.substring(5));
    }
    public void deleteProduct(Update update, String back){
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        productDao.deleteProduct(update.getCallbackQuery().getFrom().getId(),back.substring(3));
    }
}
