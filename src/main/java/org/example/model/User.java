package org.example.model;

import lombok.*;
import org.springframework.jdbc.core.RowMapper;

@AllArgsConstructor
@NoArgsConstructor
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
    private String firstName;
    private String lastName;
    private  String address;
    private  String city;

}
