package com.example.yachtRent.controller;

import com.example.yachtRent.annotation.ApiPrefixController;
import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.model.User;
import com.example.yachtRent.request.AdminRegistrationRequest;
import com.example.yachtRent.request.LoginRequest;
import com.example.yachtRent.request.RegisterRequest;
import com.example.yachtRent.request.UserRoleRequest;
import com.example.yachtRent.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@ApiPrefixController
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("users/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {

        var userEntity = userService.register(registerRequest);
        return ResponseEntity.ok(User.toModel(userEntity));
    }

    @PostMapping("users/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {

        var userEntity = userService.authonticate(loginRequest);
        return ResponseEntity.ok(User.toModel(userEntity));

    }

    @GetMapping("users/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        var users = userService.getAll();

        return ResponseEntity.ok(users);
    }

    @PostMapping("users/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        var id = (Long) request.getAttribute("userId");
        userService.logout(id);
        return ResponseEntity.ok("Bye bye");
    }

    @PutMapping("users/add-role")
    public ResponseEntity<String> giveRoleToUser(@RequestBody UserRoleRequest userRoleRequest) {
        userService.addRoleToUser(userRoleRequest.getUserId(), userRoleRequest.getRoleId());
        return ResponseEntity.ok("Role has been given to use");
    }

    @PostMapping("users/send-email")
    public ResponseEntity<String> sendRegistrationMail(@RequestBody AdminRegistrationRequest registrationRequest) {
        var response = userService.sendLinkToUser(registrationRequest.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("users/check-link/{hash}")
    public ResponseEntity<String> chechHashOfRegistrationLink(@PathVariable String hash) {
        userService.checkIfHashExists(hash);
        return ResponseEntity.ok("Hash is past check");
    }


}
