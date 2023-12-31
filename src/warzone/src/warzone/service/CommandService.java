package warzone.service;

import java.util.List;
import java.util.Scanner;

import warzone.model.*;
import warzone.view.*;

/**
 * This class is responsible to deal with players' input
 *
 */
public class CommandService {
	/**
	 * command service
	 */
	private static CommandService COMMAND_SERVICE;

	/**
	 * game context
	 */
	private GameContext d_gameContext;
	/**
	 * game engine
	 */
	private GameEngine d_gameEngine;	

	/**
	 * The constructor of the class.
	 * @param p_gameEngine initiating the GameEngine of the current object
	 */
	private CommandService(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}	
	
	/**
	 * This method will return command service object from game context.
	 * @param p_gameEngine the game engine
	 * @return CommandService object
	 */
	public static CommandService getCommandService(GameEngine p_gameEngine) {
		if( COMMAND_SERVICE == null)
			COMMAND_SERVICE = new CommandService(p_gameEngine);
		return COMMAND_SERVICE;
	}

	/**
	 * This class will get players' input and print some hints to the player. It will redirect to
	 * RouterService to parse the command.
	 * @param p_routerService this object will parse the input command
	 */
	public void commandScanner(RouterService p_routerService) {
		if(p_routerService == null)
			return;
		
		Scanner l_keyboard = new Scanner(System.in);		
		List<Router> l_routers;
		do {
			GenericView.println("");
			HelpView.printStatus(d_gameEngine);
			//render the help
			GenericView.println("Please input the command, type [help] for help, type [quit] to quit the game.");
			
			//get command from console
			String l_command = l_keyboard.nextLine();
			if(l_command.toLowerCase().trim().equals("quit"))
				break;
			else {
				l_routers = p_routerService.parseCommand(l_command);						
				//excute the command
				p_routerService.route(l_routers);
			}			
		} while (true);		
		GenericView.println("Good Game! Bye bye.");
	}
}
