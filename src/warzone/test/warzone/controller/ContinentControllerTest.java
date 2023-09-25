package warzone.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.controller.ContinentController;
import warzone.service.ControllerFactory;

public class ContinentControllerTest {
	ContinentController d_continentController = ControllerFactory.getControllerFactory().getContinentController();
	String l_parameters = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testAddContinent1() {
		l_parameters = "123 aaa";
		assertTrue(d_continentController.addContinent(l_parameters));
	}
	
	@Test
	public void testAddContinent2() {
		l_parameters = "aaa";
		assertFalse(d_continentController.addContinent(l_parameters));
	}

	@Test
	public void testAddContinent3() {
		l_parameters = "aaa aaa";
		assertFalse(d_continentController.addContinent(l_parameters));
	}
	
	@Test
	public void testAddContinent4() {
		l_parameters = "aaa sss aaa";
		assertFalse(d_continentController.addContinent(l_parameters));
	}
	
	@Test
	public void testRemoveContinent1() {
		l_parameters = "123";
		assertTrue(d_continentController.removeContinent(l_parameters));
	}
	
	@Test
	public void testRemoveContinent2() {
		l_parameters = "aaa";
		assertFalse(d_continentController.removeContinent(l_parameters));
	}

	@Test
	public void testRemoveContinent3() {
		l_parameters = "aaa aaa";
		assertFalse(d_continentController.removeContinent(l_parameters));
	}
	
	@Test
	public void testRemoveContinent4() {
		l_parameters = "";
		assertFalse(d_continentController.removeContinent(l_parameters));
	}
}
