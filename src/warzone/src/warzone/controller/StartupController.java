package warzone.controller;

import warzone.view.*;
import warzone.model.*;
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
	 * @param fileName
	 * @return
	 */
	public boolean loadMap(String p_fileName) {
		
		return d_startupService.loadMap(p_fileName);
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
		
		// TODO Auto-generated method stub
		
		return false;
	}
	
}
