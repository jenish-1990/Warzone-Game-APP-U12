package warzone.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test cases for HumanStrategy
 * @author zexin
 *
 */
public class HumanStrategyTest {

    /**
     * test if it is a human strategy
     */
    @Test
    public void testIfStrategyCorrect(){

        Player l_player = new Player("p1");

        assertEquals(l_player.getPlayerStrategyType(), PlayerStrategyType.HUMAN);
    }
}
