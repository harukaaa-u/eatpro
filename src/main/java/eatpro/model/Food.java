package eatpro.model;

public class Food {
    protected int foodId;
    protected String ingredients;
    protected String servingSize;
    protected String servingUnit;
    protected String foodCategory;
    protected MealCategory mealCategory;
    protected String foodName;

    public enum MealCategory {
        Breakfast, Snack, Lunch_Dinner, All
    }

    public Food(int foodId, String ingredients, String servingSize, String servingUnit, String foodCategory, MealCategory mealCategory, String foodName) {
        this.foodId = foodId;
        this.ingredients = ingredients;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
        this.foodCategory = foodCategory;
        this.mealCategory = mealCategory;
        this.foodName = foodName;
    }
    
    

    public Food(String ingredients, String servingSize, String servingUnit, String foodCategory,
			MealCategory mealCategory, String foodName) {
		this.ingredients = ingredients;
		this.servingSize = servingSize;
		this.servingUnit = servingUnit;
		this.foodCategory = foodCategory;
		this.mealCategory = mealCategory;
		this.foodName = foodName;
	}



	public Food(int foodId) {
		this.foodId = foodId;
	}



	// Getters and Setters
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
    	this.foodId = foodId;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public MealCategory getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(MealCategory mealCategory) {
        this.mealCategory = mealCategory;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}