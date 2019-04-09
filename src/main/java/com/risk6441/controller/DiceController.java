package com.risk6441.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import com.risk6441.entity.Country;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.models.DiceModel;
import com.risk6441.strategy.Human;
import com.risk6441.strategy.IStrategy;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Controller for the dice
 * 
 * @author Deep
 * @author Dolly
 * @author Jemish
 *
 */

public class DiceController implements Initializable{

    @FXML
    private Label lblDefenderCountryName;

    @FXML
    private Button btnCancelDiceRoll;

    @FXML
    private TextField txtNumberOfArmiesInput;

    @FXML
    private Label lblNoOfArmies;

    @FXML
    private CheckBox chkBoxattackerDice3;

    @FXML
    private CheckBox chkBoxattackerDice2;

    @FXML
    private CheckBox chkBoxdefenderDice1;

    @FXML
    private CheckBox chkBoxattackerDice1;

    @FXML
    private CheckBox chkBoxdefenderDice2;

    @FXML
    private Button btnMoveAllArmies;

    @FXML
    private Button btnAttackAllOutMode;
    
    /**
	 * This method handles the case when user press cancel button
	 * 
	 * @param event event object for the javafx
	 */

    @FXML
    private Button btnContinueRoll;

    @FXML
    private Button btnSkipMoveArmy;

    @FXML
    private Label lblAttackerArmies;

    @FXML
    private Label lblAttackerCountryName;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblDefenderArmies;

    @FXML
    private Pane moveArmiesView;

    @FXML
    private Button btnRoll;

    @FXML
    private Label lblDefenderPlayerName;

    @FXML
    private Label lblAttackerPlayerName;

    @FXML
    private Button btnMoveArmies;
    
    private static String message = "";
    
    private DiceModel diceModel;
    
    private IStrategy strategy;

    public DiceController(DiceModel diceModel, IStrategy strategy) {
		this.diceModel = diceModel;
		this.strategy = strategy;
	}

	/**
	 * This method handles the case when user press cancel button
	 * 
	 * @param event event object for the javafx
	 */
	@FXML
	void cancelDiceRoll(ActionEvent event) {
		diceModel.cancelDiceRoll();
		GameUtilities.exitWindows(btnCancelDiceRoll);
	}

	/**
	 * This method handles the case when user want to continue the attack after the
	 * first loss
	 * 
	 * @param event event object for the javafx
	 */
	@FXML
	void continueDiceRoll(ActionEvent event) {
		diceModel.setAttackerDiceValues(new ArrayList<>());
		diceModel.setDefenderDiceValues(new ArrayList<>());
		loadScreen();
		loadAndShowDice();
	}

	/**
	 * This method handles the case for the attack full on mode.
	 * 
	 * @param event event object for the javafx
	 * @throws InterruptedException This produces an interrupted exception.
	 */
	@FXML
	void attackAllOutMode(ActionEvent event) throws InterruptedException {
		CommonMapUtilities.hideControl(btnRoll, btnContinueRoll, btnAttackAllOutMode, btnCancelDiceRoll);
		Runnable task = new Runnable() {
			public void run() {
				attackAllOutMode();
			}
		};

		// Run the task in a background thread
		Thread backgroundThread = new Thread(task);
		// Terminate the running thread if the application exits
		backgroundThread.setDaemon(true);
		// Start the thread
		backgroundThread.start();

	}

