package warzone.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * test class of advanceorder
 */
public class AdvanceOrderTest {

	/**
	 * Game Context
	 */
	private GameContext d_gameContext;
	/**
	 * attacker player
	 */
	private Player d_attacker;
	/**
	 * d_defender player
	 */
	private Player d_defender;
	/**
	 *  attacking Country
	 */
	private Country d_attackingCountry;
	/**
	 * Defending Country
	 */
	private Country d_defendingCountry;
	/**
	 * Game Context
	 */

	/**
	 * setup before each method
	 */
	@Before
	public void setup() {
		
		d_gameContext = GameContext.getGameContext();
		
		//Create 2 players
		d_attacker = new Player("attacker");
		d_defender = new Player("defender");
		
		//Add players to gameContext
		d_gameContext.getPlayers().put("attacker", d_attacker);
		d_gameContext.getPlayers().put("defender", d_defender);
		
		//Create 2 countries
		d_attackingCountry = new Country(1, "attackingCountry");
		d_defendingCountry = new Country(2, "defendingCountry");
		
		//Make countries neighbors		
		d_attackingCountry.addNeighbor(d_defendingCountry);
		d_defendingCountry.addNeighbor(d_attackingCountry);
		
		//Add countries to players conquered countries maps
		d_attacker.getConqueredCountries().put(1, d_attackingCountry);
		d_defender.getConqueredCountries().put(2, d_defendingCountry);
		d_attackingCountry.setCountryState(CountryState.Occupied, d_attacker);
		d_defendingCountry.setCountryState(CountryState.Occupied,d_defender);
		
		//Add countries to gameContext
		d_gameContext.getCountries().put(1, d_attackingCountry);
		d_gameContext.getCountries().put(2, d_defendingCountry);
	}
	
	/**
	 * test attacker conquers but no army in the territory
	 */
	@Test
	public void testAttackerConquersDefender() {
		
		//Add armies to both countries
		d_attackingCountry.setArmyNumber(1000);
		d_defendingCountry.setArmyNumber(20);
		
		
		//Execute AdvanceOrder
		new AdvanceOrder(d_attacker, d_attackingCountry, d_defendingCountry, 1000).execute();
		
		//Make sure attacker conquered country
		assertTrue(d_attacker.getConqueredCountries().size() == 2);
		assertTrue(d_defender.getConqueredCountries().size() == 0);
		
		//Make sure the attacking and defending countries lost armies
		assertTrue(d_attackingCountry.getArmyNumber() == 0); //Attacker moves all armies to defender's country 
		assertTrue(d_defendingCountry.getArmyNumber() < 1000); //The attacker moves all remaining armies (some should be lost due to fights)
	}
	
	/**
	 * test attacker conquers a defender
	 */
	@Test
	public void testAttackerConquersDefenderHasNoArmy() {
		
		//Add armies to both countries
		d_attackingCountry.setArmyNumber(1000);
		d_defendingCountry.setArmyNumber(0);
		
		
		//Execute AdvanceOrder
		new AdvanceOrder(d_attacker, d_attackingCountry, d_defendingCountry, 1000).execute();
		
		//Make sure attacker conquered country
		assertTrue(d_attacker.getConqueredCountries().size() == 2);
		assertTrue(d_defender.getConqueredCountries().size() == 0);
		
		//Make sure the attacking and defending countries lost armies
		assertTrue(d_attackingCountry.getArmyNumber() == 0); //Attacker moves all armies to defender's country 
		assertTrue(d_defendingCountry.getArmyNumber() == 1000); //The attacker moves all remaining armies (some should be lost due to fights)
	}
	
	/**
	 * test when attacker conquer, some armis are kept
	 */
	@Test
	public void testAttackerConquersDefenderButKeepsSomeArmies() {
		
		//Add armies to both countries
		d_attackingCountry.setArmyNumber(1000);
		d_defendingCountry.setArmyNumber(20);
		
		
		//Execute AdvanceOrder
		new AdvanceOrder(d_attacker, d_attackingCountry, d_defendingCountry, 500).execute();
		
		//Make sure attacker conquered country
		assertTrue(d_attacker.getConqueredCountries().size() == 2);
		assertTrue(d_defender.getConqueredCountries().size() == 0);
		
		//Make sure the attacking and defending countries lost armies
		assertTrue(d_attackingCountry.getArmyNumber() == 500); //Attacker move 500 armies to defender's country, but keeps 500 in attacking country
		assertTrue(d_defendingCountry.getArmyNumber() < 500); //The attacker moves all remaining armies (some should be lost due to fights)
	}
	
	/**
	 * test when attacker does not conquer a defender
	 */
	@Test
	public void testAttackerDoesNotConquerDefender() {
		
		//Add armies to both countries
		d_attackingCountry.setArmyNumber(20);
		d_defendingCountry.setArmyNumber(1000);
		
		
		//Execute AdvanceOrder
		new AdvanceOrder(d_attacker, d_attackingCountry, d_defendingCountry, 20).execute();
		
		//Make sure attacker did not conquer country
		assertTrue(d_attacker.getConqueredCountries().size() == 1);
		assertTrue(d_defender.getConqueredCountries().size() == 1);
		
		//Make sure the attacking and defending countries lost armies
		assertTrue(d_attackingCountry.getArmyNumber() < 20); //Attacker should lose some armies
		assertTrue(d_defendingCountry.getArmyNumber() < 1000); //Some should be lost due to fights
	}

	/**
	 * test Attacker With Zero Army
	 */
	@Test
	public void testAttackerWithZeroArmy() {

		//Add armies to both countries
		d_attackingCountry.setArmyNumber(3);
		d_defendingCountry.setArmyNumber(2);


		//Execute AdvanceOrder
		new AdvanceOrder(d_attacker, d_attackingCountry, d_defendingCountry, 0).execute();

		//Make sure attacker did not conquer country
		assertTrue(d_attacker.getConqueredCountries().size() == 1);
		assertTrue(d_defender.getConqueredCountries().size() == 1);

		//Make sure the attacking and defending countries lost armies
		assertEquals(d_attackingCountry.getArmyNumber(), 3); //Attacker does not lose some armies
		assertEquals(d_defendingCountry.getArmyNumber(),  2); //Some should be lost due to fights
	}

	/**
	 * test case for test Attacker With Territory Have Zero Army
	 */
	@Test
	public void testAttackerWithTerritoryHaveZeroArmy() {

		//Add armies to both countries
		d_attackingCountry.setArmyNumber(0);
		d_defendingCountry.setArmyNumber(2);


		//Execute AdvanceOrder
		new AdvanceOrder(d_attacker, d_attackingCountry, d_defendingCountry, 20).execute();

		//Make sure attacker did not conquer country
		assertTrue(d_attacker.getConqueredCountries().size() == 1);
		assertTrue(d_defender.getConqueredCountries().size() == 1);

		//Make sure the attacking and defending countries lost armies
		assertEquals(d_attackingCountry.getArmyNumber(), 0); //Attacker does not lose some armies
		assertEquals(d_defendingCountry.getArmyNumber(),  2); //Some should be lost due to fights
	}

	/**
	 * faild test due to in Diplomacy
	 */
	@Test
	public void testWithDiplomacyInCurrentTurn(){
		d_defender.addCard(Card.NEGOTIATE);
		NegotiateOrder l_order = new NegotiateOrder(d_defender, d_attacker);
		l_order.execute();
		AdvanceOrder l_advance = new AdvanceOrder(d_attacker, d_attackingCountry, d_defendingCountry, 2);
		assertFalse(l_advance.valid());
	}
}
