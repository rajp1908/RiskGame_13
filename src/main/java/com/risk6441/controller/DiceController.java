package com.risk6441.controller;

import javafx.fxml.FXML;
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

public class PleaseProvideControllerClassName {

    @FXML
    private Label lblDefenderTerritoryName;

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
    private Label lblAttackerTerritoryName;

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

    @FXML
    void rollDice(ActionEvent event) {

    }
    
    /**
	 * This method handles the case when user does not move any armies to its
	 * defeated adjacent territory
	 * 
	 * @param event event object for the javafx
	 */

    @FXML
    void cancelDiceRoll(ActionEvent event) {

    }
    
    /**
	 * This method handles the case when user want to continue the attack after the
	 * first loss
	 * 
	 * @param event event object for the javafx
	 */

    @FXML
    void moveArmies(ActionEvent event) {

    }
    
    /**
	 * This method handles the case when dice is rolled
	 * 
	 * @param event event object for the javafx
	 */

    @FXML
    void skipMoveArmy(ActionEvent event) {

    }
    
    /*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */

    @FXML
    void moveAllArmies(ActionEvent event) {

    }
    
    /**
	 * This method handles the case when user moves the armies to its defeated
	 * adjacent territory
	 * 
	 * @param event event object for the javafx
	 */

    @FXML
    void continueDiceRoll(ActionEvent event) {

    }
    
    /**
	 * This method handles the case for the attack full on mode.
	 * 
	 * @param event event object for the javafx
	 * @throws InterruptedException This produces an interrupted exception.
	 */

    @FXML
    void attackAllOutMode(ActionEvent event) {

    }
    
    /**
	 * This method handles the case when user moves all the armies to its defeated
	 * adjacent territory
	 * 
	 * @param event event object for the javafx
	 */

}
