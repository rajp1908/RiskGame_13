package com.risk6441.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * This class is a controller for the MapEditor layout.
 * @author Jemish
 * @author Dolly
 * @author Deep
 * @author Hardik
 * @author Raj
 *
 */
public class MapRedactorController {

    @FXML
    private Label lblAuthor1;

    @FXML
    private Label lblWarn;

    @FXML
    private TextField txtWarn;

    @FXML
    private Label lblAuthor;

    @FXML
    private Label lblImage;

    @FXML
    private Label lblScroll;

    @FXML
    private Label lblWrap;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtImage;

    @FXML
    private TextField txtScroll;

    @FXML
    private TextField txtWrap;

    @FXML
    private TextField txtXCo;

    @FXML
    private TextField txtYCo;

    @FXML
    private Label lblTerrname;

    @FXML
    private Label lblAdjTerr;

    @FXML
    private ComboBox<?> comboAdjTerr;

    @FXML
    private Label tctAuthorLabel1211;

    @FXML
    private Label tctAuthorLabel121;

    @FXML
    private Button btnDelTerr;

    @FXML
    private Button btnAddTerr;

    @FXML
    private TextField txtTerrName;

    @FXML
    private Button btnUpdateTerr;

    @FXML
    private Label lblSelectedCont;

    @FXML
    private ListView<?> contList;

    @FXML
    private ListView<?> terrList;

    @FXML
    private ListView<?> adjTerrList;

    @FXML
    private Label lblContList;

    @FXML
    private Label lblTerrList;

    @FXML
    private Button btnDltAdjTerr;

    @FXML
    private TextArea txtAreaMsg;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnExit;

    @FXML
    private TextField txtContName;

    @FXML
    private Label tctAuthorLabel1;

    @FXML
    private TextField txtContControlVal;

    @FXML
    private Button btnAddCont;

    @FXML
    private Button btnDelCont;

    @FXML
    private Button btnUpdateCont;

    @FXML
    private Label tctAuthorLabel2;

    @FXML
    private Label labelContDetail;

    /**
	 * This method adds the continent to the map
	 * @param event event object containing details regarding origin of the event
	 */
	@FXML
    void addContinent(ActionEvent event) {

    }
	
	/**
	* This method adds a territory to the continent.
	* @param event When the user clicks the add territory button, this event is triggered and passed to the method.
	*/
    @FXML
    void addTerritiory(ActionEvent event) {

    }

	/**
	* This method deletes adjacent territories
	* @param event When the user clicks the delete adjacent button, this event is triggered and passed to the method.
	*/
	@FXML
    void deleteAdjTerritory(ActionEvent event) {

    }
	
	/**
	* This method deletes continent from the map.
	* @param event When the user clicks the delete continent button, this event is triggered and passed to the method.
	*/
    @FXML
    void deleteContinent(ActionEvent event) {

    }
	
	/**
	* This method deletes territory from the continent.
	* @param event When the user clicks the delete territory button, this event is triggered and passed to the method.
	*/
    @FXML
    void deleteTerritiory(ActionEvent event) {

    }
	
	/**
	* This method exits the program when the button is clicked.
	* @param event When the user clicks the exit button, this event is triggered and passed to the method.
	*/
    @FXML
    void exitBtnClick(ActionEvent event) {

    }

	/**
	* This method validates the map. If it is valid then save it else show error.
	* @param event When the user clicks the save map button, this event is triggered and passed to the method.
	*/
    @FXML
    void saveMap(ActionEvent event) {

    }

	/**
	* This method updates continent details.
	* @param event When the user clicks the update continent button, this event is triggered and passed to the method.
	* @throws InvalidMapException InvalidMapException if any error occurs
	*/
    @FXML
    void updateContinent(ActionEvent event) {

    }
	
	/**
	* This method updates territories.
	* @param event When the user clicks the update territory button, this event is triggered and passed to the method.
	* @throws InvalidMapException InvalidMapException if any error occurs
	*/
    @FXML
    void updateTerritiory(ActionEvent event) {

    }

}
