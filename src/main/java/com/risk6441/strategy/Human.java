package com.risk6441.strategy;

import java.util.ArrayList;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;


import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * @author Hardik
 * @author Dolly
 *
 */
public class Human implements IStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.
	 * ObservableList, com.risk6441.entity.Country, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Country> CountryList, Country Country,
			Player currentPlayer,ArrayList<Country> counArList,ArrayList<Country> adjcounArList) {

		if (currentPlayer.getArmies() > 0) {
			if (Country == null) {
				CommonMapUtilities.alertBox("infor", "Please Select a Country.", "Alert");
				return;
			}

			int getArmy = CommonMapUtilities.inputForReinforcement();

			if (getArmy > 0) {
				if (getArmy > currentPlayer.getArmies()) {
					CommonMapUtilities.alertBox("Info",
							"The Army to be moved in reinforce phase should be less than army you have.", "Alert");
					return;
				} else {
					Country.setArmy(Country.getArmy() + getArmy);
					currentPlayer.setArmies(currentPlayer.getArmies() - getArmy);
					CommonMapUtilities.enableOrDisableSave(false);
					GameUtilities.addLogFromText("===" + getArmy + " assigned to : === \n" + Country + "  -- Player "
							+ currentPlayer.getName() + "\n");
					GameUtilities.addLogFromText("======Reinforce Phase Completed. ===========\n");
				}
			} else {
				CommonMapUtilities.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
				return;
			}
		}

	}

		/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.
	 * ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public boolean fortificationPhase(ListView<Country> counList, ListView<Country> adjcounList,
			Player currentPlayer, Map map,ArrayList<Country> counArList,ArrayList<Country> adjcounArList) {
		Country Country = counList.getSelectionModel().getSelectedItem();
		Country adjCountry = adjcounList.getSelectionModel().getSelectedItem();

		if (Country == null) {
			CommonMapUtilities.alertBox("Info", "Please select a Country", "Alert");
			return false;
		} else if (adjCountry == null) {
			CommonMapUtilities.alertBox("Info", "Please select a adjacent Country", "Alert");
			return false;
		} else if (adjCountry.getPlayer() != Country.getPlayer()) {
			CommonMapUtilities.alertBox("Info", "The Adjacent Country does not belong to you.", "Alert");
			return false;
		}

		int armyCount = CommonMapUtilities.inputForFortification();

		if (armyCount > 0) {
			System.out.println("ArmyCount" + armyCount);
			if (armyCount >= Country.getArmy()) {
				CommonMapUtilities.alertBox("Info", "The Army to be moved in fortification phase should be less than "
						+ "existing army in Country.(e.g It can be maximum x-1, if x is the current army in Country.)",
						"Alert");
				return false;
			} else {
				Country.setArmy(Country.getArmy() - armyCount);
				adjCountry.setArmy(adjCountry.getArmy() + armyCount);
				GameUtilities.addLogFromText(
						armyCount + " armies fortified on Country: " + adjCountry.getName() + " From "+Country.getName()+"\n");
				GameUtilities.addLogFromText("======Fortification Done ===========\n");
				return true;
			}
		} else {
			CommonMapUtilities.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
			return false;
		}
	}

}
