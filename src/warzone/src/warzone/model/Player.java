package warzone.model;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import warzone.service.CommonTool;
import warzone.service.GameEngine;
import warzone.view.GenericView;

/**
 * This class represents the player in the game.
 *
 */
public class Player {

	private String d_name;
	private Map<Integer, Country> d_conqueredCountries;
	private Queue<Order> d_orders;
	private int d_armyNumber = 0;//total number
	private int d_armiesToDeploy = 0; 
	private boolean d_isAlive = true;
	private boolean d_hasFinishIssueOrder;
	private boolean d_conqueredACountryThisTurn = false;
	private List<Card> d_cards;
	GameContext d_gameContext;
	int d_armyHasIssued = 0;


	private Scanner d_keyboard = new Scanner(System.in);
	
	/**
	 * This constructor initiate the player instance.
	 * @param p_name the name of the player
	 */
	public Player(String p_name) {
		
		d_name = p_name;
		d_conqueredCountries = new HashMap<Integer, Country>();
		d_orders = new LinkedList<Order>();
		d_cards = new ArrayList<Card>();
		d_gameContext = GameContext.getGameContext();
	}		
	
	/**
	 * This method will provide the name of the player.
	 * @return the name of the player
	 */
	public String getName() {
		return d_name;
	}

	/**
	 * This method can set the name of the player.
	 * @param p_name the name of the player
	 */
	public void setName(String p_name) {
		this.d_name = p_name;
	}

	/**
	 * This method will provide all countries conquered by the current player as Map structure.
	 * @return all coutries that are conquered by the player
	 */
	public Map<Integer, Country> getConqueredCountries() {
		return d_conqueredCountries;
	}

	/**
	 * This method will offer all Orders issued by the current player.
	 * @return all orders issued by the player
	 */
	public Queue<Order> getOrders() {
		return d_orders;
	}
	/**
	 * clear the countries list for current player
	 */
	public void cleanConqueredCountries() {
		d_conqueredCountries.clear();
	}
	
	/**
	 * This method will provide the number of armies owned by the current player.
	 * @return the number of armies
	 */
	public int getArmyNumber() {
		return d_armyNumber;
	}

	/**
	 * This method can set the number of armies owned by the current player.
	 * @param p_armyNumber the number of armies
	 */
	public void setArmyNumber(int p_armyNumber) {
		this.d_armyNumber = p_armyNumber;
	}
	
	/**
	 * This method will provide the number of armies deployed by the current player.
	 * @return the number of armies
	 */
	public int getArmiesToDeploy() {
		return d_armiesToDeploy;
	}

	/**
	 * This method can set the number of armies deployed by the current player.
	 * @param p_armiesToDeploy the number to deploy the army
	 */
	public void setArmiesToDeploy(int p_armiesToDeploy) {
		this.d_armiesToDeploy = p_armiesToDeploy;
	}	
	
	/**
	 * This method will show whether a player is out of the game.
	 * @return true the current player still has at least one territory.
	 */
	public boolean getIsAlive() {
		return d_isAlive;
	}

	/**
	 * This method can set the survival of the current player.
	 * @param p_isAlive false if the player has no territory.
	 */
	public void setIsAlive(boolean p_isAlive) {
		this.d_isAlive = p_isAlive;
	}

	/**
	 * set the value of d_hasFinishIssueOrder to indicate whehter this round issue order is finished
	 * @param p_hasFinished if finished issue order
	 */
	public void setHasFinisedIssueOrder(boolean p_hasFinished){
		this.d_hasFinishIssueOrder = p_hasFinished;
	}

	/**
	 * get the value of HasFinisedIssueOrder
	 * @return whether the player has finished the order
	 */
	public boolean getHasFinisedIssueOrder(){
		return d_hasFinishIssueOrder;
	}
	
	/**
	 * get the value of conqueredACountryThisTurn
	 * @return true if player has conquered a country this turn
	 */
	public boolean getConqueredACountryThisTurn() {
		return d_conqueredACountryThisTurn;
	}

	/**
	 * set if the player has conquered a country or not this turn 
	 * @param p_conqueredACountryThisTurn if conquered a country this turn
	 */
	public void setConqueredACountryThisTurn(boolean p_conqueredACountryThisTurn) {
		this.d_conqueredACountryThisTurn = p_conqueredACountryThisTurn;
	}
	
