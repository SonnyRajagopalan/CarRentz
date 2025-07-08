package com.sonny.carrentz.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
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
                .andExpect(jsonPath("$.rentalBranchID").value(2800));
    }

    @Test
    void testCreateRentalCheckRentalRepoInsertAndAvailableStatus() throws Exception {
        
        String rentalJson = "{"
                + "\"repID\": 1000,"
                + "\"rentalBranchID\": 2800,"
                + "\"returnBranchID\": 2800,"
                + "\"carID\": \"12345\","
                + "\"carType\": \"SUV\","
                + "\"duration\": 7,"
                + "\"rentalDate\": \"2023-10-01T10:00:00\","
                + "\"returnDate\": \"2023-10-08T10:00:00\","
                + "\"customerID\": 2000,"
                + "\"expectedCharges\": 350.00,"
                + "\"actualCharges\": 0.00"
                + "}";
        
        this.mockMvc.perform(post("/rentals").
        contentType("application/json").
        content(rentalJson)).
        andExpect(status().isCreated()); // Should actually be returning isCreated (201) if the rental is created successfully

        this.mockMvc.perform(get("/inventory/12345")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(false));
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
