package eatpro.model;

public class MealPlans {
    protected int mealPlanId;
    protected String username;
    protected Integer adjustmentId;
    protected Integer totalCalorieForToday;
    
    public MealPlans(int mealPlanId, String username, Integer adjustmentId, Integer totalCalorieForToday) {
        this.mealPlanId = mealPlanId;
        this.username = username;
        this.adjustmentId = adjustmentId;
        this.totalCalorieForToday = totalCalorieForToday;
    }

    public MealPlans(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }
    
    public MealPlans(String username, Integer adjustmentId, Integer totalCalorieForToday) {
		this.username = username;
		this.adjustmentId = adjustmentId;
		this.totalCalorieForToday = totalCalorieForToday;
	}

	// Getters and Setters
    public int getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Integer adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public Integer getTotalCalorieForToday() {
        return totalCalorieForToday;
    }

    public void setTotalCalorieForToday(Integer totalCalorieForToday) {
        this.totalCalorieForToday = totalCalorieForToday;
    }
}