	/**
	 * Cards the player has available to play
	 * @return list of cards
	 */
	public List<Card> getCards() {
		return d_cards;
	}

	/**
	 * This method can convert command String into DeployOrder class.
	 * @param p_command the command that should be converted
	 * @return the converted command
	 */
	private DeployOrder conventDeployOrder(String p_command) {
		if(p_command == null)
			return null;
		
		p_command = p_command.trim().toLowerCase();

		String [] l_commandInfos = CommonTool.conventToArray(p_command);
		if(l_commandInfos.length == 3 &&  l_commandInfos[0].toString().equals("deploy")) {
			int l_countryId = CommonTool.parseInt(l_commandInfos[1]);
			int l_armyNumber = CommonTool.parseInt(l_commandInfos[2]);
			Country l_country = this.getConqueredCountries().get(l_countryId);
			
			if(l_country != null && l_armyNumber > 0 ) {
				return new DeployOrder(this, l_country, l_armyNumber );
			}
		}
		GenericView.printError("The player does not own the country or the army exceed the army in the pool");
		return null;			
	}

	/**
	 * convert command into order
	 * @param p_command command line
	 * @return an order if successfully generated, otherwise return null
	 */
	public Order conventOrder(String p_command) {
		if(p_command == null)
			return null;

		p_command = p_command.trim();

		String [] l_commandInfos = CommonTool.conventToArray(p_command);
		String l_orderName = "";
		if(l_commandInfos[0] != null)
			 l_orderName = l_commandInfos[0];
		switch(l_orderName.toLowerCase()){
			case "deploy":
				return createDeployOrder(l_commandInfos);
			case "advance":
				return createAdvanceOrder(l_commandInfos);
			case "bomb":
				return createBombOrder(l_commandInfos);
			case "blockade":
				return createBlockadeOrder(l_commandInfos);
			case "airlift":
				return createAirliftOrder(l_commandInfos);
			case "negotiate":
				return createNegotiateOrder(l_commandInfos);
		}
		return null;
	}


	/**
	 * create the deploy order by command
	 * @param p_commandInfos command infor
	 * @return the deploy order if success, otherwise return null
	 */
	public DeployOrder createDeployOrder(String[] p_commandInfos){

		if(p_commandInfos.length != 3) {
			GenericView.printError("The parameters are invalid.");
			return null;
		}

		//read the information of command
		int l_countryId = CommonTool.parseInt(p_commandInfos[1]);
		int l_armyNumber = CommonTool.parseInt(p_commandInfos[2]);
		Country l_country = this.getConqueredCountries().get(l_countryId);

		//check if the command is valid
		if (l_country == null || !this.getConqueredCountries().containsKey(l_country.getCountryID())) {
			GenericView.printError("Player does not own the given country.");
			return null;
		}
		if (l_armyNumber <= 0 || l_armyNumber > (d_armiesToDeploy - d_armyHasIssued)) {
			GenericView.printError("The army number should >0 and <" + (d_armiesToDeploy - d_armyHasIssued));
			return null;
		}

		//create the deploy order
		DeployOrder l_deployOrder = new DeployOrder(this, l_country, l_armyNumber);

		return l_deployOrder;
	}
	
