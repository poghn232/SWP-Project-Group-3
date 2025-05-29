package com.example.demo.model;

public class Recipe {
    private int recipeId;
    private int itemId;
    private int ingredientId;
    private double quantityRequired;

    public Recipe(int recipeId, int itemId, int ingredientId, double quantityRequired) {
        this.recipeId = recipeId;
        this.itemId = itemId;
        this.ingredientId = ingredientId;
        this.quantityRequired = quantityRequired;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public double getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }
}
