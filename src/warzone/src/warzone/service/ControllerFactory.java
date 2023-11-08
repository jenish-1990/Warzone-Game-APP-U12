package warzone.service;

import java.util.ArrayList;

import warzone.controller.*;
import warzone.model.GameContext;
import warzone.model.GameMap;
import warzone.model.Player;

public class ControllerFactory {	
	
	private static ControllerFactory CONTROLLER_FACTORY;
	
	private ControllerFactory()	{	
	}
	
	private ControllerFactory(GameContext p_gameContext)	{	
		d_gameContext = p_gameContext;
	}
	
	
	public static ControllerFactory getControllerFactory(GameContext p_gameContext) {
		if( CONTROLLER_FACTORY == null)
			CONTROLLER_FACTORY = new ControllerFactory(p_gameContext);

		return CONTROLLER_FACTORY;
	}
	
	
	private GameContext d_gameContext;

	
	private CommonController d_commonController;
	
	public CommonController getCommonController() {
		if(d_commonController == null)
			d_commonController = new CommonController(d_gameContext);
		return d_commonController;
	};
	
	private ContinentController d_continentController;
	
	public ContinentController getContinentController() {
		if(d_continentController == null)
			d_continentController = new ContinentController(d_gameContext);
		return d_continentController;
	};
	
	
}
