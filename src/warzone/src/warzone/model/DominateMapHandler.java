package warzone.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import warzone.service.GameEngine;
import warzone.service.MapService;

/**
 * Dominate Map Handler
 * @author fzuray
 *
 */
public class DominateMapHandler implements Serializable {

	/**
	 * game context
	 */
	protected GameContext d_gameContext;


	/**
	 * log entry buffer
	 */
	protected LogEntryBuffer d_logEntryBuffer;
	
	/**
	 * The constructor of this class
	 * @param p_gameContext the current game context
	 */
	public DominateMapHandler(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_logEntryBuffer = d_gameContext.getLogEntryBuffer();
	}
	
	/**
	 * Performs the action for user command: loadmap filename
	 * 
	 * Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph
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
			
			Scanner l_scanner = new Scanner(l_mapFile);
			String l_line;
			String[] l_splitArray;
			int l_continentCtr = 1;
			int l_id;
			Country l_country;

			//use boolean to record the different parts in file
			boolean l_processingFiles = false;
			boolean l_processingContinents = false;
			boolean l_processingCountries = false;
			boolean l_processingBorders = false;
			
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();

				// determine which part it is
				// file part
				if(l_line.equals("[files]")) {

					l_processingFiles = true;
					l_processingContinents = false;
					l_processingCountries = false;
					l_processingBorders = false;
					
					l_line = l_scanner.nextLine();
				}
				// continents part
				else if(l_line.equals("[continents]")) {
					
					l_processingFiles = false;
					l_processingContinents = true;
					l_processingCountries = false;
					l_processingBorders = false;
					
					l_line = l_scanner.nextLine();
				}
				//countries part
				else if (l_line.equals("[countries]")) {
					
					l_processingFiles = false;
					l_processingContinents = false;
					l_processingCountries = true;
					l_processingBorders = false;
					
					l_line = l_scanner.nextLine();
				}
				//borders part
				else if (l_line.equals("[borders]")) {
					
					l_processingFiles = false;
					l_processingContinents = false;
					l_processingCountries = false;
					l_processingBorders = true;

					if(!l_scanner.hasNextLine())
						l_processingBorders = false;
					else{
						l_line = l_scanner.nextLine();
					}
				}

				// read file part
				if(l_processingFiles) {
					
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
				else if(l_processingContinents && !l_line.trim().isEmpty()) {
					
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
				else if(l_processingCountries && !l_line.trim().isEmpty()) {
					
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
				else if(l_processingBorders && !l_line.trim().isEmpty()) {
					
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
			if(!(new MapService(d_gameContext).validateMap())) {
				d_logEntryBuffer.logAction("ERROR", "The map file selected failed validation: " + p_fileName);
				return false;
			}

			d_logEntryBuffer.logAction("SUCCESS", "Map succesfully loaded: " + p_fileName);
		    
		} catch (Exception e) {
			d_logEntryBuffer.logAction("ERROR", "An error occured reading the map file: " + p_fileName);
			return false;
		}
		
		return true;
	}
	
	/**
	 * save map to file
	 * @param p_fullFileName file name
	 * @return if success
	 * @throws IOException if any io exception
	 */
	public boolean saveMap(String p_fullFileName) throws IOException {
		try{
			String l_fileName ;
			if(p_fullFileName.indexOf(".") > -1)
				l_fileName = p_fullFileName.substring(0,p_fullFileName.indexOf("."));
			else
				l_fileName = p_fullFileName;
			String l_path = this.d_gameContext.getMapfolder();
			
			//build the content using StringBuilder
			StringBuilder l_map = new StringBuilder();
			l_map.append("; map: " + l_fileName);
			l_map.append("\n; map made with the 6441 Super Team");
			l_map.append("\n; 6441.net  1.0.0.1 ");
			l_map.append("\n");
			
			l_map.append("\n[files]");
			l_map.append("\npic "+ l_fileName +"_pic.jpg");
			l_map.append("\nmap "+ l_fileName +"_map.gif");
			l_map.append("\ncrd "+ l_fileName + "europe.cards");
			l_map.append("\n");
			Map<Integer,Integer> l_continentIdMapping = new HashMap(); 
			l_map.append("\n[continents]");
			int l_continentId = 1;
			for (Continent l_continent : d_gameContext.getContinents().values()) {
				l_continentIdMapping.put(l_continent.getContinentID(), l_continentId);
				l_map.append("\n" + l_continent.getContinentName()  +  " " + l_continent.getBonusReinforcements() +  " " + l_continent.getColor());
				l_continentId ++ ;
			}
			l_map.append("\n");
			
			l_map.append("\n[countries]");
			for (Continent l_continent : d_gameContext.getContinents().values()) {
				for (Country l_country : l_continent.getCountries().values()) {
					l_map.append("\n" + l_country.getCountryID()  +  " " + l_country.getCountryName()  +  " " + l_continentIdMapping.get(l_continent.getContinentID()) +  " " + l_country.getXPosition()+  " " + l_country.getYPosition() );
				}				
			}			
			l_map.append("\n");
			
			l_map.append("\n[borders]");
			for (Country l_country : d_gameContext.getCountries().values()) {
				l_map.append("\n" + l_country.getCountryID());
				for (Country l_neighborCountry : l_country.getNeighbors().values()) {
					l_map.append(" " + l_neighborCountry.getCountryID()  );
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
	
	/**
	 * Load a map from an existing map
	 * or create a new map from scratch if the file does not exist.
	 * @param p_fileName file name
	 * @return if success
	 */
	public boolean editMap (String p_fileName) {

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
			
			d_logEntryBuffer.logAction("SUCCESS", "Map succesfully loaded: " + p_fileName);
		    
		} catch (Exception e) {
			d_logEntryBuffer.logAction("ERROR", "An error occured reading the map file: " + p_fileName);
			return false;
		}
		
		return true;
	}
}