	/**
	 * create the bomb order by command
	 * @param p_commandInfos command info
	 * @return the bomb order
	 */
	public BombOrder createBombOrder(String[] p_commandInfos){
		if(p_commandInfos.length != 2) {
			GenericView.printError("The parameters are invalid.");
			return null;
		}
		
		//check if a card available?
        if(!this.d_cards.contains(Card.BOMB)){
            GenericView.printError("Player " + this.getName() + " does not have a BOMB card");
            return null;
        }
            
		//read the information of command
		int l_targetCountryId = CommonTool.parseInt(p_commandInfos[1]);		

		Country l_targetCountry;
		l_targetCountry = this.d_gameContext.getCountries().get(l_targetCountryId);
      
		//check if country exist
		if(!GameContext.getGameContext().getCountries().containsKey(l_targetCountryId)){
			GenericView.printError("The target country does not exist");
			return null;
		}
        //check whether the target country belongs to the player
        if(this.getConqueredCountries().containsKey(l_targetCountryId)){
            GenericView.printError("The player cannot destroy armies in his own country.");
            return null;
        }
        //check whether the target country is adjacent to one of the countries that belong to the player
        boolean l_isAdjacent = false;
        for (Integer l_conqueredCountryId : this.getConqueredCountries().keySet()) {
        	if (this.getConqueredCountries().get(l_conqueredCountryId).getNeighbors().containsKey(l_targetCountryId)) {
        		l_isAdjacent = true;
        		break;
        	}
        }
        if (!l_isAdjacent) {
        	GenericView.printError("The target country is not adjacent to one of the countries that belong to the player.");
        	return null;
        }
        
		BombOrder l_bombOrder = new BombOrder(this, l_targetCountry);

		//remove one of NEGOTIATE the card 
		this.d_cards.remove(Card.BOMB);

		return l_bombOrder;
	}


/**
	 * create the Blockade Order by command
	 * @param p_commandInfos command info
	 * @return the Blockade order
	 */
	public BlockadeOrder createBlockadeOrder(String[] p_commandInfos){
		if(p_commandInfos.length != 2)  {
			GenericView.printError("The parameters are invalid.");
			return null;
		}

		//check if a card available?
        if(!this.d_cards.contains(Card.BLOCKADE)){
            GenericView.printError("Player " + this.getName() + " does not have a BLOCKADE card");
            return null;
        }
        
		//read the information of command
		int l_targetCountryId = CommonTool.parseInt(p_commandInfos[1]);
		Country l_targetCountry;
		if(l_targetCountryId>0) {
			l_targetCountry = this.d_gameContext.getCountries().get(l_targetCountryId);
			if(l_targetCountry != null) {
				BlockadeOrder l_order = new BlockadeOrder(this, l_targetCountry);
				//remove one of BLOCKADE the card 
				this.d_cards.remove(Card.BLOCKADE);
				return l_order;
			}
		}
		GenericView.printError("The player does not own the country");
		return null;
	}	

	/*
	 * create the advance order by command
	 * @param p_commandInfos command infor
	 * @return the deploy order if success, otherwise return null
	 */
	public AdvanceOrder createAdvanceOrder(String[] p_commandInfos){

		if(p_commandInfos.length != 4) {			
			GenericView.printError("Incorrect number of parameters. The command should be as follows: advance countryfromname countrytoname numarmies");
			return null;
		}

		//read the information of command
		Country l_fromCountry = findCountryByName(p_commandInfos[1]);
		Country l_toCountry = findCountryByName(p_commandInfos[2]);
		int l_numArmies = Integer.parseInt(p_commandInfos[3]);
		boolean l_isValidCommand = true;

		//check if the command is valid
		if (l_fromCountry == null) {			
			GenericView.printError("Country " + p_commandInfos[1] + " was not found. Please check your spelling.");
			l_isValidCommand = false;
		}
		if (l_toCountry == null) {			
			GenericView.printError("Country " + p_commandInfos[2] + " was not found. Please check your spelling.");
			l_isValidCommand = false;
		}
		
		if(l_isValidCommand) {			
			//create the deploy order
			return new AdvanceOrder(this, l_fromCountry, l_toCountry, l_numArmies);
		}
		return null;
	}
	
	/**
	 *  find country according to given name
	 * @param p_countryName given country Name
	 * @return country
	 */
	private Country findCountryByName(String p_countryName) {
		
		Map<Integer, Country> l_countries = this.d_gameContext.getCountries();
		List<Integer> l_countryIDs = new ArrayList<Integer>(l_countries.keySet());
		
		for(Integer l_countryID : l_countryIDs) {			
			if(l_countries.get(l_countryID).getCountryName().equalsIgnoreCase(p_countryName.trim())) {				
				return l_countries.get(l_countryID);
			}
		}
		
		return null;
	}
	
