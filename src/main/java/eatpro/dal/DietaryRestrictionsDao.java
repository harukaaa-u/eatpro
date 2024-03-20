package eatpro.dal;

import eatpro.model.DietaryRestrictions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DietaryRestrictionsDao {
    protected ConnectionManager connectionManager;
    
    private static DietaryRestrictionsDao instance = null;
    protected DietaryRestrictionsDao() {
        connectionManager = new ConnectionManager();
    }
    public static DietaryRestrictionsDao getInstance() {
        if(instance == null) {
            instance = new DietaryRestrictionsDao();
        }
        return instance;
    }

    public DietaryRestrictions create(DietaryRestrictions dietaryRestriction) throws SQLException {
        String insertDietaryRestriction = "INSERT INTO DietaryRestrictions(UserName,RestrictionType,Description) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertDietaryRestriction, PreparedStatement.RETURN_GENERATED_KEYS);
            
            insertStmt.setString(1, dietaryRestriction.getUserName());
            insertStmt.setString(2, dietaryRestriction.getRestrictionType().name());
            insertStmt.setString(3, dietaryRestriction.getDescription());
            
            insertStmt.executeUpdate();
            
            resultKey = insertStmt.getGeneratedKeys();
            int restrictionId = 0;
            if(resultKey.next()) {
                restrictionId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            dietaryRestriction.setRestrictionId(restrictionId);
            return dietaryRestriction;
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
    
    public DietaryRestrictions getDietaryRestrictionById(int restrictionId) throws SQLException {
        String selectDietaryRestriction = "SELECT RestrictionId,UserName,RestrictionType,Description FROM DietaryRestrictions WHERE RestrictionId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectDietaryRestriction);
            selectStmt.setInt(1, restrictionId);
            
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultRestrictionId = results.getInt("RestrictionId");
                String userName = results.getString("UserName");
//                String restrictionType = results.getString("RestrictionType");
                DietaryRestrictions.RestrictionType restrictionType = DietaryRestrictions.RestrictionType.valueOf(results.getString("RestrictionType"));
                String description = results.getString("Description");
                
                DietaryRestrictions dietaryRestriction = new DietaryRestrictions(resultRestrictionId, userName, restrictionType, description);
                return dietaryRestriction;
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
    
    public DietaryRestrictions delete(DietaryRestrictions dietaryRestriction) throws SQLException {
        String deleteDietaryRestriction = "DELETE FROM DietaryRestrictions WHERE RestrictionId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteDietaryRestriction);
            deleteStmt.setInt(1, dietaryRestriction.getRestrictionId());
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

    public DietaryRestrictions updateDescription(DietaryRestrictions dietaryRestriction, String newDescription) throws SQLException {
        String updateDescription = "UPDATE DietaryRestrictions SET Description=? WHERE RestrictionId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateDescription);
            updateStmt.setString(1, newDescription);
            updateStmt.setInt(2, dietaryRestriction.getRestrictionId());
            updateStmt.executeUpdate();
            
            dietaryRestriction.setDescription(newDescription);
            return dietaryRestriction;
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
