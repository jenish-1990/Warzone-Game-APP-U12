package warzone.service;

import java.util.ArrayList;

import warzone.controller.*;
import warzone.model.*;

public class RouterService {
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	public void route(Router p_router){
		 
		ControllerFactory l_controllerFactory = ControllerFactory.getControllerFactory(GameContext.getGameContext());
		switch(p_router.getControllerName()) {
			case COMMON:
				switch(p_router.getActionName()) {
					case "welcome":
						CommonController l_controller = l_controllerFactory.getCommonController();
						l_controller.welcome(p_router.getActionParameters());
						break;
				}
				break;
			case CONTINENT:
				ContinentController l_continentController = l_controllerFactory.getContinentController();

				switch(p_router.getActionName()) {
					case "add":
						l_continentController.addContinent(p_router.getActionParameters());
						break;
					case "remove":
						l_continentController.removeContinent(p_router.getActionParameters());
						break;
				}
				break;
			
		}	
		
	}
	
	/**
	 * This class will parse console commands entered by the user and call the corresponding Java method(s)
	 * passing any command line arguments as parameters
	 * 
	 */
//	public Router parseCommand(String p_command) {
//		//todo
//		return null;
//	}
	
	/**

	 * 
	 * e.g.
	 * editcountry -add countryID continentID -remove countryID
	 *   new Router(ControllerName.COUNTRY, "add", "countryID continentID");
	 *   new Router(ControllerName.COUNTRY, "remove", "countryID");
	 */
	public ArrayList<Router> parseCommand(String p_command) {
		//todo
		return null;
	}
}

