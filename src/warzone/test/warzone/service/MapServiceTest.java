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
		
		GameContext l_gameContext = GameContext.getGameContext();
		MapController l_mapController = new MapController(l_gameContext);
		
		assertTrue(l_mapController.editMap("europe.map"));
		
		System.out.println("Map File Name: " + l_gameContext.getMapFileName());
		System.out.println("Map File Pic: " + l_gameContext.getMapFilePic());
		System.out.println("Map File Map: " + l_gameContext.getMapFileMap());
		System.out.println("Map File Name: " + l_gameContext.getMapFileCards());
		
		System.out.println();
		
		System.out.println("Number of Continents: " + l_gameContext.getContinents().size());
		System.out.println("Number of Countries: " + l_gameContext.getCountries().size());
	}
	
    /**
     * map1 is valid
     */
    @Test
    public void validateMap1() {

        //set map into d_gamecontext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, "RED" ));
        d_gameContext.getContinents().put(2,new Continent(2,"South_America", 5, "BLUE" ));

        CountryController l_countryCtrl = new CountryController(d_gameContext);
        new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(1,1);

        new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(2,1);

        new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(3,1);

        new Country(4,"cuba", 343,435, d_gameContext.getContinents().get(2) );
        l_countryCtrl.addCountry(4,2);

        new Country(5,"brazil", 343,435, d_gameContext.getContinents().get(2) );
        l_countryCtrl.addCountry(5,2);

        new Country(6,"chili", 343,435, d_gameContext.getContinents().get(2) );
        l_countryCtrl.addCountry(6,2);

        new Country(7,"agentina", 343,435, d_gameContext.getContinents().get(2) );
        l_countryCtrl.addCountry(7,2);

        NeighborController l_nbCtrl = new NeighborController(d_gameContext);
        l_nbCtrl.addNeighbor(1,2);
        l_nbCtrl.addNeighbor(2,1);
        l_nbCtrl.addNeighbor(2,3);
        l_nbCtrl.addNeighbor(3,2);

        l_nbCtrl.addNeighbor(3,5);
        l_nbCtrl.addNeighbor(5,3);

        l_nbCtrl.addNeighbor(4,5);
        l_nbCtrl.addNeighbor(4,6);
        l_nbCtrl.addNeighbor(4,7);
        l_nbCtrl.addNeighbor(5,7);
        l_nbCtrl.addNeighbor(5,6);
        l_nbCtrl.addNeighbor(5,4);
        l_nbCtrl.addNeighbor(6,7);
        l_nbCtrl.addNeighbor(6,4);
        l_nbCtrl.addNeighbor(7,6);

        MapService l_mapService = new MapService(d_gameContext);
        assertTrue(l_mapService.validateMap(d_gameContext));
    }
    
	/**
     * map2 is invalid, a map only has one country
     */
    @Test
    public void validateMap2() {

        //add map info into d_gameContext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, "RED" ));

        CountryController l_countryCtrl = new CountryController(d_gameContext);
        new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(1,1);

        MapService l_mapService = new MapService(d_gameContext);
        assertFalse(l_mapService.validateMap(d_gameContext));
    }
    
	/**
     * map3 is invalid, a map with a continent has no country
     */
    @Test
    public void validateMap3() {

        //add map info into d_gameContext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, "RED" ));
        d_gameContext.getContinents().put(3,new Continent(2,"Asia", 5, "PINK"));

        CountryController l_countryCtrl = new CountryController(d_gameContext);
        new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(1,1);


        new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(2,1);

        NeighborController l_nbCtrl = new NeighborController(d_gameContext);
        l_nbCtrl.addNeighbor(1,2);
        l_nbCtrl.addNeighbor(2,1);

        MapService l_mapService = new MapService(d_gameContext);
        assertFalse(l_mapService.validateMap(d_gameContext));
    }

    /**
     * map 4 is invalid, with north america connected but south america not connected
     */
    @Test
    public void validateMap4() {

        //set map into d_gamecontext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, "RED" ));
        d_gameContext.getContinents().put(2,new Continent(2,"South_America", 5, "BLUE" ));

        CountryController l_countryCtrl = new CountryController(d_gameContext);
        new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(1,1);

        new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(2,1);

        new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(3,1);

        new Country(4,"cuba", 343,435, d_gameContext.getContinents().get(2) );
        l_countryCtrl.addCountry(4,2);

        new Country(5,"brazil", 343,435, d_gameContext.getContinents().get(2) );
        l_countryCtrl.addCountry(5,2);

        new Country(6,"chili", 343,435, d_gameContext.getContinents().get(2) );
        l_countryCtrl.addCountry(6,2);

        new Country(7,"agentina", 343,435, d_gameContext.getContinents().get(2) );
        l_countryCtrl.addCountry(7,2);

        NeighborController l_nbCtrl = new NeighborController(d_gameContext);
        l_nbCtrl.addNeighbor(1,2);
        l_nbCtrl.addNeighbor(2,1);
        l_nbCtrl.addNeighbor(2,3);
        l_nbCtrl.addNeighbor(3,2);

        l_nbCtrl.addNeighbor(3,5);
        l_nbCtrl.addNeighbor(5,3);

        l_nbCtrl.addNeighbor(4,5);
        l_nbCtrl.addNeighbor(4,6);
        l_nbCtrl.addNeighbor(4,7);
        l_nbCtrl.addNeighbor(5,7);
        l_nbCtrl.addNeighbor(5,6);
        l_nbCtrl.addNeighbor(5,4);
        l_nbCtrl.addNeighbor(6,7);

        MapService l_mapService = new MapService(d_gameContext);
        assertFalse(l_mapService.validateMap(d_gameContext));
    }

    /**
     * continentMap1 is a connected subgraph
     */
    @Test
    public void validateContinentMap1() {
        //set map into d_gamecontext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, "RED" ));
        d_gameContext.getContinents().put(2,new Continent(2,"South_America", 5, "BLUE" ));

        CountryController l_countryCtrl = new CountryController(d_gameContext);
        new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(1,1);

        new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(2,1);

        new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(3,1);

        NeighborController l_nbCtrl = new NeighborController(d_gameContext);
        l_nbCtrl.addNeighbor(1,2);
        l_nbCtrl.addNeighbor(2,1);
        l_nbCtrl.addNeighbor(2,3);
        l_nbCtrl.addNeighbor(3,2);

        MapService l_mapService = new MapService(d_gameContext);
        Continent l_continent = d_gameContext.getContinents().get(1);
        assertTrue(l_mapService.validateSubGraph(l_continent));
    }
    /**
     * continentMap2 is not a connected subgraph
     */
    @Test
    public void validateContinentMap2() {
        //set map into d_gamecontext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, "RED" ));
        d_gameContext.getContinents().put(2,new Continent(2,"South_America", 5, "BLUE" ));

        CountryController l_countryCtrl = new CountryController(d_gameContext);
        new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(1,1);

        new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(2,1);

        new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        l_countryCtrl.addCountry(3,1);

        NeighborController l_nbCtrl = new NeighborController(d_gameContext);
        l_nbCtrl.addNeighbor(1,2);
        l_nbCtrl.addNeighbor(2,1);
        l_nbCtrl.addNeighbor(2,3);

        MapService l_mapService = new MapService(d_gameContext);
        Continent l_continent = d_gameContext.getContinents().get(1);
        assertFalse(l_mapService.validateSubGraph(l_continent));
    }
}