package eatpro.servlet;

import eatpro.dal.UsersDao;
import eatpro.model.Users;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userlogin")
public class UserLoginServlet extends HttpServlet {
    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);
       
        req.getRequestDispatcher("/UserLogin.jsp").forward(req, resp);
    }
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Username and password cannot be empty.");
            req.getRequestDispatcher("/UserLogin.jsp").forward(req, resp);
            return;
        }

        try {
            Users user = usersDao.getUserByUserName(username);
            if (user == null) {
                req.setAttribute("error", "User with the given username does not exist.");
            } else if (!user.getPassword().equals(password)) {
                req.setAttribute("error", "Password is incorrect.");
            } else {
            	//successful log-in
            	req.getSession().setAttribute("user", user);
                resp.sendRedirect("LoggedInHomePage.jsp");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error occurred. Please try again.");
        }

        req.getRequestDispatcher("/HomePage.jsp").forward(req, resp);
    }
}
