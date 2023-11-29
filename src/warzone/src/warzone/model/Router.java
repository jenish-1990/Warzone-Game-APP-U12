package warzone.model;

import java.io.Serializable;

/**
 * This class represents Router in the game which can route the command parsed by
 * command parser to the corresponding controller.
 *
 */
public class Router implements Serializable {
	/**
	 * This constructor can initiate the Router.
	 * @param p_controllerName the name of controller
	 * @param p_actionName the action of the controller, such as 'add' and 'remove'
	 * @param p_actionParameters the parameters of action parsed by the command parser
	 * @param p_command the command for this action
	 */
	public Router(ControllerName p_controllerName, String p_actionName, String p_actionParameters, String p_command) {
		this.d_controllerName = p_controllerName;
		this.d_actionName = p_actionName;
		this.d_actionParameters = p_actionParameters;
		this.d_command = p_command;
	}
	
	/**
	 * This constructor can initiate the Router without parameters
	 * @param p_controllerName the name of controller
	 * @param p_actionName the action of the controller
	 * @param p_command the command for this action
	 */
	public Router(ControllerName p_controllerName, String p_actionName, String p_command) {
		this.d_controllerName = p_controllerName;
		this.d_actionName = p_actionName;
		this.d_command = p_command;
	}
	
	/**
	 * Controller Name
	 */
	private ControllerName d_controllerName;
	/**
	 *  action Name
	 */
	private String d_actionName;
	/**
	 *  action Parameters
	 */
	private String d_actionParameters;	
	/**
	 *  command
	 */
	private String d_command;
	
	
	/**
	 * This method will show the command
	 * @return the command
	 */
	public String getCommand(){
		return d_command;
	}
	
	/**
	 * This method will show the name of the controller
	 * @return the name of the controller
	 */
	public ControllerName getControllerName(){
		return d_controllerName;
	}

	/**
	 * This method will show the name of the action.
	 * @return the name of the action
	 */
	public String getActionName(){
		return d_actionName;
	}

	/**
	 * This method will show the parameters of the action.
	 * @return the parameters of the action
	 */
	public String getActionParameters(){
		return d_actionParameters;
	}

	/**
	 * This method can convert Router to String class.
	 * @return the controller name and the actionname and its parameters
	 */
	public String toString() {
		return String.format("%s | %s | %s" ,d_controllerName.toString() ,d_actionName, getActionParameters());
	}
}


