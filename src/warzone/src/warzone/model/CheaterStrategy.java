package warzone.model;
import java.util.List;


/**
 *	define of the CheaterStrategy
 */
public class CheaterStrategy extends PlayerStrategy {	
	
	/**
	 * constructor of CheaterStrategy
	 * @param p_player given Player
	 */
	CheaterStrategy(Player p_player){
		super(p_player);
	}
	
	/**
	 *  implementation of createOrder
	 * @return null
	 */
	public Order createOrder() {

		//conquer the immediate neighbor
		for(Country l_country : d_player.getConqueredCountries().values()){
			for( Country l_neighbor : l_country.getNeighbors().values()){
				if(l_neighbor.getOwner() != d_player){
					l_neighbor.setCountryState(CountryState.Occupied, d_player);
				}
			}
		}

		//double the army of a country if it has an enemy
		for(Country l_country : d_player.getConqueredCountries().values()){
			for( Country l_neighbor : l_country.getNeighbors().values()) {
				if (l_neighbor.getOwner() != d_player) {
					int l_army = l_country.getArmyNumber();
					l_country.setArmyNumber(l_army * 2);
					break;
				}
			}
		}
		//set player finish the issue order
		d_player.setHasFinisedIssueOrder(true);
		return null;
	}
}
