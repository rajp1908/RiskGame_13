package com.risk6441.gameutilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidMap;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.models.PlayerModel;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * This is a test class for GameUtilities. {@link GameUtilities}
 * @author Jemish
 * @author Raj
 */
public class GameUtilitiesTest {

	static Map map;
	static Continent continent;
	String continentName = "Asia";
	String counName1 = "India";
	String counName2 = "Canada";
	
	@FXML
	static TextArea txtAreaMsg;
	
	static JFXPanel fxPanel;
	
	int controlValue1 = 5;
	static Country coun1;
	static Country coun2;
	static Country adjCOuntry;
	int x1=1;
	int y1=1;
	int x2=2;
	int y2=2;
	static Player player;
	
	List<Continent> listOfContinents = new ArrayList<>();	
	List<Country> listOfCojuntries = new ArrayList<>();
	static List<Player> playerList;
	String mapAuthor = "Arthur";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		continent = new Continent();
		coun1 = new Country();
		coun2 = new Country();
		map = new Map();
		player = new Player(1, "author");
		fxPanel = new JFXPanel();
		txtAreaMsg =  new TextArea();
		playerList =new ArrayList<Player>();
	}
	
	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void before() {
		map = new Map();
		listOfContinents = new ArrayList<>();	
		listOfCojuntries = new ArrayList<>();
		
		continent.setName(continentName);
		continent.setValue(controlValue1);
		
		coun1.setName(counName1);
		coun1.setBelongToContinent(continent);
		coun2.setName(counName2);
		coun2.setBelongToContinent(continent);		
		
		coun2.getAdjacentCountries().add(coun1);
		coun1.setAdjacentCountries(coun2.getAdjacentCountries());		
		coun1.getAdjacentCountries().add(coun1);
		coun2.setAdjacentCountries(coun1.getAdjacentCountries());
		
		listOfContinents.add(continent);
		map.setContinents(listOfContinents);
		
		player.setArmies(99);
		listOfCojuntries.add(coun1);
		player.setAssignedCountry(listOfCojuntries);
		playerList.add(player);
	}
	


	
	/**
	 * This method tests the allocation of country to player is working or not.
	 * @throws InvalidMap Throws invalid map if country allocation is not done properly.
	 */
	@Test
	public void testAllocateCountryToPlayer() throws InvalidMap {
		playerList.get(0).getAssignedCountry().clear();
		listOfCojuntries.clear();
		listOfCojuntries.add(coun1);
		listOfCojuntries.add(coun2);
		continent.setCountries(listOfCojuntries);
		map.setContinents(listOfContinents);
		System.out.println(map.getContinents().get(0).getCountries());
		GameUtilities.allocateCountryToPlayer(map, playerList, txtAreaMsg);
		Assert.assertTrue(playerList.get(0).getAssignedCountry().size() > 0 );		
	}
	
	
}
