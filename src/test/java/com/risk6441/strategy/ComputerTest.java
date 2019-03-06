package com.risk6441.strategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.gameutilities.GameUtilities;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * This is a test class for the strategy Random.
 * @author Raj
 *
 */
public class ComputerTest {

	Computer ob = new Computer();
	static Map map;
	static Continent continent;
	String continentName = "Asia";
	String counName1 = "India";
	String counName2 = "Canada";
	int controlValue1 = 5;
	static Country coun1;
	static Country coun2;
	static Country adjCountry;
	static ArrayList<Country> counList;
	static ArrayList<Country> adjCounList;
	int x1=1;
	int y1=1;
	int x2=2;
	int y2=2;
	static Player player;
	@FXML
	static ListView<Country> list1;
	
	@FXML
	static ListView<Country> list2;
	
	static JFXPanel fxPanel;
	
	List<Continent> listOfContinents = new ArrayList<>();	
	List<Country> listOfCountries = new ArrayList<>();
	static List<Player> playerList;
	String mapAuthor = "Jemish";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		GameUtilities.isTestMode = true;
		continent = new Continent();
		coun1 = new Country();
		coun2 = new Country();
		map = new Map();
		player = new Player(1, "Krishna");
		counList=new ArrayList<Country>();
		adjCounList=new ArrayList<Country>();
		playerList =new ArrayList<Player>();
		fxPanel = new JFXPanel();
		list1=new ListView<Country>();
		list2=new ListView<Country>();
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
		
		coun1.setName(counName1);
		coun1.setBelongToContinent(continent);
		coun2.setName(counName2);
		coun2.setBelongToContinent(continent);		
		
		coun2.getAdjacentCountries().add(coun1);
		coun1.getAdjacentCountries().add(coun2);
		coun2.setBelongToContinent(continent);
		coun1.setBelongToContinent(continent);
		
		coun1.setPlayer(player);
		coun2.setPlayer(player);
		
		Country coun3 = new Country();
		coun3.getAdjacentCountries().add(coun1);
		coun1.getAdjacentCountries().add(coun3);
		coun3.setPlayer(new Player(2, "Player 2"));
		
		
		
		counList.add(coun1);
		counList.add(coun2);
		
		adjCounList.add(coun3);
		continent.setCountries(counList);
		
		listOfContinents.add(continent);
		map.setContinents(listOfContinents);
		
		player.setArmies(99);
		coun1.setArmy(2);
		coun2.setArmy(1);
		coun3.setArmy(1);
		listOfCountries.add(coun1);
		listOfCountries.add(coun2);
		player.setAssignedCountry(listOfCountries);
		playerList.add(player);
		
		list1.setEditable(true);
		list1.getItems().add(coun1);
		list1.getItems().add(coun2);
		
		list2.setEditable(true);
		list2.getItems().add(coun3);

	}
	
	/**
	 * This method checks if the random Country generates correct output for 1 territory.
	 */
	@Test
	public void testGetRandomCountryFor1Coun() {
		Computer ob = new Computer();
		ArrayList<Country> t = new ArrayList<>();
		t.add(listOfCountries.get(0));
		Country coun = ob.getRandomCountry(t);
		assertEquals(listOfCountries.get(0).getName(), coun.getName());
	}
	
	/**
	 * Tests the normal functionality of the getRandomCountry method.
	 */
	@Test
	public void testGetRandomCountry() {
		ArrayList<Country> t = new ArrayList<>();
		for(Country i : listOfCountries)
			t.add(i);
		coun1.getPlayer().setStrategy(new Computer());
		Country coun = ob.getRandomCountry(t);
		assertEquals(listOfCountries.get(0).getName(), coun.getName());
	}
	
	/**
	 * Tests the normal functionality of fortification method.
	 */
	@Test
	public void testFortificationPhase() {
		coun1.setArmy(100);
		coun2.setArmy(3);
		coun1.getPlayer().setStrategy(new Computer());
		Assert.assertTrue(player.getStrategy().fortificationPhase(list1, list2, player, map, counList, adjCounList));
	}

}
