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
	public void execute();

	/**
	 * check if the order can be executed
	 * @return true if valid
	 */
	public boolean valid();

	/**
	 * print the order
	 */
	public void printOrder();
	
}
