package warzone.controller;

import warzone.model.GameContext;
import warzone.service.StartupService;
import warzone.view.GenericView;

/**
 * startup controller is to manipulate the startup phase in game
 * including add/remove players
 */
public class StartupController {

	private StartupService d_startupService;
	private GameContext d_gameContext;

	/**
	 * constructor with setting gamecontext and create the startupService
	 * @param p_gameContext the game context
	 */
	public StartupController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_startupService = new StartupService(p_gameContext);
	}
	

	/**
	 * Performs the action for user command: gameplayer -add playerName
	 * 
	 * @param p_playerName player's name
	 * @return true if add successfully, otherwise return false
	 */
	public boolean addPlayer(String p_playerName) {
		if(p_playerName == null || p_playerName.trim().equals("")) {
			GenericView.printWarning("Invalid player name.");
			return false;
		}
		//1. create a new player instance
		Player l_player = new Player(p_playerName);
		
		//2. add player to PlayerService
		boolean l_ok=d_startupService.addPlayer(l_player);
		
		//3. render to view
		if(l_ok) {
			GenericView.printSuccess( String.format("Player [%s] was added successfully.", l_player.getName()) );
		}else {
			GenericView.printError( String.format("Player [%s] was added failed.", l_player.getName()) );
		}
		return l_ok;
	}
	
	/**
	 * Performs the action for user command: gameplayer -remove playerName
	 * 
	 * @param p_playerName player's name
	 * @return true if remove successfully, otherwise return false
	 */
	public boolean removePlayer(String p_playerName) {
		if( d_startupService.removePlayer(p_playerName)) {
			GenericView.printSuccess( String.format("Player [%s] was removed successfully.", p_playerName) );
			return true;
		}else {
			GenericView.printWarning( String.format("Failed to remove Player [%s].", p_playerName ) );
			return false;
		}
	}
	
	/**
	 * Performs the action for user command: loadmap filename
	 * 
	 * Game starts by user selection of a user-saved map file,
	 * the map should be a connected graph
	 * 
	 * @param p_fileName the file to load
	 * @return true if load map successfully, otherwise return false
	 */
	public boolean loadMap(String p_fileName) {
		return d_startupService.loadMap(p_fileName);
	}
		
	
	/**
	 * Performs the action for user command: assigncountries
	 * 
	 * After user creates all the players, all countries are randomly assigned to players. 
	 * 
	 * @return true if assign the countries successfully, otherwise return false
	 */
	public boolean assignCountries() {
		
		boolean result = d_startupService.assignCountries();
		
		if(result == false) {
			GenericView.printError("Must have more than 2 players, and map have at least the same number of countries as players ");
		}
		else
			GenericView.printSuccess("Succeed to assign all the countries to players");			
		
		return result;
	}
	
}
