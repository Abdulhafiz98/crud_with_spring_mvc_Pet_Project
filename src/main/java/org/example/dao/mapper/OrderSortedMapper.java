package org.example.dao.mapper;

import org.example.dto.response.OrderDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderSortedMapper implements RowMapper<OrderDto> {
    @Override
    public OrderDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDto orderDto = OrderDto.builder()
                .id(rs.getInt("id"))
                .userName(rs.getString("name"))
                .phoneNumber(rs.getString("phone_number"))
                .productQuantity(rs.getInt("product_quantity"))
                .status(rs.getString("status"))
                .orderTime(rs.getString("order_time"))
                .build();
        return orderDto;
    }
}
