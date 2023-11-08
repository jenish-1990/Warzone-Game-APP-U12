package warzone.service;

import warzone.model.*;

import warzone.model.*;

public class Startup {
	
	public static void main(String[] args) {

		RouterService d_RouterService;
		d_RouterService = new RouterService();
		
		//1 welcome
		Router welcome = new Router(ControllerName.COMMON, "welcome");
		d_RouterService.route(welcome);
	
		//2 init the game
		//3 standby
		//System.in.read();
//		while(System.IO.Readline()) {
//			//parse the command to a router
//			//route the router
////			Router welcome = new Router(ControllerName.COMMON, "welcome");
////			d_RouterService.route(welcome);
//		}
	}
}
