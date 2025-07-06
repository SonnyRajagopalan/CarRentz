package com.sonny.carrentz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Rentals") // Assuming the table name is 'rentals'
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentalID", unique = true, nullable = false)
    private Long rentalID;
    @Column(name = "repID", nullable = false)
    private Long repID; // Assuming repID is a foreign key reference to Customer
    @Column(name = "rentalBranchID", nullable = false)
    private Long rentalBranchID; // Assuming branchID is a foreign key reference to Branch
    @Column(name = "returnBranchID", nullable = false)
    private Long returnBranchID; 
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
    private Long customerID; // Assuming customerID is a foreign key reference to Customer
    @Column(name = "expectedCharges", nullable = false)
    private float  expectedCharges;
    @Column(name = "actualCharges", nullable = false)
    private float  actualCharges;
    
    public Rental() {
    }

    // Constructor with all fields
    public Rental(Long rentalID, Long repID, Long rentalBranchID, Long returnBranchID, Long carID, String carType, int duration, 
                        LocalDateTime rentalDate, LocalDateTime returnDate, Long customerID, float expectedCharges, float actualCharges) {
        this.rentalID = rentalID;
        this.repID = repID;
        this.rentalBranchID = rentalBranchID;
        this.returnBranchID = returnBranchID;
        this.carID = carID;
        this.carType = carType;
        this.duration = duration;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.customerID = customerID;
        this.expectedCharges = expectedCharges;
        this.actualCharges = actualCharges;
    }

    public Long getRentalID() {
        return rentalID;
    }

    public void setRentalID(Long rentalID) {
        this.rentalID = rentalID;
    }

    public Long getRepID() {
        return repID;
    }

    public void setRepID(Long repID) {
        this.repID = repID;
    }

    public Long getRentalBranchID() {
        return rentalBranchID;
    }

    public void setRentalBranchID(Long branchID) {
        this.rentalBranchID = branchID;
    }

    public Long getReturnBranchID() {
        return returnBranchID;
    }

    public void setReturnBranchID(Long branchID) {
        this.returnBranchID = branchID;
    }
    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
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

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public float getExpectedCharges() {
        return expectedCharges;
    }

    public void setExpectedCharges(float expectedCharges) {
        this.expectedCharges = expectedCharges;
    }

    public float getActualCharges() {
        return actualCharges;
    }

    public void setActualCharges(float actualCharges) {
        this.actualCharges = actualCharges;
    }
}
