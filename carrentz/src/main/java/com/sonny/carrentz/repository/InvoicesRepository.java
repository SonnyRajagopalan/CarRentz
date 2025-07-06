package com.sonny.carrentz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sonny.carrentz.model.Invoices;

public interface InvoicesRepository extends JpaRepository<Invoices, Long> {
    // This interface will inherit all CRUD operations from JpaRepository
    // Additional custom query methods can be defined here if needed
    // For example, you could add methods to find invoices by customer ID or date range
}