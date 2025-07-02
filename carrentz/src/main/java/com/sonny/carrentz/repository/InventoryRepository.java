package com.sonny.carrentz.repository;

import java.util.List;
import com.sonny.carrentz.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    @NativeQuery(value="SELECT * FROM inventory WHERE carType = ?1 AND available = true")
    List<Inventory> findByCarType(String carType);

    @NativeQuery(value="SELECT * FROM inventory WHERE carID = ?1 LIMIT 1")
    Inventory findByCarID(Long cID);
}
