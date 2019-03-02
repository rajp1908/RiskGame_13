package com.risk6441.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class defines Continent with its properties like name, control value and list of countries.
 * @author Hardik
 * @see Country
 */
public class Continent implements Serializable{
	
	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = -8840145817364335110L;
	
	private String name;
	private int value;
	private HashMap<String, Country> countryMap;
	private List<Country> countries;
	private boolean isVisited = false;
	
	
	/**
	 * @param name used for name of the continent
	 * @param value used for control value of the continent
	 */
	public Continent(String name, int value) {
		super();
		this.name = name;
		this.value = value;
		this.countryMap = new HashMap<String, Country>();
		this.countries = new ArrayList<Country>();
	}

	/**
	 * Default constructor of Continent
	 */
	public Continent() {
		this.countries = new ArrayList<>();
		this.countryMap = new HashMap<>();
	}

	/**
	 * Getter method for the continent name.
	 * @return name continent name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter method for the continent name.
	 * @param name set continent name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for the continent control value.
	 * @return continent control value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Setter method for the continent control value.
	 * @param value set continent control value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Getter method for the continent countryMap.
	 * @return continent countryMap
	 */
	public HashMap<String, Country> getCountryMap() {
		return countryMap;
	}
	
	/**
	 * Setter method for the continent countries.
	 * @param countryMap set continent countryMap
	 */
	public void setCountryMap(HashMap<String, Country> countryMap) {
		this.countryMap = countryMap;
	}
	
	/**
	 * Getter method for the continent countries.
	 * @return countries list
	 */
	public List<Country> getCountries() {
		return countries;
	}
	
	/**
	 * Setter method for the continent countries.
	 * @param countries set the countries
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	
	
	/**
	 * This method checks whether the continent is visited or not.
	 * @return isVisited
	 */
	public boolean isVisited() {
		return isVisited;
	}

	/**
	 * This sets the continent to have been visited.
	 * @param isVisited set isVisited
	 */
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Continent [name=" + name + ", value=" + value + ", countries=" + countries + "]";
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Continent)) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}

		Continent continent = (Continent) obj;
		return continent.getName().equalsIgnoreCase(name);
	}
	
}
