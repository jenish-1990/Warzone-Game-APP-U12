package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.*;

/**
 * Gameplay Controller is to manipulate the actions in game play
 */
public class GameplayController {	
	
	private GameEngine d_gameEngine;
	private GameContext d_gameContext;

	/**
	 * constructor with setting gameContext and gameEngine
	 * @param p_gameContext the game context
	 */
	public GameplayController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_gameEngine = GameEngine.getGameEngine(p_gameContext);
	}

	/**
	 * check if gameengine is ready to start and printout the map
	 * @return true is ready to play, otherwise return false
	 */
	public boolean play() {
		
		this.d_gameContext.setGamePhase(GamePhase.PLAY);
		GenericView.printSuccess("Warzone is in the phase :" + d_gameContext.getGamePhase());
		HelpView.printHelp(d_gameContext.getGamePhase() );
		
		if( !d_gameEngine.isReadyToStart()) {
			GenericView.printWarning("Game is not ready to start, please check the map， countries and players.");
			return false;
		}
		else
			GenericView.println("The game start to play........");
			
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
	 */
	public void showMap() {
		
		MapView.printMap(d_gameContext);
		MapView.printMapWithArmies(d_gameContext.getContinents());
	}
}
