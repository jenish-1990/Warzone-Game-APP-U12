package warzone.model;

public class DeployOrder implements Order {

	private Country d_country;
	private Player d_player;	
	private int d_armyNumber;
	
	public DeployOrder(Player p_player, Country p_country, int p_armyNumber) {
		d_country = p_country;
		d_armyNumber = p_armyNumber;
		d_player = p_player;
	}
	
	public DeployOrder(Country p_country, int p_armyNumber) {
		d_country = p_country;
		d_armyNumber = p_armyNumber;
	}
	
	
	public Country getCountry() {
		return d_country;
	}

	public void setCountry(Country p_country) {
		this.d_country = p_country;
	}
	
	public Player getPlayer() {
		return d_player;
	}

	public void setPlayer(Player p_player) {
		this.d_player = p_player;
	}
	
	public int getArmyNumber() {
		return d_armyNumber;
	}

	public void setArmyNumber(int p_armyNumber) {
		this.d_armyNumber = p_armyNumber;
	}
	
	
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
