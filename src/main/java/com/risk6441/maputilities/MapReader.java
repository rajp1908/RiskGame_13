package com.risk6441.maputilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.text.WordUtils;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidMap;

/**
 * This class reads and parse the map file and sets data in corresponding objects.
 * @author Jemish
 * @author Raj
 */
public class MapReader {
	
	//make a object of Map class to return it once map is processed successfully.
	private Map map;
	
	//make a map to make sure that country belongs to only one continent
	private HashMap<String, Integer> countryBelongContinentCount = new HashMap<String, Integer>();
	
	//default constructor to initialize the map
	public MapReader() {
		this.map = new Map();
	}
	
	/**
	 * return the map object after processing the map file
	 * @return the map
	 */
	private Map getMap() {
		return map;
	}
	
	
	/**
	 * This method is used to read and process map data
	 * @param file file path
	 * @return map returns the map object after processing the file data
	 * @throws InvalidMap throws InvalidMapException if map is not valid 
	 */
	private Map processMapFile(File file) throws InvalidMap {
		

		Scanner mapFileReader;
		try {
			mapFileReader = new Scanner(new FileInputStream(file));
			StringBuilder mapString = new StringBuilder();
			int count=0;
			String prevLine = "";
			//process and read map file in three steps
			while(mapFileReader.hasNext()) {
				String line = mapFileReader.nextLine();
				if(!line.isEmpty()) {
					mapString.append(line + "|");
					prevLine = line;
					count=0;
				}
				else if(line.isEmpty())
				{
//					if(prevLine.equals("[Continents]") || prevLine.equals("[Countries]")) {
//						continue;
//					}
					
					count++;
					if(count==1)
						mapString.append("\n");
					else
						count=0;
				}
			}

			//set map attributes 
			System.out.println(mapString);
			mapFileReader = new Scanner(mapString.toString());
			map = processMapAttribute(mapFileReader);
			//set continents info

			//set country info

		}
		catch(IOException e) {
			System.out.println("Map File is not selected");
			System.out.println(e.getMessage());
		}
		
		
		return map;
	}
	
	
	/**
	 * This method process map attributes and call method for processing continents.
	 * @param scan of type {@link Scanner}
	 * @return Map of type {@link Map}
	 * @throws InvalidMapException throws InvalidMapException if map is not valid.
	 */
	private Map processMapAttribute(Scanner scan) throws InvalidMap{
		
		HashMap<String, String> mapAttributeMap = new HashMap<String, String>();
		
		StringTokenizer tokensForMapAttribute = new StringTokenizer(scan.nextLine(), "|");
		while (tokensForMapAttribute.hasMoreTokens()) {
			String str = tokensForMapAttribute.nextToken();
			System.out.println(str);
			if (str.equalsIgnoreCase("[Map]")) {
				continue;
			} else {
				String[] data = str.split("=");
				mapAttributeMap.put(data[0], data[1]);
			}
		}
		
		map.setMapData(mapAttributeMap);
		
		List<Continent> continentList = parseContinents(scan);
		HashMap<String, Continent> continentMap = new HashMap<String, Continent>();
		for (Continent continent : continentList) {
			continentMap.put(continent.getName(), continent);
		}
		map.setContinentMap(continentMap);
		map.setContinents(continentList);
		
		return map;
	}
	
