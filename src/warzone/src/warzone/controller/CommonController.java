package warzone.controller;

import warzone.view.*;
import warzone.model.*;

public class CommonController {
	
	private GameContext d_gameContext;
	
	public CommonController() {
		
	}

	public CommonController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}
	
	public boolean welcome(String p_actionParameters) {
		HelpView.printWelcome();
		return true;
	}
	

	
	public boolean help() {
		HelpView.printHelp(d_gameContext.getGamePhase());
		return true;
	}
}
