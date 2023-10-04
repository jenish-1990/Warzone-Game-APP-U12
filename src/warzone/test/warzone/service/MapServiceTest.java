package warzone.service;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import warzone.controller.CountryController;
import warzone.controller.MapController;
import warzone.controller.NeighborController;
import warzone.model.*;

/**
 * test cases for map service
 */
public class MapServiceTest {
	
    GameContext d_gameContext;

    /**
     * clear the gamecontext before each test
     */
    @Before
    public void beforeEachTetCase(){
        GameContext.clear();
        d_gameContext = GameContext.getGameContext();
    }

    /**
     * clear the gamecontext after this class run
     */
    @AfterClass
    public static void afterClass() {
        GameContext.clear();
    }

    /**
     * test if successfully load map to edit
     */
	@Test
	public void testEditMap() {
		
		GameContext gameContext = GameContext.getGameContext();
		MapController mapController = new MapController(gameContext);
		
		assertTrue(mapController.editMap("europe.map"));
		
		System.out.println("Map File Name: " + gameContext.getMapFileName());
		System.out.println("Map File Pic: " + gameContext.getMapFilePic());
		System.out.println("Map File Map: " + gameContext.getMapFileMap());
		System.out.println("Map File Name: " + gameContext.getMapFileCards());
		
		System.out.println();
		
		System.out.println("Number of Continents: " + gameContext.getContinents().size());
		System.out.println("Number of Countries: " + gameContext.getCountries().size());
	}
	
    /**
     * map1 is valid
     */
    @Test
    public void validateMap1() {

        //set map into d_gamecontext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, Color.RED ));
        d_gameContext.getContinents().put(2,new Continent(2,"South_America", 5, Color.BLUE ));

        CountryController _countryCtrl = new CountryController(d_gameContext);
        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(1,1);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(2,1);

        Country mexco = new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(3,1);

        Country cuba = new Country(4,"cuba", 343,435, d_gameContext.getContinents().get(2) );
        _countryCtrl.addCountry(4,2);

        Country brazil = new Country(5,"brazil", 343,435, d_gameContext.getContinents().get(2) );
        _countryCtrl.addCountry(5,2);

        Country chili = new Country(6,"chili", 343,435, d_gameContext.getContinents().get(2) );
        _countryCtrl.addCountry(6,2);

        Country agentina = new Country(7,"agentina", 343,435, d_gameContext.getContinents().get(2) );
        _countryCtrl.addCountry(7,2);

        NeighborController _nbCtrl = new NeighborController(d_gameContext);
        _nbCtrl.addNeighbor(1,2);
        _nbCtrl.addNeighbor(2,1);
        _nbCtrl.addNeighbor(2,3);
        _nbCtrl.addNeighbor(3,2);

        _nbCtrl.addNeighbor(3,5);
        _nbCtrl.addNeighbor(5,3);

        _nbCtrl.addNeighbor(4,5);
        _nbCtrl.addNeighbor(4,6);
        _nbCtrl.addNeighbor(4,7);
        _nbCtrl.addNeighbor(5,7);
        _nbCtrl.addNeighbor(5,6);
        _nbCtrl.addNeighbor(5,4);
        _nbCtrl.addNeighbor(6,7);
        _nbCtrl.addNeighbor(6,4);
        _nbCtrl.addNeighbor(7,6);

        MapService d_mapService = new MapService(d_gameContext);
        boolean r = d_mapService.validateMap(d_gameContext);
        assertTrue(r);
    }
    
	/**
     * map2 is invalid, a map only has one country
     */
    @Test
    public void validateMap2() {

        //add map info into d_gameContext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, Color.RED ));

        CountryController _countryCtrl = new CountryController(d_gameContext);
        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(1,1);

        MapService d_mapService = new MapService(d_gameContext);
        assertFalse(d_mapService.validateMap(d_gameContext));
    }
    
