package warzone.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.controller.StartupController;
import warzone.model.GameContext;
import warzone.service.ControllerFactory;

/**
 * tests for StartupController class
 */
public class StartupControllerTest {
	StartupController d_startupController ;
	String l_parameters = null;
	GameContext d_gameContext = GameContext.getGameContext();

	/**
	 * clear the gamecontext after test
	 */
	@AfterClass
	public static void afterClass() {
		GameContext.clear();
	}

	/**
	 * set up before each class
	 * clear the gameContext and initiate StartupController
	 * @throws Exception from create startup controller
	 */
	@Before
	public void setUp() throws Exception {		
		d_gameContext.clear();
		d_startupController = new StartupController(d_gameContext);		
	}

	/**
	 * test1 for add player, assert true
	 */
	@Test
	public void testPlayer1() {
		l_parameters = "123";
		assertTrue(d_startupController.addPlayer(l_parameters));
		assertTrue(d_gameContext.getPlayers().containsKey(l_parameters));		
	}

	/**
	 * test2 for add player, assert true
	 */
	@Test
	public void testPlayer2() {
		l_parameters = "123 1114";
		assertTrue(d_startupController.addPlayer(l_parameters));
	}

	/**
	 *test for add player with empty parameter, assert false
	 */
	@Test
	public void testPlayer3() {
		l_parameters = "   ";
		assertFalse(d_startupController.addPlayer(l_parameters));
	}

	/**
	 *test for remove player with not existed player, assert false
	 */
	@Test
	public void testRemovePlayer1() {
		l_parameters = "123456";
		assertFalse(d_startupController.removePlayer(l_parameters));
	}

	/**
	 *test for remove player with wrong parameter, assert false
	 */
	@Test
	public void testRemovePlayer2() {
		l_parameters = "123 sssadsfdsf";
		assertFalse(d_startupController.removePlayer(l_parameters));
	}

	/**
	 *test for check if successfully remove a player
	 */
	@Test
	public void testRemovePlayer3() {
		l_parameters = "1234";
		assertTrue(d_startupController.addPlayer(l_parameters));
		assertTrue(d_gameContext.getPlayers().containsKey(l_parameters));	
		assertTrue(d_startupController.removePlayer(l_parameters));
		assertFalse(d_gameContext.getPlayers().containsKey(l_parameters));
	}
}
