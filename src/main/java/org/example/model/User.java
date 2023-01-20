package org.example.model;

import lombok.*;
import org.springframework.jdbc.core.RowMapper;

@Getter
@Setter
@Builder
@ToString
public class User {
    private int id;
    private String name;
    private String phoneNumber;
    private String password;
    private String email;
    private Long chatId;
    private UserRole userRole;
}
