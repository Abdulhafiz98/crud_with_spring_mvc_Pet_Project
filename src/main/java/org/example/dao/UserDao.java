package org.example.dao;

import org.example.dao.mapper.UserMapper;
import org.example.dto.UserLoginRequest;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

public class UserDao implements BaseDao<User> {

    private JdbcTemplate jdbcTemplate;

    public UserDao() {
    }

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(int id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, new UserMapper());
    }

    @Override
    public List<User> getList() {
        return jdbcTemplate.query("select * from users", new UserMapper());
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("delete from users where id = ?", new Object[]{id}) > 0;
    }

    @Override
    public boolean add(User user) {
        return jdbcTemplate.update(
                "insert into users(name, email, password) values (?,?,?)",
                new Object[]{user.getName(), user.getEmail(), user.getPassword()}
        ) > 0;
    }

    public User login(final UserLoginRequest userLoginRequest) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from users where name = ? and password = ? ",
                    new Object[]{userLoginRequest.getName(), userLoginRequest.getPassword()},
                    new UserMapper());
        }catch (Exception e){
            return null;
        }
    }
}
