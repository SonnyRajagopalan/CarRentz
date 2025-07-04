package com.sonny.carrentz.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Inventory") // Assuming the table name is 'inventory'
public class Inventory {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "carID", unique = true, nullable = false)
    private Long carID; 
    @Column(name = "carType", nullable = false)
    private String carType;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "make", nullable = false)
    private String make;
    @Column(name = "milesDriven", nullable = false)
    private float milesDriven;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "color", nullable = false)
    private String color;
    @Column(name = "pricePerDay", nullable = false)
    private float pricePerDay;
    @Column(name = "available", nullable = false)
    private boolean available;

    public Inventory() {
    }

    public Inventory(Long carID, String carType, String model, 
            String make, float milesDriven, int year, String color, 
            float pricePerDay, boolean available) {
        this.carID = carID;
        this.carType = carType;
        this.model = model;
        this.make = make;
        this.milesDriven = 0.0f; // Default value for miles driven
        this.year = year;
        this.color = color;
        this.pricePerDay = pricePerDay;
        this.available = available;
    }

    // Getters and Setters
    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }
    public float getMilesDriven() {
        return milesDriven;
    }

    public void setMilesDriven(float milesDriven) {
        this.milesDriven = milesDriven;
    }
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
