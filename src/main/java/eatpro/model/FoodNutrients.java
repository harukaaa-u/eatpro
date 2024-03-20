package eatpro.model;

public class FoodNutrients {
    protected int foodNutrientId;
    protected Food food;
    protected Nutrients nutrient;
    protected float amount;

    public FoodNutrients(int foodNutrientId, Food food, Nutrients nutrient, float amount) {
        this.foodNutrientId = foodNutrientId;
        this.food = food;
        this.nutrient = nutrient;
        this.amount = amount;
    }
    
    public FoodNutrients(Food food, Nutrients nutrient, float amount) {
		this.food = food;
		this.nutrient = nutrient;
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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Nutrients getNutrient() {
        return nutrient;
    }

    public void setNutrient(Nutrients nutrient) {
        this.nutrient = nutrient;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
