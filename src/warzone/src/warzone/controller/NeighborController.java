package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.CommonTool;
import warzone.service.ContinentService;
import warzone.service.NeighborService;

public class NeighborController {
	
	private NeighborService d_neighborService;
	private GameContext d_gameContext;

	public NeighborController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_neighborService = new NeighborService(p_gameContext);
	}

	public boolean addNeighbor (String p_parameters) {
		//0. parse [p_parameters]
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
		if(l_countryID == -1 || l_neighborCountryID == -1 ){
			GenericView.printError("Missing valid parameters.");
			return false;
		}
		else
			return addNeighbor(l_countryID, l_neighborCountryID);
	}
	/**
	 * Performs the action for the user command: editneighbor -add countryID neighborCountryID
	 */
	public boolean addNeighbor (int p_countryID, int p_neighborCountryID) {
		
		// TODO Auto-generated method stub
		
		if( d_neighborService.add(p_countryID, p_neighborCountryID)) {
			GenericView.printSuccess( String.format("Neighbor [%s] was added to Country [%s] successfully.", p_neighborCountryID, p_countryID) );
			return true;
		}			
		else {
			GenericView.printWarning( String.format("Failed to add Neighbor [%s] to Country [%s].", p_neighborCountryID, p_countryID) );
			return false;
		}	
	}
	
	public boolean removeNeighbor (String p_parameters) {
		//0. parse [p_parameters]
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
		if(l_countryID == -1 || l_neighborCountryID == -1 ){
			GenericView.printError("Missing valid parameters.");
			return false;
		}
		else
			return removeNeighbor(l_countryID, l_neighborCountryID);
	}
	
	/**
	 * Performs the action for the user command: editneighbor -remove countryID neighborCountryID
	 */
	public boolean removeNeighbor (int p_countryID, int p_neighborCountryID) {
		
		// TODO Auto-generated method stub
		
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
