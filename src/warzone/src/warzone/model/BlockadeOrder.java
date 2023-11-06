package warzone.model;

import warzone.view.GenericView;

/**
 *  Blockade Order
 *  the implimentation of Blockading a country
 * @author fzuray
 *
 */
public class BlockadeOrder extends Order {	
    

	/**
	 * target Country
	 */
    private Country d_targetCountry;
    
    /** 
     * current player
     */
    private Player d_player;
    
    /**
     * constructor of blockade order
     * @param p_player current player
     * @param p_targetCountry target country 
     */
    public BlockadeOrder(Player p_player,Country p_targetCountry) {
    	d_targetCountry = p_targetCountry;
        d_player=p_player;
		this.d_orderType = OrderType.BLOCKADE;
		this.d_gameContext = GameContext.getGameContext();
    }

    /**
     *  execute blockade order.
     */
	@Override
	public void execute() {
        if(!valid()) {
        	GenericView.printWarning("Fail to execute order:" + toString());
        	this.logExecution("Fail","The context does not satisfy the order" );
        	return;
        }
        
		//triple the number of armies on one of the current player's territories
		d_targetCountry.setArmyNumber(d_targetCountry.getArmyNumber()*3);
		//set country state to neutral
		d_targetCountry.setCountryState(CountryState.Neutral, null);
		//set owner to null
		//d_targetCountry.setOwner(null);
		
		//print success information
		GenericView.printSuccess("Success to execute order:" + toString());
		this.logExecution("Success", this.toString() );
	}

    /**
     * check if the order can be executed
     * @return true if valid
     */
	@Override
	public boolean valid() {
		if(d_targetCountry ==null) {			
			GenericView.printError("target country should not be null.");
			return false;
		}
		if(!d_player.getIsAlive()) {
			GenericView.printError("Player "+d_player.getName()+" is dead!");
			return false;
		}
		if(d_targetCountry.getOwner() != this.d_player) {
			GenericView.printError("Blockade order invalid:target country not belong to current player!");
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
	
	/**;
	 * override of print the order
	 */
	@Override
	public String toString(){
		return String.format("Blockade Order, issued by player [%s], blockading [%s]",  this.d_player.getName(), d_targetCountry.getCountryName() );		
	}
}
