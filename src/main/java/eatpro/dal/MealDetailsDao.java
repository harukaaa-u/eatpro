package eatpro.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import eatpro.model.MealDetails;
import eatpro.model.Meals;
import eatpro.model.Food;

public class MealDetailsDao {
    protected ConnectionManager connectionManager;

    private static MealDetailsDao instance = null;
    protected MealDetailsDao() {
        connectionManager = new ConnectionManager();
    }
    public static MealDetailsDao getInstance() {
        if(instance == null) {
            instance = new MealDetailsDao();
        }
        return instance;
    }

    public MealDetails create(MealDetails mealDetails) throws SQLException {
        String insertMealDetails =
                "INSERT INTO MealDetails(MealId, FoodId) " +
                        "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMealDetails,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, mealDetails.getMeal().getMealId());
            insertStmt.setInt(2, mealDetails.getFood().getFoodId());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            int mealDetailId = -1;
            if(resultKey.next()) {
                mealDetailId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            mealDetails.setMealDetailId(mealDetailId);
            return mealDetails;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(resultKey != null) {
                resultKey.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }


    public MealDetails getMealDetailsById(int mealDetailId) throws SQLException {
        String selectMealDetails =
                "SELECT MealDetailId, MealId, FoodId " +
                        "FROM MealDetails " +
                        "WHERE MealDetailId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        MealsDao mealsDao = MealsDao.getInstance();
        FoodDao foodDao = FoodDao.getInstance();
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMealDetails);
            selectStmt.setInt(1, mealDetailId);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultMealDetailId = results.getInt("MealDetailId");
                int mealId = results.getInt("MealId");
                int foodId = results.getInt("FoodId");

                Meals meal = mealsDao.getMealById(mealId);
                Food food = foodDao.getFoodById(foodId);
                MealDetails mealDetails = new MealDetails(resultMealDetailId, food, meal);
                return mealDetails;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return null;
    }

    // Delete the MealDetails instance.
    public MealDetails delete(MealDetails mealDetails) throws SQLException {
        String deleteMealDetails = "DELETE FROM MealDetails WHERE MealDetailId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteMealDetails);
            deleteStmt.setInt(1, mealDetails.getMealDetailId());
            deleteStmt.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(deleteStmt != null) deleteStmt.close();
            if(connection != null) connection.close();
        }
    }
}
