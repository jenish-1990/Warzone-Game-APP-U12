package warzone.model;

/**
 * phase of load map
 */
public enum LoadMapPhase {
	/**
	 * Load Map Phase of loading FILES
	 */
	FILES,
	/**
	 * Load Map Phase of loading CONTINENTS
	 */
	CONTINENTS,
	/**
	 * Load Map Phase of loading COUNTRIES
	 */
	COUNTRIES,
	/**
	 * Load Map Phase of loading BORDERS
	 */
	BORDERS,
	/**
	 * Load Map Phase of COMPLETE
	 */
	COMPLETE
}