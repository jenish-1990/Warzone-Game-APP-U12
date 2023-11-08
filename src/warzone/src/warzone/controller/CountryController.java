package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.CommonTool;
import warzone.service.ContinentService;
import warzone.service.CountryService;

public class CountryController {

	private CountryService d_countryService;
	private GameContext d_gameContext;
	
	public CountryController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_countryService = new CountryService(p_gameContext);
	}
	
	public boolean addCountry (String p_parameters) {
		//0. parse [p_parameters] to  [ l_continentID, String l_continentName]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		int l_countryID = -1, l_continentID = -1;
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		if(l_parameters.length == 2 ) {			
			l_countryID = CommonTool.parseInt(l_parameters[0]);
			l_continentID = CommonTool.parseInt(l_parameters[1]);
		}
		if(l_countryID == -1 || l_continentID == -1 ){
			GenericView.printError("Missing valid parameters.");
			return false;
		}
		else
			return addCountry(l_countryID, l_continentID);
	}
	
	/**
	 * Performs the action for the user command: editcountry -add countryID continentID
	 */
	public boolean addCountry (int p_countryID, int p_continentID) {		
		if( d_countryService.addCountryToContient(p_countryID, p_continentID) ) {
			GenericView.printSuccess( String.format("Country ID [%s] was added to Continent [%s] successfully.", p_countryID, p_continentID) );
			return true;
		}			
		else {
			GenericView.printWarning( String.format("Failed to add Country ID [%s] to Continent [%s].", p_countryID , p_continentID) );
			return false;
		}	
	}
	
	public boolean removeCountry(String p_parameters) {
		//0. parse [p_parameters] 
		if(p_parameters == null)
		{			
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		int l_countryID = CommonTool.parseInt(p_parameters);
		
		if(l_countryID == -1 ){
			GenericView.printError("Missing valid parameters.");
			return false;
		}
		
		//1. remove continent from ContinentService by id
		return removeCountry(l_countryID);
	}	
	
	
	/**
	 * Performs the action for the user command: editcountry -remove countryID
	 */
	public boolean removeCountry (int p_countryID) {
		if( d_countryService.remove(p_countryID)) {
			GenericView.printSuccess( String.format("Country ID [%s] was removed successfully.", p_countryID) );
			return true;
		}			
		else {
			GenericView.printWarning( String.format("Failed to remove Country ID [%s].", p_countryID ) );
			return false;
		}			
	}
}
