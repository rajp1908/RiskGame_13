package com.risk6441.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;


/**
 * This class elucidates Map and its list of continents.
 * @author Dolly
 * @see Country
 * @see Continent
 */
public class Map extends Observable implements Serializable{

	/**
	 * The serial ID
	 */
	private static final long serialVersionUID = -1455266814146248057L;
	
	private HashMap<String, String> mapData;
	private List<Continent> continents;
	private HashMap<String, Continent> continentMap;
	
	/**
	 * This is the default constructor of Map.
	 */
	public Map() {
		mapData = new HashMap<String, String>();
		continents = new ArrayList<Continent>();
		continentMap = new HashMap<String,Continent>();
	}
	
	/**
	 * This is parameterized constructor for map.
	 * @param newMap The new map object.
	 */
	public Map(Map newMap) {
		mapData = new HashMap<String, String>(newMap.mapData);
		continents = new ArrayList<Continent>(newMap.continents);
		continentMap = new HashMap<String,Continent>(newMap.continentMap);
	}
	
	/**
	 * Returns the map data.
	 * @return the mapData
	 */
	public HashMap<String, String> getMapData() {
		return mapData;
	}
	
	/**
	 * This sets the map data.
	 * @param mapData the mapData to set
	 */
	public void setMapData(HashMap<String, String> mapData) {
		this.mapData = mapData;
	}
	
	/**
	 * It returns list of continents.
	 * @return the continents
	 */
	public List<Continent> getContinents() {
		return continents;
	}
	
	/**
	 * This sets the continents.
	 * @param continents the continents to set
	 */
	public void setContinents(List<Continent> continents) {
		this.continents = continents;
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Returns the continent maps
	 * @return the continentMap
	 */
	public HashMap<String, Continent> getContinentMap() {
		return continentMap;
	}
	
	/**
	 * This sets the continent map.
	 * @param continentMap the continentMap to set
	 */
	public void setContinentMap(HashMap<String, Continent> continentMap) {
		this.continentMap = continentMap;
	}

	/**
	 * The update change method is used for observers.
	 */
	public void setChangedForMap() {
		setChanged();
		notifyObservers(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Map [mapData=" + mapData + ", continents=" + continents + ", continentMap=" + continentMap + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		ObjectOutputStream outPut = null;
		ObjectInputStream inPut = null;
		Map clonedMap = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			outPut = new ObjectOutputStream(bos);
			// serialize and pass the object
			outPut.writeObject(this);
			outPut.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			inPut = new ObjectInputStream(bin);
			clonedMap = (Map) inPut.readObject();
		} catch (Exception e) {
			System.out.println("Exception in ObjectCloner = " + e);
		} finally {
			try {
				outPut.close();
				inPut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return clonedMap;
	}
	
}
