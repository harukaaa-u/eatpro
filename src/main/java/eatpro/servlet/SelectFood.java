package eatpro.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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
		String breakfastId = (String) req.getAttribute("breakfast");
		String lunchId = (String) req.getAttribute("lunch");
		String dinnerId = (String) req.getAttribute("dinner");
		String snackId = (String) req.getAttribute("snack");
		HashMap<String, Double> breakfastNutrients = (HashMap<String, Double>) req.getAttribute("breakfastNutrients");
		HashMap<String, Double> lunchNurtients = (HashMap<String, Double>) req.getAttribute("lunchNurtients");
		HashMap<String, Double> dinnerNutrients = (HashMap<String, Double>) req.getAttribute("dinnerNutrients");
		HashMap<String, Double> snackNutrients = (HashMap<String, Double>) req.getAttribute("snackNutrients");

		selectFood(breakfastId, breakfastNutrients, messages);
			selectFood(lunchId, lunchNurtients, messages);
			selectFood(dinnerId, dinnerNutrients, messages);
			selectFood(snackId, snackNutrients, messages);

			req.setAttribute("breakfastId", breakfastId);
			req.setAttribute("lunchId", lunchId);
			req.setAttribute("dinnerId", dinnerId);
			req.setAttribute("snackId", snackId);
			req.getRequestDispatcher("/displaymeal").forward(req, resp);
	}

	private void selectFood(String mealId, HashMap<String, Double> nutrients, Map<String, String> messages ) throws IOException {
		try {
					
			if (mealId != null && !mealId.trim().isEmpty() && nutrients != null) {
				Meals meal = mealsDao.getMealById(Integer.parseInt(mealId));
				Food proteinFood = foodDao.getRandomFoodByMealFoodCategory(meal.getMealType(), "protein", nutrients.get("calories"));
				Food carbohydrateFood = foodDao.getRandomFoodByMealFoodCategory(meal.getMealType(), "carbohydrates", nutrients.get("calories"));
				Food vegetableFood = foodDao.getRandomFoodByMealFoodCategory(meal.getMealType(), "vegetables", nutrients.get("calories"));
				MealDetails mealDetails1 = new MealDetails(proteinFood, meal);
				mealDetailsDao.create(mealDetails1);
				MealDetails mealDetails2 = new MealDetails(carbohydrateFood, meal);
				mealDetailsDao.create(mealDetails2);
				MealDetails mealDetails3 = new MealDetails(vegetableFood, meal);
				mealDetailsDao.create(mealDetails3);
			} else {
				messages.put("title", "Invalid Meal.");
			}

        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}



}