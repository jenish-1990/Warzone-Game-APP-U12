package warzone.model;

import java.util.HashMap;
import java.util.Map;

public class GameContext {

	private Map<Integer, Player> d_players;
	private Map<Integer, Country> d_countries;
	private Map<Integer, Continent> d_continents;
	private static GameContext GAME_CONTEXT;
	
	private GameContext() {
		
		d_players = new HashMap<Integer, Player>() ;
		d_countries = new HashMap<Integer, Country>();
		d_continents = new HashMap<Integer, Continent>();
	}		
	
	public static GameContext getGameContext() {
		if(GAME_CONTEXT == null) {
			GAME_CONTEXT = new GameContext();
		}
		return GAME_CONTEXT;
	}
	
	public Map<Integer, Player> getPlayers() {
		if(d_players == null) {
			d_players = new HashMap<Integer, Player>();
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
}
