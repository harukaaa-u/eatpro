package eatpro.dal;

import eatpro.model.Meals;
import eatpro.model.Meals.MealType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MealsDao {
    protected ConnectionManager connectionManager;

    private static MealsDao instance = null;
    protected MealsDao() {
        connectionManager = new ConnectionManager();
    }
    public static MealsDao getInstance() {
        if(instance == null) {
            instance = new MealsDao();
        }
        return instance;
    }

    public Meals create(Meals meal) throws SQLException {
        String insertMeal = "INSERT INTO Meals(MealType, MealPlanDetailId) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMeal,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, meal.getMealType().toString());
            insertStmt.setInt(2, meal.getMealPlanDetailId());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it to the Meal instance
            resultKey = insertStmt.getGeneratedKeys();
            int mealId = 0;
            if(resultKey.next()) {
                mealId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            meal.setMealId(mealId);
            return meal;
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

    public Meals getMealById(int mealId) throws SQLException {
        String selectMeal = "SELECT MealId, MealType, MealPlanDetailId FROM Meals WHERE MealId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMeal);
            selectStmt.setInt(1, mealId);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultMealId = results.getInt("MealId");
                String mealType = results.getString("MealType");
                int mealPlanDetailId = results.getInt("MealPlanDetailId");
                Meals meal = new Meals(resultMealId, MealType.valueOf(mealType), mealPlanDetailId);
                return meal;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(results != null) {
                results.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public Meals update(Meals meal) throws SQLException {
        String updateMeal = "UPDATE Meals SET MealType=?, MealPlanDetailId=? WHERE MealId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateMeal);
            updateStmt.setString(1, meal.getMealType().toString());
            updateStmt.setInt(2, meal.getMealPlanDetailId());
            updateStmt.setInt(3, meal.getMealId());
            updateStmt.executeUpdate();

            // Update the Meal instance
            return meal;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(updateStmt != null) {
                updateStmt.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }

public List<MealDetails> getMealDetailsByMealsId(int mealsId) throws SQLException {
    List<MealDetails> mealDetailsList = new ArrayList<>();
    String selectMealDetails =
        "SELECT MealDetailId, MealId, FoodId " +
        "FROM MealDetails " +
        "WHERE MealId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    MealsDao mealsDao = MealsDao.getInstance();
    FoodDao foodDao = FoodDao.getInstance();
    try {
        connection = connectionManager.getConnection();
        selectStmt = connection.prepareStatement(selectMealDetails);
        selectStmt.setInt(1, mealsId);
        results = selectStmt.executeQuery();
        while (results.next()) {
            int mealDetailId = results.getInt("MealDetailId");
            int mealId = results.getInt("MealId");
            int foodId = results.getInt("FoodId");

            Meals meal = mealsDao.getMealById(mealId); // Retrieve the associated Meals instance.
            Food food = foodDao.getFoodById(foodId); // Retrieve the associated Food instance.
            MealDetails mealDetail = new MealDetails(mealDetailId, meal, food);
            mealDetailsList.add(mealDetail);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    } finally {
        if (results != null) results.close();
        if (selectStmt != null) selectStmt.close();
        if (connection != null) connection.close();
    }
    return mealDetailsList;
}



    public Meals delete(Meals meal) throws SQLException {
        String deleteMeal = "DELETE FROM Meals WHERE MealId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteMeal);
            deleteStmt.setInt(1, meal.getMealId());
            deleteStmt.executeUpdate();

            // Return null since the Meal has been deleted
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(deleteStmt != null) {
                deleteStmt.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }
}
