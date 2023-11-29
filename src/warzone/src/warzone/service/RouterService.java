package warzone.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import warzone.model.*;
import warzone.state.Phase;
import warzone.view.GenericView;

/**
 * This class can offer service related router to controllers.
 *
 */
public class RouterService {

	/**
	 * Router service
	 */
	private static RouterService ROUTER_SERVICE;

	/**
	 * game context
	 */
	private GameContext d_gameContext;

	/**
	 * game engine
	 */
	private GameEngine d_gameEngine;

	/**
	 * game phase
	 */
	private Phase d_gamePhase;

	/**
	 * the constructor of it, only can be used inside this class.
	 * @param p_gameEngine the current  Game Engine
	 */
	private RouterService(GameEngine p_gameEngine) {
		d_gameContext = p_gameEngine.getGameContext();
		d_gameEngine = p_gameEngine;		
	}	
	
	/**
	 * This method will return a routerService instance and create it if the instance
	 * is null.
	 * @param p_gameEngine the Game Engine instance
	 * @return the RouterService instance
	 */
	public static RouterService getRouterService( GameEngine p_gameEngine) {
		if( ROUTER_SERVICE == null)
			ROUTER_SERVICE = new RouterService(p_gameEngine);
		return ROUTER_SERVICE;
	}
	
	/**
	 * This method will parse a single console commands entered by the user and call the corresponding controller by controller name
	 * @param p_router the Router parsed from the command
	 * @throws IOException exception from reading the commands
	 */
	public void route(Router p_router) throws IOException{
		d_gamePhase = d_gameEngine.getPhase();
		switch(p_router.getControllerName()) {
		case COMMON:// actions of common
			switch(p_router.getActionName()) {
				case "help":
					d_gamePhase.help();
					break;	
				case "qamode":
					d_gameEngine.qaMode(p_router.getActionParameters());
					break;	
				case "reboot":
					d_gameEngine.reboot();
					break;						
				case "next":
					d_gamePhase.next();
					break;
			}
			break;
		case CONTINENT://actions of continent
			switch(p_router.getActionName()) {
				case "add":
					d_gamePhase.addContinent(p_router.getActionParameters());
					break;
				case "remove":
					d_gamePhase.removeContinent(p_router.getActionParameters());
					break;
			}
			break;
		case MAP://actions of map
			switch(p_router.getActionName().toLowerCase()) {
				case "savemap":
					d_gamePhase.saveMap(p_router.getActionParameters());
					break;
				case "editmap":
					d_gamePhase.editMap(p_router.getActionParameters());
					break;
				case "showmap":
					d_gamePhase.showMap();
					break;
				case "validatemap":
					d_gamePhase.validateMap();					
					break;
			}
			break;
		case COUNTRY://actions of country
			switch(p_router.getActionName()) {
				case "add":
					d_gamePhase.addCountry(p_router.getActionParameters());			
					break;
				case "remove":
					d_gamePhase.removeCountry(p_router.getActionParameters());
					break;
			}
			break;
		case NEIGHBOR://actions of neighbor
			switch(p_router.getActionName()) {
				case "add":
					d_gamePhase.addNeighbor(p_router.getActionParameters());
					break;
				case "remove":
					d_gamePhase.removeNeighbor(p_router.getActionParameters());
					break;
			}
			break;
		case GAMEPLAY://actions of gameplay
			switch(p_router.getActionName()) {
				case "showmap":
					d_gamePhase.showMap();
					break;
				case "play":
					d_gamePhase.play();
					break;
			}
			break;
		case STARTUP://actions in startup phase
			switch(p_router.getActionName()) {
				case "add":
					d_gamePhase.addPlayer(p_router.getActionParameters());
					break;
				case "remove":
					d_gamePhase.removePlayer(p_router.getActionParameters());
					break;
				case "loadmap":
					d_gamePhase.loadMap(p_router.getActionParameters());
					break;
				case "assigncountries":
					d_gamePhase.assigncountries();
					break;
				case "m":
					d_gamePhase.setTournamentMapFiles(p_router.getActionParameters().split("\\s+"));
					break;
				case "p":
					d_gamePhase.setTournamentPlayerStrategies(p_router.getActionParameters().split("\\s+"));
					break;
				case "g":
					d_gamePhase.setTournamentNumberOfGames(Integer.parseInt(p_router.getActionParameters()));
					break;
				case "d":
					d_gamePhase.setTournamentMaxTurns(Integer.parseInt(p_router.getActionParameters()));
					break;
			}
			break;
		case ERROR://error
			d_gamePhase.error();
			break;
		}		
	}
	
	/**
	 * This method can parse a list of commands
	 * @param p_routers the list of router
	 */
	public void route(List<Router> p_routers) {
		if(p_routers != null) {
			p_routers.forEach((l_router) ->{
				try {
					GenericView.printDebug("Excuting router: " + l_router.toString() );
					this.d_gameContext.setCurrentRouter(l_router);
					route(l_router);
				}
				catch(Exception l_ex){
					GenericView.printError("Exception occur: " + l_ex.toString());
				}
			});			
		}
	}
	
