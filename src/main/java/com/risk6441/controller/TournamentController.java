package com.risk6441.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.risk6441.configuration.PlayerStrategy;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.exception.InvalidMap;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.maputilities.MapReader;
import com.risk6441.models.TournamentModel;
import com.risk6441.strategy.Aggressive;
import com.risk6441.strategy.Benevolent;
import com.risk6441.strategy.Cheater;
import com.risk6441.strategy.IStrategy;
import com.risk6441.strategy.Random;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class controls the action of tournament
 * <ul>
 * <li>  no of player in Tournament  </li>
 * <li>  get no of players </li>
 * <li>  action map </li>
 * <li>  create players </li>
 * <li>  etc... </li>
 * </ul>
 * @author Deep
 */


public class TournamentController implements Initializable, Observer {

	private int numeberOfGames;

	private List<Map> mapList = new ArrayList<>();

	private List<Player> playerList = new ArrayList<>();
	
	private int mapCount = 0;
	
	private int gameCount = 0;
	
	private int numberOfTurns;

	private TournamentModel model;

	@FXML
	private TextArea txtAreaConsole;

	@FXML
	private AnchorPane ttlPane;

	@FXML
	private TextArea txtAreaResult;

	@FXML
	private Button btnPlay;

	@FXML
	private Button btnExit;

	@FXML
	private ComboBox<Integer> comboGames;

	@FXML
	private ComboBox<Integer> comboTurns;

	@FXML
	private Button btnMap1;

	@FXML
	private Button btnMap2;

	@FXML
	private Button btnMap3;

	@FXML
	private Button btnMap4;

	@FXML
	private Button btnMap5;

	@FXML
	private ComboBox<String> comboP1;

	@FXML
	private ComboBox<String> comboP2;

	@FXML
	private ComboBox<String> comboP3;

	@FXML
	private ComboBox<String> comboP4;

	@FXML
	private Label lblMessage;

	public TournamentController() {
		mapList = new ArrayList<>();
		playerList = new ArrayList<>();
	}

	/**
	 * @return the numeberOfGames
	 */
	public int getNumeberOfGames() {
		return numeberOfGames;
	}

	/**
	 * @param numeberOfGames the numeberOfGames to set
	 */
	public void setNumeberOfGames(int numeberOfGames) {
		this.numeberOfGames = numeberOfGames;
	}

	/**
	 * @return the numberOfTurns
	 */
	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setErrorMessage(String message) {
		lblMessage.setText(message);
	}

