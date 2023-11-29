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

	/**
	 * start up service
	 */
	private StartupService d_startUpService;

	/**
	 * The constructor of the class
	 * @param p_gameEngine the game engine
	 */
	public GamePlay(GameEngine p_gameEngine) {
		super(p_gameEngine);
		this.d_gamePhase = GamePhase.GamePlay;
		d_startUpService = new StartupService(p_gameEngine);
	}
	
	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		super.next();
	}

	/**
	 * Performs the action for user command: showmap
	 *
	 * Shows all countries and continents, armies on each country, ownership,
	 * and connectivity in a way that enables efficient game play
	 */
	 public void showMap() {
		 MapView.printMapWithArmies(GameContext.getGameContext().getContinents());
	 }	
	
	/**
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	 public void addContinent(String p_parameters){
		 printInvalidCommandMessage();
	 }
	 
	/**
	 * Performs the action for the user command: editcontinent -remove continentID
	 * @param p_parameters id of continent
	 */
	 public void removeContinent(String p_parameters) {
		 printInvalidCommandMessage();
	 }
	 
	/**
	 * add country to the map
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	 public void addCountry (String p_parameters) {
		 printInvalidCommandMessage();
	 }	
	 
	/**
	 * remove the country from map
	 * @param p_parameters parameters parsed by parser
	 */
	 public void removeCountry(String p_parameters) {
		 printInvalidCommandMessage();
	 }	
	 
	/**
	 * Performs the action for the user command: editneighbor -add countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	 public void addNeighbor (String p_parameters) {
		 printInvalidCommandMessage();
	 }		
	 
	/**
	 * Performs the action for the user command: editneighbor -remove countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	 public void removeNeighbor (String p_parameters) {
		 printInvalidCommandMessage();
	 }
	 
	/**
	 * execute issue_order or execute_order
	 */
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

	/**
	 * save game context
	 * @param p_fileName file name
	 * @return true if success
	 */
	public boolean saveGame(String p_fileName){
		return d_startUpService.saveGame(p_fileName);
	 }

	/**
	 * lode game context
	 * @param p_fileName file name
	 * @return true if success
	 */
	public boolean loadGame(String p_fileName){
		return d_startUpService.loadGame(p_fileName);
	 }
}
