package warzone.model;

import java.util.ArrayList;
import java.util.List;

public class Continent {
	
	private int continentID;
	private String continentName;
	private String color;
	private List<Country> countries;
	
	public int getContinentID() {
		return continentID;
	}
	
	public void setContinentID(int continentID) {
		this.continentID = continentID;
	}
	
	public String getContinentName() {
		return continentName;
	}
	
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}	
}
