package warzone.state;
import warzone.service.*;
import warzone.model.*;
import warzone.view.*;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
public class MapEditor extends Phase {

	private MapService d_mapService;
	
	public MapEditor(GameEngine p_ge) {
		super(p_ge);
		d_mapService = new MapService(d_gameContext);
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		d_gameEngine.setPhase(new Startup(d_gameEngine));
	}
	
	 public void addContinent(String p_parameters){
		 //todo
	 }	
	 public void removeContinent(String p_parameters) {
		 //todo
	 }	
	 public void addCountry (String p_parameters) {
		 //todo
	 }	
	 public void removeCountry(String p_parameters) {
		 //todo
	 }	
	 public void addNeighbor (String p_parameters) {
		 //todo
	 }		
	 public void removeNeighbor (String p_parameters) {
		 //todo
	 }	
	 
	/**
	 * Performs the action for the user command: showmap
	 *
	 * Displays the map as text, showing all continents and countries and their respective neighbors.
	 */
	public void showMap () {

		MapView.printMap(d_gameContext);
	}
	
	 public void saveMap (String p_fileName) {
		 //todo
	 }	
	 public void editMap (String p_fileName) {
		 //todo
	 }		
	 public void validateMap() {
		 //todo
	 }	

	 public void addPlayer(String p_playerName) {
		 printInvalidCommandMessage();
	 }	
	 public void removePlayer(String p_playerName){
		 printInvalidCommandMessage();
	 }	
	 public void loadMap(String p_fileName){
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
	public void executeOrder(){
		 printInvalidCommandMessage();
	 }	
	
	

}
