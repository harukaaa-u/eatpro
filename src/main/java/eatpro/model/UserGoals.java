package eatpro.model;

import java.time.LocalDate;

public class UserGoals {
	public enum GoalType {
		WEIGHTLOSS, GAINWEIGHT, MAINTAINHEALTH
	}
	
	public enum Status {
		ACTIVE, ACHIEVED, FAILED, ONHOLD
	}
	
    protected int goalId;
    protected Users user;
    protected GoalType goalType;
    protected LocalDate targetDate;
    protected double targetValue;
    protected Status status;
    protected LocalDate creationDate;
    protected LocalDate lastUpdated;
    
    
	public UserGoals(Users user, GoalType goalType, LocalDate targetDate, double targetValue, Status status,
			LocalDate creationDate, LocalDate lastUpdated) {
		this.user = user;
		this.goalType = goalType;
		this.targetDate = targetDate;
		this.targetValue = targetValue;
		this.status = status;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
	}
	
	public UserGoals(int goalId) {
		this.goalId = goalId;
	}

	public UserGoals(int goalId, Users user, GoalType goalType, LocalDate targetDate, double targetValue,
			Status status, LocalDate creationDate, LocalDate lastUpdated) {
		this.goalId = goalId;
		this.user = user;
		this.goalType = goalType;
		this.targetDate = targetDate;
		this.targetValue = targetValue;
		this.status = status;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
	}
	
	public int getGoalId() {
		return goalId;
	}
	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}

	public GoalType getGoalType() {
		return goalType;
	}
	public void setGoalType(GoalType goalType) {
		this.goalType = goalType;
	}
	public LocalDate getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}
	public double getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(double targetValue) {
		this.targetValue = targetValue;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public LocalDate getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
    
}
