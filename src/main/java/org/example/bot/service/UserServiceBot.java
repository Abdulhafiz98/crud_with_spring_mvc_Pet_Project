package org.example.bot.service;

import org.example.bot.utils.BaseBot;
import org.example.dao.UserDao;
import org.example.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.meta.api.objects.Contact;

import javax.sql.DataSource;

public class UserServiceBot extends BotService {
    public boolean checkUser(long chatId, Contact contact){
        UserDao userDao = new UserDao(new JdbcTemplate(dataSource()));
        if (contact == null){
            User user = userDao.getUserList().stream().filter((a) -> a.getChatId() == chatId).findFirst().orElse(null);
            if(user != null){
                return true;
            }else {
                return false;
            }
        }else{
            User user = userDao.getUserList().stream().filter((a) -> a.getPhoneNumber().contains(contact.getPhoneNumber())).findFirst().orElse(null);
            if (user != null){
                return new UserDao(new JdbcTemplate(dataSource())).registerUser(chatId,contact.getPhoneNumber());
            }
        }
        return false;
    }
}
