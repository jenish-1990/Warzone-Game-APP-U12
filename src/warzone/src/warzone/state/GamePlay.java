package warzone.state;
import warzone.service.*;
import warzone.model.*;
import warzone.view.*;

import java.io.IOException;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
public abstract class GamePlay extends Phase {

	public GamePlay(GameEngine p_ge) {
		super(p_ge);
		this.d_gamePhase = GamePhase.GamePlay;
	}
	abstract public void next();

	/**
	 * Performs the action for user command: showmap
	 *
	 * Shows all countries and continents, armies on each country, ownership,
	 * and connectivity in a way that enables efficient game play
	 */
	 public void showMap() {
		 MapView.printMapWithArmies(d_gameContext.getContinents());
	 }	
	
	 public void addContinent(String p_parameters){
		 printInvalidCommandMessage();
	 }	
	 public void removeContinent(String p_parameters) {
		 printInvalidCommandMessage();
	 }	
	 public void addCountry (String p_parameters) {
		 printInvalidCommandMessage();
	 }	
	 public void removeCountry(String p_parameters) {
		 printInvalidCommandMessage();
	 }	
	 public void addNeighbor (String p_parameters) {
		 printInvalidCommandMessage();
	 }		
	 public void removeNeighbor (String p_parameters) {
		 printInvalidCommandMessage();
	 }
	public void play(){
		printInvalidCommandMessage();
	}
	/**
	 * print invalid message and return false
	 * @param p_fileName the filename
	 * @return false
	 */
	public boolean saveMap (String p_fileName) {
			printInvalidCommandMessage();
			return false;
	 }

	/**
	 * print invalid message and return false
	 * @param p_fileName the filename
	 * @return false
	 */
	 public boolean editMap (String p_fileName) {
		 printInvalidCommandMessage();
		 return false;
	 }

	/**
	 * print invalid message and return false
	 * @return false
	 */
	 public boolean validateMap() {
		 printInvalidCommandMessage();
		 return false;
	 }		
	 

}
