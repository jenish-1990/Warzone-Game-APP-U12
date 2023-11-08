package warzone.service;

import warzone.controller.*;
import warzone.model.GameContext;

public class ControllerFactory {	
	
	private static ControllerFactory CONTROLLER_FACTORY;
	private static GameContext d_gameContext;
	private CommonController d_commonController;
	private ContinentController d_continentController;
	
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
}