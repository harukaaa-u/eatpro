package eatpro.dal;
import eatpro.model.*;

import eatpro.model.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDao {
    // Reference to the database connection manager, assuming you have one.
    protected ConnectionManager connectionManager;
    
    // Singleton pattern: making the constructor private and providing a getInstance method.
    private static UsersDao instance = null;
    protected UsersDao() {
        connectionManager = new ConnectionManager();
    }
    public static UsersDao getInstance() {
        if(instance == null) {
            instance = new UsersDao();
        }
        return instance;
    }

    public Users create(Users user) throws SQLException {
        String insertUser = "INSERT INTO Users(UserName, Password, InitialWeight, Height, GainWeight) VALUES(?,?,?,?,?);";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(insertUser)) {
            insertStmt.setString(1, user.getUserName());
            insertStmt.setString(2, user.getPassword());
            insertStmt.setDouble(3, user.getInitialWeight());
            insertStmt.setDouble(4, user.getHeight());
            insertStmt.setBoolean(5, user.isGainWeight());
            insertStmt.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public Users getUserByUserName(String userName) throws SQLException {
        String selectUser = "SELECT UserName, Password, InitialWeight, Height, GainWeight FROM Users WHERE UserName=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectUser)) {
            selectStmt.setString(1, userName);
            try (ResultSet results = selectStmt.executeQuery()) {
                if(results.next()) {
                    String resultUserName = results.getString("UserName");
                    String password = results.getString("Password");
                    double initialWeight = results.getDouble("InitialWeight");
                    double height = results.getDouble("Height");
                    boolean gainWeight = results.getBoolean("GainWeight");
                    Users user = new Users(resultUserName, password, initialWeight, height, gainWeight);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }
    
    public Users updateInitialWeight(String userName, double newInitialWeight) throws SQLException {
        String updateWeight = "UPDATE Users SET InitialWeight=? WHERE UserName=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateWeight)) {
            updateStmt.setDouble(1, newInitialWeight);
            updateStmt.setString(2, userName);
            updateStmt.executeUpdate();
            
            return getUserByUserName(userName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public Users updateGainWeight(String userName, boolean newGainWeight) throws SQLException {
        String updateGainWeight = "UPDATE Users SET GainWeight=? WHERE UserName=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateGainWeight)) {
            updateStmt.setBoolean(1, newGainWeight);
            updateStmt.setString(2, userName);
            updateStmt.executeUpdate();
            
            return getUserByUserName(userName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Users delete(Users user) throws SQLException {
        String deleteUser = "DELETE FROM Users WHERE UserName=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement deleteStmt = connection.prepareStatement(deleteUser)) {
            deleteStmt.setString(1, user.getUserName());
            deleteStmt.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
