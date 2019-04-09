/**
 * 
 */
package com.risk6441.strategy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.models.PlayerModel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Dolly
 *
 */
public class Cheater extends Observable implements IStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.
	 * ObservableList, com.risk6441.entity.Territory, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Country> countryList, Country country, Player currentPlayer,
			ArrayList<Country> counArList, ArrayList<Country> adjCounArList) {

		for (Country coun : counArList) {
			coun.setArmy(coun.getArmy() * 2);
			GameUtilities.addLogFromText("Armies Doubled to " + coun.getArmy() + " on " + coun.getName() + "\n");
		}
		currentPlayer.setArmies(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView,
	 * javafx.scene.control.ListView, com.risk6441.models.PlayerModel,
	 * javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Country> counList1, ListView<Country> adjCounList1, PlayerModel playerModel,
			List<Player> playerList, ArrayList<Country> counArList, ArrayList<Country> adjCounArList)
			throws InvalidGameAction {

		this.addObserver(playerModel);
		Iterator<Country> counListIterator = counArList.iterator();
		while (counListIterator.hasNext()) {
			Country attackingCoun = counListIterator.next();
			List<Country> defCounList = getDefendingCoun(attackingCoun);
			if (defCounList.size() > 0) {
				for (Country defCoun : defCounList) {
					defCoun.setArmy((attackingCoun.getArmy() / 2) + 1);
					attackingCoun.setArmy((attackingCoun.getArmy() / 2) + 1);
					System.out.println(attackingCoun.getName() + "(" + attackingCoun.getPlayer().getName() + ""
							+ ") attacking on " + defCoun + "(" + defCoun.getPlayer().getName() + ")\n");
					GameUtilities.addLogFromText(attackingCoun.getName() + " attacking on " + defCoun.getName() + "\n");
					defCoun.getPlayer().getAssignedCountry().remove(defCoun);
					defCoun.setPlayer(attackingCoun.getPlayer());

					attackingCoun.getPlayer().getAssignedCountry().add(defCoun);
					GameUtilities.addLogFromText(
							defCoun.getName() + " is conquered by " + attackingCoun.getPlayer().getName() + "\n");
					playerModel.setNumOfCountryWon(playerModel.getNumOfCountryWon() + 1);
					Platform.runLater(() -> {
						setChanged();
						notifyObservers("oneAttackDoneForCheater");
					});

				}
			} else {
				continue;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.
	 * ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player, com.risk6441.entity.Map)
	 */
	@Override
	public boolean fortificationPhase(ListView<Country> selectedCountry, ListView<Country> adjCountry,
			Player currentPlayer, Map map, ArrayList<Country> counArList, ArrayList<Country> adjCounArList) {
		for (Country coun : counArList) {
			List<Country> adjDefCounList = getDefendingCoun(coun);
			if (adjDefCounList != null && adjDefCounList.size() > 0) {
				GameUtilities.addLogFromText("Doubled the army on country :" + coun.getName() + "\n");
				coun.setArmy(coun.getArmy() * 2);
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.risk6441.strategy.IStrategy#hasAValidAttackMove(javafx.scene.control.
	 * ListView, javafx.scene.control.TextArea)
	 */
	@Override
	public boolean hasAValidAttackMove(ArrayList<Country> countries) {
		boolean isValidAttackMove = false;
		for (Country country : countries) {
			if (getDefendingCoun(country).size() > 0) {
				isValidAttackMove = true;
			}
		}
		if (!isValidAttackMove) {
			GameUtilities.addLogFromText("No valid attack move avialble.\n");
			GameUtilities.addLogFromText("****> Attack phase ended! ***** \n");
		}
		return isValidAttackMove;
	}

}
