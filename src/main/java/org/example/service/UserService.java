package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUserList(){
        return userDao.getList();
    }

    public boolean deleteUser(int id){
        return userDao.delete(id);
    }

    public User getUser(int id){
        return userDao.getById(id);
    }


    public Integer getUserIdFromSession(HttpServletRequest request){
        Integer user_id = (Integer) request.getSession().getAttribute("userId");
        return user_id;
    }
}
