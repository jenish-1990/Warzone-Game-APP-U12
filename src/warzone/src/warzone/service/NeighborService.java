package warzone.service;

import java.util.Map;

import warzone.model.Country;
import warzone.model.GameContext;

public class NeighborService {
	
	private GameContext d_gameContext;

	public NeighborService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	public boolean add(int p_countryID, int p_neighborCountryID) {
		Map<Integer,Country> l_countries=d_gameContext.getCountries();
		if(!l_countries.containsKey(p_countryID) || !l_countries.containsKey(p_neighborCountryID)) {
			return false;
		}
		
		Country l_country = l_countries.get(p_countryID);
		Country l_neighborCountry = l_countries.get(p_neighborCountryID);

		return l_country.addNeighbor(l_neighborCountry);		
	}
	
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
