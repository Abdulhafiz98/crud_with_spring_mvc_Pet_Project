package org.example.dao.mapper;

import org.example.model.User;
import org.example.model.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .userRole(UserRole.valueOf(rs.getString("role")))
                .phoneNumber(rs.getString("phone_number"))
                .chatId(rs.getLong("chat_id"))
                .build();
    }
}
