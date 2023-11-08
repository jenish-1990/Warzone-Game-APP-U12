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
     * make sure that player cannot deploy more armies that there is in their reinforcement pool
     */
    @Test
    public void excuteOrderTest1() {
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
    public void excuteOrderTest2() {
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
    public void excuteOrderTest3() {
    	//arrange
    	Player l_player = new Player("P1");
    	l_player.setArmiesToDeploy(5);

    	Country l_country = new Country(1,"C1",0,0,null);
    	
    	Order l_order = new DeployOrder(l_player, l_country , 5); 
    	
    	//act
    	assertEquals(l_order.execute(), false);
    	
    	//assert
    	assertEquals(l_country.getArmyNumber(),0 );
    }
    
    /**
     * check if player can deploy a negative number of army to a country
     */
    @Test
    public void excuteOrderTest4() {
    	//arrange
    	Player l_player = new Player("P1");
    	l_player.setArmiesToDeploy(5);

    	Country l_country = new Country(1,"C1",0,0,null);
    	l_country.setOwner(l_player);
    	l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
    	
    	Order l_order = new DeployOrder(l_player, l_country , -5); 
    	
    	//act
    	assertEquals(l_order.execute(), false);
    	
    	//assert
    	assertEquals(l_country.getArmyNumber(),0 );
    }
    
    
}
