package org.example.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String phoneNumber;
    private String password;

}
