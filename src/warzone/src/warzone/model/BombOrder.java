package warzone.model;

import warzone.view.GenericView;

/**
 * This class represents the bomb card in the game.
 * @author zexin
 *
 */
public class BombOrder extends Order{
	
	/**
	 * target Country
	 */
    private Country d_targetCountry;
    	
    /** 
     * current player
     */
    private Player d_player;
	
	/**
	 * This method is the constructor of the class.
	 * @param p_player the current Player
	 * @param p_targetCountry the target country 
	 */
	public BombOrder(Player p_player, Country p_targetCountry) {
    	d_targetCountry = p_targetCountry;
        d_player=p_player;
		this.d_orderType = OrderType.BOMB;
        d_gameContext = GameContext.getGameContext();  
	}
	
//    /**
//     * set the player of the order
//     * @param p_player the player
//     */
//    public void setPlayer(Player p_player){
//        d_player = p_player;
//    }

    /**
     * get the player of the order
     * @return the player
     */
    public Player getPlayer() {
        return  d_player;
    }

    /**
     * This method will execute the current order.
     */
	@Override
	public void execute() {
        if(!valid()) {
        	GenericView.printWarning("Fail to execute order:" + toString());
        	return;
        }
        
        d_targetCountry.setArmyNumber( d_targetCountry.getArmyNumber() / 2); 

		//print success information
		GenericView.printSuccess("Success to execute order:" + toString());
	}

	/**
	 * This method is responsible to check the validity of the current order.
	 * @return false if the current order is invalid
	 */
	@Override
	public boolean valid() {
        
        //check whether the target country belongs to the player
        if(d_player.getConqueredCountries().containsValue(d_targetCountry)){
            GenericView.printError("The player cannot destroy armies in his own country.");
            return false;
        }
        
		//check if DIPLOMACY 
		if( d_targetCountry.getOwner()!= null && this.d_player != null 
				&& this.d_gameContext.isDiplomacyInCurrentTurn(d_player, d_targetCountry.getOwner())){
      			GenericView.printWarning(String.format("The player [%s] and [%s] are in Diplomacy in current turn.", this.d_player.getName(), d_targetCountry.getOwner() ));
      		    return false;
		}		
		
        //check whether the target country is adjacent to one of the countries that belong to the player
        boolean l_isAdjacent = false;
        for (Integer l_conqueredCountryId : d_player.getConqueredCountries().keySet()) {
        	if (d_player.getConqueredCountries().get(l_conqueredCountryId).getNeighbors().containsValue(d_targetCountry)) {
        		//d_targetCountry = d_player.getConqueredCountries().get(l_conqueredCountryId).getNeighbors().get(d_targetCountry);
        		l_isAdjacent = true;
        		break;
        	}
        }
        if (!l_isAdjacent) {
        	GenericView.printError("The target country is not adjacent to one of the countries that belong to the player.");
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
		return String.format("Bomb Order, issued by player [%s], bombing [%s]",  this.d_player.getName(), d_targetCountry.getCountryName() );		
	}

}
