package warzone.service;

import warzone.controller.CommonController;
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
		
		switch(p_router.getControllerName()) {
			case COMMON:
				switch(p_router.getActionName()) {
					case "welcome":
						CommonController l_commonController = ControllerFactory.getCommonController();
						l_commonController.welcome(p_router.getActionParameters());
						
				}
			
		}
		
		
	}
	
	/**
	 * This class will parse console commands entered by the user and call the corresponding Java method(s)
	 * passing any command line arguments as parameters
	 * 
	 */
	public Router parseCommand(String p_command) {
		//todo
		return null;
	}
	
	/**

	 * 
	 * e.g.
	 * editcountry -add countryID continentID -remove countryID
	 *   new Router(ControllerName.COUNTRY, "add", "countryID continentID");
	 *   new Router(ControllerName.COUNTRY, "remove", "countryID");
	 */
	public List<Router> parseCommand(String p_command) {
		//todo
		return null;
	}
}

