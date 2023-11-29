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
public class MapEditor extends Phase {

	/**
	 * Map Service
	 */
	private MapService d_mapService;
	/**
	 * Continent Service
	 */
	private ContinentService d_continentService;
	/**
	 * Country Service
	 */
	private CountryService d_countryService;
	/**
	 * Neighbor Service
	 */
	private NeighborService d_neighborService;
	/**
	 * LogEntry Buffer
	 */
	private LogEntryBuffer d_logEntryBuffer;

	/**
	 * Constructor for MapEditor
	 * @param p_gameEngine Game Engine
	 */
	public MapEditor(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_mapService = new MapService(d_gameContext);
		d_continentService = new ContinentService(d_gameContext);
		d_countryService  = new CountryService(d_gameContext);
		d_neighborService = new NeighborService(d_gameContext);
		d_logEntryBuffer = d_gameContext.getLogEntryBuffer();
		this.d_gamePhase = GamePhase.MAPEDITOR;
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		d_gameEngine.setPhase(new Startup(d_gameEngine));
		super.next();
	}
	
	/**
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	public void addContinent(String p_parameters) {

		if(p_parameters == null) {
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}

		int l_continentID = -1;
		int l_bonusReinforcements = -1;
		// separate the parameter string
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		// check if parameter length is valid
		if(l_parameters.length == 2 ) {
			l_continentID = CommonTool.parseInt(l_parameters[0]);
			l_bonusReinforcements = CommonTool.parseInt(l_parameters[1]);
		}
		// if continent id or name is not correct, return error info
		if(l_continentID == -1 || l_bonusReinforcements < 0){
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}

		addContinent(l_continentID, l_bonusReinforcements);
	}

	/**
	 * add continent into map
	 * @param p_continentID continent id
	 * @param p_bonusReinforcements bonusReinforcements
	 */
	public void addContinent(int p_continentID, int p_bonusReinforcements) {
		//1. create a new continent instance
		Continent l_Continent = new Continent(p_continentID, "CONTINENT-"+p_continentID);
		l_Continent.setBonusReinforcements(p_bonusReinforcements);
		//2. add continent to ContinentService
		d_continentService.add(l_Continent);

		//3. render to view
		d_logEntryBuffer.logAction("SUCCESS", String.format("Continent [%s] was added successfully.", l_Continent.getContinentName()));
	}

	/**
	 * Performs the action for the user command: editcontinent -remove continentID
	 * @param p_parameters id of continent
	 */
	public void removeContinent(String p_parameters) {
		
		//parse [p_parameters] to  [ l_continentID ]
		if(p_parameters == null)
		{
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}
		int l_continentID = CommonTool.parseInt(p_parameters);
		if(l_continentID == -1 ){	
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}
		removeContinent(l_continentID);
	}

	/**
	 * remove the continent from the map
	 * @param p_continentID the continent id
	 */
	public void removeContinent(int p_continentID) {
		if( d_continentService.remove(p_continentID)) {
			d_logEntryBuffer.logAction("SUCCESS", String.format("Continent ID [%s] was removed successfully.", p_continentID));
		}			
		else {
			d_logEntryBuffer.logAction("WARNING",String.format("Failed to remove Continent ID [%s].", p_continentID ));
		}
	}

	/**
	 * add country to the map
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	public void addCountry (String p_parameters) {
		
		//parse [p_parameters] to  [ l_continentID, String l_continentName]
		if(p_parameters == null){
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}

		int l_countryID = -1, l_continentID = -1;
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		// check if parameter length is valid
		if(l_parameters.length == 2 ) {
			l_countryID = CommonTool.parseInt(l_parameters[0]);
			l_continentID = CommonTool.parseInt(l_parameters[1]);
		}
		// if country id or name is not correct, return error info
		if(l_countryID == -1 || l_continentID == -1 ){
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}
		else
			addCountry(l_countryID, l_continentID);
	}

	/**
	 * Performs the action for the user command: editcountry -add countryID continentID
	 * @param p_countryID the id of country to add
	 * @param p_continentID the id of countinent add to
	 */
	public void addCountry (int p_countryID, int p_continentID) {
		if( d_countryService.addCountryToContient(p_countryID, p_continentID) ) {
			d_logEntryBuffer.logAction("SUCCESS", String.format("Country ID [%s] was added to Continent [%s] successfully.", p_countryID, p_continentID));
		}			
		else {
			if(d_countryService.isExisted(p_countryID))
				d_logEntryBuffer.logAction("WARNING", String.format(" Country [%s] was added, but failed to add Country ID [%s] to Continent [%s].", p_countryID , p_countryID , p_continentID));
			else
				d_logEntryBuffer.logAction("WARNING", String.format("Failed to add Country ID [%s] to Continent [%s].", p_countryID , p_continentID));
		}
	}

