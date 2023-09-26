package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.*;

public class GameplayController {	
	
	private GameEngine d_gameEngine;
	private GameContext d_gameContext;
	
	public GameplayController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_gameEngine = GameEngine.getGameEngine(p_gameContext);
	}
	
	public boolean play() {
		if( !d_gameEngine.isReadyToStart()) {
			GenericView.printWarning("Game is not ready to start, please check the map and players.");
			return false;
		}
			
		boolean l_finished = d_gameEngine.play();
		if(l_finished) {
			GenericView.printSuccess( String.format("Game was finished successfully."));
		}
		else {
			GenericView.printError( String.format("Game was not finished after certain loop.") );
		}
		
		//show the map
		showMap();		
		
		return l_finished;
	}
	
	
	/**
	 * Performs the action for user command: showmap 
	 * 
	 * Shows all countries and continents, armies on each country, ownership, 
	 * and connectivity in a way that enables efficient game play
	 * 
	 * @return
	 */
	public GameContext showMap() {
		
		MapView.printMap(d_gameContext.getContinents());
		MapView.printMapWithArmies(d_gameContext.getContinents());
		
		return null;
	}
}
