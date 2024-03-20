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

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
        mealPlansDao = MealPlansDao.getInstance();
    }

    // MealPlan 
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("title", "Invalid username.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/foodselection"); // 要改！可能可以发到某个jsp来display error
            dispatcher.forward(req, resp);
            return;
        }
// create 一个meal plan detail => 
        // 拿回meal plan detail id
        // 建4个meals
        try {
            Users user = usersDao.getUserByUserName(userName);
            if (user == null) {
                messages.put("title", "User not found: " + userName);
            } else {
                int totalDailyCalories = 2000; // This should come from the Daily Calorie Intake Servlet or user adjustments.
                boolean gainWeight = user.isGainWeight();
                //distribution公式还要改
                int breakfastCalories = (int) (totalDailyCalories * 0.25);
                int lunchCalories = (int) (totalDailyCalories * 0.35);
                int dinnerCalories = (int) (totalDailyCalories * 0.35);
                int snackCalories = (int) (totalDailyCalories * 0.05);

                // 在request里改attribute
                req.setAttribute("breakfastCalories", breakfastCalories);
                req.setAttribute("lunchCalories", lunchCalories);
                req.setAttribute("dinnerCalories", dinnerCalories);
                req.setAttribute("snackCalories", snackCalories);

                messages.put("title", "Meal Plan for " + userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        req.getRequestDispatcher("/foodselection").forward(req, resp); // 把parameter都计算好的request发到food servlet
    }
}
