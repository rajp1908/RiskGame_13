/**
 * 
 */
package com.risk6441.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.entity.Country;
import com.risk6441.gameutilities.GameUtilities;

/**
 * @author Jemish
 * @author Raj
 *
 */
public class DiceModelTest {

	
	
	private static Country attackingCountry;
	private static Country defendingCountry;
	private static ArrayList<Integer> attackerDiceValuesList;
	private static ArrayList<Integer> defenderDiceValuesList;
	private static DiceModel diceModel;

	static int defenderDiceNumber, attackerDiceNumber;
	

	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		GameUtilities.isTestMode = true;
		attackingCountry = new Country();
		defendingCountry = new Country();	
		attackerDiceValuesList = new ArrayList<Integer>();
		defenderDiceValuesList = new ArrayList<Integer>();
		diceModel = new DiceModel(attackingCountry, defendingCountry);
	}	

	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeTest() {
		attackingCountry.setArmy(3);
		defendingCountry.setArmy(3);
		attackerDiceValuesList.add(diceModel.randomNumber());
		defenderDiceValuesList.add(diceModel.randomNumber());
		attackerDiceNumber = attackerDiceValuesList.get(0);
		defenderDiceNumber = defenderDiceValuesList.get(0);
	}
	
	/**
	 * This method is used to check whether more dice rolls are available.
	 */
	@Test
	public void moreDiceRollAvailable() {
		assertTrue(attackingCountry.getArmy() > 2);
		assertTrue(defendingCountry.getArmy() > 0);
		assertTrue(diceModel.isMoreDiceRollAvailable());
	}
	
	
	/**
	 * This method checks whether more dice rolls are available if attacking country has only one army left.
	 */
	@Test
	public void moreDiceRollAvailablePassWrongValues() {
		attackingCountry.setArmy(1);
		defendingCountry.setArmy(0);
		assertFalse(attackingCountry.getArmy() > 2);
		assertFalse(defendingCountry.getArmy() > 0);
		assertFalse(diceModel.isMoreDiceRollAvailable());
	}
	
	
	/**
	 * This method performs a attack and reduce army of both player by one and checks that at least one player should have lost the match.
	 */
	@Test
	public void updateArmiesAfterAttackArmiesTest() {
		List<String> playResult = new ArrayList<>();
		int checkAttackerArmies = attackingCountry.getArmy() - 1;
		int checkDefenderArmies = defendingCountry.getArmy() - 1;	
		diceModel.updateArmiesAfterAttackFinished(defenderDiceNumber, attackerDiceNumber, playResult);
		assertTrue(attackingCountry.getArmy() ==  checkAttackerArmies||
				 defendingCountry.getArmy() == checkDefenderArmies);
	}
	
	
	/**
	 * This method performs a attack and reduce army of both player by one and checks that at least one player should have lost the match.
	 * This method checks the result array which is a array of string.
	 */
	@Test
	public void getPlayResultAfterDiceThrown() {
		List<String> playResult = new ArrayList<>();
		diceModel.updateArmiesAfterAttackFinished(defenderDiceNumber, attackerDiceNumber, playResult);
		assertTrue(playResult.get(0).equals("1 army lost by Defender") ||playResult.get(0).equals("1 army lost by Attacker"));
	}	
}
