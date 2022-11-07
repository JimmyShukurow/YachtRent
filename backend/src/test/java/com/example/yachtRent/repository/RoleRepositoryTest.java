package com.example.yachtRent.repository;

import com.example.yachtRent.entity.RoleEntity;
import com.example.yachtRent.entity.UserRoleEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.management.relation.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository underTest;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void itShouldGetRoleByNameFromRolesTable() {
        //given
        String name = "TestRole";
        RoleEntity entity = new RoleEntity();
        entity.setName(name);
        underTest.save(entity);
        //when
        var exits = underTest.findByName(name);
        //then
        assertThat(exits).isEqualTo(entity);
    }
    @Test
    void itShouldSayThatRoleByNameDoesNotExistsInTheRolesTable() {
        //given
        String name = "TestRole";
        //when
        var exits = underTest.findByName(name);
        //then
        assertThat(exits).isNull();
    }


    @Test
    @Disabled
    void itShouldGetAllRolesOfGivenUserIdFromTable() {
        //given
        Long user_id = 9L;
        List<RoleEntity> roleEntities = new ArrayList<>();
        var roleEntity1 = new RoleEntity();
        roleEntity1.setName("Test1");
        var roleEntity2 = new RoleEntity();
        roleEntity2.setName("Test2");
        roleEntities.add(roleEntity1);
        roleEntities.add(roleEntity2);
        underTest.save(roleEntity1);
        underTest.save(roleEntity2);
        var userRoleEntity1 = new UserRoleEntity();
        userRoleEntity1.setUserId(user_id);
        userRoleEntity1.setRoleId(roleEntity1.getId());
        userRoleRepository.save(userRoleEntity1);
        var userRoleEntity2 = new UserRoleEntity();
        userRoleEntity2.setUserId(user_id);
        userRoleEntity2.setRoleId(roleEntity2.getId());
        userRoleRepository.save(userRoleEntity2);

        //when
        var roles = underTest.findRolesOfUser(user_id).get();

        //then
        assertThat(roles).isNotEmpty();


    }
}