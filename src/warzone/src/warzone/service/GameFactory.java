package warzone.service;

import java.util.ArrayList;
import java.util.List;

import warzone.model.GameMap;
import warzone.model.Player;

public class GameFactory {
	private static GameMap GLOBAL_MAP;
	private static List<Player> ALL_PLAYERS;
	
	public static GameMap getMap() {
		if(GLOBAL_MAP == null)
			GLOBAL_MAP = new GameMap();
		return GLOBAL_MAP;
	};
	
	public static List<Player> getAllPlays()
	{
		if(ALL_PLAYERS == null)
			ALL_PLAYERS = new ArrayList<Player>();
		return ALL_PLAYERS;
	};	
}
