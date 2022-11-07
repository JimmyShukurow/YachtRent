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
        assertThat(exits).isInstanceOf(RoleEntity.class);
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
    void itShouldGetAllRolesOfGivenUserIdFromTable() {
        //given
        Long user_id = 1L;
        List<RoleEntity> roleEntities = new ArrayList<>();
        var roleEntity1 = new RoleEntity(1L, "Test1");
//        var roleEntity2 = new RoleEntity(2L, "Test2");
        roleEntities.add(roleEntity1);
//        roleEntities.add(roleEntity2);
        var userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(roleEntity1.getId());
        userRoleEntity.setUserId(user_id);
        userRoleRepository.save(userRoleEntity);
        //when
        var roles = underTest.findRolesOfUser(user_id).get();
        System.out.println(roles);
        //then

        verify(userRoleRepository).save(userRoleEntity);
        assertThat(roles).isEqualTo(roleEntities);


    }
}