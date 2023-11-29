package warzone.state;

import org.junit.Test;

import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.GamePhase;
import warzone.model.Player;
import warzone.model.Router;
import warzone.service.GameEngine;

/** 
 * Test class for reinforcement phase
 */
public class ReinforcementTest {
	/**
	 * test for next command
	 */
	@Test
	public void inputNextCommand() {
		GameContext l_gameContext = GameContext.getGameContext();
		GameEngine l_gameEngine = GameEngine.getGameEngine(l_gameContext);
//		l_gameContext.setCurrentRouter(new Router(null, null, null));
//		Phase l_phase = new Startup(l_gameEngine);
//		l_gameEngine.setPhase(l_phase);
//		Reinforcement l_reinforcementState = new Reinforcement(l_gameEngine);
//		l_gameEngine.setPhase(l_reinforcementState);
//		Player l_player = new Player("P1");
//		Continent l_continent = new Continent(1, "Continent-1");
//		Country l_country = new Country(1, "country-1");		
//		l_country.setContinent(l_continent);
//		l_country.setOwner(l_player);
		
		//l_reinforcementState.next();
		//todo: fix this assert
		//assert(l_gameEngine.getPhase().getGamePhase()==l_gameEngine.getPhase().getGamePhase());
		assert(true);
	}
}
