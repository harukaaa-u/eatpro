package eatpro.servlet;
import eatpro.dal.*;
import eatpro.model.*;

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

  protected UserAdjustmentsDao userAdjustmentsDao;
  protected UsersDao usersDao;

  @Override
  public void init() throws ServletException {
    userAdjustmentsDao = UserAdjustmentsDao.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException{
    // Map for storing messages.
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
    String username = req.getParameter("username");
    String dateLoggedStr = req.getParameter("datelogged");
    String weightStr = req.getParameter("weight");
    String workoutTodayStr = req.getParameter("workouttoday");
    String expectedExerciseCalorieStr = req.getParameter("expectedexercisecalorie");
    // Validate the input.
    if (username == null || username.trim().isEmpty() ||
        dateLoggedStr == null || dateLoggedStr.trim().isEmpty() ||
        weightStr == null || weightStr.trim().isEmpty() ||
        workoutTodayStr == null || expectedExerciseCalorieStr == null) {
      messages.put("success", "Invalid input.");
    } else {
      try {
        // Fetch the user by username.
        Users user = usersDao.getUserByUserName(username);
        if (user == null) {
          messages.put("failure", "User does not exist.");
          req.getRequestDispatcher("/UserAdjustment.jsp").forward(req, resp);
          return;
        }

        // Parse the dateLogged, weight, workoutToday, and expectedExerciseCalorie.
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateLogged = new Date(dateFormat.parse(dateLoggedStr).getTime());
        Double weight = Double.valueOf(weightStr);
        Boolean workoutToday = Boolean.valueOf(workoutTodayStr);
        Integer expectedExerciseCalorie = Integer.valueOf(expectedExerciseCalorieStr);

        // Create the UserAdjustment.
        UserAdjustments userAdjustment = new UserAdjustments(user, dateLogged, weight, workoutToday, expectedExerciseCalorie);
        userAdjustment = userAdjustmentsDao.create(userAdjustment);
        messages.put("success", "Successfully created user adjustment for " + username);
        
        // 新修改：Store the UserAdjustments object in session
        req.getSession().setAttribute("userAdjustment", userAdjustment);
        
        // 可以添加一个redirect，Redirect to DailyCalorieIntake servlet，有bug可以删掉
        resp.sendRedirect("dailyintakecalculation");
        
      } catch (ParseException e) {
        messages.put("success", "Invalid date format. Please use yyyy-MM-dd.");
      } catch (NumberFormatException e) {
        messages.put("success", "Invalid number format.");
      } catch (SQLException e) {
        messages.put("success", "Database error.");
        e.printStackTrace();
      }
    }

    req.getRequestDispatcher("/UserAdjustment.jsp").forward(req, resp);
  }
}