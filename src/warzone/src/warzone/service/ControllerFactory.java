package warzone.service;

import warzone.controller.*;
import warzone.model.GameContext;

public class ControllerFactory {	
	
	private static ControllerFactory CONTROLLER_FACTORY;
	private static GameContext d_gameContext;
	private CommonController d_commonController;
	private ContinentController d_continentController;
	private MapController d_mapController;
	
	private ControllerFactory()	{	
		d_gameContext = GameContext.getGameContext();
	}
	
	public static ControllerFactory getControllerFactory() {
		if( CONTROLLER_FACTORY == null)
			CONTROLLER_FACTORY = new ControllerFactory();

		return CONTROLLER_FACTORY;
	}
	
	public CommonController getCommonController() {
		if(d_commonController == null)
			d_commonController = new CommonController(d_gameContext);
		return d_commonController;
	};
	
	public ContinentController getContinentController() {
		if(d_continentController == null)
			d_continentController = new ContinentController(d_gameContext);
		return d_continentController;
	};
	
	public MapController getMapController() {
		if(d_mapController == null)
			d_mapController = new MapController(d_gameContext);
		return d_mapController;
	};
	
	
}