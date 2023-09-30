package warzone.model;

import java.util.ArrayList;
import java.util.List;

public class Country {
	
	private int countryID;
	private String countryName;
	private int continentID;
	private int positionX;
	private int positionY;
	private List<Country> country;
	
	private Continent continent; 
	
	public int getCountryID() {
		return countryID;
	}
	
	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}	
}
