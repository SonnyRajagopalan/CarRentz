package com.sonny.carrentz.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.sonny.carrentz.controller.InventoryController;
import com.sonny.carrentz.repository.InventoryRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Test
    public void testInventoryAdd () throws Exception {
        mockMvc.perform(post("/inventory")
                .param("carID", "12345")
                .param("carType", "SUV")
                .param("currentBranchID", "2800")
                .param("available", "true"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.carID").value(12345))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carType").value("SUV"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentBranchID").value(2800))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(true));  
    }
}
