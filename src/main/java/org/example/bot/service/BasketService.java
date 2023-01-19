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


//    public List<SendPhoto> getOrderList(Long chatId){
//        List<SendPhoto> sendPhotosList=new ArrayList<>();
//        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
//
//        List<Product> listOrder = productDao.getListOrder(chatId);
//        for (Product product: listOrder) {
//            if (product != null) {
//                File file = new File("web/WEB-INF/image/" + product.getName());
//                InputFile inputFile=new InputFile(file,product.getName());
//                SendPhoto sendPhoto=new SendPhoto();
//                System.out.println(file);
//                System.out.println(inputFile);
//                sendPhoto.setChatId(chatId);
//                sendPhoto.setPhoto(inputFile);
//                sendPhoto.setCaption(
//                        "Name : "+product.getName()+"\n"+
//                        "Price : "+product.getPrice()+"\n"+
//                        "Info : "+product.getInfo()
//                );
//                sendPhoto.setReplyMarkup(BotService.buildInlineDifferentCallBack(List.of("\uD83D\uDED2 Buy", "❌"),List.of("Yes: "+product.getName(),"No:"+product.getName()),2));
//                sendPhotosList.add(sendPhoto);
//            }
//        }
//        return sendPhotosList;
//    }
    public List<Product> getOrderList(Long chatId){
//        List<SendPhoto> sendPhotosList=new ArrayList<>();
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
//        productDao.buyProduct(update.getCallbackQuery().getFrom().getId(), Integer.parseInt(back.substring(4)));
    }
    public void deleteProduct(Update update, String back){
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        productDao.deleteProduct(update.getCallbackQuery().getFrom().getId(),back.substring(3));
    }
}
