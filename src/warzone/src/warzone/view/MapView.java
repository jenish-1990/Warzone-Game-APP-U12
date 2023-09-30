package warzone.view;

import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.Render;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class can print the map in the command line
 *
 */
public class MapView {

    /**
     * generate the table of continent information
     * continentId, Name, countryId, Name, neighbours
     * @param p_continents map of continent
     */
    public static void printMap(GameContext p_gameContext) {

        System.out.println("****************************************************");
        System.out.println("**         Continent - Country - neighbors        **");
        System.out.println("****************************************************");

        if( p_gameContext.getContinents().isEmpty())
            GenericView.println("No continent exist.");
        //int[] l_countryKeys = p_gameContext.getCountries().keySet().to.toArray( new int[0]);
        Set<Integer> l_countryKeys = new HashSet<>();
        l_countryKeys.addAll(p_gameContext.getCountries().keySet());
        //Object[] l_countryKeys = p_gameContext.getCountries().keySet().toArray(); 

        for (Continent l_continent : p_gameContext.getContinents().values()) {
            System.out.println(String.format("%-2s", l_continent.getContinentID()) + l_continent.getContinentName());

                for (Country l_country : l_continent.getCountries().values()) {
                	l_countryKeys.remove(l_country.getCountryID());
                    System.out.println(String.format("%22s", l_country.getCountryID()) + " " + l_country.getCountryName());
                    for (Country _nCountry : l_country.getNeighbors().values())
                        System.out.println(String.format("%35s", _nCountry.getCountryID()) + " " +_nCountry.getCountryName());
                }
        }
        if(l_countryKeys.size() > 0) {
        	System.out.println(String.format("**    [%s]  Orphans Country - neighbors        **", l_countryKeys.size()));
        	for( int l_countryKey : l_countryKeys) {
        		Country l_country = p_gameContext.getCountries().get(l_countryKey);
        		 System.out.println(String.format("%22s", l_country.getCountryID()) + " " + l_country.getCountryName());
                 for (Country _nCountry : l_country.getNeighbors().values())
                     System.out.println(String.format("%35s", _nCountry.getCountryID()) + " " +_nCountry.getCountryName());
        	}        	
        }        
    }

    /**
     * generate the table
     * with continentid, name, countryid, name, Ownerplayer. armies
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
