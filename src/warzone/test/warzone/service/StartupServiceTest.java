package warzone.service;

import static org.junit.Assert.*;

import org.junit.Test;

import warzone.controller.MapController;
import warzone.controller.StartupController;
import warzone.model.GameContext;

public class StartupServiceTest {

	@Test
	public void testLoadMap() {
		
		GameContext gameContext = GameContext.getGameContext();
		StartupController startupController = new StartupController(gameContext);
		
		startupController.loadMap("europe.map");
		
		System.out.println("Map File Name: " + gameContext.getMapFileName());
		System.out.println("Map File Pic: " + gameContext.getMapFilePic());
		System.out.println("Map File Map: " + gameContext.getMapFileMap());
		System.out.println("Map File Name: " + gameContext.getMapFileCards());
		
		System.out.println();
		
		System.out.println("Number of Continents: " + gameContext.getContinents().size());
		System.out.println("Number of Countries: " + gameContext.getCountries().size());
	}
}