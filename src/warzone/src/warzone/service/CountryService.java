package warzone.service;

import java.util.Map;

import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;

public class CountryService {
	private GameContext d_gameContext;

	public CountryService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	public boolean addCountryToContient(int p_countryID, int p_ContinentID) {
		if(p_countryID > 0 && p_ContinentID>0) {
			Country l_country = d_gameContext.getCountries().get(p_countryID);
			Continent l_continent = d_gameContext.getContinents().get(p_ContinentID);
			
			if(l_country != null && l_continent != null){
				l_country.setContinent(l_continent);
				return true;
			}
		}
		return false;
	}	
	
	public boolean remove(int p_countryID) {
		Country l_country = d_gameContext.getCountries().get(p_countryID);
		
		if(l_country != null) {
			//remove from neighbor
	        for (Country l_tempCountry : d_gameContext.getCountries().values()) {
	        	l_tempCountry.getNeighbors().remove(p_countryID);        	
	        }
	        
	        //remove from Continent
	        d_gameContext.getContinents().get( l_country.getContinent().getContinentID() ).getCountries().remove(p_countryID);
	        
	        //remove from Country
			d_gameContext.getCountries().remove(p_countryID);
			return true;
		}
		
		return false;		
	}
}
