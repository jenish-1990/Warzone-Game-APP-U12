package warzone.state;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import warzone.model.ControllerName;
import warzone.model.GameContext;
import warzone.model.GamePhase;
import warzone.model.Router;
import warzone.service.GameEngine;
import warzone.service.StartupService;

/** 
 * Test class for startup phase
 */
public class StartupTest {

	/**
	 * Game Context
	 */
	private GameContext d_gameContext;
	/**
	 * Game Engine
	 */
	private GameEngine d_gameEngine;
	/**
	 * Startup phase
	 */
	private Startup d_startupState;
	
	/**
	 * set up the context of the test class
	 */
	@Before
	public void setup() {
		
		d_gameContext = GameContext.getGameContext();
		d_gameEngine = GameEngine.getGameEngine(d_gameContext);
		d_gameContext.setCurrentRouter(new Router(null, null, null));
		d_startupState = new Startup(d_gameEngine);
		
		d_gameEngine.setPhase(d_startupState);
	}
	
	/**
	 * This method will test the wrong input map name
	 */
	@Test
	public void loadMapWrongFileNameIntegrationTest() {
		
		String l_mapName = "invalid_map_name.mapp";
		
		d_gameContext.setCurrentRouter(new Router(ControllerName.STARTUP, "loadmap", l_mapName));
		
		d_startupState.loadMap(l_mapName);
		
		assertTrue(d_gameContext.getContinents().size() == 0);
		assertTrue(d_gameContext.getCountries().size() == 0);
		
	}
	
	/**
	 * This mehod will test the valid input map name
	 */
	@Test
	public void loadMapCorrectFileNameIntegrationTest() {
		
		String l_mapName = "europe.map";
		
		d_gameContext.setCurrentRouter(new Router(ControllerName.STARTUP, "loadmap", l_mapName));
		
		d_startupState.loadMap(l_mapName);
		
		assertTrue(d_gameContext.getContinents().size() == 4);
		assertTrue(d_gameContext.getCountries().size() == 24);
		
	}
	
	/**
	 * This method can load a non-connected map.
	 */
	@Test
	public void loadMapLowConnectivityIntegrationTest() {
		
		String l_mapName = "not-connected.map";
		
		d_gameContext.setCurrentRouter(new Router(ControllerName.STARTUP, "loadmap", l_mapName));
		
		boolean l_isConnected = new StartupService(d_gameContext).loadMap(l_mapName);
		
		assertTrue(l_isConnected == false);
		assertTrue(d_gameContext.getContinents().size() == 2);
		assertTrue(d_gameContext.getCountries().size() == 5);
		
	}
	
	/**
	 * test for next command
	 * it is not ready to play
	 */
	@Test
	public void inputNextCommand() {
		d_startupState.next();
		assertFalse(d_gameEngine.getPhase().getGamePhase().equals(GamePhase.Reinforcement));
	}
}