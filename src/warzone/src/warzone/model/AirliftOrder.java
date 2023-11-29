package warzone.model;

import warzone.view.GenericView;

import java.io.Serializable;

/**
 * This class represents one airlift order of the gameplay
 */
public class AirliftOrder extends Order implements Serializable {

    /**
     * airlift from country
     */
    private Country d_fromCountry;

    /**
     * airlift to country
     */
    private Country d_toCountry;
    /**
     * army numbers
     */
    private int d_armyNumber;
    /**
     * owner of the order
     */
    private Player d_player;

    /**
     * constructor
     * @param p_player player of the order
     * @param p_fromCountry airlift from country
     * @param p_toCountry airlift to country
     * @param p_armyNumber army number
     */
    public AirliftOrder(Player p_player, Country p_fromCountry, Country p_toCountry, int p_armyNumber){
    	d_player = p_player;
		d_fromCountry = p_fromCountry;
		d_toCountry = p_toCountry;
        d_armyNumber = p_armyNumber;
		this.d_orderType = OrderType.AIRLIFT;
    }

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
        	this.logExecution("Fail","The context does not satisfy the order" );
        	return;
        }
        int l_armyInTarget = d_toCountry.getArmyNumber() + d_armyNumber;
        int l_armyInSource = d_fromCountry.getArmyNumber() - d_armyNumber;
        d_toCountry.setArmyNumber(l_armyInTarget);
        d_fromCountry.setArmyNumber(l_armyInSource);
        
		//print success information
		GenericView.printSuccess("Success to execute order:" + toString());
		this.logExecution("Success", this.toString() );
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
        //check if players exist
        if( l_targetPlayer != null && l_targetPlayer != d_player){
            GenericView.printError("Target country does not belongs to the player.");
            return false;
        }
        //check if army number is more than 0
        if(d_armyNumber <= 0){
            GenericView.printError("The number of airlift army shoud more than 0.");
            return false;
        }
        //if does not have enough army, adjust the army number
		if (this.d_fromCountry.getArmyNumber() <  this.d_armyNumber) {
			d_armyNumber = this.d_fromCountry.getArmyNumber();
			GenericView.printWarning("The country does not have enough army to airlift, then the airlift army number is adjusted to " + this.d_fromCountry.getArmyNumber());
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
