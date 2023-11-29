package warzone.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import warzone.model.GameContext;
import warzone.model.Router;
import warzone.state.MapEditor;

/**
 * test cases for ConquestMapWriter
 * @author zexin
 *
 */
public class ConquestMapWriterTest {
	/**
	 * gamecontext before each test
	 */
    GameContext d_gameContext;

    /**
     * conquestMapWriter
     */
    ConquestMapWriter d_conquestMapWriter;
    
    /**
     * conquestMapReader
     */
    ConquestMapReader d_conquestMapReader;

    /**
     * clear the gamecontext before each test
     */
    @Before
    public void beforeEachTetCase(){
        GameContext.clear();
        d_gameContext = GameContext.getGameContext();
        d_conquestMapWriter = new ConquestMapWriter(d_gameContext);
        d_conquestMapReader = new ConquestMapReader(d_gameContext);
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
    	assertTrue(d_conquestMapReader.loadConquestMap("starwar.map"));
    	int l_continentNum = d_gameContext.getContinents().size();
    	int l_countryNum = d_gameContext.getCountries().size();

    	assertTrue(d_conquestMapWriter.saveConquestMap("starwar-test.map"));
    	assertEquals(d_gameContext.getContinents().size(), l_continentNum);
    	assertEquals(d_gameContext.getCountries().size(), l_countryNum);
    }

    /**
     * test save map function in wrong file name "This-is-not-an-valid-file-name.map"
     * @throws IOException io exception 
     */
    @Test
    public void testSaveConquestMapInvalidFileName1() throws IOException {
    	assertFalse(d_conquestMapWriter.saveConquestMap("This-is-not-an-valid-file-name.map"));
    }

    /**
     * test save map function null file name
     * @throws IOException io exception 
     */
    @Test
    public void testSaveConquestMapInvalidFileName2() throws IOException {
    	assertFalse(d_conquestMapWriter.saveConquestMap(null));
    }

    /**
     * test save map function with empty file name
     * @throws IOException io exception 
     */
    @Test
    public void testSaveConquestMapInvalidFileName3() throws IOException {
    	assertFalse(d_conquestMapWriter.saveConquestMap(""));
    }
}
