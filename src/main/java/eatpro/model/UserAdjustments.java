package eatpro.model;

import java.sql.Date;

public class UserAdjustments {
    protected int adjustmentId;
    protected Users user;
    protected Date dateLogged;
    protected Double weight;
    protected Boolean workoutToday;
    protected Integer expectedExerciseCalorie;
    
    public UserAdjustments(int adjustmentId, Users user, Date dateLogged, Double weight, Boolean workoutToday, Integer expectedExerciseCalorie) {
        this.adjustmentId = adjustmentId;
        this.user = user;
        this.dateLogged = dateLogged;
        this.weight = weight;
        this.workoutToday = workoutToday;
        this.expectedExerciseCalorie = expectedExerciseCalorie;
    }
    
    
    public UserAdjustments(Users user, Date dateLogged, Double weight, Boolean workoutToday,
			Integer expectedExerciseCalorie) {
		this.user = user;
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

    public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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