package com.sonny.carrentz.controller;

import org.springframework.web.bind.annotation.RestController;
import com.sonny.carrentz.repository.InventoryRepository;

@RestController
public class InventoryController {
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
}
