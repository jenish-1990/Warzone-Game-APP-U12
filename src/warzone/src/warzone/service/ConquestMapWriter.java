package warzone.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;

import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.LogEntryBuffer;

/**
 * Conquest Map Writer
 */
public class ConquestMapWriter {
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
	 * The constructor of this class
	 * @param p_gameContext the current game context
	 */
	public ConquestMapWriter(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_logEntryBuffer = new LogEntryBuffer(p_gameContext);
		d_mapService = new MapService(p_gameContext);
	}
	
	/**
	 * Save a map to a text file exactly as edited (using the "conquest" game map format).
	 * @param p_fileName the filename
	 * @return true if successfully save the map, otherwise return false
	 * @throws IOException the exception of saving files
	 */
	public boolean saveConquestMap(String p_fileName) throws IOException {
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
			if(saveMap(p_fileName)) {
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
	private boolean saveMap(String p_fullFileName) throws Exception {
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
