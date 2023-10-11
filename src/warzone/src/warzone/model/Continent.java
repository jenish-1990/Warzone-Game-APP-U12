package warzone.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the continent in the game
 */
public class Continent {
	
	private int d_continentID;
	private String d_continentName;
	private int d_bonusReinforcements;
	private String d_color;
	private Map<Integer, Country> d_countries;

	/**
	 * constructor with setting continent id and name
	 * @param p_continentID continent id
	 * @param p_continentName continent name
	 */
	public Continent(int p_continentID, String p_continentName) {
		
		d_continentID = p_continentID;
		d_continentName = p_continentName;
		d_countries = new HashMap<Integer, Country>();
		d_bonusReinforcements = 0;
	}

	/**
	 * constructor with setting continent id, name, bonusReinforcements and color
	 * @param p_continentID the continent id
	 * @param p_continentName the continent name
	 * @param p_bonusReinforcements the bonus reinforcement of the continnet
	 * @param p_color the color of the continent
	 */
	public Continent(int p_continentID, String p_continentName, int p_bonusReinforcements, String p_color) {
		
		d_continentID = p_continentID;
		d_continentName = p_continentName;
		d_bonusReinforcements = p_bonusReinforcements;
		d_color = p_color;
		d_countries = new HashMap<Integer, Country>();
	}

	/**
	 * get continent id
	 * @return the id of the continent
	 */
	public int getContinentID() {
		return d_continentID;
	}

	/**
	 * set continent id
	 * @param p_continentID the continent id
	 */
	public void setContinentID(int p_continentID) {
		this.d_continentID = p_continentID;
	}

	/**
	 * get continent name
	 * @return the name of continent
	 */
	public String getContinentName() {
		return d_continentName;
	}

	/**
	 * set continent name
	 * @param p_continentName the continent name
	 */
	public void setContinentName(String p_continentName) {
		this.d_continentName = p_continentName;
	}

	/**
	 * get the bonus reinforcement of the continent
	 * @return the bonus reinforcement of the continent
	 */
	public int getBonusReinforcements() {
		return d_bonusReinforcements;
	}

	/**
	 * set the bonus reinforcement of the continent
	 * @param p_bonusReinforcements the bonus reinforcement of the continent
	 */
	public void setBonusReinforcements(int p_bonusReinforcements) {
		this.d_bonusReinforcements = p_bonusReinforcements;
	}

	/**
	 * get the color of the continent
	 * @return the color
	 */
	public String getColor() {
		return d_color;
	}

	/**
	 * get the color
	 * @param p_color the color of the continent
	 */
	public void setColor(String p_color) {
		this.d_color = p_color;
	}

	/**
	 * get the map of the countries in the continent
	 * @return the countries in the continent
	 */
	public Map<Integer, Country> getCountries() {
		return d_countries;
	}

	/**
	 * add a country to the continent
	 * @param p_country the country
	 * @return true if successfully add to the continent, otherwise return false
	 */
	public boolean addCountry(Country p_country) {
		if( p_country != null ) {
			d_countries.put(p_country.getCountryID(), p_country);
			return p_country.setContinent(this);
		}
		return false;
	}
}
