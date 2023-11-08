package warzone.model;

import warzone.view.GenericView;

/**
 * This class represents one advance order of the gameplay
 */
public class AdvanceOrder extends Order{

	/**
	 * advance from country
	 */
	private Country d_fromCountry;

	/**
	 * advance to country
	 */
	private Country d_toCountry;

	/**
	 * number of armies
	 */
	private int d_numberOfArmies;

	/**
	 * owner of the order
	 */
	private Player d_player;

	/**
	 * a local value of d_numberOfArmies used in local methods
	 */
	private int l_numberOfArmies;

	/**
	 * AdvanceOrder constructor
	 * 
	 * @param p_player the owner of the order
	 * @param p_fromCountry advance from country
	 * @param p_toCountry advance to country
	 * @param p_numberOfArmies number of armies
	 */
	public AdvanceOrder(Player p_player, Country p_fromCountry, Country p_toCountry, int p_numberOfArmies) {
		d_player = p_player;
		d_fromCountry = p_fromCountry;
		d_toCountry = p_toCountry;
		d_numberOfArmies = p_numberOfArmies;
		this.d_orderType = OrderType.ADVANCE;
		this.d_gameContext = GameContext.getGameContext();  
	}
	
	/**
	 * Get fromCountry, the country that is attacking
	 * 
	 * @return fromCountry The country that is attacking
	 */
	public Country getFromCountry() {
		return d_fromCountry;
	}

	/**
	 * Set fromCountry, the country that is attacking
	 * 
	 * @param fromCountry advance from country
	 */
	public void setFromCountry(Country fromCountry) {
		this.d_fromCountry = fromCountry;
	}

	/**
	 * Get toCountry, the country that is defending
	 * 
	 * @return toCountry The country that is defending
	 */
	public Country getToCountry() {
		return d_toCountry;
	}

	/**
	 * Set toCountry, the country that is defending
	 * 
	 * @param toCountry advance to country
	 */
	public void setToCountry(Country toCountry) {
		this.d_toCountry = toCountry;
	}

	/**
	 * Get the number of armies the attacker wants to send out
	 * 
	 * @return numberOfArmies The number of armies the attacker wants to send out
	 */
	public int getNumberOfArmies() {
		return d_numberOfArmies;
	}

	/**
	 * Set the number of armies the attacker wants to send out
	 * 
	 * @param numberOfArmies The number of armies the attacker wants to send out
	 */
	public void setNumberOfArmies(int numberOfArmies) {
		this.d_numberOfArmies = numberOfArmies;
	}

	/**
	 * Get the player that initiated the advance order (the attacker)
	 * 
	 * @return player The player that initiated the advance order (the attacker)
	 */
	public Player getPlayer() {
		return d_player;
	}

	/**
	 * Set the player that initiated the advance order (the attacker)
	 * 
	 * @param player The player that initiated the advance order (the attacker)
	 */
	public void setPlayer(Player player) {
		this.d_player = player;
	}

	/**
     * Perform the advanceOrder. A series of skirmishes occur between to attacking and defending countries.
     * The attacker claims the defender's country if they defeat all the defender's armies and still have armies
     * remaining to advance. If not, both countries will likely suffer casualties, but no change of ownership will occur.
     */
	@Override
	public void execute() {
		
		if(!valid()){
			GenericView.printWarning("Fail to execute order:" + toString());
			this.logExecution("Fail","The context does not satisfy the order" );
			return;
		}
	
		//Make sure that there are enough armies to advance
		if(d_fromCountry.getArmyNumber() < d_numberOfArmies) {
			
			d_numberOfArmies = d_fromCountry.getArmyNumber();
		}

		l_numberOfArmies = d_numberOfArmies;
		//If toCountry is owned by current player -> advance armies
		if(d_toCountry.getOwner() != null && d_toCountry.getOwner().equals(d_player)) {
		
			//Move the armies
			d_fromCountry.setArmyNumber(d_fromCountry.getArmyNumber() - l_numberOfArmies);
			d_toCountry.setArmyNumber(d_toCountry.getArmyNumber() + l_numberOfArmies);
		}
		//Else toCountry is owned by opponent -> attack
		else {
			do {
				// check if successfully conquer a country
				if(d_toCountry.getArmyNumber() == 0 && l_numberOfArmies >0) {
					changeCountryOwnership(d_toCountry, d_fromCountry, l_numberOfArmies);
					break;
				}
				//a single attack between two army units
				singleAttack();
			}while( l_numberOfArmies > 0);
		}
		//print success information
		GenericView.printSuccess("Success to execute order:" + toString());
		this.logExecution("Success", this.toString() );
	}

