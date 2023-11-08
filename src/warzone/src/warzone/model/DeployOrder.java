package warzone.model;

/**
 * This class represents one deploy order of the gameplay
 */
public class DeployOrder implements Order {

	private Country d_country;
	private Player d_player;	
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
	}

	/**
	 * constructor
	 * @param p_country the country this order manipulate
	 * @param p_armyNumber the army number it deploys
	 */
	public DeployOrder(Country p_country, int p_armyNumber) {
		d_country = p_country;
		d_armyNumber = p_armyNumber;
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
	 * overide of the execution of the order
	 * @return true if successfully executed, otherwise return false
	 */
	@Override
	public boolean execute() {
		//check
		if(!this.d_country.getOwner().equals(this.d_player) ) {
			return false;
		} 
		// if the remaining army is less than deploy number:
		if( this.d_player.getArmiesToDeploy() <  this.d_armyNumber) {
			this.d_armyNumber = this.d_player.getArmiesToDeploy();
		}
				
		//move army
		this.d_country.setArmyNumber( this.d_country.getArmyNumber() +  this.d_armyNumber );
		this.d_player.setArmiesToDeploy(this.d_player.getArmiesToDeploy() - this.d_armyNumber);
		return true;
	}
}
