package warzone.service;

import warzone.model.*;
import warzone.state.IssueOrder;
import warzone.state.MapEditor;
import warzone.state.OrderExecution;
import warzone.state.Phase;
import warzone.view.GenericView;
import warzone.view.HelpView;
import warzone.view.MapView;
import warzone.view.TournamentResultsView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Main game loop.
 * 
 * Loop over each player for the assign reinforcements, issue orders, and execute orders main game loop phases
 * 
 */
public class GameEngine implements Serializable {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * This method is the entrance of the game. It will initiate the game context and use
	 * command scanner to get the command of the player.
	 * @param args the parameters for Java Virtual Machine
	 */
	public static void main(String args[]) {
		GameContext l_gameContext = GameContext.getGameContext();
		GameEngine l_gameEngine = getGameEngine(l_gameContext);
		l_gameEngine.setPhase(new MapEditor(l_gameEngine));
		l_gameEngine.start();
	}

	/**
	 * game context
	 */
	private GameContext d_gameContext;
	
	/**
	 * tournament context
	 */
	private TournamentContext d_tournamentContext;
	
	/**
	 * tournament mode boolean
	 */
	private boolean d_isInTournamentMode;
	
	/**
	 * game engine
	 */
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
	 * load game context
	 * @param p_gameContext game context
	 */
	public void loadGameContext(GameContext p_gameContext){
		if(p_gameContext != null) {
			d_gameContext = p_gameContext;
			GameContext.setGameContext(p_gameContext);
		}
	}
	/**
	 * Method that allows the GameEngine object to change its state.  
	 * @param p_phase new state to be set for the GameEngine object.
	 */
	public void setPhase(Phase p_phase) {
		d_gamePhase = p_phase;
		p_phase.refresh(this);
		System.out.println("new phase: " + p_phase.getClass().getSimpleName());
	}
	
	public boolean getIsInTournamentMode() {
		
		return d_isInTournamentMode;
	}
	
