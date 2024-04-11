package eatpro.servlet;

import eatpro.dal.*;
import eatpro.model.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/usergoal")
public class UserGoalServlet extends HttpServlet {
	private static final double LBS_TO_KG = 0.453592;
    protected UserGoalsDao userGoalsDao;
    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        userGoalsDao = UserGoalsDao.getInstance();
        usersDao = UsersDao.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);
        
        String action = req.getParameter("action");
        if (action != null && action.equals("view")) {
            String userName = req.getParameter("username");
            try {
                List<UserGoals> goals = userGoalsDao.getGoalsByUserName(userName);
                req.setAttribute("goals", goals);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/UserGoal.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String action = req.getParameter("action");
        Users user = (Users) req.getSession().getAttribute("user");
        String userName = user.getUserName();
        UserGoals.GoalType goalType = UserGoals.GoalType.valueOf(req.getParameter("goaltype").toUpperCase());
        double targetValue = Double.parseDouble(req.getParameter("targetvalue"))* LBS_TO_KG;
        Date targetDate = Date.valueOf(req.getParameter("targetdate"));

        
        if ("create".equals(action)) {
            try {
            	// change existing active goals to onhold status
	            	List<UserGoals> activeGoals = userGoalsDao.getActiveGoalsByUserName(userName);
	                for (UserGoals goal : activeGoals) {
	                    userGoalsDao.updateStatus(goal.getGoalId(), UserGoals.Status.ONHOLD);
	                }
                    UserGoals newGoal = new UserGoals(user, goalType, targetDate, targetValue);
                    UserGoals createdGoal = userGoalsDao.create(newGoal);                   
                    if (createdGoal != null) {
                        messages.put("success", "Successfully created a new goal for " + userName);
                    } else {
                        messages.put("fail", "Failed to create a new goal for " + userName);
                    }
            } catch (SQLException e) {
                e.printStackTrace();
                messages.put("fail", "Database error occurred. Please try again.");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                messages.put("fail", "Invalid input for goal type.");
            }
        }
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("userGoalsDisplay");
    }
}
        

//        Users user = null;
//        try {
//            user = usersDao.getUserByUserName(userName);
//            if (user == null) { // Check if the user actually exists
//                messages.put("fail", "User not found: " + userName);
//                req.getRequestDispatcher("/UserGoal.jsp").forward(req, resp);
//                return; // Stop further execution
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            messages.put("fail", "Failed to retrieve user: " + userName);
//            req.getRequestDispatcher("/UserGoal.jsp").forward(req, resp);
//            return;
//        }
//        
//
//        try {
//            //UserGoals.Status status = UserGoals.Status.valueOf(req.getParameter("status").toUpperCase());
//
//            if ("create".equals(action)) {
////                UserGoals userGoal = new UserGoals(user, goalType, targetDate, targetValue);
////                UserGoals goal = userGoalsDao.create(goal);
//                UserGoals userGoal = new UserGoals(user, goalType, targetDate, targetValue);
//                UserGoals createdGoal = userGoalsDao.create(userGoal);
//                messages.put("success", "Successfully created a new goal for " + userName);
//                
//                
//                
//            } 
////                
////				else if ("update".equals(action)) {
////                int goalId = Integer.parseInt(req.getParameter("goalid"));
////                UserGoals goal = userGoalsDao.updateGoalById(goalId, new UserGoals(user, goalType, targetDate, targetValue, status));
////                if (goal != null) {
////                    messages.put("success", "Successfully updated goal for " + userName);
////                } else {
////                    messages.put("fail", "Failed to update goal for " + userName);
////                }
////            } else if ("delete".equals(action)) {
////                int goalId = Integer.parseInt(req.getParameter("goalid"));
////                boolean success = userGoalsDao.delete(goalId);
////                if (success) {
////                    messages.put("success", "Deleted goal successfully.");
////                } else {
////                    messages.put("fail", "Failed to delete goal.");
////                }
////            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            messages.put("fail", "Database error occurred. Please try again.");
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            messages.put("fail", "Invalid input. Please check your data and try again.");
//        }
//
//        req.getRequestDispatcher("/UserGoal.jsp").forward(req, resp);
//    }


//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        // Map for storing messages.
//        Map<String, String> messages = new HashMap<>();
//        req.setAttribute("messages", messages);
//
//        // Fetch the action from the request.
//        String action = req.getParameter("action");
//
//        String userName = req.getParameter("username");
//        Users user = usersDao.getUserByUserName(userName);
//        
//        UserGoals.GoalType goalType = UserGoals.GoalType.valueOf(req.getParameter("goaltype").toUpperCase());
//
//        Date targetDate = Date.valueOf(req.getParameter("targetdate"));
//        
//        double targetValue = Double.parseDouble(req.getParameter("targetvalue"));
//        
////        String status = req.getParameter("status");
//        UserGoals.Status status = UserGoals.Status.valueOf(req.getParameter("status").toUpperCase());
//
//        try {
//            if ("create".equals(action)) {
//                UserGoals goal = new UserGoals(user, goalType, targetDate, targetValue, status);
//                goal = userGoalsDao.create(goal);
//                messages.put("success", "Successfully created a new goal for " + userName);
//            } else if ("update".equals(action)) {
//                // For updating, you would need the goal ID as well
//                int goalId = Integer.parseInt(req.getParameter("goalid"));
//                UserGoals goal = userGoalsDao.updateGoalById(goalId, new UserGoals(user, goalType, targetDate, targetValue, status));
//                if(goal != null) {
//                    messages.put("success", "Successfully updated goal for " + userName);
//                } else {
//                    messages.put("fail", "Failed to update goal for " + userName);
//                }
//            } else if ("delete".equals(action)) {
//                int goalId = Integer.parseInt(req.getParameter("goalid"));
//                userGoalsDao.delete(goalId);
//                messages.put("success", "Deleted goal successfully.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IOException(e);
//        }
//
//        req.getRequestDispatcher("/UserGoal.jsp").forward(req, resp);
//    }
