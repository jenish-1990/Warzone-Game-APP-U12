package warzone.service;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import warzone.controller.MapController;
import warzone.controller.StartupController;
import warzone.model.Continent;
import warzone.model.Country;
import warzone.model.GameContext;
import warzone.model.Player;
import warzone.model.WarzoneProperties;
import warzone.view.GenericView;

/**
 * This class will test the correctness of StartupService.
 *
 */
public class StartupServiceTest {

	GameContext d_gameContext;
	StartupController d_startupController;
	
	/**
	 * This method can set up game context before test cases begin.
	 */
	@Before
	public void setup() {
		
		d_gameContext = GameContext.getGameContext();
		d_gameContext.getContinents().clear();
		d_gameContext.getCountries().clear();
		d_gameContext.getPlayers().clear();
	}
	
	/**
	 * This method can clear the current game context so that it will not affect other
	 * test cases.
	 * @throws Exception exception when reading properties file
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		GameContext.clear();
	}
	
	/**
	 * This method can test the correctness of loadmap.
	 */
	@Test
	public void testLoadMap() {
		
		System.out.println("=====================================");
		System.out.println("testAssignCountries()");
		System.out.println("=====================================");
		
		d_startupController = new StartupController(d_gameContext);
		d_startupController.loadMap("europe.map");
		
		System.out.println("Map File Name: " + d_gameContext.getMapFileName());
		System.out.println("Map File Pic: " + d_gameContext.getMapFilePic());
		System.out.println("Map File Map: " + d_gameContext.getMapFileMap());
		System.out.println("Map File Name: " + d_gameContext.getMapFileCards());
		
		System.out.println();
		
		System.out.println("Number of Continents: " + d_gameContext.getContinents().size());
		System.out.println("Number of Countries: " + d_gameContext.getCountries().size());
		System.out.println();
		
		assertTrue(d_gameContext.getContinents().size() == 4);
		assertTrue(d_gameContext.getCountries().size() == 24);
	}
	
	@Test
	public void testAssignCountries() {
		
		System.out.println("=====================================");
		System.out.println("testAssignCountries()");
		System.out.println("=====================================");
		
		d_gameContext.getPlayers().put("player1", new Player("player1"));
		d_gameContext.getPlayers().put("player2", new Player("player2"));
		d_gameContext.getPlayers().put("player3", new Player("player3"));
		d_gameContext.getPlayers().put("player4", new Player("player4"));
		
		d_gameContext.getCountries().put(1, new Country(1, "country01", 5, 5, null));
		d_gameContext.getCountries().put(2, new Country(2, "country02", 5, 5, null));
		d_gameContext.getCountries().put(3, new Country(3, "country03", 5, 5, null));
		d_gameContext.getCountries().put(4, new Country(4, "country04", 5, 5, null));
		d_gameContext.getCountries().put(5, new Country(5, "country05", 5, 5, null));
		d_gameContext.getCountries().put(6, new Country(6, "country06", 5, 5, null));
		d_gameContext.getCountries().put(7, new Country(7, "country07", 5, 5, null));
		d_gameContext.getCountries().put(8, new Country(8, "country08", 5, 5, null));
		d_gameContext.getCountries().put(9, new Country(9, "country09", 5, 5, null));
		d_gameContext.getCountries().put(10, new Country(10, "country10", 5, 5, null));
		
		d_startupController = new StartupController(d_gameContext);
		d_startupController.assignCountries();
		
		//Create a list of playerIDs from the game context and shuffle their order
		List<String> playerNames = new ArrayList<String>(d_gameContext.getPlayers().keySet());
		List<Integer> neighborIDs;
		Player player;
		
		for(String playerID : playerNames) {	
			
			player = d_gameContext.getPlayers().get(playerID);
			System.out.print(player.getName() + " neighbors: ");
			
			neighborIDs = new ArrayList<Integer>(player.getConqueredCountries().keySet());
			
			for(Integer neighborID : neighborIDs) {
				
				System.out.print(d_gameContext.getCountries().get(neighborID).getCountryName() + " ");
			}
			
			System.out.println();
			
			assertTrue(player.getConqueredCountries().size() >= 2);
		}
		
		//Create a list of playerIDs from the game context and shuffle their order
		List<Integer> countryIDs = new ArrayList<Integer>(d_gameContext.getCountries().keySet());
		int player1CountryCtr = 0, player2CountryCtr = 0, player3CountryCtr = 0, player4CountryCtr = 0;
		Country country;
		
		for(Integer countryID : countryIDs) {
			
			country = d_gameContext.getCountries().get(countryID);
			
			if(country.getOwner().getName().equals("player1")) {
				
				player1CountryCtr++;
			}
			else if(country.getOwner().getName().equals("player2")) {
				
				player2CountryCtr++;
			}
			else if(country.getOwner().getName().equals("player3")) {
				
				player3CountryCtr++;
			}
			else if(country.getOwner().getName().equals("player4")) {
				
				player4CountryCtr++;
			}
		}
		
		System.out.println("player1CountryCtr: " + player1CountryCtr);
		System.out.println("player2CountryCtr: " + player2CountryCtr);
		System.out.println("player3CountryCtr: " + player3CountryCtr);
		System.out.println("player4CountryCtr: " + player4CountryCtr);
		
		assertTrue(player1CountryCtr >= 2 && player2CountryCtr >= 2 && player3CountryCtr >= 2 && player4CountryCtr >= 2);
		
		System.out.println();
	}
	
