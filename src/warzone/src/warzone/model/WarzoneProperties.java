package warzone.model;

import java.io.IOException;
import java.util.Properties;

import warzone.view.GenericView;

public class WarzoneProperties {

	//Class variables
	private static WarzoneProperties WARZONE_PROPERTIES;
	private Properties d_properties;
	
	//Properties
	private String d_gameMapDirectory;
	private boolean d_isDemoMode;
	private boolean d_isDebug;
	
	//Create singelton
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
	public String getGameMapDirectory() { return d_gameMapDirectory; }
	public boolean getIsDemoMode() { return d_isDemoMode; }
	public boolean getIsDebug() { return d_isDebug; }
}