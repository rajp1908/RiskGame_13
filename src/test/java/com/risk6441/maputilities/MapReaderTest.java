package com.risk6441.maputilities;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk6441.entity.Map;
import com.risk6441.exception.InvalidMap;
import com.risk6441.maputilities.MapReader;

/**
 * This is the test class for MapReader. {@link MapReader}
 * @author Jemish
 * @author Raj
 */
public class MapReaderTest {
	
	static File file;
	static MapReader mapReader;
	String[] invalidFiles = {"continent_is_not_a subgraph.map","continent_without_country.map",
			"country_with_two_continent.map","country_without_continent.map","country_not_assigned_any_continent.map"};
	ClassLoader loader;
	
	/**
	 * This method is called before executing any methods of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		mapReader = new MapReader();
	}
	
	
	/**
	 * This method is executed before every method of the class.
	 * @throws IOException Throws error if map the is not valid.
	 */
	@Before
	public void beforeMethod() throws IOException {
		loader = getClass().getClassLoader();
	}
	
	/**
	 * This method tests the valid map.
	 * @throws InvalidMapException Throws error if map is not valid.
	 */
	@Test
	public void testValidMap() throws InvalidMap {
		file = new File(loader.getResource("valid.map").getFile());
		Map map = mapReader.readMapFile(file);
		assertEquals(map.getContinents().size(),8);
	}
	
	/**
	 * This method test the map whose continent are not conntected graph formed by countries
	 * @throws InvalidMap InvalidMapException
	 */
	@Test (expected=InvalidMap.class)
	public void checkForContinentNotBeingSubgraph() throws InvalidMap {
		System.out.println("");
		file = new File(loader.getResource(invalidFiles[0]).getFile());
		mapReader.readMapFile(file);
	}
	
	/**
	 * This method tests the map in which Countries are not mapped mutually.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMap.class)
	public void checkForCountryNotMappedMutually() throws InvalidMap {
		file = new File(loader.getResource(invalidFiles[1]).getFile());
		mapReader.readMapFile(file);
	}
	
	/**
	 * This method tests the map in which countries is mapped with two continents.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMap.class)
	public void checkForCountryWithTwoContinents() throws InvalidMap {
		file = new File(loader.getResource(invalidFiles[2]).getFile());
		mapReader.readMapFile(file);
	}
	
	/**
	 * This method tests the map which has countries without continents.
	 * @throws InvalidMap InvalidMapException
	 */
	@Test (expected=InvalidMap.class)
	public void checkForCountryWithoutContinents() throws InvalidMap {
		file = new File(loader.getResource(invalidFiles[3]).getFile());
		mapReader.readMapFile(file);
	}
	
	/**
	 * This method tests the map in which Country has no continents.
	 * @throws InvalidMap InvalidMapException
	 */
	@Test (expected=InvalidMap.class)
	public void checkForCountryNotAssignedToAnyContinents() throws InvalidMap {
		file = new File(loader.getResource(invalidFiles[4]).getFile());
		mapReader.readMapFile(file);
	}

}
