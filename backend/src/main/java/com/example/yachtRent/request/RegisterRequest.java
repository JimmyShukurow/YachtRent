package com.example.yachtRent.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
