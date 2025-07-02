package com.sonny.carrentz.dto;

public class CustomerDto {
    private String customerID;
    private String firstname;
    private String lastName;
    private String driverID; // This could be a reference to the license or driver ID
    private String phoneNumber;
    private String email;
    private String address;

    public CustomerDto() {
    }

    public CustomerDto(String customerID, String firstname, String lastName, String driverID, String phoneNumber, String email, String address) {
        this.customerID = customerID;
        this.firstname = firstname;
        this.lastName = lastName;
        this.driverID = driverID;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
