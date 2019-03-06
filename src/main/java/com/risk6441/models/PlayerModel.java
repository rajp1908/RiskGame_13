package com.risk6441.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.zip.CheckedOutputStream;

import com.risk6441.configuration.Configuration;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.entity.Country;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.strategy.Human;
import com.sun.media.jfxmedia.events.PlayerTimeListener;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Dolly
 * @author Hardik
 */
public class PlayerModel extends Observable implements Observer, Serializable {

	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = 6224554451688312772L;

	private List<Player> playerList;

	/**
	 * Returns the playerlist
	 * @return the playerList
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * Sets the playerlist.
	 * @param playerList the playerList to set
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * 
	 * the @playerPlaying reference
	 */
	Player currentPlayer;

	/**
	 * Get the current player.
	 * @return player playing
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * This method is to set the current player.
	 * 
	 * @param player Current player.
	 */
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	/**
	 * This method allocates armies to players and display log in TextArea.
	 * 
	 * @param ply list of players
	 * @param txtArea textArea object
	 */
	public static void allocateArmiesToPlayers(List<Player> ply, TextArea txtArea) {
		GameUtilities.addLogFromText("\nAssigning armies to players.\n");

		int playerArmy = 0;
		int number_of_players = ply.size();

		switch (number_of_players) {
		case 2:
			playerArmy = Configuration.ARMIES_TWO_PLAYER;
			break;

		case 3:
			playerArmy = Configuration.ARMIES_THREE_PLAYER;
			break;

		case 4:
			playerArmy = Configuration.ARMIES_FOUR_PLAYER;
			break;

		case 5:
			playerArmy = Configuration.ARMIES_FIVE_PLAYER;
			break;

		case 6:
			playerArmy = Configuration.ARMIES_SIX_PLAYER;
			break;
		}

		for (Player player : ply) {
			player.setArmies(playerArmy);
			GameUtilities.addLogFromText(player.getName() + " has been assigned: " + playerArmy + "\n");
		}
	}

	/**
	 * This method creates the players.
	 * 
	 * @param number_of_players no of players to be created
	 * @param textArea  textArea object to append the log
	 * @return list of players after creating players
	 */
	public static List<Player> createPlayers(int number_of_players, TextArea textArea) {
		List<Player> players = new ArrayList<Player>();
		for (int i = 1; i <= number_of_players; i++) {
			String name = "Player" + i;
			players.add(new Player(i, name));
			GameUtilities.addLogFromText(name + " created!\n");
		}
		Iterator<Player> i = players.iterator();
		while(i.hasNext()) {
			System.out.println("Name: Player"+i.next().getId());
		}
		return players;
	}

	/**
	 * This method counts the number of reinforcement armies for the player.
	 * 
	 * @param map map object
	 * @param currentPlayer current player object
	 * @return return the player object after assigning armies.
	 */
	public static Player countReinforcementArmies(Map map, Player currentPlayer) {
		int currentArmies = currentPlayer.getArmies();
		int countryCount = currentPlayer.getAssignedCountry().size();
		System.out.println("Number of Countires for Player : " + currentPlayer.getName() + " = " + countryCount);
		if (countryCount < 9) {
			currentArmies = currentArmies + 3;
		} else {
			currentArmies += (countryCount / 3);
		}

		List<Continent> continents = getPlayersContinents(map, currentPlayer);
		if (continents.size() > 0) {
			for (Continent continent : continents) {
				currentArmies = currentArmies + continent.getValue();
			}
		}
		currentPlayer.setArmies(currentArmies);

		return currentPlayer;
	}

