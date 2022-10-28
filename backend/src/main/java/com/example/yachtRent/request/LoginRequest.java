package com.example.yachtRent.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class LoginRequest {
    private String username;
    private String password;
}
