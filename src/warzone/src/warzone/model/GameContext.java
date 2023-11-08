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
		
		d_continents.put(1,new Continent(1,"North_America", "Red", 3 ));
		d_continents.put(2,new Continent(2,"South_America", "Blue", 5 ));
		d_continents.put(3,new Continent(2,"Asia", "Pink", 5 ));
//		
//		Country canada = new Country(1,"Canada", 343,435 );
//		d_countries.put(1,canada);
//		d_continents.get(1).getCountries().values().add(canada);
//		
//		Country us = new Country(2,"US", 343,435 ); 
//		d_countries.values().add(us);
//		d_continents.get(1).getCountries().values().add(us);
//		
//		Country mexco = new Country(3,"mexco", 343,435 ); 
//		d_countries.values().add(mexco);
//		d_continents.get(1).getCountries().values().add(mexco);
//		
//		Country cuba = new Country(4,"cuba", 343,435 ); 
//		d_countries.values().add(cuba);
//		d_continents.get(2).getCountries().values().add(cuba);
//		
//		Country brazil = new Country(5,"brazil", 343,435 ); 
//		d_countries.values().add(brazil);
//		d_continents.get(2).getCountries().values().add(brazil);
//		
//		Country chili = new Country(6,"chili", 343,435 );
//		d_countries.values().add(chili);
//		d_continents.get(2).getCountries().values().add(chili);
//		
//		Country agentina = new Country(7,"agentina", 343,435 ); 
//		d_countries.values().add(agentina);
//		d_continents.get(2).getCountries().values().add(agentina);
//		
//		canada.getNeighbors().values().add(us);
//		us.getNeighbors().values().add(canada);
//		us.getNeighbors().values().add(mexco);
//		
//		cuba.getNeighbors().values().add(brazil);
//		cuba.getNeighbors().values().add(chili);
//		cuba.getNeighbors().values().add(agentina);
//		brazil.getNeighbors().values().add(agentina);
//		brazil.getNeighbors().values().add(chili);
//		chili.getNeighbors().values().add(agentina);
//		agentina.getNeighbors().values().add(chili);
		
		
		
		
		
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
