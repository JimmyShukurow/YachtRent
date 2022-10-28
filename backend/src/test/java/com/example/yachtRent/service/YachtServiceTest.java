package com.example.yachtRent.service;

import com.example.yachtRent.repository.YachtRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class YachtServiceTest {

    @Mock
    private YachtRepository yachtRepository;
    private YachtService yachtService;


    @BeforeEach
    void setUp() {
        yachtService = new YachtService(yachtRepository);
    }


    @Test
    void getAllYachts() {
        //when
        yachtService.getAllYachts();
        //then
        verify(yachtRepository).findAll();
    }

    @Disabled
    @Test
    void storeYacht() {
    }

    @Disabled
    @Test
    void update() {
    }

    @Disabled
    @Test
    void deleteYacht() {
    }
}