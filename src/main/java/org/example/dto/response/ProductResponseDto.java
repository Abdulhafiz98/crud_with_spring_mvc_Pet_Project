package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.model.Info;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProductResponseDto {

    private String name;
    private String productUrl;
    private double price;
    private int quantity;
//    private List<Info> info;
    private String info;
    private String categoryName;
    private double discount;
}