	/**
	 * This method handles the case for the attack full on mode.
	 */
	private void attackAllOutMode() {
		do {
			System.out.println("Befor Click btnContinueRoll " + btnContinueRoll.isDisabled());
			// wait with thread sleep 3 seconds to allow user to see results
			try {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// check for the dice visibility
						if (!btnContinueRoll.isDisabled()) {
							btnContinueRoll.fire();
						}
						System.out.println("After clicking btnContinueRoll " + btnContinueRoll.isDisabled());
						if (chkBoxattackerDice1.isVisible()) {
							chkBoxattackerDice1.setSelected(true);
						}

						if (chkBoxattackerDice2.isVisible()) {
							chkBoxattackerDice2.setSelected(true);
						}

						if (chkBoxattackerDice3.isVisible()) {
							chkBoxattackerDice3.setSelected(true);
						}

						if (chkBoxdefenderDice1.isVisible()) {
							chkBoxdefenderDice1.setSelected(true);
						}

						if (chkBoxdefenderDice2.isVisible()) {
							chkBoxdefenderDice2.setSelected(true);
						}
						// click Roll Dice

						btnRoll.fire();
						lblStatus.setText(message);
						message = "";
					}
				});
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("After Click roll " + btnContinueRoll.isDisabled());
		} while (!btnContinueRoll.isDisabled());
		btnAttackAllOutMode.setDisable(true);

	}

	/**
	 * This method handles the case when user moves all the armies to its defeated
	 * adjacent country
	 * 
	 * @param event event object for the javafx
	 */
	@FXML
	void moveAllArmies(ActionEvent event) {
		diceModel.moveAllArmies();
		GameUtilities.exitWindows(btnMoveAllArmies);
	}

	/**
	 * This method handles the case when user moves the armies to its defeated
	 * adjacent country
	 * 
	 * @param event event object for the javafx
	 */
	@FXML
	void moveArmies(ActionEvent event) {
		String value = txtNumberOfArmiesInput.getText();
		if (StringUtils.isEmpty(value)) {
			CommonMapUtilities.alertBox("Info", "Input number of armies to move.", "Error");
			return;
		}
		int armiesToMove = Integer.valueOf(value);
		diceModel.moveArmies(armiesToMove, lblStatus, btnMoveArmies);
	}

	/**
	 * This method handles the case when dice is rolled
	 * 
	 * @param event event object for the javafx
	 */
	@FXML
	void rollDice(ActionEvent event) {

		if (!chkBoxattackerDice1.isSelected() && !chkBoxattackerDice2.isSelected()
				&& !chkBoxattackerDice3.isSelected()) {
			CommonMapUtilities.alertBox("Info", "Please Select atleast one of the attacker dice", "Message");
			return;
		} else if (!chkBoxdefenderDice1.isSelected() && !chkBoxdefenderDice2.isSelected()) {
			CommonMapUtilities.alertBox("Info", "Please Select atleast one of the defender dice", "Message");
			return;
		}
		rollAttackerDice(chkBoxattackerDice1, chkBoxattackerDice2, chkBoxattackerDice3);
		rollDefenderDice(chkBoxdefenderDice1, chkBoxdefenderDice2);

		List<String> playResult = diceModel.getPlayResultAfterRoll();

		Country attackingCountry = diceModel.getAttackingCountry();
		Country defendingCountry = diceModel.getDefendingCountry();
		if (defendingCountry.getArmy() <= 0) {
			playResult.add(attackingCountry.getPlayer().getName() + " won the country: "
					+ defendingCountry.getName() + " From " + defendingCountry.getPlayer().getName() + "\n");
			diceModel.setNumberOfCountryWon(diceModel.getNumberOfCountryWon() + 1);
			GameUtilities.enablePane(moveArmiesView);

			// attacker needs to move atleast as many army as used in attack
			CommonMapUtilities.disableControls(btnSkipMoveArmy);

			CommonMapUtilities.hideControl(btnRoll, btnContinueRoll, btnCancelDiceRoll, btnAttackAllOutMode);
		} else if (attackingCountry.getArmy() < 2) {
			playResult.add(attackingCountry.getPlayer().getName() + " lost the match");
			CommonMapUtilities.showControls(btnCancelDiceRoll);
			btnCancelDiceRoll.setDisable(false);
			CommonMapUtilities.disableControls(btnRoll, btnContinueRoll, btnAttackAllOutMode);
		} else {
			CommonMapUtilities.disableControls(btnRoll);
			CommonMapUtilities.enableControls(btnContinueRoll);
		}
		CommonMapUtilities.enableOrDisableSave(false);
		lblDefenderArmies.setText("Armies: " + String.valueOf(defendingCountry.getArmy()));
		lblAttackerArmies.setText("Armies: " + String.valueOf(attackingCountry.getArmy()));
		lblStatus.setText(playResult.toString());
		message = playResult.toString();
		System.out.println(playResult.toString());
		GameUtilities.addLogFromText(playResult.toString().replaceAll(",", "\n") + "\n");
		lblStatus.setVisible(true);
	}

	/**
	 * This method handles the case when user does not move any armies to its
	 * defeated adjacent country
	 * 
	 * @param event event object for the javafx
	 */
	@FXML
	void skipMoveArmy(ActionEvent event) {
		diceModel.skipMoveArmy();
		GameUtilities.exitWindows(btnSkipMoveArmy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("inside dice controller init");
		loadScreen();
		loadAndShowDice();
	}

	/**
	 * Load attack Screen for attacker and defender.
	 */
	public void loadScreen() {
		// Load attacker details
		Country attackingCountry = diceModel.getAttackingCountry();
		lblAttackerPlayerName.setText(attackingCountry.getPlayer().getName());
		lblAttackerCountryName.setText("Country: " + attackingCountry.getName());
		lblAttackerArmies.setText("Armies: " + String.valueOf(attackingCountry.getArmy()));

		// Load defender details
		Country defendingCountry = diceModel.getDefendingCountry();
		lblDefenderPlayerName.setText(defendingCountry.getPlayer().getName());
		lblDefenderCountryName.setText("Country: " + defendingCountry.getName());
		lblDefenderArmies.setText("Armies: " + String.valueOf(defendingCountry.getArmy()));
		lblStatus.setText(StringUtils.EMPTY);
		// clear check boxes
		GameUtilities.clearCheckBoxes(chkBoxattackerDice1, chkBoxattackerDice2, chkBoxattackerDice3, chkBoxdefenderDice1,
				chkBoxdefenderDice2);
		// Hide output details
		CommonMapUtilities.enableControls(btnRoll);
		CommonMapUtilities.disableControls(lblStatus, btnContinueRoll);
		GameUtilities.disableViewPane(moveArmiesView);
	}

	/**
	 * Show dices according to number of armies .
	 */
	public void loadAndShowDice() {

		if (diceModel.getAttackingCountry().getArmy() >= 4) {
			// if army >=4 then show all dice
			CommonMapUtilities.showControls(chkBoxattackerDice1, chkBoxattackerDice2, chkBoxattackerDice3);
		} else if (diceModel.getAttackingCountry().getArmy() >= 3) {
			// if army ==3 then show two
			CommonMapUtilities.showControls(chkBoxattackerDice1, chkBoxattackerDice2);
			CommonMapUtilities.hideControl(chkBoxattackerDice3);
		} else if (diceModel.getAttackingCountry().getArmy() >= 2) {
			// else only one dice
			CommonMapUtilities.showControls(chkBoxattackerDice1);
			CommonMapUtilities.hideControl(chkBoxattackerDice2, chkBoxattackerDice3);
		}

		if (diceModel.getDefendingCountry().getArmy() > 2) {
			// if >2 means 3 then show 3 dice
			CommonMapUtilities.showControls(chkBoxdefenderDice1, chkBoxdefenderDice2);
		} else if (diceModel.getDefendingCountry().getArmy() >= 1) {
			CommonMapUtilities.showControls(chkBoxdefenderDice1);
			CommonMapUtilities.hideControl(chkBoxdefenderDice2);
		}
		
		if (!(diceModel.getDefendingCountry().getPlayer().getStrategy() instanceof Human)) {
			if(chkBoxdefenderDice1.isVisible()) {
				chkBoxdefenderDice1.setSelected(true);
			}
			if(chkBoxdefenderDice2.isVisible()) {
				chkBoxdefenderDice2.setSelected(true);
			}
		}

	}

	/**
	 * Roll Dice of Attacker
	 * 
	 * @param dices check Box... dices (Varargs)
	 */
	public void rollAttackerDice(CheckBox... dices) {
		DiceModel.numberOfDiceUsedByAttacker = 0;
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				DiceModel.numberOfDiceUsedByAttacker++;
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getAttackerDiceValues().add(value);
			}
		}
	}

	/**
	 * Roll dice of oponent
	 * 
	 * @param dices check Box... dices (Varargs)
	 */
	public void rollDefenderDice(CheckBox... dices) {
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getDefenderDiceValues().add(value);
			}
		}
	}

	/**
	 * This method is responsible for loading dice controller for the specified
	 * strategy.
	 */
	public void loadDiceControllerForStrategy() {

		btnAttackAllOutMode = new Button();
		btnCancelDiceRoll = new Button();
		btnContinueRoll = new Button();
		btnMoveAllArmies = new Button();
		btnMoveArmies = new Button();
		btnRoll = new Button();
		btnSkipMoveArmy = new Button();
		lblAttackerArmies = new Label();
		lblAttackerPlayerName = new Label();
		lblAttackerCountryName = new Label();
		lblDefenderArmies = new Label();
		lblDefenderPlayerName = new Label();
		lblDefenderCountryName = new Label();
		lblNoOfArmies = new Label();
		lblStatus = new Label();
		chkBoxattackerDice1 = new CheckBox();
		chkBoxattackerDice2 = new CheckBox();
		chkBoxattackerDice3 = new CheckBox();
		chkBoxdefenderDice1 = new CheckBox();
		chkBoxdefenderDice2 = new CheckBox();
		txtNumberOfArmiesInput = new TextField();
		moveArmiesView = new Pane();

		loadScreen();
		loadAndShowDice();

		int count = attackFullOnForStrategy();
//		System.out.println(moveArmiesView.isVisible() + "Anna");
//		System.out.println(btnCancelDiceRoll.isVisible() + "Anna");
		if (moveArmiesView.isVisible()) {
			diceModel.moveAllArmies();
		} else {
			diceModel.cancelDiceRoll();
		}
	}

	/**
	 * This method is used for a full-on attack for the strategy.
	 * 
	 * @return Returns the count of attacks.
	 */
	public int attackFullOnForStrategy() {
		int count = 0;
		do {
			System.out.println("Befor Click btnContinueRoll " + btnContinueRoll.isDisabled());
			count = 0;
			// wait with thread sleep 3 seconds to allow user to see results
			try {
				// check for the dice visibility
				if (!btnContinueRoll.isDisabled()) {
					continueDiceRoll(null);
				}
				System.out.println("After clicking btnContinueRoll " + btnContinueRoll.isDisabled());
				if (chkBoxattackerDice1.isVisible()) {
					chkBoxattackerDice1.setSelected(true);
					count++;
				}

				if (chkBoxattackerDice2.isVisible()) {
					chkBoxattackerDice2.setSelected(true);
					count++;
				}

				if (chkBoxattackerDice3.isVisible()) {
					chkBoxattackerDice3.setSelected(true);
					count++;
				}

				if (chkBoxdefenderDice1.isVisible()) {
					chkBoxdefenderDice1.setSelected(true);
				}

				if (chkBoxdefenderDice2.isVisible()) {
					chkBoxdefenderDice2.setSelected(true);
				}
				// click Roll Dice

				rollDice(null);
				lblStatus.setText(message);
				message = "";
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("After Click roll " + btnContinueRoll.isDisabled());
		} while (!btnContinueRoll.isDisabled());
		btnAttackAllOutMode.setDisable(true);
		return count;
	}

}