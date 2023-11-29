package warzone.model;
import java.io.Serializable;
import java.util.List;

import warzone.service.GameEngine;


/**
 *	Strategy of the Strategy pattern
 */
public abstract class PlayerStrategy implements Serializable {

	/**
	 * the owner player
	 */
	Player d_player; 
	
	/**
	 * Get Game Context
	 */
	protected GameContext d_gameContext;
	/**
	 * Get Game Engine
	 */
	protected GameEngine d_gameEngine ;
	

	/**
	 *  abstract of createOrder
	 * @return Order
	 */
	public abstract Order createOrder();	
	
	/**
	 * constructor of 
	 * @param p_player PlayerStrategy
	 */
	PlayerStrategy(Player p_player){
		d_player = p_player; 
		d_gameContext = GameContext.getGameContext();
		d_gameEngine = GameEngine.getGameEngine(d_gameContext);
	}
}
