package warzone.controller;

import warzone.view.GenericView;

/**
 * Error Controller is to manipulate the error informations
 */
public class ErrorController {
	/**
	 * print out the error
	 * @param p_error the error information
	 */
	public void error(String p_error) {
		GenericView.printError("Incorrect command: " +  p_error);
	}
}
