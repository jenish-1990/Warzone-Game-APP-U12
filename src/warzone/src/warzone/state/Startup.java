package warzone.state;
import warzone.service.*;
import warzone.model.*;
import warzone.view.*;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
public class Startup extends GamePlay {

	public Startup(GameEngine p_ge) {
		super(p_ge);
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		d_gameEngine.setPhase(new Reinforcement(d_gameEngine));
	}
	
	 public void loadMap(String p_fileName){
		 //todo;
	 }	


	 public void addPlayer(String p_playerName) {
		 //todo;
	 }	
	 public void removePlayer(String p_playerName){
		 //todo;
	 }	
	
	 public void populatecountries(){
		 //todo;
	 }	
	 
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
