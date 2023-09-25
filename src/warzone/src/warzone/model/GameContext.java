package warzone.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameContext {
	private static GameContext GAME_CONTEXT;
	GamePhase d_gamePhase = GamePhase.MAPEDITING;
	private int d_orderNumberPerRound = 5;
	
	private boolean d_isDemoMode = true;

	private Map<String, Player> d_players;
	private Map<Integer, Country> d_countries;
	private Map<Integer, Continent> d_continents;
	
	private String d_mapFileName;
	private String d_mapFilePic;
	private String d_mapFileMap;
	private String d_mapFileCards;
		
	public String getMapFileCards() {
		return d_mapFileCards;
	}

	public void setMapFileCards(String mapFileCards) {
		this.d_mapFileCards = mapFileCards;
	}

	private GameContext() {
		
		d_players = new HashMap<String, Player>() ;
		d_countries = new HashMap<Integer, Country>();
		d_continents = new HashMap<Integer, Continent>();
	}		
	
	public static GameContext getGameContext() {
		if(GAME_CONTEXT == null) {
			GAME_CONTEXT = new GameContext();
		}
		return GAME_CONTEXT;
	}
	
	/**
	 * clear the game context 
	 * @return a new game context
	 */
	public static GameContext clear(){
		return GAME_CONTEXT = new GameContext();
	}
	
	public Map<String, Player> getPlayers() {
		if(d_players == null) {
			d_players = new HashMap<String, Player>();
		}
		return d_players;
	}
	
	public Map<Integer, Country> getCountries() {
		if(d_countries == null) {
			d_countries = new HashMap<Integer, Country>();
		}
		return d_countries;
	}
	
	public Map<Integer, Continent> getContinents() {
		if(d_continents == null) {
			d_continents = new HashMap<Integer, Continent>();
		}
		return d_continents;
	}

	public String getMapFileName() {
		return d_mapFileName;
	}

	public void setMapFileName(String p_mapFileName) {
		this.d_mapFileName = p_mapFileName;
	}

	public String getMapFilePic() {
		return d_mapFilePic;
	}

	public void setMapFilePic(String p_mapFilePic) {
		this.d_mapFilePic = p_mapFilePic;
	}

	public String getMapFileMap() {
		return d_mapFileMap;
	}

	public void setMapFileMap(String p_mapFileMap) {
		this.d_mapFileMap = p_mapFileMap;
	}
	
	public GamePhase getGamePhase() {
		return d_gamePhase;
	}

	public void setGamePhase(GamePhase p_gamePhase) {
		this.d_gamePhase = p_gamePhase;
	}	
	
	public boolean getIsDemoMode() {
		return d_isDemoMode;
	}

	public void setIsDemoMode(boolean p_isDemoMode) {
		this.d_isDemoMode = p_isDemoMode;
	}		
	
	public int getOrderNumberPerRound() {
		return d_orderNumberPerRound;
	}	
	
	
	
}