	/**
	 * remove the country from map
	 * @param p_parameters parameters parsed by parser
	 */
	public void removeCountry(String p_parameters) {
		
		//parse [p_parameters] 
		if(p_parameters == null) {
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}

		int l_countryID = CommonTool.parseInt(p_parameters);		
		if(l_countryID == -1 ){	
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;	
		}

		removeCountry(l_countryID);
	}

	/**
	 * Performs the action for the user command: editcountry -remove countryID
	 * @param p_countryID the id of the country to remove
	 */
	public void removeCountry (int p_countryID) {
		if( d_countryService.remove(p_countryID)) {
			d_logEntryBuffer.logAction("SUCCESS", String.format("Country ID [%s] was removed successfully.", p_countryID));
		}			
		else {
			d_logEntryBuffer.logAction("WARNING", String.format("Failed to remove Country ID [%s].", p_countryID ));
		}
	}

	/**
	 * Performs the action for the user command: editneighbor -add countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	public void addNeighbor (String p_parameters) {
		
		//parse [p_parameters]
		if(p_parameters == null){			
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}

		int l_countryID = -1, l_neighborCountryID = -1;
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		// check if parameter length is valid
		if(l_parameters.length == 2 ) {
			l_countryID = CommonTool.parseInt(l_parameters[0]);
			l_neighborCountryID = CommonTool.parseInt(l_parameters[1]);
		}
		// if country ids not correct, return error info
		if(l_countryID == -1 || l_neighborCountryID == -1 ){
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}
		addNeighbor(l_countryID, l_neighborCountryID);
	}

	/**
	 * add neighbor
	 * @param p_countryID neighbor from country
	 * @param p_neighborCountryID neighbor to country
	 */
	public void addNeighbor (int p_countryID, int p_neighborCountryID) {

		if( d_neighborService.add(p_countryID, p_neighborCountryID)) {
			d_logEntryBuffer.logAction("SUCCESS", String.format("Neighbor [%s] was added to Country [%s] successfully.", p_neighborCountryID, p_countryID));
		}			
		else {
			d_logEntryBuffer.logAction("WARNING", String.format("Failed to add Neighbor [%s] to Country [%s].", p_neighborCountryID, p_countryID));
		}
	}	
	
	/**
	 * Performs the action for the user command: editneighbor -remove countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 */
	public void removeNeighbor (String p_parameters) {
		
		//parse [p_parameters]
		if(p_parameters == null){			
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}

		int l_countryID = -1, l_neighborCountryID = -1;
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		if(l_parameters.length == 2 ) {
			l_countryID = CommonTool.parseInt(l_parameters[0]);
			l_neighborCountryID = CommonTool.parseInt(l_parameters[1]);
		}
		// if country ids not correct, return error info
		if(l_countryID == -1 || l_neighborCountryID == -1 ){
			d_logEntryBuffer.logAction("ERROR", "Missing valid parameters.");
			return;
		}
		removeNeighbor(l_countryID, l_neighborCountryID);
	}

	/**
	 * remove neighbor relationship
	 * @param p_countryID the from country id
	 * @param p_neighborCountryID the to country id
	 */
	public void removeNeighbor (int p_countryID, int p_neighborCountryID) {

		if( d_neighborService.remove(p_countryID, p_neighborCountryID)) {
			d_logEntryBuffer.logAction("SUCCESS",String.format("Neighbor [%s] was removed from Country [%s] successfully.", p_neighborCountryID, p_countryID));
		}			
		else {
			d_logEntryBuffer.logAction("WARNING", String.format("Failed to remove Neighbor [%s] to Country [%s].", p_neighborCountryID, p_countryID));
		}
	}
	 
	/**
	 * Performs the action for the user command: showmap
	 *
	 * Displays the map as text, showing all continents and countries and their respective neighbors.
	 */
	public void showMap () {
		d_logEntryBuffer.logAction("SUCCESS", "");
		MapView.printMap(d_gameContext);
	}

