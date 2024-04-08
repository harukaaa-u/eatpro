package eatpro.servlet;

import eatpro.dal.*;
import eatpro.model.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/dailyintakecalculation")
public class DailyCalorieIntake extends HttpServlet {

    protected UsersDao usersDao;
    protected UserAdjustmentsDao userAdjustmentsDao;
    protected MealPlansDao mealPlansDao;
    protected MealsDao mealsDao;
    protected MealPlanDetailsDao mealPlanDetailsDao;
    protected NutrientsDao nutrientsDao;
    protected UserGoalsDao userGoalsDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
        userAdjustmentsDao = UserAdjustmentsDao.getInstance();
        userGoalsDao = UserGoalsDao.getInstance();
        mealPlansDao = MealPlansDao.getInstance();
        mealsDao = MealsDao.getInstance();
        mealPlanDetailsDao = MealPlanDetailsDao.getInstance();
        nutrientsDao = NutrientsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);
       
        Users user = (Users) req.getSession().getAttribute("user");
        String username = user.getUserName();


        UserAdjustments userAdjustment = null;
        UserGoals userGoal = null;
        try {
            userAdjustment = userAdjustmentsDao.getLatestAdjustmentByUserName(username);
            userGoal = userGoalsDao.getGoalByUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("failure", "Database error occurred. Please try again.");
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            return;
        }

        if (userAdjustment == null) {
            messages.put("failure", "Please add your current weight first.");
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("/UserAdjustment.jsp").forward(req, resp);
            return;
        }

        if (userGoal == null) {
            messages.put("failure", "Please set your goal first.");
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("/UserGoal.jsp").forward(req, resp);
            return;
        }

        double currentWeight = userAdjustment != null ? userAdjustment.getWeight() : user.getInitialWeight();

        try {
        	int totalCalories = calculateTotalCalories(user, currentWeight, userGoal, userAdjustment);
            messages.put("success", "Total Daily Calorie Intake: " + totalCalories + " calories");

            // Create a MealPlan object based on the calculated total daily calories
            MealPlans newMealPlan = new MealPlans(user, userAdjustment, totalCalories);
            MealPlans createdMealPlan = mealPlansDao.create(newMealPlan);
            req.setAttribute("username", username);
            req.setAttribute("mealPlan", createdMealPlan);            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.getSession().setAttribute("user", user);
        req.getRequestDispatcher("/mealplanning").forward(req, resp); 
    }
    
    private int calculateTotalCalories(Users user, double currentWeight, UserGoals userGoal, UserAdjustments userAdjustment) throws SQLException {
        // Example user and goal data - replace with actual data
        double goalWeight = userGoal.getTargetValue(); // in kg
        java.util.Date goalDateUtil = new java.util.Date(userGoal.getTargetDate().getTime());
        LocalDate goalDate = goalDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        long daysUntilGoal = ChronoUnit.DAYS.between(today, goalDate);
        
        double weightDifference = currentWeight - goalWeight; // in kg
        double totalCalorieDifference = weightDifference * 7700; // 3500 calories per pound, 7700 calories per kg
        double dailyCalorieAdjustment = totalCalorieDifference / daysUntilGoal;

        double bmr = 10 * currentWeight + 6.25 * user.getHeight() - 5;
        double totalCalorieNeeds = bmr + userAdjustment.getExpectedExerciseCalorie();

        if (weightDifference < 0) { // to gain weight
            totalCalorieNeeds += Math.abs(dailyCalorieAdjustment);
        } else if (weightDifference > 0) { // to lose weight
            totalCalorieNeeds -= Math.abs(dailyCalorieAdjustment);
        }

        return (int)totalCalorieNeeds;
    }

//
//    private int calculateTotalCalories(Users user, UserAdjustments userAdjustments) throws SQLException {
//        // Use a constant to represent the BMR for an average adult.
//        double bmrConstant = 10 * user.getInitialWeight() + 6.25 * user.getHeight() - 5;
//        double dailyCalorieNeeds = user.isGainWeight() ? bmrConstant * 1.1 : bmrConstant; 
//        dailyCalorieNeeds += userAdjustments.getExpectedExerciseCalorie();
//        int totalCalories = (int)dailyCalorieNeeds;
//        return totalCalories;
//    }
}