package warzone.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class WarzonePropertiesTest {

	@Test
	public void warzonePropertiesTest() {

		assertTrue(WarzoneProperties.getWarzoneProperties().getGameMapDirectory() != null);
		System.out.println(WarzoneProperties.getWarzoneProperties().getIsDemoMode());
		System.out.println(WarzoneProperties.getWarzoneProperties().getIsDebug());
	}
}
