package warzone.model;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for BlockadeOrder class
 */
public class BlockadeOrderTest {
	/** player */
	Player d_player;
	/** country */
	Country d_country;
	/** blockade order */
	BlockadeOrder d_order;
	
	/**
	 * This method can set up game context before test cases begin.
	 */
	@Before
	public void setup() {
		d_player=new Player("player1");
		d_country=new Country(0,"country1");
		d_order=new BlockadeOrder(d_player, 0);
	}
	
	/**
	 * This method tests the valid method of BlockadeOrder class
	 */
	@Test
	public void testValid() {
		assert(d_order.valid()==false);
		d_player.getConqueredCountries().put(d_country.getCountryID(), d_country);
		assert(d_order.valid()==true);
	}
}
