package warzone.service;

import java.util.Map;

import warzone.model.*;

/**
 * This class can offer some services to controllers, including adding and removing continents.
 *
 */
public class ContinentService {
	
	private GameContext d_gameContext;

	/**
	 * This method can initiate the game context of this class.
	 * @param p_gameContext the game context of current game
	 */
	public ContinentService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	/**
	 * This method will add new continent to current map.
	 * @param p_continent the continent that should be added to current map
	 * @return the result of operation
	 */
	public boolean add(Continent p_continent) {
		//0. add the item to
		if(p_continent != null) {
			if(d_gameContext.getContinents().containsKey(p_continent.getContinentID())) {
				Continent l_continent = d_gameContext.getContinents().get(p_continent.getContinentID());
				l_continent.setBonusReinforcements(p_continent.getBonusReinforcements());				
			}
			else {				
				Map<Integer,Continent> l_continents=d_gameContext.getContinents();			
				l_continents.put(p_continent.getContinentID(), p_continent);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Check if a continent id is existed
	 * @param p_continentID given continent id
	 * @return true if existed otherwise false
	 */
	public boolean isExist(int p_continentID) {
		return d_gameContext.getContinents().containsKey(p_continentID);		
	}
	
	/**
	 * This method will remove a continent by its ID from the map.
	 * @param p_continentID the ID of the continent
	 * @return the result of operation
	 */
	public boolean remove(int p_continentID) {
		if(p_continentID > 0) {
			Map<Integer,Continent> l_continents=d_gameContext.getContinents();
			if(l_continents.containsKey(p_continentID)){
				Continent l_continent = l_continents.get(p_continentID);
				//remove from continent list
		        for (Country l_tempCountry : l_continent.getCountries().values())
		        	l_tempCountry.setContinent(null);        
		        
				//remove from the country reference
				l_continents.remove(p_continentID);
				return true;
			}			
		}
		return false;
	}
}
