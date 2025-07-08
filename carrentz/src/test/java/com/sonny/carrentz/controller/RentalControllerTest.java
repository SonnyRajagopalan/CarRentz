package com.sonny.carrentz.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class RentalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private int carID1 = 0;
    private int carID2 = 0;

    @Test
    @Order (1)
    public void addCar1SUV() throws Exception {
        String car1Json = "{"
                + "\"carID\": null,"
                + "\"available\": true,"
                + "\"carType\": \"SUV\","
                + "\"color\": \"BLUE\","
                + "\"currentBranchID\": 5000,"
                + "\"make\": \"Subaru-made from car1Json\","
                + "\"milesDriven\": 42,"
                + "\"model\": \"Impreza\","
                + "\"pricePerDay\": 45.0,"
                + "\"year\": 2025"
                + "}";

        MvcResult response = this.mockMvc.perform(post("/inventory")
                .contentType("application/json").content(car1Json))
                .andExpect(status().isCreated()).andReturn();
        String car1 = response.getResponse().getContentAsString();
        this.carID1 = JsonPath.read(car1, "$.carID");
        System.out.println ("Car1 add test");
    }

    @Test
    @Order(2)
    public void addCar2Van() throws Exception {
        String car2Json = "{"
                + "\"carID\": null,"
                + "\"available\": true,"
                + "\"carType\": \"Van\","
                + "\"color\": \"ORANGE\","
                + "\"currentBranchID\": 5000,"
                + "\"make\": \"Subaru-made from car2Json\","
                + "\"milesDriven\": 42,"
                + "\"model\": \"Impreza\","
                + "\"pricePerDay\": 45.0,"
                + "\"year\": 2025"
                + "}";

        MvcResult response = this.mockMvc.perform(post("/inventory")
                .contentType("application/json").content(car2Json))
                .andExpect(status().isCreated()).andReturn();
        String car2 = response.getResponse().getContentAsString();
        this.carID2 = JsonPath.read(car2, "$.carID");
    }

    // @Test
    // @Order(3)
    // public void testGetAvailableRentalByCarTypeSUV() throws Exception {
    //     this.mockMvc.perform(get("/rentals/availableByCarType?carType=SUV&branchID=5000"))
    //             .andExpect(status().isOk()).andExpect(jsonPath("$.carType").value("SUV"))
    //             .andExpect(jsonPath("$.rentalBranchID").value(5000))
    //             .andExpect(jsonPath("$.duration").value(1));
    // }

    @Test
    @Order(4)
    public void testGetAvailableRentalByCarTypeVan() throws Exception {
        this.mockMvc.perform(get("/rentals/availableByCarType?carType=Van&branchID=5000"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.carType").value("Van"))
                .andExpect(jsonPath("$.rentalBranchID").value(5000))
                .andExpect(jsonPath("$.duration").value(1));
    }

    @Test
    @Order(5)
    void testCreateRentalCheckRentalRepoInsertAndAvailableStatus() throws Exception {

        // First get an available car in branch 5000
        MvcResult response = mockMvc.perform(get("/rentals/availableByCarType?carType=SUV&branchID=5000")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String aCar = response.getResponse().getContentAsString();
        int carID = JsonPath.read(aCar, "$.carID");

        String rentalJson = "{"
                + "\"repID\": 1000,"
                + "\"rentalBranchID\": 5000,"
                + "\"returnBranchID\": 5000,"
                + "\"carID\": " + carID + ","
                + "\"carType\": \"SUV\","
                + "\"duration\": 7,"
                + "\"rentalDate\": \"2023-10-01T10:00:00\","
                + "\"returnDate\": \"2023-10-08T10:00:00\","
                + "\"customerID\": 2000,"
                + "\"expectedCharges\": 350.00,"
                + "\"actualCharges\": 0.00"
                + "}";

        this.mockMvc.perform(post("/rentals").contentType("application/json").content(rentalJson))
                .andExpect(status().isCreated()); // Should actually be returning isCreated (201) if the rental is
                                                  // created successfully

        this.mockMvc.perform(get("/inventory/" + carID)).andExpect(status().isOk())
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
