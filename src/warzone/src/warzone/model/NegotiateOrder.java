package warzone.model;

import warzone.view.GenericView;

/**
 * This class represents one deploy order of the gameplay
 */
public class NegotiateOrder extends Order {

	/**
	 * owner of the order
	 */
	private Player d_player;
	/**
	 * target player
	 */
	private Player d_targetPlayer;

	/**
	 * constructor of negotiate order
	 * @param p_player owner of the order
	 * @param p_targetPlayer target of the order
	 */
	public NegotiateOrder(Player p_player,Player p_targetPlayer) {
		d_targetPlayer = p_targetPlayer;
		d_player = p_player;
		this.d_orderType = OrderType.NEGOTIATE;
		this.d_gameContext = GameContext.getGameContext(); 
	}

	/**
	 * the owner of this order
	 * @return the player
	 */
	public Player getPlayer() {
		return d_player;
	}
	
	/**
	 * the owner of Target order
	 * @return the Target player
	 */
	public Player getTargetPlayer() {
		return this.d_targetPlayer;
	}

	/**
	 * override of the execution of the order
	 */
	@Override
	public void execute() {
        if(!valid()) {
        	GenericView.printWarning("Fail to execute order:" + toString());
    		this.logExecution("Fail","The context does not satisfy the order" );
        	return;
        }
        
		// add order to engine
		d_gameContext.addDiplomacyOrderToList(this);
		
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
		if(d_player != null && d_player.getIsAlive() 
				&& d_targetPlayer != null && d_player.getIsAlive() 
				&&  d_player != d_targetPlayer)
			return true;
		else {
			GenericView.printWarning("One of the indecated player is not alive.");
			return false;
		}
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
		return String.format("Negotiate order, issued by player [%s], negotiating with player [%s] ",  this.d_player.getName(), this.d_targetPlayer.getName());		
	}
	
}
