package warzone.service;

import  warzone.model.*;
import warzone.view.GenericView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * service class for map
 */
public class MapService {

	private GameContext d_gameContext;

	/**
	 * constructor
	 * @param p_gameContext the gamecontext
	 */
	public MapService(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
	}

	/**
	 * save map to file
	 * @param p_fileName file name
	 * @return if success
	 * @throws IOException if any io exception
	 */
	public boolean saveMap(String p_fileName) throws IOException {
		try{
			String l_fullFileName = p_fileName + ".map";
			
			//build the content using StringBuilder
			StringBuilder l_map = new StringBuilder();
			l_map.append("; map: " + p_fileName);
			l_map.append("\n; map made with the 6441 Super Team");
			l_map.append("\n; 6441.net  1.0.0.1 ");
			l_map.append("\n");
			
			l_map.append("\n[files]");
			l_map.append("\npic "+ p_fileName +"_pic.jpg");
			l_map.append("\nmap "+ p_fileName +"_map.gif");
			l_map.append("\ncrd "+ p_fileName + "europe.cards");
			l_map.append("\n");
			
			l_map.append("\n[continents]");
			for (Continent l_continent : d_gameContext.getContinents().values()) {
				l_map.append("\n" + l_continent.getContinentName()  +  " " + l_continent.getBonusReinforcements() +  " " + l_continent.getColor());
			}
			l_map.append("\n");
			
			l_map.append("\n[countries]");
			for (Continent l_continent : d_gameContext.getContinents().values()) {
				for (Country l_country : l_continent.getCountries().values()) {
					l_map.append("\n" + l_country.getCountryID()  +  " " + l_country.getCountryName()  +  " " + l_continent.getContinentID() +  " " + l_country.getXPosition()+  " " + l_country.getYPosition() );
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
//			Path l_fileFullPath = Path.of(l_fullFileName);	        
//	        Files.write(l_fileFullPath, l_map.toString().getBytes());
//	        
	        
	        BufferedWriter writer = new BufferedWriter(new FileWriter(l_fullFileName));
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
		
		String mapDirectory = null;
		
		try {
			
			//Get the map directory from the properties file
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
			mapDirectory = properties.getProperty("gameMapDirectory");
			
		} catch (IOException ex) {
				
			GenericView.printError("Error loading properties file.");
			return false;
		}
		
		try {
			
			//Clear gameContext
			d_gameContext.getContinents().clear();
			d_gameContext.getCountries().clear();
			
			File mapFile = new File(mapDirectory + p_fileName);
			
			d_gameContext.setMapFileName(p_fileName);

			//Specified file name does not exist (new map)
			if(!mapFile.exists() || mapFile.isDirectory()) { 

				GenericView.printSuccess("Creating a new map: " + p_fileName);
				return true;
			}
			
			Scanner scanner = new Scanner(mapFile);
			String line;
			String[] splitArray;
			int continentCtr = 1;
			int id;
			Country country;
			
			boolean processingFiles = false;
			boolean processingContinents = false;
			boolean processingCountries = false;
			boolean processingBorders = false;
			
			while (scanner.hasNextLine()) {
				
				line = scanner.nextLine();
				
				if(line.equals("[files]")) {
					
					processingFiles = true;
					processingContinents = false;
					processingCountries = false;
					processingBorders = false;
					
					line = scanner.nextLine();
				}
				else if(line.equals("[continents]")) {
					
					processingFiles = false;
					processingContinents = true;
					processingCountries = false;
					processingBorders = false;
					
					line = scanner.nextLine();
				}
				else if (line.equals("[countries]")) {
					
					processingFiles = false;
					processingContinents = false;
					processingCountries = true;
					processingBorders = false;
					
					line = scanner.nextLine();
				}
				else if (line.equals("[borders]")) {
					
					processingFiles = false;
					processingContinents = false;
					processingCountries = false;
					processingBorders = true;
					
					line = scanner.nextLine();
				}
				
				if(processingFiles) {
					
					/*
					 *  [files]
					 *	pic europe_pic.jpg
					 *	map europe_map.gif
					 *	crd europe.cards
					 */
					
					if(line.startsWith("pic")) {
						
						d_gameContext.setMapFilePic(line.substring(4));
					}
					else if(line.startsWith("map")) {
						
						d_gameContext.setMapFileMap(line.substring(4));
					}
					else if(line.startsWith("crd")) {
						
						d_gameContext.setMapFileCards(line.substring(4));
					}
				}
				else if(processingContinents && !line.trim().isEmpty()) {
					
					/*
					 *  [continents]
					 *	North_Europe 5 red
					 *	East_Europe 4 magenta
					 *	South_Europe 5 green
					 *	West_Europe 3 blue
					 */
					
					splitArray = line.split("\\s+");
										
					d_gameContext.getContinents().put(continentCtr, 
							new Continent(continentCtr, splitArray[0], Integer.parseInt(splitArray[1]), Color.valueOf(splitArray[2].toUpperCase())));
					
					continentCtr++;
				}
				else if(processingCountries && !line.trim().isEmpty()) {
					
					/*
					 *  [countries]
					 *	1 England 1 164 126
					 *	2 Scotland 1 158 44
					 *	3 N_Ireland 1 125 70
					 *	4 Rep_Ireland 1 106 90
					 */
					
					splitArray = line.split("\\s+");
					
					id = Integer.parseInt(splitArray[0]);
					country = new Country(id, splitArray[1], Integer.parseInt(splitArray[3]), 
							Integer.parseInt(splitArray[4]), d_gameContext.getContinents().get(Integer.parseInt(splitArray[2])));
					
					d_gameContext.getCountries().put(id, country);
					
					d_gameContext.getContinents().get(Integer.parseInt(splitArray[2])).getCountries().put(id, country);
				}
				else if(processingBorders && !line.trim().isEmpty()) {
					
					/*
					 *  [borders]
					 *	1 8 21 6 7 5 2 3 4
					 *	2 8 1 3
					 *	3 1 2
					 *	4 22 1 5	
					 */
					
					splitArray = line.split("\\s+");
					country = d_gameContext.getCountries().get(Integer.parseInt(splitArray[0]));
					
					for(int i = 1; i < splitArray.length; i++) {
						
						id = Integer.parseInt(splitArray[i]);
						country.getNeighbors().put(id, d_gameContext.getCountries().get(id));
					}
				}
			}
		    
			scanner.close();
			
			GenericView.printSuccess("Map succesfully loaded: " + p_fileName);
		    
		} catch (Exception e) {
		      
			GenericView.printError("An error occured reading the map file: " + p_fileName);
			return false;
		}
		
		return true;
	}

	/*
	 * 	Map validation
	 */
	// record the relationship between index of linkedlist and country/continent id
	Map<Integer, Integer> d_mapIndexToCountryId = new HashMap<>();
	Map<Integer, Integer> d_mapCountryIdToIndex = new HashMap<>();
	Map<Integer, Integer> d_mapIndexToContinentId = new HashMap<>();
	Map<Integer, Integer> d_mapContinentIdToIndex = new HashMap<>();

	LinkedList<Object>[] l_continentAdjList; // list to store the relationship between continents
	int l_continentIndex = 0; // initiate the record of continent tree

	/**
	 * initiate the list of neighbours
	 * @param p_size list size
	 * @param p_adj list for manipulation
	 * @return list of (country, neighbour)
	 */
	public LinkedList<Object>[] listInit(int p_size, LinkedList<Object>[] p_adj ) {
		p_adj = new LinkedList[p_size];
		for (int i = 0; i < p_size; ++i)
			p_adj[i] = new LinkedList<>();
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
	 * @param p_gameContext game context
	 * @return if map is valid
	 */
	public boolean validateMap(GameContext p_gameContext) {

		d_mapIndexToContinentId.clear();
		d_mapContinentIdToIndex.clear();

		// condition1: check if more than one country
		int l_countryCount = p_gameContext.getCountries().size();
		if ( l_countryCount <= 1 ) {
			GenericView.printError("The map should contain more than one country.");
			return false;
		}
 		// condition2: check if each country belongs to one continent
		for (Country _country : p_gameContext.getCountries().values()){
			if(_country.getContinent() == null)
				GenericView.printError("Each country should belong to one continent.");

		}
		// condition3: check if more than one continent
		Map<Integer, Continent> l_continent = p_gameContext.getContinents();
		if ( l_continent.size() <= 1 ) {
			GenericView.printError("The map should contain at least one continent.");
			return false;
		}
		// condition4: check if each continent has one country
		for( Continent _continent : l_continent.values()) {
			if(_continent.getCountries().size() < 1) {
				GenericView.printError("Each countinent should have at least a country.");
				return false;
			}
		}

		// condition5: check if each continent is strongly connected
		// l_continentAdjList is the list store the relationship between continents
		l_continentAdjList = new LinkedList[l_continent.size()];
		l_continentAdjList = listInit(l_continent.size(), l_continentAdjList);

		for( Continent _continent : l_continent.values()) {
			// add the from continent as the head of the list l_continentAdjList
			// also, check if the continent is recored in the map, if not, add to the map
			if( !d_mapContinentIdToIndex.containsKey(_continent.getContinentID()) ) {
				l_continentAdjList[l_continentIndex].add(_continent);
				d_mapIndexToContinentId.put(l_continentIndex, _continent.getContinentID());
				d_mapContinentIdToIndex.put(_continent.getContinentID(), l_continentIndex++);
			}
			else{
				int i = d_mapContinentIdToIndex.get(_continent.getContinentID());
				l_continentAdjList[i].add(_continent);
			}
			if (!validateSubGraph(_continent))
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

		for (Country _fromCountry : p_continent.getCountries().values()) {

			// add the from country as the head of the list l_countryList
			// also, check if the country is recored in the map, if not, add to the map
			if (!d_mapCountryIdToIndex.containsKey(_fromCountry.getCountryID())) {
				l_countryList[l_countryIndex].add(_fromCountry);
				d_mapIndexToCountryId.put(l_countryIndex, _fromCountry.getCountryID());
				d_mapCountryIdToIndex.put(_fromCountry.getCountryID(), l_countryIndex++);
			} else {
				int i = d_mapCountryIdToIndex.get(_fromCountry.getCountryID());
				l_countryList[i].add(_fromCountry);
			}

			for (Country _toCountry : _fromCountry.getNeighbors().values()) {
				// check if the country is in the same continent of from country
				if (_toCountry.getContinent().getContinentID() == p_continent.getContinentID()) {
					// check if the county is already recorded in the map
					if (!d_mapCountryIdToIndex.containsKey(_toCountry.getCountryID())) {
						d_mapIndexToCountryId.put(l_countryIndex, _toCountry.getCountryID());
						d_mapCountryIdToIndex.put(_toCountry.getCountryID(), l_countryIndex++);
					}
					// add country to the list of from country
					int i = d_mapCountryIdToIndex.get(_fromCountry.getCountryID());
					l_countryList[i].add(_toCountry);
				}
				// if not, add the country's continent to the list l_continentAdjList
				else {
					if (!d_mapContinentIdToIndex.containsKey(_toCountry.getContinent().getContinentID())) {
						d_mapIndexToContinentId.put(l_continentIndex, _toCountry.getContinent().getContinentID());
						d_mapContinentIdToIndex.put(_toCountry.getContinent().getContinentID(), l_continentIndex++);
					}
					int i = d_mapContinentIdToIndex.get(_fromCountry.getContinent().getContinentID());
					l_continentAdjList[i].add(_toCountry.getContinent());
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

	private int l_seq = 0; // the sequence it is read in tree


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
		for (int i = 0; i < p_size; i++) {
			l_disc[i] = -1;
			l_low[i] = -1;
		}
		l_seq = 0;

		boolean l_stackMember[] = new boolean[p_size];
		Stack<Integer> l_st = new Stack<Integer>();

		// DFS traversal
		for (int _curr = 0; _curr < p_size; _curr++) {
			if (l_disc[_curr] == -1)
				innerDFS(_curr, l_low, l_disc, l_stackMember, l_st, p_list, l_resList);
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
			Iterator<Object> i = p_list[p_cur].iterator();
			while( i.hasNext() ){
				Country c_next = (Country) i.next();
				int n_index = d_mapCountryIdToIndex.get(c_next.getCountryID());
				if(p_disc[n_index] == -1){
					innerDFS(n_index, p_low, p_disc, p_stackMember, p_st, p_list, p_resList);
					p_low[p_cur] = Math.min(p_low[p_cur], p_low[n_index]);
				}
				else if(p_stackMember[n_index] == true) {
					p_low[p_cur] = Math.min(p_low[p_cur], p_disc[n_index]);
				}
			}
		}
		//if it is a continnet graph
		else {
			Iterator<Object> i = p_list[p_cur].iterator();
			while( i.hasNext() ){
				Continent c_next = (Continent) i.next();
				int n_index = d_mapContinentIdToIndex.get(c_next.getContinentID());
				if(p_disc[n_index] == -1){
					innerDFS(n_index, p_low, p_disc, p_stackMember, p_st, p_list, p_resList);
					p_low[p_cur] = Math.min(p_low[p_cur], p_low[n_index]);
				}
				else if(p_stackMember[n_index] == true) {
					p_low[p_cur] = Math.min(p_low[p_cur], p_disc[n_index]);
				}
			}
		}

		// record the result and pop the stack
		int w = -1;
		if (p_low[p_cur] == p_disc[p_cur]) {
			List<Integer> _list = new ArrayList<>();
			while (w != p_cur) {
				w = (int) p_st.pop();
				if(l_isCountry)
					_list.add(d_mapIndexToCountryId.get(w));
				else
					_list.add(d_mapIndexToContinentId.get(w));
				p_stackMember[w] = false;
			}
			p_resList.add(_list);
		}
	}
}
