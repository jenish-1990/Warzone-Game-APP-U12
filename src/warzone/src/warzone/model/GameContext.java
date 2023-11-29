package warzone.model;

import warzone.service.LogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent the state of the game, and it contains some useful instances for
 * other classes.
 *
 */
public class GameContext {
	/**
	 * game context
	 */
	private static GameContext GAME_CONTEXT;
	/**
	 * order Number Per Round
	 */
	private int d_orderNumberPerRound = 5;

	/**
	 * players in game context
	 */
	private Map<String, Player> d_players;

	/**
	 * countries in game context
	 */
	private Map<Integer, Country> d_countries;

	/**
	 * continents in game context
	 */
	private Map<Integer, Continent> d_continents;

	/**
	 * log service
	 */
	private LogService d_logService;
	/**
	 * current router
	 */
	private Router d_currentRouter;
	/**
	 * map file name
	 */
	private String d_mapFileName;
	/**
	 * picture of map file
	 */
	private String d_mapFilePic;
	/**
	 * map of mapfile
	 */
	private String d_mapFileMap;
	/**
	 * cards of mapfile
	 */
	private String d_mapFileCards;
	/**
	 * negotiate Orders In Current Turn
	 */
	private List<NegotiateOrder> d_negotiateOrdersInCurrentTurn;
	/**
	 *  is Tournament Mode
	 */
	private boolean d_isTournamentMode=false;
	

	/**
	 * properties of the game
	 */
	private WarzoneProperties d_warzoneProperties;

	/**
	 * check if there is a Diplomacy existed between 2 given players
	 * @param p_playerA first given player 
	 * @param p_playerB second  given player
	 * @return True if there is a Diplomacy existed, otherwise false
	 */
	public boolean isDiplomacyInCurrentTurn(Player p_playerA,Player p_playerB) {
		if(p_playerA == null || p_playerB == null)
			return false;
		for(NegotiateOrder l_orderTemp : d_negotiateOrdersInCurrentTurn) {
			if((l_orderTemp.getPlayer() == p_playerA &&  l_orderTemp.getTargetPlayer() == p_playerB  )
					|| (l_orderTemp.getPlayer() == p_playerB &&  l_orderTemp.getTargetPlayer() == p_playerA  ) )
				return true;
		}

		return false;
	}
	
	/**
	 * rest Diplomacy Order List when a new turn is started
	 */
	public void resetDiplomacyOrderList() {
		d_negotiateOrdersInCurrentTurn = new ArrayList<NegotiateOrder>();
	}
	
	/**
	 *  add Diplomacy Order To List for current turn
	 * @param p_diplomacyOrder given Diplomacy Order
	 */
	public void addDiplomacyOrderToList(NegotiateOrder p_diplomacyOrder) {
		d_negotiateOrdersInCurrentTurn.add(p_diplomacyOrder);
	}
	
	
	/**
	 * get current running router
	 * @return current running router
	 */
	public Router getCurrentRouter() {
		return d_currentRouter;
	}
	
	/**
	 * set current running router
	 * @param p_currentRouter current running router
	 */
	public void setCurrentRouter(Router p_currentRouter) {
		d_currentRouter =  p_currentRouter;
	}
	
	/**
	 * get  Is Tournament Mode
	 * @return  if Is Tournament Mode
	 */
	public boolean getIsTournamentMode() {
		return d_isTournamentMode;
	}
	
	/**
	 * set  Is Tournament Mode
	 * @param p_isTournamentMode 
	 */
	public void setIsTournamentMode(boolean p_isTournamentMode) {
		d_isTournamentMode =  p_isTournamentMode;
	}	

	/**
	 * singlton of LogEntryBuffer
	 */
	private LogEntryBuffer d_logEntryBuffer;
	
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
		d_logService = new LogService();
		d_negotiateOrdersInCurrentTurn = new ArrayList<NegotiateOrder>();
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
	public LogEntryBuffer getLogEntryBuffer() {
		if(d_logEntryBuffer == null) {
			d_logEntryBuffer= new LogEntryBuffer(this);
			d_logEntryBuffer.attach(d_logService);
		}
		return d_logEntryBuffer;
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
		d_negotiateOrdersInCurrentTurn = new ArrayList<NegotiateOrder>();
		d_logService = new LogService();
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
	 * This method will return simple commands.
	 * @return  simple command
	 */
	public String getSimpleCommand() {
		return d_warzoneProperties.getSimpleCommand();
	}
	
	/**
	 * This method will return complex commands.
	 * @return  complex command
	 */
	public String getComplexCommand() {
		return d_warzoneProperties.getComplexCommand();
	}
	
	/**
	 * This method can provide the number of order in every round of the game.
	 * @return the number of orders in each round
	 */
	public int getOrderNumberPerRound() {
		return d_orderNumberPerRound;
	}
}
