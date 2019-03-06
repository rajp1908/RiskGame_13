package com.risk6441.controller;

import java.io.Externalizable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Map.Entry;

import com.risk6441.configuration.Configuration;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Country;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.exception.InvalidMap;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.models.GameUIStateModel;
import com.risk6441.models.PlayerModel;
import com.risk6441.models.WorldDominationModel;
import com.risk6441.strategy.Human;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
public class PlayGameController extends Observable implements Initializable, Observer, Externalizable{

	/**
	 * The map object {@link Map}
	 * 
	 */
	private Map map;

	
	private Player playerLost = null;

	private int noOfPlayer;

	public List<Player> playerList;

	private Player currentPlayer;

	private PlayerModel playerModel;

	private Iterator<Player> playerListIterator;
	
	@FXML
    private ListView<Country> counList;

    @FXML
    private VBox vbox;

    @FXML
    private ListView<Country> adjCounList;

    @FXML
    private Button btnPlaceArmy;

    @FXML
    private ChoiceBox<Integer> choiceBoxNoOfPlayer;

    @FXML
    private Button btnReinforcement;

    @FXML
    private Label lblGamePhase;

//    @FXML
//    private Button btnNoMoreAttack;

    @FXML
    private Button btnFortify;

    @FXML
    private Button btnEndTurn;

    @FXML
    private BarChart<String, Number> armyDominationChart;

    @FXML
    private TextArea txtAreaMsg;

    @FXML
    private Label lblCurrPlayer;
    
    private String txtMsgAreaTxt;

	private String phaseOfTheGame;

	private String lblPlayerString;

	public GameUIStateModel state;

//	private int attackCount = 5;

	private int numOfTurnDone;

	private int maxNumOfTurn;
	
	private boolean isGameOver = false;

	private int maxNumOfEachPlayerTurn;

	private boolean oneTime = true;


	/**
	 * This method returns the player list.
	 * returns the playerList
	 * @return playerList he playerList the list of players
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	private int gameNo = 1;


	/**
	 * This method ends the turn of particular player using Scheduled Executor class
	 * 
	 * @param event button event will be passed as a parameter
	 */
	@FXML
	void endTrun(ActionEvent event) {
		GameUtilities.addLogFromText(currentPlayer.getName() + " ended his turn.\n");
	/*	if (playerModel.getNumOfCountryWon() > 0) {
			allocateCardToPlayer();
		}*/
		playerModel.endTurn();
	}

	

	/**
	 * This method will be called by user to start the fortification phase
	 * 
	 * @param event button click event will be passes as parameter
	 */
	@FXML
	void fortify(ActionEvent event) {
		fortifyArmy();
	}

	/**
	 * This method will allow the players to place the armies one by one in round
	 * robin fashion
	 * 
	 * @param event Button triggered event will be passed as parameter
	 */
	@FXML
	void placeArmy(ActionEvent event) {
		playerModel.placeArmies(counList, playerList, txtAreaMsg);
	}

	/**
	 * This method sets the label for the current phase.
	 * 
	 * @param phase phase name
	 */
	public void setPhase(String phase) {
		lblGamePhase.setText(phase);
	}

	/**
	 * This method returns the map object
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Initialize components for placing army.
	 */
	private void initializePlaceArmy() {
		loadCurrentPlayer(false);
		updateMap();
		counList.refresh();
		
		if (!(currentPlayer.getStrategy() instanceof Human)) {
			placeArmy(null);
		}
	}

	/**
	 * This method will allow the user to place the armies after the fortification
	 * phase is completed
	 * 
	 * @param event button click event will be passed as parameter
	 */
	@FXML
	void reinforce(ActionEvent event) {
		Country country = counList.getSelectionModel().getSelectedItem();
//			if (currentPlayer.getStrategy() instanceof Human) {
//	//			CommonMapUtilities.alertBox("Info", "You Risk Cards >= 5, please exchange these cards for the army.",
//	//					"Info");
//	//			return;
//			} else {
//
//			}
		
		reinforceArmy(country);
	}

