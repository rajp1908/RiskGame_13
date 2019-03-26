/**
 * 
 */
package com.risk6441.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import com.risk6441.entity.Country;
import com.risk6441.gameutilities.GameUtilities;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author Deep
 *
 */
public class DiceModel extends Observable implements Serializable{
	private Country attackingCountry;

	private Country defendingCountry;

	private List<Integer> attackerDiceValues;

	private List<Integer> defenderDiceValues;

	private int numberOfCountryWon;

	public static int numberOfDiceUsedByAttacker = 0;
	
	/**
	 * Constructor for DiceModel class
	 * @param attackingCountry   details about attacking Country
	 * 
	 * @param defendingCountry    details about defending Country
	 */
	public DiceModel(Country attackingCountry, Country defendingCountry) {
		this.attackingCountry = attackingCountry;
		this.defendingCountry = defendingCountry;
		attackerDiceValues = new ArrayList<>();
		defenderDiceValues = new ArrayList<>();
		numberOfCountryWon = 0;

	}
	
	/**
	 * Get Result of dice after it thrown
	 * 
	 * @return List list of players.
	 */
	public List<String> getPlayResultAfterRoll() {
		List<String> playResult = new ArrayList<>();
		Collections.sort(attackerDiceValues, Collections.reverseOrder());
		Collections.sort(defenderDiceValues, Collections.reverseOrder());

		for (Integer defenderDiceValue : defenderDiceValues) {
			for (Integer attackerDiceValue : attackerDiceValues) {
				updateArmiesAfterAttackFinished(defenderDiceValue, attackerDiceValue, playResult);
				break;
			}
			if (attackerDiceValues.size() >= 1) {
				attackerDiceValues.remove(0);
			}
		}
		return playResult;
	}
	
	/**
	 * Update Armies After attack
	 * 
	 * @param defenderDiceValue  Integer Dice Value of defender
	 * @param attackerDiceValue   Integer DiceValue of attacker
	 * @param playResult List playResult
	 */
	public void updateArmiesAfterAttackFinished(Integer defenderDiceValue, Integer attackerDiceValue, List<String> playResult) {
		if (attackerDiceValue.compareTo(defenderDiceValue) == 0) {
			playResult.add("1 army lost by Attacker");
			if (attackingCountry.getArmy() > 1) {
				attackingCountry.setArmy(attackingCountry.getArmy() - 1);
			}
		} else if (attackerDiceValue.compareTo(defenderDiceValue) > 0) {
			playResult.add("1 army lost by Defender");
			if (defendingCountry.getArmy() > 0) {
				defendingCountry.setArmy(defendingCountry.getArmy() - 1);
			}
		} else {
			playResult.add("1 army lost by Attacker");
			if (attackingCountry.getArmy() > 1) {
				attackingCountry.setArmy(attackingCountry.getArmy() - 1);
			}
		}
	}
	
	
	/**
	 * Cancel Dice Roll
	 */
	public void cancelDiceRoll() {
		setChanged();
		notifyObservers("rollDiceComplete");
	}

	
	/**
	 * This method is used to Move All Armies
	 */
	public void moveAllArmies() {
		int attckingArmies = getAttackingCountry().getArmy();
		getAttackingCountry().setArmy(1);
		getDefendingCountry().setArmy(attckingArmies - 1);
		GameUtilities.addLogFromText("Moved "+(attckingArmies-1)+" armies from "+getAttackingCountry().getName()+" to "+getDefendingCountry().getName()+"\n");
		changeOwnershipOfCountry();
		setChanged();
		notifyObservers("rollDiceComplete");
	}
	
	
	/**
	 * Skip Move Army
	 */
	public void skipMoveArmy() {
		int attckingArmies = getAttackingCountry().getArmy();
		getAttackingCountry().setArmy(attckingArmies - 1);
		getDefendingCountry().setArmy(1);
		changeOwnershipOfCountry();
		setChanged();
		notifyObservers("rollDiceComplete");
	}
	
	
	/**
	 * Move the specific number of armies.
	 * 
	 * @param armiesToMove armies to move
	 * @param message  message
	 * @param btnMoveArmies  button reference
	 */
	public void moveArmies(int armiesToMove, Label message, Button btnMoveArmies) {
		int existingArmy = getAttackingCountry().getArmy();
		if ((existingArmy <= armiesToMove) || (armiesToMove < numberOfDiceUsedByAttacker)) {
			message.setVisible(true);
			message.setText("Maximum Army You Can Move is " + (existingArmy - 1) + "." + " And Minimum is "+numberOfDiceUsedByAttacker+".");
			
			return;
		} else {
			getAttackingCountry().setArmy(existingArmy - armiesToMove);
			getDefendingCountry().setArmy(armiesToMove);
			changeOwnershipOfCountry();
			GameUtilities.exitWindows(btnMoveArmies);
			setChanged();
			notifyObservers("rollDiceComplete");
		}
	}

	
	/**
	 * Reassign Country
	 */
	public void changeOwnershipOfCountry() {
		List<Country> defendersCountries = defendingCountry.getPlayer().getAssignedCountry();
		defendersCountries.remove(defendingCountry);

		defendingCountry.setPlayer(attackingCountry.getPlayer());
		attackingCountry.getPlayer().getAssignedCountry().add(defendingCountry);

	}
	
