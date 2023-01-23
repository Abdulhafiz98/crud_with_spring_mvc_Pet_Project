package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class UserService {
    private final UserDao userDao;

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

    public User getUserById(HttpServletRequest request){
        Integer userIdFromSession = getUserIdFromSession(request);
        if (userIdFromSession!=null){
        User user = userDao.getById(userIdFromSession);
            return user;
        }
        return null;
    }
    
    public boolean updateUser(User user){
        return userDao.update(user);
    }
    
    public Integer getUserIdFromSession(HttpServletRequest request){
        Object userId = request.getSession().getAttribute("userId");
        if (userId!=null){
            return (Integer) userId;
        }
        return null;
    }
}
