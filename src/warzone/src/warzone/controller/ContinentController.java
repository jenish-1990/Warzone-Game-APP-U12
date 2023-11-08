package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.*;

public class ContinentController {
	
	private ContinentService d_continentService;
	private GameContext d_gameContext;

	public ContinentController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_continentService = new ContinentService(p_gameContext);
	}
	
	public boolean addContinent(String p_parameters) {
		//0. parse [p_parameters] to  [ l_continentID, String l_continentName]
		int l_continentID = 0;
		String l_continentName = ""; 

		//1. create a new contient instance
		Continent l_Continent = new Continent(l_continentID, l_continentName);
		//2. TODO Auto-generated method stub
		//ContinentService.add(continent);
		
		//3. render to view
		GenericView.println(l_Continent);
		return true;
	}
	
	/**
	 * Performs the action for the user command: editcontinent -remove continentID
	 */
	public boolean removeContinent(String p_parameters) {
		//0. parse [p_parameters] to  [ l_continentID ]
		int l_continentID = 0;
		
		// TODO Auto-generated method stub
		
		return true;
	}
}
