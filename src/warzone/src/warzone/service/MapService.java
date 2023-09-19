package warzone.service;

import warzone.model.*;
import warzone.view.GenericView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MapService {

	private GameContext d_gameContext;

	public MapService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	
	public boolean validateMap() {
		return true;
	}
	
	public boolean saveMap(String p_fileName) throws IOException {
		try{
			String l_fullFileName = p_fileName + ".map";
			
			//build the content using StringBuilder
			StringBuilder l_map = new StringBuilder();
			l_map.append("; map: " + p_fileName);
			l_map.append("\n; map made with the 6441 Super Team");
			l_map.append("\n; 6441.net  1.0.0.1 ");
			l_map.append("\n");
			
			l_map.append("\n[files]");
			l_map.append("\npic "+ p_fileName +"_pic.jpg");
			l_map.append("\nmap "+ p_fileName +"_map.gif");
			l_map.append("\ncrd "+ p_fileName + "europe.cards");
			l_map.append("\n");
			
			l_map.append("\n[continents]");
			for (Continent l_continent : d_gameContext.getContinents().values()) {
				l_map.append("\n" + l_continent.getContinentName()  +  " " + l_continent.getBonusReinforcements() +  " " + l_continent.getColor());
			}
			l_map.append("\n");
			
			l_map.append("\n[countries]");
			for (Continent l_continent : d_gameContext.getContinents().values()) {
				for (Country l_country : l_continent.getCountries().values()) {
					l_map.append("\n" + l_country.getCountryID()  +  " " + l_country.getCountryName()  +  " " + l_continent.getContinentID() +  " " + l_country.getXPosition()+  " " + l_country.getYPosition() );
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
//			Path l_fileFullPath = Path.of(l_fullFileName);	        
//	        Files.write(l_fileFullPath, l_map.toString().getBytes());
//	        
	        
	        BufferedWriter writer = new BufferedWriter(new FileWriter(l_fullFileName));
	        writer.write(l_map.toString());
	        
	        writer.close();
	        return true;
		}
		catch(Exception ex) {
			throw ex;
		}		
	}
	
	public boolean editMap (String p_fileName) {
		
		String mapDirectory = "./doc/map/europe/"; //LOAD FROM PROPERTIES FILE
		
		//Clear gameContext
		d_gameContext.getContinents().clear();
		d_gameContext.getCountries().clear();
		
		try {
			
			File mapFile = new File(mapDirectory + p_fileName);
			
			d_gameContext.setMapFileName(p_fileName);

			//Specified file name does not exist (new map)
			if(!mapFile.exists() || mapFile.isDirectory()) { 

				GenericView.println("Creating a new map: " + p_fileName);
				return true;
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
					
					//TODO ADD CONTINENT
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
			
			GenericView.println("Map succesfully loaded: " + p_fileName);
		    
		} catch (Exception e) {
		      
			GenericView.println("An error occured reading the map file: " + p_fileName);
		}
		
		return true;
	}
}
