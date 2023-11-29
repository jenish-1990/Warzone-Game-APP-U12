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
	/**
	 *  currenet phase
	 */
	protected GamePhase d_gamePhase = GamePhase.MAPEDITOR;
	
	/**
	 * current Game Context
	 */
	protected GameContext d_gameContext;	

	/**
	 * Constructor for Phase
	 * @param p_gameEngine Game Engine
	 */
	Phase(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
		d_gameContext = p_gameEngine.getGameContext();
	}

	/**
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	abstract public void addContinent(String p_parameters);

	/**
	 * Performs the action for the user command: editcontinent -remove continentID
	 * @param p_parameters id of continent
	 */
	abstract public void removeContinent(String p_parameters);

	/**
	 * add country to the map
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	abstract public void addCountry (String p_parameters);

	/**
	 * remove the country from map
	 * @param p_parameters parameters parsed by parser
	 */
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

	/**
	 * Performs the action for the user command: editneighbor -add countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	abstract public void addNeighbor (String p_parameters);

	/**
	 * Performs the action for the user command: editneighbor -remove countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	abstract public void removeNeighbor (String p_parameters);

	/**
	 * Performs the action for user command: gameplayer -add playerName
	 *
	 * @param p_playerName player's name
	 */
	abstract public void addPlayer(String p_playerName);

	/**
	 * Performs the action for user command: gameplayer -remove playerName
	 *
	 * @param p_playerName player's name
	 */
	abstract public void removePlayer(String p_playerName);

	/**
	 * Performs the action for user command: loadmap filename
	 *
	 * Game starts by user selection of a user-saved map file,
	 * the map should be a connected graph
	 *
	 * @param p_fileName the file to load
	 */
	abstract public void loadMap(String p_fileName);

	/**
	 * Performs the action for user command: assigncountries
	 *
	 * After user creates all the players, all countries are randomly assigned to players.
	 */
	abstract public void assigncountries();
	
	/**
	 * Sets the list of map files to be used in the tournament.
	 * 
	 * @param p_mapFiles
	 */
	abstract public void setTournamentMapFiles(String[] p_mapFiles);
	
	/**
	 * Sets the list of player strategies to be used in the tournament.
	 * 
	 * @param p_playerStrategies
	 */
	abstract public void setTournamentPlayerStrategies(String[] p_playerStrategies);

	/**
	 * Sets the number of games to be played on each map in the tournament.
	 * 
	 * @param p_numberOfGames
	 */
	abstract public void setTournamentNumberOfGames(int p_numberOfGames);
	
	/**
	 * Sets the maximum number of turns for each player in the tournament.
	 * If no player has won once this limit is reached, the game will end as a draw.
	 * 
	 * @param p_maxTurns
	 */
	abstract public void setTournamentMaxTurns(int p_maxTurns);

	/**
	 * 	go to next phase
	 */
	public void next() {
		this.d_gameContext.getLogEntryBuffer().logAction("SUCCESS", "go to next phase : " + this.d_gamePhase);
	}

	/**
	 * execute issue_order or execute_order
	 */
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
