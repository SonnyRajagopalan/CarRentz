package com.sonny.carrentz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @GetMapping("/")
    public String getRoot() {
                return "Hello world!";
    }

    @GetMapping("/customer")
    public String getCustomer(@RequestParam String name) {
        return "Hello, " + name + "!";
    }
}
