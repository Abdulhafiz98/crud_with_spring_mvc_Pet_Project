package org.example.dao.mapper;
import org.example.model.Order;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = Order.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .status(rs.getString("status"))
                .build();
        return order;
    }
}
