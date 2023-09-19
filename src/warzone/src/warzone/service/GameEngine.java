package warzone.service;

import java.io.IOException;

import warzone.model.*;

/**
 * Main game loop.
 * 
 * Loop over each player for the assign reinforcements, issue orders, and execute orders main game loop phases
 * 
 */
public class GameEngine {
	
	public GameEngine()	{}
	
	public static void main(String[] args) throws IOException {

		RouterService d_RouterService;
		d_RouterService = new RouterService();
		
		//1 welcome
		Router welcomeRouter = new Router(ControllerName.COMMON, "welcome");
		d_RouterService.route(welcomeRouter);
		
//		Router saveMapRouter = new Router(ControllerName.MAP, "saveMap","map-na");
//		d_RouterService.route(saveMapRouter);


//		Router showMapRouter = new Router(ControllerName.MAP, "showMap");
//		d_RouterService.route(showMapRouter);
		
//		Router addContinentRouter = new Router(ControllerName.CONTINENT, "add", "1 veu");
//		d_RouterService.route(addContinentRouter);



		//2 init the game
		//3 standby
		//System.in.read();
//		while(System.IO.Readline()) {
//			//parse the command to a router
//			//route the router
////			Router welcome = new Router(ControllerName.COMMON, "welcome");
////			d_RouterService.route(welcome);
//		}
	}

	/**
	 * Assign each player the correct number of reinforcement armies according to the Warzone rules.
	 */
	private void assignReinforcements() {
		
		//This may not need to be its own method
	}
	
	/**
	 * The GameEngine class calls the issue_order() method of the Player. This method will wait for the following command, 
	 * then create a deploy order object on the players list of orders, then reduce the number of armies in the 
	 * players reinforcement pool. The game engine does this for all players in round-robin fashion until all the players 
	 * have placed all their reinforcement armies on the map.
	 */
	private void issueOrders() {
		
		//This may not need to be its own method
	}
	
	
	/**
	 * The GameEngine calls the next_order() method of the Player. Then the Order object�s execute() method is called 
	 * which will enact the order. 
	 */
	private void executeOrders() {
		// run excute() for each order,  5 rounds
		//This may not need to be its own method
	}

}