	/**
	 * This method parses the command entered by the player, and construct corresponding Router
	 * by different commands
	 * e.g.
	 * editcountry -add countryID continentID -remove countryID
	 * new Router(ControllerName.COUNTRY, "add", "countryID continentID");
	 * new Router(ControllerName.COUNTRY, "remove", "countryID");
	 * @param p_command command entered by the player
	 * @return error Router if the command is wrong
	 */
	public List<Router> parseCommand(String p_command) {
		List<Router> l_routerList = new LinkedList<Router>();
		
		//validation
		if( p_command == null || p_command.trim().equals("") ){
			l_routerList.add(createErrorRouter(ErrorType.MISSING_COMMAND.toString()));
			return l_routerList;
		}
			
		GenericView.printDebug("parseCommand: start to work on command: " + p_command);
				
		// remove prefix whitespace and convert the String to lower case 
		p_command = p_command.toLowerCase().trim();
				
		// split command with any number of whitespace
		String[] l_commandArray = p_command.split("\\s+");
		
		String l_firstWord = "," + l_commandArray[0] + ",";
		// TODO move these commands into the properties file
		String l_complexCommand = d_gameContext.getComplexCommand();
		String l_simpleCommand = d_gameContext.getSimpleCommand();
		 if(l_simpleCommand.indexOf(l_firstWord) > -1) {
				//simple command with only one router
				GenericView.printDebug("parseCommand: start to work on simple command: " + p_command);
				l_routerList.add(parseSimpleCommand(l_commandArray));				
		}		 
		else if(l_complexCommand.indexOf(l_firstWord) > -1) {
			//complex command with multiple routers
			GenericView.printDebug("parseCommand: start to work on complex command: " + p_command);
			l_routerList = parseComplexCommand(l_commandArray);
		}
		else {
			l_routerList.add(createErrorRouter(ErrorType.NO_SUCH_COMMAND.toString()));
			return l_routerList;
		}
		return l_routerList;
	}	
	
	/**
	 * A command can be divided into two types, complex command and simple command. 
	 * This method is responsible to parse complex commands, such as editCountry and editContinent, 
	 * and convert the command into a list of Router
	 * @param p_commandArray command divided by whitespace
	 * @return a Router list representing the command
	 */
	private List<Router> parseComplexCommand(String[] p_commandArray) {
		List<Router> l_routers = new LinkedList<Router>();
		List<Action> l_actions = parseCommandToAction(p_commandArray);		
		 
		if(l_actions.isEmpty() ) {
			l_routers.add(createErrorRouter(ErrorType.MISSING_PARAMETER.toString()));
			GenericView.printDebug("parseComplexCommand: Empty Action" );
			return l_routers;
		}

		//check and get the controller
		ControllerName l_controllerName = ControllerName.COMMON;		
		switch (p_commandArray[0]) {
			case "editcontinent":
				l_controllerName = ControllerName.CONTINENT;
				break;
			case "editcountry":
				l_controllerName = ControllerName.COUNTRY;				
				break;
			case "editneighbor":
				l_controllerName = ControllerName.NEIGHBOR;				
				break;
			case "gameplayer":
				l_controllerName = ControllerName.STARTUP;				
				break;
			case "tournament":
				l_controllerName = ControllerName.STARTUP;				
				break;
		}
		GenericView.printDebug("ControllerName is :" + l_controllerName.toString() );
		// if the action is not equal to 'add' or 'remove', we return an error router
		for(Action l_action: l_actions) {
			//TODO add it in the property file
			String l_actionArray = "-add,-remove,-m,-p,-g,-d";
			if(l_actionArray.indexOf(l_action.getAction()) > -1) { 
				String l_command = Arrays.toString(p_commandArray).replace(",", " ");
				Router l_router = new Router(l_controllerName, l_action.getAction(), l_action.getParameters(),l_command);
				
				l_routers.add(l_router);
				GenericView.printDebug("Add an action to a router");
			}
			else {
				l_routers = new LinkedList<Router>();
				l_routers.add(createErrorRouter(ErrorType.BAD_OPTION.toString()));
				GenericView.printDebug("Meet an error when adding an action to a router");
				return l_routers;
			}
		}		
		return l_routers;
	}
	
