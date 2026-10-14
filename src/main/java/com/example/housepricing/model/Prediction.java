package com.example.housepricing.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private double builtUpArea;
    private int bedrooms;
    private int bathrooms;
    private boolean parking;
    private double estimatedPrice;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Prediction() {}

    public Prediction(String location, double builtUpArea, int bedrooms, int bathrooms,
                      boolean parking, double estimatedPrice) {
        this.location = location;
        this.builtUpArea = builtUpArea;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.parking = parking;
        this.estimatedPrice = estimatedPrice;
    }

    public Long getId() { return id; }
    public String getLocation() { return location; }
    public double getBuiltUpArea() { return builtUpArea; }
    public int getBedrooms() { return bedrooms; }
    public int getBathrooms() { return bathrooms; }
    public boolean isParking() { return parking; }
    public double getEstimatedPrice() { return estimatedPrice; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
