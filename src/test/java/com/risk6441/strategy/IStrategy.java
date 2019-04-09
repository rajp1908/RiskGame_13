package com.risk6441.strategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.risk6441.entity.Country;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.models.PlayerModel;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
/**
 * 
 * @author Deep
 * @author Jemish
 * @author Dolly
 */
public interface IStrategy extends Serializable{
	
	/**
	 * This method will responsible for reinforcement phase.
	 * @param countryList  list of country
	 * @param country Selected country.
	 * @param currentPlayer Current player.
	 * @param countryArList List of countries available to player.
	 * @param adjCountryArList List of neighbour countries.
	 */
	public void reinforcementPhase(ObservableList<Country> countryList, Country country,
			Player currentPlayer,
			ArrayList<Country> countryArList,ArrayList<Country> adjCountryArList);
	
	
	/**
	 * This method implements the attack phase of the strategy.
	 * @param conList listview of country which belong to a player
	 * @param adjConList adjacent country listview for a particular country
	 * @param playerModel object of {@link PlayerModel}
	 * @param playerList list of players
	 * @param conArList List of country available to player.
	 * @param adjConArList List of adjacent country.
	 * @throws InvalidGameAction throws InvalidGameAction if move is not valid
	 */
	void attackPhase(ListView<Country> conList, ListView<Country> adjConList,
			PlayerModel playerModel, List<Player> playerList,
			ArrayList<Country> conArList,ArrayList<Country> adjConArList) throws InvalidGameAction;
	
	
	
	

	/**
	 * This method implements the fortification phase of the strategy.
	 * @param selectedCountry List of selected countries for the fortification phase.
	 * @param adjCountry List of neighbour countries for fortification phase.
	 * @param currentPlayer  current player object.
	 * @param map  current map loader.
	 * @param countryArList List of countries.
	 * @param adjCountryArList List of neighbour countries.
	 * @return Returns true if fortification phase is possible.
	 */
	boolean fortificationPhase(ListView<Country> selectedCountry, ListView<Country> adjCountry, Player currentPlayer, Map map,
			ArrayList<Country> countryArList,ArrayList<Country> adjCountryArList);
	
	
	
	/**
	 * This method gives the defending countries for given country.
	 * @param country Selected country
	 * @return List of defending country.
	 */
	default public List<Country> getDefendingCoun(Country country) {
		List<Country> defCountryList = country.getAdjacentCountries().stream()
				.filter(t -> (country.getPlayer() != t.getPlayer())).collect(Collectors.toList());
		return defCountryList;
	}
	
	
	/**
	 * Check if the player has a valid attack move
	 * @param countries List of countries with the player.
	 * @return Returns true if the player has a valid move available.
	 */
	default public boolean hasAValidAttackMove(ArrayList<Country> countries) {
		boolean isValidAttackMove = false;
		for (Country con : countries) {
			if (con.getArmy() > 1 && getDefendingCoun(con).size() > 0) {
				isValidAttackMove = true;
				return isValidAttackMove;
			}
		}
		if (!isValidAttackMove) {
			GameUtilities.addLogFromText("No valid attack move avialble move to Fortification phase.\n");
			GameUtilities.addLogFromText("******Attack phase ended! ******** \n");
			return isValidAttackMove;
		}
		return isValidAttackMove;
	}
}
