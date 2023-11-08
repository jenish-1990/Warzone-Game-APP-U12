package warzone.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for BlockadeOrder class
 */
public class BlockadeOrderTest {

	/**
	 * This method tests a valid order
	 */
	@Test
	public void validOrder() {
		Player l_player = new Player("player1");
		Country l_country = new Country(0, "country1");
		BlockadeOrder l_order = new BlockadeOrder(l_player, l_country);
		assertFalse(l_order.valid());
		l_country.setCountryState(CountryState.Occupied, l_player);
		l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
		assertTrue(l_order.valid());
		l_player.setIsAlive(false);
		assertFalse(l_order.valid());
	}

	/**
	 * Invalid order if target country not belong to player
	 */
	@Test
	public void invalidOrderIfTargetCountryNotBelongToPlayer() {
		Player l_player = new Player("player1");
		Country l_country = new Country(0, "country1");
		BlockadeOrder l_order = new BlockadeOrder(l_player, l_country);
		assertFalse(l_order.valid());
	}

	/**
	 * Invalid order if player dead
	 */
	@Test
	public void invalidOrderIfPlayerDead() {
		Player l_player = new Player("player1");
		Country l_country = new Country(0, "country1");
		BlockadeOrder l_order = new BlockadeOrder(l_player, l_country);
		l_country.setCountryState(CountryState.Occupied, l_player);
		l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
		assert (l_order.valid());
		l_player.setIsAlive(false);
		assertFalse(l_order.valid());
	}

	/**
	 * blockade order will execute success
	 */
	@Test
	public void willBlockade() {
		Player l_player = new Player("player1");
		Country l_country = new Country(0, "country1");
		l_country.setArmyNumber(4);
		BlockadeOrder l_order = new BlockadeOrder(l_player, l_country);
		l_country.setCountryState(CountryState.Occupied, l_player);
		l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
		l_order.execute();
		assertEquals(l_country.getArmyNumber(), 12);
		assertEquals(l_country.getCountryState(), CountryState.Neutral);
	}

	/**
	 * check if success to generate the blockade order with right command
	 */
	@Test
	public void willCreateBlockadeOrder() {
		Player l_player = new Player("P1");
		Country l_country = new Country(1, "C1", 0, 0, null);
		GameContext.getGameContext().getCountries().put(l_country.getCountryID(), l_country);
		l_country.setArmyNumber(5);
		l_country.setCountryState(CountryState.Occupied, l_player);
		l_player.getConqueredCountries().put(l_country.getCountryID(), l_country);
		l_player.getCards().add(Card.BLOCKADE);
		Order l_order = l_player.conventOrder("blockade 1");
		assertNotEquals(l_order, null);
	}

	/**
	 * should be fail for blockade a country which not belong to me
	 */
	@Test
	public void testValid() {
		Player l_player = new Player("player1");
		Country l_country = new Country(1, "country1");

		// act
		Order l_order = new BlockadeOrder(l_player, l_country);

		// assert
		assertFalse(l_order.valid());
	}

	/**
	 * This method tests the valid method of BlockadeOrder class
	 */
	@Test
	public void willFailedWhenNullCountry() {
		Player l_player = new Player("player1");

		// act
		Order l_order = new BlockadeOrder(l_player, null);

		// assert
		assertFalse(l_order.valid());
	}

	/**
	 * This method tests the valid method of BlockadeOrder class
	 */
	@Test
	public void willTrueWhenTargetCountryIsTheSameOwner() {
		Player l_player = new Player("player1");
		Country l_country = new Country(1, "country1");
		l_country.setCountryState(CountryState.Occupied, l_player);

		// act
		Order l_order = new BlockadeOrder(l_player, l_country);

		// assert
		assertTrue(l_order.valid());
	}

	/**
	 * This method tests the valid method of BlockadeOrder class
	 */
	@Test
	public void willFailWhenTargetCountryIsDiplomacyInCurrentTurn() {
		Player l_player = new Player("player1");
		Player l_player2 = new Player("player2");
		Country l_country = new Country(1, "country2");
		l_country.setCountryState(CountryState.Occupied, l_player2);

		GameContext l_gameContext = GameContext.getGameContext();
		NegotiateOrder l_negotiateOrder = new NegotiateOrder(l_player, l_player2);
		l_gameContext.addDiplomacyOrderToList(l_negotiateOrder);

		// act
		Order l_order = new BlockadeOrder(l_player, l_country);

		// assert
		assertFalse(l_order.valid());
	}

}
