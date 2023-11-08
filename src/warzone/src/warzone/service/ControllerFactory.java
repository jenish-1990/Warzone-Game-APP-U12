package warzone.service;

import warzone.controller.*;

public class ControllerFactory {
	private static CommonController COMMON_CONTROLLER;
	
	public static CommonController getCommonController() {
		if(COMMON_CONTROLLER == null)
			COMMON_CONTROLLER = new CommonController();
		return COMMON_CONTROLLER;
	};
}
