package warzone.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * tests for DeployOrder class
 */
public class DeployOrderTest {

    /**
     * make sure that player cannot deploy more armies that exceed their reinforcement pool
     */
    @Test
    public void willNotDeployArmyExceedPool() {
        //arrange
        Player l_player = new Player("P1");
        l_player.setArmiesToDeploy(10);

        Country l_country = new Country(1,"C1",0,0,null);
        l_country.setCountryState(CountryState.Occupied, l_player);
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
        l_country.setCountryState(CountryState.Occupied, l_player);
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
        l_country.setCountryState(CountryState.Occupied, l_player);
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
        l_country.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
        //l_player.l_armyHasIssued = 0;

        //act
        Order l_order = l_player.conventOrder("Deploy 1 2");

        //assert
        assertEquals(((DeployOrder)l_order).getArmyNumber(), 2);
        assertEquals(((DeployOrder)l_order).getCountry(), l_country);
    }
}