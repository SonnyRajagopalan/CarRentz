package com.sonny.carrentz.repository;

import com.sonny.carrentz.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @NativeQuery(value="SELECT * FROM Rentals WHERE rentalID = ?1")
    Rental findByRentalID(Long rentalID);

    @NativeQuery(value="SELECT FROM Rentals WHERE carID = ?1")
    Rental findByCarID(Long carID);
}