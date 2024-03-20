package eatpro.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eatpro.model.*;

public class FoodNutrientsDao {
	protected ConnectionManager connectionManager;
	
	private static FoodNutrientsDao instance = null;
	protected FoodNutrientsDao() {
		connectionManager = new ConnectionManager();
	}
	public static FoodNutrientsDao getInstance() {
		if (instance == null) {
			instance = new FoodNutrientsDao();
		}
		return instance;
	}
	
	/**
	 * Create and save a FoodNutrients instance by storying it in the MySQL instance
	 * This runs a INSERT statement.
	 * @param foodNutrient
	 * @return
	 * @throws SQLException
	 */
	public FoodNutrients create(FoodNutrients foodNutrient) throws SQLException {
		String insertNutrient= "INSERT INTO FoodNutrients(FoodNutrientId,FoodId,NutrientId,Amount) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertNutrient);

			insertStmt.setInt(1, foodNutrient.getFoodNutrientId());
			insertStmt.setInt(2, foodNutrient.getFood().getFoodId());
			insertStmt.setInt(3, foodNutrient.getNutrient().getNutrientId());
			insertStmt.setFloat(4, foodNutrient.getAmount());

			insertStmt.executeUpdate();
			
			return foodNutrient;
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
		}
	}
	
	/**
	 * Get the FoodNutrients record by fetching it from the MySQL instance.
	 * This runs a SELECT statement and returns a single FoodNutrients instance.
	 * @param foodNutrientId
	 * @return 
	 * @throws SQLException
	 */
	public FoodNutrients getNutrientById(int foodNutrientId) throws SQLException {
		String selectFoodNutrient= "SELECT FoodNutrientId,FoodId,NutrientId,Amount FROM FoodNutrients WHERE FoodNutrientId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFoodNutrient);
			selectStmt.setInt(1, foodNutrientId);

			results = selectStmt.executeQuery();
			
			FoodDao foodDao = FoodDao.getInstance();
			NutrientsDao nutrientsDao = NutrientsDao.getInstance();

			if(results.next()) {
				int resultFoodNutrientId = results.getInt("FoodNutrientId");
				int foodId = results.getInt("FoodId");
				int nutrientId = results.getInt("NutrientId");
				float amount = results.getInt("Amount");
				
				Food food = foodDao.getFoodById(foodId);
				Nutrients nutrient = nutrientsDao.getNutrientById(nutrientId);
				
				FoodNutrients foodNutrient= new FoodNutrients(resultFoodNutrientId, food, nutrient, amount);
				return foodNutrient;
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
	 * Delete the FoodNutrients instance.
	 * This runs a DELETE statement.
	 * @param foodNutrient
	 * @return null
	 * @throws SQLException
	 */
	public FoodNutrients delete(FoodNutrients foodNutrient) throws SQLException {
		String deleteFoodNutrient= "DELETE FROM FoodNutrients WHERE FoodNutrientId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFoodNutrient);
			deleteStmt.setInt(1, foodNutrient.getFoodNutrientId());
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

}