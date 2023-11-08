package warzone.view;

import java.util.Map;

import warzone.model.*;
import warzone.service.GameEngine;
import warzone.state.Phase;

/**
 * This class can print some help hints to players.
 *
 */
public class HelpView {

	/**
	 * This method can print welcome page.
	 */
    public static void printWelcome() {
    	System.out.println("");
    	System.out.println("        #    #   ##   #####  ######  ####  #    # ######"); 
    	System.out.println("        #    #  #  #  #    #     #  #    # ##   # #      ");
    	System.out.println("        #    # #    # #    #    #   #    # # #  # #####  ");
    	System.out.println("        # ## # ###### #####    #    #    # #  # # #      ");
    	System.out.println("        ##  ## #    # #   #   #     #    # #   ## #      ");
    	System.out.println("        #    # #    # #    # ######  ####  #    # ######  By 6441 Group #22");
    	System.out.println("");
    }

    
    /**
     * This method will print current status of the game from the game context.
     * @param p_gameEngine the current Game Engine
     */
    public static void printStatus(GameEngine p_gameEngine) {
    	GameContext l_gameContext = p_gameEngine.getGameContext();
    	System.out.print(String.format("[Status] Game Phase:%s | Demo Mode:%s | Debug Mode:%s | ", 
    			p_gameEngine.getPhase(), l_gameContext.getIsDemoMode(), l_gameContext.getIsDebug()));
    	System.out.println(String.format("Player:%s | Continent:%s | Country:%s",
    			l_gameContext.getPlayers().size(), l_gameContext.getContinents().size(), l_gameContext.getCountries().size(), l_gameContext.getMapFileName()));
    }
    
    /**
     * This method will print out the message how to use every command according to
     * the current phase.
     * @param p_phase the current phase of the game.
     */
    public static void printHelp(Phase p_phase ) {
    	System.out.println(String.format("*****************************************    HELP IN PHASE [ %s ]  *********************************",  p_phase));    	
    	System.out.println("Commands available for current game phase: " + p_phase);
    	switch(p_phase.toString().toUpperCase()) {
	    	case "MAPEDITOR":
	    		System.out.println(""
	    				+ " -  editmap filename\n"
	    				+ " -  savemap filename\n"
	    				+ " -  showmap\n"
	    				+ " -  validatemap\n"	    				
	    				+ " -  editcontinent -add continentID continentvalue -remove continentID\n"
	    				+ " -  editcountry -add countryID continentID -remove countryID\n"
	    				+ " -  editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID\n"
	    				+ " -  startup \n"
	    				+ " -  reboot \n");

	    		break;
	    	case "STARTUP":
	    		System.out.println(""
	    				+ " -  loadmap filename\n"
	    				+ " -  showmap\n"
	    				+ " -  validatemap\n"	    				
	    				+ " -  gameplayer -add playername -remove playername\n"
	    				+ " -  assigncountries\n"
	    				+ " -  play                      [will start the game engine and go to next phase.]\n"
	    				+ " -  mapeditor \n"
	    				+ " -  reboot \n");
	    		break;
	    	case "PLAY":
	    		System.out.println(""
	    				+ " You are in game PLAYING phase, you can deploy armies. \n"
	    				+ " -  deploy countryID num\n"
	    				+ " \n"
	    				+ " -  showmap \n"
	    				+ " -  mapeditor \n"
	    				+ " -  play \n"
	    				+ " -  startup \n"	    				);
	    		break;
	    		//todo: add other info
    	}
    	System.out.println(String.format("*****************************************    HELP IN PHASE [ %s ]  *********************************",  p_phase));    	
    }
}
