package com.risk6441.maputilities;

import java.util.ArrayList;
import java.util.List;
import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidMap;

/**
 * @author Raj
 * @author Jemish
 *
 */
public class MapOperations {
	
	/**
	 * Adds continent to the map with details like control value etc.
	 * @param map Current map object.
	 * @param name Name of the continent.
	 * @param ctrlValue Control value of the continent.
	 * @return Returns the newly created continent.
	 * @throws InvalidMap Throws IOException if there is an issue while reading a map file.
	 */
	public static Continent addContinent(Map map, String name, String ctrlValue) throws InvalidMap {
		Continent cnt = new Continent();
		
		cnt.setName(name);
		cnt.setValue(Integer.parseInt(ctrlValue));
		
		if(map.getContinents().contains(cnt)) {
			throw new InvalidMap("The Continent with name "+name+" already exist.");
		}
		
		return cnt;
	}
	
	/**
	 * Adds country to the map and the continent with its respective details.
	 * @param map Current map object.
	 * @param name Name of the Country.
	 * @param xCo X Co-ordinate of the Country.
	 * @param yCo Y Co-ordinate of the Country.
	 * @param adjCoun Adjacent countries of the current country.
	 * @param continent Continent to which the country belongs to.
	 * @return The newly created Country. 
	 * @throws InvalidMap Throws IOException if there is an issue while reading a map file.
	 */
	public static Country addCountry(Map map, String name, String xCo, String yCo, Country adjCoun,
			Continent continent) throws InvalidMap {
		Country country = new Country();
		
		country.setxCoordinate(Integer.parseInt(xCo));
		country.setyCoordinate(Integer.parseInt(yCo));
		country.setBelongToContinent(continent);
		country.setName(name);
		
		ArrayList<Country> list = new ArrayList<Country>();
		
		if(adjCoun!=null) {
			list.add(adjCoun);
		}
		country.setAdjacentCountries(list);
		
		System.out.println(map.getContinents());
		//check if country with same name exist or not
		for(Continent allCont : map.getContinents()) {
			if(allCont.getCountries().contains(country)) {
				throw new InvalidMap("Country with same name "+name+" already exist in continent "
						+ allCont.getName() +".");
			}
		}
		
		if(adjCoun!=null) {
			adjCoun.getAdjacentCountries().add(country);
		}
		
		
		return country;
		
	}
	
	/**
	 * This method updates the continent details if the user selects the continent.
	 * @param continent The continent whose details must be updated.
	 * @param map map object {@link Map}
	 * @param name name of the continent to be updated
	 * @param ctrlValue The control value of the continent.
	 * @return The current continent.
	 * @throws InvalidMapException  InvalidMapException if any error occurs
	 */
	public static Continent updateContinent(Continent continent, Map map , String name, String ctrlValue) throws InvalidMap {
		if(!continent.getName().equals(name)) {
			if(containsContinentName(map.getContinents(), name)) {
				throw new InvalidMap("The Continent with name "+name+" already exist.");
			}
			continent.setName(name);
		}
		
		continent.setValue(Integer.parseInt(ctrlValue));
		return continent;
	}
	
	/**
	 * This method checks whether the continent name is present or not.
	 * @param list list of all continents
	 * @param name name of the continents to be updated
	 * @return true if list does not contain other continents with same name
	 */
	public static boolean containsContinentName(final List<Continent> list, final String name){
	    return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
	}
	
	/**
	 * This method updates the continent details when the user selects the country.
	 * @param country The country whose values must be updated.
	 * @param map Map Object {@link Map}
	 * @param name name for the Country to be updated - new name for the Country
	 * @param xCo X-Co-ordinate of the Country.
	 * @param yCo Y-Co-ordinate of the Country.
	 * @param adjCoun The adjacent Countries list. 
	 * @return The object to the newly updated Country.
	 * @throws InvalidMap InvalidMap if any error occurs
	 */
	public static Country updateCountry(Country country, Map map, String name,String xCo, String yCo, 
			Country adjCoun) throws InvalidMap {
		country.setxCoordinate(Integer.parseInt(xCo));
		country.setyCoordinate(Integer.parseInt(yCo));
		
		if(!country.getName().equals(name)) {
			ArrayList<Country> listAllCoun = new ArrayList<Country>();
			for(Continent cont : map.getContinents()) {
				listAllCoun.addAll(cont.getCountries());
			}
			
			if(containsCounName(listAllCoun, name)) {
				throw new InvalidMap("The Country with name "+name+" already exist.");
			}
			country.setName(name);
		}
		
		if(adjCoun!=null) {
			if(!adjCoun.getAdjacentCountries().contains(country)) {
				adjCoun.getAdjacentCountries().add(country);
			}
			
			if(!country.getAdjacentCountries().contains(adjCoun)) {
				country.getAdjacentCountries().add(adjCoun);
			}
			
		}
		
		return country;
	}
	
	
	/**
	 * This checks whether the Country name is there or not.
	 * @param list list of all Countries
	 * @param name name of the Country to be checked
	 * @return true if list does not contain other Country with same name
	 */
	public static boolean containsCounName(final ArrayList<Country> list, final String name){
	    return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
	}
	
	/**
	 * This method adds the country to the corresponding continent.
	 * @param continent
	 * 		   continent object which will be assigned Countries
	 * @param country The country which is added to the continent.
	 * @return the Object to the newly updated continent.
	 */
	public static Continent mapCountryToContinent(Continent continent, Country country) {
		
		try {
			continent.getCountries().add(country);
		}catch(Exception e) {
			ArrayList<Country> list = new ArrayList<>();
			list.add(country);
			continent.setCountries(list);
		}
		
		return continent;
	}
		

}
