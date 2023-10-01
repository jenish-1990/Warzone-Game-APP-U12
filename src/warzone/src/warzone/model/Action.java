package warzone.model;

/**
 * Action is the model of command
 */
public class Action {
	private String d_action;
	private String d_parameters;

	/**
	 * Constructor that set the action and the parameters
	 * @param p_action the action name
	 * @param p_parameters the parameters to handle
	 */
	public Action(String p_action, String p_parameters) {
		this.d_action = p_action;
		this.d_parameters = p_parameters;
	}

	/**
	 * get action
	 * @return the action string
	 */
	public String getAction() {
		return d_action;
	}

	/**
	 * get parameters
	 * @return the parameters string
	 */
	public String getParameters() {
		return d_parameters;
	}
}
