package warzone.state;
import warzone.service.*;
import warzone.model.*;
import warzone.view.*;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
public class Reinforcement extends GamePlay {

	public Reinforcement(GameEngine p_ge) {
		super(p_ge);
		this.d_gamePhase = GamePhase.Reinforcement;
		
		this.d_gameEngine.assignReinforcements();
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		d_gameEngine.setPhase(new IssueOrder(d_gameEngine));
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
	
	 public void assigncountries(){
		 printInvalidCommandMessage();
	 }	
	 
	 
 	public void play(){
	 printInvalidCommandMessage();
 }
	public void issueOrder(){
		 printInvalidCommandMessage();
	 }	
	public void executeOrder(){
		 printInvalidCommandMessage();
	 }
}
