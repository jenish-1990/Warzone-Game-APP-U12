package warzone.model;

import warzone.view.GenericView;

public class AirliftOrder extends Order{

//    private int d_sourceCountryId;
//    private int d_targetCountryId;
    private Country d_fromCountry;
    private Country d_toCountry;
    private int d_armyNumber;
    private Player d_player;

    /**
     * constructor of airlift order
     * @param p_sourceCountryId source country
     * @param p_tragetCountryId target country
     * @param p_armyNumber army number
     */
    public AirliftOrder(Player p_player, Country p_fromCountry, Country p_toCountry, int p_armyNumber){
    	d_player = p_player;
		d_fromCountry = p_fromCountry;
		d_toCountry = p_toCountry;
        d_armyNumber = p_armyNumber;
		this.d_orderType = OrderType.AIRLIFT;
		this.d_gameContext = GameContext.getGameContext();  
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
     *  execute airlift order.
     */
    @Override
    public void execute(){
        if(!valid()) {
        	GenericView.printWarning("Fail to execute order:" + toString());
        	return;
        }
        int l_armyInTarget = d_toCountry.getArmyNumber() + d_armyNumber;
        int l_armyInSource = d_fromCountry.getArmyNumber() - d_armyNumber;
        d_toCountry.setArmyNumber(l_armyInTarget);
        d_fromCountry.setArmyNumber(l_armyInSource);
        
		//print success information
		GenericView.printSuccess("Success to execute order:" + toString());
    }

    /**
     * check if the order can be executed
     * @return true if valid
     */
    @Override
    public boolean valid(){
        //check if countries belongs to the player
        if(!d_player.getConqueredCountries().containsValue(d_fromCountry) ){
            GenericView.printError("Source country does not belongs to the player.");
            return false;
        }
        Player l_targetPlayer = d_toCountry.getOwner();
        if( l_targetPlayer != null && l_targetPlayer != d_player && d_toCountry.getArmyNumber() > 0 ){
            GenericView.printError("Target country does not belongs to the player.");
            return false;
        }        
 
		if (this.d_fromCountry.getArmyNumber() <  this.d_armyNumber) {
			d_armyNumber = this.d_player.getArmiesToDeploy();
			GenericView.printWarning("The country does not have enough army to airlift, then the airlift army number is adjusted to " + d_armyNumber);
		}	
		
        //check if army number is more than 0
        if(d_armyNumber <= 0){
            GenericView.printError("The number of airlift army shoud more than 0.");
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
		return String.format("Airlift Order, issued by player [%s], airlifting [%s] armies from  [%s] to [%s]",  this.d_player.getName(), d_armyNumber, d_fromCountry.getCountryName(),  d_toCountry.getCountryName() );		
	}
}