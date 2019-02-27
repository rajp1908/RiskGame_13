/**
 * Sample Skeleton for 'gameplay.fxml' Controller Class
 */
package com.risk6441.controller;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
/**
 * This class controls the behavior of main game and allow users to perform action
 * <ul>
 * <li>  Choose Number of Players  </li>
 * <li>  Place Army </li>
 * <li>  All Phase including Reinforcements, Fortify and Attack </li>
 * <li>  World Domination by Player </li>
 * <li>  Choose Cards </li>
 * </ul>
 * @author Jemish
 * @author Dolly
 * @author Deep
 */
public class PlayGameController {

    @FXML // fx:id="btnReinforcement"
    private Button btnReinforcement; // Value injected by FXMLLoader

    @FXML // fx:id="btnFortify"
    private Button btnFortify; // Value injected by FXMLLoader

    @FXML // fx:id="btnEndTurn"
    private Button btnEndTurn; // Value injected by FXMLLoader

    @FXML // fx:id="terrList"
    private ListView<?> terrList; // Value injected by FXMLLoader

    @FXML // fx:id="adjTerrList"
    private ListView<?> adjTerrList; // Value injected by FXMLLoader

    @FXML // fx:id="btnCards"
    private Button btnCards; // Value injected by FXMLLoader

    @FXML // fx:id="btnNoMoreAttack"
    private Button btnNoMoreAttack; // Value injected by FXMLLoader

    @FXML // fx:id="txtAreaMsg"
    private TextArea txtAreaMsg; // Value injected by FXMLLoader

    @FXML // fx:id="worldDominationPieChart"
    private PieChart worldDominationPieChart; // Value injected by FXMLLoader

    @FXML // fx:id="militaryDominationbarChart"
    private BarChart<?, ?> militaryDominationbarChart; // Value injected by FXMLLoader

    @FXML // fx:id="lblCurrPlayer"
    private Label lblCurrPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="vbox"
    private VBox vbox; // Value injected by FXMLLoader

    @FXML // fx:id="choiceBoxNoOfPlayer"
    private ChoiceBox<?> choiceBoxNoOfPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnPlaceArmy"
    private Button btnPlaceArmy; // Value injected by FXMLLoader

    @FXML // fx:id="lblGamePhase"
    private Label lblGamePhase; // Value injected by FXMLLoader

	/**
	 * This method ends the turn of particular player using Scheduled Executor class
	 * 
	 * @param event button event will be passed as a parameter
	 */
    @FXML
    void endTrun(ActionEvent event) {

    }
	
	/**
	 * This method will be called by user to start the fortification phase
	 * 
	 * @param event button click event will be passes as parameter
	 */
    @FXML
    void fortify(ActionEvent event) {

    }
	
	/**
	 * This method is responsible for ending attack phase and providing
	 * the notification.
	 * 
	 * @param event event button click event will be passed as a parameter
	 */
    @FXML
    void noMoreAttack(ActionEvent event) {

    }
	
	/**
	 * This method will open the card pane
	 * 
	 * @param event button click event will be passes as parameter
	 */
    @FXML
    void openCardPane(ActionEvent event) {

    }
	
	/**
	 * This method will allow the players to place the armies one by one in round
	 * robin fashion
	 * 
	 * @param event Button triggered event will be passed as parameter
	 */
    @FXML
    void placeArmy(ActionEvent event) {

    }
	
	/**
	 * This method will allow the user to place the armies after the fortification
	 * phase is completed
	 * 
	 * @param event button click event will be passed as parameter
	 */
    @FXML
    void reinforce(ActionEvent event) {

    }

    

}
