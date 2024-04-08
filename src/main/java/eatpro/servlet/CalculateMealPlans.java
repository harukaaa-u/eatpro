package eatpro.servlet;

import eatpro.dal.*;
import eatpro.model.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/mealplanning")
public class CalculateMealPlans extends HttpServlet {
    
    protected UsersDao usersDao;
    protected MealPlansDao mealPlansDao;
    protected MealPlanDetailsDao mealPlanDetailsDao;
    protected MealsDao mealsDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
        mealPlansDao = MealPlansDao.getInstance();
        mealsDao = MealsDao.getInstance();
        mealPlanDetailsDao = MealPlanDetailsDao.getInstance();
    }

    // MealPlan 
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);
        System.out.println("Received username attribute: " + req.getAttribute("username"));
        Users user = (Users) req.getSession().getAttribute("user");
        String username = user.getUserName();
        //String username =req.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            messages.put("title", "Invalid username.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/Test.jsp"); 
            dispatcher.forward(req, resp);
            return;
        }

        try {
            MealPlans mealPlan = (MealPlans) req.getAttribute("mealPlan");
            if (mealPlan == null) {
            	messages.put("error", "No meal plan found.");
            	req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            	return;
            }
            boolean gainWeight = user.isGainWeight();
         // Create MealPlanDetails
            MealPlanDetails mealPlanDetails = new MealPlanDetails(mealPlan);
            MealPlanDetails createdMealPlanDetails = mealPlanDetailsDao.create(mealPlanDetails);

            // Calculate calorie distribution for each meal
            int totalDailyCalories = mealPlan.getTotalCalorieForToday();
            System.out.println(totalDailyCalories + "Calories for Today");
            double breakfastCalories = gainWeight? (totalDailyCalories * 0.31) : (totalDailyCalories * 0.25);
            double lunchCalories = gainWeight? (totalDailyCalories * 0.31) : (totalDailyCalories * 0.35);
            double dinnerCalories = gainWeight? (totalDailyCalories * 0.31) : (totalDailyCalories * 0.35);
            double snackCalories = gainWeight? (totalDailyCalories * 0.07) : (totalDailyCalories * 0.05);
            breakfastCalories = Math.round(breakfastCalories * 100.0) / 100.0;
            lunchCalories = Math.round(lunchCalories * 100.0) / 100.0;
            dinnerCalories = Math.round(dinnerCalories * 100.0) / 100.0;
            snackCalories = Math.round(snackCalories * 100.0) / 100.0;
            
            Meals breakfastMeal = new Meals(Meals.MealType.Breakfast, createdMealPlanDetails);
            Meals lunchMeal = new Meals(Meals.MealType.Lunch, createdMealPlanDetails);
            Meals dinnerMeal = new Meals(Meals.MealType.Dinner, createdMealPlanDetails);
            Meals snackMeal = new Meals(Meals.MealType.Snack, createdMealPlanDetails);
            // Create meals for the meal plan
            mealsDao.create(breakfastMeal);
            mealsDao.create(lunchMeal);
            mealsDao.create(dinnerMeal);
            mealsDao.create(snackMeal);
            
            req.setAttribute("totalCalories", totalDailyCalories);
            req.setAttribute("breakfastMealId", breakfastMeal.getMealId());
            req.setAttribute("lunchMealId", lunchMeal.getMealId());
            req.setAttribute("dinnerMealId", dinnerMeal.getMealId());
            req.setAttribute("snackMealId", snackMeal.getMealId());
            req.setAttribute("breakfastCalories", breakfastCalories);
            req.setAttribute("lunchCalories", lunchCalories);
            req.setAttribute("dinnerCalories", dinnerCalories);
            req.setAttribute("snackCalories", snackCalories);
            req.setAttribute("username", username);
            
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("username", username);
            req.getSession().setAttribute("totalCalories", totalDailyCalories);
            req.getSession().setAttribute("breakfastMealId", breakfastMeal.getMealId());
            req.getSession().setAttribute("lunchMealId", lunchMeal.getMealId());
            req.getSession().setAttribute("dinnerMealId", dinnerMeal.getMealId());
            req.getSession().setAttribute("snackMealId", snackMeal.getMealId());
            req.getSession().setAttribute("breakfastCalories", breakfastCalories);
            req.getSession().setAttribute("lunchCalories", lunchCalories);
            req.getSession().setAttribute("dinnerCalories", dinnerCalories);
            req.getSession().setAttribute("snackCalories", snackCalories);

            messages.put("title", "Meal Plan for " + username);
           // req.getRequestDispatcher("/foodselection").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.getRequestDispatcher("/CaloriesDisplay.jsp").forward(req, resp);
        //req.getRequestDispatcher("/foodselection").forward(req, resp); // 把attributes都计算好的request发到food servlet
    }
}
