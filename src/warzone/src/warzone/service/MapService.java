package warzone.service;

import warzone.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
				l_map.append("\n" + l_continent.getContinentName()  +  " " + l_continent.getWeight() +  " " + l_continent.getColor());
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
}
