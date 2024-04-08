package eatpro.servlet;

import eatpro.model.*;
import eatpro.dal.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/userGoalsDisplay")
public class DisplayUserGoals extends HttpServlet {

    protected UserGoalsDao userGoalsDao;

    @Override
    public void init() throws ServletException {
    	userGoalsDao = UserGoalsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users user = (Users) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("HomePage.jsp");
            return;
        }

        try {
            List<UserGoals> goals = userGoalsDao.getGoalsByUserName(user.getUserName());
            req.setAttribute("goals", goals);
            req.getRequestDispatcher("/UserGoalsDisplay.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving user goals", e);
        }
    }
}

