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
	 * @return
	 */
	public Order createOrder() {
		Order l_order = null;
		
		//todo: implement it with real order according to the spec
		Country l_country = this.d_player.getConqueredCountries().entrySet().iterator().next().getValue();
		l_order = new DeployOrder(this.d_player, l_country, 1);
		
		
		return l_order;
	}
}
