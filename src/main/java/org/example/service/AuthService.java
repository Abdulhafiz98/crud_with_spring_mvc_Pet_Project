package org.example.service;

import lombok.NoArgsConstructor;
import org.example.dao.UserDao;
import org.example.dto.UserLoginRequest;
import org.example.dto.UserRegisterRequest;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@NoArgsConstructor
public class AuthService {

    private UserDao userDao;

    @Autowired
    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean addUser(final UserRegisterRequest userRegisterRequest) {
        User user = User.builder()
                .name(userRegisterRequest.getName())
                .email(userRegisterRequest.getEmail())
                .password(userRegisterRequest.getPassword())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .build();

        return userDao.add(user);
    }

    public User login(final UserLoginRequest userLoginRequest) {
        return userDao.login(userLoginRequest);
    }



}
