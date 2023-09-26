package warzone.model;

public class Router {
	
	public Router(ControllerName p_controllerName, String p_actionName, String p_actionParameters)
	{
		this.d_controllerName = p_controllerName;
		this.d_actionName = p_actionName;
		this.d_actionParameters = p_actionParameters;
	}
	public Router(ControllerName p_controllerName, String p_actionName)
	{
		this.d_controllerName = p_controllerName;
		this.d_actionName = p_actionName;
	}
	
	
	private ControllerName d_controllerName;
	
	private String d_actionName;
	
	private String d_actionParameters;	
	
	public ControllerName getControllerName(){
		return d_controllerName;
	}
	public String getActionName(){
		return d_actionName;
	}
	public String getActionParameters(){
		return d_actionParameters;
	}
	public String toString() {
		return String.format("%s | %s | %s" ,d_controllerName.toString() ,d_actionName, getActionParameters());
	}
}


