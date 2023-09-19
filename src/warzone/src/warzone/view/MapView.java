package warzone.view;

import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.Render;

import java.util.Map;

public class MapView {

    /**
     * generate the table of continent information
     * continentId and continentName
     *                              countryId, countryName,
     *                                                      neighbours
     * @param p_continents map of continent
     */
    public static void printMap(Map<Integer, Continent> p_continents) {

        System.out.println("****************************************************");
        System.out.println("**         Continent - Country - neighbors        **");
        System.out.println("****************************************************");

        if( p_continents.isEmpty())
            GenericView.println("No continent exist.");

        for (Continent _continent : p_continents.values()) {
            System.out.println(String.format("%-2s", _continent.getContinentID()) + _continent.getContinentName());

                for (Country _country : _continent.getCountries().values()) {
                    System.out.println(String.format("%25s", _country.getCountryID()) + " " + _country.getCountryName());
                    for (Country _nCountry : _country.getNeighbors().values())
                        System.out.println(String.format("%50s", _nCountry.getCountryID()) + " " +_nCountry.getCountryName());
                }
        }
    }
}
