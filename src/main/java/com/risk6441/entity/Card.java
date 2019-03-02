package com.risk6441.entity;
import java.io.Serializable;

import com.risk6441.configuration.CardType;

/**
 * This class is the bean class for the card.
 * @author Dolly
 *
 */
public class Card implements Serializable{
	
	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = 745413331877577285L;

	CardType cardType;
	
	private Country countryToWhichCardBelong;

	/**
	 * @return the cardKind
	 */
	public CardType getCardType() {
		return cardType;
	}

	/**
	 * Parameterized Constructor for Card
	 * 
	 * @param cardType
	 * reference to get cardType enum
	 */
	
	public Card(CardType cardType){
		this.cardType = cardType;
	}
	
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the countryToWhichCardBelong
	 */
	public Country getCountryToWhichCardBelong() {
		return countryToWhichCardBelong;
	}

	/**
	 * @param countryToWhichCardBelong the countryToWhichCardBelong will be added
	 */
	public void setCountryToWhichCardBelong(Country countryToWhichCardBelong) {
		this.countryToWhichCardBelong = countryToWhichCardBelong;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Card [cardType =" + cardType + ", CountryofCard=" + countryToWhichCardBelong + "]";
	}
	
	
}
