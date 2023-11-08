package warzone.model;

import java.util.HashMap;
import java.util.Map;

public class Country {
	
	private int d_countryID;
	private String d_countryName;
	private Player d_owner;
	private int d_deployedForces;
	private int d_xPosition;
	private int d_yPosition;
	private Map<Integer, Country> d_neighbors;
	private Continent d_continent;

	public Country(int p_countryID, String p_countryName, int p_xPosition, int p_yPosition, Continent p_continent) {
		
		d_countryID = p_countryID;
		d_countryName = p_countryName;
		d_xPosition = p_xPosition;
		d_yPosition = p_yPosition;
		d_neighbors = new HashMap<Integer, Country>();
		d_continent = p_continent;
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
	
	public Player getOwner() {
		return d_owner;
	}

	public void setOwner(Player p_owner) {
		this.d_owner = p_owner;
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

	public Continent getContinent() { 
		return d_continent; 
	}

	public boolean setContinent(Continent p_continent) {

		if(p_continent != null) {
			d_continent = p_continent;
			return true;
		}
		else
			return false;
	}

	public boolean addNeighbor(Country p_country) {
		if(p_country != null) {
			d_neighbors.put(p_country.getCountryID(), p_country);
			return true;
		}
		else
			return false;
	}
}
