package warzone.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * tests for Country class
 */
public class CountryTest {

    Country d_country;
    Player d_player;

    /**
     * test case for check whether setOwner success
     */
    @Test
    public void testSetOwnerSuccess(){
        d_country = new Country(1,"canada");
        d_player = new Player("jack");
        d_country.setOwner(d_player);
        assertEquals(d_country.getOwner(),d_player);
        assertEquals(d_player.getConqueredCountries().containsValue(d_country),true);
    }

}