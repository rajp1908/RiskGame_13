package com.risk6441.configuration;

/**
 * This class defines static properties for the game such as 
 * number of armies according to the number of players.
 * @author Deep
 */

public class Configuration {

	/**
	 * The ARMIES_TWO_PLAYER Constant assigns 40 armies to 2 player in the game 
	 */
	public static final Integer ARMIES_TWO_PLAYER = 40;

	/**
	 *The message to be printed in text after attack phase is completed 
	 */
	public static String message = "";
	
	/**
	 * The ARMIES_TWO_PLAYER Constant assigns 35 armies to 3 player in the game 
	 */
	public static final Integer ARMIES_THREE_PLAYER = 35;
	
	/**
	 * The ARMIES_TWO_PLAYER Constant assigns 30 armies to 4 player in the game 
	 */
	public static final Integer ARMIES_FOUR_PLAYER = 30;
	
	/**
	 * The ARMIES_TWO_PLAYER Constant assigns 25 armies to 5 player in the game 
	 */
	public static final Integer ARMIES_FIVE_PLAYER = 25;
	
	/**
	 * The ARMIES_TWO_PLAYER Constant assigns 20 armies to 6 player in the game 
	 */
	public static final Integer ARMIES_SIX_PLAYER = 20;
	
	/**
	 * The isGameOver
	 */
	public static boolean isGameOver = false;

	public static boolean isAllComputerPlayer = true;
	
	public static int waitBeweenTurn = 3000; //   7 sec
	
	public static boolean isPopUpShownInAutoMode = true;

	public static boolean isTournamentMode = false;

	public static boolean isThreadingForTournament = false;
}
