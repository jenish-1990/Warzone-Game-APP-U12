package warzone.state;

import warzone.service.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import warzone.model.*;
import warzone.view.*;

/**
 * ConcreteState of the State pattern. In this example, defines behavior for
 * commands that are valid in this state, and for the others signifies that the
 * command is invalid.
 */
public class Startup extends GamePlay {

	/**
	 * Startup Service
	 */
	private StartupService d_startupService;
	/**
	 * Log Entry Buffer
	 */
	private LogEntryBuffer d_logEntryBuffer;

	/**
	 * constructor of the startup class
	 * 
	 * @param p_gameEngine gameengine instance to initial the class
	 */
	public Startup(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_startupService = new StartupService(p_gameEngine);
		d_logEntryBuffer = d_gameContext.getLogEntryBuffer();
		this.d_gamePhase = GamePhase.STARTUP;
	}

	/**
	 * Call this method to go the the next state in the sequence.
	 */
	public void next() {
		
		if(d_gameEngine.getIsInTournamentMode() == true) {
			
			d_gameEngine.playTournament();
			d_gameEngine.reboot();			
		}	
		else if(d_gameEngine.isSingleMode()) {
			d_gameEngine.playSingleMode();
			d_gameEngine.reboot();
		}		
		else if (d_gameEngine.isReadyToStart()) {			
			d_gameEngine.setPhase(new Reinforcement(d_gameEngine));
			super.next();
		}
		else {			
			GenericView.printWarning("It is no ready to play, please check prerequists.");
		}
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
		d_startupService.loadMap(p_fileName);
	}

	/**
	 * Performs the action for user command: gameplayer -add playerName
	 * 
	 * @param p_playerName player's name
	 */
	public void addPlayer(String p_playerName) {
		if (p_playerName == null || p_playerName.trim().equals("")) {
			d_logEntryBuffer.logAction("ERROR", "Invalid player name.");
			return;
		}
		// split command with any number of whitespace
		String[] l_paraArray = p_playerName.split("\\s+");
		String l_playerName = "";
		PlayerStrategyType l_playerStrategyType = PlayerStrategyType.HUMAN;
		l_playerName = l_paraArray[0];
		if(l_paraArray.length >1) {
			try {
				l_playerStrategyType = PlayerStrategyType.valueOf(l_paraArray[1].toUpperCase());
			}
			catch(Exception ex) {
				GenericView.printError("Error happen when converting the Player Strategy Type [" + l_paraArray[1] + "], please try again.");
				return;
			}
			
		}
		
		// 1. create a new player instance
		Player l_player = new Player(l_playerName,l_playerStrategyType);

		// 2. add player to PlayerService
		boolean l_ok = d_startupService.addPlayer(l_player);

		// 3. render to view
		if (l_ok) {
			d_logEntryBuffer.logAction("SUCCESS",
					String.format("Player [%s - %s] was added successfully.", l_player.getName(), l_player.getPlayerStrategyType() ));
		} else {
			d_logEntryBuffer.logAction("ERROR", String.format("Player [%s - %s] was added failed.", l_player.getName(), l_player.getPlayerStrategyType()));
		}
	}

	/**
	 * Performs the action for user command: gameplayer -remove playerName
	 * 
	 * @param p_playerName player's name
	 */
	public void removePlayer(String p_playerName) {
		if (d_startupService.removePlayer(p_playerName)) {
			d_logEntryBuffer.logAction("SUCCESS", String.format("Player [%s] was removed successfully.", p_playerName));
		} else {
			d_logEntryBuffer.logAction("ERROR", String.format("Failed to remove Player [%s].", p_playerName));
		}
	}

	/**
	 * Performs the action for user command: assigncountries
	 * 
	 * After user creates all the players, all countries are randomly assigned to
	 * players.
	 */
	public void assigncountries() {
		// check if current map is valid.
		if (!(new MapService(GameContext.getGameContext()).validateMap())) {
			d_logEntryBuffer.logAction("ERROR", "The map is invalid,please fix it before assigning countries");
			return;
		}

		boolean result = d_startupService.assignCountries();
		if (result == false) {
			d_logEntryBuffer.logAction("ERROR",
					"Must have more than 2 players, and map have at least the same number of countries as players ");
		} else {
			d_logEntryBuffer.logAction("SUCCESS", "Succeed to assign all the countries to players");
		}
	}
	
	/**
	 * Sets the list of map files to be used in the tournament.
	 * 
	 * @param p_mapFiles given map files
	 */
	public void setTournamentMapFiles(String[] p_mapFiles) {
		
		TournamentContext.getTournamentContext().setMapFiles(Arrays.asList(p_mapFiles));
		d_gameEngine.setIsInTournamentMode(true);
	}
	
	/**
	 * Sets the list of player strategies to be used in the tournament.
	 * 
	 * @param p_playerStrategies given strategies
	 */
	public void setTournamentPlayerStrategies(String[] p_playerStrategies) {
		
		List<PlayerStrategyType> playerStrategyTypes = new ArrayList<PlayerStrategyType>();
		
		for(String playerStrategy : p_playerStrategies) {
			try {
				playerStrategyTypes.add(PlayerStrategyType.valueOf(playerStrategy.toUpperCase()));
			}catch(Exception ex) {
				GenericView.printError("Fail occur when adding new player strategy into Tournament. " + ex.toString());
				GenericView.printError("Skip this playerStrategy .");
			}
		}
		
		TournamentContext.getTournamentContext().setPlayerStrategies(playerStrategyTypes);
		d_gameEngine.setIsInTournamentMode(true);
	}

	/**
	 * Sets the number of games to be played on each map in the tournament.
	 * 
	 * @param p_numberOfGames given  number of games
	 */
	public void setTournamentNumberOfGames(int p_numberOfGames) {
		
		TournamentContext.getTournamentContext().setNumberOfGames(p_numberOfGames);
		d_gameEngine.setIsInTournamentMode(true);
	}
	
	/**
	 * Sets the maximum number of turns for each player in the tournament.
	 * If no player has won once this limit is reached, the game will end as a draw.
	 * 
	 * @param p_maxTurns given maximum number of turns
	 */
	public void setTournamentMaxTurns(int p_maxTurns) {
		
		TournamentContext.getTournamentContext().setMaxTurns(p_maxTurns);
		d_gameEngine.setIsInTournamentMode(true);
	}

	/**
	 * execute issue_order or execute_order
	 */
	public void play(String p_mode) {
		printInvalidCommandMessage();
	}

	/**
	 * Performs the action for user command: reinforcement
	 */
	public void reinforcement() {
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
