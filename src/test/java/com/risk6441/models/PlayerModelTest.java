package com.risk6441.models;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import com.risk6441.configuration.Configuration;
import com.risk6441.configuration.PlayerStrategy;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.gameutilities.GameUtilities;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Raj
 * @author Jemish
 *
 */
public class PlayerModelTest {

	static Map map;
	static Continent continent;
	String continentName = "Asia";
	String terrName1 = "India";
	String terrName2 = "Canada";
	
	@FXML
	static TextArea txtAreaMsg;
	
	@FXML
	static ListView<Country> counListView;
	
	static JFXPanel fxPanel;
	
	int controlValue1 = 5;
	static Country coun1;
	static Country coun2;
	static Country adjCountry;
	int x1=1;
	int y1=1;
	int x2=2;
	int y2=2;
	static Player player;
	static PlayerModel playerModel;
	List<Continent> listOfContinents = new ArrayList<>();	
	List<Country> listOfCountries = new ArrayList<>();
	static List<Player> playerList;
	String mapAuthor = "Robert";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	/**
	 * This method is executed before all the methods of a class.
	 */
	@BeforeClass
	public static void beforeClass() {
		continent = new Continent();
		coun1 = new Country();
		GameUtilities.isTestMode = true;
		coun2 = new Country();
		map = new Map();
		player = new Player(1, "Jemish");
		player.setPlayerStrategy(PlayerStrategy.HUMAN);
		fxPanel = new JFXPanel();
		txtAreaMsg =  new TextArea();
		counListView = new ListView<Country>();
		playerList =new ArrayList<Player>();
		playerModel = new PlayerModel();
	}
	
	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void before() {
		map = new Map();
		listOfContinents = new ArrayList<>();	
		listOfCountries = new ArrayList<>();
		
		continent.setName(continentName);
		continent.setValue(controlValue1);
		
		coun1.setName(terrName1);
		coun1.setBelongToContinent(continent);
		coun2.setName(terrName2);
		coun2.setBelongToContinent(continent);		
		
		coun2.getAdjacentCountries().add(coun1);
		coun1.setAdjacentCountries(coun2.getAdjacentCountries());		
		coun1.getAdjacentCountries().add(coun1);
		coun2.setAdjacentCountries(coun1.getAdjacentCountries());
		coun2.setBelongToContinent(continent);
		
		
		coun1.setPlayer(player);
		coun2.setPlayer(player);
		
		Country coun3 = new Country();
		coun3.getAdjacentCountries().add(coun1);
		coun1.getAdjacentCountries().add(coun3);
		coun3.setPlayer(new Player(2, "Player 2"));
		
		counListView.setEditable(true);
		counListView.getItems().add(coun1);
		counListView.getItems().add(coun2);
		ArrayList<Country> counList = new ArrayList<Country>();
		counList.add(coun1);
		counList.add(coun2);
		continent.setCountries(counList);
		
		listOfContinents.add(continent);
		map.setContinents(listOfContinents);
		
		player.setArmies(99);
		
		listOfCountries.add(coun1);
		listOfCountries.add(coun2);
		player.setAssignedCountry(listOfCountries);
		playerList.add(player);
		
	}
	
