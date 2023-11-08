package warzone.model;

import java.util.ArrayList;
import java.util.List;

public class GameContext {
	private GameMap d_GameMap;
	private List<Player> d_GamePlayers;
	
	private static GameContext GAME_CONTEXT;
	
	
	private GameContext()
	{	
		d_GameMap = new GameMap();
		d_GamePlayers = new ArrayList<Player>();
	}
	
	public static GameContext getGameContext() {
		if( GAME_CONTEXT == null)
			GAME_CONTEXT = new GameContext();
		return GAME_CONTEXT;
	}
	
	public GameMap getGameMap() {
		return d_GameMap;
	};
	
	public List<Player> getGamePlayers()
	{
		return d_GamePlayers;
	};	
}
