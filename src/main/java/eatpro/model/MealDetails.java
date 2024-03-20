package eatpro.model;

public class MealDetails {
    protected int mealDetailId;
    protected int foodId;
    protected int mealId;

    public MealDetails(int mealDetailId, int foodId, int mealId) {
        this.mealDetailId = mealDetailId;
        this.foodId = foodId;
        this.mealId = mealId;
    }
    
    public MealDetails(int foodId, int mealId) {
		this.foodId = foodId;
		this.mealId = mealId;
	}

	// Getters and Setters
    public int getMealDetailId() {
        return mealDetailId;
    }

    public void setMealDetailId(int mealDetailId) {
        this.mealDetailId = mealDetailId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }
}
