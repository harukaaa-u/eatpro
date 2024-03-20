package eatpro.model;

public class DietaryRestrictions {
    protected int restrictionId;
    protected String userName;
    protected RestrictionType restrictionType;
    protected String description;

    public enum RestrictionType {
        Allergy, DietaryPreference
    }
    
    public DietaryRestrictions(int restrictionId, String userName, RestrictionType restrictionType, String description) {
        this.restrictionId = restrictionId;
        this.userName = userName;
        this.restrictionType = restrictionType;
        this.description = description;
    }
    

    public DietaryRestrictions(String userName, RestrictionType restrictionType, String description) {
		this.userName = userName;
		this.restrictionType = restrictionType;
		this.description = description;
	}


	public DietaryRestrictions(int restrictionId) {
        this.restrictionId = restrictionId;
    }

    // Getters and Setters
    public int getRestrictionId() {
        return restrictionId;
    }

    public void setRestrictionId(int restrictionId) {
        this.restrictionId = restrictionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
