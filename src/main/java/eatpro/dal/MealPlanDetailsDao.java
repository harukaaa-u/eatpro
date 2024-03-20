package eatpro.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import eatpro.model.Users;
import eatpro.model.UserAdjustments;
import eatpro.model.MealPlans;
import eatpro.model.MealPlanDetails;

public class MealPlanDetailsDao {
  protected ConnectionManager connectionManager;

  private static MealPlanDetailsDao instance = null;
  protected MealPlanDetailsDao() {
    connectionManager = new ConnectionManager();
  }
  public static MealPlanDetailsDao getInstance() {
    if(instance == null) {
      instance = new MealPlanDetailsDao();
    }
    return instance;
  }

  public MealPlanDetails create(MealPlanDetails mealPlanDetails) throws SQLException {
    String insertMealPlanDetails =
        "INSERT INTO MealPlanDetails(MealPlanId) " +
            "VALUES(?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      // MealPlanDetails has an auto-generated key. So we want to retrieve that key.
      insertStmt = connection.prepareStatement(insertMealPlanDetails,
          Statement.RETURN_GENERATED_KEYS);
      insertStmt.setInt(1, mealPlanDetails.getMealPlan().getMealPlanId());
      insertStmt.executeUpdate();

      // Retrieve the auto-generated key and set it, so it can be used by the caller.
      // For more details, see:
      // http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
      resultKey = insertStmt.getGeneratedKeys();
      int mealPlanDetailsId = -1;
      if(resultKey.next()) {
    	  mealPlanDetailsId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      mealPlanDetails.setMealPlanDetailId(mealPlanDetailsId);
      return mealPlanDetails;
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

  /**
   * Get the MealPlanDetails record by fetching it from your MySQL instance.
   * This runs a SELECT statement and returns a single mealPlans instance.
   * Note that we use Users and Adjustments to retrieve the referenced mealPlans instance.
   * One alternative (possibly more efficient) is using a single SELECT statement
   * to join the mealPlans, user tables and then build each object.
   */
  public MealPlanDetails getMealPlansDetailsById(int MealPlanDetailsId) throws SQLException {
    String selectMealPlanDetails =
        "SELECT MealPlanDetailsId, MealPlanId" +
            "FROM MealPlanDetails " +
            "WHERE MealPlanDetailsId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMealPlanDetails);
      selectStmt.setInt(1, MealPlanDetailsId);
      results = selectStmt.executeQuery();
      MealPlansDao mealPlansDao = MealPlansDao.getInstance();
      if(results.next()) {
        int resultMealPlanDetailsId = results.getInt("MealPlanDetailsId");
        int mealPlansId = results.getInt("MealPlansId");
        MealPlans mealPlans = mealPlansDao.getMealPlansById(mealPlansId);
        MealPlanDetails mealPlanDetails = new MealPlanDetails(resultMealPlanDetailsId, mealPlans);
        return mealPlanDetails;
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

  /**
   * Get the all the mealPlanDetails for a MealPlan.
   */
  public List<MealPlanDetails> getMealPlanDetailsByMealPlanId(Integer mealPlansId) throws SQLException {
    List<MealPlanDetails> mealPlanDetails = new ArrayList<MealPlanDetails>();
    String selectMealPlanDetails =
        "SELECT MealPlanDetailId, MealPlanId" +
            "FROM MealPlanDetails " +
            "WHERE MealPlanId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMealPlanDetails);
      selectStmt.setInt(1, mealPlansId);
      results = selectStmt.executeQuery();
      MealPlansDao mealPlansDao = MealPlansDao.getInstance();
      while(results.next()) {
        int mealPlansDetailsId = results.getInt("MealPlanDetailsId");
        MealPlans mealPlan = mealPlansDao.getMealPlansById(mealPlansId);
        MealPlanDetails mealPlanDetail = new MealPlanDetails(mealPlansDetailsId, mealPlan);
        mealPlanDetails.add(mealPlanDetail);
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
    return mealPlanDetails;
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
  

  /**
   * Delete the MealPlanDetails instance.
   * This runs a DELETE statement.
   */
  public MealPlanDetails delete(MealPlanDetails mealPlanDetails) throws SQLException {
    // Note: BlogComments has a fk constraint on MealPlans with the reference option
    // ON DELETE CASCADE. So this delete operation will delete all the referencing
    // BlogComments.
    String deleteMealPlanDetails = "DELETE FROM MealPlanDetails WHERE MealPlanDetailId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteMealPlanDetails);
      deleteStmt.setInt(1, mealPlanDetails.getMealPlanDetailId());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Recommendations instance.
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
}
