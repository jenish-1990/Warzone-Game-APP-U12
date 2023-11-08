package warzone.service;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import warzone.model.*;
import warzone.state.Startup;
import warzone.state.MapEditor;
import warzone.state.Phase;
import warzone.view.GenericView;
import warzone.view.HelpView;
import warzone.view.MapView;

/**
 * Main game loop.
 * 
 * Loop over each player for the assign reinforcements, issue orders, and execute orders main game loop phases
 * 
 */
public class GameEngine {
	
	/**
	 * This method is the entrance of the game. It will initiate the game context and use
	 * command scanner to get the command of the player.
	 * @param args the parameters for Java Virtual Machine
	 * @throws IOException the exception of creating or deleting files
	 */
	public static void main(String args[]) {
		GameContext l_gameContext = GameContext.getGameContext();
		GameEngine l_gameEngine = getGameEngine(l_gameContext);
		l_gameEngine.setPhase(new MapEditor(l_gameEngine));
		l_gameEngine.start();
	}
	
	
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
	 * State object of the GameEngine 
	 */
	private Phase d_gamePhase ;
	
	/**
	 * get  State of the Game 
	 * @return State of the Game 
	 */
	public Phase getPhase() {
		return d_gamePhase;
	}
	
	/**
	 * get  State of the Game Context
	 * @return State of the Game  Context
	 */
	public GameContext getGameContext() {
		return d_gameContext;
	}	
	
	/**
	 * Method that allows the GameEngine object to change its state.  
	 * @param p_phase new state to be set for the GameEngine object.
	 */
	public void setPhase(Phase p_phase) {
		d_gamePhase = p_phase;
		System.out.println("new phase: " + p_phase.getClass().getSimpleName());
	}
	
	/**
	 * This method will ask the user: 
	 * 1. What part of the game they want to start with (edit map or play game). 
	 *      Depending on the choice, the state will be set to a different object, 
	 *      which will set different behavior. 
	 * 2. What command they want to execute from the game. 
	 *      Depending on the state of the GameEngine, each command will potentially 
	 *      have a different behavior. 
	 */
	public void start() {
		RouterService l_routerService =  RouterService.getRouterService(this);
		CommandService l_commandService =  CommandService.getCommandService(this );		
		
		//1 welcome
		HelpView.printWelcome();
		
		l_commandService.commandScanner(l_routerService);		
	}	
	
	/**	
	 * This method will show whether the game can start.	
	 * @return true if the game can start	
	 */
	public boolean isReadyToStart() {
		if(this.d_gameContext == null || this.d_gameContext.getContinents().size() <1 
				|| this.d_gameContext.getCountries().size() < 1 || this.d_gameContext.getPlayers().size() < 1
				|| (this.d_gameContext.getCountries().size() < this.d_gameContext.getPlayers().size()) )
			return false;
		else {
			for(Player p_player : this.d_gameContext.getPlayers().values() ) {
				if(p_player.getConqueredCountries().size() == 0)
					return false;
			}
			
		}
		return true;
	}
	
	/**
	 * If the game turn is greater than 100, the game will end.
	 * 
	 * @return true if the game can end.
	 */
	public boolean play() {
		
		if(! isReadyToStart())
			return false;
		if(this.d_gameContext.getIsDemoMode())
			startTurn();
		else {
			while(!isGameEnded())
				startTurn();
		}
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
	public boolean isGameEnded() {
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
	public void assignReinforcements() {
		if( isGameEnded()) {
			//todo: call game over and change state
		}
		
		d_gameContext.getPlayers().forEach((l_k, l_player) -> {
			if(l_player.getIsAlive()) {
				GenericView.println("Start to assign reinforcements for player ["+ l_player.getName() +"]");
				l_player.assignReinforcements(d_gameContext);
			}
		});
		
	}
	
	/**
	 * The GameEngine class calls the issue_order() method of the Player. This method will wait for the following command, 
	 * then create a deploy order object on the players list of orders, then reduce the number of armies in the 
	 * players reinforcement pool. The game engine does this for all players in round-robin fashion until all the players 
	 * have placed all their reinforcement armies on the map.
	 */
	public void issueOrders() {
		if( isGameEnded()) {
			//todo: call game over and change state
		}		

		//local list of player
		List<Player> l_playersList = new ArrayList<>();
		d_gameContext.getPlayers().forEach((l_k, l_player) -> {
			l_player.setHasFinisedIssueOrder(false);
			l_playersList.add(l_player);
		});

		while(l_playersList.size() > 0){
			for(Player l_player : l_playersList){
				if(l_player.getIsAlive()) {
					GenericView.println("Start to issue orders for player ["+ l_player.getName() +"]");
					l_player.issue_order();
				}
				//if player finished, remove from the list
				if(l_player.getHasFinisedIssueOrder())
					l_playersList.remove(l_player);
			}
		}
	}
	
	
	/**
	 * The GameEngine calls the next_order() method of the Player. Then the Order object�s execute() method is called 
	 * which will enact the order. 
	 * <ol>
	 * <li>get the max number of the orders own by a single player</li>
	 * <li>excute the orders from player's order list in round-robin fashion</li>
	 * </ol>
	 */
	public void executeOrders() {	
		if( isGameEnded()) {
			//todo: call game over and change state
		}	
		
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
			if( isGameEnded()) {
				//todo: call game over and change state
			}	
			
			GenericView.println("Start to execute round [" + l_roundIndex + "] of orders");
			d_gameContext.getPlayers().forEach((l_k, l_player) -> {
				if(l_player.getIsAlive()) {
					Order l_order = l_player.next_order();
					if(l_order != null){
						l_order.execute();
						MapView.printMapWithArmies(d_gameContext.getContinents());
					}
				}				
			});
			l_roundIndex ++;			
		}
	}	 
}
