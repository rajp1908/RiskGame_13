package com.risk6441.entity;

import java.io.Serializable;

import com.risk6441.configuration.CardKind;

/**
 * This is the bean class for the card.
 * @author Raj
 *
 */
public class Card implements Serializable{
	
	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = 745413331877577285L;

	CardKind cardKind;
	
	private Country countryToWhichCardBelong;

	/**
	 * @return the type of the card
	 */
	public CardKind getCardKind() {
		return cardKind;
	}

	/**
	 * Parameterized Constructor for Card
	 * 
	 * @param cardKind reference to get cardType enum
	 */
	
	public Card(CardKind cardKind){
		this.cardKind = cardKind;
	}
	
	/**
	 * @param cardKind sets the kind of card
	 */
	public void setCardKind(CardKind cardKind) {
		this.cardKind = cardKind;
	}

	/**
	 * @return countryToWhichCardBelong returns country to which the card belongs
	 */
	public Country getCountryToWhichCardBelong() {
		return countryToWhichCardBelong;
	}

	/**
	 * @param countryToWhichCardBelong sets the country to which the card belongs
	 */
	public void setCountryToWhichCardBelong(Country countryToWhichCardBelong) {
		this.countryToWhichCardBelong = countryToWhichCardBelong;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Card [cardType =" + cardKind + ", CountryofCard=" + countryToWhichCardBelong + "]";
	}
	
	
}
