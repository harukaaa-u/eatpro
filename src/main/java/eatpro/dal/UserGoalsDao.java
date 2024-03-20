package eatpro.dal;
import eatpro.model.*;
import eatpro.model.UserGoals.Status;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
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
      insertStmt.setDate(3, Date.valueOf(userGoal.getTargetDate()));
      insertStmt.setBigDecimal(4, BigDecimal.valueOf(userGoal.getTargetValue()));
      insertStmt.setString(5, userGoal.getStatus().toString());
      insertStmt.setDate(6, Date.valueOf(userGoal.getCreationDate()));
      insertStmt.setDate(7, Date.valueOf(userGoal.getLastUpdated()));

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
          LocalDate targetDate = results.getDate("TargetDate").toLocalDate();
          double targetValue = results.getBigDecimal("TargetValue").doubleValue();
          String status = results.getString("Status");
          LocalDate creationDate = results.getDate("CreationDate").toLocalDate();
          LocalDate lastUpdated = results.getDate("LastUpdated").toLocalDate();

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
          LocalDate targetDate = results.getDate("TargetDate").toLocalDate();
          double targetValue = results.getBigDecimal("TargetValue").doubleValue();
          Status status = Status.valueOf(results.getString("Status"));
          LocalDate creationDate = results.getDate("CreationDate").toLocalDate();
          LocalDate lastUpdated = results.getDate("LastUpdated").toLocalDate();
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
