package com.sonny.carrentz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sonny.carrentz.model.RentalsUnavailable;

public interface RentalsUnavailableRepository extends JpaRepository<RentalsUnavailable, Long> {
    // This interface will inherit all CRUD operations from JpaRepository
    // Additional custom query methods can be defined here if needed
}
