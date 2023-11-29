package warzone.model;
import java.io.Serializable;
import java.util.List;
import java.util.Random;


/**
 *	define of the AggressiveStrategy
 */
public class AggressiveStrategy extends PlayerStrategy implements Serializable {

	/**
	 *  a random number
	 */
	private static Random l_rand = new Random();

	/**
	 * if has attack a country
	 */
	private boolean d_hasAttack;

	/**
	 * strongest country
	 */
	private Country l_strongestCountry = null;
	/**
	 * constructor of AggressiveStrategy
	 * @param p_player given Player
	 */
	AggressiveStrategy(Player p_player){
		super(p_player);
		d_hasAttack = false;
		l_strongestCountry = getStrongestConqueredCountry();
	}
	
	/**
	 * Get the conquered country which has maximum army number
	 * @return the strongest conquered country
	 */
	protected Country getStrongestConqueredCountry() {
		Country l_returnCountry=null;
		int l_maxArmyNum=-1;
		for(Country l_c:d_player.getConqueredCountries().values()) {
			if(l_c.getArmyNumber()>l_maxArmyNum) {
				l_maxArmyNum=l_c.getArmyNumber();
				l_returnCountry=l_c;
			}
		}
		return l_returnCountry;
	}

	/**
	 * Get a random country owned by the player except the except country
	 * @param p_exceptCountry except country
	 * @return random country owned by the player
	 */
	protected Country getRandomConqueredCountry(Country p_exceptCountry) {
		if(d_player.getConqueredCountries().size() <= 1) return null;

		int l_idx=l_rand.nextInt(d_player.getConqueredCountries().size());
		Country l_randomCountry;
		do {
			l_randomCountry = (Country) d_player.getConqueredCountries().values().toArray()[l_idx];
		} while (l_randomCountry != p_exceptCountry);

		return l_randomCountry;
	}

	/**
	 * implementation of createOrder
	 * @return Order
	 */
	public Order createOrder() {
		Order l_order=null;
		if(!d_player.getIsAlive())
			return null;

		//deploy and get the strongest country
		if(d_player.getArmiesToDeploy() - d_player.d_armyHasIssued > 0) {
			l_strongestCountry = getStrongestConqueredCountry();
			l_order = new DeployOrder(d_player, l_strongestCountry, d_player.getArmiesToDeploy());
			d_hasAttack = false;
			return l_order;
		}
		//if have a airlift card, airlift army to its strongest country
		if(d_player.getCards().size() > 0){
			for(Card l_card: d_player.getCards()){
				if (l_card == Card.AIRLIFT && d_player.getConqueredCountries().size() > 1) {
					Country l_randomCountry = getRandomConqueredCountry(l_strongestCountry);
					l_order = new AirliftOrder(d_player, l_randomCountry, l_strongestCountry, l_randomCountry.getArmyNumber());
					return l_order;
				}
			}
		}

		if(!d_hasAttack) {
			d_hasAttack = true;
			//if has enemy, attack
			for (Country l_c : l_strongestCountry.getNeighbors().values()) {
				if (l_c.getOwner() != d_player) {
					l_order = new AdvanceOrder(d_player, l_strongestCountry, l_c, l_strongestCountry.getArmyNumber() + d_player.d_armyHasIssued);
					return l_order;
				}
			}
			//has no enemy, move the army to next country
			for (Country l_c : l_strongestCountry.getNeighbors().values()) {
				l_order = new AdvanceOrder(d_player, l_strongestCountry, l_c, l_strongestCountry.getArmyNumber() + d_player.d_armyHasIssued);
				return l_order;
			}
		}

		//after attack, aggregate the force
		for (Country l_countryFrom : this.d_player.getConqueredCountries().values()) {
			for (Country l_countryTo : l_countryFrom.getNeighbors().values()){
				if(l_countryFrom.getOwner() == l_countryTo.getOwner() && l_countryFrom.getArmyNumber() > 0){
					if(l_countryTo.getArmyNumber() > l_countryFrom.getArmyNumber()){
						l_order = new AdvanceOrder(this.d_player, l_countryFrom, l_countryTo, l_countryFrom.getArmyNumber());
						return l_order;
					}
				}
			}
		}
		d_player.setHasFinisedIssueOrder(true);
		return null;
	}

}
