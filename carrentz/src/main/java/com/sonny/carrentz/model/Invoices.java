package com.sonny.carrentz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Invoices") // Assuming the table name is 'Invoices'
public class Invoices {
    @Id
    @Column(name = "invoiceID", unique = true, nullable = false)
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long invoiceID; // Primary key, auto-generated
    @Column(name = "rentalID", nullable = false)
    private Long rentalID; // Foreign key referencing Rental
    @Column(name = "customerID", nullable = false)
    private Long customerID; // Foreign key referencing Customer
    @Column(name = "rentalBranchID", nullable = false)
    private Long rentalBranchID; // Foreign key referencing Branch
    @Column(name = "returnBranchID", nullable = false)
    private Long returnBranchID; // Foreign key referencing Branch
    @Column(name = "repID", nullable = false)
    private Long repID; // Foreign key referencing Rep
    @Column(name = "totalCharges", nullable = false)
    private Float totalCharges; // Total charges for the rental
    @Column(name = "paymentStatus", nullable = false)
    private String paymentStatus; // e.g., "Paid", "Pending", "Overdue"

    public Invoices() {}

    public Invoices(Long rentalID, Long customerID, Long rentalBranchID, Long returnBranchID, Long repID,
            float totalCharges, String paymentStatus) {
        this.rentalID = rentalID;
        this.customerID = customerID;
        this.rentalBranchID = rentalBranchID;
        this.returnBranchID = returnBranchID;
        this.repID = repID;
        this.totalCharges = totalCharges;
        this.paymentStatus = paymentStatus;
    }

    public Long getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Long invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Long getRentalID() {
        return rentalID;
    }

    public void setRentalID(Long rentalID) {
        this.rentalID = rentalID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
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

    public Long getRepID() {
        return repID;
    }

    public void setRepID(Long repID) {
        this.repID = repID;
    }

    public Float getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(Float totalCharges) {
        this.totalCharges = totalCharges;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
