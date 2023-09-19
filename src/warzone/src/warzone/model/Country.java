package warzone.model;

import java.util.HashMap;
import java.util.Map;

public class Country {
	
	private int d_countryID;
	private String d_countryName;
	private int d_ownerID;
	private int d_deployedForces;
	private int d_xPosition;
	private int d_yPosition;
	private Map<Integer, Country> d_neighbors;
	
	public Country(int p_countryID, String p_countryName, int p_xPosition, int p_yPosition ) {
		this.d_countryID = p_countryID;
		this.d_countryName = p_countryName;
		this.d_xPosition = p_xPosition;
		this.d_yPosition = p_yPosition;	
		
		d_neighbors = new HashMap<Integer, Country>();
	}
	
	public int getCountryID() {
		return d_countryID;
	}
	
	public void setCountryID(int p_countryID) {
		this.d_countryID = p_countryID;
	}
	
	public String getCountryName() {
		return d_countryName;
	}
	
	public void setCountryName(String p_countryName) {
		this.d_countryName = p_countryName;
	}	
	
	public int getOwnerID() {
		return d_ownerID;
	}

	public void setOwnerID(int p_ownerID) {
		this.d_ownerID = p_ownerID;
	}

	public int getDeployedForces() {
		return d_deployedForces;
	}

	public void setDeployedForces(int p_deployedForces) {
		this.d_deployedForces = p_deployedForces;
	}

	public int getXPosition() {
		return d_xPosition;
	}

	public void setXPosition(int p_xPosition) {
		this.d_xPosition = p_xPosition;
	}

	public int getYPosition() {
		return d_yPosition;
	}

	public void setYPosition(int p_yPosition) {
		this.d_yPosition = p_yPosition;
	}

	public Map<Integer, Country> getNeighbors() {
		return d_neighbors;
	}
}
