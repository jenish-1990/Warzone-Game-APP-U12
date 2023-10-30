package warzone.state;
import warzone.service.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import warzone.model.*;
import warzone.view.*;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
public class Startup extends GamePlay {
	
	private StartupService d_startupService;
	private LogEntryBuffer d_logEntryBuffer;

	/**
	 *  constructor of the startup class
	 * @param p_ge gameengine instance to initial the class
	 */
	public Startup(GameEngine p_ge) {
		super(p_ge);
		d_startupService=new StartupService(d_gameContext);
		d_logEntryBuffer = d_gameContext.getLogEntryBuffer();
		this.d_gamePhase = GamePhase.STARTUP;
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		if(d_gameEngine.isReadyToStart())
			d_gameEngine.setPhase(new Reinforcement(d_gameEngine));
		else {
			GenericView.printWarning("It is no ready to play, please check prerequists.");
		}
	}
	
	/**
	 * Performs the action for user command: loadmap filename
	 * 
	 * Game starts by user selection of a user-saved map file,
	 * the map should be a connected graph
	 * 
	 * @param p_fileName the file to load
	 */
	public void loadMap(String p_fileName){
		d_startupService.loadMap(p_fileName);
	}

	/**
	 * Performs the action for user command: gameplayer -add playerName
	 * 
	 * @param p_playerName player's name
	 */
	public void addPlayer(String p_playerName) {
		if(p_playerName == null || p_playerName.trim().equals("")) {
			d_logEntryBuffer.logAction("ERROR", "Invalid player name.");
			return;
		}
		//1. create a new player instance
		Player l_player = new Player(p_playerName);
		
		//2. add player to PlayerService
		boolean l_ok=d_startupService.addPlayer(l_player);
		
		//3. render to view
		if(l_ok) {
			d_logEntryBuffer.logAction("SUCCESS", String.format("Player [%s] was added successfully.", l_player.getName()));
		}else {
			d_logEntryBuffer.logAction("ERROR", String.format("Player [%s] was added failed.", l_player.getName()));
		}
	}

	/**
	 * Performs the action for user command: gameplayer -remove playerName
	 * 
	 * @param p_playerName player's name
	 */
	public void removePlayer(String p_playerName){
		if( d_startupService.removePlayer(p_playerName)) {
			d_logEntryBuffer.logAction("SUCCESS",  String.format("Player [%s] was removed successfully.", p_playerName));
		}else {
			d_logEntryBuffer.logAction("ERROR",  String.format("Failed to remove Player [%s].", p_playerName ));
		}
	}

	/**
	 * Performs the action for user command: assigncountries
	 * 
	 * After user creates all the players, all countries are randomly assigned to players. 
	 */
	public void assigncountries(){
		//check if current map is valid.
		if(!(new MapService(d_gameContext).validateMap())) {
			d_logEntryBuffer.logAction("ERROR","The map is invalid,please fix it before assigning countries");
			return ;
		}
		
		boolean result = d_startupService.assignCountries();
		if(result == false) {
			d_logEntryBuffer.logAction("ERROR",  "Must have more than 2 players, and map have at least the same number of countries as players ");
		}
		else {
			d_logEntryBuffer.logAction("SUCCESS",  "Succeed to assign all the countries to players");
		}
	}

	public void play(){ printInvalidCommandMessage();}
	public void reinforcement(){
		printInvalidCommandMessage();
	}

	public void issueOrder(){
		printInvalidCommandMessage();
	}

	public void executeOrder(){
		printInvalidCommandMessage();
	}
}
