package com.risk6441.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.zip.CheckedOutputStream;

import com.risk6441.configuration.Configuration;
import com.risk6441.entity.Card;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidGameAction;
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
	
	private static int[] numberOfArmiesList = {Configuration.ARMIES_TWO_PLAYER, Configuration.ARMIES_THREE_PLAYER,
			Configuration.ARMIES_FOUR_PLAYER,Configuration.ARMIES_FIVE_PLAYER,Configuration.ARMIES_SIX_PLAYER	};

	private List<Player> playerList;

	/**
	 * Returns the list of players
	 * @return the playerList
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * Sets the list of players.
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
	 * the @countryWon
	 */
	private int NumberOfcountryWon;

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

		//refactoring method Substitute Algorithm applied
		playerArmy = numberOfArmiesList[number_of_players - 2];

//		switch (number_of_players) {
//		case 2:
//			playerArmy = Configuration.ARMIES_TWO_PLAYER;
//			break;
//
//		case 3:
//			playerArmy = Configuration.ARMIES_THREE_PLAYER;
//			break;
//
//		case 4:
//			playerArmy = Configuration.ARMIES_FOUR_PLAYER;
//			break;
//
//		case 5:
//			playerArmy = Configuration.ARMIES_FIVE_PLAYER;
//			break;
//
//		case 6:
//			playerArmy = Configuration.ARMIES_SIX_PLAYER;
//			break;
//		}

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
			currentArmies += Math.floor(countryCount / 3);
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
	
	
	/**
	 * This method checks if the player has a valid attack move
	 * @param countList List of countries.
	 * @return Returns true if a player has a valid move available else returns false.
	 */

	public boolean hasValidAttackMove(ArrayList<Country> countList) {
		boolean validMove = currentPlayer.getStrategy().hasAValidAttackMove((ArrayList<Country>) countList);;
		if(!validMove) {
			GameUtilities.addLogFromText("Player - " + currentPlayer.getName() + "\n");
			GameUtilities.addLogFromText("---> No valid attack move avialble move to Fortification phase.\n");
			Platform.runLater(() -> {
				setChanged();
				notifyObservers("checkForValidFortification");
			});
			return validMove;
		}
		return validMove;
	}
	
	

	/**
	 * This method is to implement attack phase.
	 * 
	 * @param countList attacking country.
	 * @param adjCountList defending country.
	 * @param txtAreaMsg the area to show results.
	 * @throws InvalidGameAction Throws invalid game exception.
	 */
	
	public void attackPhase(ListView<Country> countList, ListView<Country> adjCountList, TextArea txtmsg) throws InvalidGameAction{
		PlayerModel playerModel=  this;
		ArrayList<Country> conArList = new ArrayList<Country>(countList.getItems());
		ArrayList<Country> adjConArList = new ArrayList<Country>(adjCountList.getItems());
		
		if(playerList.size()<=1)
			return;
		
		
		if(currentPlayer.getStrategy() instanceof Human ) {
			if(playerList.size()==1) {
				setChanged();
				notifyObservers("disableGameControls");
				return;
			}
			currentPlayer.getStrategy().attackPhase(countList, adjCountList, this,playerList, conArList,adjConArList);

			
				
		}else {
			Thread backgroundThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(Configuration.waitBeweenTurn);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (playerList.size() == 1) {

						Platform.runLater(() -> {
							setChanged();
							notifyObservers("disableGameControls");
						});
						return;
					}
					try {
						currentPlayer.getStrategy().attackPhase(countList, adjCountList, playerModel,playerList, conArList,adjConArList);
					} catch (InvalidGameAction e) {
						e.printStackTrace();
					}

					
				}
			});
			backgroundThread.setDaemon(true);
			backgroundThread.start();
		}
	}
	
	
	
	/**
	 * Check if Attack Move is Valid.
	 * 
	 * @param attacking attacking Country
	 * @param defending defending Country
	 * @return isValidAttackMove if the attack move is valid
	 * @throws InvalidGameAction invalid game exception.
	 */
	public boolean isValidAttackMove(Country attacking, Country defending) throws InvalidGameAction {
		boolean isValidAttackMove = false;
		if (defending.getPlayer() != attacking.getPlayer()) {
			if (attacking.getArmy() > 1) {
				isValidAttackMove = true;
			} else {
				throw new InvalidGameAction("Attacking country should have more than one army to attack.");
			}
		} else {
			throw new InvalidGameAction("You can\'t attack on your own country.");
		}
		return isValidAttackMove;
	}

	
	
	/**
	 * Check if any Player Lost the Game.
	 * 
	 * @param playerList playerPlaying List
	 * @return playerLost Player Object who lost the game
	 */
	public Player checkAnyPlayerLostTheGame(List<Player> playerList) {
		Player playerLost = null;
		for (Player player : playerList) {
			if (player.getAssignedCountry().isEmpty()) {
				playerLost = player;
				GameUtilities.addLogFromText(currentPlayer.getName() + " Got " + playerLost.getCardList().size()
						+ " cards from " + playerLost.getName() + "\n");
				System.out.println(currentPlayer.getName() + " Got " + playerLost.getCardList().size() + " cards from "
						+ playerLost.getName() + "\n");
				currentPlayer.getCardList().addAll(playerLost.getCardList());
			}
		}
		return playerLost;
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;
		if (str.equals("rollDiceComplete")) {
			DiceModel diceModel = (DiceModel) o;
			System.out.println(getNumOfCountryWon()+"Dekho won");
			setNumOfCountryWon(diceModel.getNumberOfCountryWon()+getNumOfCountryWon());
			Platform.runLater(() -> {
				setChanged();
				notifyObservers("rollDiceComplete");
			});
		} else if (str.equals("oneAttackDoneForCheater")) {

			Platform.runLater(() -> {
				setChanged();
				notifyObservers("oneAttackDoneForCheater");
			});

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
	 * This methods returns the number of country won by the player in a turn.
	 * 
	 * @return NumOfcountryWon Number of country won by player in a turn
	 */
	public int getNumOfCountryWon() {
		return NumberOfcountryWon;
	}

	/**
	 * This method sets the number of country won by the player.
	 * 
	 * @param countryWon the country Won to set
	 */
	public void setNumOfCountryWon(int countryWon) {
		this.NumberOfcountryWon = countryWon;
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
	
	/**
	 * This method is used to exchange cards for army.
	 * and also refactored by removing unused parameter textAreaMsg
	 * @param selectedCardsByThePlayer List of cards selected by the current player.
	 * @param txtAreaMsg               The area where the message has to be
	 *                                 displayed.
	 *                                 
	 */
	public void tradeCardsAndGetArmy(List<Card> selectedCardsByThePlayer) {
		currentPlayer.setArmies(currentPlayer.getArmies() + (5 * currentPlayer.getNumeberOfTimeCardsExchanged()));
		GameUtilities.addLogFromText(currentPlayer.getName() + " exchanged 3 cards for the army "
				+ (5 * currentPlayer.getNumeberOfTimeCardsExchanged() + "\n"));
		for (Country t : currentPlayer.getAssignedCountry()) {
			for (Card card : selectedCardsByThePlayer) {
				if (t.equals(card.getCountryToWhichCardBelong())) {
					t.setArmy(t.getArmy() + 2);
					GameUtilities.addLogFromText(
							currentPlayer.getName() + " got 2 extra armies on the " + t.getName() + ".\n");
					break;
				}
			}
		}
	}

	/**
	 * This method is invoked if there can't be another attack for the player.
	 */
	public void noMoreAttack() {
		if (playerList.size() <= 1)
			return;
		Platform.runLater(() -> {
			setChanged();
			notifyObservers("noMoreAttack");
		});
		
	}

	
}
