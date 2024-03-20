package eatpro.model;

public class MealPlans {
    protected int mealPlanId;
    protected Users user;
    protected UserAdjustments userAdjustment;
    protected Integer totalCalorieForToday;
    
    public MealPlans(int mealPlanId, Users user, UserAdjustments userAdjustment, Integer totalCalorieForToday) {
        this.mealPlanId = mealPlanId;
        this.user = user;
        this.userAdjustment = userAdjustment;
        this.totalCalorieForToday = totalCalorieForToday;
    }

    public MealPlans(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }
    

    public MealPlans(Users user, UserAdjustments userAdjustment, Integer totalCalorieForToday) {
		this.user = user;
		this.userAdjustment = userAdjustment;
		this.totalCalorieForToday = totalCalorieForToday;
	}

	// Getters and Setters
    public int getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public Users getUser() {
        return user;
    }

    public void setUsername(String username) {
        this.user = user;
    }

    public UserAdjustments getAdjustment() {
        return userAdjustment;
    }

    public void setAdjustment(UserAdjustments userAdjustment) {
        this.userAdjustment = userAdjustment;
    }

    public Integer getTotalCalorieForToday() {
        return totalCalorieForToday;
    }

    public void setTotalCalorieForToday(Integer totalCalorieForToday) {
        this.totalCalorieForToday = totalCalorieForToday;
    }
}
