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
 * adapter of ConquestMapHandler
 * @author nidhi
 *
 */
public class ConquestMapHandlerAdapter extends DominateMapHandler implements Serializable {

	/**
	 * Map Service
	 */
	private MapService d_mapService;
	/**
	 * Conquest MapHandler
	 */
	private ConquestMapHandler d_conquestMapHandler;
	

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
	 * @param p_conquestMapHandler given  conquestMapHandler
	 */
	public ConquestMapHandlerAdapter(GameContext p_gameContext, ConquestMapHandler p_conquestMapHandler) {
		super(p_gameContext);
		d_mapService = new MapService(p_gameContext);
		d_conquestMapHandler = p_conquestMapHandler;
	}
	
	/**
	 * Load a map with format 'conquest'
	 * or create a new map from scratch if the file does not exist.
	 * @param p_fileName file name
	 * @return true if success
	 */
	public boolean editMap(String p_fileName) {
		return d_conquestMapHandler.editMap(p_fileName);		
	}

	/**
	 * Game starts by user selection of a user-saved map file, which loads the map as a connected directed graph.
	 * This method will load map using 'conquest' game format
	 * 
	 * @param p_fileName file name of map
	 * @return if map successfully loaded
	 */
	public boolean loadMap(String p_fileName) {
		return d_conquestMapHandler.loadMap(p_fileName);
	}
	
	/**
	 * Save a map to a text file exactly as edited (using the "conquest" game map format).
	 * @param p_fileName the filename
	 * @return true if successfully save the map, otherwise return false
	 * @throws IOException the exception of saving files
	 */
	public boolean saveMap(String p_fileName) throws IOException {
		return d_conquestMapHandler.saveMap(p_fileName);
	}
	
}
