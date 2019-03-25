package com.risk6441.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.controller.DiceController;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.models.DiceModel;
import com.risk6441.models.PlayerModel;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

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
					//CommonMapUtilities.enableOrDisableSave(false);
					GameUtilities.addLogFromText("******************  " + getArmy + " assigned to : **************************************** \n" + Country + "  -- Player: "
							+ currentPlayer.getName() + "\n\n");
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
		
		System.out.println("In fortification phase");
		Country Country = counList.getSelectionModel().getSelectedItem();
		Country adjCountry = adjcounList.getSelectionModel().getSelectedItem();
		System.out.println(Country == null);
		if (Country == null) {
			System.out.println("Inside null condition");
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
						armyCount + " armies fortified on Country: " + adjCountry.getName() + " From "+Country.getName()+"\n\n");
				GameUtilities.addLogFromText("**************    Fortification Phase Ends    ********************************\n\n");
				return true;
			}
		} else {
			CommonMapUtilities.alertBox("Info", "Invalid Input. Number should be > 0.", "Alert");
			return false;
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView,
	 * javafx.scene.control.ListView, com.risk6441.models.PlayerModel,
	 * javafx.scene.control.TextArea)
	 */
	public void attackPhase(ListView<Country> countryList, ListView<Country> adjCountryList, PlayerModel playerModel,
			List<Player> playerList,ArrayList<Country> countryArList,ArrayList<Country> adjCountryArList) throws InvalidGameAction {
		Country attackingCountry = countryList.getSelectionModel().getSelectedItem();
		Country defendingCountry = adjCountryList.getSelectionModel().getSelectedItem();
		if (attackingCountry != null && defendingCountry != null) {
			playerModel.isValidAttackMove(attackingCountry, defendingCountry);

			DiceModel diceModel = new DiceModel(attackingCountry, defendingCountry);
			diceModel.addObserver(playerModel);
			final Stage stage = new Stage();
			stage.setTitle("Attack Window");

			DiceController diceController = new DiceController(diceModel, this);

			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("diceview.fxml"));
			loader.setController(diceController);

			Parent root = null;
			try {
				root = (Parent) loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			throw new InvalidGameAction("Please choose both attacking and defending Country.");
		}
	}

}
