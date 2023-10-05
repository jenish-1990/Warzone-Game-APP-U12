package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.*;

/**
 * Continent controller is for manipulate the continents in the map
 */
public class ContinentController {
	
	private ContinentService d_continentService;
	private GameContext d_gameContext;

	/**
	 * constructor with setting game context and create continent service
	 * @param p_gameContext game context
	 */
	public ContinentController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_continentService = new ContinentService(p_gameContext);
	}
	
	/**
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 * @return the result of adding new continent
	 */
	public boolean addContinent(String p_parameters) {
		if(p_parameters == null) {
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		int l_continentID = -1;
		String l_continentName = "";
		// separate the parameter string
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		// check if parameter length is valid
		if(l_parameters.length == 2 ) {			
			l_continentID = CommonTool.parseInt(l_parameters[0]);
			l_continentName = l_parameters[1];
		}
		// if continent id or name is not correct, return error info
		if(l_continentID == -1 || l_continentName ==""){
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		return addContinent(l_continentID, l_continentName);		
	}

	/**
	 * add continent into map
	 * @param p_continentID continent id
	 * @param p_continentName continent name
	 * @return true if successfully add the continent, otherwise return false
	 */
	public boolean addContinent(int p_continentID, String p_continentName) {
		//1. create a new contient instance
		Continent l_Continent = new Continent(p_continentID, p_continentName);
		
		//2. add continent to ContinentService
		d_continentService.add(l_Continent);
		
		//3. render to view
		GenericView.printSuccess( String.format("Continent [%s] was added successfully.", l_Continent.getContinentName()) );
		return true;
	}
	
	/**
	 * Performs the action for the user command: editcontinent -remove continentID
	 * @param p_parameters id of continent
	 * @return if remove success
	 */
	public boolean removeContinent(String p_parameters) {
		//parse [p_parameters] to  [ l_continentID ]
		if(p_parameters == null)
		{			
			GenericView.printError("Missing valid parameters.");
			return false;
		}
		int l_continentID = CommonTool.parseInt(p_parameters);
		if(l_continentID == -1 ){	
			GenericView.printError("Missing valid parameters.");	
			return false;	
		}
		
		return removeContinent(l_continentID);
	}

	/**
	 * remove the continent from the map
	 * @param p_continentID the continent id
	 * @return true if successfully removed, otherwise return false
	 */
	public boolean removeContinent(int p_continentID) {
		if( d_continentService.remove(p_continentID)) {
			GenericView.printSuccess( String.format("Continent ID [%s] was removed successfully.", p_continentID) );
			return true;
		}			
		else {
			GenericView.printWarning( String.format("Failed to remove Continent ID [%s].", p_continentID ) );
			return false;
		}
			
	}
}
