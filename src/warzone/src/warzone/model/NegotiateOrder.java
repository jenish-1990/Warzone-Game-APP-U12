package warzone.model;

import java.util.Map;

import warzone.view.GenericView;
import warzone.view.MapView;

/**
 * This class represents one deploy order of the gameplay
 */
public class NegotiateOrder extends Order {

	private Player d_player;
	private Player d_targetPlayer;
	

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
			GenericView.printSuccess("The Diplomacy Order is invalid, because one or more player is not alive.");
			return;
		} 
		// add order to engine
		d_gameContext.addDiplomacyOrderToList(this);
		
		//print success information
		GenericView.printSuccess("Success to apply Diplomacy order.");
		
		printOrder();
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
		else
			return false;
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
		return String.format("Diplomacy order for player %s and player %s ",  this.d_player.getName(), this.d_targetPlayer.getName());		
	}
	
}