	/**
	 * This method can test the correctness of assigning reinforcement for players without any territory.
	 */
	@Test
	public void testAssignReinforcementsNoOwnedContinentsNoOwnedCountries() {
		
		GenericView.printDebug("=====================================");
		GenericView.printDebug("testAssignReinforcementsNoOwnedContinentsNoOwnedCountries()");
		GenericView.printDebug("=====================================");
		
		//Load map file
		d_startupController = new StartupController(d_gameContext);
		d_startupController.loadMap("europe.map");
		
		Player player1 = new Player("player1");
		player1.assignReinforcements(d_gameContext);
		GenericView.printDebug("Total reinforcements assigned: " + player1.getArmiesToDeploy());
		
		assertTrue(player1.getArmiesToDeploy() == WarzoneProperties.getWarzoneProperties().getMinimumReinforcementsEachRound());
	}
	
	/**
	 * This method will test the correctness of assigning reinforcement for players owned fourteen countries.
	 */
	@Test
	public void testAssignReinforcementsNoOwnedContinentsFourteenOwnedCountries() {
		
		GenericView.printDebug("=====================================");
		GenericView.printDebug("testAssignReinforcementsNoOwnedContinentsFourteenOwnedCountries()");
		GenericView.printDebug("=====================================");
		
		//Load map file
		d_startupController = new StartupController(d_gameContext);
		d_startupController.loadMap("europe.map");
		
		Player l_player1 = new Player("player1");
		
		//All but 1 country from continent 1
		l_player1.getConqueredCountries().put(1, d_gameContext.getCountries().get(1));
		l_player1.getConqueredCountries().put(2, d_gameContext.getCountries().get(2));
		l_player1.getConqueredCountries().put(3, d_gameContext.getCountries().get(3));
		l_player1.getConqueredCountries().put(4, d_gameContext.getCountries().get(4));
		l_player1.getConqueredCountries().put(5, d_gameContext.getCountries().get(5));
		l_player1.getConqueredCountries().put(6, d_gameContext.getCountries().get(6));
		
		//All but 1 country from continent 2
		l_player1.getConqueredCountries().put(8, d_gameContext.getCountries().get(8));
		l_player1.getConqueredCountries().put(9, d_gameContext.getCountries().get(9));
		l_player1.getConqueredCountries().put(10, d_gameContext.getCountries().get(10));
		l_player1.getConqueredCountries().put(11, d_gameContext.getCountries().get(11));
		
		//Some but not all countries from continent 3
		l_player1.getConqueredCountries().put(13, d_gameContext.getCountries().get(13));
		l_player1.getConqueredCountries().put(14, d_gameContext.getCountries().get(14));
		l_player1.getConqueredCountries().put(15, d_gameContext.getCountries().get(15));
		l_player1.getConqueredCountries().put(16, d_gameContext.getCountries().get(16));
		
		l_player1.assignReinforcements(d_gameContext);
		GenericView.printDebug("Total reinforcements assigned: " + l_player1.getArmiesToDeploy());
		
		assertTrue(l_player1.getArmiesToDeploy() == 4);
	}
	
	/**
	 * This method will test the correctness of assigning reinforcement for players owned five countries.
	 */
	@Test
	public void testAssignReinforcementsOneOwnedContinentFiveOwnedCountries() {
		
		int l_continentID = 2;
		
		GenericView.printDebug("=====================================");
		GenericView.printDebug("testAssignReinforcementsOneOwnedContinent()");
		GenericView.printDebug("=====================================");
		
		//Load map file
		d_startupController = new StartupController(d_gameContext);
		d_startupController.loadMap("europe.map");
		
		Player player1 = new Player("player1");
		player1.assignReinforcements(d_gameContext);
		GenericView.printDebug("Initial reinforcements assigned: " + player1.getArmiesToDeploy());
		
		assertTrue(player1.getArmiesToDeploy() == WarzoneProperties.getWarzoneProperties().getMinimumReinforcementsEachRound());
		
		/* Bonus: 4 armies
		 * 8 Denmark 2 275 76
		 * 9 Germany 2 261 149
		 * 10 Poland 2 346 141
		 * 11 Czech_Rep 2 308 173
		 * 12 Slovakia 2 356 190
		 */
		
		//Assign the player all the countries from continentID:2
		d_gameContext.getContinents().get(l_continentID).getCountries().forEach(
				
			(countryID, country) -> {
				
				player1.getConqueredCountries().put(countryID, country);
			}
		);
		
		player1.assignReinforcements(d_gameContext);
		
		GenericView.printDebug("Total reinforcements assigned: " + player1.getArmiesToDeploy());
		
		assertTrue(player1.getArmiesToDeploy() == WarzoneProperties.getWarzoneProperties().getMinimumReinforcementsEachRound() + d_gameContext.getContinents().get(l_continentID).getBonusReinforcements());
	}
}