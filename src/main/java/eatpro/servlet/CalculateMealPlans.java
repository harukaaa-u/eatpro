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

        String userName = req.getParameter("username");
//        if (userName == null || userName.trim().isEmpty()) {
//            messages.put("title", "Invalid username.");
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/foodselection"); // 要改！可能可以发到某个jsp来display error
//            dispatcher.forward(req, resp);
//            return;
//        }
// create 一个meal plan detail => 
        // 拿回meal plan detail id
        // 建4个meals
        try {
            Users user = usersDao.getUserByUserName(userName);
            if (user == null) {
                messages.put("title", "User not found: " + userName);
            }
            int mealPlanId = Integer.parseInt(req.getParameter("MealPlanId"));
            boolean gainWeight = user.isGainWeight();
         // Create MealPlanDetails
            MealPlanDetails mealPlanDetails = new MealPlanDetails(mealPlanId);
            MealPlanDetails createdMealPlanDetails = mealPlanDetailsDao.create(mealPlanDetails);

            // Calculate calorie distribution for each meal
            int totalDailyCalories = Integer.parseInt(req.getParameter("Calories")); // Example value, adjust as needed => retrieve from parameter
            double breakfastCalories = gainWeight? (totalDailyCalories * 0.31) : (totalDailyCalories * 0.25);
            double lunchCalories = gainWeight? (totalDailyCalories * 0.31) : (totalDailyCalories * 0.35);
            double dinnerCalories = gainWeight? (totalDailyCalories * 0.31) : (totalDailyCalories * 0.35);
            double snackCalories = gainWeight? (totalDailyCalories * 0.07) : (totalDailyCalories * 0.05);
            
            Meals breakfastMeal = new Meals(Meals.MealType.Breakfast, createdMealPlanDetails.getMealPlanDetailId());
            Meals lunchMeal = new Meals(Meals.MealType.Lunch, createdMealPlanDetails.getMealPlanDetailId());
            Meals dinnerMeal = new Meals(Meals.MealType.Dinner, createdMealPlanDetails.getMealPlanDetailId());
            Meals snackMeal = new Meals(Meals.MealType.Snack, createdMealPlanDetails.getMealPlanDetailId());
            // Create meals for the meal plan
            mealsDao.create(breakfastMeal);
            mealsDao.create(lunchMeal);
            mealsDao.create(dinnerMeal);
            mealsDao.create(snackMeal);
            
            // Forward to FoodSelectionServlet with meal details as attributes
            req.setAttribute("breakfastMealId", breakfastMeal.getMealId());
            req.setAttribute("lunchMealId", lunchMeal.getMealId());
            req.setAttribute("dinnerMealId", dinnerMeal.getMealId());
            req.setAttribute("snackMealId", snackMeal.getMealId());
            
            req.setAttribute("breakfastCalories", breakfastCalories);
            req.setAttribute("lunchCalories", lunchCalories);
            req.setAttribute("dinnerCalories", dinnerCalories);
            req.setAttribute("snackCalories", snackCalories);
            req.setAttribute("userName", userName); // Pass along the userName if needed

            messages.put("title", "Meal Plan for " + userName);
           // req.getRequestDispatcher("/foodselection").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.getRequestDispatcher("/Test.jsp").forward(req, resp);
        //req.getRequestDispatcher("/foodselection").forward(req, resp); // 把attributes都计算好的request发到food servlet
    }
}
