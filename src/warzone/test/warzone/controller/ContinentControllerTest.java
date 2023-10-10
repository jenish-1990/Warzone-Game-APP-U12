package warzone.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.controller.ContinentController;
import warzone.model.GameContext;
import warzone.service.ControllerFactory;

/**
 * tests for ContinentController class
 */
public class ContinentControllerTest {
	ContinentController d_continentController = ControllerFactory.getControllerFactory().getContinentController();
	String l_parameters = null;

	/**
	 * clear the gamecontext before test
	 */
	@BeforeClass
	public static void beforeClass() {
		GameContext.clear();
	}

	/**
	 * clear the gamecontext after test
	 */
	@AfterClass
	public static void afterClass() {
		GameContext.clear();
	}

	/**
	 * test for add continent1, assert true
	 */
	@Test
	public void testAddContinent1() {
		l_parameters = "123 aaa";
		assertTrue(d_continentController.addContinent(l_parameters));
	}

	/**
	 * test for add continent2 without continent id, assert false
	 */
	@Test
	public void testAddContinent2() {
		l_parameters = "aaa";
		assertFalse(d_continentController.addContinent(l_parameters));
	}

	/**
	 * test for add continent3 with unacceptable continent id, assert false
	 */
	@Test
	public void testAddContinent3() {
		l_parameters = "aaa aaa";
		assertFalse(d_continentController.addContinent(l_parameters));
	}

	/**
	 * test for add continent4 with unacceptable parameters, assert false
	 */
	@Test
	public void testAddContinent4() {
		l_parameters = "aaa sss aaa";
		assertFalse(d_continentController.addContinent(l_parameters));
	}

	/**
	 * test for remove continent1, assert true
	 */
	@Test
	public void testRemoveContinent1() {
		l_parameters = "123";
		assertTrue(d_continentController.removeContinent(l_parameters));
	}

	/**
	 * test for remove continent2 without continent id, assert false
	 */
	@Test
	public void testRemoveContinent2() {
		l_parameters = "aaa";
		assertFalse(d_continentController.removeContinent(l_parameters));
	}

	/**
	 * test for remove continent3 wiith unacceptable continent id, assert false
	 */
	@Test
	public void testRemoveContinent3() {
		l_parameters = "aaa aaa";
		assertFalse(d_continentController.removeContinent(l_parameters));
	}

	/**
	 * test for remove continent4 with none parameters, assert false
	 */
	@Test
	public void testRemoveContinent4() {
		l_parameters = "";
		assertFalse(d_continentController.removeContinent(l_parameters));
	}
}
