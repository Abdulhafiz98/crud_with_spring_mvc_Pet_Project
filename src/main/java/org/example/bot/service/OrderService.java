package org.example.bot.service;

import org.example.dao.ProductDao;
import org.example.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OrderService extends BotService{
    public List<SendPhoto> getOrderList(Long chatId){
        List<SendPhoto> sendPhotosList=new ArrayList<>();
        ProductDao productDao = new ProductDao(new JdbcTemplate(dataSource()));
        List<Product> listOrder = productDao.getListOrder(chatId);
        for (Product product: listOrder) {
            if (product != null) {
                String name=product.getName();
                File file = new File("C:\\Users\\HP\\Documents\\B24_Bootcamp\\web\\WEB-INF\\image\\"+"a.png");
                InputFile inputFile=new InputFile(file,name);
                SendPhoto sendPhoto=new SendPhoto();
                System.out.println(file);
                System.out.println(inputFile);
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setCaption(
                        "Name : "+product.getName()+"\n"+
                                "Price : "+product.getPrice()+"\n"+
                                "Info : "+product.getInfo()
                );
                //sendPhoto.setReplyMarkup(BotService.buildInlineMarkup(List.of("buy","sel"),2));
                sendPhotosList.add(sendPhoto);
            }
        }
        return sendPhotosList;
    }
}