	/**
	 * @param numberOfTurns the numberOfTurns to set
	 */
	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}

	/**
	 * This method is for the map button 1
	 * @param event Map button clicks
	 */
	@FXML
	void actionMap1(ActionEvent event) {
		File filePath = openDialogAndUploadMap(1);
		btnMap1.setText(filePath.getName());
	}


	/**
	 * This method is for the map button 2
	 * @param event Map button clicks
	 */
	@FXML
	void actionMap2(ActionEvent event) {
		File filePath = openDialogAndUploadMap(2);
		btnMap2.setText(filePath.getName());
	}


	/**
	 * This method is for the map button 3
	 * @param event Map button clicks
	 */
	@FXML
	void actionMap3(ActionEvent event) {
		File filePath = openDialogAndUploadMap(3);
		btnMap3.setText(filePath.getName());
	}


	/**
	 * This method is for the map button 4
	 * @param event Map button clicks
	 */
	@FXML
	void actionMap4(ActionEvent event) {
		File filePath = openDialogAndUploadMap(4);
		btnMap4.setText(filePath.getName());
	}


	/**
	 * This method is for the map button 5
	 * @param event Map button clicks
	 */
	@FXML
	void actionMap5(ActionEvent event) {
		File filePath = openDialogAndUploadMap(5);
		btnMap5.setText(filePath.getName());
	}


	/**
	 * This method is for the map button 6
	 * @param event Map button clicks
	 */
	@FXML
	void exitTournament(ActionEvent event) {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
		stage.setOnCloseRequest(e -> Platform.exit());
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setErrorMessage("Please enter the details!");
		lblMessage.setAlignment(Pos.CENTER);
		model = new TournamentModel();
		GameUtilities.loadTurnsInTournament(comboTurns);
		GameUtilities.loadGamesInTournament(comboGames);
		GameUtilities.loadPlayersInTournament(comboP1);
		GameUtilities.loadPlayersInTournament(comboP2);
		GameUtilities.loadPlayersInTournament(comboP3);
		GameUtilities.loadPlayersInTournament(comboP4);
		setUpListener();
	}

	/**
	 * This method sets up listeners for the tournament mode screen.
	 */
	private void setUpListener() {
		comboTurns.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfTurns(comboTurns.getSelectionModel().getSelectedItem());
			}
		});

		comboGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumeberOfGames(comboGames.getSelectionModel().getSelectedItem());
			}
		});

		comboP1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player player1 = createPlayer(1);
				player1.setName("Player" + 1);
				setPlayerStrategy(comboP1.getSelectionModel().getSelectedItem(), player1);
			}
		});

		comboP2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player player2 = createPlayer(2);
				player2.setName("Player" + 2);
				setPlayerStrategy(comboP2.getSelectionModel().getSelectedItem(), player2);
			}
		});

		comboP3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player player3 = createPlayer(3);
				player3.setName("Player" + 3);
				setPlayerStrategy(comboP3.getSelectionModel().getSelectedItem(), player3);
			}
		});

		comboP4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player player4 = createPlayer(4);
				player4.setName("Player" + 4);
				setPlayerStrategy(comboP4.getSelectionModel().getSelectedItem(), player4);
			}
		});
	}

	/**
	 * This method creates the players for the tournament mode.
	 * @param id The id of player
	 * @return The player object created.
	 */
	public Player createPlayer(int id) {
		Player player = null;
		for (Player p : playerList) {
			if (p.getId() == id) {
				player = p;
			}
		}
		if (player == null) {
			player = new Player(id, "Player" + id);
			System.out.println("Creating "+player);
			playerList.add(player);
		}
		
		return player;
	}

	/**
	 * This method sets the players strategy.
	 * @param playerType Type of player like aggressive, random etc.
	 * @param player The current player object.
	 */
	public void setPlayerStrategy(String playerType, Player player) {
		PlayerStrategy type = null;
		IStrategy strategy = null;
		if (PlayerStrategy.AGGRESSIVE.toString().equals(playerType)) {
			type = PlayerStrategy.AGGRESSIVE;
			strategy = new Aggressive();
		} else if (PlayerStrategy.BENEVOLENT.toString().equals(playerType)) {
			type = PlayerStrategy.BENEVOLENT;
			strategy = new Benevolent();
		} else if (PlayerStrategy.CHEATER.toString().equals(playerType)) {
			type = PlayerStrategy.CHEATER;
			strategy = new Cheater();
		} else if (PlayerStrategy.RANDOM.toString().equals(playerType)) {
			type = PlayerStrategy.RANDOM;
			strategy = new Random();
		}
		player.setPlayerStrategy(type);
		player.setStrategy(strategy);
	}

	/**
	 * This method loads the map.
	 * @param mapPosition Position of map.
	 * @return The file openend/
	 */
	public File openDialogAndUploadMap(int mapPosition) {
		File file = CommonMapUtilities.openMapFile();
		MapReader mapReader = new MapReader();
		Map map = null;
		setErrorMessage("Please enter the details!");
		try {
			map = mapReader.readMapFile(file);
			map.getMapData().put("image", "Map"+mapPosition);
			mapList.add(mapPosition-1, map);
		} catch (InvalidMap e) {
			e.printStackTrace();
			setErrorMessage("Map : "+mapPosition+" is invalid!");
			CommonMapUtilities.alertBox("Error", e.getMessage(), "Map is not valid.");
		}catch (Exception e) {
			CommonMapUtilities.alertBox("Error", "Load previour maps before laoding map", "Error"+mapPosition);
		}
		for(Map m: mapList) {
			System.out.println(m.getMapData().get("image"));
		}
		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@FXML
	void playTournament(ActionEvent event) throws CloneNotSupportedException {
		setErrorMessage("Playing tournament");
		model.addObserver(this);
		model.txA = txtAreaResult;
		if (getNumeberOfGames() == 0) {
			setErrorMessage("Please Select number of games");
			return;
		}

		if (getNumberOfTurns() == 0) {
			setErrorMessage("Please Select number of turns");
			return;
		}

		if (mapList.isEmpty()) {
			setErrorMessage("At least one map should be there");
			return;
		} else if (playerList.size() < 2) {
			setErrorMessage("Choose at least 2 Players.");
			return;
		}else {
			GameUtilities.addLogFromText("=====Tournament started!=====\n");
			
			btnPlay.setDisable(true);
			Map newMap = (Map) mapList.get(0).clone();
			
			gameCount++;
			

			model.startTournament(playerList, newMap, numberOfTurns, numeberOfGames, gameCount, txtAreaConsole);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		GameUtilities.addLogFromText("Update Called in tournament\n", txtAreaConsole);
		
		if(gameCount==numeberOfGames) {
			mapCount++;
			gameCount = 0;
		}
		System.out.println("Gameover");
		//final call
		if(mapCount==mapList.size() && gameCount==0) {
			//print result
			setErrorMessage("Tournament ended. Check the results");
			GameUtilities.addLogFromText("Result\n", txtAreaResult);
			for (Entry<String, HashMap<String, String>> entry : model.getTournamentResults().entrySet()) {
				GameUtilities.addLogFromText(entry.getKey() + "\n", txtAreaResult);
				for (Entry<String, String> data : entry.getValue().entrySet()) {
					GameUtilities.addLogFromText(data.getKey() + " : " + data.getValue() + "\n", txtAreaResult);
				}
				GameUtilities.addLogFromText("=============================================\n", txtAreaResult);
			}
		}else {
			//call playgame and return
			//gameCount
			Map newMap = null;
			try {
				newMap = (Map) mapList.get(mapCount).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			System.out.println("Calling for "+mapCount+ " - "+gameCount+"\n");
			GameUtilities.addLogFromText("Calling for "+mapCount+ " - "+gameCount+"\n", txtAreaConsole);
			model.startTournament(playerList, newMap, numberOfTurns, numeberOfGames, ++gameCount, txtAreaConsole);
		}
	}

}
