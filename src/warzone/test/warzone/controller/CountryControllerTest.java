package warzone.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.controller.*;
import warzone.model.*;
import warzone.service.*;

/**
 * tests for CountryController class
 */
public class CountryControllerTest {
	CountryController d_countryController;
	String l_parameters = null;
	GameContext d_gameContext = GameContext.getGameContext();

	/**
	 * set up before each class
	 * initiate country controller and set the data
	 * @throws Exception from set and get the continent and country
	 */
	@Before
	public void setUp() throws Exception {
		GameContext.clear();
		d_countryController = new CountryController(d_gameContext);
		d_gameContext.getContinents().put(1, new Continent(1, "Continent-1"));
		d_gameContext.getCountries().put(1, new Country(1, "Continent-1"));
	}

	/**
	 * clear the gamecontext after test
	 */
	@AfterClass
	public static void afterClass() {
		GameContext.clear();
	}

	/**
	 * test for add country
	 */
	@Test
	public void addCountryTest() {
		l_parameters = "1 1";
		assertTrue(d_countryController.addCountry(l_parameters));
	}

}
