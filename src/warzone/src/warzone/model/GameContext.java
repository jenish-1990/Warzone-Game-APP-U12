package warzone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represent the state of the game, and it contains some useful instances for
 * other classes.
 *
 */
public class GameContext {
	private static GameContext GAME_CONTEXT;
	GamePhase d_gamePhase = GamePhase.MAPEDITOR;
	private int d_orderNumberPerRound = 5;
	
	private boolean d_isDemoMode = true;
	private boolean d_isDebug = false;

	private Map<String, Player> d_players;
	private Map<Integer, Country> d_countries;
	private Map<Integer, Continent> d_continents;

	
	private String d_mapFileName;
	private String d_mapFilePic;
	private String d_mapFileMap;
	private String d_mapFileCards;
	
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
	 * clear the game context
	 */
	public static void clear(){
		GAME_CONTEXT = new GameContext();		
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
	 * This method will provide current game phase
	 * @return the current game phase
	 */
	public GamePhase getGamePhase() {
		return d_gamePhase;
	}

	/**
	 * This method can set current game phase
	 * @param p_gamePhase the current game phase
	 */
	public void setGamePhase(GamePhase p_gamePhase) {
		this.d_gamePhase = p_gamePhase;
	}	
	
	/**
	 * This method will show current mode whether is demo mode.
	 * @return true if the current mode is demo
	 */
	public boolean getIsDemoMode() {
		return d_isDemoMode;
	}

	/**
	 * This method can set current mode to demo mode
	 * @param p_isDemoMode true if we want to set current mode as demo
	 */
	public void setIsDemoMode(boolean p_isDemoMode) {
		this.d_isDemoMode = p_isDemoMode;
	}		
	
	/**
	 * This method will show current mode whether is debug mode.
	 * @return true if the current mode is debug mode
	 */
	public boolean getIsDebug() {
		return d_isDebug;
	}

	/**
	 * This method can set current mode to debug mode
	 * @param p_isDebug true if we want to set current mode as debug mode
	 */
	public void setIsDebug(boolean p_isDebug) {
		this.d_isDebug = p_isDebug;
	}			
	
	/**
	 * This method can provide the number of order in every round of the game.
	 * @return the number of orders in each round
	 */
	public int getOrderNumberPerRound() {
		return d_orderNumberPerRound;
	}	
	/**
	 * check if current game phase is in the given phase list
	 * @param p_gamePhases given phase list
	 * @return true if include, otherwise false
	 */
	public boolean getIsContainCurrentPhase(List<GamePhase> p_gamePhases) {
		if(p_gamePhases != null) {
			return p_gamePhases.contains(this.d_gamePhase);
		}
		return false;
	}
	
	
	
}
