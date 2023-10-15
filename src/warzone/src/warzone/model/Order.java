package warzone.model;

/**
 * This interface abstract the action of Order
 *
 */
public interface Order {

	/**
	 * This method will execute the order.
	 * @return true if the order has been executed successfully
	 */
	public boolean execute();
	
}
