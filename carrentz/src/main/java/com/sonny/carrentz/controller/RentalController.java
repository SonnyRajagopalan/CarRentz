package com.sonny.carrentz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonny.carrentz.model.Rental;
import com.sonny.carrentz.model.Inventory;
import com.sonny.carrentz.repository.RentalRepository;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import com.sonny.carrentz.repository.InventoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// import java.sql.Connection;
// import java.sql.Types;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping ("/rentals")
public class RentalController {
    private final RentalRepository rentalRepository;
    private final InventoryRepository inventoryRepository;
    
    public RentalController (RentalRepository rentalRepository, InventoryRepository inventoryRepository) {
        this.rentalRepository = rentalRepository;
        this.inventoryRepository = inventoryRepository;
    }
    
    @GetMapping("/availableByCarType/{carType}")
    public ResponseEntity<Rental> getAvailableRentalByCarType(@PathVariable String carType) {
        Rental rental = new Rental();
        System.out.println("Fetching available rental for car type: " + carType);
        List<Inventory> availableCars = inventoryRepository.findByCarType(carType);
        if (availableCars.isEmpty()) {
            System.out.println("No available rentals found for car type: " + carType);
            return ResponseEntity.notFound().build();
        }
        System.out.println("Available rentals found for car type: " + carType);
        // Assuming you want to return the first available rental
        Inventory car = availableCars.get(0);
        rental.setCarID(car.getCarID());
        rental.setCarType(car.getCarType());
        rental.setDuration(1); // Default duration of 1 day
        rental.setRentalDate(LocalDateTime.now());
        rental.setReturnDate(LocalDateTime.now().plusDays(rental.getDuration()));
        rental.setCustomerID("defaultCustomerID"); // Placeholder for customer ID
        rental.setExpectedCost(0.0f); // Default expected cost
        rental.setactualCharges(0.0f); // Default actual cost
        System.out.println("Rental created: " + rental);
        return ResponseEntity.ok(rental);
    }

    @GetMapping("/{rentalID}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long rentalID) {
        System.out.println("Fetching rental with ID: " + rentalID);
        return ResponseEntity.ok(rentalRepository.findByRentalID(rentalID));
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        rental.setCustomerID(rental.getCustomerID()); // One of a few required fields
        rental.setExpectedCost(0.0f); // Default expected cost
        rental.setactualCharges(0.0f); // Default actual cost
        rental.setCarID(rental.getCarID()); // Assuming carID is set later
        rental.setCarType(rental.getCarType()); // Default car type
        rental.setDuration(rental.getDuration()); // Default duration
        // Set rental and return dates
        rental.setRentalDate(LocalDateTime.now());
        rental.setReturnDate(LocalDateTime.now().plusDays(rental.getDuration()));
        Rental savedRental = rentalRepository.save(rental);
        return ResponseEntity.ok(savedRental);
    }
}
