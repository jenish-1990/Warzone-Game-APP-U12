package warzone.controller;

import warzone.view.*;
import warzone.model.*;
import warzone.service.CommonTool;
import warzone.service.CountryService;

/**
 * Country controller is for manipulate the countries in the map
 */
public class CountryController {

	private CountryService d_countryService;
	private GameContext d_gameContext;

	/**
	 * instructor with game context setting and country service initiated
	 * @param p_gameContext game context
	 */
	public CountryController(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_countryService = new CountryService(p_gameContext);
	}

	/**
	 * add country to the map
	 * This methods can receive parameters from the Router, check the correctness of
	 * commands and call the internal methods.
	 * @param p_parameters parameters parsed by parser
	 * @return true if successfully add country, otherwise return false
	 */
	public boolean addCountry (String p_parameters) {
		//parse [p_parameters] to  [ l_continentID, String l_continentName]
		if(p_parameters == null){			
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		int l_countryID = -1, l_continentID = -1;
		String[] l_parameters = CommonTool.conventToArray(p_parameters);
		// check if parameter length is valid
		if(l_parameters.length == 2 ) {			
			l_countryID = CommonTool.parseInt(l_parameters[0]);
			l_continentID = CommonTool.parseInt(l_parameters[1]);
		}
		// if country id or name is not correct, return error info
		if(l_countryID == -1 || l_continentID == -1 ){
			GenericView.printError("Missing valid parameters.");
			return false;
		}
		else
			return addCountry(l_countryID, l_continentID);
	}

	/**
	 * Performs the action for the user command: editcountry -add countryID continentID
	 * @param p_countryID the id of country to add
	 * @param p_continentID the id of countinent add to
	 * @return true if successfully added, otherwise return false
	 */
	public boolean addCountry (int p_countryID, int p_continentID) {		
		if( d_countryService.addCountryToContient(p_countryID, p_continentID) ) {
			GenericView.printSuccess( String.format("Country ID [%s] was added to Continent [%s] successfully.", p_countryID, p_continentID) );
			return true;
		}			
		else {
			if(d_countryService.isExisted(p_countryID))
				GenericView.printWarning( String.format("Country [%s] was added, but failed to add Country ID [%s] to Continent [%s].", p_countryID , p_countryID , p_continentID) );	
			else
				GenericView.printWarning( String.format("Failed to add Country ID [%s] to Continent [%s].", p_countryID , p_continentID) );
			return false;
		}	
	}

	/**
	 * remove the country from map
	 * @param p_parameters parameters parsed by parser
	 * @return true if successfully remove the country, otherwise return false
	 */
	public boolean removeCountry(String p_parameters) {
		//parse [p_parameters] 
		if(p_parameters == null) {
			GenericView.printError("Missing valid parameters.");
			return false;
		}

		int l_countryID = CommonTool.parseInt(p_parameters);		
		if(l_countryID == -1 ){	
			GenericView.printError("Missing valid parameters.");	
			return false;	
		}
		
		return removeCountry(l_countryID);
	}	
	
	
	/**
	 * Performs the action for the user command: editcountry -remove countryID
	 * @param p_countryID the id of the country to remove
	 * @return true if successfully remove the country, otherwise return false
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
