package eatpro.dal;

import eatpro.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class UserAdjustmentsDao {
    protected ConnectionManager connectionManager;

    private static UserAdjustmentsDao instance = null;

    protected UserAdjustmentsDao() {
        connectionManager = new ConnectionManager();
    }
    public static UserAdjustmentsDao getInstance() {
        if(instance == null) {
            instance = new UserAdjustmentsDao();
        }
        return instance;
    }

    public UserAdjustments create(UserAdjustments userAdjustment) throws SQLException {
        String insertUserAdjustment = "INSERT INTO UserAdjustments(UserName, DateLogged, Weight, WorkoutToday, ExpectedExerciseCalorie) VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertUserAdjustment, PreparedStatement.RETURN_GENERATED_KEYS);

            insertStmt.setString(1, userAdjustment.getUser().getUserName());
            insertStmt.setDate(2, userAdjustment.getDateLogged());
            insertStmt.setDouble(3, userAdjustment.getWeight());
            insertStmt.setBoolean(4, userAdjustment.getWorkoutToday());
            insertStmt.setInt(5, userAdjustment.getExpectedExerciseCalorie());

            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int adjustmentId = 0;
            if(resultKey.next()) {
                adjustmentId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            userAdjustment.setAdjustmentId(adjustmentId);
            return userAdjustment;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
            if(resultKey != null) {
                resultKey.close();
            }
        }
    }

    public UserAdjustments getUserAdjustmentById(int adjustmentId) throws SQLException {
        String selectUserAdjustment = "SELECT AdjustmentId, UserName, DateLogged, Weight, WorkoutToday, ExpectedExerciseCalorie FROM UserAdjustments WHERE AdjustmentId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUserAdjustment);
            selectStmt.setInt(1, adjustmentId);
            UsersDao usersDao = UsersDao.getInstance();

            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultAdjustmentId = results.getInt("AdjustmentId");
                String userName = results.getString("UserName");
                Users user = usersDao.getUserByUserName(userName);
                Date dateLogged = results.getDate("DateLogged");
                Double weight = results.getDouble("Weight");
                Boolean workoutToday = results.getBoolean("WorkoutToday");
                Integer expectedExerciseCalorie = results.getInt("ExpectedExerciseCalorie");

                UserAdjustments userAdjustment = new UserAdjustments(resultAdjustmentId, user, dateLogged, weight, workoutToday, expectedExerciseCalorie);
                return userAdjustment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return null;
    }

    public UserAdjustments delete(UserAdjustments userAdjustment) throws SQLException {
        String deleteUserAdjustment = "DELETE FROM UserAdjustments WHERE AdjustmentId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUserAdjustment);
            deleteStmt.setInt(1, userAdjustment.getAdjustmentId());
            deleteStmt.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }

    public UserAdjustments updateWeight(UserAdjustments userAdjustment, double newWeight) throws SQLException {
        String updateWeight = "UPDATE UserAdjustments SET Weight=? WHERE AdjustmentId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateWeight);
            updateStmt.setDouble(1, newWeight);
            updateStmt.setInt(2, userAdjustment.getAdjustmentId());
            updateStmt.executeUpdate();

            userAdjustment.setWeight(newWeight);
            return userAdjustment;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(updateStmt != null) {
                updateStmt.close();
            }
        }
    }
}
