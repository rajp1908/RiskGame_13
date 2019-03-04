package com.risk6441.exception;

/**
 * This class is used to handle any type of Invalid Game Move exceptions.
 * 
 * @author Dolly 
 *
 */
public class InvalidGameAction extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * This method throws a user defined exception if a map is not valid.
	 * @param message message for the exceptions thrown
	 */
	public InvalidGameAction(String message) {
		super(message);
	}
}
