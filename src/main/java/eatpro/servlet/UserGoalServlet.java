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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/usergoal")
public class UserGoalServlet extends HttpServlet {

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

        // Fetch the action from the request.
        String action = req.getParameter("action");

        String userName = req.getParameter("username");
        Users user = usersDao.getUserByUserName(userName);
        String goalType = req.getParameter("goaltype");
        Date targetDate = Date.valueOf(req.getParameter("targetdate"));
        double targetValue = Double.parseDouble(req.getParameter("targetvalue"));
        String status = req.getParameter("status");

        try {
            if ("create".equals(action)) {
                UserGoals goal = new UserGoals(user, goalType, targetDate, targetValue, status);
                goal = userGoalsDao.create(goal);
                messages.put("success", "Successfully created a new goal for " + userName);
            } else if ("update".equals(action)) {
                // For updating, you would need the goal ID as well
                int goalId = Integer.parseInt(req.getParameter("goalid"));
                UserGoals goal = userGoalsDao.updateGoalById(goalId, new UserGoals(user, goalType, targetDate, targetValue, status));
                if(goal != null) {
                    messages.put("success", "Successfully updated goal for " + userName);
                } else {
                    messages.put("fail", "Failed to update goal for " + userName);
                }
            } else if ("delete".equals(action)) {
                int goalId = Integer.parseInt(req.getParameter("goalid"));
                userGoalsDao.delete(goalId);
                messages.put("success", "Deleted goal successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        req.getRequestDispatcher("/UserGoal.jsp").forward(req, resp);
    }
}
