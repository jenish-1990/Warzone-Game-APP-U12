package warzone.service;

import java.util.Map;

import warzone.model.Country;
import warzone.model.GameContext;

/**
 * This class can provide service to controllers.
 *
 */
public class NeighborService {
	
	private GameContext d_gameContext;

	/**
	 * This constructor will initiate the game context of the class.
	 * @param p_gameContext the current game context
	 */
	public NeighborService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	/**
	 * This method will add a neighbor country to another adjacent country.
	 * @param p_countryID the ID of its adjacent country
	 * @param p_neighborCountryID the ID of neighbor country
	 * @return the result of operation
	 */
	public boolean add(int p_countryID, int p_neighborCountryID) {
		Map<Integer,Country> l_countries=d_gameContext.getCountries();
		if(!l_countries.containsKey(p_countryID) || !l_countries.containsKey(p_neighborCountryID)) {
			return false;
		}
		
		Country l_country = l_countries.get(p_countryID);
		Country l_neighborCountry = l_countries.get(p_neighborCountryID);

		return l_country.addNeighbor(l_neighborCountry);		
	}
	
	/**
	 * This method will remove a country from its neighbor.
	 * @param p_countryID the ID of the country that should be removed
	 * @param p_neighborCountryID the ID of its neighbor
	 * @return the result of operation
	 */
	public boolean remove(int p_countryID, int p_neighborCountryID) {
		Map<Integer,Country> l_countries=d_gameContext.getCountries();
		if(!l_countries.containsKey(p_countryID) || !l_countries.containsKey(p_neighborCountryID)) {
			return false;
		}
		
		Country l_country = l_countries.get(p_countryID);
		l_country.getNeighbors().remove(p_neighborCountryID);		
		return true;
	}
}
