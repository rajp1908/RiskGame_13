/**
 * 
 */
package com.risk6441.maputilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.entity.Continent;
import com.risk6441.entity.Map;
import com.risk6441.entity.Country;
import com.risk6441.exception.InvalidMap;
import com.risk6441.maputilities.MapReader;
import com.risk6441.maputilities.MapVerifier;


/**
 * This is the test class for MapVerifier. {@link MapVerifier}
 * @author Raj
 * @author Jemish
 */
public class MapVerifierTest {

	static Continent continent;
	static Country country;
	static Map map;
	ClassLoader loader;

	String mapAuthor = "Jemish";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	String continentName = "North America";
	int controlValue = 10;
	
	static HashMap<String, String> mapData = new HashMap<>();
	List<Continent> continentList;

	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		continent = new Continent();
		country = new Country();
		map = new Map();
		
	}	
	
	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeTest() {
		mapData.put("author", mapAuthor);
		mapData.put("image", mapImage);
		mapData.put("wrap", mapWrap);
		mapData.put("scroll", mapScroll);
		mapData.put("warn", mapWarn);		
		map.setMapData(mapData);
		loader = getClass().getClassLoader();
		continent.setName(continentName);
		continent.setValue(controlValue);
		
		country.setName("Canada");
		country.setxCoordinate(1);
		country.setyCoordinate(2);
		
		continentList = new ArrayList<>();
		continentList.add(continent);
	}
	
	/**
	 * This method tests that map is null or not.
	 * @throws InvalidMap InvalidMapException
	 */
	@Test (expected = InvalidMap.class)
	public void verifyNullMap() throws InvalidMap {
		MapVerifier.verifyMap(null);
	}
	
	
	/**
	 * This method verifies that map has at least one continent.
	 * @throws InvalidMap InvalidMapException
	 */
	@Test (expected = InvalidMap.class)
	public void verifyMap() throws InvalidMap {
		MapVerifier.verifyMap(new Map());
	}
	
	/**
	 * This method is used to verify that continent is null or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMap.class)
	public void validateContinentForNullCountry() throws InvalidMap {
		map.setContinents(continentList);
		MapVerifier.verifyContinents(map);	
	}
	
	/**
	 * This method is used to test if a continent is a sub-graph or not.
	 * @throws InvalidMap invalid map exception.
	 */
	@Test
	public void validateMapForSubGraph() throws InvalidMap {		
		assertFalse(MapVerifier.isMapConnectedGraph(map));
	}
	
	/**
	 * This method verify that continent is subgraph or not formed by countries or not.
	 * @throws InvalidMap invalid map exception.
	 */
	@Test 
	public void validateContinentForSubGraph() throws InvalidMap {
		List<Country> counList = new ArrayList<>();
		counList.add(country);
		Country country2 =  new Country();
		country2.setName("India");
		country2.setxCoordinate(1);
		country2.setyCoordinate(2);
		counList.add(country);
		continent.setCountries(counList);
		assertEquals(MapVerifier.isContinentConnectedGraph(continent, map), true);
		
		List<Country> adjCounList = new ArrayList<>();
		adjCounList.add(country);
		country2.setAdjacentCountries(adjCounList);
		
		adjCounList = new ArrayList<>();
		adjCounList.add(country2);
		country.setAdjacentCountries(adjCounList);
		
		counList.remove(1);
		counList.add(country2);
		continent.setCountries(counList);
		assertTrue(MapVerifier.isContinentConnectedGraph(continent, map));
	}
	

	/**
	 * This method checks if the program can load 3D cliff map.
	 * @throws InvalidMap Throws invalid map exception.
	 */
	@Test
	public void check3Dcliff() throws InvalidMap
	{
		File file = new File(loader.getResource("3DCliff.map").getFile());
		System.out.println(file==null);
		Map map1 = new MapReader().readMapFile(file);
		assertNotNull(map1);
	}
	
	/**
	 * This method checks if the program can load twin volcano map.
	 * @throws InvalidMap Throws invalid map exception.
	 */
	@Test (expected = InvalidMap.class)
	public void checkTwinVolcano() throws InvalidMap
	{
		File file = new File(loader.getResource("TwinVolcano.map").getFile());
		Map map1 = new MapReader().readMapFile(file);
	}
	
	
	/**
	 * This method checks if the program can load world map.
	 * @throws InvalidMap Throws invalid map exception.
	 */
	@Test 
	public void checkWorldMap() throws InvalidMap
	{
		File file = new File(loader.getResource("World.map").getFile());
		Map map = new MapReader().readMapFile(file);
		assertNotNull(map);
	}
	
	
	/**
	 * This method checks if the program can load unconnected continent map.
	 * @throws InvalidMap Throws invalid map exception.
	 */
	@Test (expected = InvalidMap.class)
	public void checkUnconnectedContinentMap() throws InvalidMap
	{
		File file = new File(loader.getResource("UnconnectedContinent.map").getFile());
		Map map = new MapReader().readMapFile(file);
	}
	
}
