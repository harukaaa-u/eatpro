package eatpro.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eatpro.model.*;

public class NutrientsDao {
	protected ConnectionManager connectionManager;
	
	private static NutrientsDao instance = null;
	protected NutrientsDao() {
		connectionManager = new ConnectionManager();
	}
	public static NutrientsDao getInstance() {
		if (instance == null) {
			instance = new NutrientsDao();
		}
		return instance;
	}
	
	/**
	 * Create and save a Nutrients instance by storying it in the MySQL instance
	 * This runs a INSERT statement.
	 * @param nutrient
	 * @return
	 * @throws SQLException
	 */
	public Nutrients create(Nutrients nutrient) throws SQLException {
		String insertNutrient= "INSERT INTO Nutrients(NutrientId,NutrientName,UnitName) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertNutrient);

			insertStmt.setInt(1, nutrient.getNutrientId());
			insertStmt.setString(2, nutrient.getNutrientName());
			insertStmt.setString(3, nutrient.getUnitName());

			insertStmt.executeUpdate();
			
			return nutrient;
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
	 * Get the Nutrients record by fetching it from the MySQL instance.
	 * This runs a SELECT statement and returns a single Nutrients instance.
	 * @param nutrientId
	 * @return 
	 * @throws SQLException
	 */
	public Nutrients getNutrientById(int nutrientId) throws SQLException {
		String selectNutrient= "SELECT NutrientId,NutrientName,UnitName FROM Nutrients WHERE NutrientId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNutrient);
			selectStmt.setInt(1, nutrientId);

			results = selectStmt.executeQuery();

			if(results.next()) {
				int resultNutrientId = results.getInt("NutrientId");
				String nutrientName = results.getString("NutrientName");
				String unitName = results.getString("UnitName");
				Nutrients nutrient= new Nutrients(resultNutrientId, nutrientName, unitName);
				return nutrient;
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
	 * Delete the Nutrients instance.
	 * This runs a DELETE statement.
	 * @param nutrient
	 * @return null
	 * @throws SQLException
	 */
	public Nutrients delete(Nutrients nutrient) throws SQLException {
		String deleteNutrient= "DELETE FROM Nutrients WHERE NutrientId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteNutrient);
			deleteStmt.setInt(1, nutrient.getNutrientId());
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