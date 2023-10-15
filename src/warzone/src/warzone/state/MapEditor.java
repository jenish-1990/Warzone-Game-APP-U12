package warzone.state;
import warzone.service.*;

import java.io.File;
import java.util.Scanner;

import warzone.controller.MapController;
import warzone.model.*;
import warzone.view.*;

/**
 *	ConcreteState of the State pattern. In this example, defines behavior 
 *  for commands that are valid in this state, and for the others signifies  
 *  that the command is invalid. 
 */
public class MapEditor extends Phase {

	private MapService d_mapService;
	private ContinentService d_continentService;
	private CountryService d_countryService;
	private NeighborService d_neighborService;
	
	public MapEditor(GameEngine p_ge) {
		super(p_ge);
		d_mapService = new MapService(d_gameContext);
		d_continentService = new ContinentService(d_gameContext);
		d_countryService  = new CountryService(d_gameContext);
		d_neighborService = new NeighborService(d_gameContext);
	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	public void next() {
		d_gameEngine.setPhase(new Startup(d_gameEngine));
	}
	
	/**
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 * @return the result of adding new continent
	 */
	public void addContinent(String p_parameters) {
		if(p_parameters == null) {
			GenericView.printError("Missing valid parameters.");
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
			GenericView.printError("Missing valid parameters.");
			return;
		}

		addContinent(l_continentID, l_bonusReinforcements);		
	}

	/**
	 * add continent into map
	 * @param p_continentID continent id
	 * @param p_bonusReinforcements bonusReinforcements
	 * @return true if successfully add the continent, otherwise return false
	 */
	public void addContinent(int p_continentID, int p_bonusReinforcements) {
				
		//1. create a new continent instance
		Continent l_Continent = new Continent(p_continentID, "CONTINENT-"+p_continentID);
		l_Continent.setBonusReinforcements(p_bonusReinforcements);
		//2. add continent to ContinentService
		d_continentService.add(l_Continent);
		
		//3. render to view
		GenericView.printSuccess( String.format("Continent [%s] was added successfully.", l_Continent.getContinentName()) );
	}

	/**
	 * Performs the action for the user command: editcontinent -remove continentID
	 * @param p_parameters id of continent
	 * @return if remove success
	 */
	public void removeContinent(String p_parameters) {
		//parse [p_parameters] to  [ l_continentID ]
		if(p_parameters == null)
		{			
			GenericView.printError("Missing valid parameters.");
			return;
		}
		int l_continentID = CommonTool.parseInt(p_parameters);
		if(l_continentID == -1 ){	
			GenericView.printError("Missing valid parameters.");	
			return;
		}
		removeContinent(l_continentID);
	}

	/**
	 * remove the continent from the map
	 * @param p_continentID the continent id
	 * @return true if successfully removed, otherwise return false
	 */
	public void removeContinent(int p_continentID) {
		if( d_continentService.remove(p_continentID)) {
			GenericView.printSuccess( String.format("Continent ID [%s] was removed successfully.", p_continentID) );
		}			
		else {
			GenericView.printWarning( String.format("Failed to remove Continent ID [%s].", p_continentID ) );
		}
	}
	
	/**
	 * add country to the map
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 * @return true if successfully add country, otherwise return false
	 */
	public void addCountry (String p_parameters) {
		//parse [p_parameters] to  [ l_continentID, String l_continentName]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
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
			GenericView.printError("Missing valid parameters.");
			return;
		}
		else
			addCountry(l_countryID, l_continentID);
	}

	/**
	 * Performs the action for the user command: editcountry -add countryID continentID
	 * @param p_countryID the id of country to add
	 * @param p_continentID the id of countinent add to
	 * @return true if successfully added, otherwise return false
	 */
	public void addCountry (int p_countryID, int p_continentID) {		
		if( d_countryService.addCountryToContient(p_countryID, p_continentID) ) {
			GenericView.printSuccess( String.format("Country ID [%s] was added to Continent [%s] successfully.", p_countryID, p_continentID) );
			return;
		}			
		else {
			if(d_countryService.isExisted(p_countryID))
				GenericView.printWarning( String.format("Country [%s] was added, but failed to add Country ID [%s] to Continent [%s].", p_countryID , p_countryID , p_continentID) );	
			else
				GenericView.printWarning( String.format("Failed to add Country ID [%s] to Continent [%s].", p_countryID , p_continentID) );
			return;
		}	
	}
	
