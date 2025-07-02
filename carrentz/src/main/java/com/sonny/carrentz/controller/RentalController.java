package com.sonny.carrentz.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonny.carrentz.model.Rental;
import com.sonny.carrentz.repository.RentalRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// import java.sql.Connection;
// import java.sql.Types;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
import java.time.LocalDateTime;


@RestController
@RequestMapping ("/rentals")
public class RentalController {
    private final RentalRepository rentalRepository;
    
    public RentalController(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
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
