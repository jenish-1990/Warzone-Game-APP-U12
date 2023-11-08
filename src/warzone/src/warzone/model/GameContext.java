package warzone.model;

import java.util.HashMap;
import java.util.Map;

public class GameContext {

	private Map<Integer, Player> d_players;
	private Map<Integer, Country> d_countries;
	private Map<Integer, Continent> d_continents;
	
	private static GameContext GAME_CONTEXT;
	
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
		
		d_players = new HashMap<Integer, Player>() ;
		d_countries = new HashMap<Integer, Country>();
		d_continents = new HashMap<Integer, Continent>();
//
//		d_continents.put(1,new Continent(1,"North_America", "Red", 3 ));
//		d_continents.put(2,new Continent(2,"South_America", "Blue", 5 ));
//		d_continents.put(3,new Continent(2,"Asia", "Pink", 5 ));
//
//		Country canada = new Country(1,"Canada", 343,435 );
//		d_countries.put(1,canada);
//		d_continents.get(1).addCountry(canada);
//
//		Country us = new Country(2,"US", 343,435 );
//		d_countries.put(2,us);
//		d_continents.get(1).addCountry(us);
//
//		Country mexco = new Country(3,"mexco", 343,435 );
//		d_countries.put(3,mexco);
//		d_continents.get(1).addCountry(mexco);
//
//		Country cuba = new Country(4,"cuba", 343,435 );
//		d_countries.put(4,cuba);
//		d_continents.get(2).addCountry(cuba);
//
//		Country brazil = new Country(5,"brazil", 343,435 );
//		d_countries.put(5,brazil);
//		d_continents.get(2).addCountry(brazil);
//
//		Country chili = new Country(6,"chili", 343,435 );
//		d_countries.put(6,chili);
//		d_continents.get(2).addCountry(chili);
//
//		Country agentina = new Country(7,"agentina", 343,435 );
//		d_countries.put(7,agentina);
//		d_continents.get(2).addCountry(agentina);
//
//		canada.addNeighbor(us);
//		us.addNeighbor(canada);
//		us.addNeighbor(mexco);
//
//		cuba.addNeighbor(brazil);
//		cuba.addNeighbor(chili);
//		cuba.addNeighbor(agentina);
//		brazil.addNeighbor(agentina);
//		brazil.addNeighbor(chili);
//		chili.addNeighbor(agentina);
//		agentina.addNeighbor(chili);
//
//
		
		
		
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
}
