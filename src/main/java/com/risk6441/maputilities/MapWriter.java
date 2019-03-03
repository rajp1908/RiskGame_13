/**
 * 
 */
package com.risk6441.maputilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Country;

/**
 * This class is responsible to write the map file when user creates the map.
 * @author Jemish
 */
public class MapWriter {
	
	/**
	 * This method writes the map details to the map file.
	 * @param map object of the map which is being processed
	 * @param file file path
	 */
	public void writeMapFile(Map map, File file) {
		
		FileWriter fileWriter;
		try {
			if (map == null) {
				System.out.println("Map Object is NULL!");
			}
			
			String content = parseMapAndReturnString(map);
			fileWriter = new FileWriter(file, false);
			fileWriter.write(content);
			fileWriter.close();
			
		}catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	/**
	 * This method processes the map by calling three different methods and makes a string to be written in the map file.
	 * @param map object of the map which is being processed
	 * @return String to be written in the map file
	 */
	private String parseMapAndReturnString(Map map){
		StringBuilder content = new StringBuilder();
		content = processMapAttribute(map);
		content.append(processContinent(map));
		content.append(processCountries(map));
		return content.toString();
	}
	
	
	/**
	 * This method process the map attributes.
	 * @param map object of the map which is being processed
	 * @return a String that contains the map properties.
	 */
	private StringBuilder processMapAttribute(Map map) {
		StringBuilder mapAttribute = new StringBuilder();
		mapAttribute.append("[Map]");
		mapAttribute.append("\n");
		
		for(Entry<String, String> keymap: map.getMapData().entrySet()) {
			mapAttribute.append(keymap.getKey() + "=" + keymap.getValue());
			mapAttribute.append("\n");
		}
		
		return mapAttribute;
	}
		
	/**
	 * This method processes the continents.
	 * @param map object of the map which is being processed
	 * @return a string that contains details of the continents that will eventually be written in the map file. 
	 */
	private StringBuilder processContinent(Map map) {
		StringBuilder continentData = new StringBuilder();
		continentData.append("\n");
		continentData.append("[Continents]");
		continentData.append("\n");
		return continentData;
	}
	
	/**
	 * This method processes the countries.
	 * @param map object of the map that is being processed
	 * @return a string that contains details of the countries that will eventually be written in the map file. 
	 */
	private StringBuilder processCountries(Map map) {
		StringBuilder countryData = new StringBuilder();
		countryData.append("\n");
		countryData.append("[Countries]");
		countryData.append("\n");
		
		for (Continent continent : map.getContinents()) {
			List<Country> countriesList = continent.getCountries();
			if (countriesList != null) {
				for(Country country : countriesList) {
					countryData.append(country.getName() + "," + country.getxCoordinate() + ","
							+ country.getyCoordinate() + "," + country.getBelongToContinent().getName());
					for (Country adjacentCountries : country.getAdjacentCountries()) {
						countryData.append(",");
						countryData.append(adjacentCountries.getName());
					}
					countryData.append("\n");
				}
				countryData.append("\n");
			}			}
		return countryData;
	}

}
