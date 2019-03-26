package com.risk6441.controller;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import com.risk6441.configuration.PlayerStrategy;
import com.risk6441.entity.Player;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.strategy.Human;
import com.risk6441.strategy.IStrategy;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * This class controller allows to choose a player Strategies for selected number of player which are listed below
 * <ul>
 * <li>HUMAN</li>
 * <li>COMPUTER</li>
 * </ul>
 * @author Deep
 * @author Jemish
 * @author Dolly
 */
public class PlayerStrategyChooserController extends Observable implements Initializable{
	
	private List<Player> playerList = null;
	
	public PlayerStrategyChooserController(List<Player> playerList) {
		this.playerList = playerList;
	}
	
	
	@FXML
    private VBox vBox;

    @FXML
    private Button btnSubmit;

    /**
     * This method is used for submitting the strategy.
     * @param event Button click event for choosing strategy.
     */
    @FXML
    void submit(ActionEvent event) {
    	ObservableList<Node> hBoxes = vBox.getChildren();
    	int count = 0;
    	for (Node n : hBoxes) {
			HBox hBox = (HBox) n;
			ObservableList<Node> node = hBox.getChildren();
			
			
			if (node.get(1) instanceof ChoiceBox<?>) {
				PlayerStrategy strategyType = (PlayerStrategy) ((ChoiceBox<?>) node.get(1)).getSelectionModel().getSelectedItem();
				playerList.get(count).setPlayerStrategy(strategyType);
				Human strategy = getStrategyObjectForThePlayer(strategyType.toString());
				playerList.get(count).setStrategy(strategy);
				count++;
			}
		}
    	
    	if(count == playerList.size()) {
    		String msg = "";
    		for(Player p : playerList) {
    			msg += p.getName()+" is a playing with strategy : "+p.getPlayerStrategy()+"\n";
    		}
    		GameUtilities.addLogFromText(msg);
    		
    		GameUtilities.exitWindows(btnSubmit);
    		setChanged();
			notifyObservers("playerStrategyChoosen"); 	
    	}
    }

	/**
	 * This method is used for getting the strategy for the player.
	 * Applied Encapsulate Downcast refactoring 
	 * @param str string defining type of strategy for the player
	 * @return strategy object
	 */
	private Human getStrategyObjectForThePlayer(String str) {
		Human strategy = null;
		System.out.println("string selection"+str.isEmpty());
		if (str.equals("HUMAN")) {
			strategy = new Human();
		}
		return strategy;
	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBox.setSpacing(25);

		for (int i = 0; i < playerList.size(); i++) {
			HBox getBox = getElementsWindow(playerList.get(i));
			vBox.getChildren().addAll(getBox);
		}
	}
	
	/**
	 * This method is used for getting the strategies from the user.
	 * @param player The current player.
	 * @return horizontal box for the elements. 
	 */
	public HBox getElementsWindow(Player player) {
		ChoiceBox<PlayerStrategy> playerStrategyType = new ChoiceBox<>();
		playerStrategyType.getItems().addAll(PlayerStrategy.values());
		playerStrategyType.getSelectionModel().select(1);;

		HBox hBox = new HBox();
		hBox.setSpacing(25);

		Label playerID = new Label();
		playerID.setText(String.valueOf(player.getId()));

		hBox.getChildren().addAll(playerID, playerStrategyType);
		return hBox;
	}
}
