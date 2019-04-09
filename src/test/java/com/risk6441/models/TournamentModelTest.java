package com.risk6441.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.configuration.PlayerStrategy;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.exception.InvalidMap;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.strategy.Benevolent;
import com.risk6441.strategy.Cheater;
import com.risk6441.strategy.Random;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * @author Jemish
 * @author Hardik
 */
public class TournamentModelTest {

	/**
	 * The playerModel.
	 */
	static PlayerModel playerModel;

	/**
	 * The tournamentModel.
	 */
	static TournamentModel tournamentModel;

	/**
	 * The txtAreaResult
	 */
	@FXML
	static TextArea txtAreaResult;

	/**
	 * The fxPane
	 */
	static JFXPanel fxPane;
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		playerModel = new PlayerModel();
		GameUtilities.isTestMode = true;
		tournamentModel = new TournamentModel();
		fxPane = new JFXPanel();
		txtAreaResult = new TextArea();	
		tournamentModel.results = new HashMap<String, HashMap<String, String>>();
	}

	/**
	 * This method is invoked at the start of all the test methods.
	 */
	@Before
	public void beforeTest() {
		playerModel.setCurrentPlayer(new Player(1,"Player1"));

	}

	/**
	 * This method creates map clone test.
	 * Map Clone Test
	 * @throws InvalidMap InvalidMapException
	 * @throws CloneNotSupportedException  throws if cloning has some error
	 */
	@Test
	public void createMapCloneTest() throws  InvalidMap, CloneNotSupportedException {
		Map map = new Map();
		Continent continent = new Continent();
		Country country1 = new Country();
		Country country2 = new Country();
		int controlValue = 5;
		continent.setName("Europe");
		continent.setValue(controlValue);
		country1.setName("Germany");
		country1.setBelongToContinent(continent);
		continent.getCountries().add(country1);
		country2.setName("Egypt");
		country2.setBelongToContinent(continent);
		continent.getCountries().add(country2);
		country1.getAdjacentCountries().add(country2);
		country2.getAdjacentCountries().add(country1);
		Continent continent2 = new Continent();
		Country coun = new Country();
		continent2.setName("Australia");
		continent2.setValue(5);
		coun.setName("Sydney");
		coun.setBelongToContinent(continent2);
		coun.getAdjacentCountries().add(country1);
		country1.getAdjacentCountries().add(coun);
		continent2.getCountries().add(coun);
		map.getContinents().add(continent);
		map.getContinents().add(continent2);
		Map clonedMap = (Map) map.clone();
		Assert.assertEquals(map.getContinents().get(0).getName(), clonedMap.getContinents().get(0).getName());
		Assert.assertEquals(map.getContinents().get(0).getCountries().get(0).getName(),
				clonedMap.getContinents().get(0).getCountries().get(0).getName());
		Assert.assertEquals(map.getContinents().get(1).getName(), clonedMap.getContinents().get(1).getName());
		Assert.assertEquals(map.getContinents().get(1).getCountries().get(0).getName(),
				clonedMap.getContinents().get(1).getCountries().get(0).getName());
	}
	
	
}
