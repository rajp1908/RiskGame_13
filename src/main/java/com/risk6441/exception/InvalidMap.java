package com.risk6441.exception;

/**
 * User defined exception is defined in this class
 * @author Raj
 *
 */
public class InvalidMap extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * This method throws user defined exception if map is invalid
	 * @param message message related to exception 
	 */
	public InvalidMap(String message) {
		super(message);		
	}

}