	/**
     * map3 is invalid, a map with a continent has no country
     */
    @Test
    public void validateMap3() {

        //add map info into d_gameContext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, Color.RED ));
        d_gameContext.getContinents().put(3,new Continent(2,"Asia", 5, Color.PINK));

        CountryController _countryCtrl = new CountryController(d_gameContext);
        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(1,1);


        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(2,1);

        NeighborController _nbCtrl = new NeighborController(d_gameContext);
        _nbCtrl.addNeighbor(1,2);
        _nbCtrl.addNeighbor(2,1);

        MapService d_mapService = new MapService(d_gameContext);
        assertFalse(d_mapService.validateMap(d_gameContext));
    }

    /**
     * map 4 is invalid, with north america connected but south america not connected
     */
    @Test
    public void validateMap4() {

        //set map into d_gamecontext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, Color.RED ));
        d_gameContext.getContinents().put(2,new Continent(2,"South_America", 5, Color.BLUE ));

        CountryController _countryCtrl = new CountryController(d_gameContext);
        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(1,1);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(2,1);

        Country mexco = new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(3,1);

        Country cuba = new Country(4,"cuba", 343,435, d_gameContext.getContinents().get(2) );
        _countryCtrl.addCountry(4,2);

        Country brazil = new Country(5,"brazil", 343,435, d_gameContext.getContinents().get(2) );
        _countryCtrl.addCountry(5,2);

        Country chili = new Country(6,"chili", 343,435, d_gameContext.getContinents().get(2) );
        _countryCtrl.addCountry(6,2);

        Country agentina = new Country(7,"agentina", 343,435, d_gameContext.getContinents().get(2) );
        _countryCtrl.addCountry(7,2);

        NeighborController _nbCtrl = new NeighborController(d_gameContext);
        _nbCtrl.addNeighbor(1,2);
        _nbCtrl.addNeighbor(2,1);
        _nbCtrl.addNeighbor(2,3);
        _nbCtrl.addNeighbor(3,2);

        _nbCtrl.addNeighbor(3,5);
        _nbCtrl.addNeighbor(5,3);

        _nbCtrl.addNeighbor(4,5);
        _nbCtrl.addNeighbor(4,6);
        _nbCtrl.addNeighbor(4,7);
        _nbCtrl.addNeighbor(5,7);
        _nbCtrl.addNeighbor(5,6);
        _nbCtrl.addNeighbor(5,4);
        _nbCtrl.addNeighbor(6,7);

        MapService d_mapService = new MapService(d_gameContext);
        assertFalse(d_mapService.validateMap(d_gameContext));
    }

    /**
     * continentMap1 is a connected subgraph
     */
    @Test
    public void validateContinentMap1() {
        //set map into d_gamecontext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, Color.RED ));
        d_gameContext.getContinents().put(2,new Continent(2,"South_America", 5, Color.BLUE ));

        CountryController _countryCtrl = new CountryController(d_gameContext);
        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(1,1);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(2,1);

        Country mexco = new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(3,1);

        NeighborController _nbCtrl = new NeighborController(d_gameContext);
        _nbCtrl.addNeighbor(1,2);
        _nbCtrl.addNeighbor(2,1);
        _nbCtrl.addNeighbor(2,3);
        _nbCtrl.addNeighbor(3,2);

        MapService d_mapService = new MapService(d_gameContext);
        Continent continent = d_gameContext.getContinents().get(1);
        assertTrue(d_mapService.validateSubGraph(continent));
    }
    /**
     * continentMap2 is not a connected subgraph
     */
    @Test
    public void validateContinentMap2() {
        //set map into d_gamecontext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, Color.RED ));
        d_gameContext.getContinents().put(2,new Continent(2,"South_America", 5, Color.BLUE ));

        CountryController _countryCtrl = new CountryController(d_gameContext);
        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(1,1);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(2,1);

        Country mexco = new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        _countryCtrl.addCountry(3,1);

        NeighborController _nbCtrl = new NeighborController(d_gameContext);
        _nbCtrl.addNeighbor(1,2);
        _nbCtrl.addNeighbor(2,1);
        _nbCtrl.addNeighbor(2,3);

        MapService d_mapService = new MapService(d_gameContext);
        Continent continent = d_gameContext.getContinents().get(1);
        assertFalse(d_mapService.validateSubGraph(continent));
    }
}