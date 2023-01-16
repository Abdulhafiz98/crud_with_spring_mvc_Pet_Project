package org.example.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private long id;
    private List<OrderItem> orderItems;
    private int userId;

}
