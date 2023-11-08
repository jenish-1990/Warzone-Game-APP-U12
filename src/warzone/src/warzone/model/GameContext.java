package warzone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import warzone.service.LogService;
import warzone.service.MapService;

/**
 * This class represent the state of the game, and it contains some useful instances for
 * other classes.
 *
 */
public class GameContext {
	private static GameContext GAME_CONTEXT;
	//GamePhase d_gamePhase = GamePhase.MAPEDITOR;
	private int d_orderNumberPerRound = 5;
	

	private Map<String, Player> d_players;
	private Map<Integer, Country> d_countries;
	private Map<Integer, Continent> d_continents;

	
	private String d_mapFileName;
	private String d_mapFilePic;
	private String d_mapFileMap;
	private String d_mapFileCards;
	
	private WarzoneProperties d_warzoneProperties;
	
	private static LogEntryBuffer LOG_ENTRY_BUFFER;
	
	/**
	 * get map file cards
	 * @return the map file cards
	 */
	public String getMapFileCards() {
		return d_mapFileCards;
	}

	/**
	 * set map file cards
	 * @param mapFileCards the map file cards
	 */
	public void setMapFileCards(String mapFileCards) {
		this.d_mapFileCards = mapFileCards;
	}

	/**
	 * This constructor will initiate the players, countries and continents.
	 */
	private GameContext() {
		
		d_players = new HashMap<String, Player>() ;
		d_countries = new HashMap<Integer, Country>();
		d_continents = new HashMap<Integer, Continent>();
		d_warzoneProperties = WarzoneProperties.getWarzoneProperties();
	}		
	
	/**
	 * This method can return the game context instance and create a new one if
	 * it is null.
	 * @return the game context instance
	 */
	public static GameContext getGameContext() {
		if(GAME_CONTEXT == null) {
			GAME_CONTEXT = new GameContext();
		}
		return GAME_CONTEXT;
	}
	
	/**
	 * This method can return the logEntryBuffer instance and create a new one if
	 * it is null.
	 * @return the logEntryBuffer instance
	 */
	public static LogEntryBuffer getLogEntryBuffer() {
		if(LOG_ENTRY_BUFFER == null) {
			LOG_ENTRY_BUFFER= new LogEntryBuffer();
			LOG_ENTRY_BUFFER.attach(new LogService());
		}
		return LOG_ENTRY_BUFFER;
	}
	
	/**
	 * clear the game context
	 */
	public static void clear(){
		getGameContext().reset();
	}
	
	/**
	 * clear the game context
	 */
	public void reset(){
		d_players = new HashMap<String, Player>() ;
		d_countries = new HashMap<Integer, Country>();
		d_continents = new HashMap<Integer, Continent>();
		d_warzoneProperties = WarzoneProperties.getWarzoneProperties();
		d_mapFileName = "";
		d_mapFilePic = "";
		d_mapFileMap = "";
		d_mapFileCards = "";
	}
	
	/**
	 * This method will offer all players in Map structure.
	 * @return a Map object containing all players
	 */
	public Map<String, Player> getPlayers() {
		return d_players;
	}
	
	/**
	 * This method will offer all countries in Map structure.
	 * @return a Map object containing all countries
	 */
	public Map<Integer, Country> getCountries() {
		return d_countries;
	}
	
	/**
	 * This method will offer all continents in Map structure.
	 * @return a Map object containing all continents
	 */
	public Map<Integer, Continent> getContinents() {
		return d_continents;
	}

	/**
	 * This method will provide the name of the current map.
	 * @return the name of the current map.
	 */
	public String getMapFileName() {
		return d_mapFileName;
	}

	/**
	 * This method can set the name of the current map.
	 * @param p_mapFileName the name of the map.
	 */
	public void setMapFileName(String p_mapFileName) {
		this.d_mapFileName = p_mapFileName;
	}

	/**
	 * This method will provide map file picture as a String
	 * @return the map file picture
	 */
	public String getMapFilePic() {
		return d_mapFilePic;
	}

	/**
	 * This method can set map file picture
	 * @param p_mapFilePic the map file picture
	 */
	public void setMapFilePic(String p_mapFilePic) {
		this.d_mapFilePic = p_mapFilePic;
	}

	/**
	 * This method will provide map file map as a String
	 * @return the map file map
	 */
	public String getMapFileMap() {
		return d_mapFileMap;
	}

	/**
	 * This method can set map file map
	 * @param p_mapFileMap the map file map
	 */
	public void setMapFileMap(String p_mapFileMap) {
		this.d_mapFileMap = p_mapFileMap;
	}
	
	
	/**
	 * This method will show current mode whether is demo mode.
	 * @return true if the current mode is demo
	 */
	public boolean getIsDemoMode() {
		return d_warzoneProperties.getIsDemoMode();
	}

	
	/**
	 * This method will show current mode whether is debug mode.
	 * @return true if the current mode is debug mode
	 */
	public boolean getIsDebug() {
		return d_warzoneProperties.getIsDebug();
	}
	
	/**
	 * This method will show whether needs logs
	 * @return true if the game needs logs
	 */
	public boolean getIsLog() {
		return d_warzoneProperties.getIsLog();
	}
	
	/**
	 * This method will return Map folder.
	 * @return  Map folder path
	 */
	public String getMapfolder() {
		return d_warzoneProperties.getGameMapDirectory();
	}	
	
	/**
	 * This method will return Log folder.
	 * @return  Map folder path
	 */
	public String getLogfolder() {
		return d_warzoneProperties.getLogDirectory();
	}	
	
	/**
	 * This method can provide the number of order in every round of the game.
	 * @return the number of orders in each round
	 */
	public int getOrderNumberPerRound() {
		return d_orderNumberPerRound;
	}	
//	/**
//	 * check if current game phase is in the given phase list
//	 * @param p_gamePhases given phase list
//	 * @return true if include, otherwise false
//	 */
//	public boolean getIsContainCurrentPhase(List<GamePhase> p_gamePhases) {
//		if(p_gamePhases != null) {
//			return p_gamePhases.contains(this.d_gamePhase);
//		}
//		return false;
//	}
//	
	
	
}
