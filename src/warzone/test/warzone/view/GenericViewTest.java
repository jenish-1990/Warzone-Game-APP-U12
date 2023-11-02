package warzone.view;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * tests for GenericView class
 */
public class GenericViewTest {

    /**
     * test println
     */
    @Test
    public void testPrintln(){
        boolean l_res = GenericView.println("test");
        assertTrue(l_res);
    }
}