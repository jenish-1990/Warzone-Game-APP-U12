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


public class Player {

	private String d_name;
	private Map<Integer, Country> d_conqueredCountries;
	private Queue<Order> d_orders;
	private int d_armyNumber = 0;//total number
	private int d_armiesToDeploy = 0; 
	private boolean d_isAlive = true;
	
	private Scanner d_keyboard = new Scanner(System.in);
	
	public Player(String p_name) {
		
		d_name = p_name;
		d_conqueredCountries = new HashMap<Integer, Country>();
		d_orders = new LinkedList<Order>();
	}
	
	public String getName() {
		return d_name;
	}

	public void setName(String p_name) {
		this.d_name = p_name;
	}

	public Map<Integer, Country> getConqueredCountries() {
		return d_conqueredCountries;
	}

	public Queue<Order> getOrders() {
		return d_orders;
	}
	
	public int getArmyNumber() {
		return d_armyNumber;
	}

	public void setArmyNumber(int p_armyNumber) {
		this.d_armyNumber = p_armyNumber;
	}
	
	public int getArmiesToDeploy() {
		return d_armiesToDeploy;
	}

	public void setArmiesToDeploy(int p_armiesToDeploy) {
		this.d_armiesToDeploy = p_armiesToDeploy;
	}	
	
	public boolean getIsAlive() {
		return d_isAlive;
	}

	public void setIsAlive(boolean p_isAlive) {
		this.d_isAlive = p_isAlive;
	}
	
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
	 * The GameEngine class calls the issue_order() method of the Player. This method will wait for the following 
	 * command, then create a deploy order object on the players list of orders, then reduce the number of armies in the 
	 * players reinforcement pool. The game engine does this for all players in round-robin fashion until all the players 
	 * have placed all their reinforcement armies on the map.
	 * 
	 * Issuing order command: deploy countryID num (until all reinforcements have been placed)
	 */
	public void issue_order() {
		if( this.d_armiesToDeploy == 0 )
			return ;

		String l_command = "";
		int l_armyToIssue = this.getArmiesToDeploy();
		int l_armyHasIssued = 0;
		GameContext l_gameContext = GameContext.getGameContext();
		do {
			GenericView.println("Please input the deploy command for player " + this.getName());
			DeployOrder l_deployOrder;

			if(!l_gameContext.getIsDemoMode()) {
				//1. issue order from interaction
				l_command = d_keyboard.next();
				//convent the commend to deploy order.
				l_deployOrder = conventDeployOrder(l_command);
				if(l_deployOrder != null && this.getConqueredCountries().containsKey(l_deployOrder.getCountry().getCountryID()) 
					&&  l_deployOrder.getArmyNumber() <= l_armyToIssue	) {
					l_deployOrder.setPlayer(this);				
				}		
			}
			else {
				//2. generate the command automatically.
				List<Integer> l_countryKeys = new ArrayList(d_conqueredCountries.keySet());
				Integer l_countryKey = l_countryKeys.get( CommonTool.getRandomNumber(0, (l_countryKeys.size() -1 )) );			
				Country l_country = d_conqueredCountries.get(l_countryKey);
				int l_armyNumber =  CommonTool.getRandomNumber(0, l_armyToIssue);
				l_deployOrder = new DeployOrder(this, l_country, l_armyNumber );
			}
			
			if(l_deployOrder != null)
			{				
				this.d_orders.add(l_deployOrder);
				
				l_armyToIssue = l_armyToIssue - l_deployOrder.getArmyNumber();
				l_armyHasIssued = l_armyHasIssued + l_deployOrder.getArmyNumber();
			}
			else {
				GenericView.printWarning("Incorrect command, please check the countryID and the number of army");
			}			
		} while (l_armyToIssue > 0 );		
	}
	
	
	/**
	 * The GameEngine calls the next_order() method of the Player. Then the Order objects execute() method is called, 
	 * which will enact the order. 
	 * 
	 * @return
	 */
	public Order next_order() {
		
		return this.d_orders.poll();
	}
	
	public void assignReinforcements() {		

	}	
	
}