	/**
	 * This method is responsible to parse simple commands, such as showmap and validatemap, 
	 * and convert the command into a list of Router
	 * @param p_commandArray command divided by whitespace
	 * @return a Router list representing the command
	 */
	private Router parseSimpleCommand(String[] p_commandArray) {
		// create the router according to the command
		Router l_router = null;
		// the first element of commandArray is command
		String l_command = Arrays.toString(p_commandArray).replace(",", " ");
		switch (p_commandArray[0]) {
			case "reboot":
				l_router = new Router(ControllerName.COMMON, "reboot", l_command);
				break;	
			case "startup":
			case "mapeditor":
				l_router = new Router(ControllerName.COMMON, "changephase",p_commandArray[0],l_command);
				break;
			case  "help":
				l_router = new Router(ControllerName.COMMON, "help", l_command);
				break;	
			case  "qamode":
				String l_para =  p_commandArray.length > 1 ? p_commandArray[1] : "" ;
				l_router = new Router(ControllerName.COMMON, "qamode", l_para ,l_command);
				break;	
			case  "next":
				l_router = new Router(ControllerName.COMMON, "next", l_command);
				break;	
			case  "showmap":
					l_router = new Router(ControllerName.MAP, "showmap", l_command);
				break;
			case  "validatemap":
				l_router =  new Router(ControllerName.MAP, "validatemap", l_command);
				break;
			case  "play":
				l_router =  new Router(ControllerName.GAMEPLAY, "play", l_command);
				break;				
			case  "assigncountries":
				l_router =  new Router(ControllerName.STARTUP, "assigncountries", l_command);
				break;
			case  "savemap":
				if (p_commandArray.length == 1) {
					return createErrorRouter(ErrorType.MISSING_PARAMETER.toString(), l_command);
				}
				else if(p_commandArray.length == 2 ) {
					l_router =  new Router(ControllerName.MAP, "savemap", p_commandArray[1], l_command);
				}
				else {
					return createErrorRouter(ErrorType.TOO_MUCH_PARAMETERS.toString(), l_command);
				}
				break;
			case  "editmap":
				if (p_commandArray.length == 1) {
					return createErrorRouter(ErrorType.MISSING_PARAMETER.toString(), l_command);
				}
				if(p_commandArray.length == 2 ) {
					l_router =  new Router(ControllerName.MAP, "editmap", p_commandArray[1], l_command);
				}
				else {
					return createErrorRouter(ErrorType.TOO_MUCH_PARAMETERS.toString());
				}
				break;
			case  "loadmap":
				if (p_commandArray.length == 1) {
					return createErrorRouter(ErrorType.MISSING_PARAMETER.toString());
				}
				if(p_commandArray.length == 2 ) {
					l_router =  new Router(ControllerName.STARTUP, "loadmap", p_commandArray[1], l_command);
				}
				else {
					return createErrorRouter(ErrorType.TOO_MUCH_PARAMETERS.toString());
				}
				break;
			//TODO other routers for simple commands
		}
		return l_router;
	}

	/**
	 * This method can extract options and corresponding parameters from the command and construct
	 * Action for every option.
	 * @param p_commandArray command divided by whitespace
	 * @return a list of Action representing option and parameters
	 */
	private List<Action> parseCommandToAction(String[] p_commandArray) {
		List<Action> l_actions = new LinkedList<Action>();

		// in this loop we recognize option and according parameter
		for(int l_tempi = 1; l_tempi < p_commandArray.length; l_tempi++) {
			// judge that if p_commandArray[i] is an option
			if(p_commandArray[l_tempi].charAt(0) == '-') {
				// if an option is the last element of the array or there are two
				// continuous options, we still return missing parameter error
				if (l_tempi == p_commandArray.length - 1 || p_commandArray[l_tempi + 1].charAt(0) == '-') {
					return new LinkedList<Action>();
				}
				for (int l_tempj = l_tempi + 1; l_tempj < p_commandArray.length; l_tempj++) {
					if (p_commandArray[l_tempj].charAt(0) == '-') {
						Action l_action = new Action(p_commandArray[l_tempi].replace("-", ""), CommonTool.convertArray2String(p_commandArray, " ", l_tempi + 1, l_tempj - 1));
						l_actions.add(l_action);
						l_tempi = l_tempj;
					}
					if (l_tempj == p_commandArray.length - 1) {
						Action l_action = new Action(p_commandArray[l_tempi].replace("-", ""), CommonTool.convertArray2String(p_commandArray, " ", l_tempi + 1, l_tempj));
						l_actions.add(l_action);
						l_tempi = l_tempj;
					}
				}
			}
		}
		return l_actions;
	}
	
	/**
	 * This method will create the error controller by its error type.
	 * @param p_errorType the error type of the command
	 * @return Router representing error
	 */
	private Router createErrorRouter(String p_errorType) {
		return new Router(ControllerName.ERROR, p_errorType, "");
	}
	
	/**
	 * This method will create the error controller by its error type.
	 * @param p_errorType the error type of the command
	 * @param p_command the command for this action
	 * @return Router representing error
	 */
	private Router createErrorRouter(String p_errorType, String p_command) {
		return new Router(ControllerName.ERROR, p_errorType,p_command);
	}
	
	
}