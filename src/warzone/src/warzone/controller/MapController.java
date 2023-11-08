package warzone.controller;

import warzone.view.*;

import java.io.IOException;

import warzone.model.*;
import warzone.service.*;

public class MapController {

	private MapService d_mapService;
	private GameContext d_gameContext;

	public MapController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_mapService = new MapService(p_gameContext);
	}
	
	/**
	 * Performs the action for the user command: showmap
	 * 
	 * Displays the map as text, showing all continents and countries and their respective neighbors.
	 */
	public GameContext showMap () {
		
		// TODO Auto-generated method stub
		
		return null;
	}
	
	/**
	 * Performs the action for the user command: savemap filename
	 * 
	 * Save a map to a text file exactly as edited (using the "domination" game map format).
	 * @throws IOException 
	 */
	public boolean saveMap (String p_fileName) throws IOException {
		
		// validate if the filename is legal
		if(p_fileName == null || p_fileName.trim().isEmpty() || p_fileName.trim().length() > 20 ) {
			GenericView.printError("InValid File Name, please type a valid file name, with length less than 20.");
			return false;
		}
		
		// call mapService to save the map and return the path
		p_fileName = p_fileName.trim();
		try{
			if(d_mapService.saveMap(p_fileName)) {
				GenericView.printSuccess("Map was saved in :" + p_fileName + ".map" );
				return true;
			}
			else {
				GenericView.printError("Exception occured when saving the map, please valid the file name or contact the Administrator.");
				return false;	
			}		
		}
		catch(Exception ex) {
			GenericView.printError("Exception occured when saving the map. " + ex.toString());
			return false;
		}
	}
	
	/**
	 * Performs the action for the user command: editmap filename
	 * 
	 * Load a map from an existing "domination" map file, or create a new map from scratch if the file does not exist
	 */
	public boolean editMap (String fileName) {
		
		// TODO Auto-generated method stub
		
		return false;
	}
	
	/**
	 * Performs the action for the user command: validatemap
	 * 
	 * Verification of map correctness. The map should be automatically validated upon loading 
	 * and before saving (at least 3 types of incorrect maps). The validatemap command can be 
	 * triggered any time during map editing. 
	 */
	public boolean validateMap () {
		
		// TODO Auto-generated method stub
		
		return false;
	}

}
