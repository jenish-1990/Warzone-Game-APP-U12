package warzone.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.controller.StartupController;
import warzone.model.GameContext;
import warzone.service.ControllerFactory;

public class StartupControllerTest {
	StartupController d_startupController ;
	String l_parameters = null;
	GameContext d_gameContext = GameContext.getGameContext();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {		
		d_gameContext.clear();
		d_startupController = new StartupController(d_gameContext);		
	}
	
	@Test
	public void testPlayer1() {
		l_parameters = "123";
		assertTrue(d_startupController.addPlayer(l_parameters));
		assertTrue(d_gameContext.getPlayers().containsKey(l_parameters));		
	}
	
	@Test
	public void testPlayer2() {
		l_parameters = "123 1114";
		assertTrue(d_startupController.addPlayer(l_parameters));
	}
	
	@Test
	public void testPlayer3() {
		l_parameters = "   ";
		assertFalse(d_startupController.addPlayer(l_parameters));
	}
	
	@Test
	public void testRemovePlayer1() {
		l_parameters = "123456";
		assertFalse(d_startupController.removePlayer(l_parameters));
	}
	
	@Test
	public void testRemovePlayer2() {
		l_parameters = "123 sssadsfdsf";
		assertFalse(d_startupController.removePlayer(l_parameters));
	}
	
	@Test
	public void testRemovePlayer3() {
		l_parameters = "1234";
		assertTrue(d_startupController.addPlayer(l_parameters));
		assertTrue(d_gameContext.getPlayers().containsKey(l_parameters));	
		assertTrue(d_startupController.removePlayer(l_parameters));
		assertFalse(d_gameContext.getPlayers().containsKey(l_parameters));
	}
}
