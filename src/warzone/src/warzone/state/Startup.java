package warzone.state;
import warzone.service.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import warzone.controller.MapController;
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
	
	/**
	 * Performs the action for user command: loadmap filename
	 * 
	 * Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph
	 * 
	 * @param p_fileName file name of map
	 */
	public void loadMap(String p_fileName){
		 
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
			
			//Validate the map
			if(!(new MapController(d_gameContext).validateMap())) {
				
				GenericView.printError("The map file selected failed validation: " + p_fileName);
				return;
			}
			
			GenericView.printSuccess("Map succesfully loaded: " + p_fileName);
		    
		} catch (Exception e) {
			GenericView.printError("An error occured reading the map file: " + p_fileName);
		}
	 }	


	 public void addPlayer(String p_playerName) {
		 //todo;
	 }	
	 public void removePlayer(String p_playerName){
		 //todo;
	 }	
	
	 /**
	 * Performs the action for user command: populatecountries
	 * 
	 * After user creates all the players, all countries are randomly assigned to players. 
	 * 1-reset the countries
	 * 2-assign countries
	 * 
	 */
	 public void populatecountries(){
		 
		//Make sure there are more than 1 player
		//and there are enough countries to distribute between all the players
		if( d_gameContext.getPlayers().size() < 2 || d_gameContext.getPlayers().size() > d_gameContext.getCountries().size() ) {
			return;
		}
		//reset the countries list and for each player.
		for( Player l_player : d_gameContext.getPlayers().values()) {
			l_player.cleanConqueredCountries();
		}
		//rest all the owner for countries
		for( Country l_countryTemp: d_gameContext.getCountries().values()) {
			l_countryTemp.setOwner(null);
		}		
		
		//Each player will be assigned the same number of countries. Leftover countries will be unassigned (neutral)
		//Create a list of playerIDs from the game context and shuffle their order
		List<String> l_playerNames = new ArrayList<String>(d_gameContext.getPlayers().keySet());
		Collections.shuffle(l_playerNames);
		
		//Create a list of countryIDs from the game context and shuffle their order
		List<Integer> l_countryIDs = new ArrayList<Integer>(d_gameContext.getCountries().keySet());
		Collections.shuffle(l_countryIDs);
				
		//Looping variables
		Country l_country;
		Player l_player;
		int l_ctr = 0;
		int l_playerIndex = 0;
		
		//Loop through each country to assign to a random player
		for(Integer l_countryID : l_countryIDs) {			
			//Reset the index once each player has been assigned a country
			if(l_playerIndex >= l_playerNames.size()) {
				l_playerIndex = 0;
			}
			
			l_country = d_gameContext.getCountries().get(l_countryID);
			l_player = d_gameContext.getPlayers().get(l_playerNames.get(l_playerIndex));
			
			l_country.setOwner(l_player);
			l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
			
			//Update the looping variables
			l_playerIndex++;
			l_ctr++;
		}			
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