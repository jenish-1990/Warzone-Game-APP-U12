package warzone.model;

/**
 * This class represents Router in the game which can route the command parsed by
 * command parser to the corresponding controller.
 *
 */
public class Router {
	/**
	 * This constructor can initiate the Router.
	 * @param p_controllerName the name of controller
	 * @param p_actionName the action of the controller, such as 'add' and 'remove'
	 * @param p_actionParameters the parameters of action parsed by the command parser
	 */
	public Router(ControllerName p_controllerName, String p_actionName, String p_actionParameters)
	{
		this.d_controllerName = p_controllerName;
		this.d_actionName = p_actionName;
		this.d_actionParameters = p_actionParameters;
	}
	
	/**
	 * This constructor can initiate the Router without parameters
	 * @param p_controllerName the name of controller
	 * @param p_actionName the action of the controller
	 */
	public Router(ControllerName p_controllerName, String p_actionName)
	{
		this.d_controllerName = p_controllerName;
		this.d_actionName = p_actionName;
	}
	
	
	private ControllerName d_controllerName;
	
	private String d_actionName;
	
	private String d_actionParameters;	
	
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
	 */
	public String toString() {
		return String.format("%s | %s | %s" ,d_controllerName.toString() ,d_actionName, getActionParameters());
	}
}


