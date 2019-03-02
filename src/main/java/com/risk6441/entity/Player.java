package com.risk6441.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.risk6441.configuration.PlayerStrategy;


/**
 * @author Hardik
 * This class defines Player with its properties like id, name, armies and number of countries win by player.
 */
public class Player implements Serializable{
	
	
	private int id;
	private String name;
	private int armies;
	private List<Country> assignedCountry;
	private List<Card> cardList;
	private int numeberOfCardsExchanged;
	
	/**
	 * Player's Strategy For the Behavior 
	 */
	private IStrategy strategy;
	
	private PlayerStrategy playerStrategy;
	
	
	/**
	 * Getter method for the player strategy.
	 * @return the strategy
	 */
	public IStrategy getStrategy() {
		return strategy;
	}

	/**
	 * Setter method for the player strategy.
	 * @param strategy set strategy
	 */
	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Getter method for the current player strategy.
	 * @return playerStrategy
	 */
	public PlayerStrategy getPlayerStrategy() {
		return playerStrategy;
	}

	/**
	 * Setter method for the player strategy.
	 * @param playerStrategy set playerStrategy
	 */
	public void setPlayerStrategy(PlayerStrategy playerStrategy) {
		this.playerStrategy = playerStrategy;
	}

	/**
	 * Getter method for the cards of player.
	 * @return the cardList
	 */
	public List<Card> getCardList() {
		return cardList;
	}

	/**
	 * Setter method for the cards of the player
	 * @param cardList set cardList
	 */
	public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}

	/**
	 * Player parameterized constructor.
	 * @param id player id
	 * @param name player name
	 */
	public Player(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.assignedCountry = new ArrayList<Country>();
		this.cardList = new ArrayList<>();
		this.numeberOfCardsExchanged = 0;
	}

	/**
	 * Getter method for the player ID.
	 * @return player id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter method for the player ID.
	 * @param id set player id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter method for the player name.
	 * @return player name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter method for the player name.
	 * @param name set player name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for the player armier.
	 * @return player armies
	 */
	public int getArmies() {
		return armies;
	}
	
	/**
	 * Setter method for the player armies.
	 * @param armies set player armies
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}
	
	/**
	 * Getter method for the assigned country of the player.
	 * @return the assignedCountry
	 */
	public List<Country> getAssignedCountry() {
		return assignedCountry;
	}
	
	/**
	 * Setter method to assign country to the player.
	 * @param assignedCountry set player's assignedCountry
	 */
	public void setAssignedCountry(List<Country> assignedCountry) {
		this.assignedCountry = assignedCountry;
	}
	
	/**
	 * Getter method for the number of times the cards is exchanged.
	 * @return the numeberOfCardsExchanged
	 */
	public int getNumeberOfTimeCardsExchanged() {
		return numeberOfCardsExchanged;
	}

	/**
	 * Setter method for the cards exchanged.
	 * @param numeberOfCardsExchanged set numeberOfCardsExchanged
	 */
	public void setNumeberOfTimesCardsExchanged(int numeberOfCardsExchanged) {
		this.numeberOfCardsExchanged = numeberOfCardsExchanged;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Player)) {
			return false;
		}

		Player player = (Player) obj;
		return player.getName().equalsIgnoreCase(name);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Player [name=" + name + ", playerStrategy=" + playerStrategy + "]";
	}
}

