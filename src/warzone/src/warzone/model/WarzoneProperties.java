package warzone.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import warzone.view.GenericView;

/**
 * This class is responsible to get all properties written in the properties file.
 *
 */
public class WarzoneProperties implements Serializable {

	/**
	 * static member of Warzone Properties
	 */
	private static WarzoneProperties WARZONE_PROPERTIES;
	/**
	 * a private Properties
	 */
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
		d_isLog = Boolean.parseBoolean(d_properties.getProperty("isLog"));
		
		//Gameplay Settings
		d_gameMapDirectory = d_properties.getProperty("gameMapDirectory");
		d_logDirectory = d_properties.getProperty("logDirectory");
		d_minimumReinforcementsEachRound = Integer.parseInt(d_properties.getProperty("minimumReinforcementsEachRound"));
		d_minimumCountriesPerReinforcementBonus = Integer.parseInt(d_properties.getProperty("minimumCountriesPerReinforcementBonus"));
		d_simpleCommand = d_properties.getProperty("simpleCommand");
		d_complexCommand = d_properties.getProperty("complexCommand");
		d_maxTurnPerRound = Integer.parseInt(d_properties.getProperty("maxTurnsPerRound"));
	}
	
	/**
	 *  is running in Demo Mode
	 */
	private boolean d_isDemoMode; 
	
	/**
	 * This method will show whether the current mode is demo mode
	 * @return true if the current mode is demo
	 */
	public boolean getIsDemoMode() { return d_isDemoMode; }
	
	/**
	 * is running in debug mode
	 */
	private boolean d_isDebug;
	
	/**
	 * This method will show whether the current mode is debug mode
	 * @return true if the current mode is debug mode
	 */
	public boolean getIsDebug() { return d_isDebug; }
	
	/**
	 * true if log is enabled
	 */
	private boolean d_isLog;
	/**
	 * This method will show whether needs logs
	 * @return true if current game needs logs
	 */
	public boolean getIsLog() { return d_isLog; }
		
	/**
	 *  game Map Directory
	 */
	private String d_gameMapDirectory;
	/**
	 * This method will provide all properties in the property file
	 * @return all properties in the property file
	 */
	public String getGameMapDirectory() { return d_gameMapDirectory; }
	
	/**
	 * log Directory
	 */
	private String d_logDirectory;
	/**
	 * This method will return the log directory.
	 * @return the log directory
	 */
	public String getLogDirectory() { return d_logDirectory; }
	/**
	 *  simple Command
	 */
	private String d_simpleCommand;
	/**
	 * This method will return simple command in the configuration file
	 * @return simple command
	 */
	public String getSimpleCommand() {
		return d_simpleCommand;
	}
	
	/**
	 *  complex Command
	 */
	private String d_complexCommand;
	/**
	 * This method will return complex command in the configuration file
	 * @return complex command
	 */
	public String getComplexCommand() {
		return d_complexCommand;
	}
		
	/**
	 *  minimum Reinforcements in Each Round
	 */
	private int d_minimumReinforcementsEachRound;
	
	/**
	 * get minimum Reinforcements in Each Round
	 * @return minimum Reinforcements in Each Round
	 */
	public int getMinimumReinforcementsEachRound() { 
		return d_minimumReinforcementsEachRound; 
	}
	
	/**
	 *  minimum Countries PerReinforcement Bonus
	 */
	private int d_minimumCountriesPerReinforcementBonus;
	/**
	 * get minimum Countries PerReinforcement Bonus
	 * @return minimum Countries PerReinforcement Bonus
	 */
	public int getMinimumCountriesPerReinforcementBonus() { 
		return d_minimumCountriesPerReinforcementBonus; 
	}

	/**
	 * max turn number per round
	 */
	private int d_maxTurnPerRound;

	/**
	 * max turn number per round
	 * @return max turn number per round
	 */
	public int getMaxTurnPerRound() { return d_maxTurnPerRound; }
}