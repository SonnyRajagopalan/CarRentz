package com.sonny.carrentz.repository;

import com.sonny.carrentz.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;



public interface RentalRepository extends JpaRepository<Rental, Long> {
    // Define custom query methods if needed
    // For example, find rentals by customer ID, car type, etc.
    // Example: List<Rental> findByCustomerId(Long customerId);

    // @org.springframework.data.jpa.repository.Query(value="SELECT r FROM rentals r WHERE r.rentalID = ?1", nativeQuery = true)
    @NativeQuery(value="SELECT * FROM rentals WHERE rentalID = ?1")
    Rental findByRentalID(Long rentalID);

    // @org.springframework.data.jpa.repository.Query(value="SELECT r FROM rentals r WHERE r.carID = ?1", nativeQuery = true)
    @NativeQuery(value="SELECT FROM rentals WHERE carID = ?1")
    Rental findByCarID(Long carID);
}

