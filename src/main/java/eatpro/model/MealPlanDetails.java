package eatpro.model;

public class MealPlanDetails {
    protected int mealPlanDetailId;
    protected Integer mealPlanId;

    public MealPlanDetails(int mealPlanDetailId, Integer mealPlanId) {
        this.mealPlanDetailId = mealPlanDetailId;
        this.mealPlanId = mealPlanId;
    }

    public MealPlanDetails(int mealPlanDetailId) {
        this.mealPlanDetailId = mealPlanDetailId;
    }
    

    // Getters and Setters
    public int getMealPlanDetailId() {
        return mealPlanDetailId;
    }

    public void setMealPlanDetailId(int mealPlanDetailId) {
        this.mealPlanDetailId = mealPlanDetailId;
    }

    public Integer getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(Integer mealPlanId) {
        this.mealPlanId = mealPlanId;
    }
}
