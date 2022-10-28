package com.example.yachtRent.service;

import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.repository.RoleRepository;
import com.example.yachtRent.repository.UserRepository;
import com.example.yachtRent.repository.UserRoleRepository;
import com.example.yachtRent.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@WebMvcTest(UserService.class)
class UserServiceTest {

    @Autowired
    private  UserService userService;

    @MockBean
    private  UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @Test
    void itShouldAuthonticate() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin@gmail.com");
        loginRequest.setPassword("123456");
        userService.authonticate(loginRequest);

        verify(userRepository).findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
    }
}