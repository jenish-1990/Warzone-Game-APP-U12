package warzone.model;

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
	List<Country> d_cheatCountryList;
	
	/**
	 * log entry buffer
	 */
	private LogEntryBuffer d_logEntryBuffer;

	/**
	 * constructor of CheaterStrategy
	 * @param p_player given Player
	 */
	CheaterStrategy(Player p_player){
		super(p_player);
		d_cheatCountryList = new ArrayList<>();
		d_logEntryBuffer = GameContext.getGameContext().getLogEntryBuffer();
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
					if(!d_cheatCountryList.contains(l_neighbor))
						d_cheatCountryList.add(l_neighbor);
				}
			}
		}

		//set cheat countries' owner
		for(Country  l_cheat: d_cheatCountryList){
			l_cheat.setCountryState(CountryState.Occupied, d_player);
			d_logEntryBuffer.logAction("SUCCESS", "Cheater get the country " + l_cheat.getCountryName());
		}

		//double the army of a country if it has an enemy
		for(Country l_country : d_player.getConqueredCountries().values()){
			for( Country l_neighbor : l_country.getNeighbors().values()) {
				if (l_neighbor.getOwner() != d_player) {
					int l_army = l_country.getArmyNumber();
					l_country.setArmyNumber(l_army * 2);
					d_logEntryBuffer.logAction("SUCCESS", "Cheater double the army in country " + l_country.getCountryName());
					break;
				}
			}
		}

		//check if cheater win this game
		d_gameEngine.isGameEnded(false);

		//set player finish the issue order
		d_player.setHasFinisedIssueOrder(true);
		return null;
	}
}
