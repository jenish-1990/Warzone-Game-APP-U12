package warzone.model;
import java.util.List;


/**
 *	Strategy of the Strategy pattern
 */
public abstract class PlayerStrategy {	

	/**
	 * the owner player
	 */
	Player d_player; 
	

	/**
	 *  abstract of createOrder
	 * @return Order
	 */
	public abstract Order createOrder();	
	
	/**
	 * constructor of 
	 * @param p_player PlayerStrategy
	 */
	PlayerStrategy(Player p_player){
		d_player = p_player; 
	}
}
