package eatpro.model;

public class MealPlanDetails {
    protected int mealPlanDetailId;
    protected MealPlans mealPlan;

    public MealPlanDetails(int mealPlanDetailId, MealPlans mealPlan) {
        this.mealPlanDetailId = mealPlanDetailId;
        this.mealPlan = mealPlan;
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

    public MealPlans getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlans newMealPlan) {
        this.mealPlan = newMealPlan;
    }
}
