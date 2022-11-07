package com.example.yachtRent.repository;

import com.example.yachtRent.entity.RoleEntity;
import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.entity.UserRoleEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository underTest;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    @Disabled
    void ItShouldGetDataFromUserRolesTable() {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Jimi");
        userEntity.setLastName("Shukurov");
        userEntity.setUsername("Neo");
        userEntity.setPassword("123456");
        userEntity.setCreatedAt(LocalDateTime.now());
        var userRepo = userRepository.save(userEntity);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("Test");
        roleRepository.save(roleEntity);
        var roleRepo = roleRepository.findByName("Test");

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(roleRepo.getId());
        userRoleEntity.setUserId(userRepo.getId());
        underTest.save(userRoleEntity);

        //when
        var exists = underTest.findByUserIdAndRoleId(userRepo.getId(), roleRepo.getId()).get();

        //then
        assertThat(exists).isEqualTo(userRoleEntity);
    }
}