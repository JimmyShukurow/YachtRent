package com.example.yachtRent.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AdminRegistrationRequest {
    private String email;
}
