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
                .phoneNumber(rs.getString("phone_number"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .chatId(rs.getLong("chat_id"))
                .userRole(UserRole.valueOf(rs.getString("role")))
                .build();
    }
}
