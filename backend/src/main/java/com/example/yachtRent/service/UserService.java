package com.example.yachtRent.service;

import com.example.yachtRent.entity.InvitationLinkEntity;
import com.example.yachtRent.entity.RoleEntity;
import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.entity.UserRoleEntity;
import com.example.yachtRent.exception.*;
import com.example.yachtRent.repository.InvitationLinkRepository;
import com.example.yachtRent.repository.RoleRepository;
import com.example.yachtRent.repository.UserRepository;
import com.example.yachtRent.repository.UserRoleRepository;
import com.example.yachtRent.request.LoginRequest;
import com.example.yachtRent.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserService extends HelperFunction {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InvitationLinkRepository invitationLinkRepository;
    private  final EmailSenderService emailSenderService;
    private final UserRoleRepository userRoleRepository;
    @Value("${frontend.url}")
    private String frontendURL;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository,
            EmailSenderService emailSenderService,
            InvitationLinkRepository invitationLinkRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.emailSenderService = emailSenderService;
        this.invitationLinkRepository = invitationLinkRepository;
    }

    public UserEntity register(RegisterRequest registerRequest) {
        var user = userRepository.findByUsername(registerRequest.getUsername());
        if (user.isPresent()) {
            throw new UsernameIsAlreadyTakenException();
        }
        if (checkPasswordComplexity(registerRequest.getPassword())) {
            throw new PasswordIsNotComplexException();
        }
        var userEntity = new UserEntity();
        userEntity.setFirstName(registerRequest.getFirstName());
        userEntity.setLastName(registerRequest.getLastName());
        userEntity.setUsername(registerRequest.getUsername());
        userEntity.setPassword(hashString(registerRequest.getPassword()));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setToken(generateToken());

        userRepository.save(userEntity);
        return userEntity;
    }

    public UserEntity authenticate(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new InvalidCredentialsException();
        }
        var hashedPassword = hashString(loginRequest.getPassword());
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

    public String sendLinkToUser(String toUser) throws Exception{
        var hashRandom = generateRandomAlphaNumeric();
        var entity = new InvitationLinkEntity();
        entity.setHash(hashRandom);
        entity.setExpireAt(OffsetDateTime.now().plusDays(1));
        entity.setCreatedAt(OffsetDateTime.now());
        invitationLinkRepository.save(entity);
        var subject = "Registration Mail";
        var body = "click this link to get to registration page \n" + frontendURL+"admin/register/"+ hashRandom;
        return emailSenderService.sendEmail(toUser, subject, body);
    }

    public List<String> getUserRoles(UserEntity userEntity) {
        var roles = roleRepository.findRolesOfUser(userEntity.getId()).get();
        return roles.stream().map(roleEntitie -> roleEntitie.getName()).collect(Collectors.toList());
    }

    public void checkIfHashExists(String hash) {
        var entity = invitationLinkRepository.findByHash(hash);
        var check = entity != null && entity.getExpireAt().isBefore(OffsetDateTime.now());
        if (check) {
            throw new RuntimeException();
        }

    }
}
