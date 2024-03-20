package eatpro.model;

public class Meals {
    protected int mealId;
    protected MealType mealType;
    protected UserAdjustments userAdjustment;

    public enum MealType {
        Breakfast, Lunch, Dinner, Snack
    }

    public Meals(int mealId, MealType mealType, UserAdjustments userAdjustment) {
        this.mealId = mealId;
        this.mealType = mealType;
        this.userAdjustment = userAdjustment;
    }

    public Meals(int mealId) {
        this.mealId = mealId;
    }

    public Meals(MealType mealType, UserAdjustments userAdjustment) {
        this.mealType = mealType;
        this.userAdjustment = userAdjustment;
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

    public UserAdjustments getUserAdjustment() {
        return userAdjustment;
    }

    public void setUserAdjustment(UserAdjustments userAdjustment) {
        this.userAdjustment = userAdjustment;
    }
}
