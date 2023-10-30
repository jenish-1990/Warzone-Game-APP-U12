package warzone.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AirliftOrderTest {
    /**
     * check if failed if the source country does not belongs to the owner
     * the actual check is in the creation only.
     */
    @Test
    public void WillAirliftOrderWithoutAirliftCard() {
        //arrange
        Player l_player = new Player("P1");
        Country l_country1 = new Country(1,"C1",0,0,null);
        Country l_country2 = new Country(2,"C2",0,0,null);
        l_country1.setArmyNumber(5);
        l_country2.setArmyNumber(3);
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_country2.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player.getCards().add(Card.BLOCKADE);

        //act
        AirliftOrder l_order = new AirliftOrder(l_player,l_country2, l_country2, 2);

        //assert
        assertTrue(l_order.valid());
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
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_country2.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player.getCards().add(Card.AIRLIFT);

        //act
        AirliftOrder l_order = new AirliftOrder(l_player,null, l_country2, 2);

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
        Player l_player2 = new Player("P2");
        Country l_country1 = new Country(1,"C1",0,0,null);
        Country l_country2 = new Country(2,"C2",0,0,null);
        l_country1.setArmyNumber(5);
        l_country2.setArmyNumber(3);
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_country2.setCountryState(CountryState.Occupied, l_player2);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player2.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player.getCards().add(Card.AIRLIFT);

        //act
        AirliftOrder l_order = new AirliftOrder(l_player,l_country1, l_country2, 2);

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
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_country2.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player.getCards().add(Card.AIRLIFT);

        //act
        AirliftOrder l_order = new AirliftOrder(l_player,l_country1, l_country2, 0);

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
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_country2.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player.getCards().add(Card.AIRLIFT);

        //act

        AirliftOrder l_order = new AirliftOrder(l_player,l_country1, l_country2, -2);

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
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_country2.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player.getCards().add(Card.AIRLIFT);

        //act
        AirliftOrder l_order = new AirliftOrder(l_player,l_country1, l_country2, 7);

        //assert
        assertTrue(l_order.valid());
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
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_country2.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player.getCards().add(Card.AIRLIFT);

        //act

        AirliftOrder l_order = new AirliftOrder(l_player,l_country1, l_country2, 3);
        l_order.execute();

        //assert
        assertEquals(l_country1.getArmyNumber(), 2);
        assertEquals(l_country2.getArmyNumber(), 6);
    }
    /**
     * check if failed to generate the airlift order if the country does not exist
     */
    @Test
    public void WillNotCreateAirliftOrderWithErrorCountryID() {
        //arrange
        Player l_player = new Player("P1");
        Country l_country1 = new Country(1,"C1",0,0,null);
        Country l_country2 = new Country(2,"C2",0,0,null);
        l_country1.setArmyNumber(5);
        l_country2.setArmyNumber(3);
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_country2.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player.getCards().add(Card.AIRLIFT);

        //act
        Order l_order = l_player.conventOrder("airlift 3 2 2");

        //assert
        assertEquals(l_order, null);
    }

}