	/**
	 * create the airlift order by command
	 * @param p_commandInfos command info
	 * @return the airlift order
	 */
	public AirliftOrder createAirliftOrder(String[] p_commandInfos){
		if(p_commandInfos.length != 4)  {
			GenericView.printError("The parameters are invalid.");
			return null;
		}

		//check if the player has a airlift card
		if(!this.d_cards.contains(Card.AIRLIFT)){
			GenericView.printError("Player " + this.getName() + " does not have a AIRLIFT card");
			return null;
		}
		
		//read the information of command
		int l_armyNumber = CommonTool.parseInt(p_commandInfos[3]);
		Country l_fromCountry = this.d_gameContext.getCountries().get(CommonTool.parseInt(p_commandInfos[1]));
		Country l_toCountry = this.d_gameContext.getCountries().get(CommonTool.parseInt(p_commandInfos[2]));
		
		//check if country exist
		if(l_fromCountry == null || l_toCountry == null){
			GenericView.printError("The sorce or target country is not existed.");
			return null;
		}
		//check if army number is more than 0
		if(l_armyNumber <= 0){
			GenericView.printError("The number of airlift army shoud more than 0.");
			return null;
		}
		AirliftOrder l_order = new AirliftOrder(this, l_fromCountry, l_toCountry, l_armyNumber);
		//remove one of AIRLIFT the card 
		this.d_cards.remove(Card.AIRLIFT);
		return l_order;
	}	
	
	/**
	 *  create Diplomacy Order from command
	 * @param p_commandInfos given command array
	 * @return Diplomacy Order if the command is valid
	 */
	public NegotiateOrder createNegotiateOrder(String[] p_commandInfos){
		if(p_commandInfos.length != 2 || p_commandInfos[1]==null || p_commandInfos[1].toString() =="" ){
			GenericView.printError("The parameters are invalid.");
			return null;
		}
		
		//check if a card available?
        if(!this.d_cards.contains(Card.NEGOTIATE)){
            GenericView.printError("Player " + this.getName() + " does not have a NEGOTIATE card");
            return null;
        }

		//read the information of command
		Player l_targetPlayer = d_gameContext.getPlayers().get(p_commandInfos[1].toString());
		if(l_targetPlayer != null  && l_targetPlayer.getIsAlive()) {
			NegotiateOrder l_diplomacyOrder = new NegotiateOrder(this, l_targetPlayer);
			//remove one of NEGOTIATE the card 
			this.d_cards.remove(Card.NEGOTIATE);
			return l_diplomacyOrder;
		}
		else {
			GenericView.printError("The target player is invalid or is not alive.");
			return null;
		}
	}

	
	/**
	 * The GameEngine class calls the issue_order() method of the Player. This method will wait for the following 
	 * command, then create a deploy order object on the players list of orders, then reduce the number of armies in the 
	 * players reinforcement pool. The game engine does this for all players in round-robin fashion until all the players 
	 * have placed all their reinforcement armies on the map.
	 * 
	 * Issuing order command: deploy countryID num (until all reinforcements have been placed)
	 */
	public void issue_order() {

		//check if the player finish the issue order
		if(d_hasFinishIssueOrder) return;

		String l_command = "";
		boolean l_hasOrderGenerated = false;
		int l_armyToIssue = this.d_armiesToDeploy - d_armyHasIssued;
		GameEngine l_gameEngine = GameEngine.getGameEngine(d_gameContext);
		
		Order l_order = null;
		do {
			GenericView.println(String.format("----- Player [%s] has [%s] Countries ", this.getName(), this.getConqueredCountries().size() ));
			for(Country l_countryTemp : this.getConqueredCountries().values()) {
				GenericView.println(String.format("Country ID : [%s] , Name : [%s], Army: [%s]", l_countryTemp.getCountryID(), l_countryTemp.getCountryName(), l_countryTemp.getArmyNumber() ));	
			}
			
			//render available cards 
			renderAvailableCards();
			
			//render current issued order 
			renderIssuedOrders();
			
			GenericView.println(String.format("*****  Please input command for player [%s] , there is [%s] army available for deployment", this.getName(), l_armyToIssue ));

			if(!d_gameContext.getIsDemoMode()) {
				//1. issue order from interaction
				l_command = d_keyboard.nextLine().trim();				

				if(l_command.equalsIgnoreCase("help")) {
					l_gameEngine.getPhase().help();
					continue;
				}
				else if( l_command.equalsIgnoreCase("showmap") ) {
					l_gameEngine.getPhase().showMap();
					continue;
				}
				//check if the issue order has finished
				else if(l_command.equalsIgnoreCase("done")){
					d_hasFinishIssueOrder = true;
					GenericView.println(String.format("---------- Finish issuing order for player [%s] in this turn.", this.getName()));
					return;
				}

				String [] l_commandInfos = CommonTool.conventToArray(l_command);
				//convent the commend to order.
				l_order = conventOrder(l_command);
				if (l_order != null) {
					l_order.setCommand(l_command);
					l_hasOrderGenerated = true;
					this.d_orders.add(l_order);
					l_order.printOrder();

					//if the order is a deploy order
					if (l_order instanceof DeployOrder) {
						d_armyHasIssued = d_armyHasIssued + ((DeployOrder)l_order).getArmyNumber();
					}
					d_gameContext.getLogEntryBuffer().logIssueOrder("Succeed", "Issued an order", l_command);
				} else {
					GenericView.printWarning("Incorrect command, please retry.");
					d_gameContext.getLogEntryBuffer().logIssueOrder("Error", "failed to issued an order", l_command);
					l_hasOrderGenerated = false;					
				}
			}
					
		} while (l_hasOrderGenerated == false );		
	}
	/**
	 * render the issued orders 
	 */
	private void renderIssuedOrders()	{
		GenericView.println(String.format("----- Player [%s] has issued [%s] orders:", this.getName() ,d_orders.size() ) );
		for (Order l_order: d_orders) {
			GenericView.println(l_order.toString());
        }
	}
	/**
	 * render the avaliable cards 
	 */
	private void renderAvailableCards()	{
		GenericView.println(String.format("----- Player [%s] has [%s] cards available:", this.getName() , this.d_cards.size() ) );
		for (Card l_card: d_cards) {
			GenericView.println(l_card.toString());
        }
	}	
	
	
	
