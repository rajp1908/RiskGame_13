package com.risk6441.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.risk6441.controller.DiceController;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.models.DiceModel;
import com.risk6441.models.PlayerModel;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * @author Jemish
 * @author Raj
 * @author Dolly
 */
public class Aggressive implements IStrategy {

	private Country attackingCoun;
	private PlayerModel playerModel;
	private DiceModel diceModel;
	private int numOfAttack = 0;
	private Player currentPlayer = null;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.
	 * ObservableList, com.risk6441.entity.Country, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Country> countryList1, Country country, Player currentPlayer,
			ArrayList<Country> counArList,ArrayList<Country> adjCounArList) {
		System.out.println(currentPlayer.getName() + " - " + counArList.size() + " - Country List Size");
		this.currentPlayer  = currentPlayer;
		numOfAttack = 0;
		if(attackingCoun == null) { //first reinforce
			attackingCoun = sortAndGetMaxDefendingCoun(counArList).get(0);
		}else {
			attackingCoun = getAttackingCountry(counArList);	
		}
		
		int army = currentPlayer.getArmies();
		attackingCoun.setArmy(attackingCoun.getArmy() + army);
		currentPlayer.setArmies(0);
		GameUtilities.addLogFromText("****" + army + " assigned to : ***** \n" + attackingCoun + "  -- Player "
				+ currentPlayer.getName() + "\n");
		System.out.println("****" + army + " assigned to : ***** \n" + attackingCoun + "  -- Player "
				+ currentPlayer.getName() + "\n");
		GameUtilities.addLogFromText("**********Reinforcement Phase Completed. *************\n");
		
		for(Country t : getDefendingCoun(attackingCoun)) {
			System.out.println(t.getName()+ " adjacent");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView,
	 * javafx.scene.control.ListView, com.risk6441.models.PlayerModel,
	 * javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Country> counList1, ListView<Country> adjCounList1, PlayerModel playerModel,
			List<Player> playerList,ArrayList<Country> counArList,ArrayList<Country> adjCounArList) throws InvalidGameAction {
		System.out.println("Inside attackpahse aggressive");
		this.playerModel = playerModel;
		this.diceModel = null;

		if(attackingCoun != null)
			System.out.println(attackingCoun.getArmy() > 1 );
		
		System.out.println(playerList.size() > 1);
		System.out.println(attackingCoun.getArmy() > 1 && playerList.size() > 1);
		
		while (attackingCoun.getArmy() > 1  && playerList.size() > 1) {
			System.out.println("Playerlist size " + playerList.size());
			List<Country> defendingCounList = getDefendingCoun(attackingCoun);
			if (defendingCounList.size() == 0) {
				System.out.println("Defending Country Size 0");
				break;
			} else {
				System.out.println("Inside else attackphase");
				for (Country defCoun : defendingCounList) {
					GameUtilities.addLogFromText("Army on defending " + defCoun.getArmy() + "\n");
					GameUtilities.addLogFromText(attackingCoun.getName() + " attacking on " + defCoun.getName() + "\n");
					attack(attackingCoun, defCoun, playerModel);
					System.out.println(attackingCoun.getName() + "(" + attackingCoun.getPlayer().getName()
							+ ") attacking on " + defCoun.getName() + "(" + defCoun.getPlayer().getName() + ")");
					if(defCoun.getPlayer().equals(attackingCoun.getPlayer())) {
						counArList.add(defCoun);
					}
					if(playerList.size()>1  && (!hasAValidAttackMove(counArList))) {
						//don't call no more attack if attack is not valid, this check will itself redirect to fortification
						playerModel.hasValidAttackMove(counArList);
						return;
					}
					break;
				}
			}

			if (playerList.size() == 1) {
				break;
			}

			attackingCoun = getAttackingCountry(counArList);
		}

		goToNoMoreAttack();
	}

	/**
	 * This method ends the current player's attack.
	 */
	private void goToNoMoreAttack() {
		playerModel.noMoreAttack();
	}

	/**
	 * This method perform attacks from attacking country to defending country.
	 * 
	 * @param attackingCoun Attacking Country
	 * @param defCoun       Defending Country
	 * @param playerModel   object of {@link PlayerModel}
	 */
	private void attack(Country attackingCoun, Country defCoun, PlayerModel playerModel) {
		this.playerModel = playerModel;
		diceModel = new DiceModel(attackingCoun, defCoun);
		if (playerModel != null) {
			diceModel.addObserver(playerModel);
		}
		numOfAttack++;
		DiceController diceController = new DiceController(diceModel, this);
		diceController.loadDiceControllerForStrategy();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.
	 * ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea,
	 * com.risk6441.entity.Player)
	 */
	@Override
	public boolean fortificationPhase(ListView<Country> counList1, ListView<Country> adjCountry2,
			Player currentPlayer, Map map,ArrayList<Country> counArList,ArrayList<Country> adjCounArList) {
		System.out.println(counArList.size() + "------ size");
		
		if (numOfAttack<1) {
			System.out.println("Country Won 0");
			List<Country> sortedMaxarmyCoun = sortAndGetStrongestCoun1(counArList);
			for (Country strongCoun : sortedMaxarmyCoun) {
				System.out.println("Strong Country "+strongCoun);
				if (strongCoun.getArmy() < 2) {
					return false;
				}
				List<Country> adjCounList = GameUtilities.getAdjecentCountryForFortifiction(strongCoun, map, currentPlayer);
				adjCounList = sortAndGetMaxDefendingCoun((ArrayList<Country>) adjCounList);

				for (Country targetCoun : adjCounList) {
					GameUtilities.addLogFromText((strongCoun.getArmy() - 1) + " Armies Moved From " + strongCoun.getName()
							+ " to " + targetCoun.getName()+"\n");
					System.out.println((strongCoun.getArmy() - 1) + " Armies Moved From " + strongCoun.getName()
							+ " to " + targetCoun.getName());
					targetCoun.setArmy(targetCoun.getArmy() + strongCoun.getArmy() - 1);
					strongCoun.setArmy(1);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return true;
				}
			}
		}

		List<Country> sortedMaxArmyCoun = sortAndGetStrongestCoun1(counArList);
		if (sortedMaxArmyCoun.get(0).equals(sortAndGetMaxDefendingCoun1(counArList).get(0))) {
			// don't do fortification
			GameUtilities.addLogFromText("Not doing fortification because it is on best possible country"+"\n");
			return true;
		}
		
		for (Country targetCoun : sortedMaxArmyCoun) {
			List<Country> reachableCounList = new ArrayList<Country>();
			reachableCounList = GameUtilities.getAdjecentCountryForFortifiction(targetCoun, map, currentPlayer);
			System.out.println("Reachable Country of " + targetCoun + " - " + reachableCounList.size());
			if (reachableCounList.size() != 0) {
				reachableCounList = sortAndGetStrongestCoun1((ArrayList<Country>) reachableCounList);
				for (Country fromCoun : reachableCounList) {
					if (fromCoun.getArmy() > 1) {
						GameUtilities.addLogFromText((fromCoun.getArmy() - 1) + " Armies Moved From " + fromCoun.getName()
								+ " to " + targetCoun.getName());
						System.out.println((fromCoun.getArmy() - 1) + " Armies Moved From " + fromCoun.getName()
								+ " to " + targetCoun.getName());
						targetCoun.setArmy(targetCoun.getArmy() + fromCoun.getArmy() - 1);
						fromCoun.setArmy(1);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.risk6441.strategy.IStrategy#hasAValidAttackMove(javafx.scene.control.
	 * ListView)
	 */
	@Override
	public boolean hasAValidAttackMove(ArrayList<Country> countries) {
		attackingCoun = getAttackingCountry(countries);
		List<Country> defendingCountryList = getDefendingCoun(attackingCoun);
		if (defendingCountryList.size() > 0 && attackingCoun.getArmy() > 1) {
			return true;
		}
		return false;
	}

	/**
	 * Get the attacking country.
	 * @param counList The country list.
	 * @return Country which can attack.
	 */
	private Country getAttackingCountry(ArrayList<Country> counList) {
		List<Country> sortedListFromMaxAdjacent = sortAndGetStrongestCoun1(counList);
		if (attackingCoun == null || (!attackingCoun.equals(sortedListFromMaxAdjacent.get(0)))
				|| attackingCoun.getArmy() < 2) {
			for (Country t : sortedListFromMaxAdjacent) {
				if (t.getArmy() > 1) {
					attackingCoun = t;
					break;
				}
			}
		}
		System.out.println(attackingCoun + "attackingCoun Dekho");
		if(!attackingCoun.getPlayer().equals(currentPlayer)) {
			System.out.println("Dekho Here");
			return currentPlayer.getAssignedCountry().get(0);
		}
		return attackingCoun;
	}

	/**
	 * This method sorts the country list from max army to least army
	 * 
	 * @param countryList list of countries which belong to player
	 * @return return list of country in sorted order .... from max army to least
	 *         army
	 */
	private List<Country> sortAndGetStrongestCoun1(ArrayList<Country> countryList) {
		Collections.sort(countryList, new Comparator<Country>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Country t1, Country t2) {
				return Integer.valueOf(t2.getArmy()).compareTo(t1.getArmy());
			}
		});
		return countryList;
	}

	/**
	 * This methods sorts the country list from max countries with max defending
	 * country to least defending country
	 * 
	 * @param countryList list of countries which belong to player
	 * @return return list of country in sorted order .... with max Opponent
	 *         Countries at Top
	 */
	private List<Country> sortAndGetMaxDefendingCoun(ArrayList<Country> countryList) {
		Collections.sort(countryList, new Comparator<Country>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Country t1, Country t2) {
				return Integer.valueOf(getDefendingCoun(t2).size())- (getDefendingCoun(t1).size());
			}
		});
		return countryList;
	}
	
	private List<Country> sortAndGetMaxDefendingCoun1(ArrayList<Country> countryList) {
		Collections.sort(countryList, new Comparator<Country>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Country t1, Country t2) {
				return Integer.valueOf(getDefendingCoun(t2).size())- (getDefendingCoun(t1).size());
			}
		});
		return countryList;
	}
	
	
	/**
	 * This method sets the current players. used in test cases.
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	
}
