/**
 * 
 */
package com.risk6441.strategy;

import java.util.ArrayList;
import java.util.List;

import com.risk6441.controller.DiceController;
import com.risk6441.entity.Map;
import com.risk6441.entity.Player;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidGameAction;
import com.risk6441.gameutilities.GameUtilities;
import com.risk6441.maputilities.CommonMapUtilities;
import com.risk6441.models.DiceModel;
import com.risk6441.models.PlayerModel;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * @author Jemish
 * @author Raj
 * @author Dolly
 */
public class Random implements IStrategy {

	private Country attackingCoun;
	private PlayerModel playerModel;
	private DiceModel diceModel;
	private Player currentPlayer = null;
	private int numOfAttack = 0;
	
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

	/* (non-Javadoc)
	 * @see com.risk6441.strategy.IStrategy#attackPhase(javafx.scene.control.ListView, javafx.scene.control.ListView, com.risk6441.models.PlayerModel, javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Country> counList1, ListView<Country> adjCounList1, PlayerModel playerModel
			, List<Player> playerList,ArrayList<Country> counArList,ArrayList<Country> adjCounArList) throws InvalidGameAction {
		this.playerModel = playerModel;
		attackingCoun = getRandomCountry(counArList);
		List<Country> defendingCounList = getDefendingCoun(attackingCoun);
		if(defendingCounList.size()<1||hasAValidAttackMove(counArList)==false) {
			goToNoMoreAttack();			
		}
		else
		{
			for(Country defCoun : defendingCounList) {
				if (attackingCoun.getArmy() > 1 ) {
					GameUtilities.addLogFromText(attackingCoun.getName() + " attacking on " + defCoun.getName() + "\n");
					System.out.println(attackingCoun.getName()+ "("+attackingCoun.getPlayer().getName()+") attacking on "+defCoun+"("+defCoun.getPlayer().getName()+")\n");
					attack(attackingCoun, defCoun, playerModel);
					if(defCoun.getPlayer().equals(attackingCoun.getPlayer())) {
						counArList.add(defCoun);
					}
					break;
				}
			}
			goToNoMoreAttack();			
		}
	}
	
	private void goToNoMoreAttack() {
		playerModel.noMoreAttack();
	}

	
	/**
	 * This method perform attacks from attacking country to defending country.
	 * @param attackingCoun Attacking Country
	 * @param defCoun Defending Country
	 * @param playerModel object of {@link PlayerModel}
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
