package warzone.controller;

import warzone.view.*;
import warzone.model.*;

public class CommonController {
	
	private GameContext d_gameContext;

	public CommonController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	public String welcome(String p_actionParameters) {
		String body = "Welcome to Warzone";
		GenericView.println(body);
		return body;
	}
	

	
	public void standby() {}
}
