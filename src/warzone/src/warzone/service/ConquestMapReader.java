package warzone.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.LoadMapPhase;
import warzone.model.LogEntryBuffer;
import warzone.model.WarzoneProperties;

/**
 * Conquest map reader
 */
public class ConquestMapReader {
	/**
	 * game context
	 */
	private GameContext d_gameContext;

	/**
	 * log entry buffer
	 */
	private LogEntryBuffer d_logEntryBuffer;

	/**
	 * the scanner
	 */
	private Scanner l_scanner;

	/**
	 * The constructor of this class
	 * @param p_gameContext the current game context
	 */
	public ConquestMapReader(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_logEntryBuffer = d_gameContext.getLogEntryBuffer();
	}
	
	/**
	 * Load a map with format 'conquest'
	 * or create a new map from scratch if the file does not exist.
	 * @param p_fileName file name
	 * @return true if success
	 */
	public boolean editConquestMap(String p_fileName) {

		String l_mapDirectory = WarzoneProperties.getWarzoneProperties().getGameMapDirectory();
		
		try {
			
			//Clear gameContext
			d_gameContext.reset();
			
			File l_mapFile = new File(l_mapDirectory + p_fileName);
			
			d_gameContext.setMapFileName(p_fileName);
			
			//Specified file name does not exist (new map)
			if(!l_mapFile.exists() || l_mapFile.isDirectory()) {
				d_logEntryBuffer.logAction("SUCCESS", "Creating a new map: " + p_fileName);
				return true;
			}
		    
		} catch (Exception e) {
			d_logEntryBuffer.logAction("ERROR", "An error occured reading the map file: " + p_fileName);
			return false;
		}
		
		return loadConquestMap(p_fileName);
	}

	/**
	 * Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph.
	 * This method will load map using 'conquest' game format
	 * 
	 * @param p_fileName file name of map
	 * @return if map successfully loaded
	 */
	public boolean loadConquestMap(String p_fileName) {

		String l_mapDirectory = null;

		try {
			//Get the map directory from the properties file
			Properties l_properties = new Properties();
			l_properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			l_mapDirectory = l_properties.getProperty("gameMapDirectory");

		} catch (IOException ex) {
			d_logEntryBuffer.logAction("ERROR", "Error loading properties file.");
			return false;
		}

		try {

			//Clear gameContext
			d_gameContext.reset();

			File l_mapFile = new File(l_mapDirectory + p_fileName);

			d_gameContext.setMapFileName(p_fileName);

			//Specified file name does not exist (new map)
			if(!l_mapFile.exists() || l_mapFile.isDirectory()) {
				d_logEntryBuffer.logAction("ERROR", "The following map file is invalid, please select another one: " + p_fileName);
				return false;
			}

			l_scanner = new Scanner(l_mapFile);
			String l_line;
			String[] l_splitArray;
			int l_continentCtr = 1;
			int l_id = 1;
			Country l_country;
			List<String[]> l_borderList = new LinkedList<String[]>();
			Map<String, Integer> l_continentMap = new HashMap<String, Integer>();
			Map<String, Integer> l_countryMap = new HashMap<String, Integer>();

			LoadMapPhase l_loadMapPhase = null;

			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();

				// determine which part it is
				// file part
				if(l_line.equals("[Map]")) {

					l_loadMapPhase = LoadMapPhase.FILES;
					l_line = l_scanner.nextLine();
				}
				// continents part
				else if(l_line.equals("[Continents]")) {

					l_loadMapPhase = LoadMapPhase.CONTINENTS;
					l_line = l_scanner.nextLine();
				}
				// continents part
				else if(l_line.equals("[Territories]")) {
					l_loadMapPhase = LoadMapPhase.TERRITORIES;
					l_line = l_scanner.nextLine();
				}

				// read file part
				if(l_loadMapPhase == LoadMapPhase.FILES) {

					/*
					 *  [files]
					 *	pic europe_pic.jpg
					 *	map europe_map.gif
					 *	crd europe.cards
					 */

					if(l_line.startsWith("image=")) {						
						d_gameContext.setMapFilePic(l_line.substring(6));
					}
				}
				//read continent part
				else if(l_loadMapPhase == LoadMapPhase.CONTINENTS && !l_line.trim().isEmpty()) {


					/*
					 *  [continents]
						Cockpit=5
						Right Thruster=6
						Left Thruster=6
						Right Cargo=10
					 */

					l_splitArray = l_line.split("=");

					d_gameContext.getContinents().put(l_continentCtr,
							new Continent(l_continentCtr, l_splitArray[0], Integer.parseInt(l_splitArray[1]), null));
					l_continentMap.put(l_splitArray[0], l_continentCtr);
					l_continentCtr++;
				}
				//read territories part
				else if(l_loadMapPhase == LoadMapPhase.TERRITORIES && !l_line.trim().isEmpty()) {

					/*
					 *  [territories]
						Cockpit01,658,355,Cockpit,Cockpit02,Territory33
						Cockpit02,658,375,Cockpit,Cockpit01,Cockpit03,Territory33,Territory85
						Cockpit03,658,395,Cockpit,Cockpit02,Territory85
					 */

					l_splitArray = l_line.split(",");

					// no such continent
					if (!l_continentMap.containsKey(l_splitArray[3])) {
						d_logEntryBuffer.logAction("ERROR", "No such continent: " + l_splitArray[3]);
						return false;
					}

					l_country = new Country(l_id, l_splitArray[0], Integer.parseInt(l_splitArray[1]),
							Integer.parseInt(l_splitArray[2]), d_gameContext.getContinents().get(l_continentMap.get(l_splitArray[3])));

					d_gameContext.getCountries().put(l_id, l_country);
					l_countryMap.put(l_country.getCountryName(), l_id);
					d_gameContext.getContinents().get(l_continentMap.get(l_splitArray[3])).getCountries().put(l_id, l_country);
					l_borderList.add(l_splitArray);
					l_id++;
				}
			}

			// put borders into the map
			int l_key = 1;
			for (String[] l_borders: l_borderList) {
				l_country = d_gameContext.getCountries().get(l_key);
				for (int i = 4; i < l_borders.length; i++) {
					l_country.getNeighbors().put(l_countryMap.get(l_borders[i]), d_gameContext.getCountries().get(l_countryMap.get(l_borders[i])));
				}
				l_key++;
			}

			//close reading the file
			l_scanner.close();

			d_logEntryBuffer.logAction("SUCCESS", "Map succesfully loaded: " + p_fileName);

		} catch (Exception e) {
			d_logEntryBuffer.logAction("ERROR", "An error occured reading the map file: " + p_fileName);
			return false;
		}
		return true;
	}
}
