package org.example.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseModel {
    private String name;
    private String productUrl;
    private double price;
    private int quantity;
    private String info;

    private int categoryId;
}
