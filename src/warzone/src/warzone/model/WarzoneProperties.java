package warzone.model;

import warzone.view.GenericView;

import java.io.IOException;
import java.util.Properties;

/**
 * This class is responsible to get all properties written in the properties file.
 *
 */
public class WarzoneProperties {

	//Class variables
	private static WarzoneProperties WARZONE_PROPERTIES;
	private Properties d_properties;
	
	//Create singelton
	/**
	 * This method use Singleton pattern to ensure the singleton of the class.
	 */
	private WarzoneProperties() {
		
		try {

			d_properties = new Properties();
			d_properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			
			loadProperties();
		
		} catch (IOException e) {

			GenericView.printError("Error loading properties file.");
		}
	}
	
	//Return singleton
	/**
	 * This method can provide a unique instance of this class.
	 * @return the unique instance of this class
	 */
	public static WarzoneProperties getWarzoneProperties() {
		
		if(WARZONE_PROPERTIES == null) {
			WARZONE_PROPERTIES = new WarzoneProperties();
		}
		return WARZONE_PROPERTIES;
	}
	
	/**
	 * Load each property from config.properties into the java variables 
	 */
	private void loadProperties() {
		
		//Run Configurations
		d_isDemoMode = Boolean.parseBoolean(d_properties.getProperty("isDemoMode"));
		d_isDebug = Boolean.parseBoolean(d_properties.getProperty("isDebug"));
		
		//Gameplay Settings
		d_gameMapDirectory = d_properties.getProperty("gameMapDirectory");
		d_minimumReinforcementsEachRound = Integer.parseInt(d_properties.getProperty("minimumReinforcementsEachRound"));
		d_minimumCountriesPerReinforcementBonus = Integer.parseInt(d_properties.getProperty("minimumCountriesPerReinforcementBonus"));
	}
	
	//Run Configurations
	private boolean d_isDemoMode; 
	/**
	 * This method will show whether the current mode is demo mode
	 * @return true if the current mode is demo
	 */
	public boolean getIsDemoMode() { return d_isDemoMode; }
	
	private boolean d_isDebug;
	/**
	 * This method will show whether the current mode is debug mode
	 * @return true if the current mode is debug mode
	 */
	public boolean getIsDebug() { return d_isDebug; }
		
	//Gameplay Settings
	private String d_gameMapDirectory;
	/**
	 * This method will provide all properties in the property file
	 * @return all properties in the property file
	 */
	public String getGameMapDirectory() { return d_gameMapDirectory; }
		
	private int d_minimumReinforcementsEachRound;
	public int getMinimumReinforcementsEachRound() { return d_minimumReinforcementsEachRound; }
	
	private int d_minimumCountriesPerReinforcementBonus;
	public int getMinimumCountriesPerReinforcementBonus() { return d_minimumCountriesPerReinforcementBonus; }
}