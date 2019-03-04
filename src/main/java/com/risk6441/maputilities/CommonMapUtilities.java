package com.risk6441.maputilities;

import java.io.File;
import java.util.Optional;
import java.util.Random;

import com.risk6441.configuration.Configuration;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Country;
import com.risk6441.gameutilities.GameUtilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * This class provide methods which are required in Play Game and Edit Map.
 * @author Raj
 * @author Jemish
 */
public class CommonMapUtilities {
	
	public static Button btnSave = null;

	/**
	 * This method is used to enable controls
	 * @param controls It is the object of javafx.scene.control.Control {@link javafx.scene.control.Control}
	 */
	public static void enableControls(Control... controls) {
		for(Control c : controls)
			c.setDisable(false);
	}
	
	/**
	 * This method is used to disable controls
	 * @param controls It is the object of javafx.scene.control.Control {@link javafx.scene.control.Control}
	 */
	public static void disableControls(Control... controls) {
		for(Control c : controls)
			c.setDisable(true);
	}
	
	/**
	 * This method is used to choose a map file.
	 * @return file of type {@link File}
	 */
	public static File openMapFile() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extensions = new FileChooser.ExtensionFilter("Map Files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extensions);
		file = fileChooser.showOpenDialog(null);
		return file;
	}
	
	/**
	 * This method is used to save map files.
	 * @return file of type {@link File}
	 */
	public static File saveMapFile() {
		FileChooser fileChooser = new FileChooser();
		File file = null;
		FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Map Files (*.map)","*.map");
		fileChooser.getExtensionFilters().add(extension);
		file = fileChooser.showSaveDialog(null);
		return file;
	}
	
	/**
	 * This method generates random number from 0 to number.
	 * @param number number up to which find random numbers to be generated, from 0 to number
	 * @return random number from 0 to number, including number
	 */
	public static int getRandomNumber(int number) {
		return new Random().nextInt(number+1);
	}
	
	/**
	 * This method generates random number from 1 to number.
	 * @param number number till which you want to find random number
	 * @return random number from 1 to number
	 */
	public static int getRandomNumberFromOne(int number) {
		return new Random().nextInt(number) + 1;
	}
	
	/**
	 * This method opens a text box allowing user to add number armies in reinforcement phase.
	 * @return Integer number added by user 			
	 */
	public static int inputForReinforcement() {
		TextInputDialog input = new TextInputDialog();
		input.setTitle("Reinforcement");
		input.setHeaderText("Enter number of armies to reinforce:");
		Optional<String> result = input.showAndWait();
		if(result.isPresent())
			return Integer.parseInt(result.get());
		return 0;		
	}
	
	/**
	 * This method opens a text box allowing user to add number armies in fortify phase.
	 * @return Integer number added by user 			
	 */
	public static int inputForFortification() {
		TextInputDialog input = new TextInputDialog();
		input.setTitle("Fortification");
		input.setHeaderText("Enter number of armies to fortify (1 less then current armies of the countries)");
		Optional<String> result = input.showAndWait();
		if(result.isPresent())
			return Integer.parseInt(result.get());
		return 0;
	}
	
	/**
	 * This method is used to write message in text area
	 * @param text This is object of TextArea {@link javafx.scene.control.TextArea}
	 * @param message This the value to be shown as message.
	 */
	public static void printMessage(TextArea text,String message) {
		text.setText(message);
	}
	
	/**
	 * This method is used to clear TextField
	 * @param textField
	 */
	public static void clearTextArea(TextField... textField) {
		for(TextField txt : textField)
			txt.clear();
	}
	
	/**
	 * This method is used to show controls
	 * @param controls It is the object of javafx.scene.control.Control {@link javafx.scene.control.Control}
	 */
	public static void showControls(Control... controls) {
		for(Control c : controls)
			c.setVisible(true);
	}
	
	/**
	 * This method is used to hide controls
	 * @param controls It is the object of javafx.scene.control.Control {@link javafx.scene.control.Control}
	 */
	public static void hideControl(Control...controls) {
		for(Control control:controls)
			control.setVisible(false);
	}
	
	/**
	 * This method shows the alert pop-up.
	 * @param title title of the message.
	 * @param message message content.
	 * @param header header of the stage.
	 */
	public static void alertBox(String title, String message,  String header) {
		if(GameUtilities.isTestMode) {
			return;
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		if(!Configuration.isAllComputerPlayer)
			alert.showAndWait();
		else if(Configuration.isPopUpShownInAutoMode)
			alert.show();
		
	}
	
	/**
	 * This method loads up the countries of a particular continent and shows them in a pane with number of armies on the country.
	 * @param continent object of the continent
	 * @return the pane with title for the UI of the game play.
	 */
	public static TitledPane getNewPaneForVerticalBox(Continent continent) {
		VBox vbox = new VBox();
		for (Country country : continent.getCountries()) {
			Label label1 = new Label();
			if (country.getPlayer() != null) {
				label1.setText(
						country.getName() + ":-" + country.getArmy() + "-" + country.getPlayer().getName());
			} else {
				label1.setText(country.getName() + ":-" + country.getArmy());
			}
			vbox.getChildren().add(label1);
		}
		TitledPane pane = new TitledPane(continent.getName()+" - "+continent.getValue(), vbox);

		return pane;
	}

	/**
	 * @param bool true for enabling the button; false for disabling button
	 */
	public static void enableOrDisableSave(boolean bool) {
		if(!Configuration.isAllComputerPlayer)
			btnSave.setDisable(!bool);
	}
}