	/**
	 * This method returns the continents owned by the current player.
	 * 
	 * @param map map object
	 * @param currentPlayer player object
	 * @return returns the list of continents that are owned by the player.
	 */
	public static List<Continent> getPlayersContinents(Map map, Player currentPlayer) {
		List<Continent> continents = new ArrayList<>();

		for (Continent continent : map.getContinents()) {
			boolean continentBelongToPlayer = true;
			for (Country con : continent.getCountries()) {
				if (!con.getPlayer().equals(currentPlayer)) {
					continentBelongToPlayer = false;
					break;
				}
			}
			if (continentBelongToPlayer) {
				System.out.println(continent.getName() + " is owned by " + currentPlayer.getName());
				continents.add(continent);
			}
		}

		return continents;
	}

	public boolean hasValidAttackMove(ArrayList<Country> countList) {
		boolean validMove = false;
		if(!validMove) {
			GameUtilities.addLogFromText("Player - " + currentPlayer.getName() + "\n");
			Platform.runLater(() -> {
				setChanged();
				notifyObservers("checkForValidFortification");
			});
			return validMove;
		}
		return validMove;
	}
	
	public void attackPhase(ListView<Country> countList, ListView<Country> adjCountList, TextArea txtmsg) throws InvalidGameAction{		
		if(playerList.size() <= 1)
			return;
				
		if((currentPlayer.getStrategy() instanceof Human) && playerList.size() > 1) {
			setChanged();
			notifyObservers("Fortification");
		}
	}
	/**
	 * This method implements the reinforcement phase for the player.
	 * @param country Selected country for reinforcement phase.
	 * @param countryList List of countries.
	 * @param txtAreaMsg Textarea where the message will be displayed.
	 */
	public void reinforcementPhase(Country country, ObservableList<Country> countryList, TextArea txtAreaMsg) {
		ArrayList<Country> counArList = new ArrayList<Country>(countryList);
		System.out.println("Inside reinforcement phase of player model");
		if(playerList.size()<=1)
			return;
		
		// Run the task in a background thread
		if(currentPlayer.getStrategy() instanceof Human) {
			System.out.println("Inside this");
			currentPlayer.getStrategy().reinforcementPhase(countryList, country, currentPlayer,counArList, null);
			if (currentPlayer.getArmies() <= 0 && playerList.size() > 1) {
				GameUtilities.addLogFromText("\n******** Reinforcement Phase Ended! ********************************\n");
				setChanged();
				notifyObservers("Attack");
			}
		}
		else {
			
			Thread backgroundThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(Configuration.waitBeweenTurn);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					currentPlayer.getStrategy().reinforcementPhase(countryList, country, currentPlayer,counArList,null);
					if (currentPlayer.getArmies() <= 0 && playerList.size() > 1) {
						GameUtilities.addLogFromText("\\n******** Reinforcement Phase Ended! ********************************\\\n");
						Platform.runLater(() -> {
							setChanged();
							notifyObservers("updateReinforceArmy");
							setChanged();
							notifyObservers("Attack");
						});
						return;
					}
				}
			});
			// Terminate the running thread if the application exits
			backgroundThread.setDaemon(true);
			// Start the thread
			backgroundThread.start();

		}
	}

	/**
	 * fortificationPhase() Method  implements fortification phase.
	 * @param countryList List of countries for fortification.
	 * @param adjCountryList List of adjacent countries for fortification.
	 * @param map The object of current map.
	 */
	public void fortificationPhase(ListView<Country> countryList, ListView<Country> adjCountryList, Map map) {
		ArrayList<Country> countryArList = new ArrayList<Country>(countryList.getItems());
		ArrayList<Country> adjCountryArList = new ArrayList<Country>(adjCountryList.getItems());
		
		if(playerList.size()<=1)
			return;
		
		if(currentPlayer.getStrategy() instanceof Human) {
			boolean isFortificationComplate = currentPlayer.getStrategy().fortificationPhase(countryList, adjCountryList,currentPlayer, map,
					countryArList, adjCountryArList);
			
			if(currentPlayer.getStrategy() instanceof Human  && (isFortificationComplate == false)) {
				return;
			}
			if (playerList.size() > 1) {
				setChanged();
				notifyObservers("Reinforcement");
			}
			
		}else {
			Thread backgroundThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(Configuration.waitBeweenTurn);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					boolean isFortificationComplate = currentPlayer.getStrategy().fortificationPhase(countryList,
							adjCountryList, currentPlayer, map, countryArList, adjCountryArList);

					if (playerList.size() > 1) {
						Platform.runLater(() -> {
							setChanged();
							notifyObservers("Reinforcement");
						});
					}

				}
			});
			backgroundThread.setDaemon(true);
			backgroundThread.start();
		}
	}


	/**
	 * This method is to place Armies in the countries.
	 * 
	 * @param countryList The list of countries.
	 * @param playerList number of  players in the game.
	 * @param txtAreaMsg The area where the message is to be displayed.
	 */
	public void placeArmies(ListView<Country> countryList, List<Player> playerList, TextArea txtAreaMsg) {
		if (currentPlayer.getStrategy() instanceof Human) {
			int playerArmies = currentPlayer.getArmies();
			if (playerArmies > 0) {
				Country country = countryList.getSelectionModel().getSelectedItem();
				if (country == null) {
					country = countryList.getItems().get(0);
				}
				GameUtilities.addLogFromText(
						currentPlayer.getName() + "  Assigned Armies to " + country.getName() + "\n");
				country.setArmy(country.getArmy() + 1);
				currentPlayer.setArmies(playerArmies - 1);
			}
		} else {
			assignArmiesTocountry(txtAreaMsg);
		}
		countryList.refresh();

		// if exhausted then call next phases
		if (checkIfPlayersArmiesExhausted(playerList)) {
			GameUtilities.addLogFromText("\n************   Place Army Phase Completed   ********************************\n");
			GameUtilities.addLogFromText("************    Start-up Phase Completed    ********************************\n\n");
			setChanged();
			notifyObservers("ReinforcementFirst");
		} else {
			setChanged();
			notifyObservers("placeArmy");
		}

	}
	
	
	/**
	 * This method ends the turn of the player.
	 */
	public void endTurn() {
		Platform.runLater(()->{
			setChanged();
			notifyObservers("Reinforcement");
		});
		
	}

	/**
	 * This method assign armies to player's countries randmomly for COMPUTER strategy 
	 * 
	 * @param txtAreaMsg The area where the message is to be displayed.
	 */
	private void assignArmiesTocountry(TextArea txtAreaMsg) {
		if (currentPlayer.getArmies() > 0) {
			Country con = currentPlayer.getAssignedCountry()
					.get(CommonMapUtilities.getRandomNumber(currentPlayer.getAssignedCountry().size() - 1));
			GameUtilities.addLogFromText(currentPlayer.getName() + " - Assigned Armies to " + con.getName() + "\n");
			con.setArmy(con.getArmy() + 1);
			currentPlayer.setArmies(currentPlayer.getArmies() - 1);
		}
	}

	/**
	 * This method checks if players armies is exhausted.
	 * 
	 * @param players object of player
	 * @return returns true if player has exhausted the armies
	 */
	public static boolean checkIfPlayersArmiesExhausted(List<Player> players) {
		for (Player player : players) {
			if (player.getArmies() != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method checks if the fortification phase is possible or not.
	 * 
	 * @param map map object
	 * @param currentPlayer object of current player
	 * @return return true if fortification phase is valid
	 */
	public boolean isFortificationPhasePossible(Map map, Player currentPlayer) {
		for (Continent continent : map.getContinents()) {
			for (Country con : continent.getCountries()) {
				if (con.getPlayer().equals(currentPlayer) && con.getArmy() > 1) {
					for (Country adjCountry : con.getAdjacentCountries()) {
						if (adjCountry.getPlayer().equals(currentPlayer)) {
							Platform.runLater(()->{
								setChanged();
								notifyObservers("Fortification");
							});
							return true;
						}

					}
				}
			}
		}
	
		Platform.runLater(()->{
			setChanged();
			notifyObservers("NoFortification");
		});
		return false;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}
