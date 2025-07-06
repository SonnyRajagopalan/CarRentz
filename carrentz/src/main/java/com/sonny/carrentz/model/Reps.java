package com.sonny.carrentz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reps") // Assuming the table name is 'Reps'
public class Reps {
    @Id
    @Column(name = "repID", unique = true, nullable = false)
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long repID; // Unique identifier for the representative
    @Column(name = "lastname", nullable = false)
    private String lastname; // Last name of the representative
    @Column(name = "firstname", nullable = false)
    private String firstname; // First name of the representative
    @Column(name = "branchID", nullable = false)
    private Long branchID; // Branch ID where the representative works
    @Column(name = "contactInfo", nullable = false)
    private String contactInfo; // Contact information for the representative
    @Column(name = "email", nullable = false)
    private String email; // Email address of the representative
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber; // Phone number of the representative

    public Long getRepID() {
        return repID;
    }

    public void setRepID(Long repID) {
        this.repID = repID;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Long getBranchID() {
        return branchID;
    }

    public void setBranchID(Long branchID) {
        this.branchID = branchID;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
