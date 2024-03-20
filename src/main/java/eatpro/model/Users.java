package eatpro.model;

public class Users {
    protected String userName;
    protected String password;
    protected double initialWeight;
    protected double height;
    protected boolean gainWeight;
	
    public Users(String userName, String password, double initialWeight, double height, boolean gainWeight) {
		this.userName = userName;
		this.password = password;
		this.initialWeight = initialWeight;
		this.height = height;
		this.gainWeight = gainWeight;
	}

	public Users(String userName) {
		this.userName = userName;
	}

	public Users(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getInitialWeight() {
		return initialWeight;
	}

	public void setInitialWeight(double initialWeight) {
		this.initialWeight = initialWeight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public boolean isGainWeight() {
		return gainWeight;
	}

	public void setGainWeight(boolean gainWeight) {
		this.gainWeight = gainWeight;
	}
}
