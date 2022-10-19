package com.example.yachtRent.model;

import com.example.yachtRent.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private String fullName;
    private String username;
    private String token;

    public static User toModel(UserEntity userEntity) {
        var user = new User();
        user.setFullName(userEntity.getFirstName() + " " + userEntity.getLastName());
        user.setUsername(userEntity.getUsername());
        user.setToken(userEntity.getId()+"_"+userEntity.getToken());
        return user;
    }
}
