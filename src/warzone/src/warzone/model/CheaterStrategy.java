package warzone.model;
import warzone.view.GenericView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *	define of the CheaterStrategy
 */
public class CheaterStrategy extends PlayerStrategy implements Serializable {

	/**
	 * save the cheat country list
	 */
	List<Country> l_cheatCountryList;

	/**
	 * constructor of CheaterStrategy
	 * @param p_player given Player
	 */
	CheaterStrategy(Player p_player){
		super(p_player);
		l_cheatCountryList = new ArrayList<>();
	}
	
	/**
	 *  implementation of createOrder
	 * @return null
	 */
	public Order createOrder() {

		//save the immediate neighbor
		for(Country l_country : d_player.getConqueredCountries().values()){
			for( Country l_neighbor : l_country.getNeighbors().values()){
				if(l_neighbor.getOwner() != d_player){
					if(!l_cheatCountryList.contains(l_neighbor))
						l_cheatCountryList.add(l_neighbor);
				}
			}
		}

		//set cheat countries' owner
		for(Country  l_cheat: l_cheatCountryList){
			l_cheat.setCountryState(CountryState.Occupied, d_player);
			GenericView.println("Cheater get the country " + l_cheat.getCountryName());
		}

		//double the army of a country if it has an enemy
		for(Country l_country : d_player.getConqueredCountries().values()){
			for( Country l_neighbor : l_country.getNeighbors().values()) {
				if (l_neighbor.getOwner() != d_player) {
					int l_army = l_country.getArmyNumber();
					l_country.setArmyNumber(l_army * 2);
					GenericView.println("Cheater double the army in country " + l_country.getCountryName());
					break;
				}
			}
		}
		//set player finish the issue order
		d_player.setHasFinisedIssueOrder(true);
		return null;
	}
}
