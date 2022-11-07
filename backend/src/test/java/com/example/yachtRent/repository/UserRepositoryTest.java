package com.example.yachtRent.repository;

import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @MockBean
    private UserService userService;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldGetUserByUsernameAndPassword() {
        //given
        String username = "NEO", password = "underson";
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Jimi");
        userEntity.setLastName("Test");
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setCreatedAt(LocalDateTime.now());
        underTest.save(userEntity);
        //when
        var exists = underTest.findByUsernameAndPassword(username, password).get();
        //then
        assertThat(exists).isEqualTo(userEntity);

    }
    @Test
    void itDoesNotGetUserByUsernameAndPassword() {
        //given
        String username = "NEO";
        String password = "underson";
        //when
        var exists = underTest.findByUsernameAndPassword(username, password);
        //then
        assertThat(exists).isEmpty();

    }

    @Test
    void itShouldFindUserByIdAndToken() {
        //given
        var token = userService.generateToken();
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Neo");
        userEntity.setLastName("Underson");
        userEntity.setUsername("Jimi");
        userEntity.setPassword("123456");
        userEntity.setToken(token);
        userEntity.setCreatedAt(LocalDateTime.now());
        var result = underTest.save(userEntity);
        //when
        var exists = underTest.findByIdAndToken(result.getId(), token).get();
        //then
        assertThat(exists).isEqualTo(userEntity);
    }
    @Test
    void itDoesntFindUserByIdAndToken() {
        //given
        var token = userService.generateToken();
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Neo");
        userEntity.setLastName("Underson");
        userEntity.setUsername("Jimi");
        userEntity.setPassword("123456");
        userEntity.setToken(token);
        userEntity.setCreatedAt(LocalDateTime.now());
        var result = underTest.save(userEntity);
        //when
        var newToken = userService.generateToken();
        var exists = underTest.findByIdAndToken(2L, newToken);
        //then
        assertThat(exists).isEmpty();
    }


}