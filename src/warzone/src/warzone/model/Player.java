package warzone.model;

import java.util.Map;
import java.util.Queue;


public class Player implements IOrder {

	private String name;
	private Map<Integer, Country> conqueredCountries;
	private Queue<IOrder> orders;
	
	
	/**
	 * The GameEngine class calls the issue_order() method of the Player. This method will wait for the following 
	 * command, then create a deploy order object on the players list of orders, then reduce the number of armies in the 
	 * players reinforcement pool. The game engine does this for all players in round-robin fashion until all the players 
	 * have placed all their reinforcement armies on the map.
	 * 
	 * Issuing order command: deploy countryID num (until all reinforcements have been placed)
	 */
	public void issue_order() {
		
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * The GameEngine calls the next_order() method of the Player. Then the Order objects execute() method is called, 
	 * which will enact the order. 
	 * 
	 * @return
	 */
	public IOrder next_order() {
		
		// TODO Auto-generated method stub
		
		return null;
	}
	
	@Override
	public boolean execute() {
		// pick up the next order and excute
		return false;
	}
}