	/**
	 * This method is used for reinforcement of army.
	 * @param country The selected country where the reinforcement is to be done.
	 */
	private void reinforceArmy(Country country) {
		playerModel.reinforcementPhase(country, counList.getItems(), txtAreaMsg);
		setCurrentPlayerLabel(currentPlayer.getName() + ":- " + currentPlayer.getArmies() + "armies left.");
		updateMap();
		showMilitaryDominationData();
		counList.refresh();
		adjCounList.refresh();
		System.out.println("reinforce army phase is successful....");
	}

	/**
	 * This method is responsible for ending attack phase and providing
	 * the notification.
	 * 
	 * @param event event button click event will be passed as a parameter
	 */
//	@FXML
//	void noMoreAttack(ActionEvent event) {
//		System.out.println("Inside pgc noMoreAttack");
//		refreshList();
//		if (playerList.size() <= 1) {
//			disableGameControls();
//			return;
//		}
////		GameUtilities.addLogFromText("**** Attack phase ended! ****\n");
//		/*if (playerModel.getNumOfCountryWon() > 0) {
//			allocateCardToPlayer();
//		}*/
//		CommonMapUtilities.enableControls(btnEndTurn);
//
//		isValidFortificationPhase();
//	}


	/**
	 * check if there is a valid fortification phase.
	 */
	private void isValidFortificationPhase() {
		playerModel.isFortificationPhasePossible(map, currentPlayer);
	}

	/**
	 * This is a constructor to initialize the Map object.
	 * 
	 * @param map Current map object.
	 */
	public PlayGameController(Map map) {
		this.map = map;
		this.playerModel = new PlayerModel();
		playerModel.addObserver(this);
		playerList = new ArrayList<Player>();
	}

	/**
	 * The default constructor.
	 */
	public PlayGameController() {

	}

	/**
	 * This method sets the label text of player to current player
	 * 
	 * @param str Contains the current player name.
	 */
	public void setCurrentPlayerLabel(String str) {
		lblCurrPlayer.setText("Playing ------ : " + str);
	}

	/**
	 * Loads the current player and clears the selected and adjacent country list
	 * 
	 * @param isLoadingFromFirstPlayer true if we are starting from player 0
	 * @return The current player
	 */
	public Player loadCurrentPlayer(boolean isLoadingFromFirstPlayer) {
		if (playerLost != null) {
			playerListIterator = playerList.iterator();
			while (playerListIterator.hasNext()) {
				Player temp = playerListIterator.next();
				if (temp.equals(currentPlayer)) {
					currentPlayer = temp;
					break;
				}
			}
			playerLost = null;
		}

		if (!playerListIterator.hasNext() || isLoadingFromFirstPlayer) {
			playerListIterator = playerList.iterator();
		}
		currentPlayer = playerListIterator.next();
		if(!(currentPlayer.getStrategy() instanceof Human)) {
//			CommonMapUtilities.enableOrDisableSave(false);
		}
		playerModel.setPlayerList(playerList);
		playerModel.setCurrentPlayer(currentPlayer);
		GameUtilities.addLogFromText("********************************************************** \n");
		GameUtilities.addLogFromText(currentPlayer.getName() + "!------.started playing.\n");
		refreshList();
		setCurrentPlayerLabel(currentPlayer.getName() + " => " + currentPlayer.getArmies() + " armies left.\n");
		return currentPlayer;
	}

	/**
	 * This method checks whether the player has armies or not for placement in
	 * countries.
	 * 
	 * @return Returns true or false (boolean) after testing condition.
	 */
	public boolean checkPlayerWithNoArmyWhilePlacingArmy() {
		if (currentPlayer.getArmies() == 0) {
			GameUtilities.addLogFromText(gameNo+" Skipped " + currentPlayer.getName() + " It doesn't have army for placing.\n");
			loadCurrentPlayer(false);
			return true;
		}
		return false;
	}



