package warzone.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import warzone.controller.CountryController;
import warzone.controller.MapController;
import warzone.controller.NeighborController;
import warzone.model.*;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * tests for Player class
 */
public class PlayerTest {
	
	/**
	 * check whether successes
	 */
	@Test
	public void WillBombOrder() {
		//arrange
		Player l_player1 = new Player("P1");
		Player l_player2 = new Player("P2");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player1);
		l_country2.setOwner(l_player2);
		l_player1.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player2.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_country1.getNeighbors().put(2, l_country2);
		l_player1.getCards().add(Card.BOMB);

		//act
		BombOrder l_bombOrder = new BombOrder(2);
		l_bombOrder.setPlayer(l_player1);

		//assert
		assertTrue(l_bombOrder.valid());
		l_bombOrder.execute();
		assertEquals(l_country2.getArmyNumber(), 1);
	}
	
	/**
	 * check whether failed if the target country belongs to the owner
	 */
	@Test
	public void WillNotBombOrder2HisOwnCountry() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		l_country1.setArmyNumber(5);
		l_country1.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getCards().add(Card.BOMB);

		//act
		BombOrder l_bombOrder = new BombOrder(1);
		l_bombOrder.setPlayer(l_player);

		//assert
		assertFalse(l_bombOrder.valid());
	}
	
	/**
	 * check whether failed if the current player does not have bomb card
	 */
	@Test
	public void WillNotBombOrderWithoutAirliftCard() {
		//arrange
		Player l_player1 = new Player("P1");
		Player l_player2 = new Player("P2");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player1);
		l_country2.setOwner(l_player2);
		l_player1.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player2.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_country1.getNeighbors().put(2, l_country2);
		l_player1.getCards().add(Card.BLOCKADE);

		//act
		BombOrder l_bombOrder = new BombOrder(2);
		l_bombOrder.setPlayer(l_player1);

		//assert
		assertFalse(l_bombOrder.valid());
	}
    
    /**
     * make sure that player cannot deploy more armies that exceed their reinforcement pool
     */
    @Test
    public void willNotDeployArmyExceedPool() {
    	//arrange
    	Player l_player = new Player("P1");
    	l_player.setArmiesToDeploy(10);

    	Country l_country = new Country(1,"C1",0,0,null);
    	l_country.setOwner(l_player);
    	l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
    	
    	Order l_order = new DeployOrder(l_player, l_country , 20); 
    	
    	//act
    	l_order.execute();
    	
    	//assert
    	assertEquals(l_country.getArmyNumber(),10 );
    }
    
    /**
     * make sure that player deploy the same amount of army in the deploy order
     */
    @Test
    public void willDeploySameArmyAsPool() {
    	//arrange
    	Player l_player = new Player("P1");
    	l_player.setArmiesToDeploy(5);

    	Country l_country = new Country(1,"C1",0,0,null);
    	l_country.setOwner(l_player);
    	l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
    	
    	Order l_order = new DeployOrder(l_player, l_country , 5); 
    	
    	//act
    	l_order.execute();
    	
    	//assert
    	assertEquals(l_country.getArmyNumber(),5 );
    }
    
    /**
     * make sure that player can not deploy the army to a country which the player does not own.
     */
    @Test
    public void willNotDeployToOtherCountry() {
    	//arrange
    	Player l_player = new Player("P1");
    	l_player.setArmiesToDeploy(5);

    	Country l_country = new Country(1,"C1",0,0,null);
    	
    	Order l_order = new DeployOrder(l_player, l_country , 5); 
    	
    	//act
    	assertFalse(l_order.valid());
    	
    	//assert
    	assertEquals(l_country.getArmyNumber(),0 );
    }
    
    /**
     * check if player can deploy a negative number of army to a country
     */
    @Test
    public void willNotDeployNegativeArmy() {
    	//arrange
    	Player l_player = new Player("P1");
    	l_player.setArmiesToDeploy(5);

    	Country l_country = new Country(1,"C1",0,0,null);
    	l_country.setOwner(l_player);
    	l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
    	
    	Order l_order = new DeployOrder(l_player, l_country , -5); 
    	
    	//act
		assertFalse(l_order.valid());
    	
    	//assert
    	assertEquals(l_country.getArmyNumber(),0 );
    }

	/**
	 * check if conventOrder can generate the deploy order correctly
	 */
	@Test
	public void willGenerateDeployOrder() {
    	//arrange
		Player l_player = new Player("P1");
		l_player.setArmiesToDeploy(5);

		Country l_country = new Country(1,"C1",0,0,null);
		l_country.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
		l_player.l_armyToIssue = 5;
		l_player.l_armyHasIssued = 0;

		//act
		Order l_order = l_player.conventOrder("Deploy 1 2");

		//assert
		assertEquals(((DeployOrder)l_order).getArmyNumber(), 2);
		assertEquals(((DeployOrder)l_order).getCountry(), l_country);
	}

	/**
	 * check if failed if the source country does not belongs to the owner
	 */
	@Test
	public void WillNotAirliftOrderWithoutAirliftCard() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player);
		l_country2.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_player.getCards().add(Card.BLOCKADE);

		//act
		AirliftOrder l_order = new AirliftOrder(1, 2, 2);
		l_order.setPlayer(l_player);

		//assert
		assertFalse(l_order.valid());
	}

	/**
	 * check if failed if the source country does not belongs to the owner
	 */
	@Test
	public void WillNotAirliftOrderSourceCountryNotValid() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player);
		l_country2.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_player.getCards().add(Card.AIRLIFT);

		//act
		AirliftOrder l_order = new AirliftOrder(3, 1, 2);
		l_order.setPlayer(l_player);

		//assert
		assertFalse(l_order.valid());
	}

	/**
	 * check if failed if the target country does not belongs to the owner
	 */
	@Test
	public void WillNotAirliftOrderTargetCountryNotValid() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player);
		l_country2.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_player.getCards().add(Card.AIRLIFT);

		//act
		AirliftOrder l_order = new AirliftOrder(1, 3, 2);
		l_order.setPlayer(l_player);

		//assert
		assertFalse(l_order.valid());
	}

	/**
	 * check if failed when the airlift army is zero
	 */
	@Test
	public void WillNotAirliftOrderWithZeroArmy() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player);
		l_country2.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_player.getCards().add(Card.AIRLIFT);

		//act
		AirliftOrder l_order = new AirliftOrder(1, 2, 0);
		l_order.setPlayer(l_player);

		//assert
		assertFalse(l_order.valid());
	}

	/**
	 * check if failed when airlift army is below zero
	 */
	@Test
	public void WillNotAirliftOrderWithArmyBelowZero() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player);
		l_country2.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_player.getCards().add(Card.AIRLIFT);

		//act
		AirliftOrder l_order = new AirliftOrder(1, 2, -2);
		l_order.setPlayer(l_player);

		//assert
		assertFalse(l_order.valid());
	}

	/**
	 * check if failed when army is more than the player owns.
	 */
	@Test
	public void WillNotAirliftOrderWithArmyMoreThanInCountry() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player);
		l_country2.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_player.getCards().add(Card.AIRLIFT);

		//act
		AirliftOrder l_order = new AirliftOrder(1, 2, 7);
		l_order.setPlayer(l_player);

		//assert
		assertFalse(l_order.valid());
	}

	/**
	 * test cast for successfully execute airlift order
	 */
	@Test
	public void WillAirliftOrder() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player);
		l_country2.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_player.getCards().add(Card.AIRLIFT);

		//act
		AirliftOrder l_order = new AirliftOrder(1, 2, 3);
		l_order.setPlayer(l_player);
		l_order.execute();

		//assert
		assertEquals(l_country1.getArmyNumber(), 2);
		assertEquals(l_country2.getArmyNumber(), 6);
	}
	/**
	 * check if failed to generate the airlift order if the country does not exist
	 */
	@Test
	public void WillNotCreqteAirliftOrderWithErrorCountryID() {
		//arrange
		Player l_player = new Player("P1");
		Country l_country1 = new Country(1,"C1",0,0,null);
		Country l_country2 = new Country(2,"C2",0,0,null);
		l_country1.setArmyNumber(5);
		l_country2.setArmyNumber(3);
		l_country1.setOwner(l_player);
		l_country2.setOwner(l_player);
		l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
		l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
		l_player.getCards().add(Card.AIRLIFT);

		//act
		Order l_order = l_player.conventOrder("airlift 3 2 2");

		//assert
		assertEquals(l_order, null);
	}

}
