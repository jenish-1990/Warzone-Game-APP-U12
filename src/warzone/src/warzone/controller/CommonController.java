package warzone.controller;

import warzone.view.*;
import warzone.model.*;

/**
 * Common controller is for manipulate the welcome and help action
 */
public class CommonController {
	
	private GameContext d_gameContext;

	/**
	 * constructor
	 */
	public CommonController() {
		
	}

	/**
	 * constructor with setting game context
	 * @param p_gameContext gameContext
	 */
	public CommonController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}

	/**
	 * printout welcome sentence
	 * @param p_actionParameters parameters for action
	 * @return true if success
	 */
	public boolean welcome(String p_actionParameters) {
		HelpView.printWelcome();
		return true;
	}

	/**
	 * printout help menu
	 * @return true if success
	 */
	public boolean help() {
		HelpView.printHelp(d_gameContext.getGamePhase());
		return true;
	}
}
