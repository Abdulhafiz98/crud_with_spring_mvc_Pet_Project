package org.example.dto;

import lombok.Data;

@Data
public class CategoryRequest {
    private String name;
    private String parentName;
}
