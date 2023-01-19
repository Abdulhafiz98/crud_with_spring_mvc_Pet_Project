package org.example.dao;

import org.example.dao.mapper.OrderItemMapper;
import org.example.dao.mapper.OrderMapper;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class OrderDao implements BaseDao<Order> {
    JdbcTemplate jdbcTemplate;

    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order getById(int id) {
        Order order = jdbcTemplate.queryForObject("select * from orders where id = ?", new Object[]{id}, new OrderMapper());
        List<OrderItem> orderItemList = jdbcTemplate.query("select * from order_item where order_id = ?", new Object[]{order.getId()}, new OrderItemMapper());
        order.setOrderItems(orderItemList);
        return order;
    }

    @Override
    public List<Order> getList() {
        List<Order> orderList = jdbcTemplate.query("select * from orders", new OrderMapper());
        List<OrderItem> orderItemList = jdbcTemplate.query("select * from order_item ", new OrderItemMapper());

        return orderList.stream().parallel().map((order) -> {
            OrderItem orderItem1 = orderItemList.stream()
                    .filter(
                            (orderItem -> order.getId() == orderItem.getOrderId())
                    ).findFirst()
                    .orElse(null);

            return new Order(
                    order.getId(),
                    orderItemList,
                    order.getUserId(),
                    order.getStatus(),
                    order.getOrderTime()
            );
        }).toList();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean add(Order order) {
        boolean addOrderItem;
        if (jdbcTemplate.update(
                "insert into orders(user_id) values (?)",
                new Object[]{order.getUserId()}
        ) > 0) {
            for (int i = 0; i < order.getOrderItems().size(); i++) {
                addOrderItem = jdbcTemplate.update(
                        "insert into order_item(product_id,order_id,quantity) values (?,?,?)",
                        new Object[]{order.getOrderItems().get(i).getProductId(),
                                order.getOrderItems().get(i).getOrderId(),
                                order.getOrderItems().get(i).getQuantity()
                        }) > 0;
                if (!addOrderItem) return false;
            }
        }
        return true;
    }

    public List<OrderItem> getOrderItemList(int id) {
        return getById(id).getOrderItems();
    }

    public boolean editStatus(int id, String status) {
        return jdbcTemplate.update(
                "update orders set (?) where id = (?)",
                new Object[]{id,status}
        ) > 0;
    }
}