	/**
	 * This method processes the continents and call method to process Countries
	 * and also  map Countries and continents.
	 * @param scan scanner object which points to line of the file which is to be processed
	 * @return continentList after processing
	 * @throws InvalidMapException throws InvalidMapException if map is not valid
	 */
	private List<Continent> parseContinents(Scanner scan) throws InvalidMap{
		List<Continent> continentList = new ArrayList<Continent>();
		StringTokenizer tokenForContinents = new StringTokenizer(scan.nextLine(), "|");
		while (tokenForContinents.hasMoreTokens()) {
			String line = tokenForContinents.nextToken();
			if (line.equalsIgnoreCase("[Continents]")) {
				continue;
			} else {
				Continent continent = new Continent();
				String[] data = line.split("=");
				continent.setName(WordUtils.capitalizeFully(data[0].trim()));
				System.out.println(data[0]);
				continent.setValue(Integer.parseInt(data[1]));
				continentList.add(continent);
			}
		}
		
		List<Country> countryList = new ArrayList<Country>();
		while (scan.hasNext()) {
			String countryData = scan.nextLine();
			//call processCountry for each line of country
			countryList.addAll(parseCountries(countryData, continentList));
		}
		
		//here you can create continent map 
		//pass it to Country method and set there only
		
		HashMap<String, Country> countryMap = new HashMap<String, Country>();
		for (Country t : countryList) {
			countryMap.put(t.getName(), t);
		}
		
		//Map neighbour Country object to Country
		for(Country country : countryList) {
			for(String adjacentCountry : country.getAdjCountries()) {
				if(countryMap.containsKey(adjacentCountry)){
					if (country.getAdjacentCountries() == null) {
						country.setAdjacentCountries(new ArrayList<Country>());
					}
					country.getAdjacentCountries().add(countryMap.get(adjacentCountry));
				}else {
					//if particular country has adjacent country defined, but actually it doesn't exist
					throw new InvalidMap("Country: " + adjacentCountry + " not assigned to any continent.");
				}
			}
			
		}
		
		//Map countries and continent
		for(Continent continent : continentList) {
			HashMap<String, Country> continentTMap = new HashMap<String, Country>();
			for(Country country : countryList) {
				if (country.getBelongToContinent().equals(continent)) {
					if (continent.getCountries() == null) {
						continent.setCountries(new ArrayList<Country>());
						continentTMap.put(country.getName(), country);
					}
					continent.getCountries().add(country);
					continentTMap.put(country.getName(), country);
				}
			}
			continent.setCountryMap(continentTMap);
		}
		
		
		return continentList;
	}
	
	/**
	 * This method processes countries and check that it should be assign to only one continent
	 * @param countryLine Line from the map file for the Country
	 * @param continentList Produces the continent list.
	 * @return countryList After processing
	 * @throws InvalidMapException Throws InvalidMapException if map is not valid
	 */
	private List<Country> parseCountries(String countryLine, List<Continent> continentList) throws InvalidMap{
		
		List<Country> countryList = new ArrayList<Country>();
		StringTokenizer tokenForCountry = new StringTokenizer(countryLine, "|");
		while (tokenForCountry.hasMoreTokens()) {
			
			String element = tokenForCountry.nextToken();
			if (element.equalsIgnoreCase("[Countries]")) {
				continue;
			} else {
				
				Country country = new Country();
				List<String> adjacentCountries = new ArrayList<String>();
				String[] dataOfCountry = element.split(",");
				dataOfCountry[0] = WordUtils.capitalizeFully(dataOfCountry[0].trim());
				
				country.setName(dataOfCountry[0]);
				country.setxCoordinate(Integer.parseInt(dataOfCountry[1]));
				country.setyCoordinate(Integer.parseInt(dataOfCountry[2]));

				for (Continent continent : continentList) {
					if (continent.getName().equalsIgnoreCase(dataOfCountry[3])) {
						country.setBelongToContinent(continent);
						
						if (countryBelongContinentCount.get(dataOfCountry[0]) == null) {
							countryBelongContinentCount.put(dataOfCountry[0], 1);
						} else {
							throw new InvalidMap("A Country "+ country.getName() +" can be assigned to only one Continent.");
						}
					}
				}
				if (countryBelongContinentCount.get(dataOfCountry[0]) == null) {
					throw new InvalidMap("A Country must be assigned to one Continent.");
				}
				
				for (int i = 4; i < dataOfCountry.length; i++) {
					String str = WordUtils.capitalizeFully(dataOfCountry[i].trim());
					if(!country.getName().equals(str))
						adjacentCountries.add(str);
				}
				country.setAdjCountries(adjacentCountries);
				countryList.add(country);
			}
			
		}
		
		return countryList;
	}

}
