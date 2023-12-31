package warzone.model;
import static org.junit.Assert.assertFalse;

import org.junit.Test;


/**
 * Tests for NegotiateOrder  class
 */
public class NegotiateOrderTest {

	/**
	 * check if success to generate the Negotiate order with right command
	 */
	@Test
	public void willCreateNegotiateOrder() {
		Player l_player = new Player("P1");
		Player l_player2 = new Player("p2");
		GameContext l_gameContext = GameContext.getGameContext();
		l_gameContext.getPlayers().put(l_player2.getName(), l_player2);
		l_player.getCards().add(Card.NEGOTIATE);
		Order l_order = l_player.conventOrder("negotiate p2");
		assert(l_order != null);
		assert(l_player.getCards().contains(Card.NEGOTIATE) == false);
	}
	
	/**
	 * check if failed to generate the Negotiate order Without A Card
	 */
	@Test
	public void willNotCreateNegotiateOrderWithoutACard() {
		Player l_player = new Player("P1");
		Player l_player2 = new Player("p2");
		GameContext l_gameContext = GameContext.getGameContext();
		l_gameContext.getPlayers().put(l_player2.getName(), l_player2);
		Order l_order = l_player.conventOrder("negotiate p2");
		assert(l_order == null);
	}
	
	/**
	 * check if failed to generate the Negotiate order Without A right layerName
	 */
	@Test
	public void willNotCreateNegotiateOrderWithoutAPlayerName() {
		Player l_player = new Player("P1");
		Player l_player2 = new Player("p2");
		GameContext l_gameContext = GameContext.getGameContext();
		l_gameContext.getPlayers().put(l_player2.getName(), l_player2);
		Order l_order = l_player.conventOrder("negotiate p23434");
		assert(l_order == null);
	}
	
	/**
	 * check if order is Valid
	 */
	@Test
	public void willBeValid() {
		Player l_player = new Player("P1");
		Player l_player2 = new Player("p2");
		GameContext l_gameContext = GameContext.getGameContext();
		l_gameContext.getPlayers().put(l_player2.getName(), l_player2);
		l_player.getCards().add(Card.NEGOTIATE);
		Order l_order = l_player.conventOrder("negotiate p2");
		Boolean l_result = l_order.valid();
		assert(l_result);
	}	
	
	/**
	 * check if game engine will Update Context After Execution
	 */
	@Test
	public void willUpdateContextAfterExecution() {
		//arrange
		Player l_player = new Player("P1");
		Player l_player2 = new Player("p2");
		Player l_player3 = new Player("p3");
		GameContext l_gameContext = GameContext.getGameContext();
		l_gameContext.getPlayers().put(l_player2.getName(), l_player2);
		l_player.getCards().add(Card.NEGOTIATE);
		Order l_order = l_player.conventOrder("negotiate p2");
		
		//act
		l_order.execute();
		
		//assert
		assert(l_gameContext.isDiplomacyInCurrentTurn(l_player,l_player2));
		assert(l_gameContext.isDiplomacyInCurrentTurn(l_player2,l_player));	
		assertFalse(l_gameContext.isDiplomacyInCurrentTurn(l_player,l_player3));	
	}
	
	
}
