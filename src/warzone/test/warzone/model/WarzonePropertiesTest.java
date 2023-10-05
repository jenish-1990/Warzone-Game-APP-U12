package warzone.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for WarzoneProperties test
 */
public class WarzonePropertiesTest {

	/**
	 * check if the gamemap directory is not null
	 */
	@Test
	public void warzonePropertiesTest() {

		assertTrue(WarzoneProperties.getWarzoneProperties().getGameMapDirectory() != null);
		System.out.println(WarzoneProperties.getWarzoneProperties().getIsDemoMode());
		System.out.println(WarzoneProperties.getWarzoneProperties().getIsDebug());
	}
}
