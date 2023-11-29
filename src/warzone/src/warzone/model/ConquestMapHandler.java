package warzone.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import warzone.service.MapService;

import java.util.Map.Entry;

/**
 * class of Conquest Map Handler
 * @author fzuray
 *
 */
public class ConquestMapHandler implements Serializable {

	
	/**
	 * game context
	 */
	private GameContext d_gameContext;


	/**
	 * log entry buffer
	 */
	private LogEntryBuffer d_logEntryBuffer;
	/**
	 * Map Service
	 */
	private MapService d_mapService;

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the scanner
	 */
	private transient Scanner l_scanner;

	/**
	 * The constructor of this class
	 * @param p_gameContext the current game context
	 */
	public ConquestMapHandler(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_logEntryBuffer = d_gameContext.getLogEntryBuffer();
		d_mapService = new MapService(p_gameContext);
	}
	
	/**
	 * Load a map with format 'conquest'
	 * or create a new map from scratch if the file does not exist.
	 * @param p_fileName file name
	 * @return true if success
	 */
	public boolean editMap(String p_fileName) {

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
		
		return loadMap(p_fileName);
	}

	/**
	 * Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph.
	 * This method will load map using 'conquest' game format
	 * 
	 * @param p_fileName file name of map
	 * @return if map successfully loaded
	 */
	public boolean loadMap(String p_fileName) {

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
	
	/**
	 * Save a map to a text file exactly as edited (using the "conquest" game map format).
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
			if(saveToMap(p_fileName)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception ex) {
			d_logEntryBuffer.logAction("ERROR", "Exception occured when saving the map. " + ex.toString());
			return false;
		}
	}
	
	/**
	 * This method will save the map
	 * @param p_fullFileName the name of the file that should be saved
	 * @return true if success
	 * @throws Exception the exceptions
	 */
	private boolean saveToMap(String p_fullFileName) throws Exception {
		try{
			String l_fileName ;
			if(p_fullFileName.indexOf(".") > -1)
				l_fileName = p_fullFileName.substring(0,p_fullFileName.indexOf("."));
			else
				l_fileName = p_fullFileName;
			String l_path = this.d_gameContext.getMapfolder();

			//build the content using StringBuilder
			StringBuilder l_map = new StringBuilder();
			// map part
			l_map.append("[Map]");
			l_map.append("\nauthor=the 6441 Super Team");
			l_map.append("\nimage=" + l_fileName + ".bmp");
			l_map.append("\nwrap=no");
			l_map.append("\nscroll=no");
			l_map.append("\nwarn=no");

			// continents part
			l_map.append("\n");
			l_map.append("\n[Continents]");
			for (Entry<Integer, Continent> l_entry: d_gameContext.getContinents().entrySet()) {
				l_map.append("\n" + l_entry.getValue().getContinentName() + "=" + l_entry.getValue().getBonusReinforcements());
			}

			// territories part
			l_map.append("\n");
			l_map.append("\n[Territories]");
			for (Entry<Integer, Country> l_entry : d_gameContext.getCountries().entrySet()) {
				Country l_country = l_entry.getValue();
				l_map.append("\n" + l_country.getCountryName() + "," + l_country.getXPosition() + "," + l_country.getYPosition()
				+ "," + l_country.getContinent().getContinentName());
				for (Entry<Integer, Country> l_neighbors : l_country.getNeighbors().entrySet()) {
					l_map.append("," + l_neighbors.getValue().getCountryName());
				}
			}				
			l_map.append("\n");

			//write the content into the map
	        BufferedWriter writer = new BufferedWriter(new FileWriter(l_path + p_fullFileName));
	        writer.write(l_map.toString());

	        writer.close();
	        return true;
		}
		catch(Exception ex) {
			throw ex;
		}		
	}
}
