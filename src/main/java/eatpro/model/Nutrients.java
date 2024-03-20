package eatpro.model;

public class Nutrients {
    protected int nutrientId;
    protected String nutrientName;
    protected String unitName;

    public Nutrients(int nutrientId, String nutrientName, String unitName) {
        this.nutrientId = nutrientId;
        this.nutrientName = nutrientName;
        this.unitName = unitName;
    }
    
    public Nutrients(int nutrientId) {
		this.nutrientId = nutrientId;
	}


	public Nutrients(String nutrientName, String unitName) {
		this.nutrientName = nutrientName;
		this.unitName = unitName;
	}



	// Getters and Setters
    public int getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
