package warzone.model;

import warzone.view.GenericView;

/**
 * This class represents one deploy order of the gameplay
 */
public class DeployOrder extends Order {

	/**
	 * country to deploy army
	 */
	private Country d_country;
	/**
	 * owner of the order
	 */
	private Player d_player;
	/**
	 * army number
	 */
	private int d_armyNumber;

	/**
	 * constructor
	 * @param p_player the owner of this order
	 * @param p_country the country this order manipulate
	 * @param p_armyNumber the army number it deploys
	 */
	public DeployOrder(Player p_player, Country p_country, int p_armyNumber) {
		d_country = p_country;
		d_armyNumber = p_armyNumber;
		d_player = p_player;
		this.d_orderType = OrderType.DEPLOY;
		this.d_gameContext = GameContext.getGameContext(); 
	}

	/**
	 * get the country of this order
	 * @return the country
	 */
	public Country getCountry() {
		return d_country;
	}

	/**
	 * set the country of this order
	 * @param p_country the country
	 */
	public void setCountry(Country p_country) {
		this.d_country = p_country;
	}

	/**
	 * the owner of this order
	 * @return the player
	 */
	public Player getPlayer() {
		return d_player;
	}

	/**
	 * set the owner of the order
	 * @param p_player the player who owns this order
	 */
	public void setPlayer(Player p_player) {
		this.d_player = p_player;
	}

	/**
	 * get the army number of this order
	 * @return the army number of this deploy order
	 */
	public int getArmyNumber() {
		return d_armyNumber;
	}

	/**
	 * set the army number of this order
	 * @param p_armyNumber the army number of this deploy order
	 */
	public void setArmyNumber(int p_armyNumber) {
		this.d_armyNumber = p_armyNumber;
	}


	/**
	 * override of the execution of the order
	 */
	@Override
	public void execute() {
		if(!valid()){		
			GenericView.printWarning("Fail to execute order:" + toString());
    		this.logExecution("Fail","The context does not satisfy the order" );
    		return;
		}

		//move army
		this.d_country.setArmyNumber( this.d_country.getArmyNumber() +  this.d_armyNumber );
		this.d_player.setArmiesToDeploy(this.d_player.getArmiesToDeploy() - this.d_armyNumber);
		
		//print success information
		GenericView.printSuccess("Success to execute order:" + toString());
		this.logExecution("Success", this.toString() );
	}

	/**
	 * override of the valid of the order
	 * @return true if valid
	 */
	@Override
	public boolean valid(){
		if(this.d_country.getOwner()== null || !this.d_country.getOwner().equals(this.d_player) ) {
  			GenericView.printWarning("The player does not own the country or order ");
  			//this.d_player.setArmiesToDeploy(this.d_player.getArmiesToDeploy() + d_armyNumber );
			return false;
		}
		if (this.d_player.getArmiesToDeploy() <  this.d_armyNumber) {
			d_armyNumber = this.d_player.getArmiesToDeploy();
			GenericView.printWarning("The player does not have enough army to deploy, then the deploy army number is adjusted to " + d_armyNumber);
		}		
		if ( this.d_armyNumber <=0) {
  			GenericView.printWarning("The number of deploy army should greater than 0.");
			return false;
		}
		return true;
	}
	
	/**
	 * override of print the order
	 */
	@Override
	public void printOrder(){
		GenericView.println(this.toString());		
	}
	
	/**
	 * override of print the order
	 */
	@Override
	public String toString(){
		return String.format("Deploy Order, issued by player [%s], deploying [%s] armies to [%s]",  this.d_player.getName(),d_armyNumber, d_country.getCountryName() );		
	}	
}
