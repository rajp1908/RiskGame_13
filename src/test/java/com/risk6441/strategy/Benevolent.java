/**
 * 
 */
package com.risk6441.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.models.PlayerModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author Jemish
 * @author Raj
 *
 */
public class Benevolent implements IStrategy {

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Country, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Country> countryList, Country country,
			Player currentPlayer,ArrayList<Country> counArList,ArrayList<Country> adjCounArList) {
		List<Country> maximumOponentCoun = sortAndGetWeakestCoun(counArList);
		country = maximumOponentCoun.get(0);
		int army = currentPlayer.getArmies();
		country.setArmy(country.getArmy() + army);
		currentPlayer.setArmies(0);
		GameUtilities.addLogFromText(
				"***" + army + " assigned to : *** \n" + country + "  -- Player " + currentPlayer.getName() + "\n");
		System.out.println("***" + army + " assigned to : *** \n" + country + "  -- Player " + currentPlayer.getName() + "\n");
		GameUtilities.addLogFromText("********  Reinforce Phase Completed. *********\n");

	}


	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, com.risk6441.models.PlayerModel, javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Country> counList, ListView<Country> adjCounList, PlayerModel playerModel,
			List<Player> playerList,ArrayList<Country> counArList,ArrayList<Country> adjCounArList)  {
		// Benevolent Player does not attack
		
	}

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea, com.risk6441.entity.Player, com.risk6441.entity.Map)
	 */
	@Override
	public boolean fortificationPhase(ListView<Country> counList, ListView<Country> adjCounList,
			Player currentPlayer, Map map,ArrayList<Country> counArList,ArrayList<Country> adjCounArList) {
		List<Country> sortedMinAdjCoun = sortAndGetWeakestCoun(counArList);
		for (Country weakCoun : sortedMinAdjCoun) {
			for(Country adjCoun : weakCoun.getAdjacentCountries()) {
				if (adjCoun.getArmy() > 1) {
					weakCoun.setArmy(weakCoun.getArmy() + adjCoun.getArmy() -1);
					GameUtilities.addLogFromText((adjCoun.getArmy()-1)+" Armies Moved From "+adjCoun.getName()+" to "+weakCoun.getName()+"\n");
					adjCoun.setArmy(1);
					return true;
				}
			}
			
		}
		return false;
	}

	
	/**
	 * This method returns list of Countries in an increasing order of defending countries.
	 * @param countryList list of countries which belong to player
	 * @return return list of country in sorted order .... with minOpp Country at Top
	 */
	public List<Country> sortAndGetWeakestCoun(ArrayList<Country> countryList) {
		Collections.sort(countryList, new Comparator<Country>() {
			
			/* (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(Country t1, Country t2) {
				return Integer.valueOf(getDefendingCoun(t2).size()).compareTo(getDefendingCoun(t1).size());
			}
		});
		return countryList;
	}
}
