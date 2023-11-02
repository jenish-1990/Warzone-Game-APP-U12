package warzone.state;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import warzone.model.ControllerName;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.Player;
import warzone.model.Router;
import warzone.service.GameEngine;

/**
 * Test class for Gameplay phase
 */
public class GamePlayTest {

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
	 * Test if a player wins the game by conquering all the countries
	 */
	@Test
	public void testIsGameEnded() {
		
		//Create 2 players
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		p1.setIsAlive(true);
		p2.setIsAlive(true);
		d_gameContext.getPlayers().put("p1", p1);
		d_gameContext.getPlayers().put("p2", p2);
		
		//Create 2 countries
		Country country1 = new Country(1, "country1");		
		Country country2 = new Country(2, "country2");		
		country1.addNeighbor(country2);
		country2.addNeighbor(country1);
		country1.setArmyNumber(3);
		country2.setArmyNumber(0);
		d_gameContext.getCountries().put(1, country1);
		d_gameContext.getCountries().put(2, country2);
		
		//Assign one country to each player
		p1.getConqueredCountries().put(1, country1);
		p2.getConqueredCountries().put(2, country2);
		country1.setOwner(p1);
		country2.setOwner(p2);
		
		//Create an advance order -> p1's country1 attacks p2's country2
		p1.getOrders().add(p1.createAdvanceOrder(new String[] {"advance", "country1", "country2", "3"}));
		
		//Assert that the game has not yet ended (the order has not executed yet)
		assertTrue(d_gameEngine.isGameEnded() == false);
		
		//Execute the advance order to win the game
		p1.getOrders().poll().execute();
		
		//Assert that the game has ended
		assertTrue(d_gameEngine.isGameEnded() == true);
	}
}