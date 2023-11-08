package warzone.service;

import warzone.controller.*;
import warzone.model.GameContext;

public class ControllerFactory {	
	
	private static ControllerFactory CONTROLLER_FACTORY;
	private static GameContext GAME_CONTEXT;
	
	private CommonController d_commonController;
	private ContinentController d_continentController;
	private MapController d_mapController;
	private CountryController d_countryController;
	private NeighborController d_neighborController;
	
	private ControllerFactory()	{	
		GAME_CONTEXT = GameContext.getGameContext();
	}
	
	public static ControllerFactory getControllerFactory() {
		if( CONTROLLER_FACTORY == null)
			CONTROLLER_FACTORY = new ControllerFactory();

		return CONTROLLER_FACTORY;
	}
	
	public CommonController getCommonController() {
		if(d_commonController == null)
			d_commonController = new CommonController(GAME_CONTEXT);
		return d_commonController;
	};
	
	public ContinentController getContinentController() {
		if(d_continentController == null)
			d_continentController = new ContinentController(GAME_CONTEXT);
		return d_continentController;
	};
	
	public MapController getMapController() {
		if(d_mapController == null)
			d_mapController = new MapController(GAME_CONTEXT);
		return d_mapController;
	};
	
	public CountryController getCountryController() {
		if(d_countryController == null)
			d_countryController = new CountryController(GAME_CONTEXT);
		return d_countryController;
	};
	
	public NeighborController getNeighborController() {
		if(d_neighborController == null)
			d_neighborController = new NeighborController(GAME_CONTEXT);
		return d_neighborController;
	};
	
	
}