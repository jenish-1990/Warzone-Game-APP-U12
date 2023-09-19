package warzone.service;

import java.io.IOException;
import java.util.ArrayList;

import warzone.controller.*;
import warzone.model.*;

public class RouterService {
	
	public void route(Router p_router) throws IOException{
		 
		ControllerFactory l_controllerFactory = ControllerFactory.getControllerFactory();
		switch(p_router.getControllerName()) {
			case COMMON:
				switch(p_router.getActionName()) {
					case "welcome":
						CommonController l_commonController = l_controllerFactory.getCommonController();
						l_commonController.welcome(p_router.getActionParameters());
						break;
				}
				break;
			case CONTINENT:
				ContinentController l_continentController= l_controllerFactory.getContinentController();

				switch(p_router.getActionName()) {
					case "add":
						l_continentController.addContinent(p_router.getActionParameters());
						break;
					case "remove":
						l_continentController.removeContinent(p_router.getActionParameters());
						break;
				}
				break;
			case MAP:
				MapController l_mapController= l_controllerFactory.getMapController();

				switch(p_router.getActionName()) {
					case "saveMap":
						l_mapController.saveMap(p_router.getActionParameters());
						break;
					case "editMap":
						l_mapController.editMap(p_router.getActionParameters());
						break;
					case "showMap":
						l_mapController.showMap();
						break;
					case "validateMap":
						l_mapController.validateMap();
						break;
				}
				break;
			case COUNTRY:
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

