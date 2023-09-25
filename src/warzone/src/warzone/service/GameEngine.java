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
	private GameContext d_gameContext;
	
	public GameEngine(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}

	public static void main(String[] args) throws IOException {

		RouterService d_RouterService;
		d_RouterService = new RouterService();
		
		//1 welcome
		Router welcomeRouter = new Router(ControllerName.COMMON, "welcome");
		d_RouterService.route(welcomeRouter);
		
//		Router tempRouter = new Router(ControllerName.GAMEPLAY, "play");
//		d_RouterService.route(tempRouter);
		
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
	
	
	public boolean play() {
		int l_loopNumber = 1;		
		while( !isGameEnded() && l_loopNumber <= 100) {
			startTurn();
			l_loopNumber ++;
		}
		
		return isGameEnded();
	}
	
	
	private void startTurn() {		
		assignReinforcements();
		issueOrders();
		executeOrders();		
	}
	
	private boolean isGameEnded() {
		//check and update PlayerStatus		
		//set p_isLoser = true, when the player does not have any country
		int l_survivedPlayerNumber = 0;
		for(Player l_player :d_gameContext.getPlayers().values() ){
			if(l_player.getConqueredCountries().size() == 0) {
				l_player.setIsSurvived(false);
				l_survivedPlayerNumber ++;
			}
		}		
		return l_survivedPlayerNumber <= 1;
	}
	

	/**
	 * Assign each player the correct number of reinforcement armies according to the Warzone rules.
	 */
	private void assignReinforcements() {
		d_gameContext.getPlayers().forEach((k, player) -> {
			if(player.getIsSurvived())
				player.assignReinforcements();
		});
	}
	
	/**
	 * The GameEngine class calls the issue_order() method of the Player. This method will wait for the following command, 
	 * then create a deploy order object on the players list of orders, then reduce the number of armies in the 
	 * players reinforcement pool. The game engine does this for all players in round-robin fashion until all the players 
	 * have placed all their reinforcement armies on the map.
	 */
	private void issueOrders() {
		int l_number= 0;		
		while(l_number < d_gameContext.getOrderNumberPerRound() ){
			d_gameContext.getPlayers().forEach((k, player) -> {
				if(player.getIsSurvived())
					player.issue_order();
			});
			l_number ++;			
		}	
	}
	
	
	/**
	 * The GameEngine calls the next_order() method of the Player. Then the Order object�s execute() method is called 
	 * which will enact the order. 
	 */
	private void executeOrders() {
		// run excute() for each order,  5 rounds
		int l_number = 0;		
		while(l_number < d_gameContext.getOrderNumberPerRound() ){
			d_gameContext.getPlayers().forEach((k, player) -> {
				if(player.getIsSurvived()) {
					Order l_order = player.next_order();
					if(l_order != null)
						l_order.execute();
				}				
			});
			l_number ++;			
		}		
	}
	
	 
}
