package warzone.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import warzone.controller.MapController;
import warzone.model.Color;
import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.Player;
import warzone.view.GenericView;

public class StartupService {

	private GameContext d_gameContext;

	public StartupService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	/**
	 * Performs the action for user command: loadmap filename
	 * 
	 * Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph
	 * 
	 * @param p_fileName file name of map
	 * @return if map successfully laad
	 */
	public boolean loadMap(String p_fileName) {
		
		String mapDirectory = null;
		
		try {
			
			//Get the map directory from the properties file
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			mapDirectory = properties.getProperty("gameMapDirectory");
			
		} catch (IOException ex) {
				
			GenericView.printError("Error loading properties file.");
			return false;
		}
		
		try {
			
			//Clear gameContext
			d_gameContext.getContinents().clear();
			d_gameContext.getCountries().clear();
		
			File mapFile = new File(mapDirectory + p_fileName);
			
			d_gameContext.setMapFileName(p_fileName);

			//Specified file name does not exist (new map)
			if(!mapFile.exists() || mapFile.isDirectory()) { 

				GenericView.printError("The following map file is invalid, please select another one: " + p_fileName);
				return false;
			}
			
			Scanner scanner = new Scanner(mapFile);
			String line;
			String[] splitArray;
			int continentCtr = 1;
			int id;
			Country country;
			
			boolean processingFiles = false;
			boolean processingContinents = false;
			boolean processingCountries = false;
			boolean processingBorders = false;
			
			while (scanner.hasNextLine()) {
				
				line = scanner.nextLine();
				
				if(line.equals("[files]")) {
					
					processingFiles = true;
					processingContinents = false;
					processingCountries = false;
					processingBorders = false;
					
					line = scanner.nextLine();
				}
				else if(line.equals("[continents]")) {
					
					processingFiles = false;
					processingContinents = true;
					processingCountries = false;
					processingBorders = false;
					
					line = scanner.nextLine();
				}
				else if (line.equals("[countries]")) {
					
					processingFiles = false;
					processingContinents = false;
					processingCountries = true;
					processingBorders = false;
					
					line = scanner.nextLine();
				}
				else if (line.equals("[borders]")) {
					
					processingFiles = false;
					processingContinents = false;
					processingCountries = false;
					processingBorders = true;
					
					line = scanner.nextLine();
				}
				
				if(processingFiles) {
					
					/*
					 *  [files]
					 *	pic europe_pic.jpg
					 *	map europe_map.gif
					 *	crd europe.cards
					 */
					
					if(line.startsWith("pic")) {
						
						d_gameContext.setMapFilePic(line.substring(4));
					}
					else if(line.startsWith("map")) {
						
						d_gameContext.setMapFileMap(line.substring(4));
					}
					else if(line.startsWith("crd")) {
						
						d_gameContext.setMapFileCards(line.substring(4));
					}
				}
				else if(processingContinents && !line.trim().isEmpty()) {
					
					/*
					 *  [continents]
					 *	North_Europe 5 red
					 *	East_Europe 4 magenta
					 *	South_Europe 5 green
					 *	West_Europe 3 blue
					 */
					
					splitArray = line.split("\\s+");
										
					d_gameContext.getContinents().put(continentCtr, 
							new Continent(continentCtr, splitArray[0], Integer.parseInt(splitArray[1]), Color.valueOf(splitArray[2].toUpperCase())));
					
					continentCtr++;
				}
				else if(processingCountries && !line.trim().isEmpty()) {
					
					/*
					 *  [countries]
					 *	1 England 1 164 126
					 *	2 Scotland 1 158 44
					 *	3 N_Ireland 1 125 70
					 *	4 Rep_Ireland 1 106 90
					 */
					
					splitArray = line.split("\\s+");
					
					id = Integer.parseInt(splitArray[0]);
					country = new Country(id, splitArray[1], Integer.parseInt(splitArray[3]), 
							Integer.parseInt(splitArray[4]), d_gameContext.getContinents().get(Integer.parseInt(splitArray[2])));
					
					d_gameContext.getCountries().put(id, country);
					
					d_gameContext.getContinents().get(Integer.parseInt(splitArray[2])).getCountries().put(id, country);
				}
				else if(processingBorders && !line.trim().isEmpty()) {
					
					/*
					 *  [borders]
					 *	1 8 21 6 7 5 2 3 4
					 *	2 8 1 3
					 *	3 1 2
					 *	4 22 1 5	
					 */
					
					splitArray = line.split("\\s+");
					country = d_gameContext.getCountries().get(Integer.parseInt(splitArray[0]));
					
					for(int i = 1; i < splitArray.length; i++) {
						
						id = Integer.parseInt(splitArray[i]);
						country.getNeighbors().put(id, d_gameContext.getCountries().get(id));
					}
				}
			}
		    
			scanner.close();
			
			//Validate the map
			if(!(new MapController(d_gameContext).validateMap())) {
				
				GenericView.printError("The map file selected failed validation: " + p_fileName);
				return false;
			}
			
			GenericView.printSuccess("Map succesfully loaded: " + p_fileName);
		    
		} catch (Exception e) {
		      
			GenericView.printError("An error occured reading the map file: " + p_fileName);
			return false;
		}
		
		return true;
	}

	/**
	 * Performs the action for user command: assigncountries
	 * 
	 * After user creates all the players, all countries are randomly assigned to players. 
	 * 
	 * @return
	 */
	public boolean assignCountries() {

		//Make sure there are enough countries to distribute between all the players
		if(d_gameContext.getPlayers().size() > d_gameContext.getCountries().size()) {
			
			return false;
		}
		
		//Each player will be assigned the same number of countries. Leftover countries will be unassigned (neutral)
		int countriesToAssign = d_gameContext.getCountries().size() - (d_gameContext.getCountries().size() % d_gameContext.getPlayers().size());
		
		//Create a list of playerIDs from the game context and shuffle their order
		List<String> playerNames = new ArrayList<String>(d_gameContext.getPlayers().keySet());
		Collections.shuffle(playerNames);
		
		//Create a list of countryIDs from the game context and shuffle their order
		List<Integer> countryIDs = new ArrayList<Integer>(d_gameContext.getCountries().keySet());
		Collections.shuffle(countryIDs);
				
		//Looping variables
		Country country;
		Player player;
		int ctr = 0;
		int playerIndex = 0;
		
		//Loop through each country to assign to a random player
		for(Integer countryID : countryIDs) {		
			
			//Stop assigning countries once the remaining countries is less than the number of players
			if (ctr >= countriesToAssign) {
				
				return true;
			}
			
			//Reset the index once each player has been assigned a country
			if(playerIndex >= playerNames.size()) {
				playerIndex = 0;
			}
			
			country = d_gameContext.getCountries().get(countryID);
			player = d_gameContext.getPlayers().get(playerNames.get(playerIndex));
			
			country.setOwner(player);
			player.getConqueredCountries().put(country.getCountryID(), country);
			
			//Update the looping variables
			playerIndex++;
			ctr++;
		}
		
		return true;
	}
}
