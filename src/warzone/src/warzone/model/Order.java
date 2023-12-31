package warzone.model;

/**
 * This abstract class of the action of Order
 *
 */
public abstract class Order {
	
	/**
	 * current Order Type
	 */
	protected OrderType d_orderType;
	/**
	 * command which create this order
	 */
	protected String d_command;

	/**
	 * get game context
	 * @return Current Game Context
	 */
	public GameContext getGameContext() {
		return GameContext.getGameContext();
	}
	
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
	public abstract void execute();

	/**
	 * check if the order can be executed
	 * @return true if valid
	 */
	public abstract boolean valid() ;

	/**
	 * print the order
	 */
	public abstract void printOrder() ;

	/**
	 * set command which create this order
	 * @param p_command given command
	 */
	public void setCommand(String p_command) {
		d_command = p_command;
	}

	/**
	 * get command which create this order
	 * @return the command
	 */
	public String getCommand() {
		return d_command;
	}
	
	/**
	 *  log execution of order
	 * @param p_result given result
	 * @param p_message given message
	 */
	protected void logExecution(String p_result, String p_message) {
		GameContext.getGameContext().getLogEntryBuffer().logExecuteOrder(p_result, p_message, this);
	}
}
