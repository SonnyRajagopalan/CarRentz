package com.sonny.carrentz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sonny.carrentz.model.Inventory;
import com.sonny.carrentz.repository.InventoryRepository;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        // This method can be used to add a new car to the inventory
        Inventory savedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(savedInventory);
    }

    @DeleteMapping
    public String deleteInventory() {
        // This method can be used to delete all entries in the inventory
        inventoryRepository.deleteAll();
        return "Inventory deleted successfully";
    }
}
