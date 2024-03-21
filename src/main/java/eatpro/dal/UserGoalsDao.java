package eatpro.dal;
import eatpro.model.*;

import eatpro.model.UserGoals.Status;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class UserGoalsDao {
  protected ConnectionManager connectionManager;
  private static UserGoalsDao instance = null;

  protected UserGoalsDao() {
    connectionManager = new ConnectionManager();
  }

  public static UserGoalsDao getInstance() {
    if (instance == null) {
      instance = new UserGoalsDao();
    }
    return instance;
  }

  public UserGoals create(UserGoals userGoal) throws SQLException {
    String insertUserGoal = "INSERT INTO UserGoals (UserName, GoalType, TargetDate, TargetValue, Status, CreationDate, LastUpdated) VALUES (?, ?, ?, ?, ?, ?, ?);";
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement insertStmt = connection.prepareStatement(insertUserGoal, PreparedStatement.RETURN_GENERATED_KEYS)) {
      insertStmt.setString(1, userGoal.getUser().getUserName());
      insertStmt.setString(2, userGoal.getGoalType().toString());
      java.sql.Date sqlTargetDate = new java.sql.Date(userGoal.getTargetDate().getTime());
      insertStmt.setDate(3, sqlTargetDate);
      //insertStmt.setDate(3, userGoal.getTargetDate());
      insertStmt.setBigDecimal(4, BigDecimal.valueOf(userGoal.getTargetValue()));
      insertStmt.setString(5, userGoal.getStatus().toString());
      java.sql.Date sqlCreationDate = new java.sql.Date(userGoal.getCreationDate().getTime());
      insertStmt.setDate(6, sqlCreationDate);
      
      java.sql.Date sqlLastUpdated = new java.sql.Date(userGoal.getLastUpdated().getTime());
      insertStmt.setDate(7, sqlLastUpdated);
      //insertStmt.setDate(7, userGoal.getLastUpdated());

      insertStmt.executeUpdate();

      try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          userGoal.setGoalId(generatedKeys.getInt(1));
        } else {
          throw new SQLException("Creating user goal failed, no ID obtained.");
        }
      }
      return userGoal;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }


  public UserGoals getUserGoalById(int goalId) throws SQLException {
    String selectUserGoal = "SELECT GoalId, UserName, GoalType, TargetDate, TargetValue, Status, CreationDate, LastUpdated FROM UserGoals WHERE GoalId = ?;";
    try (Connection connection = connectionManager.getConnection();

        PreparedStatement selectStmt = connection.prepareStatement(selectUserGoal)) {
      selectStmt.setInt(1, goalId);
      UsersDao usersDao = UsersDao.getInstance();

      try (ResultSet results = selectStmt.executeQuery()) {
        if (results.next()) {
          int resultGoalId = results.getInt("GoalId");
          String userName = results.getString("UserName");
          String goalType = results.getString("GoalType");
          Date targetDate = results.getDate("TargetDate");
          double targetValue = results.getBigDecimal("TargetValue").doubleValue();
          String status = results.getString("Status");
          Date creationDate = results.getDate("CreationDate");
          Date lastUpdated = results.getDate("LastUpdated");

          Users user = usersDao.getUserByUserName(userName);

          UserGoals goal = new UserGoals(resultGoalId,user, UserGoals.GoalType.valueOf(goalType), targetDate, targetValue, Status.valueOf(status), creationDate, lastUpdated);
          return goal;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return null;
  }
  public List<UserGoals> getGoalsByUserName(String userName) throws SQLException {
    List<UserGoals> goals = new ArrayList<>();
    String selectGoals = "SELECT GoalId, UserName, GoalType, TargetDate, TargetValue, Status, CreationDate, LastUpdated FROM UserGoals WHERE UserName = ?;";
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement selectStmt = connection.prepareStatement(selectGoals)) {
      selectStmt.setString(1, userName);
      UsersDao usersDao = UsersDao.getInstance();

      try (ResultSet results = selectStmt.executeQuery()) {
        while (results.next()) {
          int goalId = results.getInt("GoalId");
          String goalType = results.getString("GoalType");
          Date targetDate = results.getDate("TargetDate");
          double targetValue = results.getBigDecimal("TargetValue").doubleValue();
          Status status = Status.valueOf(results.getString("Status"));
          Date creationDate = results.getDate("CreationDate");
          Date lastUpdated = results.getDate("LastUpdated");
          Users user = usersDao.getUserByUserName(userName);

          UserGoals goal = new UserGoals(goalId, user, UserGoals.GoalType.valueOf(goalType), targetDate, targetValue, status, creationDate, lastUpdated);
          goals.add(goal);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return goals;
  }
  
  
  public UserGoals updateGoalById(int goalId, UserGoals userGoal) throws SQLException {
	    String updateUserGoal = "UPDATE UserGoals SET GoalType = ?, TargetDate = ?, TargetValue = ?, Status = ?, LastUpdated = ? WHERE GoalId = ?;";
	    try (Connection connection = connectionManager.getConnection();
	         PreparedStatement updateStmt = connection.prepareStatement(updateUserGoal)) {
	        
	        updateStmt.setString(1, userGoal.getGoalType().toString());
	     
	        java.sql.Date sqlTargetDate = new java.sql.Date(userGoal.getTargetDate().getTime());
	        updateStmt.setDate(2, sqlTargetDate);
	        //updateStmt.setDate(2, userGoal.getTargetDate());
	        updateStmt.setBigDecimal(3, BigDecimal.valueOf(userGoal.getTargetValue()));
	        updateStmt.setString(4, userGoal.getStatus().toString());
	        java.sql.Date sqlLastUpdated = new java.sql.Date(userGoal.getLastUpdated().getTime());
	        updateStmt.setDate(5, sqlLastUpdated);
	        //updateStmt.setDate(5, userGoal.getLastUpdated());
	        updateStmt.setInt(6, goalId);

	        int affectedRows = updateStmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Updating user goal failed, no rows affected.");
	        }

	        return getUserGoalById(goalId);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
  
  public UserGoals updateStatus(int userGoalId, Status newStatus) throws SQLException {
	    String updateWeight = "UPDATE UserGoals SET Status=? LastUpdated=? WHERE GoalId=?;";
	    try (Connection connection = connectionManager.getConnection();
	         PreparedStatement updateStmt = connection.prepareStatement(updateWeight)) {
	      updateStmt.setString(1, newStatus.toString());
	      UserGoals updatedUserGoals = getUserGoalById(userGoalId);
	      updatedUserGoals.setLastUpdated();
	      java.sql.Date sqlLastUpdated = new java.sql.Date(updatedUserGoals.getLastUpdated().getTime());
	      updateStmt.setDate(2, sqlLastUpdated);
	      updateStmt.setInt(3, userGoalId);
	      updateStmt.executeUpdate();
	      return updatedUserGoals;
	    } catch (SQLException e) {
	      e.printStackTrace();
	      throw e;
	    }
	  }
  
  
  public boolean delete(int goalId) throws SQLException {
	    String deleteUserGoal = "DELETE FROM UserGoals WHERE GoalId = ?;";
	    try (Connection connection = connectionManager.getConnection();
	         PreparedStatement deleteStmt = connection.prepareStatement(deleteUserGoal)) {
	        deleteStmt.setInt(1, goalId);

	        int affectedRows = deleteStmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

  
  public UserGoals delete(UserGoals userGoal) throws SQLException {
    String deleteUserGoal = "DELETE FROM UserGoals WHERE GoalId = ?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try{
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteUserGoal);
      deleteStmt.setInt(1, userGoal.getGoalId());
      deleteStmt.executeUpdate();
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }finally {
      if(connection != null) {
        connection.close();
      }
      if(deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }
}
