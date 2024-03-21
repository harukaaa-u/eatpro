package eatpro.model;

import java.sql.Date;

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
    protected Date targetDate;
    protected double targetValue;
    protected Status status;
    protected Date creationDate;
    protected Date lastUpdated;
    
    
	public UserGoals(Users user, GoalType goalType, Date targetDate, double targetValue, Status status,
			Date creationDate, Date lastUpdated) {
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

	public UserGoals(int goalId, Users user, GoalType goalType, Date targetDate, double targetValue,
			Status status, Date creationDate, Date lastUpdated) {
		this.goalId = goalId;
		this.user = user;
		this.goalType = goalType;
		this.targetDate = targetDate;
		this.targetValue = targetValue;
		this.status = status;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
	}
	
	
	public UserGoals(Users user, GoalType goalType, Date targetDate, double targetValue, Status status) {
		this.user = user;
		this.goalType = goalType;
		this.targetDate = targetDate;
		this.targetValue = targetValue;
		this.status = status;
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
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
    
}
