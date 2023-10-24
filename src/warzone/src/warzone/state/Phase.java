package warzone.state;
import warzone.service.*;

import java.io.IOException;

import warzone.model.*;
import warzone.view.*;


/**
 *	State of the State pattern. Here implemented as a abstract class. 
 *  
 *	In this example, the states represent states in the board game Risk. 
 *  There are many states, and even a hierarchy of states: 
 *
 *		Phase 
 *        MapEdit phase (abstract)

 *        GamePlay (abstract)

 *        
 *      In each state, nextState() is defined so that it goes down in 
 */
public abstract class Phase {

	/**
	 *  Contains a reference to the State of the GameEngine 
	 *  so that the state object can change the state of 
	 *  the GameEngine to transition between states. 
	 */
	protected GameEngine d_gameEngine;
	
	protected GamePhase d_gamePhase;
	
	protected GameContext d_gameContext;	

	/**
	 * Constructor for Phase
	 * @param p_ge Game Engine
	 */
	Phase(GameEngine p_ge) {
		d_gameEngine = p_ge;
		d_gameContext = p_ge.getGameContext();
	}
	abstract public void addContinent(String p_parameters);
	abstract public void removeContinent(String p_parameters);	
	abstract public void addCountry (String p_parameters);
	abstract public void removeCountry(String p_parameters);

	/**
	 * show map
	 */
	abstract public void showMap();

	/**
	 * save map
	 * @param p_fileName file name
	 * @return true if success. otherwise return false
	 * @throws IOException io exception
	 */
	abstract public boolean saveMap (String p_fileName) throws IOException;

	/**
	 * edit map
	 * @param p_fileName file name
	 * @return true if success. otherwise return false
	 */
	abstract public boolean editMap (String p_fileName);

	/**
	 * validate map
	 * @return true if success. otherwise return false
	 */
	abstract public boolean validateMap();
	abstract public void addNeighbor (String p_parameters);	
	abstract public void removeNeighbor (String p_parameters);	
	abstract public void addPlayer(String p_playerName);	
	abstract public void removePlayer(String p_playerName);	
	abstract public void loadMap(String p_fileName);	
	abstract public void assigncountries();
	
//	abstract public void reinforcement(); 
//	abstract public void issueOrder();
//	abstract public void executeOrder();
	

	
	

	// go to next phase
	abstract public void next();

	abstract public void play();
	/**
	 *  Common method to all States. 
	 */
	public void printInvalidCommandMessage() {
		System.out.println("Invalid command in state " + this.getClass().getSimpleName() );
	}
	
	/**
	 * show help for each phase
	 */
	public void help() {
		HelpView.printHelp(this.d_gamePhase);
	}

//	/**
//	 * get phase of the current engine
//	 * @return phase of the current engine
//	 */
//	public Phase getPhase() {
//		return this.d_gameEngine.getPhase();
//	}
	
//	/**
//	 * show help for each phase
//	 */
//	public void reboot() {
//		this.
//		GenericView.printSuccess("The Game has been reoot.");
//	}
	
	/**
	 * print out the error
	 */
	public void error() {
		GenericView.printError("Incorrect command. ");
	}
	
	/**
	 * get current gamephase
	 * @return current gamephase
	 */
	public GamePhase getGamePhase() {
		return this.d_gamePhase;
	}

}