	public void setIsInTournamentMode(boolean p_isInTournamentMode) {
		
		this.d_isInTournamentMode = p_isInTournamentMode;
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
		
		d_tournamentContext = TournamentContext.getTournamentContext();
		
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
				|| this.d_gameContext.getCountries().size() < 2 || this.d_gameContext.getPlayers().size() < 2
				|| (this.d_gameContext.getCountries().size() < this.d_gameContext.getPlayers().size()) ) {
			GenericView.printWarning("The number of Country, player and continent is not satisfy the requirement, game can't start.");
			return false;
		}
		else {
			for(Player p_player : this.d_gameContext.getPlayers().values() ) {
				if(p_player.getConqueredCountries().size() == 0) {
					GenericView.printWarning("One of the player does not have a country, game can't start.");
					return false;
				}
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
	
	private int d_mapIndex;
	private int d_gameIndex;
	
	/**
	 * Loop for tournament games
	 * 
	 * @return true if the game can end.
	 */
	public boolean playTournament() {
		
		int l_turnCounter; 
		
		d_tournamentContext.prepareResultsTable();
		
		for(d_mapIndex = 0; d_mapIndex < d_tournamentContext.getMapFiles().size(); d_mapIndex++) {
			
			for(d_gameIndex = 0; d_gameIndex < d_tournamentContext.getNumberOfGames(); d_gameIndex++) {
			
				l_turnCounter = 0;
				
				//Prepare the current game context
				prepareGameContextForTournamentMatch(d_tournamentContext.getMapFiles().get(d_mapIndex));
				
				while(l_turnCounter < d_tournamentContext.getMaxTurns() && !isGameEnded()) {
						
					startTurn();
					l_turnCounter++;
				}
				if(l_turnCounter >= d_tournamentContext.getMaxTurns()) {
					
					d_tournamentContext.getResults()[d_mapIndex][d_gameIndex] = "Draw";
				}
			}
		}
		
		TournamentResultsView.printTournamentResults(d_tournamentContext);
		
		return true;		
	}
	
	private void prepareGameContextForTournamentMatch(String p_mapFileName) {
		
		d_gameContext.reset();
		StartupService startupService = new StartupService(d_gameContext);
		
		startupService.loadMap(p_mapFileName);
		
		int playerNameIndex = 1;
		for(PlayerStrategyType playerStrategyType : d_tournamentContext.getPlayerStrategies()) {
			
			startupService.addPlayer(new Player(playerStrategyType.toString() + playerNameIndex, playerStrategyType));
			playerNameIndex++;
		}
		
		startupService.assignCountries();
	}
	
	
	/**
	 * This method represent one turn for each player. It contains three steps: 
	 * 1. assigning reinforcements 2. issuing orders 3.executing orders
	 */
	private void startTurn() {
		setPhase(new IssueOrder(this));
		GenericView.println("--------------------Start to assign reinforcements");
		assignReinforcements();
		GenericView.println("--------------------Start to issue orders");
		issueOrders();
		GenericView.println("--------------------Start to execute orders");
		executeOrders();
		GenericView.println("--------------------Start to assign cards randomly");
		assignCards();
	}
	
	/**
	 * This method will determine if the game whether can end.
	 * @return true if the current state satisfy the end condition: 
	 * 1. there is just one player left 2. the number of game turn is greater than 100.
	 */
	public boolean isGameEnded() {
		if(this.d_gamePhase.getGamePhase() == GamePhase.MAPEDITOR)
			return false;
		
		//check and update PlayerStatus		
		//set p_isLoser = true, when the player does not have any country
		int l_alivePlayers = 0;
		Player l_protentialWinner = null;
		for(Player l_player :d_gameContext.getPlayers().values() ){
			if(l_player.getConqueredCountries().size() > 0) {
				l_player.setIsAlive(true);
				l_protentialWinner = l_player;
				l_alivePlayers ++;
			}
		}
		if(l_alivePlayers <= 1){
			GenericView.println("-------------------- Game End");
			if(l_alivePlayers == 1) {
				
				GenericView.printSuccess("player " + l_protentialWinner.getName() + " wins the game.");
				
				if(d_gameContext.getIsTournamentMode() == true) {
					
					d_tournamentContext.getResults()[d_mapIndex][d_gameIndex] = l_protentialWinner.getName();
				}
			}
			else {
				GenericView.printSuccess("All the player died.");
				
				if(d_gameContext.getIsTournamentMode() == true) {

					d_tournamentContext.getResults()[d_mapIndex][d_gameIndex] = "Draw";
				}
			}
			GenericView.println("-------------------- Reboot the game");
			this.reboot();
			return true;
		}
		else
			return false;
	}

	/**
	 * This method will assign each player the correct number of reinforcement armies 
	 * according to the Warzone rules.
	 */
	public void assignReinforcements() {
		if( isGameEnded()) {
			GenericView.println("Game is ended.");
			return ;
		}
		GenericView.println("-------------------- Start to assign reinforcements");
		d_gameContext.getPlayers().forEach((l_k, l_player) -> {
			if(l_player.getIsAlive()) {
				GenericView.println("---------- Start to assign reinforcements for player ["+ l_player.getName() +"]");
				l_player.assignReinforcements();
			}
		});
		GenericView.println("-------------------- Finish assigning reinforcements");		
		
	}
	
	/***
	 * add card for all Alive players
	 */
	public void addCardsForAllAlivePlayers() {
		d_gameContext.getPlayers().forEach((l_k, l_player) -> {			
			if(l_player.getIsAlive()) {
				if( !l_player.getCards().contains(Card.AIRLIFT))
					l_player.addCard(Card.AIRLIFT);
				if( !l_player.getCards().contains(Card.BLOCKADE))
					l_player.addCard(Card.BLOCKADE);
				if( !l_player.getCards().contains(Card.BOMB))
					l_player.addCard(Card.BOMB);
				if( !l_player.getCards().contains(Card.NEGOTIATE))
					l_player.addCard(Card.NEGOTIATE);
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
			return;
		}		

		//local list of player
		List<Player> l_playersList = new ArrayList<>();
		d_gameContext.getPlayers().forEach((l_k, l_player) -> {			
			if(l_player.getIsAlive()) {
				l_player.setHasFinisedIssueOrder(false);
				l_playersList.add(l_player);
			}
		});
		List<Player> l_finishPlayerlist = new ArrayList<>();
		

		if(l_playersList.size() > 0) {
			GenericView.println(String.format("-------------------- Start to issue orders"));
			do{
				l_finishPlayerlist.clear();
				
				for (Player l_player : l_playersList) {
					if (l_player.getIsAlive() && !l_player.getHasFinisedIssueOrder() ) {
						GenericView.println("---------- Start to issue orders for player [" + l_player.getName() + "]");
						l_player.issue_order();
					}
					//if player finished, add to the list
					if (l_player.getHasFinisedIssueOrder())
						l_finishPlayerlist.add(l_player);
				}
			}while (l_finishPlayerlist.size() != l_playersList.size() );
			GenericView.println("-------------------- Finish issuing orders for this turn");
		}
		
		
		
		//call the order execution
		this.setPhase(new OrderExecution(this));

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
			return;
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
		GenericView.println("-------------------- Start to execute orders.");
		
		d_gameContext.resetDiplomacyOrderList();
		int l_roundIndex = 1;
		while(l_roundIndex <= l_maxOrderNumber ){
			if( isGameEnded()) {
				break;
			}	
			
			GenericView.println("---------- Start to execute round [" + l_roundIndex + "] of orders");
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
		
		GenericView.println("-------------------- Finish executing orders for this turn");
	}	
	
	/**
	 * This method will assign a random card to each player that conquered a country this turn
	 */
	public void assignCards() {

		d_gameContext.getPlayers().forEach((l_playerID, l_player) -> {

			if(l_player.getConqueredACountryThisTurn()) {

				Random l_randomNumberGenerator = new Random();
				int l_randomNumber = l_randomNumberGenerator.nextInt(4);

				if(l_randomNumber == 0) {

					l_player.getCards().add(Card.BOMB);
					GenericView.println(l_player.getName() + " was assigned a BOMB card.");
				}
				else if(l_randomNumber == 1) {

					l_player.getCards().add(Card.BLOCKADE);
					GenericView.println(l_player.getName() + " was assigned a BLOCKADE card.");
				} 
				else if(l_randomNumber == 2) {

					l_player.getCards().add(Card.AIRLIFT);
					GenericView.println(l_player.getName() + " was assigned an AIRLIFT card.");
				}
				else if(l_randomNumber == 3) {

					l_player.getCards().add(Card.NEGOTIATE);
					GenericView.println(l_player.getName() + " was assigned a NEGOTIATE card.");
				}

				l_player.setConqueredACountryThisTurn(false);
			}
		});
	}
	
	/**
	 * reboot the game
	 */
	public void reboot() {
		d_gameContext.reset();
		setPhase( new MapEditor(this));
	}
	
	/**
	 * 1)reset the context
	 * 2) read commands from a file and run it sequencially
	 * @param p_fileName given file name
	 * @throws FileNotFoundException  exception of file not found
	 */
	public void qaMode(String p_fileName) throws FileNotFoundException {
		if(p_fileName == null || p_fileName.trim() == "") {
			GenericView.printWarning("loading default command");
			p_fileName = "default.txt";
		}

		reboot();
		
		//read file
		String l_mapDirectory = WarzoneProperties.getWarzoneProperties().getGameMapDirectory();
		File l_mapFile = new File(l_mapDirectory + p_fileName);
		
		d_gameContext.setMapFileName(p_fileName);

		//Specified file name does not exist (new map)
		if(!l_mapFile.exists() || l_mapFile.isDirectory()) {

			GenericView.printError("file is not existed.");
			return;
		}
		
		Scanner l_scanner = new Scanner(l_mapFile);
		String l_command;
		List<Router> l_routers;
		RouterService l_routerService =  RouterService.getRouterService(this);
		CommandService l_commandService =  CommandService.getCommandService(this );	


		LoadMapPhase l_loadMapPhase = null;
		
		while (l_scanner.hasNextLine()) {
			l_command = l_scanner.nextLine();			

			l_routers = l_routerService.parseCommand(l_command);						
			//excute the command
			GenericView.println(l_command);
			l_routerService.route(l_routers);				
		}
		
	}
}
