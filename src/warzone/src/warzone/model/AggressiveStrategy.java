package warzone.model;
import java.util.List;
import java.util.Random;


/**
 *	define of the AggressiveStrategy
 */
public class AggressiveStrategy extends PlayerStrategy {	
	
	/**
	 * Armies to deploy
	 */
	private int d_armiesToDeploy;

	private boolean d_hasAttack;
	
	/**
	 * constructor of AggressiveStrategy
	 * @param p_player given Player
	 */
	AggressiveStrategy(Player p_player){
		super(p_player);
		d_armiesToDeploy=p_player.getArmiesToDeploy();
		d_hasAttack = false;
	}
	
	/**
	 * Get the conquered country which has maximum army number
	 * @return the strongest conquered country
	 */
	protected Country getStrongestConqueredCountry() {
		Country l_strongestCountry=null;			
		int l_maxArmyNum=-1;
		for(Country l_c:d_player.getConqueredCountries().values()) {
			if(l_c.getArmyNumber()>l_maxArmyNum) {
				l_maxArmyNum=l_c.getArmyNumber();
				l_strongestCountry=l_c;
			}
		}
		return l_strongestCountry;
	}
	
	/**
	 * implementation of createOrder
	 * @return Order
	 */
	public Order createOrder() {
		Order l_order=null;
		if(!d_player.getIsAlive())
			return null;
		//deploy to strongest country
		if(d_armiesToDeploy>0) {
			Country l_strongestCountry=getStrongestConqueredCountry();			
			l_order = new DeployOrder(d_player, l_strongestCountry, d_armiesToDeploy);
			d_armiesToDeploy-=d_armiesToDeploy;
			return l_order;
		}
		if(!d_hasAttack) {
			//attack
			Country l_strongestCountry = getStrongestConqueredCountry();
			for (Country l_c : l_strongestCountry.getNeighbors().values()) {
				if (l_c.getOwner() != d_player && d_player.getArmyNumber() > 2 * l_c.getArmyNumber()) {
					l_order = new AdvanceOrder(d_player, l_strongestCountry, l_c, 2 * l_c.getArmyNumber());
					d_hasAttack = true;
					return l_order;
				}
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
