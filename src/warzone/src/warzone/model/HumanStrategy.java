package warzone.model;
import java.io.Serializable;
import java.util.Scanner;

import warzone.view.GenericView;


/**
 *	Strategy of the Strategy pattern
 */
public class HumanStrategy extends PlayerStrategy implements Serializable {
	
	/**
	 * scanner of the command
	 */
	private transient Scanner d_keyboard = new Scanner(System.in);
	

	/**
	 * constructor of HumanStrategy
	 * @param p_player PlayerStrategy
	 */
	HumanStrategy(Player p_player){
		super(p_player);

	}
	
	/**
	 *  implementation of createOrder
	 * @return the order
	 */
	public Order createOrder() {
		
		//1. issue order from interaction
		if(d_keyboard == null)
			d_keyboard = new Scanner(System.in);
		String l_command = d_keyboard.nextLine().trim();
		Order l_order;



		if(l_command.equalsIgnoreCase("help")) {
			d_gameEngine.getPhase().help();
			return null;
		}
		else if( l_command.equalsIgnoreCase("showmap") ) {
			d_gameEngine.getPhase().showMap();
			return null;
		}
		//check if the issue order has finished
		else if(l_command.equalsIgnoreCase("done")){
			this.d_player.setHasFinisedIssueOrder(true);

			GenericView.println(String.format("---------- Finish issuing order for player [%s] in this turn.", this.d_player.getName()));
			return null;
		}

		l_order = this.d_player.conventOrder(l_command);
		if (l_order == null) {
			GenericView.printWarning("Incorrect command, please retry.");
			d_gameContext.getLogEntryBuffer().logIssueOrder("Error", "failed to issued an order", l_command);
		}
				
		return l_order;
	}
}
