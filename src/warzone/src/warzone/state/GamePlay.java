package warzone.state;
import warzone.service.*;
import warzone.model.*;
import warzone.view.*;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
public abstract class GamePlay extends Phase {

	public GamePlay(GameEngine p_ge) {
		super(p_ge);
	}
	abstract public void next();

	 public void showMap() {
		 //todo
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
	
	 public void saveMap (String p_fileName) {
		 printInvalidCommandMessage();
	 }	
	 public void editMap (String p_fileName) {
		 printInvalidCommandMessage();
	 }		
	 public void validateMap() {
		 printInvalidCommandMessage();
	 }		
	 

}
