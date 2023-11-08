package warzone.service;

import static org.junit.Assert.*;

import org.junit.Test;

import warzone.controller.MapController;
import warzone.model.GameContext;

public class MapServiceTest {

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
}