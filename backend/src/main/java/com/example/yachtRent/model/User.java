package com.example.yachtRent.model;

import com.example.yachtRent.entity.RoleEntity;
import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.repository.UserRoleRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class User {


    private String fullName;
    private String username;
    private String token;
    private List<String> roles;

    public static User toModel(UserEntity userEntity) {
        var user = new User();
        user.setFullName(userEntity.getFirstName() + " " + userEntity.getLastName());
        user.setUsername(userEntity.getUsername());
        user.setToken(userEntity.getId()+"_"+userEntity.getToken());
        var roles = userEntity.getRoles();
        user.setRoles(roles.stream().map(RoleEntity::getName).collect(Collectors.toList()));

        return user;
    }
}
