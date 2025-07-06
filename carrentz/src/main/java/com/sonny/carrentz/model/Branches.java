package com.sonny.carrentz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@jakarta.persistence.Table(name = "Branches")
public class Branches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branchID")
    private Long branchID; // Unique identifier for the branch
    @Column(name = "branchName")
    private String branchName; // Name of the branch
    @Column(name = "address")
    private String address; // Address of the branch
    @Column(name = "contactNumber")
    private String contactNumber; // Contact number for the branch
    @Column(name = "email")
    private String email; // Email address for the branch

    public Branches() {
        // Default constructor required by JPA
    }

    public Branches(Long branchID, String branchName, String address, String contactNumber, String email) {
        this.branchID = branchID;
        this.branchName = branchName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public Long getBranchID() {
        return branchID;
    }

    public void setBranchID(Long branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
