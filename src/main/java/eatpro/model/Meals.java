package eatpro.model;

public class Meals {
    protected int mealId;
    protected MealType mealType;
    protected Integer mealPlanDetailId;

    public enum MealType {
        Breakfast, Lunch, Dinner, Snack
    }

    public Meals(int mealId, MealType mealType, Integer mealPlanDetailId) {
        this.mealId = mealId;
        this.mealType = mealType;
        this.mealPlanDetailId = mealPlanDetailId;
    }

    public Meals(int mealId) {
        this.mealId = mealId;
    }

    public Meals(MealType mealType, Integer mealPlanDetailId) {
		this.mealType = mealType;
		this.mealPlanDetailId = mealPlanDetailId;
	}

	// Getters and Setters
    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public Integer getMealPlanDetailId() {
        return mealPlanDetailId;
    }

    public void setMealPlanDetailId(Integer mealPlanDetailId) {
        this.mealPlanDetailId = mealPlanDetailId;
    }
}
