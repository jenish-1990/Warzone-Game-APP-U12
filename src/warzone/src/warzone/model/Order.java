package warzone.model;

/**
 * This interface abstract the action of Order
 *
 */
public abstract class Order {
	
	protected OrderType d_orderType;
	
	protected GameContext d_gameContext;
	
	/**
	 * get Order Type
	 * @return Order Type
	 */
	public OrderType getOrderType() {
		return d_orderType;
	}
		
	
	/**
	 * This method will execute the order.
	 */
	public void execute() {}

	/**
	 * check if the order can be executed
	 * @return true if valid
	 */
	public boolean valid() {

		return true;
	}

	/**
	 * print the order
	 */
	public void printOrder() {
		
	}
	
}
