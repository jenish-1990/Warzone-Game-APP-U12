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
		//HelpView.printHelp(d_gameContext.getGamePhase());
		return true;
	}
	
	/**
	 * Change the game phase, and then other commands will be available.
	 * @param p_actionParameters the new game phase.
	 * @return true if succeed to change the game phase.
	 */
	public boolean changePhase(String p_actionParameters) {
//		if(p_actionParameters.equals("reboot") || p_actionParameters.equals("mapeditor") ) {
//			d_gameContext.reset();
//			d_gameContext.setGamePhase(GamePhase.MAPEDITOR);	
//		}
//		else if(p_actionParameters.equals("startup")) {
//			d_gameContext.reset();
//			d_gameContext.setGamePhase(GamePhase.STARTUP);	
//		}
//		
//		GenericView.printSuccess("Warzone is in the phase :" + d_gameContext.getGamePhase());
//		HelpView.printHelp(d_gameContext.getGamePhase() );
		return true;
	}	
	
}