	/**
	 * This method tests reinforcement of armies for 99 initials army and player owns entire continent with two Countries.
	 */
	@Test
	public void testCountReinforcementArmiesCaseOne() {		
		Player returnedPlayer = PlayerModel.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 107);
	}
	
	/**
	 * This method counts reinforcement armies with initial army 99 and player has two continents.
	 */
	@Test
	public void testCountReinforcementArmiesUseCaseTwo() {	
		Continent newContinent = new Continent();
		newContinent.setName("North America");
		newContinent.setValue(10);
		
		Country t = new  Country();
		t.setName("Canada");
		t.setPlayer(player);
		List<Country> listOfCoun2 = new ArrayList<>();
		listOfCoun2.add(t);
		newContinent.setCountries(listOfCoun2);
		
		//99 +5 +3 +10
		listOfContinents.add(newContinent);
		map.setContinents(listOfContinents);
		Player returnedPlayer = PlayerModel.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 117);
	}
	
	
	/**
	 * This method counts reinforcement armies for the player who owns one continent and 12 Countries.
	 */
	@Test
	public void testCountReinforcementArmiesUseCaseThree() {	
		Continent newContinent = new Continent();
		newContinent.setName("North America");
		newContinent.setValue(10);
		
		List<Country> listOfCoun2 = new ArrayList<>();
		
		Country t = new  Country();
		t.setName("Canada");
		t.setPlayer(player);
		listOfCoun2.add(t);
		
		t = new  Country();
		t.setName("Canada2");
		t.setPlayer(player);
		listOfCoun2.add(t);

		t = new  Country();
		t.setName("Canada3");
		t.setPlayer(player);
		listOfCoun2.add(t);

		t = new  Country();
		t.setName("Canada4");
		t.setPlayer(player);
		listOfCoun2.add(t);

		t = new  Country();
		t.setName("Canada5");
		t.setPlayer(player);
		listOfCoun2.add(t);

		t = new  Country();
		t.setName("Canada6");
		t.setPlayer(player);
		listOfCoun2.add(t);

		t = new  Country();
		t.setName("Canada7");
		t.setPlayer(player);
		listOfCoun2.add(t);

		t = new  Country();
		t.setName("Canada8");
		t.setPlayer(player);
		listOfCoun2.add(t);

		t = new  Country();
		t.setName("Canada9");
		t.setPlayer(player);
		listOfCoun2.add(t);
		
		t = new  Country();
		t.setName("Canada99");
		t.setPlayer(player);
		listOfCoun2.add(t);

		t = new  Country();
		t.setName("Canada10");
		t.setPlayer(new Player(2,"Krishnan"));
		listOfCoun2.add(t);
		
		newContinent.setCountries(listOfCoun2);
		
		player.getAssignedCountry().addAll(listOfCoun2);
		System.out.println(player.getAssignedCountry().size()+"a");
		//99 +5 +(12/3)
		listOfContinents.add(newContinent);
		map.setContinents(listOfContinents);
		playerModel.setCurrentPlayer(player);
		Player returnedPlayer = playerModel.countReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 108);
	}	
	
	
	/**
	 * This method checks the method which return number of  continent owned by the player.
	 */
	@Test
	public void testGetPlayersContinents() {
		List<Continent> returnedContinents = new ArrayList<>();
		coun1.setPlayer(player);
		coun1.setPlayer(player);
		returnedContinents = playerModel.getPlayersContinents(map, player);
		Assert.assertEquals("Asia", returnedContinents.get(0).getName());
		Assert.assertEquals(1, returnedContinents.size());
	}
	
	
	
	/**
	 * This method tests the method which checks if all player has completed the place army phase or not.
	 */
	@Test
	public void testCheckIfPlayersArmiesExhaustedTrue() {
		playerList = new ArrayList<>();
		playerList.add(new Player(0, "Player0"));
		playerList.get(0).setArmies(0);
		Assert.assertTrue(playerModel.checkIfPlayersArmiesExhausted(playerList));
	}
	
	
	
	/**
	 * This method tests the method which checks if all player has completed the place army phase or not.
	 */
	@Test
	public void testCheckIfPlayersArmiesExhaustedFalse() {
		playerList = new ArrayList<>();
		playerList.add(new Player(0, "Player0"));
		playerList.get(0).setArmies(1);
		Assert.assertFalse(playerModel.checkIfPlayersArmiesExhausted(playerList));
	}
	
	
	

	
	
	/**
	 * This method tests creation of the player.
	 */
	@Test
	public void testCreatePlayer() {
		List<Player> playerTest = new ArrayList<>();
		playerList = new ArrayList<>();
		playerList.add(new Player(0, "Player0"));
		playerList.add(new Player(1, "Player1"));
		playerList.add(new Player(2, "Player2"));
		playerTest = playerModel.createPlayers(playerList.size(), txtAreaMsg);
		Assert.assertEquals(3, playerTest.size());
	}
	
	
	/**
	 *  This method tests that if fortification phase is valid or not.
	 */
	@Test
	public void testisFortificationPhaseValidTrue() {
		coun1.setPlayer(player);
		coun1.setArmy(2);
		coun2.setPlayer(player);
		boolean isFortificationPhaseValid = playerModel.isFortificationPhasePossible(map, player);
		Assert.assertEquals(true, isFortificationPhaseValid);
	}
	
	
	
	/**
	 * This method tests that if fortification phase is valid or not.
	 */
	@Test
	public void testIsFortificationPhaseValidFalse() {
		coun1.setPlayer(player);
		coun1.setArmy(0);
		coun2.setArmy(0);
		coun2.setPlayer(player);
		System.out.println("Now here" +continent.getCountries());
		System.out.println("Now here" +coun2.getArmy());
		boolean isFortificationPhaseValid = playerModel.isFortificationPhasePossible(map, player);
		Assert.assertEquals(false, isFortificationPhaseValid);
	}
	
	
	
	/**
	 * This method tests the number of armies for different players.
	 */
	@Test
	public void testAssignArmiesToPlayers() {
		playerList = new ArrayList<>();
		playerList.add(new Player(1, "A"));
		playerList.add(new Player(2, "B"));
		playerList.add(new Player(3, "C"));
		playerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		Assert.assertEquals(Configuration.ARMIES_THREE_PLAYER, (Integer) playerList.get(0).getArmies());

		playerList = new ArrayList<>();
		playerList.add(new Player(1, "A"));
		playerList.add(new Player(2, "B"));
		playerList.add(new Player(3, "C"));
		playerList.add(new Player(4, "D"));
		playerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		Assert.assertEquals(Configuration.ARMIES_FOUR_PLAYER, (Integer) playerList.get(0).getArmies());

		playerList = new ArrayList<>();
		playerList.add(new Player(1, "A"));
		playerList.add(new Player(2, "B"));
		playerList.add(new Player(3, "C"));
		playerList.add(new Player(4, "D"));
		playerList.add(new Player(5, "E"));
		playerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		Assert.assertEquals(Configuration.ARMIES_FIVE_PLAYER, (Integer) playerList.get(0).getArmies());

		playerList = new ArrayList<>();
		playerList.add(new Player(1, "A"));
		playerList.add(new Player(2, "B"));
		playerList.add(new Player(3, "C"));
		playerList.add(new Player(4, "D"));
		playerList.add(new Player(5, "E"));
		playerList.add(new Player(6, "F"));
		playerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		Assert.assertEquals(Configuration.ARMIES_SIX_PLAYER, (Integer) playerList.get(0).getArmies());
	}
}

