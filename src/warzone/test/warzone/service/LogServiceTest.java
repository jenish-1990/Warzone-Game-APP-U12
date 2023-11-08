package warzone.service;

import org.junit.AfterClass;
import org.junit.jupiter.api.Test;

import warzone.model.GameContext;
import warzone.state.MapEditor;

public class LogServiceTest {
	MapEditor d_mapEditor = new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext()));
	
	@Test
	public void testAddContinent1() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.addContinent("1 2");
	}
	
	@Test
	public void testAddContinent2() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.addContinent("sda 2");
	}
	
	@Test
	public void testRemoveContinent1() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.removeContinent("1");
	}
	
	@Test
	public void testRemoveContinent2() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.removeContinent("sda 2");
	}
	
	@Test
	public void testAddContry1() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.addCountry("sda 2");
	}
	
	@Test
	public void testAddContry2() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.addCountry("1 2");
	}
	
	@Test
	public void testRemoveContry1() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.removeCountry("1 2");
	}
	
	@Test
	public void testRemoveContry2() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.removeCountry("asd 2");
	}
	
	@Test
	public void testAddNeighbor1() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.addNeighbor("1 2");
	}
	
	@Test
	public void testAddNeighbor2() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.addNeighbor("asd 2");
	}
	
	@Test
	public void testRemoveNeighbor1() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.removeNeighbor("1 2");
	}
	
	@Test
	public void testRemoveNeighbor2() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.removeNeighbor("asd 2");
	}
	
	@Test
	public void testShowmap() {
		GameContext.getLogEntryBuffer().setPhase(new MapEditor(GameEngine.getGameEngine(GameContext.getGameContext())));
		d_mapEditor.showMap();
	}
	@AfterClass
	public static void afterClass() throws Exception {
		GameContext.clear();
	}
}
