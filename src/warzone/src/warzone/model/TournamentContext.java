package warzone.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the context for tournament
 *
 */
public class TournamentContext implements Serializable {

	/**
	 * the tournament context
	 */
	private static TournamentContext TOURNAMENT_CONTEXT;
	
	/**
	 * the map files
	 */
	private List<String> d_mapFiles;
	/**
	 * the list of player strategies
	 */
	private List<PlayerStrategyType> d_playerStrategies;
	/**
	 * the number of games
	 */
	private int d_numberOfGames;
	/**
	 * the maximum turns
	 */
	private int d_maxTurns;
	/**
	 * the results
	 */
	private String[][] d_results;
	
	/**
	 * This constructor will initiate the players, countries and continents.
	 */
	private TournamentContext() {
		
		d_mapFiles = new ArrayList<String>();
		d_playerStrategies = new ArrayList<PlayerStrategyType>();
	}		
	
	/**
	 * This method can return the tournament context instance and create a new one if
	 * it is null.
	 * @return the tournament context instance
	 */
	public static TournamentContext getTournamentContext() {
		if(TOURNAMENT_CONTEXT == null) {
			TOURNAMENT_CONTEXT = new TournamentContext();
		}
		return TOURNAMENT_CONTEXT;
	}
	
	/**
	 * This method will get map files
	 * @return map files
	 */
	public List<String> getMapFiles() {
		
		return d_mapFiles;
	}

	/**
	 * set map files
	 * @param p_mapFiles the map files
	 */
	public void setMapFiles(List<String> p_mapFiles) {
		
		d_mapFiles = p_mapFiles;
	}

	/**
	 * player strategies getter
	 * @return player strategies
	 */
	public List<PlayerStrategyType> getPlayerStrategies() {
		
		return d_playerStrategies;
	}
	
	/**
	 * player strategies setter
	 * @param p_playerStrategies player strategies
	 */
	public void setPlayerStrategies(List<PlayerStrategyType> p_playerStrategies) {
		
		d_playerStrategies = p_playerStrategies;
	}

	/**
	 * game number getter
	 * @return the number of games
	 */
	public int getNumberOfGames() {
		
		return d_numberOfGames;
	}
	
	/**
	 * game number setter
	 * @param p_numberOfGames the number of games
	 */
	public void setNumberOfGames(int p_numberOfGames) {
		
		this.d_numberOfGames = p_numberOfGames;
	}
	
	/**
	 * maximum turns getter
	 * @return maximum turns
	 */
	public int getMaxTurns() {
		
		return d_maxTurns;
	}
	
	/**
	 * maximum turns setter
	 * @param p_maxTurns maximum turns
	 */
	public void setMaxTurns(int p_maxTurns) {
		
		this.d_maxTurns = p_maxTurns;
	}

	/**
	 * results getter
	 * @return results
	 */
	public String[][] getResults() {
		
		return d_results;
	}
	
	/**
	 * This method will prepare for the results table
	 */
	public void prepareResultsTable() {
		
		d_results = new String[d_mapFiles.size()][d_numberOfGames];
	}
}