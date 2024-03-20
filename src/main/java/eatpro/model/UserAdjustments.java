package eatpro.model;

import java.sql.Date;

public class UserAdjustments {
    protected int adjustmentId;
    protected String userName;
    protected Date dateLogged;
    protected Double weight;
    protected Boolean workoutToday;
    protected Integer expectedExerciseCalorie;
    
    public UserAdjustments(int adjustmentId, String userName, Date dateLogged, Double weight, Boolean workoutToday, Integer expectedExerciseCalorie) {
        this.adjustmentId = adjustmentId;
        this.userName = userName;
        this.dateLogged = dateLogged;
        this.weight = weight;
        this.workoutToday = workoutToday;
        this.expectedExerciseCalorie = expectedExerciseCalorie;
    }
    
    public UserAdjustments(int adjustmentId) {
        this.adjustmentId = adjustmentId;
    }
    
    // Getters and Setters
    public int getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(int adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(Date dateLogged) {
        this.dateLogged = dateLogged;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getWorkoutToday() {
        return workoutToday;
    }

    public void setWorkoutToday(Boolean workoutToday) {
        this.workoutToday = workoutToday;
    }

    public Integer getExpectedExerciseCalorie() {
        return expectedExerciseCalorie;
    }

    public void setExpectedExerciseCalorie(Integer expectedExerciseCalorie) {
    	this.expectedExerciseCalorie = expectedExerciseCalorie;
    }
}