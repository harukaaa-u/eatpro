package eatpro.servlet;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eatpro.dal.UserAdjustmentsDao;
import eatpro.model.UserAdjustments;

@WebServlet("/deleteAdjustment")
public class DeleteAdjustment extends HttpServlet {
    protected UserAdjustmentsDao userAdjustmentsDao;

    @Override
    public void init() {
        userAdjustmentsDao = UserAdjustmentsDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int adjustmentId = Integer.parseInt(req.getParameter("adjustmentId"));

        try {
            UserAdjustments adjustmentToDelete = new UserAdjustments(adjustmentId);
            userAdjustmentsDao.delete(adjustmentToDelete);

            // req.getSession().setAttribute("deleteMessage", "Adjustment deleted successfully.");
        } catch (SQLException e) {
            throw new ServletException("SQL error while deleting adjustment", e);
        }
        resp.sendRedirect(req.getContextPath() + "/userAdjustmentsDisplay");

        //req.getRequestDispatcher("/userAdjustmentsDisplay").forward(req, resp);
    }
}