	/**
	 * This method allocates countries to the player and start the game.
	 * 
	 * @throws InvalidMap Throws IOException if there is an issue while
	 *                             loading the map.
	 */
	private void allocateCountriesToPlayer() throws InvalidMap {
		GameUtilities.addLogFromText("*******************************************************\n                 Assigning countries   \n*******************************************************\n");
		GameUtilities.allocateCountryToPlayer(map, playerList, txtAreaMsg);
		GameUtilities.addLogFromText("*******************************************************\n               Countries assignation complete  \n*******************************************************\n");
		updateMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	public void initialize(URL location, ResourceBundle resources) {
	
		Configuration.waitBeweenTurn = 3000;
		choiceBoxNoOfPlayer.getItems().addAll(2, 3, 4, 5, 6);
		Configuration.isAllComputerPlayer = true;
		lblGamePhase.setText("Phase =>  Start  !");
		updateMap();
		GameUtilities.txtMsgArea = txtAreaMsg;
		listenerForNumberOfPlayer();
		
		CommonMapUtilities.disableControls(btnEndTurn, btnFortify, btnPlaceArmy, btnReinforcement);

		counList.setCellFactory(param -> new ListCell<Country>() {
			@Override
			protected void updateItem(Country item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + " => " + item.getArmy() + " = " + item.getPlayer().getName());
				}
			}
		});
		
