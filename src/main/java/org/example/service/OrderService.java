package org.example.service;

import org.example.dao.OrderDao;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
private OrderDao orderDao ;
    public OrderService() {
    }

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    private Order createOrder(List<Integer> productIdList , HttpServletRequest request) {
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        for (Integer integer : productIdList) {
            orderItem.setQuantity(1);
            orderItem.setProductId(integer);
            orderItemList.add(orderItem);
            orderItem = new OrderItem();
        }
        order.setOrderItems(orderItemList);
        order.setUserId(getUserIdFromSession(request));
        return order;
    }
    private Integer getUserIdFromSession(HttpServletRequest request){
        Integer user_id = (Integer) request.getSession().getAttribute("userId");
        return user_id;
    }
    public boolean addOrder(List<Integer> productIdList , HttpServletRequest request){
        Order order = createOrder(productIdList, request);
        return orderDao.add(order);

    }

}
