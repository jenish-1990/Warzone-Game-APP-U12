package warzone.view;

import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.Render;

import java.util.Map;

public class MapView {

    /**
     * generate the table of continent information
     * continentId & Name, countryId & Name, neighbours
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
                    System.out.println(String.format("%22s", _country.getCountryID()) + " " + _country.getCountryName());
                    for (Country _nCountry : _country.getNeighbors().values())
                        System.out.println(String.format("%35s", _nCountry.getCountryID()) + " " +_nCountry.getCountryName());
                }
        }
    }

    /**
     * generate the table
     * with continentid & name, countryid & name, Ownerplayer & armies
     * @param p_continents map of continent
     */
    public static void printMapWithArmies(Map<Integer, Continent> p_continents) {
        System.out.println("************************************************************");
        System.out.println("**      Continent  -  Country  -  Player  -  Armies       **");
        System.out.println("************************************************************");

        if( p_continents.isEmpty())
            GenericView.println("No continent exist.");

        for (Continent _continent : p_continents.values()) {
            System.out.println(String.format("%-2s", _continent.getContinentID()) + _continent.getContinentName());

            for (Country _country : _continent.getCountries().values()) {
                System.out.println(String.format("%22s", _country.getCountryID()) + " "
                        + String.format("%-10s", _country.getCountryName()) + " "
                        + String.format("%-12s", _country.getOwner().getName())
                        + _country.getDeployedForces());
            }
        }
    }
}
