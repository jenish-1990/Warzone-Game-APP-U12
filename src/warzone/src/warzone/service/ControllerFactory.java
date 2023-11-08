package warzone.service;

import warzone.controller.*;
import warzone.model.GameContext;

/**
 * This class is responsible to create corresponding controllers.
 * @author Zexin
 *
 */
public class ControllerFactory {	
	
	private static ControllerFactory CONTROLLER_FACTORY;
	private GameContext d_gameContext;
	
	private CommonController d_commonController;
	private ContinentController d_continentController;
	private MapController d_mapController;
	private CountryController d_countryController;
	private NeighborController d_neighborController;
	private StartupController d_startupController;
	private ErrorController d_errorController;
	private GameplayController d_gameplayController;
	
	/**
	 * The Constructor of the class.
	 */
	private ControllerFactory()	{	
		d_gameContext = GameContext.getGameContext();
	}
	
	/**
	 * This method will return a ControllerFactory instance.
	 * @return a ControllerFactory instance.
	 */
	public static ControllerFactory getControllerFactory() {
		if( CONTROLLER_FACTORY == null)
			CONTROLLER_FACTORY = new ControllerFactory();

		return CONTROLLER_FACTORY;
	}
	
	/**
	 * This method will return a CommonController instance.
	 * @return a CommonController instance
	 */
	public CommonController getCommonController() {
		if(d_commonController == null)
			d_commonController = new CommonController(d_gameContext);
		return d_commonController;
	};
	
	/**
	 * This method will return a ContinentController instance.
	 * @return a ContinentController instance
	 */
	public ContinentController getContinentController() {
		if(d_continentController == null)
			d_continentController = new ContinentController(d_gameContext);
		return d_continentController;
	};
	
	/**
	 * This method will return a MapController instance.
	 * @return a MapController instance
	 */
	public MapController getMapController() {
		if(d_mapController == null)
			d_mapController = new MapController(d_gameContext);
		return d_mapController;
	};
	
	/**
	 * This method will return a CountryController instance.
	 * @return a CountryController instance
	 */
	public CountryController getCountryController() {
		if(d_countryController == null)
			d_countryController = new CountryController(d_gameContext);
		return d_countryController;
	};
	
	/**
	 * This method will return a NeighborController instance.
	 * @return a NeighborController instance
	 */
	public NeighborController getNeighborController() {
		if(d_neighborController == null)
			d_neighborController = new NeighborController(d_gameContext);
		return d_neighborController;
	};
	
	/**
	 * This method will return a StartupController instance.
	 * @return a StartupController instance
	 */
	public StartupController getStartupController() {
		if(d_startupController == null)
			d_startupController = new StartupController(d_gameContext);
		return d_startupController;
	};
	
	/**
	 * This method will return a GameplayerController instance.
	 * @return a GameplayerController instance
	 */
	public GameplayController getGameplayController() {
		if(d_gameplayController == null)
			d_gameplayController = new GameplayController(d_gameContext);
		return d_gameplayController;
	};
	
	/**
	 * This method will return a ErrorController instance.
	 * @return a ErrorController instance
	 */
	public ErrorController getErrorController() {
		if(d_errorController == null)
			d_errorController = new ErrorController();
		return d_errorController;
	};
	
}