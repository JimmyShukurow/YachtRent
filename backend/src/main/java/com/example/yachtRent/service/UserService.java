package com.example.yachtRent.service;

import com.example.yachtRent.config.AuthConfiguration;
import com.example.yachtRent.entity.RoleEntity;
import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.entity.UserRoleEntity;
import com.example.yachtRent.exception.InvalidCredentialsException;
import com.example.yachtRent.exception.RoleAlreadyAddedToUserException;
import com.example.yachtRent.exception.RoleIsMissingException;
import com.example.yachtRent.exception.UserIsMissingException;
import com.example.yachtRent.repository.RoleRepository;
import com.example.yachtRent.repository.UserRepository;
import com.example.yachtRent.repository.UserRoleRepository;
import com.example.yachtRent.request.LoginRequest;
import com.example.yachtRent.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private UserRoleRepository userRoleRepository;
    private AuthConfiguration authConfiguration;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, AuthConfiguration authConfiguration) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.authConfiguration = authConfiguration;
    }

    public UserEntity register(RegisterRequest registerRequest) {
        var userEntity = new UserEntity();
        userEntity.setFirstName(registerRequest.getFirstName());
        userEntity.setLastName(registerRequest.getLastName());
        userEntity.setUsername(registerRequest.getUsername());
        userEntity.setPassword(hashPassword(registerRequest.getPassword()));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setToken(generateToken());


        userRepository.save(userEntity);
        return userEntity;
    }

    public String hashPassword(String rawPassword) {
        var spec = new PBEKeySpec(rawPassword.toCharArray(), authConfiguration.getSalt().getBytes(), 65536, 128);

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

    public UserEntity authonticate(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new InvalidCredentialsException();
        }
        var hashedPassword = hashPassword(loginRequest.getPassword());
        var userEntity = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), hashedPassword);
        if (userEntity.isEmpty()) {
            throw new UserIsMissingException();
        }
        var entity = userEntity.get();
        entity.setToken(generateToken());
        userRepository.save(entity);

        return userEntity.get();
    }

    public List<UserEntity> getAll() {
        return (List)userRepository.findAll();
    }

    public String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[16];
        secureRandom.nextBytes(token);

        return Base64.getEncoder().encodeToString(token);
    }

    public void logout(Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserIsMissingException();
        }
        var userEntity = user.get();
        userEntity.setToken("");
        userRepository.save(userEntity);
    }

    public void validateToken(Long id, String token) {
        var user = userRepository.findByIdAndToken(id, token);
        if (user.isEmpty()) {
            throw new InvalidCredentialsException();
        }
    }

    public void saveRole(String name) {
        var role = new RoleEntity();
        role.setName(name);
        roleRepository.save(role);
    }

    public void addRoleToUser(Long userId, Long roleId) {
        var user = userRepository.findById(userId);
        var role = roleRepository.findById(roleId);
        var roleUser = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
        if (user.isEmpty()) {
            throw new UserIsMissingException();
        }
        if (roleUser.isPresent()) {
            throw new RoleAlreadyAddedToUserException();
        }

        var userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId);
        userRoleEntity.setRoleId(roleId);
        userRoleRepository.save(userRoleEntity);
    }

    public boolean validateRole(Long user_id, String roleName) {
        var role = roleRepository.findByName(roleName);
        if(role == null){
            throw new RoleIsMissingException();
        }
        var user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            throw new UserIsMissingException();
        }
        var entity = userRoleRepository.findByUserIdAndRoleId(user_id, role.getId());
        if (entity.isEmpty()) {
            return false;
        }

        return true;
    }

}
