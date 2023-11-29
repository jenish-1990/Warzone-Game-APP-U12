package warzone.adapter;

import java.io.IOException;

import warzone.model.GameContext;
import warzone.service.ConquestMapReader;
import warzone.service.ConquestMapWriter;
import warzone.service.MapService;

/**
 * This class is the adapter for MapServiceAdapter
 * @author zexin
 *
 */
public class MapServiceAdapter extends MapService{
	/**
	 * conquest map writer
	 */
	ConquestMapWriter d_conquestMapWriter;
	/**
	 * conquest map reader
	 */
	ConquestMapReader d_conquestMapReader;

	/**
	 * the constructor of the class
	 * @param p_gameContext the current game context
	 */
	public MapServiceAdapter(GameContext p_gameContext, ConquestMapWriter p_conquestMapWriter, ConquestMapReader p_conquestMapReader) {
		super(p_gameContext);
		this.d_conquestMapWriter = p_conquestMapWriter;
		this.d_conquestMapReader = p_conquestMapReader;
	}
	
	/**
	 * This method will save maps with 'conquest' format
	 * @param p_fileName the name of the map
	 * @return true if successfully
	 */
	public boolean saveMap(String p_fileName) throws IOException {
		return d_conquestMapWriter.saveConquestMap(p_fileName);
	}
	
	/**
	 * This method will perform editmap to maps with 'conquest' format
	 * @param p_fileName the name of the map
	 * @return true if successfully
	 */
	public boolean editMap(String p_fileName) {
		return d_conquestMapReader.loadConquestMap(p_fileName);
	}
}
