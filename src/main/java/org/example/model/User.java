package org.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

@Getter
@Setter
@Builder
public class User {
    private int id;
    private String name;
    private String phoneNumber;
    private String password;
    private String email;
    private Long chatId;
    private UserRole userRole;
    private String firstName;
    private String lastName;
    private  String address;
    private  String city;

}
