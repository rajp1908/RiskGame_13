
package com.risk6441.maputilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidMap;

/**
 * This class validates the map. 
 * @author Hardik
 * @author Jemish
 *
 */
public class MapVerifier {
	
	static String message = "";

	/**
	 * This method validates the map.
	 * @param map map object for verifying map
	 * @throws InvalidMap if map has some errors
	 */
	public static void verifyMap(Map map) throws InvalidMap{
		if(map == null) {
			throw new InvalidMap("Map is not valid. It's null");
		}else {
			if(map.getContinents().size() < 1) {
				throw new InvalidMap("At least one continent must be there in the map.");	
			}else {
				//verify that map is subgraph of continents, if yes then check for continent is subgraph of countries
				verifyContinents(map);
				
				//check if map is a subgraph of continents
				if(!isMapConnectedGraph(map)) {
					throw new InvalidMap("A Continent should be a subgraph in the map. A Map should be connected graph formed by continents.");
				}
				
				checkCountryBelongToOnlyOneContinent(map);
			}
		}
	}
	
	/**
	 * This method verifies the continents.
	 * @param map map object to verify continents of the map
	 * @throws InvalidMap throws if map has some errors
	 */
	public static void verifyContinents(Map map) throws InvalidMap {
		
		for(Continent continent : map.getContinents()) {
			if(continent.getCountries().size() < 1) {
				throw new InvalidMap("At least one country should be there in continent.");
			}
			//it's verified that map is a subgraph of continents. now check that continent is a subgraph of countries.
			for(Country country : continent.getCountries()) {
				verifyCountry(country,map);
			}
			//check if continent is connected graph formed by countries
			if(!isContinentConnectedGraph(continent, map)) {
				throw new InvalidMap(message+"The Continent "+continent.getName()+" is not connected by its countries. A Continent should be a connected graph formed by countries in the map.");
			}
		}	
	}

	/**
	 * This method checks that the continents are connected or not.
	 * @param continent
	 * 			continent to be verified
	 * @param map
	 * 			object of the map
	 * @return
	 * 		  return true if continent forms a connected map.
	 */
	public static boolean isContinentConnectedGraph(Continent continent,Map map) {
		bfsCountry(continent.getCountries().get(0), map);
		boolean returnValue = true;
		for(Country t : continent.getCountries()) {
			if(t.isProcessed() == false) {
				t.setProcessed(false);
				message = t.getName()+" is not forming connected graph inside continent "+continent.getName()+".";
				returnValue = false;
				break;
			}
		}
		
		for(Country t : continent.getCountries()) {
			t.setProcessed(false);
		}
		return returnValue;
	}
	
	/**
	 * This method traverse the countries in BFS Manner.
	 * @param country
	 * 					country to be traversed in bfs
	 * @param map
	 * 			  map object
	 */
	public static void bfsCountry(Country country, Map map) {

		if(country.isProcessed() == true) {
			return;
		}

		country.setProcessed(true);

		for(Country t : country.getAdjacentCountries()){
			if((t.getBelongToContinent() == country.getBelongToContinent()) && t.isProcessed() == false)
				bfsCountry(t, map);
		}
				
	}
	

	/**
	 * This method checks that the country is connected or not.
	 * @param country country to be verified
	 * @param map object of the map
	 * @throws InvalidMap throws InvalidMapException if map is not valid
	 */
	private static void verifyCountry(Country country, Map map) throws InvalidMap {
		List<Country> adjCounList = country.getAdjacentCountries();
		
		if((adjCounList == null) || (adjCounList.size() < 1)) {
			throw new InvalidMap("Country: "+country.getName()+" must have atleast one adjacent country.");
		}
		else  {
			for(Country adjCoun : adjCounList) {
				if(!adjCoun.getAdjacentCountries().contains(country)) {
					adjCoun.getAdjacentCountries().add(country);
				}
			}
		}
	}

	
	/**
	 * This method checks that Continents form a connected graph(A Map).
	 * @param map
	 * 			 object of the map
	 * @return 
	 * 			true if map is a connected graph
	 */
	public static boolean isMapConnectedGraph(Map map) {
		System.out.println("Inside is map connected");
		if(map.getContinents().size()<2) {
			return false;
		}
		
		bfsContinent(map.getContinents().get(0), map);
		
		boolean returnValue = true;
		for(Continent continent : map.getContinents()) {
			if(continent.isVisited() == false) {
				System.out.println(continent.getName()+"xxxxxxxxxxxxxx");
				returnValue = false;
				break;
			}
		}
		
		for(Continent continent : map.getContinents()) {
			continent.setVisited(false);
		}
		
		return returnValue;
		

	}
	
	/**
	 * This method traverse the continents in BFS Manner.
	 * @param continent continent to be traversed in bfs
	 * @param map
	 * 			 map object
	 */
	public static void bfsContinent(Continent continent, Map map) {

		if(continent.isVisited() == true) {
			return;
		}

		continent.setVisited(true);

		System.out.println("cont in dfs 1"+continent.getName());
		for(Continent c : getAdjacentContinents(continent, map)){
			System.out.println("inside adjCont loop");
			if(c.isVisited() == false)
				bfsContinent(c, map);
		}
				
	}
	
	/**
	 * This method returns the adjacent continent as a list of particular continent.
	 * @param continent continent whose adjacent countries to be found
	 * @param map map object
	 * @return the adjacent continent as a list of particular continent.
	 */
	public static List<Continent> getAdjacentContinents(Continent continent, Map map){
		List<Continent> adjacentContinents = new ArrayList<>();
		
		HashSet<Country> adjCounMasterSet = new HashSet<>();
		for(Country country : continent.getCountries()) {
			adjCounMasterSet.addAll(country.getAdjacentCountries());
		}
		
		System.out.println(adjCounMasterSet);
		
		for(Continent otherCont : map.getContinents()) {
			if(!continent.equals(otherCont)) {
				//process if there is any relation between two continents
				//returns true if both are totally different
				if(!Collections.disjoint(adjCounMasterSet, otherCont.getCountries())) {
					System.out.println("Inside disjoint");
					//some countries are common
					adjacentContinents.add(otherCont);
				}
			}
		}
		return adjacentContinents;
	}
	
	/**
	 * This method checks whether country belongs to only one continent or not.
	 * @param map map object
	 * @throws InvalidMap throws InvalidMapException if map is not valid.
	 */
	public static void checkCountryBelongToOnlyOneContinent(Map map) throws InvalidMap {
		HashMap<Country, Integer> countryBelongToContinentCount = new HashMap<>();

		for (Continent continent : map.getContinents()) {
			for (Country country : continent.getCountries()) {
				if (!countryBelongToContinentCount.containsKey(country)) {
					countryBelongToContinentCount.put(country, 1);
				} else {
					throw new InvalidMap("Country: "+country.getName()+"must belong to only one continents.");
				}
			}
		}
	}
	
}
