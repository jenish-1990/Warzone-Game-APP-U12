package warzone.model;

/**
 * There are eight types of error during the game.
 *
 */
public enum ErrorType {
	/**
	 * Error Type for NO SUCH COMMAND
	 */
	NO_SUCH_COMMAND,
	/**
	 * Error Type for MISSING PARAMETER
	 */
	MISSING_PARAMETER,
	/**
	 * Error Type for MISSING COMMAND
	 */
	MISSING_COMMAND,
	/**
	 * Error Type for BAD PARAMETER
	 */
	BAD_PARAMETER,
	/**
	 * Error Type for BAD OPTION
	 */
	BAD_OPTION,
	/**
	 * Error Type for WRONG PARAMETER
	 */
	WRONG_PARAMETER,
	/**
	 * Error Type for COMMAND ERROR
	 */
	COMMAND_ERROR,
	/**
	 * Error Type for TOO MUCH PARAMETERS
	 */
	TOO_MUCH_PARAMETERS;
	
	/**
	 * This method can print error type.
	 */
	public String toString() {
		return this.getClass().getName();
	}
}