	/**
	 * remove the country from map
	 * @param p_parameters parameters parsed by parser
	 * @return true if successfully remove the country, otherwise return false
	 */
	public void removeCountry(String p_parameters) {
		//parse [p_parameters] 
		if(p_parameters == null) {
			GenericView.printError("Missing valid parameters.");
			return;
		}

		int l_countryID = CommonTool.parseInt(p_parameters);		
		if(l_countryID == -1 ){	
			GenericView.printError("Missing valid parameters.");	
			return;	
		}
		
		removeCountry(l_countryID);
	}	
	
	/**
	 * Performs the action for the user command: editcountry -remove countryID
	 * @param p_countryID the id of the country to remove
	 * @return true if successfully remove the country, otherwise return false
	 */
	public void removeCountry (int p_countryID) {
		if( d_countryService.remove(p_countryID)) {
			GenericView.printSuccess( String.format("Country ID [%s] was removed successfully.", p_countryID) );
		}			
		else {
			GenericView.printWarning( String.format("Failed to remove Country ID [%s].", p_countryID ) );
		}			
	}
	
	/**
	 * Performs the action for the user command: editneighbor -add countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 * @return true if successfully add neighbor, otherwise return false
	 */
	public void addNeighbor (String p_parameters) {
		//parse [p_parameters]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
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
			GenericView.printError("Missing valid parameters.");
			return;
		}
		addNeighbor(l_countryID, l_neighborCountryID);
	}

	/**
	 * add neighbor
	 * @param p_countryID neighbor from country
	 * @param p_neighborCountryID neighbor to country
	 * @return true if successfully added, otherwise return false
	 */
	public void addNeighbor (int p_countryID, int p_neighborCountryID) {

		if( d_neighborService.add(p_countryID, p_neighborCountryID)) {
			GenericView.printSuccess( String.format("Neighbor [%s] was added to Country [%s] successfully.", p_neighborCountryID, p_countryID) );
		}			
		else {
			GenericView.printWarning( String.format("Failed to add Neighbor [%s] to Country [%s].", p_neighborCountryID, p_countryID) );
		}	
	}	
	
	/**
	 * Performs the action for the user command: editneighbor -remove countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 * @return true if successfully remove the relationship, otherwise return false
	 */
	public void removeNeighbor (String p_parameters) {
		//parse [p_parameters]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
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
			GenericView.printError("Missing valid parameters.");
			return;
		}
		removeNeighbor(l_countryID, l_neighborCountryID);
	}

	/**
	 * remove neighbor relationship
	 * @param p_countryID the from country id
	 * @param p_neighborCountryID the to country id
	 * @return true if successfully remove the relationship, otherwise return false
	 */
	public void removeNeighbor (int p_countryID, int p_neighborCountryID) {

		if( d_neighborService.remove(p_countryID, p_neighborCountryID)) {
			GenericView.printSuccess( String.format("Neighbor [%s] was removed from Country [%s] successfully.", p_neighborCountryID, p_countryID) );
		}			
		else {
			GenericView.printWarning( String.format("Failed to remove Neighbor [%s] to Country [%s].", p_neighborCountryID, p_countryID) );
		}	
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
	
	/**
	 * Load a map from an existing â€œdominationâ€� map file,
	 * or create a new map from scratch if the file does not exist.
	 * 
	 * @param p_fileName file name
	 */	
	public void editMap (String p_fileName) {
		
		String l_mapDirectory = WarzoneProperties.getWarzoneProperties().getGameMapDirectory();
			
		try {
			
			//Clear gameContext
			d_gameContext.reset();

			File l_mapFile = new File(l_mapDirectory + p_fileName);
			
			d_gameContext.setMapFileName(p_fileName);

			//Specified file name does not exist (new map)
			if(!l_mapFile.exists() || l_mapFile.isDirectory()) {
				GenericView.printError("The following map file is invalid, please select another one: " + p_fileName);
				return;
			}
			
			Scanner l_scanner = new Scanner(l_mapFile);
			String l_line;
			String[] l_splitArray;
			int l_continentCtr = 1;
			int l_id;
			Country l_country;

			LoadMapPhase l_loadMapPhase = null;
			
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();

				// determine which part it is
				// file part
				if(l_line.equals("[files]")) {

					l_loadMapPhase = LoadMapPhase.FILES;
					l_line = l_scanner.nextLine();
				}
				// continents part
				else if(l_line.equals("[continents]")) {
					
					l_loadMapPhase = LoadMapPhase.CONTINENTS;
					l_line = l_scanner.nextLine();
				}
				//countries part
				else if (l_line.equals("[countries]")) {
					
					l_loadMapPhase = LoadMapPhase.COUNTRIES;
					l_line = l_scanner.nextLine();
				}
				//borders part
				else if (l_line.equals("[borders]")) {
					
					l_loadMapPhase = LoadMapPhase.BORDERS;
					
					if(!l_scanner.hasNextLine())
						l_loadMapPhase = LoadMapPhase.COMPLETE;
					else{
						l_line = l_scanner.nextLine();
					}
				}

				// read file part
				if(l_loadMapPhase == LoadMapPhase.FILES) {
					
					/*
					 *  [files]
					 *	pic europe_pic.jpg
					 *	map europe_map.gif
					 *	crd europe.cards
					 */
					
					if(l_line.startsWith("pic")) {
						
						d_gameContext.setMapFilePic(l_line.substring(4));
					}
					else if(l_line.startsWith("map")) {
						
						d_gameContext.setMapFileMap(l_line.substring(4));
					}
					else if(l_line.startsWith("crd")) {
						
						d_gameContext.setMapFileCards(l_line.substring(4));
					}
				}
				//read continent part
				else if(l_loadMapPhase == LoadMapPhase.CONTINENTS && !l_line.trim().isEmpty()) {
					
					/*
					 *  [continents]
					 *	North_Europe 5 red
					 *	East_Europe 4 magenta
					 *	South_Europe 5 green
					 *	West_Europe 3 blue
					 */
					
					l_splitArray = l_line.split("\\s+");
										
					d_gameContext.getContinents().put(l_continentCtr,
							new Continent(l_continentCtr, l_splitArray[0], Integer.parseInt(l_splitArray[1]), l_splitArray[2]));
					
					l_continentCtr++;
				}
				//read countries part
				else if(l_loadMapPhase == LoadMapPhase.COUNTRIES && !l_line.trim().isEmpty()) {
					
					/*
					 *  [countries]
					 *	1 England 1 164 126
					 *	2 Scotland 1 158 44
					 *	3 N_Ireland 1 125 70
					 *	4 Rep_Ireland 1 106 90
					 */
					
					l_splitArray = l_line.split("\\s+");
					
					l_id = Integer.parseInt(l_splitArray[0]);
					l_country = new Country(l_id, l_splitArray[1], Integer.parseInt(l_splitArray[3]),
							Integer.parseInt(l_splitArray[4]), d_gameContext.getContinents().get(Integer.parseInt(l_splitArray[2])));
					
					d_gameContext.getCountries().put(l_id, l_country);
					
					d_gameContext.getContinents().get(Integer.parseInt(l_splitArray[2])).getCountries().put(l_id, l_country);
				}
				//read border part
				else if(l_loadMapPhase == LoadMapPhase.BORDERS && !l_line.trim().isEmpty()) {
					
					/*
					 *  [borders]
					 *	1 8 21 6 7 5 2 3 4
					 *	2 8 1 3
					 *	3 1 2
					 *	4 22 1 5	
					 */
					
					l_splitArray = l_line.split("\\s+");
					l_country = d_gameContext.getCountries().get(Integer.parseInt(l_splitArray[0]));
					
					for(int l_temp = 1; l_temp < l_splitArray.length; l_temp++) {
						
						l_id = Integer.parseInt(l_splitArray[l_temp]);
						l_country.getNeighbors().put(l_id, d_gameContext.getCountries().get(l_id));
					}
				}
			}

			//close reading the file
			l_scanner.close();
			
			GenericView.printSuccess("Map succesfully loaded: " + p_fileName);
		    
		} catch (Exception e) {
			GenericView.printError("An error occured reading the map file: " + p_fileName);
		}
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
