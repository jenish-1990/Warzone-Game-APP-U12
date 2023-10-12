package warzone.service;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import warzone.model.ControllerName;
import warzone.model.ErrorType;
import warzone.model.GameContext;
import warzone.model.Router;

/**
 * This class is responsible to test the correctness of RouterService
 * @author Zexin
 *
 */
public class RouterServiceTest {
	private String d_command;
	private RouterService d_routerService;
	private List<Router> d_routerList;

	/**
	 * This method can clear the current game context so that it will not affect other
	 * test cases.
	 * @throws Exception exception when reading properties file
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		GameContext.clear();
	}

	/**
	 * This method will initiate the game context before each test case and other date
	 * members in the class.
	 */
	@Before
	public void beforeEachClass() {
		GameContext.clear();
		d_routerService = RouterService.getRouterService( GameEngine.getGameEngine( GameContext.getGameContext() ));
		d_routerList = new LinkedList<Router>();
	}

	/**
	 * This method will test the result of input command "editcontinent dsa 1".
	 */
	@Test
	public void testParseCommand1() {
		d_command = "editcontinent dsa 1";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.ERROR);
		assertEquals(d_routerList.get(0).getActionName(), ErrorType.COMMAND_ERROR.toString());
	}

	/**
	 * This method will test the result of input command "editcontinent -add das 1 ds  sss".
	 */
	@Test
	public void testParseCommand2() {
		d_command = "editcontinent -add das 1 ds  sss";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.CONTINENT);
		assertEquals(d_routerList.get(0).getActionName(), "add");
		assertEquals(d_routerList.get(0).getActionParameters(), "das 1 ds sss");
	}

	/**
	 * This method will test the result of input command "editcontinent -remove 1 sad dsad   dsa  ".
	 */
	@Test
	public void testParseCommand3() {
		d_command = "editcontinent -remove 1 sad dsad   dsa  ";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.CONTINENT);
		assertEquals(d_routerList.get(0).getActionName(), "remove");
		assertEquals(d_routerList.get(0).getActionParameters(), "1 sad dsad dsa");
	}

	/**
	 * This method will test the result of input command "editcountry -add 1 sad sss".
	 */
	@Test
	public void testParseCommand4() {
		d_command = "editcountry -add 1 sad sss";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.COUNTRY);
		assertEquals(d_routerList.get(0).getActionName(), "add");
		assertEquals(d_routerList.get(0).getActionParameters(), "1 sad sss");
	}

	/**
	 * This method will test the result of input command "editcountry -add 1 2 sss".
	 */
	@Test
	public void testParseCommand5() {
		d_command = "editcountry -add 1 2 sss";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.COUNTRY);
		assertEquals(d_routerList.get(0).getActionName(), "add");
		assertEquals(d_routerList.get(0).getActionParameters(), "1 2 sss");
	}

	/**
	 * This method will test the result of input command "editneighbor -add 1 2 -remove 3 -add 44".
	 */
	@Test
	public void testParseCommand6() {
		d_command = "editneighbor -add 1 2 -remove 3 -add 44";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 3);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.NEIGHBOR);
		assertEquals(d_routerList.get(0).getActionName(), "add");
		assertEquals(d_routerList.get(0).getActionParameters(), "1 2");
		assertEquals(d_routerList.get(1).getActionName(), "remove");
		assertEquals(d_routerList.get(1).getActionParameters(), "3");
		assertEquals(d_routerList.get(2).getActionName(), "add");
		assertEquals(d_routerList.get(2).getActionParameters(), "44");
	}

	/**
	 * This method will test the result of input command "gameplayer -add 1    2    -remove  aaa 2 -add   3  555 2dsa  4  ".
	 */
	@Test
	public void testParseCommand7() {
		d_command = "gameplayer -add 1    2    -remove  aaa 2 -add   3  555 2dsa  4  ";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 3);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.STARTUP);
		assertEquals(d_routerList.get(0).getActionName(), "add");
		assertEquals(d_routerList.get(0).getActionParameters(), "1 2");
		assertEquals(d_routerList.get(1).getActionName(), "remove");
		assertEquals(d_routerList.get(1).getActionParameters(), "aaa 2");
		assertEquals(d_routerList.get(2).getActionName(), "add");
		assertEquals(d_routerList.get(2).getActionParameters(), "3 555 2dsa 4");
	}

	/**
	 * This method will test the result of input command "   assigncountries   ".
	 */
	@Test
	public void testAssigncountries() {
		d_command = "   assigncountries   ";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.STARTUP);
		assertEquals(d_routerList.get(0).getActionName(), "assigncountries");
		assertNull(d_routerList.get(0).getActionParameters());
	}

	/**
	 * This method will test the result of input command " validatemap   ".
	 */
	@Test
	public void testValidatemap() {
		d_command = " validatemap   ";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.MAP);
		assertEquals(d_routerList.get(0).getActionName(), "validatemap");
		assertNull(d_routerList.get(0).getActionParameters());
	}

	/**
	 * This method will test the result of input command "   loadmap aaass 111 ".
	 */
	@Test
	public void testLoadMap1() {
		d_command = "   loadmap aaass 111 ";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.ERROR);
		assertEquals(d_routerList.get(0).getActionName(), ErrorType.TOO_MUCH_PARAMETERS.toString());
	}

	/**
	 * This method will test the result of input command "   loadmap aaass".
	 */
	@Test
	public void testLoadMap2() {
		d_command = "   loadmap aaass";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.STARTUP);
		assertEquals(d_routerList.get(0).getActionName(), "loadmap");
		assertEquals(d_routerList.get(0).getActionParameters(), "aaass");
	}

	/**
	 * This method will test the result of input command "   loadmap".
	 */
	@Test
	public void testLoadMap3() {
		d_command = "   loadmap";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.ERROR);
		assertEquals(d_routerList.get(0).getActionName(), ErrorType.MISSING_PARAMETER.toString());
	}

	/**
	 * This method will test the result of input command "   editmap sss ".
	 */
	@Test
	public void testEditMap() {
		d_command = "   editmap sss ";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.MAP);
		assertEquals(d_routerList.get(0).getActionName(), "editmap");
		assertEquals(d_routerList.get(0).getActionParameters(), "sss");
	}

	/**
	 * This method will test the result of input command "   savemap sdsadsa ".
	 */
	@Test
	public void testSaveMap() {
		d_command = "   savemap sdsadsa ";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.MAP);
		assertEquals(d_routerList.get(0).getActionName(), "savemap");
		assertEquals(d_routerList.get(0).getActionParameters(), "sdsadsa");
	}

	/**
	 * This method will test the result of input command "dsadsa -dsad dsad".
	 */
	@Test
	public void testNoSuchCommand1() {
		d_command = "dsadsa -dsad dsad";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.ERROR);
		assertEquals(d_routerList.get(0).getActionName(), ErrorType.COMMAND_ERROR.toString());
	}

	/**
	 * This method will test the result of input command "wrongCommand".
	 */
	@Test
	public void testNoSuchCommand2() {
		d_command = "wrongCommand";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.ERROR);
		assertEquals(d_routerList.get(0).getActionName(), ErrorType.COMMAND_ERROR.toString());
	}

	/**
	 * This method will test the result of input command "       ".
	 */
	@Test
	public void testMissingCommand1() {
		d_command = "       ";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.ERROR);
		assertEquals(d_routerList.get(0).getActionName(), ErrorType.MISSING_COMMAND.toString());
	}

	/**
	 * This method will test the result of input command "".
	 */
	@Test
	public void testMissingCommand2() {
		d_command = "";
		d_routerList = d_routerService.parseCommand(d_command);
		assertEquals(d_routerList.size(), 1);
		assertEquals(d_routerList.get(0).getControllerName(), ControllerName.ERROR);
		assertEquals(d_routerList.get(0).getActionName(), ErrorType.MISSING_COMMAND.toString());
	}
}
