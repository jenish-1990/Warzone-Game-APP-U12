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
     * @param p_gameContext the game context
     */
    public static void printMap(GameContext p_gameContext) {

        System.out.println("**************************************************************************");
        System.out.println("**          Continent[Bonus Army]  -  Country  -  neighbors             **");
        System.out.println("**************************************************************************");

        if( p_gameContext.getContinents().isEmpty())
            GenericView.println("No continent exist.");

        Set<Integer> l_countryKeys = new HashSet<>();
        l_countryKeys.addAll(p_gameContext.getCountries().keySet());

        //printout table
        for (Continent l_continent : p_gameContext.getContinents().values()) {
            System.out.println(String.format("%-2s", l_continent.getContinentID()) + l_continent.getContinentName() + "[" + l_continent.getBonusReinforcements() + "]");

                for (Country l_country : l_continent.getCountries().values()) {
                	l_countryKeys.remove(l_country.getCountryID());
                    System.out.println(String.format("%22s", l_country.getCountryID()) + " " + l_country.getCountryName());
                    for (Country l_nCountry : l_country.getNeighbors().values())
                        System.out.println(String.format("%35s", l_nCountry.getCountryID()) + " " +l_nCountry.getCountryName());
                }
        }
        //printout Orphans Country
        if(l_countryKeys.size() > 0) {
        	System.out.println(String.format("**    [%s]  Orphans Country - neighbors        **", l_countryKeys.size()));
        	for( int l_countryKey : l_countryKeys) {
        		Country l_country = p_gameContext.getCountries().get(l_countryKey);
        		 System.out.println(String.format("%22s", l_country.getCountryID()) + " " + l_country.getCountryName());
                 for (Country l_nCountry : l_country.getNeighbors().values())
                     System.out.println(String.format("%35s", l_nCountry.getCountryID()) + " " +l_nCountry.getCountryName());
        	}        	
        }        
    }

    /**
     * generate the table
     * with continentid, name, countryid, name, Ownerplayer. armies
     * @param p_continents map of continent
     */
    public static void printMapWithArmies(Map<Integer, Continent> p_continents) {
        System.out.println("************************************************************************");
        System.out.println("**      Continent[Bonus Army]  -  Country  -  Player  -  Armies       **");
        System.out.println("************************************************************************");

        if( p_continents.isEmpty())
            GenericView.println("No continent exist.");

        for (Continent l_continent : p_continents.values()) {
            System.out.println(String.format("%-2s", l_continent.getContinentID()) + l_continent.getContinentName() + "[" + l_continent.getBonusReinforcements() + "]");

            for (Country l_country : l_continent.getCountries().values()) {
                System.out.println(String.format("%22s", l_country.getCountryID()) + " "
                        + String.format("%-20s", l_country.getCountryName()) + " "
                        + String.format("%-12s", (l_country.getOwner()==null ? "": l_country.getOwner().getName()))
                        + l_country.getArmyNumber());

            }
        }
    }
}
