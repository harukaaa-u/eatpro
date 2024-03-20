package eatpro.model;

public class MealDetails {
    protected int mealDetailId;
    protected Food food;
    protected Meals meal;
	
    public MealDetails(int mealDetailId, Food food, Meals meal) {
		this.mealDetailId = mealDetailId;
		this.food = food;
		this.meal = meal;
	}

	public MealDetails(Food food, Meals meal) {
		this.food = food;
		this.meal = meal;
	}

	public MealDetails(int mealDetailId) {
		super();
		this.mealDetailId = mealDetailId;
	}

	public int getMealDetailId() {
		return mealDetailId;
	}

	public void setMealDetailId(int mealDetailId) {
		this.mealDetailId = mealDetailId;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Meals getMeal() {
		return meal;
	}

	public void setMeal(Meals meal) {
		this.meal = meal;
	}
}
