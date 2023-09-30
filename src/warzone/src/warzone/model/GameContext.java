package warzone.model;

import java.util.ArrayList;
import java.util.List;

public class GameContext {
	private GameMap d_GameMap;
	private List<Player> d_GamePlayers;
	
	private static GameContext GAMECONTEXT;
	
	
	private GameContext()
	{	
		d_GameMap = new GameMap();
		d_GamePlayers = new ArrayList<Player>();
	}
	
	public static GameContext getGameContext() {
		if( GAMECONTEXT == null)
			GAMECONTEXT = new GameContext();
		return GAMECONTEXT;
	}
	
	public GameMap getGameMap() {
		return d_GameMap;
	};
	
	public List<Player> getGamePlayers()
	{
		return d_GamePlayers;
	};	
}
