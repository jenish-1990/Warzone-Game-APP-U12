package warzone.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import warzone.model.ConquestMapHandler;
import warzone.model.GameContext;
import warzone.model.Router;
import warzone.state.MapEditor;

/**
 * test cases for ConquestMapWriter
 * @author zexin
 *
 */
public class ConquestMapHandlerTest {
	/**
	 * gamecontext before each test
	 */
    GameContext d_gameContext;

    /**
     * conquestMapWriter
     */
    ConquestMapHandler d_conquestMapHandler;
    

    /**
     * clear the gamecontext before each test
     */
    @Before
    public void beforeEachTetCase(){
        GameContext.clear();
        d_gameContext = GameContext.getGameContext();
        d_conquestMapHandler = new ConquestMapHandler(d_gameContext);
		GameEngine.getGameEngine(d_gameContext).setPhase(new MapEditor(GameEngine.getGameEngine(d_gameContext)));
		d_gameContext.setCurrentRouter(new Router(null, null, null, "testCommand"));
    }

    /**
     * clear the gamecontext after this class run
     */
    @AfterClass
    public static void afterClass() {
        GameContext.clear();
    }
    
    /**
     * test save map function
     * @throws IOException io exception 
     */
    @Test
    public void testSaveConquestMap2() throws IOException {
    	assertTrue(d_conquestMapHandler.loadMap("starwar.map"));
    	int l_continentNum = d_gameContext.getContinents().size();
    	int l_countryNum = d_gameContext.getCountries().size();

    	assertTrue(d_conquestMapHandler.saveMap("starwar-test.map"));
    	assertEquals(d_gameContext.getContinents().size(), l_continentNum);
    	assertEquals(d_gameContext.getCountries().size(), l_countryNum);
    }

    /**
     * test save map function in wrong file name "This-is-not-an-valid-file-name.map"
     * @throws IOException io exception 
     */
    @Test
    public void testSaveConquestMapInvalidFileName1() throws IOException {
    	assertFalse(d_conquestMapHandler.saveMap("This-is-not-an-valid-file-name.map"));
    }

    /**
     * test save map function null file name
     * @throws IOException io exception 
     */
    @Test
    public void testSaveConquestMapInvalidFileName2() throws IOException {
    	assertFalse(d_conquestMapHandler.saveMap(null));
    }

    /**
     * test save map function with empty file name
     * @throws IOException io exception 
     */
    @Test
    public void testSaveConquestMapInvalidFileName3() throws IOException {
    	assertFalse(d_conquestMapHandler.saveMap(""));
    }
    
    /**
     * test map file "simple-starwar"
     */
    @Test
    public void testLoadConquestMap() {
    	assertTrue(d_conquestMapHandler.loadMap("simple-starwar.map"));
    	assertEquals(d_gameContext.getContinents().size(), 2);

    	assertEquals(d_gameContext.getContinents().get(1).getCountries().size(), 2);
    	assertEquals(d_gameContext.getContinents().get(2).getCountries().size(), 3);

    	assertEquals(d_gameContext.getCountries().size(), 5);
    	assertEquals(d_gameContext.getCountries().get(1).getNeighbors().size(), 1);
    	assertEquals(d_gameContext.getCountries().get(2).getNeighbors().size(), 3);
    	assertEquals(d_gameContext.getCountries().get(3).getNeighbors().size(), 2);
    	assertEquals(d_gameContext.getCountries().get(4).getNeighbors().size(), 1);
    	assertEquals(d_gameContext.getCountries().get(5).getNeighbors().size(), 1);

    	assertEquals(d_gameContext.getCountries().get(1).getContinent().getContinentID(), 1);
    	assertEquals(d_gameContext.getCountries().get(2).getContinent().getContinentID(), 1);
    	assertEquals(d_gameContext.getCountries().get(3).getContinent().getContinentID(), 2);
    	assertEquals(d_gameContext.getCountries().get(4).getContinent().getContinentID(), 2);
    	assertEquals(d_gameContext.getCountries().get(5).getContinent().getContinentID(), 2);
    }

    /**
     * test invalid map file "no-such-conquest-map"
     */
    @Test
    public void testLoadConquestMap2() {
    	assertFalse(d_conquestMapHandler.loadMap("no-such-conquest-map.map"));
    }
}
