package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.CommonTool;
import warzone.service.NeighborService;

/**
 * Neighbor Controller is for manipulate the neighbor relationships in map
 */
public class NeighborController {
	
	private NeighborService d_neighborService;
	private GameContext d_gameContext;

	/**
	 * constructor with setting gamecontext and create a neighborService
	 * @param p_gameContext the game context
	 */
	public NeighborController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_neighborService = new NeighborService(p_gameContext);
	}

	/**
	 * Performs the action for the user command: editneighbor -add countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 * @return true if successfully add neighbor, otherwise return false
	 */
	public boolean addNeighbor (String p_parameters) {
		//parse [p_parameters]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		int l_countryID = -1, l_neighborCountryID = -1;
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		// check if parameter length is valid
		if(l_parameters.length == 2 ) {			
			l_countryID = CommonTool.parseInt(l_parameters[0]);
			l_neighborCountryID = CommonTool.parseInt(l_parameters[1]);
		}
		// if country ids not correct, return error info
		if(l_countryID == -1 || l_neighborCountryID == -1 ){
			GenericView.printError("Missing valid parameters.");
			return false;
		}
		return addNeighbor(l_countryID, l_neighborCountryID);
	}

	/**
	 * add neighbor
	 * @param p_countryID neighbor from country
	 * @param p_neighborCountryID neighbor to country
	 * @return true if successfully added, otherwise return false
	 */
	public boolean addNeighbor (int p_countryID, int p_neighborCountryID) {

		if( d_neighborService.add(p_countryID, p_neighborCountryID)) {
			GenericView.printSuccess( String.format("Neighbor [%s] was added to Country [%s] successfully.", p_neighborCountryID, p_countryID) );
			return true;
		}			
		else {
			GenericView.printWarning( String.format("Failed to add Neighbor [%s] to Country [%s].", p_neighborCountryID, p_countryID) );
			return false;
		}	
	}

	/**
	 * Performs the action for the user command: editneighbor -remove countryID neighborCountryID
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 * @return true if successfully remove the relationship, otherwise return false
	 */
	public boolean removeNeighbor (String p_parameters) {
		//parse [p_parameters]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		int l_countryID = -1, l_neighborCountryID = -1;
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		if(l_parameters.length == 2 ) {			
			l_countryID = CommonTool.parseInt(l_parameters[0]);
			l_neighborCountryID = CommonTool.parseInt(l_parameters[1]);
		}
		// if country ids not correct, return error info
		if(l_countryID == -1 || l_neighborCountryID == -1 ){
			GenericView.printError("Missing valid parameters.");
			return false;
		}
		return removeNeighbor(l_countryID, l_neighborCountryID);
	}

	/**
	 * remove neighbor relationship
	 * @param p_countryID the from country id
	 * @param p_neighborCountryID the to country id
	 * @return true ifsuccessfully remove the relationship, otherwise return false
	 */
	public boolean removeNeighbor (int p_countryID, int p_neighborCountryID) {

		if( d_neighborService.remove(p_countryID, p_neighborCountryID)) {
			GenericView.printSuccess( String.format("Neighbor [%s] was removed from Country [%s] successfully.", p_neighborCountryID, p_countryID) );
			return true;
		}			
		else {
			GenericView.printWarning( String.format("Failed to remove Neighbor [%s] to Country [%s].", p_neighborCountryID, p_countryID) );
			return false;
		}	
	}
}
