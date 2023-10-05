package warzone.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import warzone.controller.*;
import warzone.model.*;
import warzone.view.GenericView;

/**
 * This class can offer service related router to controllers.
 *
 */
public class RouterService {
		
	private static RouterService ROUTER_SERVICE;
	
	private GameContext d_gameContext;

	/**
	 * the constructor of it, only can be used inside this class.
	 * @param p_gameContext the current game context
	 */
	private RouterService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}	
	
	/**
	 * This method will return a routerService instance and create it if the instance
	 * is null.
	 * @param p_gameContext the game context instance
	 * @return the RouterService instance
	 */
	public static RouterService getRouterService(GameContext p_gameContext) {
		if( ROUTER_SERVICE == null)
			ROUTER_SERVICE = new RouterService(p_gameContext);
		return ROUTER_SERVICE;
	}
	/**
	 * Check if current game phase is included in the given game phases list.
	 * @param p_gamePhases Given game phases list
	 * @return True if included, otherwise false.
	 */
	private boolean getIsContainCurrentPhase(List<GamePhase> p_gamePhases) {
		if( d_gameContext.getIsContainCurrentPhase(p_gamePhases))
			return true;
		else {
			GenericView.printWarning("The command is not valid in the current phase: " + d_gameContext.getGamePhase());
			return false;
		}
	}
	
	/**
	 * This method will parse a single console commands entered by the user and call the corresponding controller by controller name
	 * @param p_router the Router parsed from the command
	 * @throws IOException exception from reading the commands
	 */
	public void route(Router p_router) throws IOException{
		ControllerFactory l_controllerFactory = ControllerFactory.getControllerFactory();
		switch(p_router.getControllerName()) {
			case COMMON:
				if(!getIsContainCurrentPhase( Arrays.asList(GamePhase.MAPEDITOR,GamePhase.PLAY, GamePhase.STARTUP) ))
					break;
				CommonController l_commonController = l_controllerFactory.getCommonController();
				switch(p_router.getActionName()) {
					case "welcome":
						l_commonController.welcome(p_router.getActionParameters());
						break;
					case "help":
						l_commonController.help();
						break;
					case "changephase":
						l_commonController.changePhase(p_router.getActionParameters());
						break;						
				}
				break;
			case CONTINENT:
				if(!getIsContainCurrentPhase( Arrays.asList(GamePhase.MAPEDITOR) ))
					break;
				ContinentController l_continentController= l_controllerFactory.getContinentController();
				switch(p_router.getActionName()) {
					case "add":
						GenericView.printDebug("route: CONTINENT-add- " + p_router.getActionParameters());
						l_continentController.addContinent(p_router.getActionParameters());
						break;
					case "remove":
						GenericView.printDebug("route: CONTINENT-remove-" + p_router.getActionParameters());
						l_continentController.removeContinent(p_router.getActionParameters());
						break;
				}
				break;
			case MAP:
				MapController l_mapController= l_controllerFactory.getMapController();
				switch(p_router.getActionName().toLowerCase()) {
					case "savemap":
						if(getIsContainCurrentPhase( Arrays.asList(GamePhase.MAPEDITOR) ))
							l_mapController.saveMap(p_router.getActionParameters());
						break;
					case "editmap":
						if(getIsContainCurrentPhase( Arrays.asList(GamePhase.MAPEDITOR) ))
							l_mapController.editMap(p_router.getActionParameters());
						break;
					case "showmap":
						if(getIsContainCurrentPhase( Arrays.asList(GamePhase.MAPEDITOR) ))
							l_mapController.showMap();
						break;
					case "validatemap":
						if(getIsContainCurrentPhase( Arrays.asList(GamePhase.MAPEDITOR, GamePhase.STARTUP) ))
							l_mapController.validateMap();
						break;
				}
				break;
			case COUNTRY:
				if(!getIsContainCurrentPhase( Arrays.asList(GamePhase.MAPEDITOR) ))
					break;
				CountryController l_countryController= l_controllerFactory.getCountryController();
				switch(p_router.getActionName()) {
					case "add":
						l_countryController.addCountry(p_router.getActionParameters());
						break;
					case "remove":
						l_countryController.removeCountry(p_router.getActionParameters());
						break;
				}
				break;
			case NEIGHBOR:
				if(!getIsContainCurrentPhase( Arrays.asList(GamePhase.MAPEDITOR) ))
					break;
				NeighborController l_neighborController= l_controllerFactory.getNeighborController();

				switch(p_router.getActionName()) {
					case "add":
						l_neighborController.addNeighbor(p_router.getActionParameters());
						break;
					case "remove":
						l_neighborController.removeNeighbor(p_router.getActionParameters());
						break;
				}
				break;
			case GAMEPLAY:
				if(!getIsContainCurrentPhase( Arrays.asList(GamePhase.STARTUP) ))
					break;
				GameplayController l_gameplayController = l_controllerFactory.getGameplayController();

				switch(p_router.getActionName()) {
					case "showmap":
						l_gameplayController.showMap();
						break;
					case "play":
						l_gameplayController.play();
						break;
				}
				break;
			case STARTUP:
				if(!getIsContainCurrentPhase( Arrays.asList(GamePhase.STARTUP) ))
					break;
				StartupController l_startupController = l_controllerFactory.getStartupController();
				switch(p_router.getActionName()) {
					case "add":
						l_startupController.addPlayer(p_router.getActionParameters());
						break;
					case "remove":
						l_startupController.removePlayer(p_router.getActionParameters());
						break;
					case "loadmap":
						l_startupController.loadMap(p_router.getActionParameters());
						break;
					case "assigncountries":
						l_startupController.assignCountries();
						break;						
				}
				break;
			case ERROR:
				ErrorController l_errorController = l_controllerFactory.getErrorController();
				l_errorController.error(p_router.getActionName());
		}	
		
	}
	
	/**
	 * This method can parse a list of commands
	 * @param p_routers the list of router
	 */
	public void route(List<Router> p_routers) {
		if(p_routers != null) {
			p_routers.forEach((router) ->{
				try {
					GenericView.printDebug("Excuting router: " + router.toString() );
					route(router);
				}
				catch(Exception ex){	
					GenericView.printError("Exception occur: " + ex.toString());
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
		
		String l_firstWord = l_commandArray[0];
		// TODO move these commands into the properties file
		String l_complexCommand = "editcontinent,editcountry,editneighbor,gameplayer";
		String l_simpleCommand = "loadmap,editmap,savemap,assigncountries,validatemap,showmap,help,play,reboot,startup,mapeditor";
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
		}
		GenericView.printDebug("ControllerName is :" + l_controllerName.toString() );
		// if the action is not equal to 'add' or 'remove', we return an error router
		for(Action l_action: l_actions) {
			//TODO add it in the property file
			String l_actionArray = "-add,-remove";
			if(l_actionArray.indexOf(l_action.getAction()) > -1) { 
				l_routers.add(new Router(l_controllerName, l_action.getAction(), l_action.getParameters()));
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
		switch (p_commandArray[0]) {
			case "reboot":
			case "startup":
			case "mapeditor":
				l_router = new Router(ControllerName.COMMON, "changephase",p_commandArray[0]);
				break;
			case  "help":
				l_router = new Router(ControllerName.COMMON, "help");
				break;		
			case  "showmap":
				if(this.d_gameContext.getGamePhase().equals(GamePhase.MAPEDITOR))
					l_router = new Router(ControllerName.MAP, "showmap");
				else
					l_router = new Router(ControllerName.GAMEPLAY, "showmap");
				break;
			case  "validatemap":
				l_router =  new Router(ControllerName.MAP, "validatemap");
				break;
			case  "play":
				l_router =  new Router(ControllerName.GAMEPLAY, "play");
				break;				
			case  "assigncountries":
				l_router =  new Router(ControllerName.STARTUP, "assigncountries");
				break;
			case  "savemap":
				if (p_commandArray.length == 1) {
					return createErrorRouter(ErrorType.MISSING_PARAMETER.toString());
				}
				if(p_commandArray.length == 2 ) {
					l_router =  new Router(ControllerName.MAP, "savemap", p_commandArray[1]);
				}
				else {
					return createErrorRouter(ErrorType.TOO_MUCH_PARAMETERS.toString());
				}
				break;
			case  "editmap":
				if (p_commandArray.length == 1) {
					return createErrorRouter(ErrorType.MISSING_PARAMETER.toString());
				}
				if(p_commandArray.length == 2 ) {
					l_router =  new Router(ControllerName.MAP, "editmap", p_commandArray[1]);
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
					l_router =  new Router(ControllerName.STARTUP, "loadmap", p_commandArray[1]);
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
		for(int i = 1; i < p_commandArray.length; i++) {
			// judge that if p_commandArray[i] is an option
			if(p_commandArray[i].charAt(0) == '-') {
				// if an option is the last element of the array or there are two
				// continuous options, we still return missing parameter error
				if (i == p_commandArray.length - 1 || p_commandArray[i + 1].charAt(0) == '-') {
					return new LinkedList<Action>();
				}
				for (int j = i + 1; j < p_commandArray.length; j++) {
					if (p_commandArray[j].charAt(0) == '-') {
						Action l_action = new Action(p_commandArray[i].replace("-", ""), CommonTool.convertArray2String(p_commandArray, " ", i + 1, j - 1));
						l_actions.add(l_action);
						i = j;
					}
					if (j == p_commandArray.length - 1) {
						Action l_action = new Action(p_commandArray[i].replace("-", ""), CommonTool.convertArray2String(p_commandArray, " ", i + 1, j));
						l_actions.add(l_action);
						i = j;
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
	private Router createErrorRouter(String p_errorType){
		return new Router(ControllerName.ERROR, p_errorType);
	}
}