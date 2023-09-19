package warzone.service;

import java.util.Map;

import warzone.model.*;

public class ContinentService {
	
	private GameContext d_gameContext;

	public ContinentService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	public boolean add(Continent p_continent) {
		//0. add the item to
		if(p_continent != null) {
			Map<Integer,Continent> l_continents=d_gameContext.getContinents();			
			l_continents.put(p_continent.getContinentID(), p_continent);
			return true;
		}
		return false;
	}
	
	public boolean remove(int p_continentID) {
		if(p_continentID > 0) {
			Map<Integer,Continent> l_continents=d_gameContext.getContinents();
			if(l_continents.containsKey(p_continentID)){
				l_continents.remove(p_continentID);
				return true;
			}			
		}
		return false;
	}
}
