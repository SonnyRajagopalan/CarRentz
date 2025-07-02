package com.sonny.carrentz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @GetMapping("/")
    public String getRoot() {
                return "Hello world!";
    }

    @GetMapping("/customer/driverID")
    public String getCustomerByDriveString(@RequestParam String driverIDString) {
        return "Hello, " + driverIDString + "!";
    }
    
    @GetMapping("/customer/phoneNumber")
    public String getCustomerByPhoneNumber(@RequestParam String phoneNumber) {
        return "Hello, " + phoneNumber + "!";
    }
    @PostMapping("/customer/add")
    public String addCustomer(@RequestParam String driverIDString, @RequestParam String phoneNumber) {
        // Logic to add customer would go here
        return "Customer with Driver ID: " + driverIDString + " and Phone Number: " + phoneNumber + " added successfully!";
    }

}
