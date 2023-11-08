package warzone.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BombOrderTest {

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
        l_country1.setCountryState(CountryState.Occupied, l_player1);
        l_country2.setCountryState(CountryState.Occupied, l_player2);
        l_player1.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player2.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_country1.getNeighbors().put(2, l_country2);
        l_player1.getCards().add(Card.BOMB);

        //act
        BombOrder l_bombOrder = new BombOrder(l_player1, l_country2);

        //assert
        assertTrue(l_bombOrder.valid());
        l_bombOrder.execute();
        assertEquals(l_country2.getArmyNumber(), 1);
    }

    /**
     * check whether successes
     */
    @Test
    public void WillReturnBombOrder() {
        //arrange
        Player l_player1 = new Player("P1");
        Player l_player2 = new Player("P2");
        Country l_country1 = new Country(1,"C1",0,0,null);
        Country l_country2 = new Country(2,"C2",0,0,null);
        GameContext.getGameContext().getCountries().put(1, l_country1);
        GameContext.getGameContext().getCountries().put(2, l_country2);
        l_country1.setArmyNumber(5);
        l_country2.setArmyNumber(3);
        l_country1.setCountryState(CountryState.Occupied, l_player1);
        l_country2.setCountryState(CountryState.Occupied, l_player2);
        l_player1.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player2.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_country1.getNeighbors().put(2, l_country2);
        l_player1.getCards().add(Card.BOMB);

        //act
        BombOrder l_bombOrder = l_player1.createBombOrder(new String[] {"bomb", "2"});

        //assert
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
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getCards().add(Card.BOMB);

        //act
        BombOrder l_bombOrder = new BombOrder(l_player, l_country1);

        //assert
        assertFalse(l_bombOrder.valid());
    }

    /**
     * check whether failed if the target country belongs to the owner
     */
    @Test
    public void WillReturnNullBombOrder2HisOwnCountry() {
        //arrange
        Player l_player = new Player("P1");
        Country l_country1 = new Country(1,"C1",0,0,null);
        GameContext.getGameContext().getCountries().put(1, l_country1);
        l_country1.setArmyNumber(5);
        l_country1.setCountryState(CountryState.Occupied, l_player);
        l_player.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player.getCards().add(Card.BOMB);

        //act
        BombOrder bomb = l_player.createBombOrder(new String[] {"bomb", "1"});

        //assert
        assertNull(bomb);
    }

    /**
     * check whether failed if the target country belongs to the owner
     */
    @Test
    public void WillReturnNullBombOrderIfCountryNotExist() {
        //arrange
        Player l_player1 = new Player("P1");
        Player l_player2 = new Player("P2");
        Country l_country1 = new Country(1,"C1",0,0,null);
        Country l_country2 = new Country(2,"C2",0,0,null);
        l_country1.setArmyNumber(5);
        l_country2.setArmyNumber(3);
        l_country1.setCountryState(CountryState.Occupied, l_player1);
        l_country2.setCountryState(CountryState.Occupied, l_player2);
        l_player1.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player2.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_player1.getCards().add(Card.BOMB);

        //act
        BombOrder bomb = l_player1.createBombOrder(new String[] {"bomb", "1"});

        //assert
        assertNull(bomb);
    }

    /**
     * check whether failed if the current player does not have bomb card
     */
    @Test
    public void WillBombOrderWithoutBombCard() {

        //arrange
        Player l_player1 = new Player("P1");
        Player l_player2 = new Player("P2");
        Country l_country1 = new Country(1,"C1",0,0,null);
        Country l_country2 = new Country(2,"C2",0,0,null);
        GameContext.getGameContext().getCountries().put(1, l_country1);
        GameContext.getGameContext().getCountries().put(2, l_country2);
        l_country1.setArmyNumber(5);
        l_country2.setArmyNumber(3);
        l_country1.setCountryState(CountryState.Occupied, l_player1);
        l_country2.setCountryState(CountryState.Occupied, l_player2);
        l_player1.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player2.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_country1.getNeighbors().put(2, l_country2);
        l_player1.getCards().add(Card.BLOCKADE);

        //act
        BombOrder l_bombOrder = new BombOrder(l_player1, l_country2);

        //assert
        assertTrue(l_bombOrder.valid());
    }

    /**
     * check whether failed if the current player does not have bomb card
     */
    @Test
    public void WillReturnNullBombOrderWithoutBombCard() {
        //arrange
        Player l_player1 = new Player("P1");
        Player l_player2 = new Player("P2");
        Country l_country1 = new Country(1,"C1",0,0,null);
        Country l_country2 = new Country(2,"C2",0,0,null);
        l_country1.setArmyNumber(5);
        l_country2.setArmyNumber(3);
        l_country1.setCountryState(CountryState.Occupied, l_player1);
        l_country2.setCountryState(CountryState.Occupied, l_player2);
        l_player1.getConqueredCountries().put(l_country1.getCountryID(), l_country1);
        l_player2.getConqueredCountries().put(l_country2.getCountryID(), l_country2);
        l_country1.getNeighbors().put(2, l_country2);
        l_player1.getCards().add(Card.BLOCKADE);

        //assert
        assertNull(l_player1.createBombOrder(new String[] {"bomb", "1"}));
    }

}