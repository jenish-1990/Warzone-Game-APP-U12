package warzone.model;

public class Action {
	private String d_action;
	private String d_parameters;
	
	
	public  Action(String p_action, String p_parameters) {
		this.d_action = p_action;
		this.d_parameters = p_parameters;
	}
	
	public String getAction() {
		return d_action;
	}
	
//	public void setAction(String p_action) {
//		this.d_action = p_action;
//	}
	
	public String getParameters() {
		return d_parameters;
	}
	
//	public void setParameters(String p_parameters) {
//		this.d_parameters = p_parameters;
//	}
	
}
