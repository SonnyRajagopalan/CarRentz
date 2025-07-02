package com.sonny.carrentz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
// import java.sql.ResultSet;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "rentalID", unique = true, nullable = false)
    private Long rentalID;
    @Column(name = "carID", nullable = false)
    private Long carID; // Assuming carID is a foreign key reference to Inventory
    @Column(name = "carType", nullable = false)
    private String carType;
    @Column(name = "duration", nullable = false)
    private int duration;
    @Column(name = "rentalDate", nullable = false)
    private LocalDateTime rentalDate;
    @Column(name = "returnDate", nullable = false)
    private LocalDateTime returnDate;
    @Column(name = "customerID", nullable = false)
    private String customerID; // Assuming customerID is a foreign key reference to Customer
    @Column(name = "expectedCost", nullable = false)
    private float  expectedCost;
    @Column(name = "actualCharges", nullable = false)
    private float  actualCharges;
    
    public Rental() {
    }

    public Rental(Long rentalID, String carType, int duration, LocalDateTime rentalDate, LocalDateTime returnDate, 
                        String customerID, float expectedCost, float actualCharges) {
        this.rentalID = rentalID;
        this.carType = carType;
        this.duration = duration;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.customerID = customerID;
        this.expectedCost = expectedCost;
        this.actualCharges = actualCharges;
    }
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Long getRentalID() {
        return rentalID;
    }

    public void setRentalID(Long rentalID) {
        this.rentalID = rentalID;
    }

    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }

    public float getExpectedCost() {
        return expectedCost;
    }

    public void setExpectedCost(float expectedCost) {
        this.expectedCost = expectedCost;
    }

    public float getactualCharges() {
        return actualCharges;
    }

    public void setactualCharges(float actualCharges) {
        this.actualCharges = actualCharges;
    }
}
