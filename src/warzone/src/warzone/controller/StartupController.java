package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.CommonTool;
import warzone.service.MapService;
import warzone.service.StartupService;

public class StartupController {

	private StartupService d_startupService;
	private GameContext d_gameContext;

	public StartupController(GameContext p_gameContext) {
		
		d_gameContext = p_gameContext;
		d_startupService = new StartupService(p_gameContext);
	}
	
	
	/**
	 * Performs the action for user command: loadmap filename
	 * 
	 * Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph
	 * 
	 * @param p_fileName
	 * @return if load map success
	 */
	public boolean loadMap(String p_fileName) {
		
		return d_startupService.loadMap(p_fileName);
	}
	
	public boolean addRawPlayer(String p_parameters) {
		//parse [p_parameters]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		String l_playerName = "";
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		if(l_parameters.length == 1 ) {			
			l_playerName = l_parameters[0];
		}
		if(l_playerName == ""){
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		return addPlayer(l_playerName);	
	}
	
	/**
	 * Performs the action for user command: gameplayer -add playerName
	 * 
	 * @param name
	 * @return
	 */
	public boolean addPlayer(String name) {
		
		// TODO Auto-generated method stub
		
		return false;
	}
	
	public boolean removeRawPlayer(String p_parameters) {
		//parse [p_parameters]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		String l_playerName = "";
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		if(l_parameters.length == 1 ) {			
			l_playerName = l_parameters[0];
		}
		if(l_playerName == ""){
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		return removePlayer(l_playerName);	
	}
	
	/**
	 * Performs the action for user command: gameplayer -remove playerName
	 * 
	 * @param name
	 * @return
	 */
	public boolean removePlayer(String name) {
		
		// TODO Auto-generated method stub
		
		return false;
	}
	
	/**
	 * Performs the action for user command: assigncountries
	 * 
	 * After user creates all the players, all countries are randomly assigned to players. 
	 * 
	 * @return
	 */
	public boolean assignCountries() {
		
		boolean result = d_startupService.assignCountries();
		
		if(result == false) {
			
			GenericView.printError("There must be at least the same number of countries as players");
		}
		
		return false;
	}
	
}
