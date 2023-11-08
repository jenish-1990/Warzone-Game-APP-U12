package warzone.model;

/**
 * There are four phases in the game. They are map editing, startup, playing
 * and finished respectively.
 *
 */
public enum GamePhase {
	/**
	 * Game Phase of  MAPEDITOR
	 */
	MAPEDITOR,
	/**
	 * Game Phase of  STARTUP
	 */
	STARTUP,
	/**
	 * Game Phase of  GamePlay
	 */
	GamePlay,
	/**
	 * Game Phase of  IssueOrder
	 */
	IssueOrder,
	/**
	 * Game Phase of  OrderExecution
	 */
	OrderExecution,
	/**
	 * Game Phase of  Reinforcement
	 */
	Reinforcement
}
