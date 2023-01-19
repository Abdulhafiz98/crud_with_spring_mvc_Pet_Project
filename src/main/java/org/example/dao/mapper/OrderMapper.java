package org.example.dao.mapper;
import org.example.model.Order;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = Order.builder()
                .id(rs.getLong("id"))
                .userId(rs.getInt("user_id"))
                .status(rs.getString("status"))
                .orderTime(rs.getString("order_time"))
                .build();
        return order;
    }
}
