package warzone.service;

import java.util.List;
import java.util.Scanner;

import warzone.model.*;
import warzone.view.*;

public class CommandService {
	
	private static CommandService COMMAND_SERVICE;
	
	private GameContext d_gameContext;

	private CommandService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}	
	
	public static CommandService getCommandService(GameContext p_gameContext) {
		if( COMMAND_SERVICE == null)
			COMMAND_SERVICE = new CommandService(p_gameContext);
		return COMMAND_SERVICE;
	}

	public void commandScanner(RouterService p_routerService) {
		if(p_routerService == null)
			return;
		
		Scanner l_keyboard = new Scanner(System.in);		
		List<Router> l_routers;
		do {
			HelpView.printStatus(d_gameContext);
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
