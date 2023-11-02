package warzone.view;

import warzone.model.GameContext;
import warzone.model.GamePhase;
import warzone.service.GameEngine;

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
    			p_gameEngine.getPhase().getGamePhase(), l_gameContext.getIsDemoMode(), l_gameContext.getIsDebug()));
    	System.out.println(String.format("Player:%s | Continent:%s | Country:%s",
    			l_gameContext.getPlayers().size(), l_gameContext.getContinents().size(), l_gameContext.getCountries().size(), l_gameContext.getMapFileName()));
    }
    
    /**
     * This method will print out the message how to use every command according to
     * the current phase.
     * @param p_gamePhase the current phase of the game.
     */
    public static void printHelp(GamePhase p_gamePhase ) {
    	System.out.println(String.format("*****************************************    HELP IN PHASE [ %s ]  *********************************",  p_gamePhase));    	
    	System.out.println("Commands available for current game phase: " + p_gamePhase);
    	switch(p_gamePhase.toString().toUpperCase()) {
	    	case "MAPEDITOR":
	    		System.out.println(""
	    				+ " -  editmap filename\n"
	    				+ " -  savemap filename\n"
	    				+ " -  showmap\n"
	    				+ " -  validatemap\n"	    				
	    				+ " -  editcontinent -add continentID continentvalue -remove continentID\n"
	    				+ " -  editcountry -add countryID continentID -remove countryID\n"
	    				+ " -  editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID\n"
						+ " -  next    [will go to next phase]\n"
	    				+ " -  reboot \n");

	    		break;
	    	case "STARTUP":
	    		System.out.println(""
	    				+ " -  loadmap filename\n"
	    				+ " -  showmap\n"
	    				+ " -  gameplayer -add playername -remove playername\n"
	    				+ " -  assigncountries\n"
						+ " -  next\n"
	    				+ " -  reboot \n");
	    		break;
			case "REINFORCEMENT":
				System.out.println(""
						+ " -  next\n"
						+ " -  showmap\n"
						+ " -  reboot \n");
				break;
	    	case "ISSUEORDER":
	    		System.out.println(""
	    				+ " You are in game ISSUR_ORDER phase, you can issue orders. \n"
						+ " -  play   [start to issue order]\n"
						+ " -  deploy countryID num\n"
	    				+ " -  advance countrynamefrom countynameto numarmies\n"
						+ " -  bomb countryID\n"
						+ " -  blockade countryID\n"
						+ " -  airlift sourcecountryID targetcountryID numarmies\n"
						+ " -  negotiate playerID\n"
	    				+ " -  showmap \n"
						+ " -  done   [finish order]\n");
	    		break;
			case "ORDEREXECUTION":
				System.out.println(""
						+ " You are in game ORDER_EXECUTION phase, you can issue orders. \n"
						+ " -  next   [will go to next phase]\n"
						+ " -  play   [start to execute order]\n"
						+ " -  showmap \n");
				break;
    	}
    	System.out.println(String.format("*****************************************    HELP IN PHASE [ %s ]  *********************************",  p_gamePhase));    	
    }
}
