package warzone.model;

import java.util.HashMap;
import java.util.Map;

public class Continent {
	
	private int d_continentID;
	private String d_continentName;
	private String d_color;
	private int d_weight;
	private Map<Integer, Country> d_countries;
	
	public Continent(int p_continentID, String p_continentName) {
		
		d_continentID = p_continentID;
		d_continentName = p_continentName;
		d_countries = new HashMap<Integer, Country>();
	}
	
	public Continent(int p_continentID, String p_continentName, String p_color, int p_weight) {
		
		d_continentID = p_continentID;
		d_continentName = p_continentName;
		d_weight = p_weight;
		d_color = p_color;
		d_countries = new HashMap<Integer, Country>();
	}
	
	public int getContinentID() {
		return d_continentID;
	}
	
	public void setContinentID(int p_continentID) {
		this.d_continentID = p_continentID;
	}
	
	public String getContinentName() {
		return d_continentName;
	}
	
	public void setContinentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}	
	
	public String getColor() {
		return d_color;
	}
	
	public void setColor(String p_color) {
		this.d_color = p_color;
	}	
	
	public int getWeight() {
		return d_weight;
	}
	
	public void setWeight(int p_weight) {
		this.d_weight = p_weight;
	}	
	
	public Map<Integer, Country> getCountries() {
		return d_countries;
	}
}
