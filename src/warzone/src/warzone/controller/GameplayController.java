package warzone.controller;

import warzone.view.*;
import warzone.model.*;

public class GameplayController {

	private GameContext d_gameContext;

	public GameplayController(GameContext p_gameContext){
		d_gameContext = p_gameContext;
	}
	/**
	 * Performs the action for user command: showmap 
	 * 
	 * Shows all countries and continents, armies on each country, ownership, 
	 * and connectivity in a way that enables efficient game play
	 */
	public void showMap() {
		MapView.printMap(d_gameContext.getContinents());
		MapView.printMapWithArmies(d_gameContext.getContinents());
	}
}
