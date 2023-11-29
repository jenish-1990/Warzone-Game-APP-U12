package warzone.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import warzone.model.ControllerName;
import warzone.model.GameContext;
import warzone.model.Player;
import warzone.model.Router;
import warzone.model.TournamentContext;
import warzone.state.Startup;

/**
 *  Game Engine Service Test
 * @author fzuray
 *
 */
public class GameEngineServiceTest {

	/**
	 * Game Context
	 */
	private GameContext d_gameContext;
	/**
	 * Game Engine
	 */
	private GameEngine d_gameEngine;

	/**
	 * set up the context of the test class
	 */
	@Before
	public void setup() {

		d_gameContext = GameContext.getGameContext();
		d_gameEngine = GameEngine.getGameEngine(d_gameContext);
	}

	/**
	 * Test if a valid game can be started
	 */
	@Test
	public void testValidGameCanStart() {

		String l_mapName = "europe.map";

		Startup l_startupPhase = new Startup(d_gameEngine);
		d_gameEngine.setPhase(l_startupPhase);

		StartupService l_startupService = new StartupService(d_gameContext);
		d_gameContext.setCurrentRouter(new Router(ControllerName.STARTUP, "loadmap", l_mapName));

		l_startupService.loadMap(l_mapName);

		Player l_p1 = new Player("p1");
		Player l_p2 = new Player("p2");

		l_startupService.addPlayer(l_p1);
		l_startupService.addPlayer(l_p2);

		l_startupService.assignCountries();

		assertTrue(d_gameEngine.isReadyToStart() == true);
	}

	/**
	 * Test if an invalid game can be started: not enough players
	 */
	@Test
	public void testInvalidGameCanStartNotEnoughPlayers() {

		String l_mapName = "europe.map";

		Startup l_startupPhase = new Startup(d_gameEngine);
		d_gameEngine.setPhase(l_startupPhase);

		StartupService l_startupService = new StartupService(d_gameContext);
		d_gameContext.setCurrentRouter(new Router(ControllerName.STARTUP, "loadmap", l_mapName));

		l_startupService.loadMap(l_mapName);

		Player l_p1 = new Player("p1");

		l_startupService.addPlayer(l_p1);

		l_startupService.assignCountries();

		assertTrue(d_gameEngine.isReadyToStart() == false);
	}

	/**
	 * Test if an invalid game can be started: no assigned countries
	 */
	@Test
	public void testInvalidGameCanStartNoCountriesAssigned() {

		String l_mapName = "europe.map";

		Startup l_startupPhase = new Startup(d_gameEngine);
		d_gameEngine.setPhase(l_startupPhase);

		StartupService l_startupService = new StartupService(d_gameContext);
		d_gameContext.setCurrentRouter(new Router(ControllerName.STARTUP, "loadmap", l_mapName));

		l_startupService.loadMap(l_mapName);

		Player l_p1 = new Player("p1");
		Player l_p2 = new Player("p2");

		l_startupService.addPlayer(l_p1);
		l_startupService.addPlayer(l_p2);

		assertTrue(d_gameEngine.isReadyToStart() == false);
	}
	
	@Test
	public void testTournamentModeEmptyMapList() {
		
		Startup l_startupPhase = new Startup(d_gameEngine);
		d_gameEngine.setPhase(l_startupPhase);
		
		l_startupPhase.setTournamentMapFiles(new String[] {""});
		l_startupPhase.setTournamentPlayerStrategies(new String[] {"aggressive", "benevolent", "random", "cheater"});
		l_startupPhase.setTournamentMaxTurns(5);
		l_startupPhase.setTournamentNumberOfGames(2);
		
		d_gameEngine.getTournamentContext().prepareResultsTable();
		d_gameEngine.initializeTournamentContext();
		TournamentContext l_tournamentContext = d_gameEngine.playTournament();
		
		assertTrue(l_tournamentContext.getResults() == null || l_tournamentContext.getResults()[0][0] == null );
	}
	
	@Test
	public void testTournamentModeEmptyPlayerStrategiesList() {
		
		Startup l_startupPhase = new Startup(d_gameEngine);
		d_gameEngine.setPhase(l_startupPhase);
		
		l_startupPhase.setTournamentMapFiles(new String[] {"europe.map, brasil.map"});
		l_startupPhase.setTournamentPlayerStrategies(new String[] {""});
		l_startupPhase.setTournamentMaxTurns(5);
		l_startupPhase.setTournamentNumberOfGames(2);
		
		d_gameEngine.getTournamentContext().prepareResultsTable();
		d_gameEngine.initializeTournamentContext();
		TournamentContext l_tournamentContext = d_gameEngine.playTournament();
		
		assertTrue(l_tournamentContext.getResults() == null || l_tournamentContext.getResults()[0][0] == null );
	}
	
	@Test
	public void testTournamentModeValid() {
		
		Startup l_startupPhase = new Startup(d_gameEngine);
		d_gameEngine.setPhase(l_startupPhase);
		
		l_startupPhase.setTournamentMapFiles(new String[] {"europe.map, brasil.map"});
		l_startupPhase.setTournamentPlayerStrategies(new String[] {"aggressive", "benevolent", "random", "cheater"});
		l_startupPhase.setTournamentMaxTurns(5);
		l_startupPhase.setTournamentNumberOfGames(2);
		
		d_gameEngine.getTournamentContext().prepareResultsTable();
		d_gameEngine.initializeTournamentContext();
		TournamentContext l_tournamentContext = d_gameEngine.playTournament();
		
		assertTrue(l_tournamentContext.getResults().length > 0 && l_tournamentContext.getResults()[0].length > 0);
	}
}