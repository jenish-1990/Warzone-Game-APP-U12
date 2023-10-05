package warzone.service;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
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

public class StartupServiceTest {

	GameContext d_gameContext;
	StartupController d_startupController;
	
	@Before
	public void setup() {
		
		d_gameContext = GameContext.getGameContext();
		d_gameContext.getContinents().clear();
		d_gameContext.getCountries().clear();
		d_gameContext.getPlayers().clear();
	}
	
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