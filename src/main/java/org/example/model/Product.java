package org.example.model;

import lombok.*;

import java.util.List;

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
    private List<Info> info;
    private int categoryId;
    private double discount;
}
