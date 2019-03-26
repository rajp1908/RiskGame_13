package com.risk6441.maputilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidMap;
import com.risk6441.maputilities.MapOperations;

/**
 * This is the test class for MapOperation. {@link MapOperations}
 * @author Jemish
 * @author Raj
 */
public class MapOperationsTest {

	static Map map;
	static Continent continent;
	String continentName = "Asia";
	String counName = "Canada";
	int controlValue1 = 1;
	int controlValue2 = 2;
	static Country country;
	static Country adjCountry;
	int x1=1;
	int y1=1;
	int x2=2;
	int y2=2;
	
	static HashMap<String,String> mapData;
	
	String mapAuthor = "Robert";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		map = new Map();
		mapData = new HashMap<>();
		continent = new Continent();
		country = new Country();
		adjCountry =  new Country();
	}
	
	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void before() {
		mapData = new HashMap<>();
		mapData.put("author", mapAuthor);
		mapData.put("image", mapImage);
		mapData.put("wrap", mapWrap);
		mapData.put("scroll", mapScroll);
		mapData.put("warn", mapWarn);		
		map.setMapData(mapData);
	}
	
	/**
	 * This method tests the functionality to add a continent.
	 * @throws InvalidMap InvalidMapException
	 */
	@Test
	public void testAddContinent() throws InvalidMap {
		continent = MapOperations.addContinent(map, continentName, String.valueOf(controlValue1));
		assertNotNull(continent);
		assertEquals(continent.getName(), continentName);
		assertEquals(continent.getValue(), controlValue1);
	}
	
	/**
	 *  This method test the functionality to update the continents.
	 * @throws InvalidMap InvalidException if continent with same name already exists
	 */
	@Test
	public void testUpdateContinent() throws InvalidMap {
		continent = MapOperations.updateContinent(continent, map,continentName ,String.valueOf(controlValue2));
		assertEquals(continent.getValue(), controlValue2);
		assertNotEquals(continent.getValue(), controlValue1);
		assertEquals(continent.getName(), continentName);
	}
	
	
	/**
	 *  This method test the functionality to add Countries.
	 * @throws InvalidMap Throws invalid map if the map wasn't read properly.
	 */
	@Test
	public void testAddCountry() throws InvalidMap {
		country = MapOperations.addCountry(map, counName, String.valueOf(x1), String.valueOf(y1), null, continent);
		assertNotNull(continent);
		assertEquals(country.getName(), counName);
		assertEquals(country.getxCoordinate(), x1);
		assertEquals(country.getyCoordinate(), y1);
		assertEquals(country.getBelongToContinent(), continent);
	}
	
	/**
	 *  This method test the functionality to update the Country.
	 * @throws InvalidMap InvalidException if Country with same name already exists
	 */
	@Test
	public void testUpdateCountry() throws InvalidMap {
		country = MapOperations.updateCountry(country, map, counName,String.valueOf(x2), String.valueOf(y2), null);
		Assert.assertNotNull(country);
		Assert.assertEquals(country.getxCoordinate(), x2);
		Assert.assertEquals(country.getyCoordinate(), y2);
		Assert.assertNotEquals(country.getxCoordinate(), x1);
		Assert.assertNotEquals(country.getyCoordinate(), y1);
	}
	
	/**
	 * This method tests the functionality to map a Country with the continent.
	 * @throws InvalidMap InvalidMapException
	 */
	@Test
	public void testMapCountryToContinent() throws InvalidMap {
		Country newCountry = new Country();
		newCountry = MapOperations.addCountry(map, "Canada", "1", "10", null, continent);
		continent = MapOperations.mapCountryToContinent(continent, newCountry);
		Assert.assertNotNull(continent);
		Assert.assertTrue(continent.getCountries().contains(newCountry));
	}
}
