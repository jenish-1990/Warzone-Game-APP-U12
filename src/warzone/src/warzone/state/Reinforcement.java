package warzone.state;

import warzone.service.*;
import warzone.model.*;


/**
 * ConcreteState of the State pattern. In this example, defines behavior for
 * commands that are valid in this state, and for the others signifies that the
 * command is invalid.
 */
public class Reinforcement extends GamePlay {

	/**
	 * This is the constructor of the class.
	 * 
	 * @param p_gameEngine the game engine
	 */
	public Reinforcement(GameEngine p_gameEngine) {
		super(p_gameEngine);
		this.d_gamePhase = GamePhase.Reinforcement;

		this.d_gameEngine.assignReinforcements();
	}

	/**
	 * Call this method to go the the next state in the sequence.
	 */
	public void next() {
		d_gameEngine.setPhase(new IssueOrder(d_gameEngine));
		super.next();
	}

	/**
	 * Performs the action for user command: loadmap filename
	 *
	 * Game starts by user selection of a user-saved map file, the map should be a
	 * connected graph
	 *
	 * @param p_fileName the file to load
	 */
	public void loadMap(String p_fileName) {
		printInvalidCommandMessage();
	}

	/**
	 * Performs the action for user command: gameplayer -add playerName
	 *
	 * @param p_playerName player's name
	 */
	public void addPlayer(String p_playerName) {
		printInvalidCommandMessage();
	}

	/**
	 * Performs the action for user command: gameplayer -remove playerName
	 *
	 * @param p_playerName player's name
	 */
	public void removePlayer(String p_playerName) {
		printInvalidCommandMessage();
	}

	/**
	 * Performs the action for user command: assigncountries
	 *
	 * After user creates all the players, all countries are randomly assigned to
	 * players.
	 */
	public void assigncountries() {
		printInvalidCommandMessage();
	}
	
	/**
	 * Sets the list of map files to be used in the tournament.
	 * 
	 * @param p_mapFiles given files
	 */
	public void setTournamentMapFiles(String[] p_mapFiles) {
		printInvalidCommandMessage();
	}
	
	/**
	 * Sets the list of player strategies to be used in the tournament.
	 * 
	 * @param p_playerStrategies given strategies
	 */
	public void setTournamentPlayerStrategies(String[] p_playerStrategies) {
		printInvalidCommandMessage();
	}

	/**
	 * Sets the number of games to be played on each map in the tournament.
	 * 
	 * @param p_numberOfGames given number of games
	 */
	public void setTournamentNumberOfGames(int p_numberOfGames) {
		printInvalidCommandMessage();
	}
	
	/**
	 * Sets the maximum number of turns for each player in the tournament.
	 * If no player has won once this limit is reached, the game will end as a draw.
	 * 
	 * @param p_maxTurns given maximum number of turns
	 */
	public void setTournamentMaxTurns(int p_maxTurns) {
		printInvalidCommandMessage();
	}

	/**
	 * execute issue_order or execute_order
	 */
	public void play(String p_mode) {
		printInvalidCommandMessage();
	}

	/**
	 * Performs the action of issuing order
	 */
	public void issueOrder() {
		printInvalidCommandMessage();
	}

	/**
	 * Performs the action of order execution
	 */
	public void executeOrder() {
		printInvalidCommandMessage();
	}
}
