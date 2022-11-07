package com.example.yachtRent.repository;

import com.example.yachtRent.entity.InvitationLinkEntity;
import com.example.yachtRent.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvitationLinkRepositoryTest {

    @Autowired
    private InvitationLinkRepository underTest;
    @MockBean
    private UserService userService;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldGetHashFromInvitationLinksTable() {
        //given
        InvitationLinkEntity entity = new InvitationLinkEntity();
        var testHash = userService.generateRandomAlphaNumeric();
        entity.setHash(testHash);
        entity.setExpireAt(OffsetDateTime.now().plusDays(1));
        entity.setCreatedAt(OffsetDateTime.now());
        underTest.save(entity);
        //when
        var exists = underTest.findByHash(testHash);
        //then
        assertThat(exists).isInstanceOf(InvitationLinkEntity.class);
    }

    @Test
    void itShouldGetThatHashDoesNotExistsInTable() {
        //given
        var testHash = userService.generateRandomAlphaNumeric();
        //when
        var exists = underTest.findByHash(testHash);
        //then
        assertThat(exists).isNull();
    }
}