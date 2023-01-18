package org.example.service;

import org.example.dao.OrderDao;
import org.example.dao.ProductDao;
import org.example.dto.response.OrderItemDto;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.Product;
import java.util.List;

public class OrderService {
    private final ProductDao productDao;
    private final OrderDao orderDao;

    public OrderService(ProductDao productDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.orderDao = orderDao;
    }
    public List<Order> getList(){
        return orderDao.getList();
    }

    public List<OrderItemDto> getOrderItemList(int id) {
        List<OrderItem> orderItemList = orderDao.getOrderItemList(id);
        List<Product> productList = productDao.getList();

        return orderItemList.stream().parallel().map((orderItem) -> {
            Product product1 = productList.stream()
                    .filter(
                            (product -> product.getId() == orderItem.getProductId())
                    ).findFirst()
                    .orElse(null);

            return new OrderItemDto(
                    product1 == null ? "" : product1.getName(),
                    orderItem.getQuantity()
            );
        }).toList();
    }
}
