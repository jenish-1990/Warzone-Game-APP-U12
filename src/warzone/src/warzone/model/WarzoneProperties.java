package warzone.model;

import java.io.IOException;
import java.util.Properties;

import warzone.view.GenericView;

/**
 * This class is responsible to get all properties written in the properties file.
 *
 */
public class WarzoneProperties {

	//Class variables
	private static WarzoneProperties WARZONE_PROPERTIES;
	private Properties d_properties;
	
	//Properties
	private String d_gameMapDirectory;
	private boolean d_isDemoMode;
	private boolean d_isDebug;
	
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
		
		d_gameMapDirectory = d_properties.getProperty("gameMapDirectory");
		d_isDemoMode = Boolean.parseBoolean(d_properties.getProperty("isDemoMode"));
		d_isDebug = Boolean.parseBoolean(d_properties.getProperty("isDebug"));
	}
	
	//Properties getters
	/**
	 * This method will provide all properties in the property file
	 * @return all properties in the property file
	 */
	public String getGameMapDirectory() { return d_gameMapDirectory; }
	/**
	 * This method will show whether the current mode is demo mode
	 * @return true if the current mode is demo
	 */
	public boolean getIsDemoMode() { return d_isDemoMode; }
	/**
	 * This method will show whether the current mode is debug mode
	 * @return true if the current mode is debug mode
	 */
	public boolean getIsDebug() { return d_isDebug; }
}