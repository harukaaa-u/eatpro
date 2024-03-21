package eatpro.servlet;

import eatpro.dal.*;
import eatpro.model.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
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

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
        userAdjustmentsDao = UserAdjustmentsDao.getInstance();
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
        //String username = (String) req.getAttribute("username");
        // 新修改：Retrieve the UserAdjustments object from session
        UserAdjustments userAdjustments = (UserAdjustments) req.getSession().getAttribute("userAdjustment");

        if (userAdjustments == null) {
            messages.put("failure", "User adjustments not found.");
            // 可能需要转发到另一个页面或显示错误消息
            //req.getRequestDispatcher("/errorPage.jsp").forward(req, resp);
            return;
        }
        
        String userName = req.getParameter("username");
        System.out.println("Setting userName attribute: " + userName);
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("failure", "Invalid username.");
//            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        try {
            Users user = usersDao.getUserByUserName(userName);
            if (user == null) {
                messages.put("failure", "User not found: " + userName);
//                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                return;
            }
            
            // ?? list of UserAdjustments OR UserAdjustments
//            UserAdjustments userAdjustments = userAdjustmentsDao.getAdjustmentByUserName(userName);
            
            int totalCalories = calculateTotalCalories(user, userAdjustments);
            messages.put("success", "Total Daily Calorie Intake: " + totalCalories + " calories");

            // Create a MealPlan object based on the calculated total daily calories
            MealPlans newMealPlan = new MealPlans(user, userAdjustments, totalCalories);
            MealPlans createdMealPlan = mealPlansDao.create(newMealPlan);
            req.setAttribute("userName", userName);
            req.setAttribute("mealPlan", createdMealPlan);            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.getRequestDispatcher("/mealplanning").forward(req, resp); 
    }

    private int calculateTotalCalories(Users user, UserAdjustments userAdjustments) throws SQLException {
        // Use a constant to represent the BMR for an average adult.
        double bmrConstant = 10 * user.getInitialWeight() + 6.25 * user.getHeight() - 5;
        double dailyCalorieNeeds = user.isGainWeight() ? bmrConstant * 1.1 : bmrConstant; 
        dailyCalorieNeeds += userAdjustments.getExpectedExerciseCalorie();
        int totalCalories = (int)dailyCalorieNeeds;
        return totalCalories;
    }
}