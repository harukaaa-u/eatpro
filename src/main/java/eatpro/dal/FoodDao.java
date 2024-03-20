package eatpro.dal;
import eatpro.model.*;
import eatpro.model.Food.MealCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodDao {
  protected ConnectionManager connectionManager;
  private static FoodDao instance = null;
  protected FoodDao() {
    connectionManager = new ConnectionManager();
  }
  public static FoodDao getInstance() {
    if(instance == null) {
      instance = new FoodDao();
    }
    return instance;
  }
  public Food create(Food food) throws SQLException {
    String insertFood = "INSERT INTO Food (FoodId, Ingredients, ServingSize, ServingUnit, FoodCategory, MealCategory, FoodName) VALUES (?, ?, ?, ?, ?, ?, ?);";
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement insertStmt = connection.prepareStatement(insertFood)) {
      insertStmt.setInt(1, food.getFoodId());
      insertStmt.setString(2, food.getIngredients());
      insertStmt.setString(3, food.getServingSize());
      insertStmt.setString(4, food.getServingUnit());
      insertStmt.setString(5, food.getFoodCategory());
      insertStmt.setString(6, food.getMealCategory().toString());
      insertStmt.setString(7, food.getFoodName());
      insertStmt.executeUpdate();
      return food;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }
  public Food getFoodById(int foodId) throws SQLException {
    String selectFood = "SELECT FoodId, Ingredients, ServingSize, ServingUnit, FoodCategory, MealCategory, FoodName FROM Food WHERE FoodId = ?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectFood);
      selectStmt.setInt(1, foodId);

      results = selectStmt.executeQuery();

      if (results.next()) {
        int resultFoodId = results.getInt("FoodId");
        String ingredients = results.getString("Ingredients");
        String servingSize = results.getString("ServingSize");
        String servingUnit = results.getString("ServingUnit");
        String foodCategory = results.getString("FoodCategory");
        String mealCategory = results.getString("MealCategory");
        String foodName = results.getString("FoodName");

        MealCategory trueMealCategory = MealCategory.valueOf(mealCategory);
        Food food = new Food(resultFoodId, ingredients, servingSize, servingUnit, foodCategory, trueMealCategory, foodName);
        return food;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (results != null) {
        results.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    return null;
  }
  /**
   * Delete the Food instance.
   * This runs a DELETE statement.
   * @param food The Food instance to be deleted from the database.
   * @return null to indicate the Food instance has been deleted.
   * @throws SQLException If there is an issue executing the query.
   */
  public Food delete(Food food) throws SQLException {
    String deleteFood = "DELETE FROM Food WHERE FoodId = ?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteFood);

      deleteStmt.setInt(1, food.getFoodId());

      deleteStmt.executeUpdate();
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (deleteStmt != null) {
        deleteStmt.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }

  /**
   * get a random food when required a special meal and food category
   * @param mealCategory enum, meal category of food
   * @param foodCategory string,food category
   * @return a randome food which satisfied both conditions
   * @throws SQLException
   */
  public Food getRandomFoodByMealFoodCategory(MealCategory mealCategory, String foodCategory) throws SQLException {
    // SQL to fetch up to 50 food items matching the meal and food category condition at the same time
    String randomFood = "SELECT FoodId, Ingredients, ServingSize, ServingUnit, FoodCategory, MealCategory, FoodName FROM Food WHERE MealCategory = ? AND FoodCategory = ? ORDER BY RAND() LIMIT 50;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    List<Food> foodList = new ArrayList<>();
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(randomFood);
      selectStmt.setString(1, mealCategory.toString());
      selectStmt.setString(2, foodCategory);

      results = selectStmt.executeQuery();

      // Collect up to 50 matching food items
      while (results.next()) {
        int foodId = results.getInt("FoodId");
        String ingredients = results.getString("Ingredients");
        String servingSize = results.getString("ServingSize");
        String servingUnit = results.getString("ServingUnit");
        String fetchedFoodCategory = results.getString("FoodCategory");
        String fetchedMealCategory = results.getString("MealCategory");
        String foodName = results.getString("FoodName");

        MealCategory trueMealCategory = MealCategory.valueOf(fetchedMealCategory);
        Food food = new Food(foodId, ingredients, servingSize, servingUnit, fetchedFoodCategory, trueMealCategory, foodName);
        foodList.add(food);
      }

      // Randomly select one food item from the list, if not empty
      if (!foodList.isEmpty()) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(foodList.size());
        return foodList.get(randomIndex);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (results != null) {
        results.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
    return null;
  }
}
