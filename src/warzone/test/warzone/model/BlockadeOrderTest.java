package warzone.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for BlockadeOrder class
 */
public class BlockadeOrderTest {
	/** 
	 * player 
	 */
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
		
	}
	
	/**
	 * This method tests the valid method of BlockadeOrder class
	 */
	@Test
	public void testValid() {
		d_player=new Player("player1");
		d_country=new Country(1,"country1");
		
		//act
		d_order=new BlockadeOrder(d_player, d_country);
		
		//assert
		assertTrue(d_order.valid());
	}
	
	/**
	 * This method tests the valid method of BlockadeOrder class
	 */
	@Test
	public void willFailedWhenNullCountry() {
		d_player=new Player("player1");
		
		//act
		d_order=new BlockadeOrder(d_player, null);
		
		//assert
		assertFalse(d_order.valid());
	}
	
	/**
	 * This method tests the valid method of BlockadeOrder class
	 */
	@Test
	public void willTrueWhenTargetCountryIsTheSameOwner() {
		d_player=new Player("player1");
		d_country=new Country(1,"country1");
		d_country.setOwner(d_player);
		
		//act
		d_order=new BlockadeOrder(d_player, d_country);
		
		//assert
		assertTrue(d_order.valid());
	}
	
	/**
	 * This method tests the valid method of BlockadeOrder class
	 */
	@Test
	public void willFailWhenTargetCountryIsDiplomacyInCurrentTurn() {
		d_player=new Player("player1");
		Player d_player2=new Player("player2");
		d_country=new Country(1,"country2");
		d_country.setOwner(d_player2);
		
		GameContext l_gameContext = GameContext.getGameContext();
		NegotiateOrder l_negotiateOrder = new NegotiateOrder(d_player, d_player2 );
		l_gameContext.addDiplomacyOrderToList(l_negotiateOrder);
		
		
		//act
		d_order=new BlockadeOrder(d_player, d_country);
		
		//assert
		assertFalse(d_order.valid());
	}
	
}
