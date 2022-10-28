package com.example.yachtRent.controller;

import com.example.yachtRent.service.YachtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(YachtController.class)
class YachtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private YachtService yachtService;

    @Test
    void getAllYachtsEmpty() throws Exception{
        when(yachtService.getAllYachts()).thenReturn(List.of());

        mockMvc
                .perform(get("/api/v1/yachts/all"))
                .andExpect(content().json("[]"))
                .andExpect(status().isOk());
    }
}