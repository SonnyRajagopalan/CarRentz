package com.sonny.carrentz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("{carID}")
    public ResponseEntity<Inventory> getACarOfId (@PathVariable Long carID) {
        // This method can be used to get a car by its ID
        Inventory inventory = inventoryRepository.findById(carID).orElse(null);
        if (inventory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventory);
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        // This method can be used to add a new car to the inventory
        Inventory savedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
    }

    @DeleteMapping
    public ResponseEntity<Inventory> deleteInventory() {
        // This method can be used to delete all entries in the inventory
        inventoryRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
