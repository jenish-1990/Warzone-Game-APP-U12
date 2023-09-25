package warzone.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.controller.StartupController;
import warzone.service.ControllerFactory;

public class StartupControllerTest {
	StartupController d_startupController = ControllerFactory.getControllerFactory().getStartupController();
	String l_parameters = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testPlayerCountry1() {
		l_parameters = "123";
//		assertTrue(d_startupController.addRawPlayer(l_parameters));
	}
	
	@Test
	public void testPlayerCountry2() {
		l_parameters = "123 111";
		assertFalse(d_startupController.addRawPlayer(l_parameters));
	}
	
	@Test
	public void testPlayerCountry3() {
		l_parameters = "   ";
		assertFalse(d_startupController.addRawPlayer(l_parameters));
	}
	
	@Test
	public void testRemoveCountry1() {
		l_parameters = "123";
		assertFalse(d_startupController.removeRawPlayer(l_parameters));
	}
	
	@Test
	public void testRemoveCountry2() {
		l_parameters = "123 sss";
		assertFalse(d_startupController.removeRawPlayer(l_parameters));
	}
}
