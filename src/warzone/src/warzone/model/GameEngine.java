package warzone.model;

/**
 * Main game loop.
 * 
 * Loop over each player for the assign reinforcements, issue orders, and execute orders main game loop phases
 * 
 */
public class GameEngine {

	
	
	
	public static void main(String[] commands) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Assign each player the correct number of reinforcement armies according to the Warzone rules.
	 */
	private void assignReinforcements() {
		
		//This may not need to be its own method
	}
	
	/**
	 * The GameEngine class calls the issue_order() method of the Player. This method will wait for the following command, 
	 * then create a deploy order object on the player’s list of orders, then reduce the number of armies in the 
	 * player’s reinforcement pool. The game engine does this for all players in round-robin fashion until all the players 
	 * have placed all their reinforcement armies on the map.
	 */
	private void issueOrders() {
		
		//This may not need to be its own method
	}
	
	
	/**
	 * The GameEngine calls the next_order() method of the Player. Then the Order object’s execute() method is called 
	 * which will enact the order. 
	 */
	private void executeOrders() {
		
		//This may not need to be its own method
	}

}
