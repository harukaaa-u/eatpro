package eatpro.servlet;
import eatpro.dal.*;
import eatpro.model.*;
import eatpro.model.UserGoals.GoalType;
import eatpro.model.UserGoals.Status;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/useradjustment")
public class UserAdjustmentServlet extends HttpServlet {
  public static double LBS_TO_KG = 0.453592;
  protected UserAdjustmentsDao userAdjustmentsDao;
  protected UsersDao usersDao;
  protected UserGoalsDao userGoalsDao;

  @Override
  public void init() throws ServletException {
	userGoalsDao = UserGoalsDao.getInstance();
    userAdjustmentsDao = UserAdjustmentsDao.getInstance();
    usersDao = UsersDao.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException{
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    req.getRequestDispatcher("/UserAdjustment.jsp").forward(req, resp);

  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate the necessary input data.
    // get user from session
    Users user = (Users) req.getSession().getAttribute("user");
    String username = user.getUserName();
    String dateLoggedStr = req.getParameter("datelogged");
    String weightStr = req.getParameter("weight");
    String workoutTodayStr = req.getParameter("workouttoday");
    String expectedExerciseCalorieStr = req.getParameter("expectedexercisecalorie");
    String redirectURL = "/userAdjustmentsDisplay";
    
    // Validate the input.
    if (username == null || username.trim().isEmpty() ||
        dateLoggedStr == null || dateLoggedStr.trim().isEmpty() ||
        weightStr == null || weightStr.trim().isEmpty() ||
        workoutTodayStr == null || expectedExerciseCalorieStr == null) {
      messages.put("failed", "Invalid input.");
    } else {
      try {
        // Parse the dateLogged, weight, workoutToday, and expectedExerciseCalorie.
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateLogged = new Date(dateFormat.parse(dateLoggedStr).getTime());
        Double weight = Double.valueOf(weightStr) * LBS_TO_KG;
        Boolean workoutToday = Boolean.valueOf(workoutTodayStr);
        Integer expectedExerciseCalorie = Integer.valueOf(expectedExerciseCalorieStr);

        redirectURL = handleUserAdjustmentCreationAndUpdateGoalStatus(
                user, dateLogged, weight, workoutToday, expectedExerciseCalorie, messages);
      } catch (ParseException e) {
        messages.put("success", "Invalid date format. Please use yyyy-MM-dd.");
      } catch (NumberFormatException e) {
        messages.put("success", "Invalid number format.");
      } catch (SQLException e) {
        messages.put("success", "Database error.");
        e.printStackTrace();
      }
    }
    if (messages.containsKey("goalMessage")) {
        req.getSession().setAttribute("goalMessage", messages.get("goalMessage"));
    }
    if (messages.containsKey("error")) {
        req.getSession().setAttribute("error", messages.get("error"));
    }
    req.getSession().setAttribute("user", user);
    req.setAttribute("username", username);
    resp.sendRedirect(req.getContextPath() + redirectURL);
    //resp.sendRedirect(req.getContextPath() + "/userAdjustmentsDisplay");
  }


	private String handleUserAdjustmentCreationAndUpdateGoalStatus(Users user, Date dateLogged, Double weight, Boolean workoutToday, Integer expectedExerciseCalorie,
	        Map<String, String> messages) throws SQLException {

		 UserAdjustments userAdjustment = new UserAdjustments(user, dateLogged, weight, workoutToday, expectedExerciseCalorie);
		   
		    userAdjustment = userAdjustmentsDao.create(userAdjustment);
		    UserGoals latestGoal = userGoalsDao.getGoalByUser(user.getUserName());
		    String redirectURL = "/userAdjustmentsDisplay"; // Default redirect URL

		    if (latestGoal != null) {
		        boolean isAchieved = false;
		        if (latestGoal.getGoalType() == GoalType.WEIGHTLOSS && weight <= (latestGoal.getTargetValue() + 0.1) ||
		            latestGoal.getGoalType() == GoalType.GAINWEIGHT && weight >= (latestGoal.getTargetValue() - 0.1)) {
		            if (dateLogged.compareTo(latestGoal.getTargetDate()) <= 0) {
		                isAchieved = true;
		                // Update goal status to ACHIEVED
		                userGoalsDao.updateStatus(latestGoal.getGoalId(), UserGoals.Status.ACHIEVED);
		                messages.put("goalMessage", "Congratulations! You achieved your goal! Now go eat some ice cream, then come back and create your next goal!");
		                redirectURL = "/usergoal";
		            }
		        }

		        if (!isAchieved && dateLogged.compareTo(latestGoal.getTargetDate()) > 0) {
		            // Update goal status to FAILED if the target date has passed and goal is not achieved
		            userGoalsDao.updateStatus(latestGoal.getGoalId(), UserGoals.Status.FAILED);
		            messages.put("goalMessage", "Target date has passed, Don't worry though! Take a break and come back for your next goal!");
		            redirectURL = "/usergoal";
		        }
		    } else {
		        messages.put("error", "You do not have an active goal. Please create a goal first.");
		        redirectURL = "/usergoal";
		    }
		    return redirectURL;
	}
}