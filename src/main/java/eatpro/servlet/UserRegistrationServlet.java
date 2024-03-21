package eatpro.servlet;

import eatpro.dal.UsersDao;
import eatpro.model.Users;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userregistration")
public class UserRegistrationServlet extends HttpServlet {
	private static final double KG_LBS_CONVERSION = 0.453592;
    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Just forward to the registration JSP.
        req.getRequestDispatcher("/UserRegistration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        // Retrieve and validate the input parameters.
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        double initialWeight = Double.parseDouble(req.getParameter("initialweight")) * KG_LBS_CONVERSION;
        int feet = Integer.parseInt(req.getParameter("feet"));
        int inches = Integer.parseInt(req.getParameter("inches"));
        double height = (feet * 12) + inches;
        //double height = Double.parseDouble(req.getParameter("height"));
        boolean gainWeight = Boolean.parseBoolean(req.getParameter("gainweight"));

        if (userName == null || userName.trim().isEmpty()) {
            messages.put("Fail", "Invalid Username");
        } else {
        	 try {
                 if (usersDao.usernameExists(userName)) {
                     messages.put("Fail", "Username already taken.");
                 } else {
                     Users user = new Users(userName, password, initialWeight, height, gainWeight);
                     user = usersDao.create(user);
                     messages.put("success", "Successfully created " + userName);
                 }
             }  catch (Exception e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }

        req.getRequestDispatcher("/UserRegistration.jsp").forward(req, resp);
    }
}
