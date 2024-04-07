package eatpro.servlet;

import eatpro.model.UserAdjustments;
import eatpro.model.Users;
import eatpro.dal.UserAdjustmentsDao;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/userAdjustmentsDisplay")
public class DisplayUserAdjustments extends HttpServlet {

    protected UserAdjustmentsDao userAdjustmentsDao;

    @Override
    public void init() throws ServletException {
        userAdjustmentsDao = UserAdjustmentsDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Users user = (Users) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("HomePage.jsp"); // Or your login page
            return;
        }

        try {
            List<UserAdjustments> adjustments = userAdjustmentsDao.getAllAdjustmentsByUserName(user.getUserName());
            req.setAttribute("adjustments", adjustments);
            req.getRequestDispatcher("/UserAdjustmentsDisplay.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving user adjustments", e);
        }
    }
}

