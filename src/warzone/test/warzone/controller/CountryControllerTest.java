package warzone.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.controller.*;
import warzone.model.*;
import warzone.service.*;

public class CountryControllerTest {
	CountryController d_countryController;
	String l_parameters = null;
	GameContext d_gameContext = GameContext.getGameContext();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		d_countryController = new CountryController(d_gameContext);
		d_gameContext.clear();
		d_gameContext.getContinents().put(1, new Continent(1, "Continent-1"));		
		d_gameContext.getCountries().put(1, new Country(1, "Continent-1"));
	}
	
	@Test
	public void addCountryTest() {		
		
		l_parameters = "1 1";
		assertTrue(d_countryController.addCountry(l_parameters));
		
		
	}
//	
//	@Test
//	public void addCountryNegativeTest() {
//		l_parameters = "2 1";
//		assertFalse(d_countryController.addCountry(l_parameters));
//		
//		l_parameters = "1 2";
//		assertFalse(d_countryController.addCountry(l_parameters));
//		
//		l_parameters = "a b";
//		assertFalse(d_countryController.addCountry(l_parameters));
//		
//		l_parameters = "1 b";
//		assertFalse(d_countryController.addCountry(l_parameters));
//		
//		l_parameters = "1 1 1";
//		assertFalse(d_countryController.addCountry(l_parameters));
//	}

	
}
