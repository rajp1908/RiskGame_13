package com.risk6441.strategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.risk6441.entity.Country;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;


import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
/**
 * 
 * @author Deep
 *
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
	 * This method gives the defending territories for given territory.
	 * @param country Selected country
	 * @return List of defending country.
	 */
	default public List<Country> getDefendingTerr(Country country) {
		List<Country> defCountryList = country.getAdjacentCountries().stream()
				.filter(t -> (country.getPlayer() != t.getPlayer())).collect(Collectors.toList());
		return defCountryList;
	}
}
