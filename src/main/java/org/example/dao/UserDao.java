package org.example.dao;

import org.example.dao.mapper.UserMapper;
import org.example.dto.UserLoginRequest;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        return jdbcTemplate.queryForObject("select * from mvc_user where id = ?", new Object[]{id}, new UserMapper());
    }

    @Override
    public List<User> getList() {
        return jdbcTemplate.query("select * from mvc_user", new UserMapper());
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("delete from mvc_user where id = ?", new Object[]{id}) > 0;
    }

    @Override
    public boolean add(User user) {
        return jdbcTemplate.update(
                "insert into mvc_user(name, email, password) values (?,?,?)",
                new Object[]{user.getName(), user.getEmail(), user.getPassword()}
        ) > 0;
    }

    public User login(final UserLoginRequest userLoginRequest) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from mvc_user where name = ? and password = ? ",
                    new Object[]{userLoginRequest.getName(), userLoginRequest.getPassword()},
                    new UserMapper());
        }catch (Exception e){
            return null;
        }
    }
}
