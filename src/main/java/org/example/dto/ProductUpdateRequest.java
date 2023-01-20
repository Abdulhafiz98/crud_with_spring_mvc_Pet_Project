package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Product;


@Data

public class ProductUpdateRequest extends Product {
    private int id;
    private String name;
    private String productUrl;
    private double price;
    private int quantity;
    private String info;
    private String categoryName;

}
