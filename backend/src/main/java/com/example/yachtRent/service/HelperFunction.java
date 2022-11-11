package com.example.yachtRent.service;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

public class HelperFunction {

    @Value("${auth.salt}")
    private  String AUTH_SALT ;
    public boolean checkPasswordComplexity(String rawPassword) {
        return rawPassword.length() > 7 &&
                rawPassword.chars().anyMatch(Character::isLowerCase) &&
                rawPassword.chars().anyMatch(Character::isUpperCase) &&
                rawPassword.chars().anyMatch(Character::isDigit);
    }

    public String generateRandomAlphaNumeric() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[16];
        secureRandom.nextBytes(token);

        return Base64.getEncoder().encodeToString(token);
    }
    public String hashString(String rawPassword) {
        var spec = new PBEKeySpec(rawPassword.toCharArray(), AUTH_SALT.getBytes(), 65536, 128);

        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return new String(hash);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
