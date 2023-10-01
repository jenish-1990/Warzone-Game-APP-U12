package warzone.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the country in the game
 */
public class Country {
	
	private int d_countryID;
	private String d_countryName;
	private Player d_owner;
	private int d_deployedForces;
	private int d_xPosition;
	private int d_yPosition;
	private int d_armyNumber = 0;
	private Map<Integer, Country> d_neighbors;
	private Continent d_continent;

	/**
	 * constructor
	 * @param p_countryID the country id
	 * @param p_countryName the country name
	 * @param p_xPosition the x position of country
	 * @param p_yPosition the y position of country
	 * @param p_continent the coutinent it belongs to
	 */
	public Country(int p_countryID, String p_countryName, int p_xPosition, int p_yPosition, Continent p_continent) {
		
		d_countryID = p_countryID;
		d_countryName = p_countryName;
		d_xPosition = p_xPosition;
		d_yPosition = p_yPosition;
		d_neighbors = new HashMap<Integer, Country>();
		d_continent = p_continent;
	}

	/**
	 * constructor
	 * @param p_countryID the country id
	 * @param p_countryName the country name
	 */
	public Country(int p_countryID, String p_countryName) {
		d_countryID = p_countryID;
		d_countryName = p_countryName;
		d_neighbors = new HashMap<Integer, Country>();
		
	}

	/**
	 * get country id
	 * @return the id of country
	 */
	public int getCountryID() {
		return d_countryID;
	}

	/**
	 * set the country id
	 * @param p_countryID the country id
	 */
	public void setCountryID(int p_countryID) {
		this.d_countryID = p_countryID;
	}

	/**
	 * get the country name
	 * @return the name of the country
	 */
	public String getCountryName() {
		return d_countryName;
	}

	/**
	 * set the name of the country
	 * @param p_countryName the country name
	 */
	public void setCountryName(String p_countryName) {
		this.d_countryName = p_countryName;
	}

	/**
	 * get the owner of the coutrny
	 * @return the Player who owns the cuntry
	 */
	public Player getOwner() {
		return d_owner;
	}

	/**
	 * set the owner of the country
	 * @param p_owner the Player who owns the country
	 */
	public void setOwner(Player p_owner) {
		this.d_owner = p_owner;
	}

	/**
	 * get the deployed force of the country
	 * @return the deployed force
	 */
	public int getDeployedForces() {
		return d_deployedForces;
	}

	/**
	 * set the deploy force to the country
	 * @param p_deployedForces the number of the deployed force
	 */
	public void setDeployedForces(int p_deployedForces) {
		this.d_deployedForces = p_deployedForces;
	}

	/**
	 * get the x position of the country
	 * @return the x position
	 */
	public int getXPosition() {
		return d_xPosition;
	}

	/**
	 * set the x position of the country
	 * @param p_xPosition the value of the x position
	 */
	public void setXPosition(int p_xPosition) {
		this.d_xPosition = p_xPosition;
	}

	/**
	 * get the y position of the country
	 * @return the y position
	 */
	public int getYPosition() {
		return d_yPosition;
	}

	/**
	 * set the y position of the country
	 * @param p_yPosition the value of the y position
	 */
	public void setYPosition(int p_yPosition) {
		this.d_yPosition = p_yPosition;
	}

	/**
	 * the number of army in the country
	 * @return the number of army
	 */
	public int getArmyNumber() {
		return d_armyNumber;
	}

	/**
	 * set the army into the country
	 * @param p_armyNumber the number of the army
	 */
	public void setArmyNumber(int p_armyNumber) {
		this.d_armyNumber = p_armyNumber;
	}

	/**
	 * get the neighbor in the country
	 * @return the neighbors
	 */
	public Map<Integer, Country> getNeighbors() {
		return d_neighbors;
	}

	/**
	 * get the continent it belongs to
	 * @return the continent it belongs to
	 */
	public Continent getContinent() { 
		return d_continent; 
	}

	/**
	 * set the continent of the country
	 * @param p_continent the continent it belongs to
	 * @return true if successfully set the continent, otherwise return false
	 */
	public boolean setContinent(Continent p_continent) {
		// the p_continent could be null, when removing the Continent was removed
		d_continent = p_continent;
		return true;
		
	}

	/**
	 * add the neighbor of the country
	 * @param p_country the neighbor country
	 * @return true if successfully added, otherwise return false
	 */
	public boolean addNeighbor(Country p_country) {
		if(p_country != null) {
			d_neighbors.put(p_country.getCountryID(), p_country);
			return true;
		}
		else
			return false;
	}
}
