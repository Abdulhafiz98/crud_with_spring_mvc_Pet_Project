package org.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Favorite {
    private int id;
    private int productId;
    private int userId;
}
