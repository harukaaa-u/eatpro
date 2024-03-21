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

public class MealPlansDao {
  protected ConnectionManager connectionManager;

  private static MealPlansDao instance = null;
  protected MealPlansDao() {
    connectionManager = new ConnectionManager();
  }
  public static MealPlansDao getInstance() {
    if(instance == null) {
      instance = new MealPlansDao();
    }
    return instance;
  }

  public MealPlans create(MealPlans mealPlans) throws SQLException {
    String insertMealPlans =
        "INSERT INTO MealPlans(UserName,AdjustmentId, TotalCalorieForToday) " +
            "VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      // MealPlans has an auto-generated key. So we want to retrieve that key.
      insertStmt = connection.prepareStatement(insertMealPlans,
          Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, mealPlans.getUser().getUserName());
      insertStmt.setInt(2, mealPlans.getAdjustment().getAdjustmentId());
      insertStmt.setInt(3, mealPlans.getTotalCalorieForToday());
      insertStmt.executeUpdate();

      // Retrieve the auto-generated key and set it, so it can be used by the caller.
      // For more details, see:
      // http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
      resultKey = insertStmt.getGeneratedKeys();
      int mealPlanId = -1;
      if(resultKey.next()) {
    	  mealPlanId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      mealPlans.setMealPlanId(mealPlanId);
      return mealPlans;
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
   * Get the MealPlans record by fetching it from your MySQL instance.
   * This runs a SELECT statement and returns a single mealPlans instance.
   * Note that we use Users and Adjustments to retrieve the referenced mealPlans instance.
   * One alternative (possibly more efficient) is using a single SELECT statement
   * to join the mealPlans, user tables and then build each object.
   */
  public MealPlans getMealPlansById(int MealPlansId) throws SQLException {
    String selectMealPlans =
        "SELECT MealPlanId,UserName,AdjustmentId,TotalCalorieForToday " +
            "FROM MealPlans " +
            "WHERE MealPlanId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMealPlans);
      selectStmt.setInt(1, MealPlansId);
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      UserAdjustmentsDao userAdjustmentsDao = UserAdjustmentsDao.getInstance();
      if(results.next()) {
        int resultMealPlansId = results.getInt("MealPlanId");
        String userName = results.getString("UserName");
        int userAdjustmentsId = results.getInt("AdjustmentId");
        int totalCalorieForToday = results.getInt("TotalCalorieForToday");
        Users user = usersDao.getUserByUserName(userName);
        UserAdjustments userAdjustments = userAdjustmentsDao.getUserAdjustmentById(userAdjustmentsId);
        MealPlans mealPlans = new MealPlans(resultMealPlansId, user, userAdjustments, totalCalorieForToday);
        return mealPlans;
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
   * Get the all the mealPlans for a user.
   */
  public List<MealPlans> getMealPlansByUserName(String userName) throws SQLException {
    List<MealPlans> mealPlans = new ArrayList<MealPlans>();
    String selectMealPlans =
        "SELECT MealPlansId,UserName,AdjustmentId,TotalCalorieForToday " +
            "FROM MealPlans " +
            "WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMealPlans);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      UserAdjustmentsDao userAdjustmentsDao = UserAdjustmentsDao.getInstance();
      while(results.next()) {
        int mealPlansId = results.getInt("MealPlansId");
        int adjustmentId = results.getInt("AdjustmenttId");
        int totalCalorieForToday = results.getInt("TotalCalorieForToday");
        Users user = usersDao.getUserByUserName(userName);
        UserAdjustments userAdjustments = userAdjustmentsDao.getUserAdjustmentById(adjustmentId);
        MealPlans mealPlan = new MealPlans(mealPlansId, user, userAdjustments, totalCalorieForToday);
        mealPlans.add(mealPlan);
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
    return mealPlans;
  }
  
  /**
   * Update the TotalCalorieForToday of the MealPlans instance.
   * This runs a UPDATE statement.
   */
  public MealPlans updateTotalCalorieForToday(MealPlans mealPlans, Integer newTotalCalorieForToday) throws SQLException {
    String updatePerson = "UPDATE MealPlans SET TotalCalorieForToday=? WHERE MealPlansId=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updatePerson);
      updateStmt.setInt(1, newTotalCalorieForToday);
      updateStmt.setInt(2, mealPlans.getMealPlanId());
      updateStmt.executeUpdate();

      // Update the mealPlans parameter before returning to the caller.
      mealPlans.setTotalCalorieForToday(newTotalCalorieForToday);
      return mealPlans;
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

  /**
   * Delete the MealPlans instance.
   * This runs a DELETE statement.
   */
  public MealPlans delete(MealPlans mealPlans) throws SQLException {
    // Note: BlogComments has a fk constraint on MealPlans with the reference option
    // ON DELETE CASCADE. So this delete operation will delete all the referencing
    // BlogComments.
    String deleteMealPlans = "DELETE FROM MealPlans WHERE MealPlansId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteMealPlans);
      deleteStmt.setInt(1, mealPlans.getMealPlanId());
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
