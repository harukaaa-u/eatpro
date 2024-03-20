package eatpro.model;

public class Meals {
    protected int mealId;
    protected MealType mealType;
    protected MealPlanDetails mealPlanDetail;

    public enum MealType {
        Breakfast, Lunch, Dinner, Snack
    }

    public Meals(int mealId, MealType mealType, MealPlanDetails mealPlanDetail) {
        this.mealId = mealId;
        this.mealType = mealType;
        this.mealPlanDetail = mealPlanDetail;
    }

    public Meals(int mealId) {
        this.mealId = mealId;
    }

    public Meals(MealType mealType, MealPlanDetails mealPlanDetail) {
        this.mealType = mealType;
        this.mealPlanDetail = mealPlanDetail;
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

    public MealPlanDetails getMealPlanDetail() {
        return mealPlanDetail;
    }

    public void setMealPlanDetail(MealPlanDetails mealPlanDetail) {
        this.mealPlanDetail = mealPlanDetail;
    }
}


