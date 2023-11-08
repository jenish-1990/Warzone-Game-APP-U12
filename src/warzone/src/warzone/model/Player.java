package warzone.model;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import warzone.service.CommonTool;
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
	 * @param p_conqueredACountryThisTurn
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
				return new DeployOrder(l_country, l_armyNumber );
			}
		}
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

		p_command = p_command.trim().toLowerCase();

		String [] l_commandInfos = CommonTool.conventToArray(p_command);
		String l_orderName = "";
		if(l_commandInfos[0] != null)
			 l_orderName = l_commandInfos[0];
		switch(l_orderName.toLowerCase()){
			case "deploy":
				return createDeployOrder(l_commandInfos);
			case "advance":
				break;
			case "bomb":
				break;
			case "blockade":
				break;
			case "airlift":
				return createAirliftOrder(l_commandInfos);
			case "diplomacy":
				break;
		}
		return null;
	}

	int l_armyToIssue = this.getArmiesToDeploy();
	int l_armyHasIssued = 0;

	/**
	 * create the deploy order by command
	 * @param p_commandInfos command infor
	 * @return the deploy order if success, otherwise return null
	 */
	public DeployOrder createDeployOrder(String[] p_commandInfos){

		if(p_commandInfos.length != 3) return null;

		//read the information of command
		int l_countryId = CommonTool.parseInt(p_commandInfos[1]);
		int l_armyNumber = CommonTool.parseInt(p_commandInfos[2]);
		Country l_country = this.getConqueredCountries().get(l_countryId);

		//check if the command is valid
		if (l_country == null || !this.getConqueredCountries().containsKey(l_country.getCountryID()))
			return null;
		if (l_armyNumber < 0 || l_armyNumber > l_armyToIssue)
			return null;

		//create the deploy order
		DeployOrder l_deployOrder = new DeployOrder(l_country, l_armyNumber);
		l_deployOrder.setPlayer(this);

		return l_deployOrder;
	}

	/**
	 * create the airlift order by command
	 * @param p_commandInfos command info
	 * @return the airlift order
	 */
	public AirliftOrder createAirliftOrder(String[] p_commandInfos){
		if(p_commandInfos.length != 4) return null;

		//read the information of command
		int l_countrySourceId = CommonTool.parseInt(p_commandInfos[1]);
		int l_countryTargetId = CommonTool.parseInt(p_commandInfos[2]);
		int l_armyNumber = CommonTool.parseInt(p_commandInfos[3]);
		//check if the player has a airlift card
		if(!this.getCards().contains(Card.AIRLIFT)){
			GenericView.printError("Player " + this.getName() + " does not have a airlift card");
			return null;
		}
		//check if country exist
		if(!GameContext.getGameContext().getCountries().containsKey(l_countryTargetId) || !GameContext.getGameContext().getCountries().containsKey(l_countrySourceId)){
			GenericView.printError("Does not exist the sorce/target country");
			return null;
		}
		//check if army number is more than 0
		if(l_armyNumber <= 0){
			GenericView.printError("The number of airlift army shoud more than 0.");
			return null;
		}
		AirliftOrder l_airliftOrder = new AirliftOrder(l_countrySourceId, l_countryTargetId, l_armyNumber);
		l_airliftOrder.setPlayer(this);

		return l_airliftOrder;
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
		int l_armyToIssue = this.getArmiesToDeploy();
		int l_armyHasIssued = 0;
		GameContext l_gameContext = GameContext.getGameContext();
		
		GenericView.println(String.format("You have [%s] Countries and [%s] armies", this.getConqueredCountries().size(), l_armyToIssue ));
		for(Country l_countryTemp : this.getConqueredCountries().values()) {
			GenericView.println(String.format("Country ID : [%s] , Name : [%s]", l_countryTemp.getCountryID(), l_countryTemp.getCountryName() ));	
		}	
		
		Order l_order = null;
		do {
			GenericView.println(String.format("Please input command for player [%s] , there is [%s] army available", this.getName(), l_armyToIssue ));
			DeployOrder l_deployOrder;

			if(!l_gameContext.getIsDemoMode()) {
				//1. issue order from interaction
				l_command = d_keyboard.nextLine();				


				//check if the issue order has finised
				String [] l_commandInfos = CommonTool.conventToArray(l_command);
				if(l_commandInfos[0].trim().toLowerCase().equals("done")){
					d_hasFinishIssueOrder = true;
					return;
				}

				//convent the commend to deploy order.
				l_order = conventOrder(l_command);
				if (l_order != null) {
					l_hasOrderGenerated = true;
					this.d_orders.add(l_order);
					l_order.printOrder();

					//if the order is a deploy order
					if (l_order instanceof DeployOrder) {
						l_armyToIssue = l_armyToIssue - ((DeployOrder)l_order).getArmyNumber();
						l_armyHasIssued = l_armyHasIssued + ((DeployOrder)l_order).getArmyNumber();
					}
				} else {
					GenericView.printWarning("Incorrect command, please retry.");
					l_hasOrderGenerated = false;
				}
			}
			else {
				//2. generate the command automatically.
//				List<Integer> l_countryKeys = new ArrayList(d_conqueredCountries.keySet());
//				Integer l_countryKey = l_countryKeys.get( CommonTool.getRandomNumber(0, (l_countryKeys.size() )) );			
//				Country l_country = d_conqueredCountries.get(l_countryKey);
//				int l_armyNumber =  CommonTool.getRandomNumber(1, l_armyToIssue);
//				l_deployOrder = new DeployOrder(this, l_country, l_armyNumber );
//				l_hasOrderGenerated = true;
//				GenericView.printSuccess(String.format("Issue order of Deploying [%s] army to Country [%s]", l_armyNumber , l_country.getCountryName() ));
			}
					
		} while (l_hasOrderGenerated == false );		
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
	 * 
	 * @param p_gameContext game context
	 */
	public void assignReinforcements(GameContext p_gameContext) {		
		
		//Set the armiesToDeploy to the minimum value
		this.setArmiesToDeploy(WarzoneProperties.getWarzoneProperties().getMinimumReinforcementsEachRound()); 
		
		//Set armiesToDeploy based on the number of owned countries (if that number is greater than the minimum)
		int l_conqueredCountriesBonus = (int)(Math.floor(this.getConqueredCountries().size() / WarzoneProperties.getWarzoneProperties().getMinimumCountriesPerReinforcementBonus()));
		
		if(l_conqueredCountriesBonus > this.getArmiesToDeploy()) {
			this.setArmiesToDeploy(l_conqueredCountriesBonus);
		}
		
		//Key: continentID, Value: Number of countries player owns in this continent
		Map<Integer, Integer> l_armiesPerContinent = new HashMap<Integer, Integer>(p_gameContext.getContinents().size());

		//Create a list of playerIDs from the game context and shuffle their order
		List<Integer> l_conqueredCountryIDs = new ArrayList<Integer>(this.getConqueredCountries().keySet());
				
		//Looping variables
		int l_continentID;
		Integer l_deployedArmies;
		
		//Loop through each conquered country, incrementing each counter of the conquered country's continent
		for(Integer countryID : l_conqueredCountryIDs) {
						
			l_continentID = p_gameContext.getCountries().get(countryID).getContinent().getContinentID();
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
				if(l_apcDeployedArmies == p_gameContext.getContinents().get(l_apcContinentID).getCountries().size()) {
					this.setArmiesToDeploy(this.getArmiesToDeploy() + p_gameContext.getContinents().get(l_apcContinentID).getBonusReinforcements());
				}
			}
		);
	}	
}