	/**
	 * The GameEngine calls the next_order() method of the Player. Then the Order objects execute() method is called, 
	 * which will enact the order. 
	 * 
	 * @return the next Order of the player
	 */
	public Order next_order() {
		
		return this.d_orders.poll();
	}
	
	/**
	 * Assign reinforcements to a player based on the continents they have conquered. Each continent has a bonus number of reinforcements
	 * per round if a player owns all the countries within it. This method loops through all the conquered countries, tracking counters of
	 * the number of countries owned in each continent. If the number of countries owned in a continent matches the number of countries in that
	 * continent, the player gets the bonus reinforcements added (for each applicable continent).
	 */
	public void assignReinforcements() {		

		//clear local deploy army number
		d_armyHasIssued = 0;
		//Set the armiesToDeploy to the minimum value
		this.setArmiesToDeploy(WarzoneProperties.getWarzoneProperties().getMinimumReinforcementsEachRound()); 
		
		//Set armiesToDeploy based on the number of owned countries (if that number is greater than the minimum)
		int l_conqueredCountriesBonus = (int)(Math.floor(this.getConqueredCountries().size() / WarzoneProperties.getWarzoneProperties().getMinimumCountriesPerReinforcementBonus()));
		
		if(l_conqueredCountriesBonus > this.getArmiesToDeploy()) {
			this.setArmiesToDeploy(l_conqueredCountriesBonus);
		}
		
		//Key: continentID, Value: Number of countries player owns in this continent
		Map<Integer, Integer> l_armiesPerContinent = new HashMap<Integer, Integer>(d_gameContext.getContinents().size());

		//Create a list of playerIDs from the game context and shuffle their order
		List<Integer> l_conqueredCountryIDs = new ArrayList<Integer>(this.getConqueredCountries().keySet());
				
		//Looping variables
		int l_continentID;
		Integer l_deployedArmies;
		
		//Loop through each conquered country, incrementing each counter of the conquered country's continent
		for(Integer countryID : l_conqueredCountryIDs) {
						
			l_continentID = d_gameContext.getCountries().get(countryID).getContinent().getContinentID();
			l_deployedArmies = l_armiesPerContinent.get(l_continentID);
			
			if(l_deployedArmies == null) {
				l_armiesPerContinent.put(l_continentID, 1);
			}
			else {
				l_armiesPerContinent.put(l_continentID, l_deployedArmies + 1);
			}
		}
		
		//Loop through the continent counters and update the players' armiesToDeploy if they own all the countries in a continent
		l_armiesPerContinent.forEach(
			(l_apcContinentID, l_apcDeployedArmies) -> {
				if(l_apcDeployedArmies == d_gameContext.getContinents().get(l_apcContinentID).getCountries().size()) {
					this.setArmiesToDeploy(this.getArmiesToDeploy() + d_gameContext.getContinents().get(l_apcContinentID).getBonusReinforcements());
				}
			}
		);
	}	
}