		adjCounList.setCellFactory(param -> new ListCell<Country>() {
			@Override
			protected void updateItem(Country item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + " => " + item.getArmy() + " = " + item.getPlayer().getName());
				}
			}
		});
		
		counList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("event invoked");
				Country coun = counList.getSelectionModel().getSelectedItem();
				showAdjCountryOfCounInList(coun);
			}
		});

		
	}

	/**
	 * This method listens for the selection of number of player in chosen box
	 */
	private void listenerForNumberOfPlayer() {
		choiceBoxNoOfPlayer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer old, Integer newV) {
				noOfPlayer = choiceBoxNoOfPlayer.getSelectionModel().getSelectedItem();
				playerList = PlayerModel.createPlayers(noOfPlayer, txtAreaMsg);
				GameUtilities.addLogFromText("*******************************************************\n         Players creation complete \n*******************************************************\n");
				showPlayerStrategyChooserPane();

			}
		});
	}

	/**
	 * This method opens the pane to allow user to select strategy for the players.
	 */
	private void showPlayerStrategyChooserPane() {
		final Stage stage = new Stage();
		stage.setTitle("Player Strategy Chooser");
		PlayerStrategyChooserController controller = new PlayerStrategyChooserController(playerList);
		controller.addObserver(this);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("playerStrategyChooser.fxml"));
		loader.setController(controller);

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This method loads the game panel after loading the saved game.
	 */
	private void loadMapDataAfterLoadingSavedGame() {
		CommonMapUtilities.disableControls(choiceBoxNoOfPlayer);
		lblCurrPlayer.setText(lblPlayerString);
		lblGamePhase.setText(phaseOfTheGame);
		GameUtilities.addLogFromText(txtMsgAreaTxt);
		showMilitaryDominationData();

		counList.getItems().addAll(FXCollections.observableArrayList(currentPlayer.getAssignedCountry()));
		
		if (state.isPlaceArmyEnable)
			CommonMapUtilities.enableControls(btnPlaceArmy);
		
		if (state.choiceBoxNoOfPlayer)
			CommonMapUtilities.enableControls(choiceBoxNoOfPlayer);

		if (state.isReinforcemetnEnable)
			CommonMapUtilities.enableControls(btnReinforcement);

//		if (state.isNoMoreAttackEnable)
//			CommonMapUtilities.enableControls(btnNoMoreAttack);

		if (state.isFortificationEnable)
			CommonMapUtilities.enableControls(btnFortify);

		if (state.isEndTurnEnable)
			CommonMapUtilities.enableControls(btnEndTurn);

		playerListIterator = playerList.iterator();
		System.out.println(playerList);

		playerListIterator = playerList.iterator();


		
		int count = 0;
		System.out.println(count);
		while (playerListIterator.hasNext()) {
			if (playerListIterator.next().equals(currentPlayer)) {
				System.out.println(count);
				break;
			}
		}
	}

	/**
	 * Show adjacent countries of the particular country
	 * 
	 * @param coun country object
	 */
	public void showAdjCountryOfCounInList(Country coun) {
		System.out.println("showAdjCountryOfCounInList");
		System.out.println(lblGamePhase.getText());
		if (lblGamePhase.getText().contains("Fortification")) {
			System.out.println("Rendering ");
			List<Country> reachableCounList = new ArrayList<Country>();
			List<Country> allCoun = GameUtilities.getCountryList(map);

			this.bfsCountry(coun, reachableCounList);

			for (Country t : allCoun) {
				t.setProcessed(false);
			}
			for(Country c : reachableCounList) {
				System.out.println("reachable : "+c.getName());
			}
			adjCounList.getItems().clear();
			adjCounList.getItems().addAll(reachableCounList);

		} else {
			adjCounList.getItems().clear();
			for (Country t : coun.getAdjacentCountries()) {
				adjCounList.getItems().add(t);
			}
		}

	}

	/**
	 * This method traverses the countries of the map using BFS algorithm.
	 * @param country The selected country.
	 * @param reachableCounList All the reachable countries from the country.
	 */
	public void bfsCountry(Country country, List<Country> reachableCounList) {

		if (country.isProcessed() == true) {
			return;
		}
		System.out.println(country.getName());
		country.setProcessed(true);
		System.out.println("adding to list:"+country.equals(counList.getSelectionModel().getSelectedItem()));
		if (!country.equals(counList.getSelectionModel().getSelectedItem())) {
			System.out.println("if con");
			reachableCounList.add(country);
		}
		for (Country t : country.getAdjacentCountries()) {
			System.out.println(t.getName());
			System.out.println(t.isProcessed() + "Player:"+t.getPlayer().equals(this.currentPlayer));
			if (t.isProcessed() == false) {
				System.out.println("andar");
				bfsCountry(t, reachableCounList);
			}
		}
	}

	/**
	 * Updates the map to show latest data.
	 */
	public void updateMap() {

		vbox.getChildren().clear();
		for (Continent c : map.getContinents()) {
			vbox.autosize();
			vbox.getChildren().add(CommonMapUtilities.getNewPaneForVerticalBox(c));
		}
	}
	/**
	 * This method initializes the components for the fortification phase.
	 */
	private void initializeFortification() {
		refreshList();
	/*	if (playerModel.getNumOfCountryWon() > 0) {
			allocateCardToPlayer();
		}
		*/
		GameUtilities.addLogFromText("\n***************************************************************\n");
		GameUtilities.addLogFromText("               Fortification phase Begins  \n\n");
//		CommonMapUtilities.enableOrDisableSave(true);
		btnFortify.setDisable(false);
//		CommonMapUtilities.disableControls(btnNoMoreAttack);
		btnFortify.requestFocus();
		CommonMapUtilities.disableControls(btnReinforcement);
		if (!(currentPlayer.getStrategy() instanceof Human)) {
			fortifyArmy();
		}
	}

	/**
	 * This method is used for fortification phase.
	 */
	private void fortifyArmy() {
		playerModel.fortificationPhase(counList, adjCounList, map);
		counList.refresh();
		adjCounList.refresh();
		updateMap();
	}

	/**
	 * This method handles the case in which fortification is not possible.
	 */
	private void noFortification() {
		GameUtilities.addLogFromText("  Fortification phase Started \n");
		GameUtilities.addLogFromText(currentPlayer.getName() + " does not have any armies for fortification.\n");
		GameUtilities.addLogFromText(" Fortification phase ended  \n");
		setPhase("Phase : Reinforcement");
		/*if(playerModel.getNumOfCountryWon()>0) {
			allocateCardToPlayer();
		}*/
		initializeReinforcement(false);
	}

	/**
	 * This method initialized the component for the reinforcement phase.
	 * 
	 * @param loadPlayerFromStart A boolean variable whether to load the player from
	 *                            start.
	 */
	private void initializeReinforcement(boolean loadPlayerFromStart) {
		System.out.println("Inside intialize reinforcement " + loadPlayerFromStart);
		refreshList();
		
//		CommonMapUtilities.enableOrDisableSave(true);
		
		loadCurrentPlayer(loadPlayerFromStart);
		CommonMapUtilities.disableControls(btnPlaceArmy, btnFortify, btnEndTurn);
		btnReinforcement.setDisable(false);
		btnReinforcement.requestFocus();
		GameUtilities.addLogFromText("\n***************************************************************\n");
		GameUtilities.addLogFromText("               Reinforcement phase Begins  \n\n");
		GameUtilities.addLogFromText(currentPlayer.getName() + "\n");
		countReinforcementArmies();
		if (!(currentPlayer.getStrategy() instanceof Human)) {
			reinforceArmy(null);
		} 
//		else {		
//			
//		}
	}

	/**
	 * method to count the number of armies to be assigned to a player in
	 * reinforcement phase.
	 */
	public void countReinforcementArmies() {
		if (this.currentPlayer != null) {
			currentPlayer = PlayerModel.countReinforcementArmies(map, currentPlayer);
			setCurrentPlayerLabel(currentPlayer.getName() + " =>  " + currentPlayer.getArmies() + " armies left.");
			System.out.println(currentPlayer.getArmies());
		} else {
			GameUtilities.addLogFromText("Error! No Current Player.");
		}
	}

	
	/**
	 * This method refreshes the lists.
	 */
	private void refreshList() {
		counList.getItems().clear();
		adjCounList.getItems().clear();
		for (Country country : currentPlayer.getAssignedCountry()) {
			counList.getItems().add(country);
		}
	}

	/**
	 * This methods sets player label and populates the world and military domination
	 * view.
	 */
	private void setLabelAndShowWorldDomination() {
		setCurrentPlayerLabel(currentPlayer.getName() + " => " + currentPlayer.getArmies() + " armies left.\n");
		showMilitaryDominationData();
	}

	/**
	 * This method prints the continents owned by a player.
	 */
	private void showContinentThatBelongsToPlayer() {
		List<Continent> listOfContinentsOwnedSingly = (playerModel.getPlayersContinents(map,currentPlayer));
		if (listOfContinentsOwnedSingly.size() != 0) {
			GameUtilities.addLogFromText("*************************************************************\n                Continents Owned By Player \n*********************************************************\n");
			for (Continent c : listOfContinentsOwnedSingly) {
				GameUtilities.addLogFromText("***********************************************************\n");
				GameUtilities.addLogFromText(c.getName() + " is owned by => " + currentPlayer.getName()+"\n");
				GameUtilities.addLogFromText("*********************************************************** \n");
			}
		}
	}

	/**
	 * Check If Any Player Won the game.
	 * 
	 * @return playerWon returns true if a player won the game
	 */
	private boolean checkIfPlayerWonTheGame() {
		
		if (!isGameOver && playerList.size() == 1 ) {
			CommonMapUtilities.alertBox("Info", "Player =>  " + playerList.get(0).getName() + " won the game!", "");
			isGameOver = true;
			refreshList();
			disableGameControls();
			setChanged();
			System.out.println("There");
			notifyObservers("gameOver"+gameNo);
			oneTime = false;
		}
		return isGameOver;
	}

	/**
	 * Disable the game after game is over
	 * 
	 */
	private void disableGameControls() {
		
		CommonMapUtilities.disableControls(counList, adjCounList, btnReinforcement, btnFortify,
				btnEndTurn);//btnNoMoreAttack
//		btnNoMoreAttack.setDisable(true);
		lblGamePhase.setText("GAME OVER");
		setCurrentPlayerLabel(playerList.get(0).getName().toUpperCase() + " WON THE GAME");
		updateMap();
		setLabelAndShowWorldDomination();
		GameUtilities.addLogFromText("********************************************************************************\n");
		System.out.println(gameNo+" - "+playerList.get(0).getName() + " WON THE GAME");
		GameUtilities.addLogFromText("*******************************************************************************\n");
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		
		String str = (String) arg;
		
		if(!str.equals("disableGameControls") && isGameOver) {
			return;
		}
		

		if (str.equals("ReinforcementFirst")) {
			setPhase("Phase : Reinforcement");
			initializeReinforcement(true);
		} else if (str.equals("Reinforcement")) {
			setPhase("Phase : Reinforcement");
			initializeReinforcement(false);
		}else if( str.equals("Attack")){
			attack();
		}else if (str.equals("placeArmy")) {
			setPhase("Phase : Place Army");
			initializePlaceArmy();	
			showMilitaryDominationData();
		} else if (str.equals("Fortification")) {
			setPhase("Phase : Fortification");
			adjCounList.getItems().clear();
			initializeFortification();
		} else if (str.equals("NoFortification")) {
			setPhase("Phase : No Fortification");
			noFortification();
		}else if (str.equals("checkForValidFortificaion")) {
			refreshList();
			isValidFortificationPhase();
		} else if (str.equals("playerStrategyChoosen")) {
			allocateArmyAndCoun();
		} else if (str.equals("printMessageOnMsgArea")) {
			GameUtilities.addLogFromText(Configuration.message);
			Configuration.message = "";
		} else if (str.equals("noMoreAttack")) {
//			noMoreAttack(null);
		} else if (str.equals("skipAndGoToFortify")) {
//			noMoreAttack(null);
		}else if (str.equals("disableGameControls")) {
			disableGameControls();
		}else if(str.equals("updateReinforceArmy")) {
			setCurrentPlayerLabel(currentPlayer.getName() + " =>  " + currentPlayer.getArmies() + " armies left.");
			counList.refresh();
		}
	}

	public void attack() {
		try {
			playerModel.attackPhase(counList, adjCounList, txtAreaMsg);
		} catch (InvalidGameAction e) {
			// TODO: handle exception
			CommonMapUtilities.alertBox("Info", e.getMessage(), "alert");
			return;
		}
	}
	/**
	 * This method assigns armies and countries to players.
	 */
	public void allocateArmyAndCoun() {
		choiceBoxNoOfPlayer.setDisable(true);
		playerListIterator = playerList.iterator();
		CommonMapUtilities.enableControls(btnPlaceArmy);
		PlayerModel.allocateArmiesToPlayers(playerList, txtAreaMsg);
		
		if (playerList.size() > GameUtilities.getCountryList(map).size()) {
			try {
				throw new InvalidMap("Countries must be more than players.");
			} catch (InvalidMap e) {
				CommonMapUtilities.alertBox("Alert", e.getMessage(), "Error");
				e.printStackTrace();
				return;
			}
		}
		
		boolean isAllComputerPlayes = checkIfAllComputerPlayer();
		Configuration.isAllComputerPlayer = isAllComputerPlayes;
		if(isAllComputerPlayes) {
	//		btnSaveGame.setDisable(true);
		}
				
		try {

			allocateCountriesToPlayer();
			setPhase("Phase : Place Army");
			loadCurrentPlayer(false);
			showMilitaryDominationData();
		} catch (InvalidMap e) {
			CommonMapUtilities.alertBox("Alert", e.getMessage(), "Error");
			e.printStackTrace();
		}
		if (!(currentPlayer.getStrategy() instanceof Human)) {
			placeArmy(null);
		}
	}

	/**
	 * This method checks if all player are human players or not.
	 * @return true if all players are computer players else return false
	 */
	private boolean checkIfAllComputerPlayer() {
		//verify if all players are computer players or not
		for(Player p : playerList) {
			if(p.getStrategy() instanceof Human) {
				//at least one is human player
				Configuration.isAllComputerPlayer = false;
				Configuration.isPopUpShownInAutoMode = true;
				return false;
			}
		}
		Configuration.isPopUpShownInAutoMode = false;
		return true;
	}

	/**
	 * This method populates World Domination Data(armies) into a bar chart.
	 */
	private void showMilitaryDominationData() {
		HashMap<String, Double> playerAndMilitaryCountMap = WorldDominationModel.getMilitaryDominationData(map);
		Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();

		ArrayList<String> sortedKeysList = new ArrayList<String>(playerAndMilitaryCountMap.keySet());
		Collections.sort(sortedKeysList);
		for (String key : sortedKeysList) {
			dataSeries1.getData().add(new XYChart.Data<String, Number>(key, playerAndMilitaryCountMap.get(key)));
		}
		armyDominationChart.getData().clear();
		armyDominationChart.getData().addAll(dataSeries1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(map);
		out.writeObject(currentPlayer);

		out.writeObject(playerModel);
		
		out.writeObject(playerList);

		state = new GameUIStateModel();

		if (!btnPlaceArmy.isDisabled()) {
			state.isPlaceArmyEnable = true;
		}
		
		if (!choiceBoxNoOfPlayer.isDisabled()) {
			state.choiceBoxNoOfPlayer = true;
		}

		if (!btnReinforcement.isDisabled()) {
			state.isReinforcemetnEnable = true;
		}


//		if (!btnNoMoreAttack.isDisabled()) {
//			state.isNoMoreAttackEnable = true;
//		}

		if (!btnFortify.isDisabled()) {
			state.isFortificationEnable = true;
		}

		if (!btnEndTurn.isDisabled()) {
			state.isEndTurnEnable = true;
		}

		out.writeObject(state);

		out.writeObject(txtAreaMsg.getText());
		out.writeObject(lblGamePhase.getText());
		out.writeObject(lblCurrPlayer.getText());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
	//	isGameSaved = true;

		map = (Map) in.readObject();
		currentPlayer = (Player) in.readObject();

		playerModel = (PlayerModel) in.readObject();
		
		playerList = new ArrayList<>();
		playerList = (List<Player>) in.readObject();

		state = (GameUIStateModel) in.readObject();

		txtMsgAreaTxt = (String) in.readObject();
		phaseOfTheGame = (String) in.readObject();
		lblPlayerString = (String) in.readObject();

		playerModel.addObserver(this);

		
	}
	

	
	/**
	 * This method loads the controller for testing.
	 * @param playerList The playerlist.
	 * @param console The area where the results are to be displayed.
	 */
	public void loadControllerForTest(List<Player> playerList, TextArea console) {
		this.numOfTurnDone = 0;
		this.maxNumOfEachPlayerTurn = maxNumOfTurn * playerList.size();
		btnReinforcement = new Button();
		lblCurrPlayer = new Label();
		lblGamePhase = new Label("Start Up");
		btnPlaceArmy = new Button();
		CategoryAxis xAxis    = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		armyDominationChart = new BarChart(xAxis, yAxis);
		btnFortify = new Button();
//		btnNoMoreAttack = new Button();
		btnEndTurn = new Button();
		counList = new ListView<Country>();
		adjCounList = new ListView<Country>();
		txtAreaMsg = new TextArea();
		vbox = new VBox();
		choiceBoxNoOfPlayer = new ChoiceBox<>();
		
	
		Configuration.waitBeweenTurn = 1000;
		Configuration.isPopUpShownInAutoMode = false;
		
		GameUtilities.txtMsgArea = console;
		
		
	}

}
