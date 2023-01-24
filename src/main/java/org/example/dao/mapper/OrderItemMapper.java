package org.example.dao.mapper;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        OrderItem orderItem = OrderItem.builder()
                .productId(rs.getInt("product_id"))
                .quantity(rs.getInt("quantity"))
                .build();
        return orderItem;
    }
}