	/**
	 * a single attack between two army units
	 */
	private void singleAttack(){

		//Attacking army has a 60% chance of killing a defending army
		if(Math.random() * 10 <= 6) {
			//Kill defending army
			d_toCountry.setArmyNumber(d_toCountry.getArmyNumber() - 1);
		}

		//Defending army has a 70% chance of killing a attacking army
		if(Math.random() * 10 <= 7) {
			//Kill attacking army
			d_fromCountry.setArmyNumber(d_fromCountry.getArmyNumber() - 1);
			l_numberOfArmies--;
		}
	}

	/**
	 * When an attacker conquers a defender's country, this method performs the exchange of the countries and armies. 
	 * 
	 * @param p_toCountry to country
	 * @param p_fromCountry from country
	 * @param p_numberOfArmies number of armies set to the new country
	 */
	private void changeCountryOwnership(Country p_toCountry, Country p_fromCountry, int p_numberOfArmies) {

		//change the country state
		p_toCountry.setCountryState(CountryState.Occupied, this.getPlayer());
		//Update army counts
		p_fromCountry.setArmyNumber(p_fromCountry.getArmyNumber() - p_numberOfArmies);
		p_toCountry.setArmyNumber(p_toCountry.getArmyNumber() + p_numberOfArmies);
		//Set this variable to true to allow the player to collect a card at the end of the turn
		this.getPlayer().setConqueredACountryThisTurn(true);
		return;
	}
	
	/**
     * Override of valid check
     * @return true if valid
     */
    @Override
    public boolean valid(){        
    	boolean l_isValid = true;
    	Player l_player = d_fromCountry.getOwner();
    	if(l_player == null || !l_player.getIsAlive()) {
			GenericView.printWarning(String.format(" The player of target country is not alive or is Null."));
			return false;
		}
    	// check if army number above zero
    	if(d_numberOfArmies <= 0){
			GenericView.printWarning("Could not perform the advance order with below 0 armies.");
			return false;
		}
    	//Check if fromCountry is owned by the current player
		if(d_fromCountry.getOwner() == null || !d_fromCountry.getOwner().equals(d_player)) {			
			GenericView.printWarning("Could not perform the advance order moving " + d_numberOfArmies + " armies from " + 
					d_fromCountry.getCountryName() + ", because " + d_player.getName() + " does not own [" + d_fromCountry.getCountryName() + "].");
			
		    return false;
		}
      
		//check if DIPLOMACY 
		if( d_toCountry.getOwner()!= null && this.d_player != null 
				&& this.d_gameContext.isDiplomacyInCurrentTurn(d_player, d_toCountry.getOwner())){
      			GenericView.printWarning(String.format("The player [%s] and [%s] are in Diplomacy in current turn.", this.d_player.getName(), d_toCountry.getOwner().getName() ));
      		    return false;
		}		
		
		//Check if fromCountry and toCountry are neighbors
		if(d_fromCountry.getNeighbors().get(d_toCountry.getCountryID()) == null) {			
			GenericView.printWarning("Could not perform the advance order moving " + d_numberOfArmies + " armies from " + 
					d_fromCountry.getCountryName() + " to " + d_toCountry.getCountryName() + " because they are not neighbors.");
			
		    return false;
		}

		if (this.d_numberOfArmies <= 0 ) {			
			GenericView.printWarning("The advance army number should greater than 0.");
			return false;
		}		
		

		if(d_fromCountry.getArmyNumber() < d_numberOfArmies && d_fromCountry.getArmyNumber() == 0) {
			GenericView.printWarning("Could not perform the advance order moving with 0 army in "+ d_fromCountry.getCountryName());

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
		return String.format("Advance Order, issued by player [%s], sending [%s] armies from  [%s] to [%s]",  this.d_player.getName(), this.d_numberOfArmies, d_fromCountry.getCountryName(),  d_toCountry.getCountryName() );		
	}
}
