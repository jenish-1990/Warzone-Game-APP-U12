package warzone.service;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import warzone.controller.MapController;
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
        d_gameContext = GameContext.getGameContext();
    }

    /**
     * clear the gamecontext after this class run
     */
    @AfterClass
    public static void afterClass() {
        GameContext.clear();
    }

	@Test
	public void testEditMap() {
		
		GameContext gameContext = GameContext.getGameContext();
		MapController mapController = new MapController(gameContext);
		
		mapController.editMap("europe.map");
		
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

        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(1,canada);
        d_gameContext.getContinents().get(1).getCountries().put(1, canada);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(2,us);
        d_gameContext.getContinents().get(1).getCountries().put(2, us);

        Country mexco = new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(3,mexco);
        d_gameContext.getContinents().get(1).getCountries().put(3, mexco);

        Country cuba = new Country(4,"cuba", 343,435, d_gameContext.getContinents().get(2) );
        d_gameContext.getCountries().put(4,cuba);
        d_gameContext.getContinents().get(2).getCountries().put(4, cuba);

        Country brazil = new Country(5,"brazil", 343,435, d_gameContext.getContinents().get(2) );
        d_gameContext.getCountries().put(5,brazil);
        d_gameContext.getContinents().get(2).getCountries().put(5, brazil);

        Country chili = new Country(6,"chili", 343,435, d_gameContext.getContinents().get(2) );
        d_gameContext.getCountries().put(6,chili);
        d_gameContext.getContinents().get(2).getCountries().put(6, chili);

        Country agentina = new Country(7,"agentina", 343,435, d_gameContext.getContinents().get(2) );
        d_gameContext.getCountries().put(7,agentina);
        d_gameContext.getContinents().get(2).getCountries().put(7, chili);

        canada.addNeighbor(us);
        us.addNeighbor(canada);
        us.addNeighbor(mexco);
        mexco.addNeighbor(us);
        mexco.addNeighbor(brazil);
        brazil.addNeighbor(mexco);

        cuba.addNeighbor(brazil);
        cuba.addNeighbor(chili);
        cuba.addNeighbor(agentina);
        brazil.addNeighbor(agentina);
        brazil.addNeighbor(chili);
        brazil.addNeighbor(cuba);
        chili.addNeighbor(agentina);
        chili.addNeighbor(cuba);
        agentina.addNeighbor(chili);

        MapService d_mapService = new MapService(d_gameContext);
        assertTrue(d_mapService.validateMap(d_gameContext));
    }
    
	/**
     * map2 is invalid, a map only has one country
     */
    @Test
    public void validateMap2() {

        //add map info into d_gameContext
        d_gameContext.getContinents().put(1,new Continent(1,"North_America", 3, Color.RED ));

        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(1,canada);
        d_gameContext.getContinents().get(1).getCountries().put(1, canada);

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


        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(1,canada);
        d_gameContext.getContinents().get(1).getCountries().put(1, canada);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(2,us);
        d_gameContext.getContinents().get(1).getCountries().put(2, us);

        canada.addNeighbor(us);
        us.addNeighbor(canada);

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

        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(1,canada);
        d_gameContext.getContinents().get(1).getCountries().put(1, canada);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(2,us);
        d_gameContext.getContinents().get(1).getCountries().put(2, us);

        Country mexco = new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(3,mexco);
        d_gameContext.getContinents().get(1).getCountries().put(3, mexco);

        Country cuba = new Country(4,"cuba", 343,435, d_gameContext.getContinents().get(2) );
        d_gameContext.getCountries().put(4,cuba);
        d_gameContext.getContinents().get(2).getCountries().put(4, cuba);

        Country brazil = new Country(5,"brazil", 343,435, d_gameContext.getContinents().get(2) );
        d_gameContext.getCountries().put(5,brazil);
        d_gameContext.getContinents().get(2).getCountries().put(5, brazil);

        Country chili = new Country(6,"chili", 343,435, d_gameContext.getContinents().get(2) );
        d_gameContext.getCountries().put(6,chili);
        d_gameContext.getContinents().get(2).getCountries().put(6, chili);

        Country agentina = new Country(7,"agentina", 343,435, d_gameContext.getContinents().get(2) );
        d_gameContext.getCountries().put(7,agentina);
        d_gameContext.getContinents().get(2).getCountries().put(7, chili);

        canada.addNeighbor(us);
        us.addNeighbor(canada);
        us.addNeighbor(mexco);
        mexco.addNeighbor(us);
        mexco.addNeighbor(brazil);
        brazil.addNeighbor(mexco);

        cuba.addNeighbor(brazil);
        cuba.addNeighbor(chili);
        cuba.addNeighbor(agentina);
        brazil.addNeighbor(agentina);
        brazil.addNeighbor(chili);
        brazil.addNeighbor(cuba);
        chili.addNeighbor(agentina);


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

        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(1,canada);
        d_gameContext.getContinents().get(1).getCountries().put(1, canada);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(2,us);
        d_gameContext.getContinents().get(1).getCountries().put(2, us);

        Country mexco = new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(3,mexco);
        d_gameContext.getContinents().get(1).getCountries().put(3, mexco);

        canada.addNeighbor(us);
        us.addNeighbor(canada);
        us.addNeighbor(mexco);
        mexco.addNeighbor(us);

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

        Country canada = new Country(1,"Canada", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(1,canada);
        d_gameContext.getContinents().get(1).getCountries().put(1, canada);

        Country us = new Country(2,"US", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(2,us);
        d_gameContext.getContinents().get(1).getCountries().put(2, us);

        Country mexco = new Country(3,"mexco", 343,435, d_gameContext.getContinents().get(1) );
        d_gameContext.getCountries().put(3,mexco);
        d_gameContext.getContinents().get(1).getCountries().put(3, mexco);

        canada.addNeighbor(us);
        us.addNeighbor(canada);
        us.addNeighbor(mexco);

        MapService d_mapService = new MapService(d_gameContext);
        Continent continent = d_gameContext.getContinents().get(1);
        assertFalse(d_mapService.validateSubGraph(continent));
    }
}