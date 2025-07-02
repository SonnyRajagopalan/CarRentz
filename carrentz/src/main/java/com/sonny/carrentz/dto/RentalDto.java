package com.sonny.carrentz.dto;

public class RentalDto {
    private String carType;
    private String duration;
    private String rentalDate;
    private String returnDate;
    private String customerID;
    private String rentalID;
    private float  expectedCost;
    private float  actualCost;
    
    public RentalDto() {
    }

    public RentalDto(String carType, String duration, String rentalDate, String returnDate, String customerID, String rentalID, float expectedCost, float actualCost) {
        this.carType = carType;
        this.duration = duration;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.customerID = customerID;
        this.rentalID = rentalID;
        this.expectedCost = expectedCost;
        this.actualCost = actualCost;
    }
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getRentalID() {
        return rentalID;
    }

    public void setRentalID(String rentalID) {
        this.rentalID = rentalID;
    }

    public float getExpectedCost() {
        return expectedCost;
    }

    public void setExpectedCost(float expectedCost) {
        this.expectedCost = expectedCost;
    }

    public float getActualCost() {
        return actualCost;
    }

    public void setActualCost(float actualCost) {
        this.actualCost = actualCost;
    }
}