	/**
	 * This method returns random number.
	 * @return Integer randomNumber between 1 to 6
	 */
	public int randomNumber() {
		return (int) (Math.random() * 6) + 1;
	}

	/**
	 * Check if more dice role available
	 * @return diceRollAvailable
	 */
	public boolean isMoreDiceRollAvailable() {
		if (attackingCountry.getArmy() < 2 || defendingCountry.getArmy() <= 0) {
			return false;
		}else
			return true;
	}


	/**
	 * Get Attacking Country
	 * @return Country attacking Country
	 */
	public Country getAttackingCountry() {
		return attackingCountry;
	}

	/**
	 * Set Attacking Country
	 * @param attackingCountry the attackingCountry to set
	 */
	public void setAttackingCountry(Country attackingCountry) {
		this.attackingCountry = attackingCountry;
	}

	/**
	 * Get Defending Country
	 * @return the defendingCountry
	 */
	public Country getDefendingCountry() {
		return defendingCountry;
	}

	/**
	 * Set Defending Country
	 * @param defendingCountry the defendingCountry to set
	 */
	public void setDefendingCountry(Country defendingCountry) {
		this.defendingCountry = defendingCountry;
	}

	/**
	 * Get Attacker Dice Values
	 * @return the attackerDiceValues
	 */
	public List<Integer> getAttackerDiceValues() {
		return attackerDiceValues;
	}

	/**
	 * Set Attacker Dice Values
	 * @param attackerDiceValues  the attackerDiceValues to set
	 */
	public void setAttackerDiceValues(List<Integer> attackerDiceValues) {
		this.attackerDiceValues = attackerDiceValues;
	}

	/**
	 * Get Defender Dice Values
	 * @return defenderDiceValues
	 */
	public List<Integer> getDefenderDiceValues() {
		return defenderDiceValues;
	}

	/**
	 * Set Defender Dice Values
	 * @param defenderDiceValues  the defenderDiceValues to set
	 */
	public void setDefenderDiceValues(List<Integer> defenderDiceValues) {
		this.defenderDiceValues = defenderDiceValues;
	}

	/**
	 * Get Number Of Country Won
	 * @return the numOfCountryWon
	 */
	public int getNumberOfCountryWon() {
		return numberOfCountryWon;
	}

	/**
	 * Set Number Of Countries Won
	 * @param numOfCountriesWon the numOfCountriesWon to set
	 */
	public void setNumberOfCountryWon(int numOfCountriesWon) {
		this.numberOfCountryWon = numOfCountriesWon;
	}
}
