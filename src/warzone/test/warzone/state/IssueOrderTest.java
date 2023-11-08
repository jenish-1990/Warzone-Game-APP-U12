package warzone.state;

import org.junit.Test;

import warzone.model.GameContext;
import warzone.model.GamePhase;
import warzone.model.Router;
import warzone.service.GameEngine;

/** 
 * Test class for issue order phase
 */
public class IssueOrderTest {
	
	/**
	 *  test for input Next Command 
	 */
	@Test
	public void inputNextCommand() {
		GameContext l_gameContext = GameContext.getGameContext();
		GameEngine l_gameEngine = GameEngine.getGameEngine(l_gameContext);
		l_gameContext.setCurrentRouter(new Router(null, null, null));
		IssueOrder l_issueOrderState = new IssueOrder(l_gameEngine);
		l_gameEngine.setPhase(l_issueOrderState);
		l_issueOrderState.next();
		assert(l_gameEngine.getPhase().getGamePhase()==GamePhase.OrderExecution);
	}
	
}
