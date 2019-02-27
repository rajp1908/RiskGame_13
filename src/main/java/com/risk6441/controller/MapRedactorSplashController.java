package com.risk6441.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * This class controls behavior of MapEditor Splash Screen.
 * <ul>
 * <li> Edit Map </li>
 * <li> Load Map </li>
 * <li> Exit </li>
 * </ul>
 * @author Raj
 * @author Hardik 
 *
 */

public class MapRedactorSplashController {

    @FXML
    private Button btnEditMap;

    @FXML
    private Button btnCreateMap;

    @FXML
    private Button btnExit;
    
    /**
     * The method gets executed when user clicks on create map button from the map splash screen.
     * It opens the map editor with blank map. The user is allowed to create new map afterwards.
     * @param event Creates button for create map.
     */
    @FXML
    void btnCreateMap(ActionEvent event) {

    }
    
    /**
     * The method gets executed when user clicks on edit map button from the map splash screen.
     * It opens the file chooser to allow user to choose a file. The user is allowed to edit a map afterwards. 
     * @param event  Creates button for edit map.  
     * @throws IOException Throws IOException if there is an issue while reading a map file.
     */

    @FXML
    void btnEditMap(ActionEvent event) {

    }
    
     /**
     * This method is executed when user clicks on exit button. It closes the application.
     * @param event  Creates button for exit.
     */

    @FXML
    void btnExit(ActionEvent event) {

    }

}
