package com.example.yachtRent.config;

import org.springframework.context.annotation.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class SecurityConfiguration {
    private static final String USER_PATH = "/api/v1/users/**";
    private static final String YACHT_PATH = "/api/v1/yachts/**";
    public boolean allowedPattern(String path, String pattern) {
        pattern = pattern.replace("**", ".+");
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(path);

        return matcher.matches();

    }






}
