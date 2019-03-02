package com.risk6441.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hardik
 * This class defines Country with its properties like its coordinates, the continent to which country
 * belongs, its adjacent countries and whether its been assigned to any player or not.
 * @see Continent 
 */
public class Country implements Serializable{
	
	
	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = -2741974265396284180L;
	
	private String name;
	private int xCoordinate;
	private int yCoordinate;
	private Continent belongToContinent;
	private List<String> adjCountries;
	private Player player;
	private List<Country> adjacentCountries;
	private boolean isProcessed;
	/**
	 * Getter method for the player.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setter method for the player.
	 * @param player set player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Getter method for the country armies.
	 * @return army
	 */
	public int getArmy() {
		return army;
	}

	/**
	 * Setter method for the country armies.
	 * @param army set army
	 */
	public void setArmy(int army) {
		if(army > 5000) {
			this.army = 5000;
			return;
		}
		this.army = Math.abs(army);
	}


	private int army;
	
	/**
	 * Getter method for the country name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Parameterized constructor for the Country.
	 * @param adjCountries names of adjacent countries
	 * @param adjacentCountries object of adjacent countries
	 */
	public Country(List<String> adjCountries, List<Country> adjacentCountries) {
		this.adjCountries = adjCountries;
		this.adjacentCountries = adjacentCountries;
	}

	/**
	 * Default constructor for the Country.
	 */
	public Country() {
		adjCountries = new ArrayList<>();
		adjacentCountries = new ArrayList<>();
	}

	/**
	 * Setter method for the country name.
	 * @param name set country name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for the X coordinate of the country.
	 * @return xCoordinate
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}
	
	/**
	 * Setter method for the X coordinate of the country.
	 * @param xCoordinate set xCoordinate
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	/**
	 * Getter method for the y coordinate of the country.
	 * @return yCoordinate
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}
	
	/**
	 * Setter method for the y coordinate of the country.
	 * @param yCoordinate set yCoordinate
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	/**
	 * Getter method to returns continent which belongs to the country.
	 * @return belongToContinent
	 */
	public Continent getBelongToContinent() {
		return belongToContinent;
	}
	
	/**
	 * Setter method to set the continent for the country.
	 * @param belongToContinent set belongToContinent
	 */
	public void setBelongToContinent(Continent belongToContinent) {
		this.belongToContinent = belongToContinent;
	}
	
	/**
	 * Getter method to return the adjacent countries of country.
	 * @return adjCountries
	 */
	public List<String> getAdjCountries() {
		return adjCountries;
	}
	
	/**
	 * Setter method for the adjacent countries to the country.
	 * @param adjCountries set adjCountries
	 */
	public void setAdjCountries(List<String> adjCountries) {
		this.adjCountries = adjCountries;
	}
	
	/**
	 * Getter method to return the adjacent countries to the countries.
	 * @return adjacentCountries
	 */
	public List<Country> getAdjacentCountries() {
		return adjacentCountries;
	}
	
	/**
	 * Setter method for the adjacent countries to the countries.
	 * @param adjacentCountries set adjacentCountries
	 */
	public void setAdjacentCountries(List<Country> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}
	
	/**
	 * Checks whether the country is processed or not.
	 * @return isProcessed returns boolean value whether a country is processed or not
	 */
	public boolean isProcessed() {
		return isProcessed;
	}
	
	/**
	 * Setter method for the country as processed.
	 * @param isProcessed set isProcessed
	 */
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Country [name=" + name +"]";
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */	
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Country)) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}

		Country t = (Country) obj;
		return t.getName().equalsIgnoreCase(name);
	}
	
}
