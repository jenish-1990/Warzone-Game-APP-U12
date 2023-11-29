package warzone.model;

import warzone.service.ConquestMapReader;
import warzone.service.StartupService;

/**
 * This class is the adapter for StartupService by loading maps with 'conquest' format
 * @author zexin
 *
 */
public class StartupServiceAdapter extends StartupService{
	/**
	 * default serial UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * conquest map reader
	 */
	ConquestMapReader d_conquestMapReader;

	/**
	 * the constructor of the class
	 * @param p_gameContext the current game context
	 */
	public StartupServiceAdapter(GameContext p_gameContext, ConquestMapReader p_conquestMapReader) {
		super(p_gameContext);
		this.d_conquestMapReader = p_conquestMapReader;
	}
	
	/**
	 * This method will load the map from the resource file
	 * @param p_fileName the name of the file
	 * @return true if load successfully
	 */
	public boolean loadMap(String p_fileName) {
		return d_conquestMapReader.loadConquestMap(p_fileName);
	}
}
