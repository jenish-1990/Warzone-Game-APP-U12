package warzone.model;

import java.util.ArrayList;
import java.util.List;

public class TournamentContext {

	private static TournamentContext TOURNAMENT_CONTEXT;
	
	private List<String> d_mapFiles;
	private List<PlayerStrategyType> d_playerStrategies;
	private int d_numberOfGames;
	private int d_maxTurns;
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
	
	public List<String> getMapFiles() {
		
		return d_mapFiles;
	}

	public void setMapFiles(List<String> p_mapFiles) {
		
		d_mapFiles = p_mapFiles;
	}

	
	public List<PlayerStrategyType> getPlayerStrategies() {
		
		return d_playerStrategies;
	}
	
	public void setPlayerStrategies(List<PlayerStrategyType> p_playerStrategies) {
		
		d_playerStrategies = p_playerStrategies;
	}

	public int getNumberOfGames() {
		
		return d_numberOfGames;
	}
	
	public void setNumberOfGames(int p_numberOfGames) {
		
		this.d_numberOfGames = p_numberOfGames;
	}
	
	public int getMaxTurns() {
		
		return d_maxTurns;
	}
	
	public void setMaxTurns(int p_maxTurns) {
		
		this.d_maxTurns = p_maxTurns;
	}

	public String[][] getResults() {
		
		return d_results;
	}
	
	public void prepareResultsTable() {
		
		d_results = new String[d_mapFiles.size()][d_numberOfGames];
	}
}