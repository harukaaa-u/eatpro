package eatpro.model;

public class FoodNutrients {
    protected int foodNutrientId;
    protected int foodId;
    protected int nutrientId;
    protected float amount;

    public FoodNutrients(int foodNutrientId, int foodId, int nutrientId, float amount) {
        this.foodNutrientId = foodNutrientId;
        this.foodId = foodId;
        this.nutrientId = nutrientId;
        this.amount = amount;
    }
    
    public FoodNutrients(int foodId, int nutrientId, float amount) {
		this.foodId = foodId;
		this.nutrientId = nutrientId;
		this.amount = amount;
	}
    
	public FoodNutrients(int foodNutrientId) {
		this.foodNutrientId = foodNutrientId;
	}

	// Getters and Setters
    public int getFoodNutrientId() {
        return foodNutrientId;
    }

    public void setFoodNutrientId(int foodNutrientId) {
        this.foodNutrientId = foodNutrientId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