	/**
	 * Performs the action for the user command: savemap filename
	 *
	 * Save a map to a text file exactly as edited (using the "domination" game map format).
	 * @param p_fileName the filename
	 * @return true if successfully save the map, otherwise return false
	 * @throws IOException the exception of saving files
	 */
	public boolean saveMap(String p_fileName) throws IOException {

		// validate if the filename is legal
		if(p_fileName == null || p_fileName.trim().isEmpty() || p_fileName.trim().length() > 20 ) {
			d_logEntryBuffer.logAction("ERROR", "InValid File Name, please type a valid file name, with length less than 20.");
			return false;
		}
		
		if(! d_mapService.validateMap() ) {
			d_logEntryBuffer.logAction("ERROR", "InValid map, please check the map.");
			return false;
		}

		// call mapService to save the map and return the path
		p_fileName = p_fileName.trim();
		try{
			if(d_mapService.saveMap(p_fileName)) {
				d_logEntryBuffer.logAction("SUCCESS", "Map was saved in :" + this.d_gameContext.getMapfolder() + p_fileName );
				return true;
			}
			else {
				d_logEntryBuffer.logAction("ERROR", "Exception occured when saving the map, please valid the file name or contact the Administrator." );
				return false;
			}
		}
		catch(Exception ex) {
			d_logEntryBuffer.logAction("ERROR", "Exception occured when saving the map. " + ex.toString());
			return false;
		}
	}

	/**
	 * Performs the action for the user command: editmap filename
	 *
	 * Load a map from an existing "domination" map file,
	 * or create a new map from scratch if the file does not exist
	 * @param p_fileName the filename
	 * @return true if successfully edit the map, otherwise return false
	 */
	public boolean editMap (String p_fileName) {
		return d_mapService.editMap(p_fileName);
	}

	/**
	 * Performs the action for the user command: validatemap
	 *
	 * Verification of map correctness. The map should be automatically validated upon loading
	 * and before saving (at least 3 types of incorrect maps). The validatemap command can be
	 * triggered any time during map editing.
	 * @return true if it is a valid map, otherwise return false
	 */
	public boolean validateMap() {
		if(! d_mapService.validateMap() ) {
			d_logEntryBuffer.logAction("ERROR", "It is not a connected map.");
			return false;
		}
		else {
			d_logEntryBuffer.logAction("SUCCESS", "Yeah! You got a connected map!");
			return true;
		}
	}
	
	/**
	 * execute issue_order or execute_order
	 */
	public void play(){
		printInvalidCommandMessage();
	}
	
	/**
	 * Performs the action for user command: gameplayer -add playerName
	 *
	 * @param p_playerName player's name
	 */
	public void addPlayer(String p_playerName) { printInvalidCommandMessage(); }
	
	/**
	 * Performs the action for user command: gameplayer -remove playerName
	 *
	 * @param p_playerName player's name
	 */
	public void removePlayer(String p_playerName){
		 printInvalidCommandMessage();
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
		 printInvalidCommandMessage();
	 }		
	
	/**
	 * Performs the action for user command: assigncountries
	 *
	 * After user creates all the players, all countries are randomly assigned to players.
	 */
	public void assigncountries(){
		 printInvalidCommandMessage();
	 }	
	
	/**
	 * Sets the list of map files to be used in the tournament.
	 * 
	 * @param p_mapFiles
	 */
	public void setTournamentMapFiles(String[] p_mapFiles) {
		printInvalidCommandMessage();
	}
	
	/**
	 * Sets the list of player strategies to be used in the tournament.
	 * 
	 * @param p_playerStrategies
	 */
	public void setTournamentPlayerStrategies(String[] p_playerStrategies) {
		printInvalidCommandMessage();
	}

	/**
	 * Sets the number of games to be played on each map in the tournament.
	 * 
	 * @param p_numberOfGames
	 */
	public void setTournamentNumberOfGames(int p_numberOfGames) {
		printInvalidCommandMessage();
	}
	
	/**
	 * Sets the maximum number of turns for each player in the tournament.
	 * If no player has won once this limit is reached, the game will end as a draw.
	 * 
	 * @param p_maxTurns
	 */
	public void setTournamentMaxTurns(int p_maxTurns) {
		printInvalidCommandMessage();
	}
	
	/**
	 * Performs the action for user command: reinforcement
	 */
	public void reinforcement(){
		 printInvalidCommandMessage();
	 }	
 
	/**
	 * Performs the action of issuing order
	 */
	public void issueOrder(){
		 printInvalidCommandMessage();
	 }	
	
	/**
	 * Performs the action of order execution
	 */
	public void executeOrder(){
		 printInvalidCommandMessage();
	 }	
	
	

}
