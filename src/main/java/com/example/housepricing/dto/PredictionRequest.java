package com.example.housepricing.dto;

import jakarta.validation.constraints.*;

public class PredictionRequest {

    @NotBlank(message = "Location is required")
    private String location;

    @Positive(message = "Built-up area must be greater than 0")
    private double builtUpArea;

    @Min(value = 1, message = "At least 1 bedroom")
    @Max(value = 10, message = "At most 10 bedrooms")
    private int bedrooms;

    @Min(value = 1, message = "At least 1 bathroom")
    @Max(value = 10, message = "At most 10 bathrooms")
    private int bathrooms;

    private boolean parking;

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getBuiltUpArea() { return builtUpArea; }
    public void setBuiltUpArea(double builtUpArea) { this.builtUpArea = builtUpArea; }
    public int getBedrooms() { return bedrooms; }
    public void setBedrooms(int bedrooms) { this.bedrooms = bedrooms; }
    public int getBathrooms() { return bathrooms; }
    public void setBathrooms(int bathrooms) { this.bathrooms = bathrooms; }
    public boolean isParking() { return parking; }
    public void setParking(boolean parking) { this.parking = parking; }
}
