package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.dao.OrderDao;
import org.example.dao.ProductDao;
import org.example.dto.response.OrderDto;
import org.example.dto.response.OrderItemDto;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.OrderStatus;
import org.example.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final ProductDao productDao;
    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao,ProductDao productDao) {
        this.productDao=productDao;
        this.orderDao=orderDao;
    }
    public List<Order> getList() {
        return orderDao.getList();
    }

    public List<OrderItemDto> getOrderItemList(int id) {
        List<OrderItem> orderItemList = orderDao.getOrderItemList(id);
        List<Product> productList = productDao.getList();

        return orderItemList.stream().parallel().map((orderItem) -> {
            Product product1 = productList.stream()
                    .filter(
                            (product -> orderItem.getProductId() == product.getId())
                    ).findFirst()
                    .orElse(null);

            return new OrderItemDto(
                    product1 == null ? "" : product1.getName(),
                    product1 == null ? 0 : product1.getPrice(),
                    orderItem.getQuantity()
            );
        }).toList();
    }

    public boolean editRole(int orderId, int statusNumber) {
        String status;
        if (statusNumber == 1)
            status = OrderStatus.ACCEPT.name();
        else
            status = OrderStatus.REJECT.name();
        return orderDao.editStatus(orderId, status);
    }

    public List<OrderDto> sortOrders(int sortNumber) {
        return orderDao.getOrdersBySort(sortNumber);
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
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        order.setOrderItems(orderItemList);
        order.setUserId(userId);
        return order;
    }
    public boolean addOrder(List<Integer> productIdList , HttpServletRequest request){
        Order order = createOrder(productIdList, request);
        return orderDao.add(order);
    }
}
