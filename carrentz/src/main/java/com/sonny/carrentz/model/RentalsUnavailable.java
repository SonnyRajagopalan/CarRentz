package com.sonny.carrentz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "RentalsUnavailable") // Assuming the table name is 'RentalsUnavailable'
public class RentalsUnavailable {
    @Id
    @Column(name = "unavailableID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unavailableID; // Primary key, auto-generated
    @Column(name = "carType", nullable = false)
    private String carType; // Type of car that is unavailable
    @Column(name = "rentalDate", nullable = false)
    private LocalDateTime rentalDate; // Start date of unavailability
    @Column(name = "duration", nullable = false)
    private int duration; // Duration of unavailability in days
    @Column(name = "customerID", nullable = false)
    private Long customerID;
    @Column(name = "repID", nullable = false)
    private Long repID; // Representative ID who had to tell the customer about the unavailability
    @Column(name = "branchID", nullable = false)
    private Long branchID;
    @Column(name = "reason")
    private String reason; // Reason for unavailability

    public RentalsUnavailable() {
        // Default constructor required by JPA
    }

    public RentalsUnavailable(Long unavailableID, String carType, LocalDateTime rentalDate,
            int duration, Long customerID, Long repID, Long branchID, String reason) {
        this.unavailableID = unavailableID;
        this.carType = carType;
        this.rentalDate = rentalDate;
        this.duration = duration;
        this.customerID = customerID;
        this.repID = repID;
        this.branchID = branchID;
        this.reason = reason;
    }

    public Long getUnavailableID() {
        return unavailableID;
    }

    public void setUnavailableID(Long unavailableID) {
        this.unavailableID = unavailableID;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getRepID() {
        return repID;
    }

    public void setRepID(Long repID) {
        this.repID = repID;
    }

    public Long getBranchID() {
        return branchID;
    }

    public void setBranchID(Long branchID) {
        this.branchID = branchID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
