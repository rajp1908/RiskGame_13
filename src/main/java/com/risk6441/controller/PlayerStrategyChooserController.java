package com.risk6441.controller;
import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


/**
 * This class controller allows to choose a player Strategies for selected number of player which are listed below
 * <ul>
 * <li>HUMAN</li>
 * <li>AGGRESSIVE</li>
 * <li>BENEVOLENT</li>
 * <li>RANDOM</li>
 * <li>CHEATER</li>
 * </ul>
 * @author Deep
 * @author Jemish
 * @author Dolly
 */
public class PlayerStrategyChooserController {

    @FXML
    private VBox vBox;

    @FXML
    private Button btnSubmit;

    
    /**
     * This method is used for submit the strategy for player.
     * @param event Action event button for choosing strategy for player.
     */
    @FXML
    void submit(ActionEvent event) {

    }

}
