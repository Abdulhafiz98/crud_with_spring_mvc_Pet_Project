package org.example.dto;

import lombok.Data;
import org.example.model.UserRole;

@Data
public class UserRegisterRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String role = UserRole.USER.name();

}
