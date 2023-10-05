package warzone.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import warzone.model.*;
import warzone.view.GenericView;

/**
 * Main game loop.
 * 
 * Loop over each player for the assign reinforcements, issue orders, and execute orders main game loop phases
 * 
 */
public class GameEngine {
	private GameContext d_gameContext;	
	private static GameEngine GAME_ENGINE;

	/**
	 * private constructor
	 * set the game context
	 * @param p_gameContext the game context
	 */
	private GameEngine(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}

	/**
	 * return the game engine
	 * if game engine is not created, then create a new game engine
	 * @param p_gameContext the game context
	 * @return the game engine
	 */
	public static GameEngine getGameEngine(GameContext p_gameContext) {
		if( GAME_ENGINE == null)
			GAME_ENGINE = new GameEngine(p_gameContext);
		return GAME_ENGINE;
	}	

	/**
	 * This method is the entrance of the game. It will initiate the game context and use
	 * command scanner to get the command of the player.
	 * @param args the parameters for Java Virtual Machine
	 * @throws IOException the exception of creating or deleting files
	 */
	public static void main(String[] args) throws IOException {

		GameContext l_gameContext = GameContext.getGameContext();
		RouterService l_routerService =  RouterService.getRouterService(l_gameContext);
		CommandService l_commandService =  CommandService.getCommandService(l_gameContext);
		
		
		//1 welcome
		Router l_welcomeRouter = new Router(ControllerName.COMMON, "welcome");
		l_routerService.route(l_welcomeRouter);
		
		l_commandService.commandScanner(l_routerService);
	}	
	
	/**	
	 * This method will show whether the game can start.	
	 * @return true if the game can start	
	 */
	public boolean isReadyToStart() {
		if(this.d_gameContext == null || this.d_gameContext.getContinents().size() <1 
				|| this.d_gameContext.getCountries().size() < 1 || this.d_gameContext.getPlayers().size() < 1 )
			return false;
		else
			return true;
	}
	/**
	 * change the current game phase
	 * @param p_gamePhase the given game phase
	 */
	public void setGamePhase(GamePhase p_gamePhase) {
		d_gameContext.setGamePhase(p_gamePhase);		
	}
	
	/**
	 * If the game turn is greater than 100, the game will end.
	 * 
	 * @return true if the game can end.
	 */
	public boolean play() {
		
		if(! isReadyToStart())
			return false;
		
		startTurn();
		return true;		
	}
	
	
	/**
	 * This method represent one turn for each player. It contains three steps: 
	 * 1. assigning reinforcements 2. issuing orders 3.executing orders
	 */
	private void startTurn() {	
		GenericView.println("Start to assign reinforcements........");
		assignReinforcements();
		GenericView.println("Start to issue orders........");
		issueOrders();
		GenericView.println("Start to execute orders........");
		executeOrders();		
	}
	
	/**
	 * This method will determine if the game whether can end.
	 * @return true if the current state satisfy the end condition: 
	 * 1. there is just one player left 2. the number of game turn is greater than 100.
	 */
	private boolean isGameEnded() {
		//check and update PlayerStatus		
		//set p_isLoser = true, when the player does not have any country
		int l_alivePlayers = 0;
		for(Player l_player :d_gameContext.getPlayers().values() ){
			if(l_player.getConqueredCountries().size() > 0) {
				l_player.setIsAlive(true);
				l_alivePlayers ++;
			}
		}		
		return l_alivePlayers <= 1;
	}
	

	/**
	 * This method will assign each player the correct number of reinforcement armies 
	 * according to the Warzone rules.
	 */
	private void assignReinforcements() {
		d_gameContext.getPlayers().forEach((k, player) -> {
			if(player.getIsAlive()) {
				GenericView.println("Start to assign reinforcements for player ["+ player.getName() +"]");
				player.assignReinforcements(d_gameContext);
			}
		});
	}
	
	/**
	 * The GameEngine class calls the issue_order() method of the Player. This method will wait for the following command, 
	 * then create a deploy order object on the players list of orders, then reduce the number of armies in the 
	 * players reinforcement pool. The game engine does this for all players in round-robin fashion until all the players 
	 * have placed all their reinforcement armies on the map.
	 */
	private void issueOrders() {

		d_gameContext.getPlayers().forEach((k, player) -> {
			if(player.getIsAlive()) {
				GenericView.println("Start to issue orders for player ["+ player.getName() +"]");
				player.issue_order();
			}
		});			
	}
	
	
	/**
	 * The GameEngine calls the next_order() method of the Player. Then the Order object�s execute() method is called 
	 * which will enact the order. 
	 * 
	 * get the max number of the orders own by a single player
	 * excute the orders from player's order list in round-robin fashion
	 * 
	 */
	private void executeOrders() {		
		//1. get the max number of the orders in a player.		
		int l_maxOrderNumber = 0;	
		for(Player l_player :d_gameContext.getPlayers().values() ){
			if(l_player.getIsAlive()) {
				if( l_player.getOrders().size() > l_maxOrderNumber)
					l_maxOrderNumber = l_player.getOrders().size();				
			}		
		}

		//2. excute the orders
		GenericView.println("Start to execute orders.");
		int l_roundIndex = 1;
		while(l_roundIndex <= l_maxOrderNumber ){
			GenericView.println("Start to execute round [" + l_roundIndex + "] of orders");
			d_gameContext.getPlayers().forEach((k, player) -> {
				if(player.getIsAlive()) {
					Order l_order = player.next_order();
					if(l_order != null)
						l_order.execute();
				}				
			});
			l_roundIndex ++;			
		}	
		
	}	 
}