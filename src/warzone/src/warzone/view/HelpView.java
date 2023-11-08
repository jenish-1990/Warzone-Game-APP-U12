package warzone.view;

import java.util.Map;

import warzone.model.*;

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
     * @param p_gameContext the current game context
     */
    public static void printStatus(GameContext p_gameContext) {
    	System.out.print(String.format("[Status] Game Phase:%s | Demo Mode:%s | Debug Mode:%s | ", 
    			p_gameContext.getGamePhase(), p_gameContext.getIsDemoMode(), p_gameContext.getIsDebug()));
    	System.out.println(String.format("Player:%s | Continent:%s | Country:%s | Map:",
    			p_gameContext.getPlayers().size(), p_gameContext.getContinents().size(), p_gameContext.getCountries().size(), p_gameContext.getMapFileName()));
    }
    
    /**
     * This method will print out the message how to use every command according to
     * the current phase.
     * @param p_gamePhase the current phase of the game.
     */
    public static void printHelp(GamePhase p_gamePhase ) {
    	System.out.println(String.format("***********************************************    HELP IN PHASE [ %s ]  *********************************",  p_gamePhase));    	
    	System.out.println("Commands available for current game phase: " + p_gamePhase);
    	switch(p_gamePhase) {
	    	case MAPEDITOR:
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
	    	case STARTUP:
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
	    	case PLAY:
	    		System.out.println(""
	    				+ " You are in game PLAYING phase, you can deploy armies. \n"
	    				+ " -  deploy countryID num\n"
	    				+ " \n"
	    				+ " -  mapeditor \n"
	    				+ " -  startup \n"	    				);
	    		break;
    	}
    	System.out.println(String.format("***********************************************    HELP IN PHASE [ %s ]  *********************************",  p_gamePhase));    	
    }
}
