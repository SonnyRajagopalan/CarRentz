package com.sonny.carrentz.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class RentalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Example test method (to be implemented):
    @Test
    public void testGetAvailableRentalByCarType() throws Exception {
        this.mockMvc.perform(get("/rentals/availableByCarType?carType=SUV&branchID=2800"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.carType").value("SUV"))
                .andExpect(jsonPath("$.branchID").value(1));
    }

    @Test
    void testCreateRental() {

    }

    @Test
    void testReturnRental() {

    }

    @Test
    void testCreateRental2() {
        
    }

    @Test
    void testGetRentalById() {
        
    }
}
