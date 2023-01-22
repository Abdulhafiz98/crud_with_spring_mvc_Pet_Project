package org.example.dao;

import org.example.dao.mapper.UserMapper;
import org.example.dto.UserLoginRequest;
import org.example.model.User;
import org.example.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
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
        try{
            return jdbcTemplate.queryForObject(
                    "select * from users where id = ? ",
                    new Object[]{id},
                    new UserMapper());
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    @Override
    public List<User> getList() {
        return jdbcTemplate.query("select * from users", new UserMapper());
    }
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("delete from users where id = ?", id) > 0;
    }
    @Override
    public boolean add(User user) {
        int update=0;
try {

    update = jdbcTemplate.update(
            "insert into users(name, email, password, phone_number, role) values (?,?,?,?,?)",
            user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), UserRole.USER.name());
}
catch (Exception ex){
    ex.printStackTrace();
}
return update>0;

    }
    public User login(final UserLoginRequest userLoginRequest) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from users where phone_number = ? and password = ? ",
                    new Object[]{userLoginRequest.getPhoneNumber(), userLoginRequest.getPassword()},
                    new UserMapper());
        }catch (Exception e){
            return null;
        }
    }
    public List<User> getUserList() {
        return jdbcTemplate.query("select * from users", new UserMapper());
    }

    public boolean registerUser(long chatId, String phoneNumber) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withFunctionName("register_user");
            SqlParameterSource in = new MapSqlParameterSource().addValue("i_chat_id", chatId).addValue("i_phone_number", phoneNumber);
            Boolean aBoolean = jdbcCall.executeFunction(boolean.class, in);
            System.out.println(aBoolean);
            return aBoolean;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(User user){
        return jdbcTemplate.update("update users set name = ?, password = ? where id = ?;",
                user.getName(),user.getPassword(),user.getId()) > 0;
    }
}
