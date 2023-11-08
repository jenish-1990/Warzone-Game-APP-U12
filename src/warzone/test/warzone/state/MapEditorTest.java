package warzone.state;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import warzone.model.GameContext;
import warzone.model.GamePhase;
import warzone.model.Router;
import warzone.service.GameEngine;

/**
 * test class for MapEditor
 */
public class MapEditorTest {
	
	/**
	 * add And Remove Neighbor
	 */
	@Test
	public void addAndRemoveNeighbor() {
		GameContext l_gameContext = GameContext.getGameContext();
		GameEngine l_gameEngine = GameEngine.getGameEngine(l_gameContext);
		MapEditor l_mapEditorState = new MapEditor(l_gameEngine);
		l_gameEngine.setPhase(l_mapEditorState);
		l_gameContext.setCurrentRouter(new Router(null, null, null));
		l_mapEditorState.addContinent(1,5);
		l_mapEditorState.addCountry(101, 1);
		l_mapEditorState.addCountry(102, 1);
		l_mapEditorState.addNeighbor(101, 102);
		l_gameContext.getCountries().get(101).getNeighbors().size();
		assert(l_gameContext.getCountries().get(101).getNeighbors().size()==1);
		l_mapEditorState.removeNeighbor(101,102);
		l_gameContext.getCountries().get(101).getNeighbors().size();
		assert(l_gameContext.getCountries().get(101).getNeighbors().size()==0);
	}
	
	/**
	 * test for  Next Command
	 */
	@Test
	public void inputNextCommand() {
		GameContext l_gameContext = GameContext.getGameContext();
		GameEngine l_gameEngine = GameEngine.getGameEngine(l_gameContext);
		l_gameContext.setCurrentRouter(new Router(null, null, null));
		MapEditor l_mapEditorState = new MapEditor(l_gameEngine);
		l_gameEngine.setPhase(l_mapEditorState);
		l_mapEditorState.next();
		assert(l_gameEngine.getPhase().getGamePhase()==GamePhase.STARTUP);
	}
}