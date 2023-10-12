package warzone.state;
import warzone.service.*;
import warzone.model.*;
import warzone.view.*;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
public class OrderExecution extends GamePlay {

	public OrderExecution(GameEngine p_ge) {
		super(p_ge);
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		d_gameEngine.setPhase(new Startup(d_gameEngine));
	}
	
	public void executeOrder(){
		//todo
	 }	
	
	 public void loadMap(String p_fileName){
		 printInvalidCommandMessage();
	 }	


	 public void addPlayer(String p_playerName) {
		 printInvalidCommandMessage();
	 }	
	 public void removePlayer(String p_playerName){
		 printInvalidCommandMessage();
	 }	
	
	 public void populatecountries(){
		 printInvalidCommandMessage();
	 }	
	 
	 public void reinforcement(){
		 printInvalidCommandMessage();
	 }	
 
	public void issueOrder(){
		 printInvalidCommandMessage();
	 }	
}
