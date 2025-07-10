package com.sonny.carrentz.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInventoryAddCar12345 () throws Exception {
        mockMvc.perform(post("/inventory")
                .param("carID", "12345")
                .param("carType", "SUV")
                .param("currentBranchID", "5000")
                .param("available", "true"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carID").value(12345))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carType").value("SUV"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentBranchID").value(5000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(true));
    }

    @Test
    public void testInventoryAddCar54321 () throws Exception {
        mockMvc.perform(post("/inventory")
                .param("carID", "54321")
                .param("carType", "Van")
                .param("currentBranchID", "5000")
                .param("available", "true"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carID").value(54321))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carType").value("Van"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentBranchID").value(5000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(true));
        System.out.println ("Car54321 test");
    }   
}
