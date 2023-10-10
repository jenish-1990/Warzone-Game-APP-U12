package warzone.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.controller.ContinentController;
import warzone.controller.NeighborController;
import warzone.service.ControllerFactory;

/**
 * tests for NeighborController class
 */
public class NeighborControllerTest {
	NeighborController d_neighborController = ControllerFactory.getControllerFactory().getNeighborController();
	String l_parameters = null;

	/**
	 * test1 for add neighbor with wrong parameter, assert false
	 */
	@Test
	public void testAddNeighbor1() {
		l_parameters = "123 111";
		assertFalse(d_neighborController.addNeighbor(l_parameters));
	}
	/**
	 * test2 for add neighbor with wrong parameter, assert false
	 */
	@Test
	public void testAddNeighbor2() {
		l_parameters = "aaa";
		assertFalse(d_neighborController.addNeighbor(l_parameters));
	}

	/**
	 * test3 for add neighbor with wrong parameter, assert false
	 */
	@Test
	public void testAddNeighbor3() {
		l_parameters = "aaa aaa";
		assertFalse(d_neighborController.addNeighbor(l_parameters));
	}

	/**
	 * test4 for add neighbor with wrong parameter, assert false
	 */
	@Test
	public void testAddNeighbor4() {
		l_parameters = "aaa sss aaa";
		assertFalse(d_neighborController.addNeighbor(l_parameters));
	}

	/**
	 * test5 for add neighbor with wrong parameter, assert false
	 */
	@Test
	public void testRemoveNeighbor1() {
		l_parameters = "123 111";
		assertFalse(d_neighborController.removeNeighbor(l_parameters));
	}

	/**
	 * test1 for remove neighbor with wrong parameter, assert false
	 */
	@Test
	public void testRemoveNeighbor2() {
		l_parameters = "aaa";
		assertFalse(d_neighborController.removeNeighbor(l_parameters));
	}

	/**
	 * test2 for remove neighbor with wrong parameter, assert false
	 */
	@Test
	public void testRemoveNeighbor3() {
		l_parameters = "aaa aaa";
		assertFalse(d_neighborController.removeNeighbor(l_parameters));
	}

	/**
	 * test3 for remove neighbor with wrong parameter, assert false
	 */
	@Test
	public void testRemoveNeighbor4() {
		l_parameters = "aaa sss aaa";
		assertFalse(d_neighborController.removeNeighbor(l_parameters));
	}
}
