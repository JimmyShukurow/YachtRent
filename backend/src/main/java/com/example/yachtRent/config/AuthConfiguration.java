package com.example.yachtRent.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("auth")
@Getter
@Setter
@NoArgsConstructor
public class AuthConfiguration {
    private String salt;
}
