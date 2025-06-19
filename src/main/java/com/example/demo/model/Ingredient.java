package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredientId;

    private String name;
    private String unit;
    private double quantityInStock;
    private double minimumRequired;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    public Ingredient() {
        
    }

    public Ingredient(int ingredientId, String name, String unit, double quantityInStock, double minimumRequired,
            Date lastUpdated) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.unit = unit;
        this.quantityInStock = quantityInStock;
        this.minimumRequired = minimumRequired;
        this.lastUpdated = lastUpdated;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(double quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getMinimumRequired() {
        return minimumRequired;
    }

    public void setMinimumRequired(double minimumRequired) {
        this.minimumRequired = minimumRequired;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
