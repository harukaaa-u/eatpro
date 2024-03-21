package eatpro.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eatpro.dal.*;
import eatpro.model.*;
import eatpro.model.Meals.MealType;

@WebServlet("/foodselection")
public class SelectFood extends HttpServlet {
	protected FoodDao foodDao;
	protected DietaryRestrictionsDao dietaryRestrictionsDao;
	protected MealsDao mealsDao;
	protected MealDetailsDao mealDetailsDao;
	@Override
	public void init() throws ServletException {
		foodDao = FoodDao.getInstance();
		dietaryRestrictionsDao = DietaryRestrictionsDao.getInstance();
		mealsDao = MealsDao.getInstance();
		mealDetailsDao = MealDetailsDao.getInstance();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);
        String userName = (String) req.getSession().getAttribute("userName");
        Integer totalCalories = (Integer) req.getSession().getAttribute("totalCalories");
        // int totalCalories = (int) req.getSession().getAttribute("totalCalories");
        Integer breakfastMealId = (Integer) req.getSession().getAttribute("breakfastMealId");
        Integer lunchMealId = (Integer) req.getSession().getAttribute("lunchMealId");
        Integer dinnerMealId = (Integer) req.getSession().getAttribute("dinnerMealId");
        Integer snackMealId = (Integer) req.getSession().getAttribute("snackMealId");
        Double breakfastCalories = (Double) req.getSession().getAttribute("breakfastCalories");
        Double lunchCalories = (Double) req.getSession().getAttribute("lunchCalories");
        Double dinnerCalories = (Double) req.getSession().getAttribute("dinnerCalories");
        Double snackCalories = (Double) req.getSession().getAttribute("snackCalories");

        
//		HashMap<String, Double> breakfastNutrients = (HashMap<String, Double>) req.getAttribute("breakfastNutrients");
//		HashMap<String, Double> lunchNurtients = (HashMap<String, Double>) req.getAttribute("lunchNurtients");
//		HashMap<String, Double> dinnerNutrients = (HashMap<String, Double>) req.getAttribute("dinnerNutrients");
//		HashMap<String, Double> snackNutrients = (HashMap<String, Double>) req.getAttribute("snackNutrients");


        HashMap<String, List<MealDetails>> mealDetailsMap = new HashMap<String, List<MealDetails>>();

		mealDetailsMap.put("breakfast", selectFood(breakfastMealId, breakfastCalories, messages));
		mealDetailsMap.put("lunch", selectFood(lunchMealId, lunchCalories, messages));
		mealDetailsMap.put("dinner", selectFood(dinnerMealId, dinnerCalories, messages));
		mealDetailsMap.put("snack", selectFood(snackMealId, snackCalories, messages));

		req.setAttribute("mealDetailsMap", mealDetailsMap);
		req.getRequestDispatcher("/MealPlanDisplay.jsp").forward(req, resp);
	}


	private List<MealDetails> selectFood(Integer mealId, double nutrients, Map<String, String> messages) throws IOException {
		List<MealDetails> mealDetails = new ArrayList<>();
		try {
					
			Meals meal = mealsDao.getMealById(mealId);
			
			if (meal.getMealType().equals(MealType.Snack)) {
				Food snackFood = foodDao.getRandomFoodByMealFoodCategory(meal.getMealType(), "Healthy Snack", nutrients);
				System.out.println(snackFood == null ? "snack null" : snackFood.getFoodName());
				MealDetails mealDetailsSnack = new MealDetails(snackFood, meal);
				mealDetails.add(mealDetailsSnack);
			} else {
				Food proteinFood = foodDao.getRandomFoodByMealFoodCategory(meal.getMealType(), "Non-Vegan Proteins", nutrients * 0.3);
				Food carbohydrateFood = foodDao.getRandomFoodByMealFoodCategory(meal.getMealType(), "Carbohydrates", nutrients * 0.45);
				Food vegetableFood = foodDao.getRandomFoodByMealFoodCategory(meal.getMealType(), "Vegetables", nutrients * 0.25);
				System.out.println(proteinFood == null ? "protein null" : proteinFood.getFoodName());
				System.out.println(carbohydrateFood == null ? "carbo null" : carbohydrateFood.getFoodName());
				System.out.println(vegetableFood == null ? "vege null" : vegetableFood.getFoodName());
				MealDetails mealDetails1 = new MealDetails(proteinFood, meal);
				mealDetailsDao.create(mealDetails1);
				MealDetails mealDetails2 = new MealDetails(carbohydrateFood, meal);
				mealDetailsDao.create(mealDetails2);
				MealDetails mealDetails3 = new MealDetails(vegetableFood, meal);
				mealDetailsDao.create(mealDetails3);
				mealDetails.add(mealDetails1);
				mealDetails.add(mealDetails2);
				mealDetails.add(mealDetails3);
			}

        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
		return mealDetails;
	}

//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//	}
}