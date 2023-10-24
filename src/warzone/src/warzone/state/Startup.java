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

	/**
	 *  constructor of the startup class
	 * @param p_ge gameengine instance to initial the class
	 */
	public Startup(GameEngine p_ge) {
		super(p_ge);
		d_startupService=new StartupService(d_gameContext);
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
			GenericView.printWarning("Invalid player name.");
			return;
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
	}

	/**
	 * Performs the action for user command: gameplayer -remove playerName
	 * 
	 * @param p_playerName player's name
	 */
	public void removePlayer(String p_playerName){
		if( d_startupService.removePlayer(p_playerName)) {
			GenericView.printSuccess( String.format("Player [%s] was removed successfully.", p_playerName) );
		}else {
			GenericView.printWarning( String.format("Failed to remove Player [%s].", p_playerName ) );
		}
	}

	/**
	 * Performs the action for user command: assigncountries
	 * 
	 * After user creates all the players, all countries are randomly assigned to players. 
	 */
	public void assigncountries(){
		boolean result = d_startupService.assignCountries();
		if(result == false) {
			GenericView.printError("Must have more than 2 players, and map have at least the same number of countries as players ");
		}
		else {
			GenericView.printSuccess("Succeed to assign all the countries to players");
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
