/**
 * 
 */
package com.risk6441.strategy;

import java.util.ArrayList;
import java.util.List;

import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.entity.Country;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * @author Dolly
 * @author Hardik
 *
 */
public class Computer implements IStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Player currentPlayer = null;

	
	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#reinforcementPhase(javafx.collections.ObservableList, com.risk6441.entity.Country, javafx.scene.control.TextArea, com.risk6441.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Country> countryList1, Country country,
			Player currentPlayer,ArrayList<Country> counArList,ArrayList<Country> adjCounArList) {
		
		
		int army = CommonMapUtilities.getRandomNumberFromOne(currentPlayer.getArmies());
		do{
			Country randomCoun = counArList.get(CommonMapUtilities.getRandomNumber(counArList.size()-1));
			randomCoun.setArmy(randomCoun.getArmy() + army);
			currentPlayer.setArmies(currentPlayer.getArmies()-army);
			GameUtilities.addLogFromText(army+" assigned to :"+randomCoun.getName()+"  by "+currentPlayer.getName()+"\n");
			if(currentPlayer.getArmies() == 0)
				break;
			army = CommonMapUtilities.getRandomNumberFromOne(currentPlayer.getArmies());
		}while (currentPlayer.getArmies() > 0);

	}

	
	
	
	/* 
	 * @see com.risk6441.strategy.IStrategy#fortificationPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, javafx.scene.control.TextArea, com.risk6441.entity.Player, com.risk6441.entity.Map)
	 */
	@Override
	public boolean fortificationPhase(ListView<Country> selectedCountry1, ListView<Country> adjCountry1,
			 Player currentPlayer, Map map,ArrayList<Country> counArList,ArrayList<Country> adjCounArList) {
		this.currentPlayer = currentPlayer;
		Country fromCoun = getRandomCountry(counArList);
		int count = -1;
		while(GameUtilities.getAdjecentCountryForFortifiction(fromCoun, map, currentPlayer).size()==0 && ++count!=(counArList.size()-1))
		{
			fromCoun = getRandomCountry(counArList);
		}
		if(count >= counArList.size()) {
			return false;
		}
		List<Country> adjCounForFortifiction = GameUtilities.getAdjecentCountryForFortifiction(fromCoun, map, currentPlayer);
		int size = adjCounForFortifiction.size();
		Country toCoun = null;
		if(size == 1) {
			toCoun = adjCounForFortifiction.get(0);
		}
		else if(size==0)
			return false;
		
		toCoun = adjCounForFortifiction.get(CommonMapUtilities.getRandomNumber(size-1));
		
		GameUtilities.addLogFromText((fromCoun.getArmy()-1)+" Armies Moved From "+fromCoun.getName()+" to "+toCoun.getName());
		System.out.println((fromCoun.getArmy()-1)+" Armies Moved From "+fromCoun.getName()+" to "+toCoun.getName());
		toCoun.setArmy(toCoun.getArmy() + fromCoun.getArmy() - 1);
		fromCoun.setArmy(1);
		return true;
	}

	/**
	 * This method is responsible for finding a random country.
	 * @param items This is a list of all the countries.
	 * @return country which can be used for attack.
	 */
	public Country getRandomCountry(ArrayList<Country> items) {
		int temp = CommonMapUtilities.getRandomNumber(items.size()-1);
		Country t = (Country) items.get(temp);
		while(t.getArmy()<2)
		{
			temp = CommonMapUtilities.getRandomNumber(items.size()-1);
			t = (Country) items.get(temp);
		}
			
		return t;
	}
	
}
