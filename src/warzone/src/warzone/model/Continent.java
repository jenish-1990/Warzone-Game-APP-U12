package warzone.model;

import java.util.HashMap;
import java.util.Map;

public class Continent {
	
	private int d_continentID;
	private String d_continentName;
	private int d_bonusReinforcements;
	private Color d_color;
	private Map<Integer, Country> d_countries;
	
	public Continent(int p_continentID, String p_continentName) {
		
		d_continentID = p_continentID;
		d_continentName = p_continentName;
		d_countries = new HashMap<Integer, Country>();
	}
	
	public Continent(int p_continentID, String p_continentName, int p_bonusReinforcements, Color p_color) {
		
		d_continentID = p_continentID;
		d_continentName = p_continentName;
		d_bonusReinforcements = p_bonusReinforcements;
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
	
	public int getBonusReinforcements() {
		return d_bonusReinforcements;
	}

	public void setBonusReinforcements(int p_bonusReinforcements) {
		this.d_bonusReinforcements = p_bonusReinforcements;
	}

	public Color getColor() {
		return d_color;
	}

	public void setColor(Color p_color) {
		this.d_color = p_color;
	}

	public Map<Integer, Country> getCountries() {
		return d_countries;
	}

	public boolean addCountry(Country p_country) {
		if( p_country != null ) {
			d_countries.put(p_country.getCountryID(), p_country);
			return p_country.setContinent(this);
		}
		return false;
	}
}
