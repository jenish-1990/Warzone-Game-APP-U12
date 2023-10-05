package warzone.service;

import java.util.Map;

import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.view.GenericView;

/**
 * This class will provide controllers with some service related to 'country' module.
 */
public class CountryService {
	private GameContext d_gameContext;

	/**
	 * This method will initiate current game context.
	 * @param p_gameContext the current game context
	 */
	public CountryService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	/**
	 * This method will add a new country to the continent.
	 * @param p_countryID the ID of the country that should insert
	 * @param p_ContinentID the ID of the continent
	 * @return the result of operation
	 */
	public boolean addCountryToContient(int p_countryID, int p_ContinentID) {
		if(p_countryID > 0 && p_ContinentID>0) {
			Country l_country = d_gameContext.getCountries().get(p_countryID);
			Continent l_continent = d_gameContext.getContinents().get(p_ContinentID);
			
			if(l_country == null) {
				l_country = new Country(p_countryID, "COUNTRY-"+p_countryID );
				d_gameContext.getCountries().put(p_countryID, l_country);
			}
			if(l_country.getContinent() != null)
				GenericView.printError("Country ID " + l_country.getCountryID() + " is already belongs to Continent ID " + l_country.getContinent().getContinentID());
			else {
				if (l_continent != null) {
					l_continent.addCountry(l_country);
					return true;
				}
				else
					GenericView.printError(" The continent " + p_ContinentID + " does not exist.");
			}
		}
		return false;
	}	
	
	/**
	 * This method will remove a country from the continent.
	 * @param p_countryID the ID of the country that should be removed
	 * @return the result of operation
	 */
	public boolean remove(int p_countryID) {
		Country l_country = d_gameContext.getCountries().get(p_countryID);
		
		if(l_country != null) {
			//remove from neighbor
	        for (Country l_tempCountry : d_gameContext.getCountries().values()) {
	        	l_tempCountry.getNeighbors().remove(p_countryID);        	
	        }
	        
	        //remove from Continent
	        d_gameContext.getContinents().get( l_country.getContinent().getContinentID() ).getCountries().remove(p_countryID);

	        //remove from players.
			if(l_country.getOwner() != null && l_country.getOwner().getConqueredCountries() != null)
	        	l_country.getOwner().getConqueredCountries().remove(l_country);
	        
	        //remove from Country
			d_gameContext.getCountries().remove(p_countryID);
			return true;
		}
		
		return false;		
	}
	
	/**
	 * This method will show the existence of the country.
	 * @param p_countryID the ID of the country
	 * @return true if the country exists
	 */
	public boolean isExisted(int p_countryID) {
		return d_gameContext.getCountries().containsKey(p_countryID);
	}
}
