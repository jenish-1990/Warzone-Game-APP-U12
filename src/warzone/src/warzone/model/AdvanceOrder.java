package warzone.model;

import java.util.Random;
import warzone.view.GenericView;

/**
 * This class represents one advance order of the gameplay
 */
public class AdvanceOrder extends Order{

	private Country d_fromCountry;
	private Country d_toCountry;
	private int d_numberOfArmies;
	private Player d_player;
	
	/**
	 * AdvanceOrder constructor
	 * 
	 * @param p_player
	 * @param p_fromCountry
	 * @param p_toCountry
	 * @param p_numberOfArmies
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
	 * @param fromCountry
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
	 * @param toCountry
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
		
		if(valid()) {
		
			//Make sure that there are enough armies to advance
			if(d_fromCountry.getArmyNumber() < d_numberOfArmies) {
				
				d_numberOfArmies = d_fromCountry.getArmyNumber();
			}
			
			//If toCountry is owned by current player -> advance armies
			if(d_toCountry.getOwner().equals(d_player)) {
			
				//Move the armies
				d_fromCountry.setArmyNumber(d_fromCountry.getArmyNumber() - d_numberOfArmies);
				d_toCountry.setArmyNumber(d_toCountry.getArmyNumber() + d_numberOfArmies);
			}
			//Else toCountry is owned by opponent -> attack
			else {
	
				Random l_randomNumberGenerator = new Random();
				
				for(int i = 0; i < d_numberOfArmies; i++) {
					
					if(d_toCountry.getArmyNumber() == 0) {
						
						changeCountryOwnership(d_toCountry, d_fromCountry, d_numberOfArmies);
						return;
					}
					
					//Attacking army has a 60% chance of killing a defending army
					if((l_randomNumberGenerator.nextInt(10) + 1) <= 6) { //random int between 1 and 10 (inclusive)
						
						//Kill defending army
						d_toCountry.setArmyNumber(d_toCountry.getArmyNumber() - 1);
					}
					
					//Defending army has a 70% chance of killing a defending army
					if((l_randomNumberGenerator.nextInt(10) + 1) <= 7) { //random int between 1 and 10 (inclusive)
						
						//Kill attacking army
						d_fromCountry.setArmyNumber(d_fromCountry.getArmyNumber() - 1);
						d_numberOfArmies--;
						i--;
					}
				}
				
				if(d_toCountry.getArmyNumber() == 0 && d_numberOfArmies > 0) {
					
					changeCountryOwnership(d_toCountry, d_fromCountry, d_numberOfArmies);
				}
			}
		}
	}
	
	/**
	 * When an attacker conquers a defender's country, this method performs the exchange of the countries and armies. 
	 * 
	 * @param p_toCountry
	 * @param p_fromCountry
	 * @param p_numberOfArmies
	 */
	private void changeCountryOwnership(Country p_toCountry, Country p_fromCountry, int p_numberOfArmies) {
		
		//Loop through each player to find who owns p_toCountry
		GameContext.getGameContext().getPlayers().forEach(
				
			(l_playerName, l_player) -> {
				
				//Try removing the conquered country from the defender's list
				if(l_player.getConqueredCountries().remove(p_toCountry.getCountryID()) != null) {
					
					//Add conquered country to attacker's list
					this.getPlayer().getConqueredCountries().put(p_toCountry.getCountryID(), p_toCountry);
					
					//Update army counts
					p_fromCountry.setArmyNumber(p_fromCountry.getArmyNumber() - p_numberOfArmies);
					p_toCountry.setArmyNumber(p_numberOfArmies);
					
					//Set this variable to true to allow the player to collect a card at the end of the turn
					this.getPlayer().setConqueredACountryThisTurn(true);
					
					return;
				}
			}
		);		
	}
	
	/**
     * Override of valid check
     * @return true if valid
     */
    @Override
    public boolean valid(){        
    	boolean l_isValid = true;
      Player l_targetPlayer = d_fromCountry.getOwner();
      if(l_targetPlayer == null || !l_targetPlayer.getIsAlive()){
        GenericView.printWarning(String.format(" The player of target country is not alive or is Null." ));
        l_isValid = false;
      }
    	
    	//Check if fromCountry is owned by the current player
		if(!d_fromCountry.getOwner().equals(d_player)) {			
			GenericView.printWarning("Could not perform the advance order moving " + d_numberOfArmies + " armies from " + 
					d_fromCountry.getCountryName() + " to " + d_toCountry.getCountryName() + " because " + d_player.getName() + " does not own " + d_fromCountry + ".");
			
			l_isValid =  false;
		}
      
    //check if DIPLOMACY 
		if(this.d_gameContext.isDiplomacyInCurrentTurn(d_player, d_toCountry.getOwner())){
      			GenericView.printWarning("Could not perform the advance order moving " + d_numberOfArmies + " armies from " + 
					d_fromCountry.getCountryName() + " to " + d_toCountry.getCountryName() + " because they are Diplomacy in current turn.");
				l_isValid =  false;
		}
		
		//Check if fromCountry and toCountry are neighbors
		if(d_fromCountry.getNeighbors().get(d_toCountry.getCountryID()) == null) {			
			GenericView.printWarning("Could not perform the advance order moving " + d_numberOfArmies + " armies from " + 
					d_fromCountry.getCountryName() + " to " + d_toCountry.getCountryName() + " because they are not neighbors.");
			
			l_isValid =  false;
		}
		
    	return l_isValid;
    }

    /**
     * override of print the order
     */
    @Override
    public void printOrder(){
    	
    	GenericView.println("Advance Order created: " + d_player.getName() + " is sending " + d_numberOfArmies + " armies from " + d_fromCountry.getCountryName() + " to " + d_toCountry.getCountryName());
    }
}
