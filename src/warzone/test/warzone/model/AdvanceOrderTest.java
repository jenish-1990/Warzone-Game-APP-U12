package warzone.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import warzone.service.StartupService;

public class AdvanceOrderTest {

	private GameContext d_gameContext;
	private Player d_attacker;
	private Player d_defender;
	private Country d_attackingCountry;
	private Country d_defendingCountry;
	
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

}
