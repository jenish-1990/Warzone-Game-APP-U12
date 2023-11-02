package warzone.service;

import  warzone.model.*;
import warzone.view.GenericView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * service class for map
 */
public class MapService {

	/**
	 * game context
	 */
	private GameContext d_gameContext;

	/**
	 * log entry buffer
	 */
	private LogEntryBuffer d_logEntryBuffer;

	/**
	 * constructor
	 * @param p_gameContext the gamecontext
	 */
	public MapService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		d_logEntryBuffer = p_gameContext.getLogEntryBuffer();
	}

	/**
	 * save map to file
	 * @param p_fullFileName file name
	 * @return if success
	 * @throws IOException if any io exception
	 */
	public boolean saveMap(String p_fullFileName) throws IOException {
		try{
			String l_fileName ;
			if(p_fullFileName.indexOf(".") > -1)
				l_fileName = p_fullFileName.substring(0,p_fullFileName.indexOf("."));
			else
				l_fileName = p_fullFileName;
			String l_path = this.d_gameContext.getMapfolder();
			
			//build the content using StringBuilder
			StringBuilder l_map = new StringBuilder();
			l_map.append("; map: " + l_fileName);
			l_map.append("\n; map made with the 6441 Super Team");
			l_map.append("\n; 6441.net  1.0.0.1 ");
			l_map.append("\n");
			
			l_map.append("\n[files]");
			l_map.append("\npic "+ l_fileName +"_pic.jpg");
			l_map.append("\nmap "+ l_fileName +"_map.gif");
			l_map.append("\ncrd "+ l_fileName + "europe.cards");
			l_map.append("\n");
			Map<Integer,Integer> l_continentIdMapping = new HashMap(); 
			l_map.append("\n[continents]");
			int l_continentId = 1;
			for (Continent l_continent : d_gameContext.getContinents().values()) {
				l_continentIdMapping.put(l_continent.getContinentID(), l_continentId);
				l_map.append("\n" + l_continent.getContinentName()  +  " " + l_continent.getBonusReinforcements() +  " " + l_continent.getColor());
				l_continentId ++ ;
			}
			l_map.append("\n");
			
			l_map.append("\n[countries]");
			for (Continent l_continent : d_gameContext.getContinents().values()) {
				for (Country l_country : l_continent.getCountries().values()) {
					l_map.append("\n" + l_country.getCountryID()  +  " " + l_country.getCountryName()  +  " " + l_continentIdMapping.get(l_continent.getContinentID()) +  " " + l_country.getXPosition()+  " " + l_country.getYPosition() );
				}				
			}			
			l_map.append("\n");
			
			l_map.append("\n[borders]");
			for (Country l_country : d_gameContext.getCountries().values()) {
				l_map.append("\n" + l_country.getCountryID());
				for (Country l_neighborCountry : l_country.getNeighbors().values()) {
					l_map.append(" " + l_neighborCountry.getCountryID()  );
				}				
			}		
			l_map.append("\n");
			
			//write the content into the map
	        BufferedWriter writer = new BufferedWriter(new FileWriter(l_path + p_fullFileName));
	        writer.write(l_map.toString());
	        
	        writer.close();
	        return true;
		}
		catch(Exception ex) {
			throw ex;
		}		
	}

	/**
	 * Load a map from an existing â€œdominationâ€� map file,
	 * or create a new map from scratch if the file does not exist.
	 * @param p_fileName file name
	 * @return if success
	 */
	public boolean editMap (String p_fileName) {

		String l_mapDirectory = WarzoneProperties.getWarzoneProperties().getGameMapDirectory();
		
		try {
			
			//Clear gameContext
			d_gameContext.reset();
			
			File l_mapFile = new File(l_mapDirectory + p_fileName);
			
			d_gameContext.setMapFileName(p_fileName);

			//Specified file name does not exist (new map)
			if(!l_mapFile.exists() || l_mapFile.isDirectory()) {
				d_logEntryBuffer.logAction("SUCCESS", "Creating a new map: " + p_fileName);
				return true;
			}
			
			Scanner l_scanner = new Scanner(l_mapFile);
			String l_line;
			String[] l_splitArray;
			int l_continentCtr = 1;
			int l_id;
			Country l_country;

			LoadMapPhase l_loadMapPhase = null;
			
			while (l_scanner.hasNextLine()) {
				l_line = l_scanner.nextLine();

				// determine which part it is
				// file part
				if(l_line.equals("[files]")) {

					l_loadMapPhase = LoadMapPhase.FILES;
					l_line = l_scanner.nextLine();
				}
				// continents part
				else if(l_line.equals("[continents]")) {

					l_loadMapPhase = LoadMapPhase.CONTINENTS;
					l_line = l_scanner.nextLine();
				}
				//countries part
				else if (l_line.equals("[countries]")) {

					l_loadMapPhase = LoadMapPhase.COUNTRIES;
					l_line = l_scanner.nextLine();
				}
				//borders part
				else if (l_line.equals("[borders]")) {

					l_loadMapPhase = LoadMapPhase.BORDERS;

					if(!l_scanner.hasNextLine())
						l_loadMapPhase = LoadMapPhase.COMPLETE;
					else{
						l_line = l_scanner.nextLine();
					}
				}

				// read file part
				if(l_loadMapPhase == LoadMapPhase.FILES) {
					
					/*
					 *  [files]
					 *	pic europe_pic.jpg
					 *	map europe_map.gif
					 *	crd europe.cards
					 */
					
					if(l_line.startsWith("pic")) {
						
						d_gameContext.setMapFilePic(l_line.substring(4));
					}
					else if(l_line.startsWith("map")) {
						
						d_gameContext.setMapFileMap(l_line.substring(4));
					}
					else if(l_line.startsWith("crd")) {
						
						d_gameContext.setMapFileCards(l_line.substring(4));
					}
				}
				//read continent part
				else if(l_loadMapPhase == LoadMapPhase.CONTINENTS && !l_line.trim().isEmpty()) {
					
					/*
					 *  [continents]
					 *	North_Europe 5 red
					 *	East_Europe 4 magenta
					 *	South_Europe 5 green
					 *	West_Europe 3 blue
					 */
					
					l_splitArray = l_line.split("\\s+");
										
					d_gameContext.getContinents().put(l_continentCtr,
							new Continent(l_continentCtr, l_splitArray[0], Integer.parseInt(l_splitArray[1]), l_splitArray[2]));
					
					l_continentCtr++;
				}
				//read countries part
				else if(l_loadMapPhase == LoadMapPhase.COUNTRIES && !l_line.trim().isEmpty()) {
					
					/*
					 *  [countries]
					 *	1 England 1 164 126
					 *	2 Scotland 1 158 44
					 *	3 N_Ireland 1 125 70
					 *	4 Rep_Ireland 1 106 90
					 */
					
					l_splitArray = l_line.split("\\s+");
					
					l_id = Integer.parseInt(l_splitArray[0]);
					l_country = new Country(l_id, l_splitArray[1], Integer.parseInt(l_splitArray[3]),
							Integer.parseInt(l_splitArray[4]), d_gameContext.getContinents().get(Integer.parseInt(l_splitArray[2])));
					
					d_gameContext.getCountries().put(l_id, l_country);
					
					d_gameContext.getContinents().get(Integer.parseInt(l_splitArray[2])).getCountries().put(l_id, l_country);
				}
				//read border part
				else if(l_loadMapPhase == LoadMapPhase.BORDERS && !l_line.trim().isEmpty()) {
					
					/*
					 *  [borders]
					 *	1 8 21 6 7 5 2 3 4
					 *	2 8 1 3
					 *	3 1 2
					 *	4 22 1 5	
					 */
					
					l_splitArray = l_line.split("\\s+");
					l_country = d_gameContext.getCountries().get(Integer.parseInt(l_splitArray[0]));
					
					for(int l_temp = 1; l_temp < l_splitArray.length; l_temp++) {
						
						l_id = Integer.parseInt(l_splitArray[l_temp]);
						l_country.getNeighbors().put(l_id, d_gameContext.getCountries().get(l_id));
					}
				}
			}
			//close reading the file
			l_scanner.close();
			
			d_logEntryBuffer.logAction("SUCCESS", "Map succesfully loaded: " + p_fileName);
		    
		} catch (Exception e) {
			d_logEntryBuffer.logAction("ERROR", "An error occured reading the map file: " + p_fileName);
			return false;
		}
		
		return true;
	}

	/**
	 * map Index To CountryId
	 */
	Map<Integer, Integer> d_mapIndexToCountryId = new HashMap<>();

	/**
	 * map CountryId To Index
	 */
	Map<Integer, Integer> d_mapCountryIdToIndex = new HashMap<>();

	/**
	 * map Index To ContinentId
	 */
	Map<Integer, Integer> d_mapIndexToContinentId = new HashMap<>();

	/**
	 * map ContinentId To Index
	 */
	Map<Integer, Integer> d_mapContinentIdToIndex = new HashMap<>();

	/**
	 * list to store the relationship between continents
	 */
	LinkedList<Object>[] l_continentAdjList;
	/**
	 * the record of continent tree
	 */
	int l_continentIndex;

	/**
	 * initiate the list of neighbours
	 * @param p_size list size
	 * @param p_adj list for manipulation
	 * @return list of (country, neighbour)
	 */
	public LinkedList<Object>[] listInit(int p_size, LinkedList<Object>[] p_adj ) {
		p_adj = new LinkedList[p_size];
		for (int l_temp = 0; l_temp < p_size; ++l_temp)
			p_adj[l_temp] = new LinkedList<>();
		return p_adj;
	}

	/**
	 *
	 * Tarjan method is used in connectivity check
	 *
	 * condition1: check if more than one country
	 * condition2: check if each country belongs to one continent
	 * condition3: check if more than one continent
	 * condition4: check if each continent has one country
	 * condition5: check if each continent is strongly connected
	 * condition6: check if the whole map is strongly connected
	 *
	 * @return if map is valid
	 */
	public boolean validateMap() {
		d_mapIndexToContinentId.clear();
		d_mapContinentIdToIndex.clear();

		// condition1: check if more than one country
		int l_countryCount = d_gameContext.getCountries().size();
		if ( l_countryCount <= 1 ) {
			GenericView.printError("The map should contain more than one country.");
			return false;
		}
 		// condition2: check if each country belongs to one continent
		for (Country l_countryTemp : d_gameContext.getCountries().values()){
			if(l_countryTemp.getContinent() == null) {
				GenericView.printError("Each country should belong to one continent.");
				return false;
			}

		}
		// condition3: check if more than one continent
		Map<Integer, Continent> l_continent = d_gameContext.getContinents();
		if ( l_continent.size() <= 1 ) {
			GenericView.printError("The map should contain at least one continent.");
			return false;
		}
		// condition4: check if each continent has one country
		for( Continent l_continentTemp : l_continent.values()) {
			if(l_continentTemp.getCountries().size() < 1) {
				GenericView.printError("Each countinent should have at least a country.");
				return false;
			}
		}

		// condition5: check if each continent is strongly connected
		// l_continentAdjList is the list store the relationship between continents
		l_continentAdjList = new LinkedList[l_continent.size()];
		l_continentAdjList = listInit(l_continent.size(), l_continentAdjList);
		l_continentIndex = 0;
		for( Continent l_continentTemp : l_continent.values()) {
			// add the from continent as the head of the list l_continentAdjList
			// also, check if the continent is recored in the map, if not, add to the map
			if( !d_mapContinentIdToIndex.containsKey(l_continentTemp.getContinentID()) ) {
				l_continentAdjList[l_continentIndex].add(l_continentTemp);
				d_mapIndexToContinentId.put(l_continentIndex, l_continentTemp.getContinentID());
				d_mapContinentIdToIndex.put(l_continentTemp.getContinentID(), l_continentIndex++);
			}
			else{
				int l_temp = d_mapContinentIdToIndex.get(l_continentTemp.getContinentID());
				l_continentAdjList[l_temp].add(l_continentTemp);
			}
			if (!validateSubGraph(l_continentTemp))
				return false;
		}

		// condition6: check if the whole map is strongly connected
		// if each connected continents are strongly connected, the whole map is connected
		return ifConnected(l_continent.size(), l_continentAdjList);
	}

	/**
	 * validate if the continent graph is connected
	 * @param p_continent continent map
	 * @return true if it is valid
	 */
	public boolean validateSubGraph(Continent p_continent) {
		// initiat the records of country tree
		int l_countryIndex = 0;
		d_mapIndexToCountryId.clear();
		d_mapCountryIdToIndex.clear();

		//l_countrylist is the list store the relationship between the countries in the same continent
		LinkedList<Object>[] l_countryList = new LinkedList[p_continent.getCountries().size()];
		l_countryList = listInit(p_continent.getCountries().size(), l_countryList);

		for (Country l_fromCountry : p_continent.getCountries().values()) {

			// add the from country as the head of the list l_countryList
			// also, check if the country is recored in the map, if not, add to the map
			if (!d_mapCountryIdToIndex.containsKey(l_fromCountry.getCountryID())) {
				l_countryList[l_countryIndex].add(l_fromCountry);
				d_mapIndexToCountryId.put(l_countryIndex, l_fromCountry.getCountryID());
				d_mapCountryIdToIndex.put(l_fromCountry.getCountryID(), l_countryIndex++);
			} else {
				int l_temp = d_mapCountryIdToIndex.get(l_fromCountry.getCountryID());
				l_countryList[l_temp].add(l_fromCountry);
			}

			for (Country l_toCountry : l_fromCountry.getNeighbors().values()) {
				// check if the country is in the same continent of from country
				if (l_toCountry.getContinent().getContinentID() == p_continent.getContinentID()) {
					// check if the county is already recorded in the map
					if (!d_mapCountryIdToIndex.containsKey(l_toCountry.getCountryID())) {
						d_mapIndexToCountryId.put(l_countryIndex, l_toCountry.getCountryID());
						d_mapCountryIdToIndex.put(l_toCountry.getCountryID(), l_countryIndex++);
					}
					// add country to the list of from country
					int l_temp = d_mapCountryIdToIndex.get(l_fromCountry.getCountryID());
					l_countryList[l_temp].add(l_toCountry);
				}
				// if not, add the country's continent to the list l_continentAdjList
				else {
					if (!d_mapContinentIdToIndex.containsKey(l_toCountry.getContinent().getContinentID())) {
						d_mapIndexToContinentId.put(l_continentIndex, l_toCountry.getContinent().getContinentID());
						d_mapContinentIdToIndex.put(l_toCountry.getContinent().getContinentID(), l_continentIndex++);
					}
					int l_temp = d_mapContinentIdToIndex.get(l_fromCountry.getContinent().getContinentID());
					l_continentAdjList[l_temp].add(l_toCountry.getContinent());
				}
			}
		}

		if (!ifConnected(p_continent.getCountries().size(), l_countryList)) {
			GenericView.printError("The continent " + p_continent.getContinentName() + " is not a connected graph");
			return false;
		} else
			GenericView.printDebug("continents " + p_continent.getContinentName() + " is connected");
		return true;
	}

	/**
	 * the sequence it is read in tree
	 */
	private int l_seq = 0;


	/**
	 * using Tarjan's algorithm (use DFS traversal) to get the connected parts in the graph
	 * the graph is strongly connected if there is only one connected part
	 * @param p_size size of list
	 * @param p_list the graph stored as tree in List for DFS
	 * @return if the graph is strongly connected components
	 */
	public boolean ifConnected(int p_size, LinkedList<Object>[] p_list) {

		//initialization
		List<List<Integer>> l_resList = new LinkedList<>();
		int l_disc[] = new int[p_size];
		int l_low[] = new int[p_size];
		for (int l_temp = 0; l_temp < p_size; l_temp++) {
			l_disc[l_temp] = -1;
			l_low[l_temp] = -1;
		}
		l_seq = 0;

		boolean l_stackMember[] = new boolean[p_size];
		Stack<Integer> l_st = new Stack<Integer>();

		// DFS traversal
		for (int l_curr = 0; l_curr < p_size; l_curr++) {
			if (l_disc[l_curr] == -1)
				innerDFS(l_curr, l_low, l_disc, l_stackMember, l_st, p_list, l_resList);
		}
		GenericView.printDebug( l_resList.toString() );

		// the l_resList store the connected parts in the graph.
		if(l_resList.size() == 1)
			return true;
		else
			return false;
	}

	/**
	 * inner function of DFS traversal
	 * @param p_cur current node in tree
	 * @param p_low the low number of the node
	 * @param p_disc the discovery number of the node
	 * @param p_stackMember if the node is a stack number
	 * @param p_st the stack of the nodes
	 * @param p_list the list for traversal
	 * @param p_resList the return list for saving the results
	 */
	private void innerDFS(int p_cur, int p_low[], int p_disc[],
						  boolean p_stackMember[], Stack<Integer> p_st, LinkedList<Object>[] p_list,
						  List<List<Integer>> p_resList) {
		// record the sequence information of the node
		boolean l_isCountry = false;
		p_disc[p_cur] = l_seq;
		p_low[p_cur] = l_seq;
		l_seq += 1;
		p_stackMember[p_cur] = true;
		p_st.push(p_cur);

		// Go through all vertices adjacent to this
		List<Object> l_clist = p_list[p_cur];
		if(l_clist == null || l_clist.size() ==0)
			return;

		// if it is a connected country graph
		if( l_clist.get(0) instanceof Country ){

			l_isCountry = true;
			Iterator<Object> l_temp = p_list[p_cur].iterator();
			while( l_temp.hasNext() ){
				Country l_cnext = (Country) l_temp.next();
				int l_nindex = d_mapCountryIdToIndex.get(l_cnext.getCountryID());
				if(p_disc[l_nindex] == -1){
					innerDFS(l_nindex, p_low, p_disc, p_stackMember, p_st, p_list, p_resList);
					p_low[p_cur] = Math.min(p_low[p_cur], p_low[l_nindex]);
				}
				else if(p_stackMember[l_nindex] == true) {
					p_low[p_cur] = Math.min(p_low[p_cur], p_disc[l_nindex]);
				}
			}
		}
		//if it is a continnet graph
		else {
			Iterator<Object> l_temp = p_list[p_cur].iterator();
			while( l_temp.hasNext() ){
				Continent l_cnext = (Continent) l_temp.next();
				int l_nindex = d_mapContinentIdToIndex.get(l_cnext.getContinentID());
				if(p_disc[l_nindex] == -1){
					innerDFS(l_nindex, p_low, p_disc, p_stackMember, p_st, p_list, p_resList);
					p_low[p_cur] = Math.min(p_low[p_cur], p_low[l_nindex]);
				}
				else if(p_stackMember[l_nindex] == true) {
					p_low[p_cur] = Math.min(p_low[p_cur], p_disc[l_nindex]);
				}
			}
		}

		// record the result and pop the stack
		int w = -1;
		if (p_low[p_cur] == p_disc[p_cur]) {
			List<Integer> l_list = new ArrayList<>();
			while (w != p_cur) {
				w = (int) p_st.pop();
				if(l_isCountry)
					l_list.add(d_mapIndexToCountryId.get(w));
				else
					l_list.add(d_mapIndexToContinentId.get(w));
				p_stackMember[w] = false;
			}
			p_resList.add(l_list);
		}
	}
}
